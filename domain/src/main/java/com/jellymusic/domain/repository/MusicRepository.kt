package com.jellymusic.domain.repository

import androidx.paging.PagingData
import com.jellymusic.domain.model.Album
import com.jellymusic.domain.model.Artist
import com.jellymusic.domain.model.Playlist
import com.jellymusic.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun getArtists(): Flow<PagingData<Artist>>
    fun getAlbums(): Flow<PagingData<Album>>
    fun getAlbumsByArtist(artistId: String): Flow<PagingData<Album>>
    fun getPlaylists(): Flow<PagingData<Playlist>>
    fun getTracksByAlbum(albumId: String): Flow<List<Track>>
    fun getTracksByPlaylist(playlistId: String): Flow<List<Track>>
    fun searchArtists(query: String): Flow<PagingData<Artist>>
    fun searchAlbums(query: String): Flow<PagingData<Album>>
    fun searchTracks(query: String): Flow<PagingData<Track>>
    suspend fun getTrack(trackId: String): Track?
}
