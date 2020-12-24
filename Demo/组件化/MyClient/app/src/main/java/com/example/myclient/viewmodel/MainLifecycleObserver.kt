package com.example.myclient.viewmodel

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.city.CurrentCityManager
import com.example.city.callback.CurrentCityChangeCallBack
import com.example.myclient.MyApplication
import com.example.state.UserStateManager
import com.example.state.callback.UserStateChangeCallBack

class MainLifecycleObserver(
        private val mCurrentCityChangeCallBack : CurrentCityChangeCallBack.Stub,
        private val mUserStateChangeCallBack : UserStateChangeCallBack.Stub
): LifecycleObserver {

    private var currentCityManager: CurrentCityManager? = null
    private var currentCityConnectedState: Boolean = false

    private var userStateManager: UserStateManager? = null
    private var userStateConnectedState: Boolean = false

    private val currentCityServiceConnection: ServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            currentCityManager = CurrentCityManager.Stub.asInterface(service)
            currentCityConnectedState = true

            currentCityManager?.registerCurrentCityListener(mCurrentCityChangeCallBack)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            currentCityConnectedState = false
            currentCityManager = null
        }

    }
    private val userStateServiceConnection: ServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            userStateManager = UserStateManager.Stub.asInterface(service)
            userStateConnectedState = true

            userStateManager?.registerUserStateListener(mUserStateChangeCallBack)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            userStateConnectedState = false
            userStateManager = null
        }

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun connectService() {

        val currentCityServiceIntent = Intent()
        currentCityServiceIntent.setPackage("com.example.myserver")
        currentCityServiceIntent.action = "android.intent.action.CURRENT_CITY_SERVICE"

        val userStateServiceIntent = Intent()
        userStateServiceIntent.setPackage("com.example.myserver")
        userStateServiceIntent.action = "android.intent.action.USER_STATE_CHANGE_SERVICE"

        MyApplication.context.bindService(
                currentCityServiceIntent,
                currentCityServiceConnection,
                AppCompatActivity.BIND_AUTO_CREATE
        )

        MyApplication.context.bindService(
                userStateServiceIntent,
                userStateServiceConnection,
                AppCompatActivity.BIND_AUTO_CREATE
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun disconnectService() {
        if(currentCityConnectedState){
            currentCityManager?.unregisterCurrentCityListener(mCurrentCityChangeCallBack)
            MyApplication.context.unbindService(currentCityServiceConnection)
        }
        if(userStateConnectedState) {
            userStateManager?.unregisterUserStateListener(mUserStateChangeCallBack)
            MyApplication.context.unbindService(userStateServiceConnection)
        }
    }
}