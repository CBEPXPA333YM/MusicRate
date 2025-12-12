package com.example.api_test.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.api_test.DeezerViewModel
import com.example.api_test.details.AlbumDetailsActivity
import com.example.api_test.details.ArtistDetailsActivity
import com.example.api_test.details.TrackDetailsActivity
import com.example.api_test.ui.SmartCard
import com.example.api_test.ui.SmartItem
import com.example.api_test.ui.SmartType


@Composable
fun SmartSearchScreen(viewModel: DeezerViewModel) {

    var query by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf(SmartType.ARTIST) }
    var resultsState by remember { mutableStateOf<List<SmartItem>>(emptyList()) }
    var errorState by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Smart Search") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // --- Поисковая строка ---
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Поиск") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // --- Кнопки режимов ---
            Row(Modifier.fillMaxWidth()) {
                Button(
                    onClick = { mode = SmartType.ARTIST },
                    modifier = Modifier.weight(1f)
                ) { Text("Исполнитель") }

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { mode = SmartType.TRACK },
                    modifier = Modifier.weight(1f)
                ) { Text("Трек") }

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { mode = SmartType.ALBUM },
                    modifier = Modifier.weight(1f)
                ) { Text("Альбом") }
            }

            Spacer(Modifier.height(16.dp))

            // --- Кнопка "Искать" ---
            Button(
                onClick = {
                    if (query.isBlank()) return@Button

                    when (mode) {
                        SmartType.ARTIST -> viewModel.searchArtistsItems(query) { items ->
                            resultsState = items
                            errorState = if (items.isEmpty()) "Ничего не найдено" else null
                        }
                        SmartType.TRACK -> viewModel.searchTracksItems(query) { items ->
                            resultsState = items
                            errorState = if (items.isEmpty()) "Ничего не найдено" else null
                        }
                        SmartType.ALBUM -> viewModel.searchAlbumsItems(query) { items ->
                            resultsState = items
                            errorState = if (items.isEmpty()) "Ничего не найдено" else null
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Искать")
            }

            Spacer(Modifier.height(16.dp))

            // --- Ошибка поиска ---
            errorState?.let {
                Text(it, color = MaterialTheme.colors.error)
                Spacer(Modifier.height(8.dp))
            }

            // --- Список результатов ---
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(resultsState) { item ->
                    SmartCard(item = item) {
                        when (item.type) {
                            SmartType.ARTIST -> context.startActivity(
                                Intent(context, ArtistDetailsActivity::class.java)
                                    .putExtra("title", item.title)
                                    .putExtra("image", item.imageUrl)
                            )
                            SmartType.ALBUM -> context.startActivity(
                                Intent(context, AlbumDetailsActivity::class.java)

                                .putExtra("title", item.title)
                                .putExtra("image", item.imageUrl)
                                )
                                SmartType.TRACK -> context.startActivity(
                            Intent(context, TrackDetailsActivity::class.java)
                                .putExtra("title", item.title)
                                .putExtra("image", item.imageUrl)
                        )
                        }
                    }
                }
            }
        }
    }
}