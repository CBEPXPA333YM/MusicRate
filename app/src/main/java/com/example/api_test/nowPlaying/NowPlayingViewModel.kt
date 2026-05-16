package com.example.api_test.nowPlaying

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.media.MediaMetadata
import android.media.session.MediaSessionManager
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_test.ui.SmartItem
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NowPlayingViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application

    private val _tracks = MutableStateFlow<List<SmartItem>>(emptyList())
    val tracks: StateFlow<List<SmartItem>> = _tracks

    init {
        viewModelScope.launch {
            while (true) {
                val newTrack = getNowPlaying()

                newTrack?.let { track ->
                    _tracks.update { current ->

                        val isSame =
                            current.firstOrNull()?.title == track.title &&
                                    current.firstOrNull()?.subtitle == track.subtitle

                        if (isSame) {
                            current
                        } else {
                            listOf(track) + current.take(19)
                        }
                    }
                }

                delay(5000)
            }
        }
    }

    private fun getNowPlaying(): SmartItem? {

        val enabled = NotificationManagerCompat
            .getEnabledListenerPackages(context)
            .contains(context.packageName)

        if (!enabled) return null

        val manager = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
        val component = ComponentName(context, MediaNotificationListener::class.java)

        val sessions = try {
            manager.getActiveSessions(component)
        } catch (e: SecurityException) {
            return null
        }

        val controller = sessions.firstOrNull() ?: return null
        val metadata = controller.metadata ?: return null

        val artwork = metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)
            ?: metadata.getBitmap(MediaMetadata.METADATA_KEY_ART)

        val track_title = metadata.getString(MediaMetadata.METADATA_KEY_TITLE)

        if (track_title.isNullOrBlank()) return null

        return SmartItem(
            id = (metadata.getString(MediaMetadata.METADATA_KEY_TITLE)
                    + metadata.getString(MediaMetadata.METADATA_KEY_ARTIST)).hashCode().toLong(),
            type = SmartType.TRACK,
            title = track_title,
            subtitle = metadata.getString(MediaMetadata.METADATA_KEY_ARTIST) ?: "тут ошибка",
            imageUrl = null,
            rating = null,
            artwork = artwork
        )
    }
}