package com.example.data_remote.repository

import android.net.Uri.encode
import android.util.Log
import com.example.data_remote.api.NewsApiService
import com.example.data_remote.mapers.toDomain
import com.example.domain.models.News
import com.example.domain.models.RequestParameter
import com.example.domain.repository.NewsRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.net.URLDecoder
import java.net.URLEncoder

class NewsRemoteRepositoryImpl(
    private val apiService: NewsApiService
) : NewsRemoteRepository {
    override suspend fun getNews(requestParameter: RequestParameter): Flow<List<News>> = flow {
        val result = mutableListOf<News>()

        try {
            val newsResponse = apiService.getNews(
                requestParameter.channel,
                requestParameter.start,
                requestParameter.num,
                "b0e49ab88a0537fb")

            if (newsResponse.status == 0 && newsResponse.result != null) {
                for (newsItem in newsResponse.result.list) {
                    result.add(newsItem.toDomain())
                }
            }
        } catch (e: Exception) {
        }

        emit(result)
    }

    companion object {
        const val TAG = "RemoteRepo"
    }
}