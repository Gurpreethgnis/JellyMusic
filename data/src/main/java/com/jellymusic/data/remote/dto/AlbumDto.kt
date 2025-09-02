package com.jellymusic.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    @SerialName("Id")
    val id: String,
    @SerialName("Name")
    val name: String,
    @SerialName("ArtistId")
    val artistId: String,
    @SerialName("ArtistName")
    val artistName: String,
    @SerialName("ImageTags")
    val imageTags: Map<String, String>? = null,
    @SerialName("ProductionYear")
    val year: Int? = null,
    @SerialName("SongCount")
    val trackCount: Int = 0
)
