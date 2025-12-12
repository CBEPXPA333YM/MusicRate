package com.example.api_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_test.data.deezerApi.ApiService.DeezerService
import com.example.api_test.ui.SmartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeezerViewModel(
    private val repo: DeezerService = DeezerService()
) : ViewModel() {

    // --------------------------------------------
    // ARTISTS
    // --------------------------------------------
    fun searchArtistsItems(query: String, onResult: (List<SmartItem>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repo.searchArtists(query)
                }

                val artists = response?.data ?: emptyList()

                val items = artists.map { ar ->
                    SmartItem(
                        title = ar.name,
                        subtitle = "Artist",
                        imageUrl = ar.picture_medium
                    )
                }

                onResult(items)

            } catch (e: Exception) {
                onResult(emptyList())
            }
        }
    }

    // --------------------------------------------
    // ALBUMS
    // --------------------------------------------
    fun searchAlbumsItems(query: String, onResult: (List<SmartItem>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repo.searchAlbums(query)
                }

                val albums = response?.data ?: emptyList()

                val items = albums.map { al ->
                    SmartItem(
                        title = al.title,
                        subtitle = al.artist?.name ?: "Unknown artist",
                        imageUrl = al.cover_medium
                    )
                }

                onResult(items)

            } catch (e: Exception) {
                onResult(emptyList())
            }
        }
    }

    // --------------------------------------------
    // TRACKS
    // --------------------------------------------
    fun searchTracksItems(query: String, onResult: (List<SmartItem>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repo.searchTracks(query)
                }

                val tracks = response?.data ?: emptyList()

                val items = tracks.map { tr ->
                    SmartItem(
                        title = tr.title,
                        subtitle = tr.artist?.name ?: "Unknown artist",
                        imageUrl = tr.album?.cover_medium,
                        previewUrl = tr.preview // 30 сек. превью Deezer
                    )
                }

                onResult(items)

            } catch (e: Exception) {
                onResult(emptyList())
            }
        }
    }
}