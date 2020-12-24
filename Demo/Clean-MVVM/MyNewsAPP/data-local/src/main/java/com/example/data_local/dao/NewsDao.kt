package com.example.data_local.dao

import androidx.room.*
import com.example.data_local.mappers.toEntity
import com.example.data_local.models.NewsEntity
import com.example.domain.models.News

@Dao
interface NewsDao {
    @Query("DELETE FROM news")
    suspend fun deleteAll(): Int

    @Transaction
    @Query("SELECT * FROM news")
    suspend fun getALL(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsEntity: NewsEntity): Long

    @Transaction
    suspend fun insert(news: News) : Long = insert(news.toEntity())

}