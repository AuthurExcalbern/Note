package com.example.data_local.repository

import com.example.data_local.dao.NewsDao
import com.example.data_local.mappers.toDomain
import com.example.domain.models.News
import com.example.domain.repository.NewsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsLocalRepositoryImpl(private val newsDao: NewsDao): NewsLocalRepository {
    override fun getAllNews(): Flow<List<News>> = flow {
        val news = newsDao.getALL()
        emit(news.map { it.toDomain() })
    }

    override fun deleteAllNews(): Flow<Int> = flow {
        val rowsAffected = newsDao.deleteAll()
        emit(rowsAffected)
    }

    override fun insertNews(news: News): Flow<Long> = flow {
        val result = newsDao.insert(news)
        emit(result)
    }
}