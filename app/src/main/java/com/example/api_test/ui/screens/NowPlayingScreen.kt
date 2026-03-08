package com.example.api_test.ui.screens

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_test.deezerApi.DeezerViewModel
import com.example.api_test.details.AlbumDetailsActivity
import com.example.api_test.details.ArtistDetailsActivity
import com.example.api_test.details.TrackDetailsActivity
import com.example.api_test.nowPlaying.NowPlayingViewModel
import com.example.api_test.ui.SmartCard
import com.example.api_test.ui.SmartType

@Composable
fun NowPlayingScreen(viewModel: NowPlayingViewModel) {

    val track by viewModel.track.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadNowPlaying()
    }

    val deezerViewModel: DeezerViewModel = viewModel()

    track?.let { trackItem ->

        SmartCard(item = trackItem) {

            val query = "${trackItem.title} ${trackItem.subtitle}"

            deezerViewModel.searchTracksItems(query) { results ->

                val firstTrack = results.firstOrNull() ?: return@searchTracksItems

                context.startActivity(
                    Intent(context, TrackDetailsActivity::class.java)
                        .putExtra("id", firstTrack.id)
                        .putExtra("title", firstTrack.title)
                        .putExtra("image", firstTrack.imageUrl)
                )

            }

        }
    }
}
