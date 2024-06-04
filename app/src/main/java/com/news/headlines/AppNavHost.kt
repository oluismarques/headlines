package com.news.headlines

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.news.details.detailScreenGraph
import com.news.details.navigateToDetail
import com.news.domain.headlines.TopHeadlineItem
import com.news.headlines.mainscreen.ROUTE_MAIN_SCREEN
import com.news.headlines.mainscreen.mainScreenGraph

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ROUTE_MAIN_SCREEN
    ) {
        mainScreenGraph(navigateToDetail = { navigateToDetail(navController, it) })

        detailScreenGraph(navController)
    }
}


private fun navigateToDetail(
    navController: NavHostController,
    topHeadlineItem: TopHeadlineItem,
) {
    navController.navigateToDetail(
        topHeadlineItem = topHeadlineItem,
        builder = {},
    )
}
