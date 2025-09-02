package com.jellymusic.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val id: String,
    val name: String,
    val albumId: String,
    val albumName: String,
    val artistId: String,
    val artistName: String,
    val durationMs: Long,
    val streamUrl: String,
    val mediaType: String = "Audio",
    val trackNumber: Int = 0
)
