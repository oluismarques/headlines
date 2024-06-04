package com.news.launchpad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.news.designsystem.components.DSNewsCard
import com.news.designsystem.components.DSTopBar
import com.news.designsystem.components.ThemePreviews
import com.news.designsystem.theme.DSBlue
import com.news.designsystem.theme.Dimen12
import com.news.designsystem.theme.Dimen8
import com.news.domain.headlines.SourceItem
import com.news.domain.headlines.TopHeadlineItem
import com.news.feature.launchpad.R
import com.news.util.FlavorChecker
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
internal fun LaunchPadScreen(
    sources: List<SourceItem>,
    navigateToDetail: (TopHeadlineItem) -> Unit,
    onSelectedSource: (SourceItem) -> Unit,
    pagingState: LazyPagingItems<TopHeadlineItem>,
) {
    var selectedItem by rememberSaveable { mutableStateOf<SourceItem?>(null) }

    Column {
        DSTopBar(
            modifier = Modifier.padding(horizontal = Dimen12),
            onSearchClick = { },
            title = stringResource(R.string.top_headlines_top_bar_title),
        )

        if (FlavorChecker.isFullFlavor()) {
            Spinner(sources = sources, onSelectedSource = {
                onSelectedSource(it)
                selectedItem = it
            }, title = selectedItem?.name ?: "BBC News")
        }

        NewsCollection(
            navigateToDetail = navigateToDetail,
            source = selectedItem?.name ?: "BBC News",
            pagingState = pagingState
        )
    }
}

@Composable
private fun Spinner(
    title: String,
    sources: List<SourceItem>,
    onSelectedSource: (SourceItem) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(Dimen8)
            .background(DSBlue)
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(modifier = Modifier.fillMaxWidth(), onClick = { expanded = !expanded }) {
                Row {
                    Text(text = title)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "More"
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                sources.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onSelectedSource(item)
                        },
                        text = {
                            Text(text = item.name)
                        })
                }
            }
        }
    }
}

@Composable
private fun NewsCollection(
    navigateToDetail: (TopHeadlineItem) -> Unit,
    source: String,
    pagingState: LazyPagingItems<TopHeadlineItem>,
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimen8, top = Dimen8, bottom = Dimen8),
        verticalArrangement = Arrangement.Top
    ) {
        when {
            pagingState.itemCount == 0 || pagingState.loadState.refresh is LoadState.Error -> {
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

            pagingState.loadState.refresh is LoadState.Loading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("loading_tag"))
                LaunchedEffect(true) { listState.scrollToItem(0) }

            }

            pagingState.loadState.append is LoadState.Loading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("loading_tag"))
            }

            else -> {
                TopHeadlineList(
                    onNewsClick = {
                        navigateToDetail.invoke(it)
                    },
                    modifier = Modifier.wrapContentHeight(),
                    source = source,
                    pagingState = pagingState
                )
            }
        }
    }
}

@Composable
private fun TopHeadlineList(
    onNewsClick: (TopHeadlineItem) -> Unit,
    modifier: Modifier = Modifier,
    source: String,
    pagingState: LazyPagingItems<TopHeadlineItem>,
) {
    Column {
        Text(text = stringResource(id = R.string.top_headlines, source))

        LazyColumn(
            modifier = modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(Dimen8),
        ) {

            items(pagingState.itemCount) { index ->
                val item = pagingState[index]

                DSNewsCard(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(end = Dimen8)
                        .testTag("news_card_tag"),
                    title = item?.title.orEmpty(),
                    onCardClick = {
                        if (item != null) {
                            onNewsClick.invoke(item)
                        }
                    },
                    imageUrl = item?.urlToImage,
                    source = item?.source?.name.orEmpty(),
                    releaseDate = item?.publishedAt.orEmpty()
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun LaunchPadScreenPreview() {
    val pagingData = MutableStateFlow(PagingData.empty<TopHeadlineItem>())

    LaunchPadScreen(
        sources = listOf(),
        navigateToDetail = {},
        onSelectedSource = {},
        pagingState = pagingData.collectAsLazyPagingItems()
    )
}
