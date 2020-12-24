package com.example.data_remote.repository

import com.example.data_remote.BaseTest
import com.example.domain.models.RequestParameter
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class NewsRemoteRepositoryImplTest : BaseTest() {

    private lateinit var newsRemoteRepositoryImpl: NewsRemoteRepositoryImpl

    @Before
    override fun setup() {
        super.setup()
        newsRemoteRepositoryImpl = NewsRemoteRepositoryImpl(newsApiService)
    }

    @Test
    fun `given exist parameters when executed then return news list`() {
        runBlocking {
            val result = newsRemoteRepositoryImpl.getNews(RequestParameter("头条", 1, 0))
            result.collect {
                Truth.assertThat(it).isNotEmpty()
            }
        }
    }

    @Test
    fun `given non-exist parameters when executed then return no results`() {
        runBlocking {
            val result = newsRemoteRepositoryImpl.getNews(RequestParameter("channel_error", 1, 0))
            result.collect {
                Truth.assertThat(it).isEmpty()
            }
        }
    }

    @Test
    fun `given whatever when connect error then return no results`() {
        runBlocking {
            val result = newsRemoteRepositoryImpl.getNews(RequestParameter("connect_error", 1, 0))
            result.collect {
                Truth.assertThat(it).isEmpty()
            }
        }
    }
}