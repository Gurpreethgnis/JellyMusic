package com.jellymusic.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jellymusic.data.di.NetworkModule
import com.jellymusic.data.remote.JellyfinApi
import com.jellymusic.domain.model.AuthState
import com.jellymusic.domain.model.LoginCredentials
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var authRepository: AuthRepositoryImpl
    private lateinit var mockContext: Context
    private lateinit var mockDataStore: DataStore<Preferences>
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        
        mockWebServer = MockWebServer()
        mockContext = mockk()
        mockDataStore = mockk()
        
        // Mock DataStore
        every { mockContext.dataStore } returns mockDataStore
        
        // Create real Retrofit with MockWebServer
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        
        val api = retrofit.create(JellyfinApi::class.java)
        authRepository = AuthRepositoryImpl(mockContext, api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

    @Test
    fun `successful login should return AuthResult with success`() = runTest {
        // Given
        val successResponse = """
            {
                "User": {
                    "Id": "user123",
                    "Name": "testuser"
                },
                "AccessToken": "token123",
                "ServerId": "server123"
            }
        """.trimIndent()
        
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(successResponse)
            .addHeader("Content-Type", "application/json"))
        
        val credentials = LoginCredentials("https://test.com", "testuser", "testpass")
        
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
        
        // Verify request was made
        val request = mockWebServer.takeRequest()
        assertEquals("POST", request.method)
        assertTrue(request.path?.contains("Users/AuthenticateByName") == true)
    }

    @Test
    fun `login with invalid credentials should return error`() = runTest {
        // Given
        val errorResponse = """
            {
                "Error": "Invalid username or password",
                "ErrorCode": "InvalidCredentials"
            }
        """.trimIndent()
        
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(401)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"))
        
        val credentials = LoginCredentials("https://test.com", "testuser", "wrongpass")
        
        // When
        val result = authRepository.login(credentials)
        
        // Then
        assertFalse(result.isSuccess)
        assertNotNull(result.errorMessage)
        assertEquals("", result.accessToken)
        assertEquals("", result.serverId)
    }

    @Test
    fun `network error should return error result`() = runTest {
        // Given
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(500)
            .setBody("Internal Server Error"))
        
        val credentials = LoginCredentials("https://test.com", "testuser", "testpass")
        
        // When
        val result = authRepository.login(credentials)
        
        // Then
        assertFalse(result.isSuccess)
        assertNotNull(result.errorMessage)
        assertEquals("", result.accessToken)
        assertEquals("", result.serverId)
    }

    @Test
    fun `malformed JSON response should return error`() = runTest {
        // Given
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody("Invalid JSON")
            .addHeader("Content-Type", "application/json"))
        
        val credentials = LoginCredentials("https://test.com", "testuser", "testpass")
        
        // When
        val result = authRepository.login(credentials)
        
        // Then
        assertFalse(result.isSuccess)
        assertNotNull(result.errorMessage)
    }

    @Test
    fun `auth state should be authenticated after successful login`() = runTest {
        // Given
        val successResponse = """
            {
                "User": {
                    "Id": "user123",
                    "Name": "testuser"
                },
                "AccessToken": "token123",
                "ServerId": "server123"
            }
        """.trimIndent()
        
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(successResponse)
            .addHeader("Content-Type", "application/json"))
        
        val credentials = LoginCredentials("https://test.com", "testuser", "testpass")
        
        coEvery { 
            mockDataStore.edit(any()) 
        } returns mockDataStore
        
        // When
        authRepository.login(credentials)
        
        // Then
        val authState = authRepository.getAuthState().first()
        assertTrue(authState is AuthState.Authenticated)
    }

    @Test
    fun `auth state should be error after failed login`() = runTest {
        // Given
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(401)
            .setBody("Unauthorized"))
        
        val credentials = LoginCredentials("https://test.com", "testuser", "wrongpass")
        
        // When
        authRepository.login(credentials)
        
        // Then
        val authState = authRepository.getAuthState().first()
        assertTrue(authState is AuthState.Error)
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "test_auth_preferences")
    }
}
