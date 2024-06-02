package com.news.headlines.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.news.designsystem.extensions.NoRippleInteractionSource
import com.news.designsystem.extensions.NoRippleTheme
import com.news.launchpad.ROUTE_LAUNCHPAD
import com.news.designsystem.theme.DSGray10
import com.news.designsystem.theme.Dimen1
import com.news.designsystem.theme.Dimen56
import com.news.domain.headlines.TopHeadlineItem

data class BottomNavItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
)

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    navigateToDetail: (TopHeadlineItem) -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MainScreenNavHost(
                navController = navController,
                navigateToDetail = navigateToDetail
            )
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = ROUTE_LAUNCHPAD,
        ),
        BottomNavItem(
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            route = "ROUTE_SEARCH",
        ),
        BottomNavItem(
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = "ROUTE_SETTINGS",
        ),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Column(
            modifier = Modifier.navigationBarsPadding()
        ) {
            HorizontalDivider(thickness = Dimen1, color = DSGray10)

            NavigationBar(
                modifier = Modifier.height(Dimen56)
            ) {
                items.forEach { bottomNavItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == bottomNavItem.route
                        } == true,
                        onClick = {
                            navController.navigate(bottomNavItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentDestination?.hierarchy?.any {
                                        it.route == bottomNavItem.route
                                    } == true) {
                                    bottomNavItem.selectedIcon
                                } else {
                                    bottomNavItem.unselectedIcon
                                },
                                contentDescription = bottomNavItem.selectedIcon.toString(),
                            )
                        },
                        interactionSource = NoRippleInteractionSource(),
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun MainScreenPreview() {
    MainScreen(navigateToDetail = {})
}
