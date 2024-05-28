package com.news.launchpad

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ROUTE_LAUNCHPAD = "ROUTE_LAUNCHPAD"

fun NavGraphBuilder.launchpadScreenGraph(
    navigateToDetail: (Int) -> Unit
) {
    composable(route = ROUTE_LAUNCHPAD) {

    }
}
