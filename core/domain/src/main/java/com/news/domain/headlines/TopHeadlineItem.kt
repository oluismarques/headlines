package com.news.domain.headlines

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopHeadlineItem(
    val description: String,
    val url: String,
    val source: Source,
    val author: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val title: String,
    val content: String?,
) : Parcelable

@Parcelize
data class Source(
    val id: String?,
    val name: String,
) : Parcelable