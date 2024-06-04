package com.news.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.news.designsystem.components.DSTopBar
import com.news.designsystem.theme.NewsTypography
import com.news.feature.details.R

@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    title: String?,
    publishedAt: String?,
    description: String?,
    author: String?,
    imageUrl: String?,
    content: String?,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DSTopBar(
            title = stringResource(R.string.details_top_bar_title),
            onBackClick = onBackClick,
            showNavigationIcon = true,
            showActionIcon = false
        )

        Text(
            modifier = Modifier.testTag("title_tag"),
            text = title.orEmpty(),
            style = NewsTypography.titleMedium,
        )

        Text(
            text = publishedAt.orEmpty(),
            style = NewsTypography.titleSmall,
        )

        Text(
            text = author.orEmpty().ifEmpty { stringResource(R.string.details_no_author_found) },
            style = NewsTypography.titleSmall,
        )

        Text(
            text = description.orEmpty(),
            style = NewsTypography.titleSmall,
        )

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = imageUrl)
                .build(),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = content.orEmpty(),
            style = NewsTypography.labelSmall,
        )
    }
}


@Preview
@Composable
fun DetailsScreenPreview() {
    DetailScreen(
        onBackClick = {},
        title = null,
        description = null,
        imageUrl = null,
        content = null,
        publishedAt = null,
        author = null,
    )
}