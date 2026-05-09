package com.example.api_test.recommendationsApi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_test.data.deezerApi.ApiService.DeezerService
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.localdb.dao.FavoritesDao
import com.example.api_test.ui.SmartItem
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class RecommendationsViewModel(
    private val repo: RecommendationsService = RecommendationsService(),
    private val deezerService: DeezerService = DeezerService(),
    private val favoritesDao: FavoritesDao
) : ViewModel() {

    private val _state = MutableStateFlow(RecommendationsUiState())
    val state: StateFlow<RecommendationsUiState> = _state

    fun loadRecommendations() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                Log.d("RECS_VM", "ЗАПУСТИЛОСЬ")
                // 1. берем favorites из Room
                val favorites = favoritesDao.getAll().first()

                // 2. готовим input
                val tracks = favorites
                    .filter { it.type == SmartType.TRACK && it.rating != null }
                    .map {
                        TrackInput(
                            track_name = it.title,
                            artist_name = it.subtitle,
                            rating = it.rating ?: 0
                        )
                    }

                if (tracks.isEmpty()) {
                    _state.value = RecommendationsUiState(
                        isLoading = false,
                        items = emptyList()
                    )
                    return@launch
                }

                // 3. API call
                val response = withContext(Dispatchers.IO) {
                    repo.getRecommendations(tracks)
                }
                Log.d("RECS_VM", "ЗАПУСТИЛОСЬ2")
                // 4. map → SmartItem
                val items = response?.let { recommendations ->

                    coroutineScope {

                        recommendations.map { rec ->

                            async(Dispatchers.IO) {

                                try {

                                    val query =
                                        "${rec.track_name} ${rec.track_artist}"

                                    val deezerResults =
                                        deezerService.searchTracks(query)

                                    val first = deezerResults?.data?.firstOrNull()

                                    SmartItem(
                                        id = first?.id ?: 0,
                                        type = SmartType.TRACK,
                                        title = first?.title ?: rec.track_name,
                                        subtitle = first?.artist?.name ?: rec.track_artist,
                                        imageUrl = first?.album?.cover_medium
                                    )

                                } catch (e: Exception) {

                                    Log.e(
                                        "RECS_VM",
                                        "DEEZER SEARCH FAILED",
                                        e
                                    )

                                    SmartItem(
                                        id = 0,
                                        type = SmartType.TRACK,
                                        title = rec.track_name,
                                        subtitle = rec.track_artist,
                                        imageUrl = null
                                    )
                                }
                            }

                        }.awaitAll()

                    }

                } ?: emptyList() ?: emptyList()
                Log.d("RECS_VM", "ЗАПУСТИЛОСЬ3")
                Log.d("RECS_VM", "Loaded items: ${items.size}")

                // 5. update state
                _state.value = RecommendationsUiState(
                    isLoading = false,
                    items = items
                )

            } catch (e: Exception) {
                Log.e("RECS_VM", "❌ API FAILED", e)

                _state.value = RecommendationsUiState(
                    isLoading = false,
                    items = emptyList(),
                    error = e.message
                )
            }
        }
    }
}