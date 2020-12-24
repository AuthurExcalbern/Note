package com.example.domain.repository

import com.example.domain.models.News
import com.example.domain.models.RequestParameter
import kotlinx.coroutines.flow.Flow

interface NewsRemoteRepository {
    suspend fun getNews(requestParameter: RequestParameter): Flow<List<News>>
}