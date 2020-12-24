package com.example.data_remote.models

data class NewsResultResponse (
        val channel: String,
        val num: Int,
        val list: List<NewsItemResponse>
)