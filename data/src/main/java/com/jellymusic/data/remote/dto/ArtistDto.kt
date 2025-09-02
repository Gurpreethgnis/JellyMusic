package com.jellymusic.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(
    @SerialName("Id")
    val id: String,
    @SerialName("Name")
    val name: String,
    @SerialName("ImageTags")
    val imageTags: Map<String, String>? = null,
    @SerialName("AlbumCount")
    val albumCount: Int = 0
)
