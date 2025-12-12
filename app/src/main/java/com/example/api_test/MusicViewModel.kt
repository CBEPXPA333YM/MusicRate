package com.example.api_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_test.data.spotifyApi.SpotifyRepository
import com.example.api_test.ui.SmartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class MusicViewModel(
    private val repo: SpotifyRepository
) : ViewModel() {

    // -------------------------------------------------------------
    // Artists
    // -------------------------------------------------------------
    fun searchArtistsItems(query: String, onResult: (List<SmartItem>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repo.searchArtists(query)
                }

                val artists = response?.artists?.items ?: emptyList()

                val items = artists.map { artist ->
                    SmartItem(
                        title = artist.name ?: "Без названия",
                        subtitle = "Популярность: ${artist.popularity ?: 0}",
                        imageUrl = artist.images?.firstOrNull()?.url // Spotify → full image
                    )
                }

                onResult(items)

            } catch (e: Exception) {
                onResult(emptyList())
            }
        }
    }

    // -------------------------------------------------------------
    // Albums
    // -------------------------------------------------------------
    fun searchAlbumsItems(query: String, onResult: (List<SmartItem>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repo.searchAlbums(query)
                }

                val albums = response?.albums?.items ?: emptyList()

                val items = albums.map { album ->
                    SmartItem(
                        title = album.name ?: "Без названия",
                        subtitle = album.artists?.joinToString { it.name ?: "" },
                        imageUrl = album.images?.firstOrNull()?.url
                    )
                }

                onResult(items)

            } catch (e: Exception) {
                onResult(emptyList())
            }
        }
    }

    // -------------------------------------------------------------
    // Tracks
    // -------------------------------------------------------------
    fun searchTracksItems(query: String, onResult: (List<SmartItem>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repo.searchTracks(query)
                }

                val tracks = response?.tracks?.items ?: emptyList()

                val sorted = tracks.sortedByDescending { it.popularity ?: 0 }

                val items = sorted.map { track ->
                    SmartItem(
                        title = track.name ?: "Без названия",
                        subtitle = track.artists?.joinToString { it.name ?: "" },
                        imageUrl = track.album?.images?.firstOrNull()?.url
                    )
                }

                onResult(items)

            } catch (e: Exception) {
                onResult(emptyList())
            }
        }
    }
}