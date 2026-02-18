package com.example.api_test.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_test.details.AlbumDetailsActivity
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.ui.SmartCard
import com.example.api_test.ui.SmartItem

fun FavoritesEntity.toSmartItem(): SmartItem {
    return SmartItem(
        id = id,
        title = title,
        imageUrl = imageUrl,
        subtitle = subtitle,
        type = type )
}

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val favorites by favoritesViewModel.getAllFavorites()
        .collectAsState(initial = emptyList())

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorites") }) // можно заменить на любой заголовок
        }
    ) { paddingValues -> // безопасные отступы для topBar
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // учёт TopAppBar
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(favorites) { entity ->
                val item = entity.toSmartItem()

                SmartCard(item = item) {
                    val intent = Intent(context, AlbumDetailsActivity::class.java).apply {
                        putExtra("id", item.id)
                        putExtra("title", item.title)
                        putExtra("image", item.imageUrl)
                        putExtra("type", item.type.name)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}
