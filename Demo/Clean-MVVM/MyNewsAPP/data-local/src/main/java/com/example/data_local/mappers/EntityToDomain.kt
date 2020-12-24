package com.example.data_local.mappers

import com.example.data_local.models.NewsEntity
import com.example.domain.models.News

internal fun NewsEntity.toDomain() =
    News(
        title = title,
        time = time,
        pic = pic,
        content = content
    )