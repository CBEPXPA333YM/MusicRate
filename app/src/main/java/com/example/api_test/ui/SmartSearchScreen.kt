package com.example.api_test.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.api_test.MusicViewModel

enum class SmartSearchMode {
    ARTIST, TRACK, ALBUM
}

@Composable
fun SmartSearchScreen(viewModel: MusicViewModel) {

    var query by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf(SmartSearchMode.ARTIST) }

    // РЕЗУЛЬТАТЫ ПОИСКА
    var resultsState by remember { mutableStateOf<List<String>>(emptyList()) }

    // ОШИБКА
    var errorState by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Поиск") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // ------------------- КНОПКИ МОДОВ -------------------
        Row(Modifier.fillMaxWidth()) {

            Button(
                onClick = { mode = SmartSearchMode.ARTIST },
                modifier = Modifier.weight(1f)
            ) { Text("Исполнитель") }

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = { mode = SmartSearchMode.TRACK },
                modifier = Modifier.weight(1f)
            ) { Text("Трек") }

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = { mode = SmartSearchMode.ALBUM },
                modifier = Modifier.weight(1f)
            ) { Text("Альбом") }
        }

        Spacer(Modifier.height(16.dp))

        // КНОПКА ПОИСКА
        Button(
            onClick = {
                if (query.isBlank()) return@Button

                when (mode) {

                    SmartSearchMode.ARTIST ->
                        viewModel.searchArtists(query) { results ->
                            resultsState = results
                            errorState =
                                if (results.isEmpty()) "Ничего не найдено" else null
                        }

                    SmartSearchMode.TRACK ->
                        viewModel.searchTracks(query) { results ->
                            resultsState = results
                            errorState =
                                if (results.isEmpty()) "Ничего не найдено" else null
                        }

                    SmartSearchMode.ALBUM ->
                        viewModel.searchAlbums(query) { results ->
                            resultsState = results
                            errorState =
                                if (results.isEmpty()) "Ничего не найдено" else null
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Искать")
        }

        Spacer(Modifier.height(16.dp))

        Text("Результаты:", style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(8.dp))

        //  ОШИБКА
        errorState?.let {
            Text(it, color = MaterialTheme.colors.error)
            Spacer(Modifier.height(8.dp))
        }

        // СПИСОК
        LazyColumn {
            items(resultsState) { item ->
                Text(item)
                Divider()
            }
        }
    }
}