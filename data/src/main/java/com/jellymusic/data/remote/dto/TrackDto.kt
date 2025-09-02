package com.jellymusic.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    @SerialName("Id")
    val id: String,
    @SerialName("Name")
    val name: String,
    @SerialName("AlbumId")
    val albumId: String,
    @SerialName("AlbumName")
    val albumName: String,
    @SerialName("ArtistId")
    val artistId: String,
    @SerialName("ArtistName")
    val artistName: String,
    @SerialName("RunTimeTicks")
    val runTimeTicks: Long,
    @SerialName("MediaType")
    val mediaType: String = "Audio",
    @SerialName("IndexNumber")
    val trackNumber: Int = 0
)
