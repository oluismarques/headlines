package com.news.headlines.mainscreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ROUTE_MAIN_SCREEN = "ROUTE_MAIN_SCREEN"

fun NavGraphBuilder.mainScreenGraph(
    navigateToDetail: (Int) -> Unit,
) {
    composable(ROUTE_MAIN_SCREEN) {
        MainScreen(navigateToDetail = navigateToDetail)
    }
}
