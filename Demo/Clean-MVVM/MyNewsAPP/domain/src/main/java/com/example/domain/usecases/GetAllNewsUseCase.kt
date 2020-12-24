package com.example.domain.usecases

import com.example.domain.models.News
import com.example.domain.repository.NewsLocalRepository
import kotlinx.coroutines.flow.Flow

typealias GetAllNewsBaseUseCase = BaseUseCase<Unit, Flow<List<News>>>

class GetAllNewsUseCase(
    private val newsLocalRepository: NewsLocalRepository
) : GetAllNewsBaseUseCase {
    override suspend fun invoke(params: Unit): Flow<List<News>> = newsLocalRepository.getAllNews()
}