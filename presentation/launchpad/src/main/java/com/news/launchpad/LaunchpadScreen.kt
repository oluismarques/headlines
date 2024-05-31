package com.news.launchpad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.news.designsystem.components.DSNewsCard
import com.news.designsystem.components.DSTopBar
import com.news.designsystem.components.ThemePreviews
import com.news.designsystem.theme.Dimen12
import com.news.designsystem.theme.Dimen8
import com.news.domain.headlines.Source
import com.news.domain.headlines.TopHeadline
import com.news.feature.launchpad.R


@Composable
internal fun LaunchPadScreen(
    launchpadResultUiState: LaunchpadResultUiState,
    navigateToDetail: (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            item {
                Spacer(
                    Modifier.windowInsetsTopHeight(
                        WindowInsets.statusBars.add(WindowInsets(top = 40.dp))
                    )
                )
            }

            item {
                NewsCollection(
                    uiState = launchpadResultUiState,
                    title = stringResource(id = R.string.top_headlines),
                    navigateToDetail = navigateToDetail
                )

            }
        }

        DSTopBar(
            modifier = Modifier.padding(horizontal = Dimen12),
            onSearchClick = { },
            title = stringResource(R.string.top_headlines_top_bar_title),
        )
    }
}

@Composable
private fun NewsCollection(
    uiState: LaunchpadResultUiState,
    title: String,
    navigateToDetail: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimen8, top = Dimen8, bottom = Dimen8),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = title)

        when (uiState) {
            LaunchpadResultUiState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.testTag("empty_tag"),
                        text = stringResource(R.string.top_headlines_empty_results)
                    )
                }
            }

            LaunchpadResultUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.testTag("error_tag"),
                        text = stringResource(R.string.top_headlines_error_message)
                    )
                }
            }

            LaunchpadResultUiState.Loading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("loading_tag"))
            }

            is LaunchpadResultUiState.Success -> {
                TopHeadlineList(modifier = Modifier.fillMaxWidth(),
                    topHeadlines = uiState.topHeadlines,
                    onNewsClick = {
                        it.source?.let {  navigateToDetail.invoke(it.id) }
                    })
            }
        }
    }
}

@Composable
private fun TopHeadlineList(
    topHeadlines: List<TopHeadline>,
    onNewsClick: (TopHeadline) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
    ) {
        itemsIndexed(topHeadlines) { index, item ->
            DSNewsCard(
                modifier = Modifier
                    .padding(end = Dimen8)
                    .testTag("movie_card_tag"),
                name = item.source.name,
                onCardClick = { onNewsClick.invoke(item) },
                imageUrl = item.urlToImage,
                releaseDate = item.publishedAt,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun LaunchPadScreenPreview() {
    LaunchPadScreen(
        launchpadResultUiState = LaunchpadResultUiState.Success(
            topHeadlines = listOf(
                TopHeadline(
                    description = "risus",
                    url = "https://search.yahoo.com/search?p=invidunt",
                    source = Source(id = "finibus", name = "Colette Howell"),
                    author = "jdnfdnif",
                    urlToImage = "sdkdskmd",
                    publishedAt = "12/ao/20202"
                )
            )
        ),
        navigateToDetail = {}
    )
}
