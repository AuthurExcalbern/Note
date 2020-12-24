package com.example.mynewsapp.mappers

import com.example.domain.models.News
import com.example.mynewsapp.models.NewsPresentation

internal fun NewsPresentation.toDomain() =
    News(title, time, pic, content)