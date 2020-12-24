package com.example.data_local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_local.dao.NewsDao
import com.example.data_local.models.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}