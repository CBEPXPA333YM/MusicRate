package com.example.api_test.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomScreen(val route: String, val title: String, val icon: ImageVector) {
    object NowPlaying : BottomScreen("nowPlaying", "Музыка", Icons.Default.MusicNote)
    object Search : BottomScreen("search", "Поиск", Icons.Default.Search)
    object Favorites : BottomScreen("favorites", "Избранное", Icons.Default.Favorite)
    object Recommendations : BottomScreen("recommendations", "Для вас", Icons.Default.AutoAwesome)
}
