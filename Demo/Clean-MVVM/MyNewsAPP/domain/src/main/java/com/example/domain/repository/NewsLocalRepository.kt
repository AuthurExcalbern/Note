package com.example.domain.repository

import com.example.domain.models.News
import kotlinx.coroutines.flow.Flow

interface NewsLocalRepository {

    fun getAllNews(): Flow<List<News>>

    fun deleteAllNews(): Flow<Int>

    fun insertNews(news: News): Flow<Long>
}