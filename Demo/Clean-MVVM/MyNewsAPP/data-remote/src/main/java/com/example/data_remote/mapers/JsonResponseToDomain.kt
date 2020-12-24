package com.example.data_remote.mapers

import com.example.data_remote.models.NewsItemResponse
import com.example.domain.models.News

internal fun NewsItemResponse.toDomain(): News =
    News(this.title, this.time, this.pic, this.content)