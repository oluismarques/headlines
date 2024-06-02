package com.news.launchpad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.news.designsystem.components.DSNewsCard
import com.news.designsystem.components.DSTopBar
import com.news.designsystem.components.ThemePreviews
import com.news.designsystem.theme.DSBlue
import com.news.designsystem.theme.DSGray50
import com.news.designsystem.theme.Dimen12
import com.news.designsystem.theme.Dimen8
import com.news.domain.headlines.Source
import com.news.domain.headlines.TopHeadlineItem
import com.news.feature.launchpad.R


@Composable
internal fun LaunchPadScreen(
    launchpadResultUiState: LaunchpadResultUiState,
    onSorterDate: (SorterDate) -> Unit,
    navigateToDetail: (TopHeadlineItem) -> Unit,
) {
    Column {
        DSTopBar(
            modifier = Modifier.padding(horizontal = Dimen12),
            onSearchClick = { },
            title = stringResource(R.string.top_headlines_top_bar_title),
        )

        NewsCollection(
            uiState = launchpadResultUiState,
            navigateToDetail = navigateToDetail,
            onSorterDate = onSorterDate
        )
    }
}

@Composable
private fun NewsCollection(
    uiState: LaunchpadResultUiState,
    navigateToDetail: (TopHeadlineItem) -> Unit,
    onSorterDate: (SorterDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimen8, top = Dimen8, bottom = Dimen8),
        verticalArrangement = Arrangement.Top
    ) {


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
                TopHeadlineList(
                    modifier = Modifier.wrapContentHeight(),
                    topHeadlineItems = uiState.topHeadlineItems,
                    onSorterDate = onSorterDate,
                    onNewsClick = {
                        navigateToDetail.invoke(it) 
                    })
            }
        }
    }
}

@Composable
private fun TopHeadlineList(
    topHeadlineItems: List<TopHeadlineItem>,
    onNewsClick: (TopHeadlineItem) -> Unit,
    onSorterDate: (SorterDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(text = stringResource(id = R.string.top_headlines))

        SorterRow(
            onSorterDate = onSorterDate,
        )

        LazyColumn(
            modifier = modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(Dimen8),
        ) {

            itemsIndexed(topHeadlineItems) { index, item ->
                DSNewsCard(
                    modifier = Modifier
                        .height(80.dp)
                        .padding(end = Dimen8)
                        .testTag("movie_card_tag"),
                    name = item.source.name,
                    onCardClick = { onNewsClick.invoke(item) },
                    imageUrl = item.urlToImage,
                    releaseDate = item.publishedAt
                )
            }
        }
    }
}

@Composable
fun SorterRow(
    onSorterDate: (SorterDate) -> Unit,
) {
    // State to keep track of the current sort order
    var currentSortOrder by remember { mutableStateOf(SorterDate.ASC) }

    val selectedColor: Color = DSBlue
    val notSelectedColor: Color = DSGray50

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        TextButton(
            onClick = {
                currentSortOrder = SorterDate.ASC
                onSorterDate(currentSortOrder)
            },
            colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                contentColor = if (currentSortOrder == SorterDate.ASC) selectedColor else notSelectedColor
            )
        ) {
            Text(text = stringResource(R.string.top_headlines_asc_date))
        }
        TextButton(
            onClick = {
                currentSortOrder = SorterDate.DESC
                onSorterDate(currentSortOrder)
            },
            colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                contentColor = if (currentSortOrder == SorterDate.DESC) selectedColor else notSelectedColor
            )
        ) {
            Text(text = stringResource(R.string.top_headlines_desc_date))
        }
    }
}

@ThemePreviews
@Composable
private fun LaunchPadScreenPreview() {
    LaunchPadScreen(
        launchpadResultUiState = LaunchpadResultUiState.Success(
            topHeadlineItems = listOf(
                TopHeadlineItem(
                    description = "risus",
                    url = "https://search.yahoo.com/search?p=invidunt",
                    source = Source(id = "finibus", name = "Colette Howell"),
                    author = "jdnfdnif",
                    urlToImage = "sdkdskmd",
                    publishedAt = "12/ao/20202", title = "iuvaret", content = null,
                )
            )
        ),
        onSorterDate = {},
        navigateToDetail = {}
    )
}
