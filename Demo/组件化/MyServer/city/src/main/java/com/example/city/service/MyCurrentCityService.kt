package com.example.city.service

import android.app.Service
import android.content.Intent
import android.os.*
import com.example.city.CurrentCityManager
import com.example.city.callback.CurrentCityChangeCallBack

//import com.example.city.bean.CurrentCity

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