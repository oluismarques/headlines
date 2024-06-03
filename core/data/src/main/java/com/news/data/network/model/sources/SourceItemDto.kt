package com.news.data.network.model.sources


import com.news.domain.headlines.SourceItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SourceItemResponse(
    @SerialName("category")
    val category: String,
    @SerialName("country")
    val country: String,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: String,
    @SerialName("language")
    val language: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)

internal fun SourceItemResponse.asDomainModel(): SourceItem =
    SourceItem(
        name = name,
        id = id
    )