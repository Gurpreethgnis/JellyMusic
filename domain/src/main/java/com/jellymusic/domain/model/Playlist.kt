package com.jellymusic.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val id: String,
    val name: String,
    val imageUrl: String? = null,
    val trackCount: Int = 0,
    val trackIds: List<String> = emptyList()
)
