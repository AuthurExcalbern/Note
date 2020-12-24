package com.example.data_local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data_local.repository.NewsLocalRepositoryImpl
import com.example.domain.repository.NewsLocalRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class NewsRepositoryImplTest : BaseTest() {

    private lateinit var newsLocalRepository: NewsLocalRepository

    @Before
    override fun setup() {
        super.setup()
        newsLocalRepository = NewsLocalRepositoryImpl(newsDao = newsDao)
    }

    @Test
    fun `when all news are requested , then return all saved news`() =
        runBlocking(Dispatchers.IO) {

            // Given: we save a news item to the db
            newsLocalRepository.insertNews(SampleData.news).collect()

            // When: we get all news items
            val news = newsLocalRepository.getAllNews()

            // Then: get saved news items
            news.collect {
                Truth.assertThat(it).isEqualTo(listOf(SampleData.news))
            }
        }

    @Test
    fun `when we delete all news , then return an empty list of news`() =
        runBlocking(Dispatchers.IO) {

            // Given: we save multiple news to the database
            newsLocalRepository.insertNews(SampleData.news).collect()
            newsLocalRepository.insertNews(SampleData.news).collect()
            newsLocalRepository.insertNews(SampleData.news).collect()
            newsLocalRepository.insertNews(SampleData.news).collect()

            // When: we delete all news from db
            newsLocalRepository.deleteAllNews().collect()

            // Then: assert all news were deleted
            newsLocalRepository.getAllNews().collect {
                Truth.assertThat(it).isEmpty()
            }
        }

}