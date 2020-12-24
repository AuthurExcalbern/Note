package com.example.domain.usecases

import com.example.domain.repository.NewsLocalRepository
import kotlinx.coroutines.flow.Flow

typealias DeleteAllNewsBaseUseCase = BaseUseCase<Unit, Flow<Int>>

class DeleteAllNewsUseCase(
    private val newsLocalRepository: NewsLocalRepository
) : DeleteAllNewsBaseUseCase {
    override suspend fun invoke(params: Unit): Flow<Int> = newsLocalRepository.deleteAllNews()
}