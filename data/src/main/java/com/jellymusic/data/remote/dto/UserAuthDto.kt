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
    val name: String,
    @SerialName("ServerId")
    val serverId: String? = null,
    @SerialName("HasPassword")
    val hasPassword: Boolean = true,
    @SerialName("HasConfiguredPassword")
    val hasConfiguredPassword: Boolean = true,
    @SerialName("HasConfiguredEasyPassword")
    val hasConfiguredEasyPassword: Boolean = false,
    @SerialName("EnableAutoLogin")
    val enableAutoLogin: Boolean = true,
    @SerialName("LastLoginDate")
    val lastLoginDate: String? = null,
    @SerialName("LastActivityDate")
    val lastActivityDate: String? = null,
    @SerialName("Configuration")
    val configuration: UserConfigurationDto? = null,
    @SerialName("Policy")
    val policy: UserPolicyDto? = null
)

@Serializable
data class UserConfigurationDto(
    @SerialName("PlayDefaultAudioTrack")
    val playDefaultAudioTrack: Boolean = true,
    @SerialName("SubtitleLanguagePreference")
    val subtitleLanguagePreference: String = "",
    @SerialName("DisplayMissingEpisodes")
    val displayMissingEpisodes: Boolean = false,
    @SerialName("GroupedFolders")
    val groupedFolders: List<String> = emptyList(),
    @SerialName("SubtitleMode")
    val subtitleMode: String = "Default",
    @SerialName("DisplayCollectionsView")
    val displayCollectionsView: Boolean = false,
    @SerialName("EnableLocalPassword")
    val enableLocalPassword: Boolean = false,
    @SerialName("OrderedViews")
    val orderedViews: List<String> = emptyList(),
    @SerialName("LatestItemsExcludes")
    val latestItemsExcludes: List<String> = emptyList(),
    @SerialName("MyMediaExcludes")
    val myMediaExcludes: List<String> = emptyList(),
    @SerialName("HidePlayedInLatest")
    val hidePlayedInLatest: Boolean = false,
    @SerialName("RememberAudioSelections")
    val rememberAudioSelections: Boolean = false,
    @SerialName("RememberSubtitleSelections")
    val rememberSubtitleSelections: Boolean = false,
    @SerialName("EnableNextEpisodeAutoPlay")
    val enableNextEpisodeAutoPlay: Boolean = false
)

@Serializable
data class UserPolicyDto(
    @SerialName("IsAdministrator")
    val isAdministrator: Boolean = false,
    @SerialName("IsHidden")
    val isHidden: Boolean = false,
    @SerialName("IsDisabled")
    val isDisabled: Boolean = false,
    @SerialName("MaxParentalRating")
    val maxParentalRating: Int? = null,
    @SerialName("BlockedTags")
    val blockedTags: List<String> = emptyList(),
    @SerialName("EnableUserPreferenceAccess")
    val enableUserPreferenceAccess: Boolean = true,
    @SerialName("AccessSchedules")
    val accessSchedules: List<String> = emptyList(),
    @SerialName("BlockUnratedItems")
    val blockUnratedItems: List<String> = emptyList(),
    @SerialName("EnableRemoteControlOfOtherUsers")
    val enableRemoteControlOfOtherUsers: Boolean = false,
    @SerialName("EnableSharedDeviceControl")
    val enableSharedDeviceControl: Boolean = false,
    @SerialName("EnableRemoteAccess")
    val enableRemoteAccess: Boolean = true,
    @SerialName("EnableLiveTvManagement")
    val enableLiveTvManagement: Boolean = false,
    @SerialName("EnableLiveTvAccess")
    val enableLiveTvAccess: Boolean = true,
    @SerialName("EnableMediaPlayback")
    val enableMediaPlayback: Boolean = true,
    @SerialName("EnableAudioPlaybackTranscoding")
    val enableAudioPlaybackTranscoding: Boolean = true,
    @SerialName("EnableVideoPlaybackTranscoding")
    val enableVideoPlaybackTranscoding: Boolean = true,
    @SerialName("EnablePlaybackRemuxing")
    val enablePlaybackRemuxing: Boolean = true,
    @SerialName("ForceRemoteSourceTranscoding")
    val forceRemoteSourceTranscoding: Boolean = false,
    @SerialName("EnableContentDownloading")
    val enableContentDownloading: Boolean = false,
    @SerialName("EnableContentDeletion")
    val enableContentDeletion: Boolean = false,
    @SerialName("EnableContentDeletionFromFolders")
    val enableContentDeletionFromFolders: List<String> = emptyList(),
    @SerialName("EnableSubtitleDownloading")
    val enableSubtitleDownloading: Boolean = false,
    @SerialName("EnableSubtitleManagement")
    val enableSubtitleManagement: Boolean = false,
    @SerialName("EnableSyncTranscoding")
    val enableSyncTranscoding: Boolean = false,
    @SerialName("EnableMediaConversion")
    val enableMediaConversion: Boolean = false,
    @SerialName("EnabledDevices")
    val enabledDevices: List<String> = emptyList(),
    @SerialName("EnableAllDevices")
    val enableAllDevices: Boolean = true,
    @SerialName("EnabledChannels")
    val enabledChannels: List<String> = emptyList(),
    @SerialName("EnableAllChannels")
    val enableAllChannels: Boolean = true,
    @SerialName("EnabledFolders")
    val enabledFolders: List<String> = emptyList(),
    @SerialName("EnableAllFolders")
    val enableAllFolders: Boolean = true,
    @SerialName("InvalidLoginAttemptCount")
    val invalidLoginAttemptCount: Int = 0,
    @SerialName("LoginAttemptsBeforeLockout")
    val loginAttemptsBeforeLockout: Int = -1,
    @SerialName("MaxActiveSessions")
    val maxActiveSessions: Int = 0,
    @SerialName("EnablePublicSharing")
    val enablePublicSharing: Boolean = false,
    @SerialName("BlockedMediaFolders")
    val blockedMediaFolders: List<String> = emptyList(),
    @SerialName("BlockedChannels")
    val blockedChannels: List<String> = emptyList(),
    @SerialName("RemoteClientBitrateLimit")
    val remoteClientBitrateLimit: Int = 0,
    @SerialName("AuthenticationProviderId")
    val authenticationProviderId: String = "",
    @SerialName("PasswordResetProviderId")
    val passwordResetProviderId: String = "",
    @SerialName("SyncPlayAccess")
    val syncPlayAccess: String = "CreateAndJoinGroups"
)
