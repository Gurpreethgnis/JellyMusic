package com.jellymusic.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.jellymusic.app.R

sealed class Screen(
    val route: String,
    val resourceId: Int,
    val icon: ImageVector
) {
    object Artists : Screen(
        route = "artists",
        resourceId = R.string.nav_artists,
        icon = Icons.Default.Person
    )
    
    object Albums : Screen(
        route = "albums",
        resourceId = R.string.nav_albums,
        icon = Icons.Default.List
    )
    
    object Playlists : Screen(
        route = "playlists",
        resourceId = R.string.nav_playlists,
        icon = Icons.Default.List
    )
    
    object Search : Screen(
        route = "search",
        resourceId = R.string.nav_search,
        icon = Icons.Default.Search
    )
}
