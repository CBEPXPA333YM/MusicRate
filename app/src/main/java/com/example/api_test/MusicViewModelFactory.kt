package com.example.api_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api_test.data.spotifyApi.SpotifyRepository

class MusicViewModelFactory(
    private val repo: SpotifyRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            return MusicViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}