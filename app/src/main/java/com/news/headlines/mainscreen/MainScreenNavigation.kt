package com.news.headlines.mainscreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.news.domain.headlines.TopHeadlineItem

const val ROUTE_MAIN_SCREEN = "ROUTE_MAIN_SCREEN"

fun NavGraphBuilder.mainScreenGraph(
    navigateToDetail: (TopHeadlineItem) -> Unit,
) {
    composable(ROUTE_MAIN_SCREEN) {
        MainScreen(navigateToDetail = navigateToDetail)
    }
}
