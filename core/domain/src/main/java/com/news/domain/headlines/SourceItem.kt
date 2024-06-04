package com.news.domain.headlines

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceItem(
    val id: String,
    val name: String,
) : Parcelable
