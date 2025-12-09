package com.example.api_test.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.api_test.MusicViewModel
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.platform.LocalContext


@Composable
fun MusicSearchScreen(viewModel: MusicViewModel) {
    var artist by remember { mutableStateOf("") }
    var album by remember { mutableStateOf("") }

    var uiData by remember { mutableStateOf<AlbumInfoUi?>(null) }
    var errorText by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Поля ввода
        OutlinedTextField(
            value = artist,
            onValueChange = { artist = it },
            label = { Text("Исполнитель") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = album,
            onValueChange = { album = it },
            label = { Text("Альбом") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        // Кнопка
        Button(
            onClick = {
                viewModel.searchAlbum(
                    artist = artist,
                    album = album,
                    onSuccess = { model ->
                        uiData = model
                        errorText = null
                    },
                    onError = { err ->
                        errorText = err
                        uiData = null
                    }
                )
            }
        ) {
            Text("Поиск")
        }

        Spacer(Modifier.height(24.dp))

        // Ошибка
        errorText?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.h6
            )
        }

        // UI данные (если есть)
        uiData?.let { data ->

            // Заголовок
            Text(
                text = data.name,
                style = MaterialTheme.typography.h5
            )

            Spacer(Modifier.height(16.dp))

            // Обложка
            data.coverUrl?.let { cover ->
                AsyncImage(
                    model = cover,
                    contentDescription = "Обложка альбома",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(Modifier.height(20.dp))
            }

            // Треки
            if (data.tracks.isNotEmpty()) {
                Text("Треклист:", style = MaterialTheme.typography.h6)
                Spacer(Modifier.height(8.dp))

                val context = LocalContext.current

                LazyColumn {
                    itemsIndexed(data.tracks) { index, track ->

                        ClickableText(
                            text = AnnotatedString("${index + 1}. ${track.name}"),
                            onClick = {
                                // Поиск на YouTube
                                val query = "${artist}, ${album}, ${track}".replace(" ", "+")
                                val url = "https://www.youtube.com/results?search_query=$query"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            }
                        )

                        Spacer(Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}