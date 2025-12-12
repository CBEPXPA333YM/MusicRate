package com.example.api_test.ui.screens

import androidx.compose.foundation.layout.Column
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

@Composable
fun AlbumDetailsScreen(title: String, imageUrl: String) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(title) }) }
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
                    contentDescription = "Album image",
                    modifier = Modifier.size(200.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text(title, style = MaterialTheme.typography.body1)

                Spacer(Modifier.height(24.dp))

                Button(onClick = { /* TODO: Добавить в избранное */ }) {
                    Text("Добавить в избранное")
                }
            }

            // Здесь можно добавить динамический список треков альбома
        }
    }
}