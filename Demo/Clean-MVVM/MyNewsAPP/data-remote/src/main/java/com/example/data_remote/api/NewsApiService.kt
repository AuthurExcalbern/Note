package com.example.data_remote.api

import com.example.data_remote.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("news/get")
    suspend fun getNews(
        @Query("channel") channel: String,
        @Query("start") start: Int,
        @Query("num") num: Int,
        @Query("appkey") appkey: String
    ) : NewsResponse
}