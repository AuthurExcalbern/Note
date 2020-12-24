package com.example.common

import android.app.Application
import android.content.Context

open class BaseApplication: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}