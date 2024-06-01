package com.news.data.network.model


import com.news.domain.headlines.Source
import com.news.domain.headlines.TopHeadline
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
)

@Serializable
internal data class SourceHeadlineResponse(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String,
)

internal fun ContentHeadlineResponse.asDomainModel(): TopHeadline =
    TopHeadline(
        author = author,
        description = description.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage,
        publishedAt = formatDate(publishedAt),
        source = sourceHeadlineResponse.asDomainModel()
    )

private fun formatDate(date: String): String {
    val zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)

    val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formattedTime = zonedDateTime.format(timeFormatter)

    return formattedTime
}

private fun SourceHeadlineResponse.asDomainModel(): Source =
    Source(
        name = name,
        id = id.orEmpty()
    )