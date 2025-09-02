package com.jellymusic.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: String,
    val name: String,
    val artistId: String,
    val artistName: String,
    val imageUrl: String? = null,
    val year: Int? = null,
    val trackCount: Int = 0
)
