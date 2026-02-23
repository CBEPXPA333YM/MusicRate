package com.example.api_test.ui.screens

import android.content.Intent
import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_test.details.AlbumDetailsActivity
import com.example.api_test.details.ArtistDetailsActivity
import com.example.api_test.details.TrackDetailsActivity
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.ui.SmartCard
import com.example.api_test.ui.SmartItem
import com.example.api_test.ui.SmartType

fun FavoritesEntity.toSmartItem(): SmartItem {
    return SmartItem(
        id = id,
        title = title,
        imageUrl = imageUrl,
        subtitle = subtitle,
        type = type,
        rating = rating)
}

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val favorites by favoritesViewModel.getAllFavorites()
        .collectAsState(initial = emptyList())

    val context = LocalContext.current

    // 🔹 Состояние фильтра
    var selectedType by remember { mutableStateOf<SmartType?>(null) }

    // 🔹 Фильтрация0
    val filteredFavorites = if (selectedType == null) {
        favorites
    } else {
        favorites.filter { it.type == selectedType }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorites") })
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // 🔹 Панель фильтров
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterButton("All", selectedType == null) { selectedType = null }
                FilterButton("Artists", selectedType == SmartType.ARTIST) { selectedType = SmartType.ARTIST }
                FilterButton("Albums", selectedType == SmartType.ALBUM) { selectedType = SmartType.ALBUM }
                FilterButton("Tracks", selectedType == SmartType.TRACK) { selectedType = SmartType.TRACK }
            }

            // 🔹 Список избранного
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(filteredFavorites) { entity ->
                    val item = entity.toSmartItem()

                    SmartCard(item = item) {
                        val intent = when (item.type) {
                            SmartType.ARTIST -> Intent(context, ArtistDetailsActivity::class.java)
                            SmartType.ALBUM  -> Intent(context, AlbumDetailsActivity::class.java)
                            SmartType.TRACK  -> Intent(context, TrackDetailsActivity::class.java)
                        }.apply {
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
}

// 🔹 Кнопка фильтра
@Composable
fun FilterButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) MaterialTheme.colors.primary else Color.LightGray
        )
    ) {
        Text(text)
    }
}

