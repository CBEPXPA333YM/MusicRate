package com.example.api_test.ui.screens.SearchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.LazyColumn
import com.example.api_test.ui.SmartType

@Composable
fun DetailsScreen(
    title: String,
    imageUrl: String,
    type: SmartType
) {

    val subtitle = when (type) {
        SmartType.ARTIST -> "Artist"
        SmartType.ALBUM -> "Album"
        SmartType.TRACK -> "Track"
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(), // 👈 важно
        topBar = {
            TopAppBar(title = { Text(title) })
        }
    ) { scaffoldPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding), // safe area для top bar
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = subtitle,
                    modifier = Modifier.size(200.dp)
                )
            }

            item {
                Text(text = subtitle, style = MaterialTheme.typography.body1)
            }

            item {
                Button(onClick = { /* TODO: add to favorites */ }) {
                    Text("Добавить в избранное")
                }
            }

            // сюда можно добавлять items(...) для треков/альбомов
        }
    }
}
