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

    // СТАРЫЙ ПОИСК
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

    /** Основной результат поиска (строки для UI) */
    var smartResults: List<String> = emptyList()
        private set

    /** Ошибки */
    var errorMessage: String? = null
        private set

    /** ----------------------------
     *  Умный поиск исполнителей
     * ---------------------------- */
    fun searchArtists(query: String, onResult: (List<String>) -> Unit) {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    repo.api.searchArtists(query)
                }
                val artists = data
                    ?.results
                    ?.artistmatches
                    ?.artist
                    ?: emptyList()

                val sortedArtists = artists.sortedByDescending { it.listeners?.toIntOrNull() ?: 0}
                val artistStrings = sortedArtists.mapNotNull { artist ->
                    if (artist.name != null) {
                        "${artist.name}, слушателей: ${artist.listeners}"
                    } else null
                }
                smartResults = artistStrings
                onResult(smartResults)



            } catch (e: Exception) {
                errorMessage = e.message
                onResult(emptyList())
            }
        }
    }

    /** ----------------------------
     *  Умный поиск альбомов
     * ---------------------------- */
    fun searchAlbums(query: String, onResult: (List<String>) -> Unit) {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    repo.api.searchAlbums(query)
                }
                val albums = data
                    ?.results
                    ?.albummatches
                    ?.album
                    ?.mapNotNull { "${it.artist} — ${it.name}" }
                    ?: emptyList()

                smartResults = albums
                onResult(albums)

            } catch (e: Exception) {
                errorMessage = e.message
                onResult(emptyList())
            }
        }
    }

    /** ----------------------------
     *  Умный поиск треков
     * ---------------------------- */
    fun searchTracks(query: String, onResult: (List<String>) -> Unit) {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    repo.api.searchTracks(query)
                }
                val tracks = data
                    ?.results
                    ?.trackmatches
                    ?.track
                    ?: emptyList()
                val sortedTracks = tracks.sortedByDescending { it.listeners?.toIntOrNull() ?: 0}
                val trackStrings = sortedTracks.mapNotNull { track ->
                    if (track.name != null && track.artist != null) {
                        "${track.artist} — ${track.name}, прослушиваний: ${track.listeners}"
                    } else null
                }
                smartResults = trackStrings
                onResult(smartResults)

            } catch (e: Exception) {
                errorMessage = e.message
                onResult(emptyList())
            }
        }
    }
}