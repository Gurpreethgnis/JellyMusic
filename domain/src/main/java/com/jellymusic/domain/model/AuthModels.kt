package com.jellymusic.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentials(
    val serverUrl: String,
    val username: String,
    val password: String
)

@Serializable
data class AuthResult(
    val user: User,
    val accessToken: String,
    val serverId: String,
    val isSuccess: Boolean,
    val errorMessage: String? = null
)

@Serializable
data class User(
    val id: String,
    val name: String,
    val serverId: String? = null,
    val hasPassword: Boolean = true,
    val enableAutoLogin: Boolean = true,
    val lastLoginDate: String? = null
)

@Serializable
data class ServerInfo(
    val id: String,
    val name: String,
    val version: String,
    val operatingSystem: String? = null,
    val localAddress: String? = null,
    val remoteAddress: String? = null,
    val wanAddress: String? = null,
    val supportsRemoteAccess: Boolean = true,
    val supportsSync: Boolean = true,
    val supportsMediaControl: Boolean = true,
    val supportsTranscoding: Boolean = true
)

sealed class AuthState {
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}
