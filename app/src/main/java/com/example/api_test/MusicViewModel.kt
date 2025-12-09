package com.example.api_test
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_test.api.apiModels.Track
import com.example.api_test.api.apiService.LastFmService
import com.example.api_test.ui.AlbumInfoUi
import com.example.api_test.ui.TrackUi
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class MusicViewModel(
    private val repo: MusicRepository = MusicRepository(LastFmService("8bb705611e38d1e368dd0328499c53c2"))
) : ViewModel() {
    var result: String = ""
        private set

    fun searchAlbum(
        artist: String,
        album: String,
        onSuccess: (AlbumInfoUi) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val albumInfo = withContext(Dispatchers.IO) {
                    repo.getAlbum(artist, album)
                }
                if (albumInfo?.album == null) {
                    onError("Альбом не найден")
                    return@launch
                }
                val alb = albumInfo.album
                // Достаём обложку
                val cover = alb.image?.find { it.size == "extralarge" }?.url
                    ?: alb.image?.lastOrNull()?.url
                // Нормализуем треки
                val rawTracks = alb.tracks?.track
                val trackList = when (rawTracks) {
                    is List<*> -> rawTracks.filterIsInstance<Track>()
                    is Track -> listOf(rawTracks)
                    else -> emptyList()
                }
                // Создаём UI-модель
                val uiModel = AlbumInfoUi(
                    name = alb.name ?: "Без названия",
                    coverUrl = cover,
                    trackCount = trackList.size,
                    tracks = trackList.map {
                        TrackUi(
                            name = it.name ?: "Без имени",
                            url = it.url ?: ""
                        )
                    }
                )
                onSuccess(uiModel)
            } catch (e: Exception) {
                onError("Ошибка: ${e.message}")
            }
        }
    }
}