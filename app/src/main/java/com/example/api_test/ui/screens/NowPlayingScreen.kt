package com.example.api_test.ui.screens

import ads_mobile_sdk.h6
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_test.deezerApi.DeezerViewModel
import com.example.api_test.details.AlbumDetailsActivity
import com.example.api_test.details.ArtistDetailsActivity
import com.example.api_test.details.TrackDetailsActivity
import com.example.api_test.nowPlaying.NowPlayingViewModel
import com.example.api_test.ui.SmartCard
import com.example.api_test.ui.SmartItem
import com.example.api_test.ui.SmartType


@Composable
fun NowPlayingScreen(
    viewModel: NowPlayingViewModel,
    deezerViewModel: DeezerViewModel = viewModel()
) {

    val tracks by viewModel.tracks.collectAsState()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.White
    ) { padding ->

        if (tracks.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Включите музыку на устройстве")
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // 🔥 Текущий трек
                item {
                    val currentTrack = tracks.first()

                    Text(
                        text = "Сейчас играет",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    SmartCard(
                        item = currentTrack,
                    ) {
                        openDetails(context, deezerViewModel, currentTrack)
                    }
                }

                // 🎵 Заголовок истории
                item {
                    Text(
                        text = "История прослушивания",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // 🎵 История
                items(
                    items = tracks.drop(1),
                    key = { "${it.id}_${it.type}" }
                ) { trackItem ->

                    SmartCard(item = trackItem) {
                        openDetails(context, deezerViewModel, trackItem)
                    }
                }
            }
        }
    }
}

private fun openDetails(
    context: Context,
    deezerViewModel: DeezerViewModel,
    trackItem: SmartItem
) {
    val query = "${trackItem.title} ${trackItem.subtitle}"

    deezerViewModel.searchTracksItems(query) { results ->

        val firstTrack = results.firstOrNull()
            ?: return@searchTracksItems

        context.startActivity(
            Intent(context, TrackDetailsActivity::class.java)
                .putExtra("id", firstTrack.id)
                .putExtra("title", firstTrack.title)
                .putExtra("image", firstTrack.imageUrl)
        )
    }
}

