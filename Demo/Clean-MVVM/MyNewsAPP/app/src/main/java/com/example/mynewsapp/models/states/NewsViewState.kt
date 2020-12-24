package com.example.mynewsapp.models.states

import com.example.mynewsapp.models.NewsPresentation

internal data class NewsViewState (
    val isLoading: Boolean,
    val error: Error?,
    val newsList: List<NewsPresentation>
)