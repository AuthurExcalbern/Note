package com.example.domain.usecases

import com.example.domain.models.News
import com.example.domain.models.RequestParameter
import com.example.domain.repository.NewsRemoteRepository
import kotlinx.coroutines.flow.Flow

typealias GetRemoteNewsBaseUseCase = BaseUseCase<RequestParameter, Flow<List<News>>>

class GetRemoteNewsUseCase(
    private val newsRemoteRepository: NewsRemoteRepository
) : GetRemoteNewsBaseUseCase {
    override suspend fun invoke(params: RequestParameter): Flow<List<News>> = newsRemoteRepository.getNews(params)
}