package com.jellymusic.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAuthDto(
    @SerialName("User")
    val user: UserDto,
    @SerialName("AccessToken")
    val accessToken: String,
    @SerialName("ServerId")
    val serverId: String
)

@Serializable
data class UserDto(
    @SerialName("Id")
    val id: String,
    @SerialName("Name")
    val name: String
)
