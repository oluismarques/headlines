package com.news.headlines.mainscreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.news.launchpad.ROUTE_LAUNCHPAD
import com.news.launchpad.launchpadScreenGraph


@Composable
fun MainScreenNavHost(
    navController: NavHostController,
    navigateToDetail: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_LAUNCHPAD
    ) {
        launchpadScreenGraph(
            navigateToDetail = navigateToDetail
        )

    }
}
