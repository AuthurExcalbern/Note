package com.example.mynewsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    abstract val coroutineExceptionHandler: CoroutineExceptionHandler

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext



    protected fun launchCoroutine(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(coroutineExceptionHandler) {
            block()
        }
    }
}