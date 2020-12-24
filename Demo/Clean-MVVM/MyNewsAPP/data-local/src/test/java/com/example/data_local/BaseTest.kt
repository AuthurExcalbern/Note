package com.example.data_local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data_local.dao.NewsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import java.io.IOException

internal open class BaseTest {
    private lateinit var db: NewsDatabase
    protected lateinit var newsDao: NewsDao

    @Before
    open fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        newsDao = db.newsDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        runBlocking(Dispatchers.IO) {
            db.clearAllTables()
        }
        db.close()
    }
}