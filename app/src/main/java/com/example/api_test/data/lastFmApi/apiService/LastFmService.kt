package com.example.api_test.data.lastFmApi.apiService

import android.util.Log
import com.example.api_test.data.lastFmApi.apiModels.AlbumResponse
import com.example.api_test.data.lastFmApi.apiModels.AlbumSearchResponse
import com.example.api_test.data.lastFmApi.apiModels.ArtistResponse
import com.example.api_test.data.lastFmApi.apiModels.ArtistSearchResponse
import com.example.api_test.data.lastFmApi.apiModels.Track
import com.example.api_test.data.lastFmApi.apiModels.TrackResponse
import com.example.api_test.data.lastFmApi.apiModels.TrackSearchResponse
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
            object : com.google.gson.reflect.TypeToken<List<Track>>() {}.type,
            TrackListDeserializer()
        )
        .create()

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

    // --- Функции умного поиска ---

    suspend fun searchArtists(query: String, limit: Int = 20): ArtistSearchResponse? {
        val json = fetch(
            method = "artist.search",
            params = mapOf(
                "artist" to query,
                "limit" to limit.toString()
            )
        )
        return json?.let { gson.fromJson(it, ArtistSearchResponse::class.java) }

    }

    suspend fun searchAlbums(query: String, limit: Int = 20): AlbumSearchResponse? {
        val json = fetch(
            method = "album.search",
            params = mapOf(
                "album" to query,
                "limit" to limit.toString()
            )
        )
        return json?.let { gson.fromJson(it, AlbumSearchResponse::class.java) }
    }

    suspend fun searchTracks(query: String, limit: Int = 20): TrackSearchResponse? {
        val json = fetch(
            method = "track.search",
            params = mapOf(
                "track" to query,
                "limit" to limit.toString()
            )
        )
        Log.d("LastFm", "← Получено JSON: $json")
        return json?.let { gson.fromJson(it, TrackSearchResponse::class.java) }
    }
}