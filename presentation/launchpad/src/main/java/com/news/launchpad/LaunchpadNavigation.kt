package com.news.launchpad

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.news.domain.headlines.TopHeadlineItem

const val ROUTE_LAUNCHPAD = "ROUTE_LAUNCHPAD"

fun NavGraphBuilder.launchpadScreenGraph(
    navigateToDetail: (TopHeadlineItem) -> Unit,
) {
    composable(route = ROUTE_LAUNCHPAD) {
        val viewModel: LaunchpadViewModel = hiltViewModel()

        val sources by viewModel.sources.collectAsState()
        val pagingState = viewModel.pagingState.collectAsLazyPagingItems()

        LaunchPadScreen(
            sources = sources,
            navigateToDetail = navigateToDetail,
            onSelectedSource = {
                viewModel.getTopHeadlines(it.id)
            },
            pagingState = pagingState
        )
    }
}
