package com.example.data_remote.models

data class NewsResponse (
    val status: Int,
    val msg: String,
    val result: NewsResultResponse?
)