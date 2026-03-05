package com.example.api_test.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.api_test.nowPlaying.NowPlayingViewModel
import com.example.api_test.ui.SmartCard

@Composable
fun NowPlayingScreen(viewModel: NowPlayingViewModel) {

    val track by viewModel.track.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadNowPlaying()
    }

    track?.let {
        SmartCard(item = it) {}
    }
}
