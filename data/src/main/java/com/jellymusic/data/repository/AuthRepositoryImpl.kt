package com.jellymusic.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jellymusic.data.mapper.AuthMapper
import com.jellymusic.data.remote.JellyfinApi
import com.jellymusic.domain.model.AuthResult
import com.jellymusic.domain.model.AuthState
import com.jellymusic.domain.model.LoginCredentials
import com.jellymusic.domain.model.ServerInfo
import com.jellymusic.domain.model.User
import com.jellymusic.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_preferences")

@Singleton
class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: JellyfinApi
) : AuthRepository {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    override fun getAuthState(): Flow<AuthState> = _authState.asStateFlow()

    private val deviceId = UUID.randomUUID().toString()
    private val deviceName = "JellyMusic Android"
    private val appVersion = "1.0.0"

    override suspend fun login(credentials: LoginCredentials): AuthResult {
        return try {
            _authState.value = AuthState.Loading
            
            val authHeader = AuthMapper.createAuthHeader(deviceId, deviceName, appVersion)
            val loginRequest = AuthMapper.toLoginRequest(credentials)
            
            val response = api.authenticateUser(loginRequest, authHeader)
            val authResult = AuthMapper.toAuthResult(response)
            
            if (authResult.isSuccess) {
                // Save credentials
                saveCredentials(
                    serverUrl = credentials.serverUrl,
                    username = credentials.username,
                    token = authResult.accessToken,
                    userId = authResult.user.id
                )
                _authState.value = AuthState.Authenticated
            }
            
            authResult
        } catch (e: Exception) {
            _authState.value = AuthState.Error(e.message ?: "Login failed")
            AuthResult(
                user = User("", ""),
                accessToken = "",
                serverId = "",
                isSuccess = false,
                errorMessage = e.message ?: "Login failed"
            )
        }
    }

    override suspend fun logout() {
        clearCredentials()
        _authState.value = AuthState.Unauthenticated
    }

    override suspend fun getServerInfo(serverUrl: String, token: String): ServerInfo? {
        return try {
            val response = api.getServerInfo(token)
            AuthMapper.toServerInfo(response)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveCredentials(serverUrl: String, username: String, token: String, userId: String) {
        context.dataStore.edit { preferences ->
            preferences[SERVER_URL_KEY] = serverUrl
            preferences[USERNAME_KEY] = username
            preferences[TOKEN_KEY] = token
            preferences[USER_ID_KEY] = userId
        }
    }

    override suspend fun getSavedCredentials(): LoginCredentials? {
        val preferences = context.dataStore.data.first()
        val serverUrl = preferences[SERVER_URL_KEY]
        val username = preferences[USERNAME_KEY]
        val token = preferences[TOKEN_KEY]
        
        return if (serverUrl != null && username != null && token != null) {
            LoginCredentials(serverUrl, username, token)
        } else {
            null
        }
    }

    override suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences.remove(SERVER_URL_KEY)
            preferences.remove(USERNAME_KEY)
            preferences.remove(TOKEN_KEY)
            preferences.remove(USER_ID_KEY)
        }
    }

    override suspend fun getCurrentUser(): User? {
        val preferences = context.dataStore.data.first()
        val userId = preferences[USER_ID_KEY]
        val username = preferences[USERNAME_KEY]
        
        return if (userId != null && username != null) {
            User(
                id = userId,
                name = username,
                enableAutoLogin = true
            )
        } else {
            null
        }
    }

    override suspend fun getAccessToken(): String? {
        val preferences = context.dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    override suspend fun getServerUrl(): String? {
        val preferences = context.dataStore.data.first()
        return preferences[SERVER_URL_KEY]
    }

    override suspend fun getUserId(): String? {
        val preferences = context.dataStore.data.first()
        return preferences[USER_ID_KEY]
    }

    override suspend fun isAuthenticated(): Boolean {
        val token = getAccessToken()
        return token != null && token.isNotEmpty()
    }

    override suspend fun getAuthHeaders(): Map<String, String> {
        val token = getAccessToken()
        return if (token != null) {
            mapOf(
                "X-MediaBrowser-Token" to token,
                "X-Emby-Authorization" to AuthMapper.createAuthHeader(deviceId, deviceName, appVersion)
            )
        } else {
            emptyMap()
        }
    }

    companion object {
        private val SERVER_URL_KEY = stringPreferencesKey("server_url")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }
}
