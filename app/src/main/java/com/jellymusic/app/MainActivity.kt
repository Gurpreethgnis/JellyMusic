package com.jellymusic.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.jellymusic.app.ui.component.JellyMusicApp
import com.jellymusic.app.ui.screen.LoginScreen
import com.jellymusic.app.ui.theme.JellyMusicTheme
import com.jellymusic.app.ui.viewmodel.LoginViewModel
import com.jellymusic.domain.model.AuthState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JellyMusicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JellyMusicApp(
                        loginViewModel = loginViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun JellyMusicApp(loginViewModel: LoginViewModel) {
    val authState by loginViewModel.authState.collectAsState()
    
    when (authState) {
        is AuthState.Loading -> {
            // Show loading screen
            LoginScreen(
                authState = authState,
                onLogin = { serverUrl, username, password ->
                    loginViewModel.login(serverUrl, username, password)
                }
            )
        }
        is AuthState.Authenticated -> {
            // Show main app
            JellyMusicMainApp(
                onLogout = { loginViewModel.logout() }
            )
        }
        is AuthState.Unauthenticated -> {
            // Show login screen
            LoginScreen(
                authState = authState,
                onLogin = { serverUrl, username, password ->
                    loginViewModel.login(serverUrl, username, password)
                }
            )
        }
        is AuthState.Error -> {
            // Show login screen with error
            LoginScreen(
                authState = authState,
                onLogin = { serverUrl, username, password ->
                    loginViewModel.login(serverUrl, username, password)
                }
            )
        }
    }
}

@Composable
fun JellyMusicMainApp(onLogout: () -> Unit) {
    // This is the existing main app content
    JellyMusicApp(onLogout = onLogout)
}
