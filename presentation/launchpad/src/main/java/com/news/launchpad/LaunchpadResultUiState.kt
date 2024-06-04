package com.news.launchpad

import android.os.Parcelable
import com.news.domain.headlines.TopHeadlineItem
import kotlinx.parcelize.Parcelize

@Parcelize
internal sealed interface LaunchpadResultUiState : Parcelable {
    @Parcelize
    data class Success(val topHeadlineItems: List<TopHeadlineItem>) : LaunchpadResultUiState

    @Parcelize
    data object Error : LaunchpadResultUiState

    @Parcelize
    data object Loading : LaunchpadResultUiState

    @Parcelize
    data object Empty : LaunchpadResultUiState
}
