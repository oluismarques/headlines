package com.news.launchpad

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.news.domain.headlines.TopHeadlineItem

const val ROUTE_LAUNCHPAD = "ROUTE_LAUNCHPAD"

fun NavGraphBuilder.launchpadScreenGraph(
    navigateToDetail: (TopHeadlineItem) -> Unit,
) {
    composable(route = ROUTE_LAUNCHPAD) {
        val viewModel: LaunchpadViewModel = hiltViewModel()
        val launchpadResultUiState by viewModel.launchpadUiState.collectAsState()

        LaunchPadScreen(
            launchpadResultUiState = launchpadResultUiState,
            navigateToDetail = navigateToDetail,
            onSorterDate = viewModel::sorterDate,
        )
    }
}
