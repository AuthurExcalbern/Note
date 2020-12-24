package com.example.mynewsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.RequestParameter
import com.example.mynewsapp.commons.ExceptionHandler
import com.example.mynewsapp.di.MyDependenceProvider
import com.example.mynewsapp.mappers.toDomain
import com.example.mynewsapp.mappers.toPresentation
import com.example.mynewsapp.models.NewsPresentation
import com.example.mynewsapp.models.states.Error
import com.example.mynewsapp.models.states.NewsViewState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

internal class NewsViewModel : BaseViewModel() {

    private val getAllNewsUseCase = MyDependenceProvider.getAllNewsUseCase
    private val getRemoteNewsUseCase = MyDependenceProvider.getRemoteNewsUseCase
    private val deleteAllNewsUseCase = MyDependenceProvider.deleteAllNewsUseCase
    private val insertNewsUseCase = MyDependenceProvider.insertNewsUseCase

    private var getAllNewsJob: Job? = null
    private var getRemoteNewsJob: Job? = null
    private var deleteAllNewsJob: Job? = null
    private var insertNewsJob: Job? = null

    val newsViewState: LiveData<NewsViewState>
        get() = _newsViewState
    private var _newsViewState = MutableLiveData<NewsViewState>()

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        val message = ExceptionHandler.parse(exception)
        _newsViewState.value =
            _newsViewState.value?.copy(isLoading = false, error = Error(message))
    }

    private var requestNewsChannel = "头条"
    private var requestNewsNum = 10
    private var requestNewsStart = 0

    init {
        _newsViewState.value =
            NewsViewState(isLoading = true, error = null, newsList = mutableListOf())
        getAllNews()
    }

    // Android API

    override fun onCleared() {

        deleteAndSave()


/*
        getAllNewsJob?.cancel()
        getRemoteNewsJob?.cancel()
        deleteAllNewsJob?.cancel()
        insertNewsJob?.cancel()*/

        Log.d(TAG, "clear")

        super.onCleared()
    }

    private fun deleteAndSave() {
        _newsViewState.value?.newsList?.run {
            if (this.isNotEmpty()) {
                deleteAllNews()

                val saveNum = if (this.size > 10) 10 else this.size

                Log.d(TAG, "delete all news and save $saveNum")
                for (i in 0 until saveNum){
                    Log.d(TAG, "start save $i")
                    saveNews(this[i])
                }
            }
        }
    }

    private fun getAllNews() {
        getAllNewsJob = launchCoroutine {
            onNewsLoading()
            loadLocalNews()
        }
    }

    fun getRemoteNews() {
        getRemoteNewsJob = launchCoroutine {
            onNewsLoading()
            loadRemoteNews()
        }
    }

    private fun saveNews(newsPresentation: NewsPresentation) {
        insertNewsJob = viewModelScope.launch(NonCancellable) {
            insertNewsUseCase(newsPresentation.toDomain()).collect {
                Log.d(TAG, "save success, long = $it")
            }
        }
    }

    private fun deleteAllNews() {
        deleteAllNewsJob = launchCoroutine {
            deleteAllNewsUseCase(Unit).collect { rows ->
                Log.d(TAG, "delete all , rows = $rows")
            }
        }
    }

    // Private API

    private fun onNewsLoading() {
        _newsViewState.value = _newsViewState.value?.copy(isLoading = true)
    }

    private suspend fun loadLocalNews() {
        getAllNewsUseCase(Unit).collect { newsList ->
            if (newsList.isNotEmpty()) {
                Log.d(TAG, "local news is not empty, size = " + newsList.size)
                val news = newsList.map { it.toPresentation() }
                onNewsLoadingComplete(news)
            } else {
                Log.d(TAG, "local news is empty, start get remote news")
                getRemoteNews()
            }
        }
    }

    private suspend fun loadRemoteNews() {
        val requestParam = RequestParameter(requestNewsChannel, requestNewsNum, requestNewsStart)
        requestNewsStart += requestNewsNum

        getRemoteNewsUseCase(requestParam).collect { newsList ->

            Log.d(TAG, "get remote news success, size = " + newsList.size.toString())

            val news = newsList.map { it.toPresentation() }
            val newNews = ArrayList<NewsPresentation>()
            newNews.addAll(news)
            _newsViewState.value?.newsList?.run {
                newNews.addAll(this)
            }

            onNewsLoadingComplete(newNews)
        }
    }

    private fun onNewsLoadingComplete(newsList: List<NewsPresentation>) {
        _newsViewState.value =
            _newsViewState.value?.copy(isLoading = false, newsList = newsList)
    }

    companion object {
        const val TAG = "NewsViewModel"
    }
}