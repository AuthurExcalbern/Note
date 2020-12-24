package com.example.myserver.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.RemoteCallbackList
import com.example.myserver.UserStateManager
import com.example.myserver.callback.UserStateChangeCallBack

class MyUserStateChangeService: Service() {

    companion object {
        var mListenerList = RemoteCallbackList<UserStateChangeCallBack>()
    }

    private val mManagerBinder: Binder = object : UserStateManager.Stub() {

        override fun registerUserStateListener(listener: UserStateChangeCallBack) {
            mListenerList.register(listener)
        }

        override fun unregisterUserStateListener(listener: UserStateChangeCallBack) {
            mListenerList.unregister(listener)
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return mManagerBinder
    }

}