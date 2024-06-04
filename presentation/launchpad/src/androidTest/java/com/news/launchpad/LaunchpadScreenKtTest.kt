package com.news.launchpad

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.news.domain.headlines.SourceItem
import com.news.domain.headlines.TopHeadlineItem
import com.news.domain.headlines.mockSources
import com.news.feature.launchpad.R
import com.news.util.test.assertAreDisplayed
import com.news.util.test.assertExists
import com.news.util.test.assertHasClickAction
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LaunchpadScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var errorMessage: String
    private lateinit var emptyMessage: String

    private val mockHeadlines = listOf(
        TopHeadlineItem(
            description = "dolores",
            url = "https://search.yahoo.com/search?p=penatibus",
            source = SourceItem(id = "arcu", name = "Darrel Rose"),
            author = null,
            urlToImage = null,
            publishedAt = "inciderint",
            title = "conclusionemque",
            content = null
        )
    )
    private val pagingData = MutableStateFlow(
        PagingData.from(
            mockHeadlines
        )
    )

    private val emptyPagingData = MutableStateFlow(
        PagingData.empty<TopHeadlineItem>()
    )

    @Before
    fun setup() {
        composeTestRule.activity.apply {
            emptyMessage = getString(R.string.top_headlines_empty_results)
        }
    }

    @Test
    fun topheadlines_whenLaunchesIsSuccess_isShown() {
        composeTestRule.setContent {
            LaunchPadScreen(
                sources = mockSources,
                onSelectedSource = {},
                navigateToDetail = {},
                pagingState = pagingData.collectAsLazyPagingItems()
            )
        }

        with(composeTestRule) {
            onAllNodesWithText(mockHeadlines.first().title).assertAreDisplayed()
            onAllNodesWithText(
                mockHeadlines.first().publishedAt
            ).assertAreDisplayed()
            onAllNodesWithTag("news_card_tag").assertHasClickAction()
        }
    }

    @Test
    fun loading_indicator_whenScreenIsLoading_showLoading() {
        composeTestRule.setContent {
            LaunchPadScreen(
                sources = mockSources,
                onSelectedSource = {},
                navigateToDetail = {},
                pagingState = pagingData.collectAsLazyPagingItems()
            )
        }

        composeTestRule.onAllNodesWithTag("loading_tag").assertExists()
    }

    @Test
    fun empty_whenScreenIsEmpty_isShow() {
            composeTestRule.setContent {
                LaunchPadScreen(
                    sources = mockSources,
                    onSelectedSource = {},
                    navigateToDetail = {},
                    pagingState = emptyPagingData.collectAsLazyPagingItems()
                )
            }

        composeTestRule.onAllNodesWithText(emptyMessage).assertExists()
        composeTestRule.onAllNodesWithTag("empty_tag").assertExists()
    }
}



