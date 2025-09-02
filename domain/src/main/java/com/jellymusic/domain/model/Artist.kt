package com.jellymusic.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: String,
    val name: String,
    val imageUrl: String? = null,
    val albumCount: Int = 0
)
