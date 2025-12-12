package com.example.api_test.data.spotifyApi.apiService

import android.util.Base64
import android.util.Log
import com.example.api_test.data.spotifyApi.apiModels.SpotifyAlbumSearchResponse
import com.example.api_test.data.spotifyApi.apiModels.SpotifyArtistSearchResponse
import com.example.api_test.data.spotifyApi.apiModels.SpotifySearchResponse
import com.example.api_test.data.spotifyApi.apiModels.SpotifyTokenResponse
import com.example.api_test.data.spotifyApi.apiModels.SpotifyTrackSearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

class SpotifyService(
    private val clientId: String,
    private val clientSecret: String
) {

    private val client = OkHttpClient()
    private val gson = Gson()

    @Volatile
    private var accessToken: String? = null
    private var tokenExpiresAt: Long = 0

    // ----------------------------------------------------------
    // Получение токена (Client Credentials)
    // ----------------------------------------------------------
    private suspend fun getAccessToken(): String =
        withContext(Dispatchers.IO) {

            val now = System.currentTimeMillis()

            if (accessToken != null && now < tokenExpiresAt) {
                return@withContext accessToken!!
            }

            val creds = "$clientId:$clientSecret"
            val basic = Base64.encodeToString(creds.toByteArray(), Base64.NO_WRAP)

            val body = FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build()

            val request = Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .addHeader("Authorization", "Basic $basic")
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            val json = response.body?.string()

            Log.d("Spotify", "← Token JSON: $json")

            val tokenResp = gson.fromJson(json, SpotifyTokenResponse::class.java)

            accessToken = tokenResp.access_token
            tokenExpiresAt = now + tokenResp.expires_in * 1000

            accessToken!!
        }

    // ----------------------------------------------------------
    // Низкоуровневый fetch (как у тебя в LastFmService)
    // ----------------------------------------------------------
    private suspend fun fetch(url: String): String? =
        withContext(Dispatchers.IO) {
            Log.d("Spotify", "→ GET $url")

            val token = getAccessToken()

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .build()

            val response = client.newCall(request).execute()

            Log.d("Spotify", "← Статус ответа: ${response.code}")

            val body = response.body?.string()
            Log.d("Spotify", "← Получено JSON: $body")

            body
        }

    // ----------------------------------------------------------
    // Поиск (artists, albums, tracks)
    // ----------------------------------------------------------
    suspend fun search(
        query: String,
        types: String = "artist,album,track",
        limit: Int = 20
    ): SpotifySearchResponse? {

        val encodedQuery = urlEncode(query)

        val url = "https://api.spotify.com/v1/search" +
                "?q=$encodedQuery&type=$types&limit=$limit"

        val json = fetch(url)

        return json?.let { gson.fromJson(it, SpotifySearchResponse::class.java) }
    }

    // ----------------------------------------------------------
    // Поиск только артистов
    // ----------------------------------------------------------
    suspend fun searchArtists(query: String, limit: Int = 20): SpotifyArtistSearchResponse? {
        val json = fetch(
            "https://api.spotify.com/v1/search" +
                    "?q=${urlEncode(query)}&type=artist&limit=$limit"
        )

        return json?.let { gson.fromJson(it, SpotifyArtistSearchResponse::class.java) }
    }

    // ----------------------------------------------------------
    // Поиск только альбомов

    // ----------------------------------------------------------
    suspend fun searchAlbums(query: String, limit: Int = 20): SpotifyAlbumSearchResponse? {
        val json = fetch(
            "https://api.spotify.com/v1/search" +
                    "?q=${urlEncode(query)}&type=album&limit=$limit"
        )

        return json?.let { gson.fromJson(it, SpotifyAlbumSearchResponse::class.java) }
    }

    // ----------------------------------------------------------
    // Поиск только треков
    // ----------------------------------------------------------
    suspend fun searchTracks(query: String, limit: Int = 20): SpotifyTrackSearchResponse? {
        val json = fetch(
            "https://api.spotify.com/v1/search" +
                    "?q=${urlEncode(query)}&type=track&limit=$limit"
        )

        return json?.let { gson.fromJson(it, SpotifyTrackSearchResponse::class.java) }
    }

    // ----------------------------------------------------------
    // URL encode — копия твоей функции
    // ----------------------------------------------------------
    private fun urlEncode(s: String): String =
        URLEncoder.encode(s, "UTF-8")
}