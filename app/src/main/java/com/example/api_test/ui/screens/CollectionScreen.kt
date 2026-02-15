package com.example.api_test.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.localdb.entity.FavoritesEntity

@Composable
fun CollectionScreen(favoritesViewModel: FavoritesViewModel) {

    val favorites = favoritesViewModel.favorites.collectAsState().value

    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Нет избранных элементов")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(favorites) { item ->
                FavoriteItem(item)
            }
        }
    }
}

@Composable
fun FavoriteItem(item: FavoritesEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = item.title, style = MaterialTheme.typography.h6)
            Text(text = item.type.name, style = MaterialTheme.typography.body2)
        }
    }
}