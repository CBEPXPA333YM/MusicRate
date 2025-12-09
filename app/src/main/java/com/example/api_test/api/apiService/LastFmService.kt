package com.example.api_test.api.apiService

import android.util.Log
import com.example.api_test.api.apiModels.AlbumResponse
import com.example.api_test.api.apiModels.ArtistResponse
import com.example.api_test.api.apiModels.Track
import com.example.api_test.api.apiModels.TrackResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

class LastFmService(
    private val apiKey: String
) {

    private val client = OkHttpClient()
    private val gson = GsonBuilder()
        .registerTypeAdapter(
            object : TypeToken<List<Track>>() {}.type,
            TrackListDeserializer()
        )
        .create()


    // Старый fetch
    /*private suspend fun fetch(method: String, params: Map<String, String>): String? =
        withContext(Dispatchers.IO) {

            val encodedParams = params
                .map { (key, value) -> "$key=${urlEncode(value)}" }
                .joinToString("&")

            val url =
                "https://ws.audioscrobbler.com/2.0/?method=$method&$encodedParams&api_key=$apiKey&format=json"

            val request = Request.Builder().url(url).build()

            val response = client.newCall(request).execute()
            response.body?.string()
        } */

    private suspend fun fetch(method: String, params: Map<String, String>): String? =
        withContext(Dispatchers.IO) {
            val encodedParams = params
                .map { (key, value) -> "$key=${urlEncode(value)}" }
                .joinToString("&")
            val url =
                "https://ws.audioscrobbler.com/2.0/?method=$method&$encodedParams&api_key=$apiKey&format=json"
            Log.d("LastFm", "→ Отправляю запрос: $url")
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            Log.d("LastFm", "← Статус ответа: ${response.code}")
            val body = response.body?.string()
            Log.d("LastFm", "← Получено JSON: $body")
            body
        }



    suspend fun getArtistInfo(artist: String): ArtistResponse? {
        val json = fetch(
            method = "artist.getinfo",
            params = mapOf("artist" to artist)
        )
        return gson.fromJson(json, ArtistResponse::class.java)
    }

    suspend fun getAlbumInfo(artist: String, album: String): AlbumResponse? {
        val json = fetch(
            method = "album.getinfo",
            params = mapOf(
                "artist" to artist,
                "album" to album
            )
        )
        return gson.fromJson(json, AlbumResponse::class.java)
    }

    suspend fun getTrackInfo(artist: String, track: String): TrackResponse? {
        val json = fetch(
            method = "track.getinfo",
            params = mapOf(
                "artist" to artist,
                "track" to track
            )
        )
        return gson.fromJson(json, TrackResponse::class.java)
    }

    // --- Кодирование URL ---
    private fun urlEncode(s: String): String =
        URLEncoder.encode(s, "UTF-8")
}