package com.jellymusic.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jellymusic.data.mapper.AuthMapper
import com.jellymusic.data.remote.JellyfinApi
import com.jellymusic.data.remote.dto.LoginResponseDto
import com.jellymusic.data.remote.dto.UserDto
import com.jellymusic.domain.model.AuthResult
import com.jellymusic.domain.model.AuthState
import com.jellymusic.domain.model.LoginCredentials
import com.jellymusic.domain.model.User
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepositoryImpl
    private lateinit var mockApi: JellyfinApi
    private lateinit var mockContext: Context
    private lateinit var mockDataStore: DataStore<Preferences>
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        
        mockApi = mockk()
        mockContext = mockk()
        mockDataStore = mockk()
        
        // Mock DataStore
        every { mockContext.dataStore } returns mockDataStore
        
        authRepository = AuthRepositoryImpl(mockContext, mockApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success should return AuthResult with success`() = runTest {
        // Given
        val credentials = LoginCredentials("https://test.com", "testuser", "testpass")
        val loginResponse = LoginResponseDto(
            user = UserDto("user123", "testuser"),
            accessToken = "token123",
            serverId = "server123"
        )
        
        coEvery { 
            mockApi.authenticateUser(any(), any()) 
        } returns loginResponse
        
        coEvery { 
            mockDataStore.edit(any()) 
        } returns mockDataStore
        
        // When
        val result = authRepository.login(credentials)
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals("user123", result.user.id)
        assertEquals("testuser", result.user.name)
        assertEquals("token123", result.accessToken)
        assertEquals("server123", result.serverId)
        assertNull(result.errorMessage)
    }

    @Test
    fun `login failure should return AuthResult with error`() = runTest {
        // Given
        val credentials = LoginCredentials("https://test.com", "testuser", "wrongpass")
        val errorMessage = "Invalid username or password"
        
        coEvery { 
            mockApi.authenticateUser(any(), any()) 
        } throws IOException(errorMessage)
        
        // When
        val result = authRepository.login(credentials)
        
        // Then
        assertFalse(result.isSuccess)
        assertEquals(errorMessage, result.errorMessage)
        assertEquals("", result.accessToken)
        assertEquals("", result.serverId)
    }

    @Test
    fun `login should save credentials on success`() = runTest {
        // Given
        val credentials = LoginCredentials("https://test.com", "testuser", "testpass")
        val loginResponse = LoginResponseDto(
            user = UserDto("user123", "testuser"),
            accessToken = "token123",
            serverId = "server123"
        )
        
        coEvery { 
            mockApi.authenticateUser(any(), any()) 
        } returns loginResponse
        
        coEvery { 
            mockDataStore.edit(any()) 
        } returns mockDataStore
        
        // When
        authRepository.login(credentials)
        
        // Then
        coVerify { 
            mockDataStore.edit(any()) 
        }
    }

    @Test
    fun `isAuthenticated should return true when token exists`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        every { preferences[AuthRepositoryImpl.TOKEN_KEY] } returns "valid_token"
        
        coEvery { 
            mockDataStore.data.first() 
        } returns preferences
        
        // When
        val result = authRepository.isAuthenticated()
        
        // Then
        assertTrue(result)
    }

    @Test
    fun `isAuthenticated should return false when token is null`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        every { preferences[AuthRepositoryImpl.TOKEN_KEY] } returns null
        
        coEvery { 
            mockDataStore.data.first() 
        } returns preferences
        
        // When
        val result = authRepository.isAuthenticated()
        
        // Then
        assertFalse(result)
    }

    @Test
    fun `isAuthenticated should return false when token is empty`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        every { preferences[AuthRepositoryImpl.TOKEN_KEY] } returns ""
        
        coEvery { 
            mockDataStore.data.first() 
        } returns preferences
        
        // When
        val result = authRepository.isAuthenticated()
        
        // Then
        assertFalse(result)
    }

    @Test
    fun `getCurrentUser should return user when credentials exist`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        every { preferences[AuthRepositoryImpl.USER_ID_KEY] } returns "user123"
        every { preferences[AuthRepositoryImpl.USERNAME_KEY] } returns "testuser"
        
        coEvery { 
            mockDataStore.data.first() 
        } returns preferences
        
        // When
        val result = authRepository.getCurrentUser()
        
        // Then
        assertNotNull(result)
        assertEquals("user123", result?.id)
        assertEquals("testuser", result?.name)
    }

    @Test
    fun `getCurrentUser should return null when credentials missing`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        every { preferences[AuthRepositoryImpl.USER_ID_KEY] } returns null
        every { preferences[AuthRepositoryImpl.USERNAME_KEY] } returns null
        
        coEvery { 
            mockDataStore.data.first() 
        } returns preferences
        
        // When
        val result = authRepository.getCurrentUser()
        
        // Then
        assertNull(result)
    }

    @Test
    fun `logout should clear credentials and set unauthenticated state`() = runTest {
        // Given
        coEvery { 
            mockDataStore.edit(any()) 
        } returns mockDataStore
        
        // When
        authRepository.logout()
        
        // Then
        coVerify { 
            mockDataStore.edit(any()) 
        }
    }

    @Test
    fun `getAuthHeaders should return headers when authenticated`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        every { preferences[AuthRepositoryImpl.TOKEN_KEY] } returns "valid_token"
        
        coEvery { 
            mockDataStore.data.first() 
        } returns preferences
        
        // When
        val headers = authRepository.getAuthHeaders()
        
        // Then
        assertEquals(2, headers.size)
        assertTrue(headers.containsKey("X-MediaBrowser-Token"))
        assertTrue(headers.containsKey("X-Emby-Authorization"))
        assertEquals("valid_token", headers["X-MediaBrowser-Token"])
    }

    @Test
    fun `getAuthHeaders should return empty map when not authenticated`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        every { preferences[AuthRepositoryImpl.TOKEN_KEY] } returns null
        
        coEvery { 
            mockDataStore.data.first() 
        } returns preferences
        
        // When
        val headers = authRepository.getAuthHeaders()
        
        // Then
        assertTrue(headers.isEmpty())
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "test_auth_preferences")
    }
}
