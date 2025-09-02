package com.jellymusic.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDto(
    @SerialName("Id")
    val id: String,
    @SerialName("Name")
    val name: String,
    @SerialName("ImageTags")
    val imageTags: Map<String, String>? = null,
    @SerialName("SongCount")
    val trackCount: Int = 0,
    @SerialName("ItemIds")
    val trackIds: List<String> = emptyList()
)
