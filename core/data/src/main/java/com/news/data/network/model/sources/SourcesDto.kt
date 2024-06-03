package com.news.data.network.model.sources


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SourceResponse(
    @SerialName("sources")
    val sourceItemsResponse: List<SourceItemResponse>,
    @SerialName("status")
    val status: String
)

internal fun List<SourceItemResponse>.asDomainModel() = map {
    it.asDomainModel()
}