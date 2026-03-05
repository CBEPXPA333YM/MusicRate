package com.example.api_test.nowPlaying

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadata
import android.media.session.MediaSessionManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.api_test.deezerApi.DeezerViewModel
import com.example.api_test.ui.SmartItem
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.io.FileOutputStream

class NowPlayingViewModel(application: Application) : AndroidViewModel(application) {

    private val _track = MutableStateFlow<SmartItem?>(null)
    val track: StateFlow<SmartItem?> = _track

    fun loadNowPlaying() {
        val context = getApplication<Application>()

        // Проверяем, включен ли доступ к уведомлениям
        val enabled = androidx.core.app.NotificationManagerCompat.getEnabledListenerPackages(context)
            .contains(context.packageName)

        if (!enabled) {
            Log.e("NOWPLAYING", "Notification access NOT granted")
            _track.value = null
            return
        }

        val manager = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
        val component = ComponentName(context, MediaNotificationListener::class.java)

        // Получаем активные сессии безопасно
        val sessions = try {
            manager.getActiveSessions(component)
        } catch (e: SecurityException) {
            Log.e("NOWPLAYING", "No notification access")
            return
        }

        val controller = sessions.firstOrNull() ?: return
        val metadata = controller.metadata ?: return


        // Создаём SmartItem
        val item = SmartItem(
            id = 0, // временно
            type = SmartType.TRACK,
            title = metadata.getString(MediaMetadata.METADATA_KEY_TITLE) ?: "Unknown",
            subtitle = metadata.getString(MediaMetadata.METADATA_KEY_ARTIST) ?: "",
            imageUrl = null,
            rating = null
        )

        _track.value = item
    }

}