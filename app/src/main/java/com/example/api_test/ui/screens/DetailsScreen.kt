package com.example.api_test.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import com.example.api_test.DeezerViewModel
import com.example.api_test.ui.SmartItem
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
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = subtitle,
                    modifier = Modifier.size(200.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.body1
                )

                Spacer(Modifier.height(24.dp))

                Button(onClick = { /* TODO */ }) {
                    Text("Добавить в избранное")
                }
            }

            // ↓ сюда в будущем можно добавить items(...) — треки, альбомы и т.п.
        }
    }
}