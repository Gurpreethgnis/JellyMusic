package com.jellymusic.data.mapper

import com.jellymusic.data.remote.dto.LoginRequestDto
import com.jellymusic.data.remote.dto.LoginResponseDto
import com.jellymusic.data.remote.dto.ServerInfoDto
import com.jellymusic.data.remote.dto.UserDto
import com.jellymusic.domain.model.AuthResult
import com.jellymusic.domain.model.LoginCredentials
import com.jellymusic.domain.model.ServerInfo
import com.jellymusic.domain.model.User

object AuthMapper {
    
    fun toLoginRequest(credentials: LoginCredentials): LoginRequestDto {
        return LoginRequestDto(
            username = credentials.username,
            password = credentials.password
        )
    }
    
    fun toAuthResult(response: LoginResponseDto, isSuccess: Boolean = true, errorMessage: String? = null): AuthResult {
        return AuthResult(
            user = toUser(response.user),
            accessToken = response.accessToken,
            serverId = response.serverId,
            isSuccess = isSuccess,
            errorMessage = errorMessage
        )
    }
    
    fun toUser(userDto: UserDto): User {
        return User(
            id = userDto.id,
            name = userDto.name,
            serverId = userDto.serverId,
            hasPassword = userDto.hasPassword,
            enableAutoLogin = userDto.enableAutoLogin,
            lastLoginDate = userDto.lastLoginDate
        )
    }
    
    fun toServerInfo(serverInfoDto: ServerInfoDto): ServerInfo {
        return ServerInfo(
            id = serverInfoDto.id,
            name = serverInfoDto.name,
            version = serverInfoDto.version,
            operatingSystem = serverInfoDto.operatingSystem,
            localAddress = serverInfoDto.localAddress,
            remoteAddress = serverInfoDto.remoteAddress,
            wanAddress = serverInfoDto.wanAddress,
            supportsRemoteAccess = serverInfoDto.supportsRemoteAccess,
            supportsSync = serverInfoDto.supportsSync,
            supportsMediaControl = serverInfoDto.supportsMediaControl,
            supportsTranscoding = serverInfoDto.supportsTranscoding
        )
    }
    
    fun createAuthHeader(deviceId: String, deviceName: String, version: String): String {
        return "MediaBrowser Client=\"JellyMusic\", Device=\"$deviceName\", DeviceId=\"$deviceId\", Version=\"$version\""
    }
}
