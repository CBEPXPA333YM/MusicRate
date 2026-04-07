package com.example.api_test.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_test.deezerApi.DeezerViewModel
import com.example.api_test.details.TrackDetailsActivity
import com.example.api_test.recommendationsApi.RecommendationsViewModel
import com.example.api_test.ui.SmartCard


@Composable
fun RecommendationsScreen(
    viewModel: RecommendationsViewModel
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val deezerVM: DeezerViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadRecommendations()
    }

    when {

        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.items.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No recommendations yet")
            }
        }

        else -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                items(state.items) { item ->

                    SmartCard(item = item) {

                        val query = "${item.title} ${item.subtitle}"

                        deezerVM.searchTracksItems(query) { results ->

                            val track = results.firstOrNull() ?: return@searchTracksItems

                            context.startActivity(
                                Intent(context, TrackDetailsActivity::class.java)
                                    .putExtra("id", track.id)
                                    .putExtra("title", track.title)
                                    .putExtra("image", track.imageUrl)
                            )
                        }
                    }
                }
            }
        }
    }
}