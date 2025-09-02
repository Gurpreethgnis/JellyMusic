package com.jellymusic.domain.repository

import com.jellymusic.domain.model.AuthResult
import com.jellymusic.domain.model.AuthState
import com.jellymusic.domain.model.LoginCredentials
import com.jellymusic.domain.model.ServerInfo
import com.jellymusic.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(credentials: LoginCredentials): AuthResult
    suspend fun logout()
    suspend fun getServerInfo(serverUrl: String, token: String): ServerInfo?
    
    // Credential management
    suspend fun saveCredentials(serverUrl: String, username: String, token: String, userId: String)
    suspend fun getSavedCredentials(): LoginCredentials?
    suspend fun clearCredentials()
    
    // Current session
    suspend fun getCurrentUser(): User?
    suspend fun getAccessToken(): String?
    suspend fun getServerUrl(): String?
    suspend fun getUserId(): String?
    
    // Auth state
    fun getAuthState(): Flow<AuthState>
    suspend fun isAuthenticated(): Boolean
    
    // Headers for API calls
    suspend fun getAuthHeaders(): Map<String, String>
}
