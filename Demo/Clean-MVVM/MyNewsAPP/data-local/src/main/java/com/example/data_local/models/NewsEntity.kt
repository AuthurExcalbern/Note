package com.example.data_local.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val title: String,
    val pic : String,
    val time : String,
    val content : String
)