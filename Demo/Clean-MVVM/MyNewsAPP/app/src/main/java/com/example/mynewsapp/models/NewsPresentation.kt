package com.example.mynewsapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class NewsPresentation (
    val title: String,
    val time: String,
    val pic: String,
    val content: String
) : Parcelable