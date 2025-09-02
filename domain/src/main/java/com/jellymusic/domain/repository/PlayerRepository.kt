package com.jellymusic.domain.repository

import com.jellymusic.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getCurrentTrack(): Flow<Track?>
    fun getPlaybackState(): Flow<PlaybackState>
    fun getQueue(): Flow<List<Track>>
    fun getCurrentPosition(): Flow<Long>
    fun getDuration(): Flow<Long>
    
    suspend fun play(track: Track)
    suspend fun playQueue(tracks: List<Track>, startIndex: Int = 0)
    suspend fun pause()
    suspend fun resume()
    suspend fun stop()
    suspend fun seekTo(position: Long)
    suspend fun skipToNext()
    suspend fun skipToPrevious()
    suspend fun setShuffleMode(enabled: Boolean)
    suspend fun setRepeatMode(mode: RepeatMode)
    
    suspend fun castToDevice(deviceId: String)
    suspend fun stopCasting()
    fun getCastDevices(): Flow<List<CastDevice>>
}

enum class PlaybackState {
    IDLE, PLAYING, PAUSED, BUFFERING, ERROR
}

enum class RepeatMode {
    NONE, ONE, ALL
}

data class CastDevice(
    val id: String,
    val name: String,
    val isConnected: Boolean = false
)
