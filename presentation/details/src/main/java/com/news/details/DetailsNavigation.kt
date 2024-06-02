package com.news.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.news.domain.headlines.TopHeadlineItem
import com.news.util.decodeFromUrl
import com.news.util.encodeForUrl
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private const val ARG_TITLE = "ARG_TITLE"
private const val ARG_CONTENT = "ARG_CONTENT"
private const val ARG_DESCRIPTION = "ARG_DESCRIPTION"
private const val ARG_PICTURE_URL = "ARG_PICTURE_URL"
private const val ARG_PUBLISH_DATE = "ARG_PUBLISH_DATE"
private const val ARG_AUTHOR = "ARG_AUTHOR"

const val ROUTE_DETAIL_SCREEN =
    "ROUTE_DETAIL_SCREEN/{$ARG_TITLE}/?{$ARG_DESCRIPTION}/{$ARG_CONTENT}/?{$ARG_PICTURE_URL}/?{$ARG_PUBLISH_DATE}/?{$ARG_AUTHOR}"

internal data class DetailScreenArgs(
    val title: String,
    val content: String,
    val imageUrl: String,
    val description: String,
    val publishDate: String,
    val author: String,
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        title = checkNotNull(savedStateHandle[ARG_TITLE]),
        description = checkNotNull(savedStateHandle[ARG_DESCRIPTION]),
        content = checkNotNull(savedStateHandle[ARG_CONTENT]),
        imageUrl = checkNotNull(savedStateHandle[ARG_PICTURE_URL]),
        publishDate = checkNotNull(savedStateHandle[ARG_PUBLISH_DATE]),
        author = checkNotNull(savedStateHandle[ARG_AUTHOR]),
    )
}


fun NavController.navigateToDetail(
    topHeadlineItem: TopHeadlineItem,
    builder: NavOptionsBuilder.() -> Unit,
) {
    val route = ROUTE_DETAIL_SCREEN
        .replace("{$ARG_TITLE}", topHeadlineItem.title)
        .replace("{$ARG_DESCRIPTION}", topHeadlineItem.description)
        .replace("{$ARG_CONTENT}", topHeadlineItem.content.orEmpty().encodeForUrl())
        .replace("{$ARG_PICTURE_URL}", topHeadlineItem.urlToImage.orEmpty())
        .replace("{$ARG_PUBLISH_DATE}", topHeadlineItem.publishedAt)
        .replace("{$ARG_AUTHOR}", topHeadlineItem.author.orEmpty())
    navigate(route, builder)
}


fun NavGraphBuilder.detailScreenGraph(
    navController: NavController,
) {
    composable(
        route = ROUTE_DETAIL_SCREEN,
        arguments = listOf(
            navArgument(ARG_TITLE) { type = NavType.StringType },
            navArgument(ARG_DESCRIPTION) { type = NavType.StringType },
            navArgument(ARG_CONTENT) { type = NavType.StringType },
            navArgument(ARG_PICTURE_URL) { type = NavType.StringType },
            navArgument(ARG_PUBLISH_DATE) { type = NavType.StringType },
            navArgument(ARG_AUTHOR) { type = NavType.StringType },
        ),
    ) {

        val arguments = it.arguments

        val detailScreenArgs = DetailScreenArgs(
            title = arguments?.getString(ARG_TITLE).orEmpty(),
            description = arguments?.getString(ARG_DESCRIPTION).orEmpty(),
            content = arguments?.getString(ARG_CONTENT).orEmpty().decodeFromUrl(),
            imageUrl = arguments?.getString(ARG_PICTURE_URL).orEmpty(),
            publishDate = arguments?.getString(ARG_PUBLISH_DATE).orEmpty(),
            author = arguments?.getString(ARG_AUTHOR).orEmpty()
        )

        DetailScreen(
            onBackClick = navController::popBackStack,
            title = detailScreenArgs.title,
            description = detailScreenArgs.description,
            content = detailScreenArgs.content,
            imageUrl = detailScreenArgs.imageUrl,
            publishedAt = detailScreenArgs.publishDate,
            author = detailScreenArgs.author,
        )
    }
}
