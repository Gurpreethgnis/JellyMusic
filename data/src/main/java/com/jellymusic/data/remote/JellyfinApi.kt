package com.jellymusic.data.remote

import com.jellymusic.data.remote.dto.AlbumDto
import com.jellymusic.data.remote.dto.ArtistDto
import com.jellymusic.data.remote.dto.LoginRequestDto
import com.jellymusic.data.remote.dto.LoginResponseDto
import com.jellymusic.data.remote.dto.PlaylistDto
import com.jellymusic.data.remote.dto.SearchResultDto
import com.jellymusic.data.remote.dto.ServerInfoDto
import com.jellymusic.data.remote.dto.TrackDto
import com.jellymusic.data.remote.dto.UserAuthDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface JellyfinApi {
    
    // Authentication endpoints
    @POST("Users/AuthenticateByName")
    suspend fun authenticateUser(
        @Body request: LoginRequestDto,
        @Header("X-Emby-Authorization") authHeader: String
    ): LoginResponseDto

    @GET("System/Info")
    suspend fun getServerInfo(
        @Header("X-MediaBrowser-Token") token: String
    ): ServerInfoDto

    // Music content endpoints
    @GET("Users/{userId}/Items")
    suspend fun getArtists(
        @Path("userId") userId: String,
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "MusicArtist",
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50,
        @Query("SortBy") sortBy: String = "SortName",
        @Query("SortOrder") sortOrder: String = "Ascending"
    ): List<ArtistDto>

    @GET("Users/{userId}/Items")
    suspend fun getAlbums(
        @Path("userId") userId: String,
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "MusicAlbum",
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50,
        @Query("SortBy") sortBy: String = "SortName",
        @Query("SortOrder") sortOrder: String = "Ascending"
    ): List<AlbumDto>

    @GET("Users/{userId}/Items")
    suspend fun getAlbumsByArtist(
        @Path("userId") userId: String,
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "MusicAlbum",
        @Query("ArtistIds") artistId: String,
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50,
        @Query("SortBy") sortBy: String = "SortName",
        @Query("SortOrder") sortOrder: String = "Ascending"
    ): List<AlbumDto>

    @GET("Users/{userId}/Items")
    suspend fun getTracksByAlbum(
        @Path("userId") userId: String,
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "Audio",
        @Query("AlbumIds") albumId: String,
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 100,
        @Query("SortBy") sortBy: String = "IndexNumber",
        @Query("SortOrder") sortOrder: String = "Ascending"
    ): List<TrackDto>

    @GET("Users/{userId}/Items")
    suspend fun getPlaylists(
        @Path("userId") userId: String,
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "Playlist",
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50,
        @Query("SortBy") sortBy: String = "SortName",
        @Query("SortOrder") sortOrder: String = "Ascending"
    ): List<PlaylistDto>

    // Search endpoint
    @GET("Search/Hints")
    suspend fun search(
        @Header("X-MediaBrowser-Token") token: String,
        @Query("SearchTerm") query: String,
        @Query("IncludeItemTypes") includeTypes: String = "MusicArtist,MusicAlbum,Audio",
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50
    ): SearchResultDto

    // Image endpoint
    @GET("Items/{itemId}/Images/{imageType}")
    suspend fun getImageUrl(
        @Path("itemId") itemId: String,
        @Path("imageType") imageType: String,
        @Query("Tag") tag: String? = null,
        @Query("MaxWidth") maxWidth: Int = 300,
        @Query("MaxHeight") maxHeight: Int = 300,
        @Query("Quality") quality: Int = 90
    ): String
}
