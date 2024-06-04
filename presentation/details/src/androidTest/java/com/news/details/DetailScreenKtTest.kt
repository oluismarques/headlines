package com.news.details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.news.feature.details.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var noAuthorFounded: String

    @Before
    fun setup() {
        composeTestRule.activity.apply {
            noAuthorFounded = getString(R.string.details_no_author_found)
        }
    }


    @Test
    fun detailItem_whenTrendingIsSuccess_isShown() {
        composeTestRule.setContent {
            DetailScreen(
                onBackClick = {},
                title = "title",
                publishedAt = "ddsknfkdsnf",
                description = "dslfldsf",
                author = null,
                imageUrl = null,
                content = null
            )
        }

        with(composeTestRule) {
            onNodeWithText("title").assertIsDisplayed()
            onNodeWithText(noAuthorFounded).assertIsDisplayed()

            onNodeWithTag("title_tag").assertExists()
        }
    }

}
