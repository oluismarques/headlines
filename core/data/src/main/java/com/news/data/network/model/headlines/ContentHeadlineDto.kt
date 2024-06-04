package com.news.data.network.model.headlines


import com.news.domain.headlines.SourceItem
import com.news.domain.headlines.TopHeadlineItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Serializable
internal data class ContentHeadlineResponse(
    @SerialName("author")
    val author: String?,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
    @SerialName("publishedAt")
    val publishedAt: String,
    @SerialName("urlToImage")
    val urlToImage: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("source")
    val sourceHeadlineResponse: SourceHeadlineResponse,
    @SerialName("content")
    val content: String?,
)

@Serializable
internal data class SourceHeadlineResponse(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String,
)

internal fun ContentHeadlineResponse.asDomainModel(): TopHeadlineItem =
    TopHeadlineItem(
        author = author,
        description = description.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage,
        publishedAt = formatDate(publishedAt),
        source = sourceHeadlineResponse.asDomainModel(),
        title = title,
        content = content
    )

private fun formatDate(date: String): String {
    val zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)

    val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formattedTime = zonedDateTime.format(timeFormatter)

    return formattedTime
}

private fun SourceHeadlineResponse.asDomainModel(): SourceItem =
    SourceItem(
        name = name,
        id = id.orEmpty()
    )