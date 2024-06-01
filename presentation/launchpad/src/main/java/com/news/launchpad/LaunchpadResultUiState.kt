package com.news.launchpad

import android.os.Parcelable
import com.news.domain.headlines.TopHeadline
import kotlinx.parcelize.Parcelize

@Parcelize
internal sealed interface LaunchpadResultUiState : Parcelable {
    @Parcelize
    data class Success(val topHeadlines: List<TopHeadline>) : LaunchpadResultUiState

    @Parcelize
    data object Error : LaunchpadResultUiState

    @Parcelize
    data object Loading : LaunchpadResultUiState

    @Parcelize
    data object Empty : LaunchpadResultUiState
}
