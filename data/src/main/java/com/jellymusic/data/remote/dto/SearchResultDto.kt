package com.jellymusic.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    @SerialName("SearchHints")
    val searchHints: List<SearchHintDto> = emptyList()
)

@Serializable
data class SearchHintDto(
    @SerialName("Id")
    val id: String,
    @SerialName("Name")
    val name: String,
    @SerialName("Type")
    val type: String,
    @SerialName("ItemId")
    val itemId: String
)
