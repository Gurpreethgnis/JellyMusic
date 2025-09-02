package com.jellymusic.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("Username")
    val username: String,
    @SerialName("Pw")
    val password: String
)

@Serializable
data class LoginResponseDto(
    @SerialName("User")
    val user: UserDto,
    @SerialName("AccessToken")
    val accessToken: String,
    @SerialName("ServerId")
    val serverId: String,
    @SerialName("SessionInfo")
    val sessionInfo: SessionInfoDto? = null
)

@Serializable
data class SessionInfoDto(
    @SerialName("Id")
    val id: String,
    @SerialName("UserId")
    val userId: String,
    @SerialName("UserName")
    val userName: String
)

@Serializable
data class ErrorResponseDto(
    @SerialName("Error")
    val error: String? = null,
    @SerialName("ErrorCode")
    val errorCode: String? = null,
    @SerialName("Message")
    val message: String? = null
)
