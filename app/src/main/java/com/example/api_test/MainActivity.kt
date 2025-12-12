package com.example.api_test
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import com.example.api_test.MusicViewModel
import com.example.api_test.data.spotifyApi.SpotifyRepository
import com.example.api_test.ui.SmartSearchScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = SpotifyRepository("bb6fa6756d71410d8783030eac178593", "b04ca2f94ca640828ec7d7e5608796f2")
        val viewModelFactory = MusicViewModelFactory(repository)

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MusicViewModel::class.java)

        setContent {
            SmartSearchScreen(viewModel)
        }
    }
}
