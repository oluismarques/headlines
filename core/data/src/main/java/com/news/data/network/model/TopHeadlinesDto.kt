package com.news.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TopHeadlinesResponse(
    @SerialName("articles")
    val contentHeadlineResponse: List<ContentHeadlineResponse>,
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int,
)

internal fun List<ContentHeadlineResponse>.asDomainModel() = map {
    it.asDomainModel()
}