package com.jellymusic.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageInfoDto(
    @SerialName("ImageType")
    val imageType: String,
    @SerialName("Index")
    val index: Int = 0,
    @SerialName("Tag")
    val tag: String? = null,
    @SerialName("Size")
    val size: Long = 0,
    @SerialName("Width")
    val width: Int = 0,
    @SerialName("Height")
    val height: Int = 0,
    @SerialName("IsPrimary")
    val isPrimary: Boolean = false
)

@Serializable
data class ServerInfoDto(
    @SerialName("Id")
    val id: String,
    @SerialName("Name")
    val name: String,
    @SerialName("Version")
    val version: String,
    @SerialName("OperatingSystem")
    val operatingSystem: String? = null,
    @SerialName("LocalAddress")
    val localAddress: String? = null,
    @SerialName("RemoteAddress")
    val remoteAddress: String? = null,
    @SerialName("WanAddress")
    val wanAddress: String? = null,
    @SerialName("SupportsRemoteAccess")
    val supportsRemoteAccess: Boolean = true,
    @SerialName("SupportsSync")
    val supportsSync: Boolean = true,
    @SerialName("SupportsMediaControl")
    val supportsMediaControl: Boolean = true,
    @SerialName("SupportsTranscoding")
    val supportsTranscoding: Boolean = true
)
