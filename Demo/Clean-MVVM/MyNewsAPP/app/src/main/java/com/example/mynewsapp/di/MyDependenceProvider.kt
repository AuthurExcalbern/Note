package com.example.mynewsapp.di

import androidx.room.Room
import com.example.data_local.NewsDatabase
import com.example.data_local.repository.NewsLocalRepositoryImpl
import com.example.data_remote.api.NewsApiService
import com.example.data_remote.repository.NewsRemoteRepositoryImpl
import com.example.domain.usecases.*
import com.example.mynewsapp.BaseApplication.Companion.context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object MyDependenceProvider {

    // init local repository

    private val room = Room
        .databaseBuilder(context, NewsDatabase::class.java, "news_db")
        .build()

    private val newsDao = room.newsDao()

    private val newsLocalRepository = NewsLocalRepositoryImpl(newsDao)


    // init remote repository

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private const val url = "https://api.jisuapi.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val newsApiService = retrofit.create(NewsApiService::class.java)

    private val newsRemoteRepository = NewsRemoteRepositoryImpl(newsApiService)

    // init use case

    val getAllNewsUseCase: GetAllNewsBaseUseCase = GetAllNewsUseCase(newsLocalRepository)
    val deleteAllNewsUseCase: DeleteAllNewsBaseUseCase = DeleteAllNewsUseCase(newsLocalRepository)
    val insertNewsUseCase: InsertNewsBaseUseCase = InsertNewsUseCase(newsLocalRepository)
    val getRemoteNewsUseCase: GetRemoteNewsBaseUseCase = GetRemoteNewsUseCase(newsRemoteRepository)

    fun onDestroy() {
        room.close()
    }
}