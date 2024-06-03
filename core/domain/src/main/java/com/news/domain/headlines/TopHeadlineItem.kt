package com.news.domain.headlines

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopHeadlineItem(
    val description: String,
    val url: String,
    val source: SourceItem,
    val author: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val title: String,
    val content: String?,
) : Parcelable