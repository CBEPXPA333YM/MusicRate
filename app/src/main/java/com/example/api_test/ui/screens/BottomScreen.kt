package com.example.api_test.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomScreen(val route: String, val title: String, val icon: ImageVector) {
    object Search : BottomScreen("search", "Поиск", Icons.Default.Search)
    object Favorites : BottomScreen("favorites", "Избранное", Icons.Default.Favorite)
}
