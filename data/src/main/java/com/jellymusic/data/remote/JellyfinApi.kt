package com.jellymusic.data.remote

import com.jellymusic.data.remote.dto.AlbumDto
import com.jellymusic.data.remote.dto.ArtistDto
import com.jellymusic.data.remote.dto.PlaylistDto
import com.jellymusic.data.remote.dto.SearchResultDto
import com.jellymusic.data.remote.dto.TrackDto
import com.jellymusic.data.remote.dto.UserAuthDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface JellyfinApi {
    
    @POST("Users/AuthenticateByName")
    suspend fun authenticateUser(
        @Query("Username") username: String,
        @Query("Pw") password: String,
        @Header("X-Emby-Authorization") authHeader: String
    ): UserAuthDto

    @GET("Artists")
    suspend fun getArtists(
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "MusicArtist",
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50
    ): List<ArtistDto>

    @GET("Albums")
    suspend fun getAlbums(
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "MusicAlbum",
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50
    ): List<AlbumDto>

    @GET("Users/{userId}/Items")
    suspend fun getAlbumsByArtist(
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "MusicAlbum",
        @Query("ArtistIds") artistId: String,
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50
    ): List<AlbumDto>

    @GET("Users/{userId}/Items")
    suspend fun getTracksByAlbum(
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "Audio",
        @Query("AlbumIds") albumId: String,
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 100
    ): List<TrackDto>

    @GET("Users/{userId}/Items")
    suspend fun getPlaylists(
        @Header("X-MediaBrowser-Token") token: String,
        @Query("IncludeItemTypes") includeTypes: String = "Playlist",
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50
    ): List<PlaylistDto>

    @GET("Search/Hints")
    suspend fun search(
        @Header("X-MediaBrowser-Token") token: String,
        @Query("SearchTerm") query: String,
        @Query("IncludeItemTypes") includeTypes: String = "MusicArtist,MusicAlbum,Audio",
        @Query("StartIndex") startIndex: Int = 0,
        @Query("Limit") limit: Int = 50
    ): SearchResultDto
}
