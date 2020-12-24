package com.example.myserver.service

import android.app.Service
import android.content.Intent
import android.os.*
import com.example.myserver.CurrentCityManager
import com.example.myserver.callback.CurrentCityChangeCallBack

//import com.example.myserver.bean.CurrentCity

class MyCurrentCityService: Service() {
    private val TAG = "MyCurrentCityService"

    companion object {
        var mListenerList = RemoteCallbackList<CurrentCityChangeCallBack>()
    }

    private val mManagerBinder: Binder = object : CurrentCityManager.Stub() {

        override fun registerCurrentCityListener(listener: CurrentCityChangeCallBack) {
            mListenerList.register(listener)
        }

        override fun unregisterCurrentCityListener(listener: CurrentCityChangeCallBack) {
            mListenerList.unregister(listener)
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return mManagerBinder
    }

}