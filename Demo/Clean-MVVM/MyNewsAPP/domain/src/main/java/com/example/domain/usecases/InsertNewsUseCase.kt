package com.example.domain.usecases

import com.example.domain.models.News
import com.example.domain.repository.NewsLocalRepository
import kotlinx.coroutines.flow.Flow

typealias InsertNewsBaseUseCase = BaseUseCase<News, Flow<Long>>

class InsertNewsUseCase(
    private val newsLocalRepository: NewsLocalRepository
) : InsertNewsBaseUseCase {
    override suspend fun invoke(params: News): Flow<Long> = newsLocalRepository.insertNews(params)
}