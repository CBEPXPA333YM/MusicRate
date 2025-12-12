
package com.example.api_test.data.spotifyApi

import android.util.Base64
import android.util.Log
import com.example.api_test.data.spotifyApi.apiModels.SpotifyAlbumSearchResponse
import com.example.api_test.data.spotifyApi.apiModels.SpotifyArtistSearchResponse
import com.example.api_test.data.spotifyApi.apiModels.SpotifyTokenResponse
import com.example.api_test.data.spotifyApi.apiModels.SpotifyTrackSearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.IOException

class SpotifyRepository(
    private val clientId: String,
    private val clientSecret: String
) {

    private val client = OkHttpClient()
    private val gson = Gson()

    @Volatile
    private var cachedToken: String? = null

    @Volatile
    private var tokenExpiresAt: Long = 0L

    // -------------------------------------------------------------------------
    // TOKEN
    // -------------------------------------------------------------------------

    private suspend fun getToken(): String? {
        val now = System.currentTimeMillis()

        if (cachedToken != null && now < tokenExpiresAt) {
            return cachedToken
        }

        return fetchToken()
    }

    private suspend fun fetchToken(): String? = withContext(Dispatchers.IO) {

        val url = "https://accounts.spotify.com/api/token"

        val credentials = "$clientId:$clientSecret"
        val encoded = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        val body = RequestBody.Companion.create(
            "application/x-www-form-urlencoded".toMediaType(),
            "grant_type=client_credentials"
        )

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Basic $encoded")
            .post(body)
            .build()

        Log.d("Spotify", "→ Запрос TOKEN: $url")

        return@withContext try {
            val response = client.newCall(request).execute()

            val json = response.body?.string()
            Log.d("Spotify", "← Token Response: $json")

            if (!response.isSuccessful || json == null) return@withContext null

            val tokenResponse = gson.fromJson(json, SpotifyTokenResponse::class.java)

            cachedToken = tokenResponse.access_token
            tokenExpiresAt = System.currentTimeMillis() + tokenResponse.expires_in * 1000

            cachedToken
        } catch (e: IOException) {
            Log.e("Spotify", "Ошибка запроса токена", e)
            null
        }
    }

    // -------------------------------------------------------------------------
    // ВСПОМОГАТЕЛЬНЫЙ ЗАПРОС С АВТО TOKEN
    // -------------------------------------------------------------------------

    private suspend fun fetchJson(url: String): String? = withContext(Dispatchers.IO) {
        val token = getToken() ?: return@withContext null

        Log.d("Spotify", "→ GET: $url")

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val json = response.body?.string()

            Log.d("Spotify", "← Ответ: $json")

            json
        } catch (e: IOException) {
            Log.e("Spotify", "Ошибка запроса", e)
            null
        }
    }

    // -------------------------------------------------------------------------
    // SEARCH METHODS
    // -------------------------------------------------------------------------

    suspend fun searchArtists(q: String, limit: Int = 20): SpotifyArtistSearchResponse? {
        val url = "https://api.spotify.com/v1/search" +
                "?q=${q.trim()}" +
                "&type=artist" +
                "&limit=$limit"

        val json = fetchJson(url) ?: return null
        return gson.fromJson(json, SpotifyArtistSearchResponse::class.java)
    }

    suspend fun searchAlbums(q: String, limit: Int = 20): SpotifyAlbumSearchResponse? {
        val url = "https://api.spotify.com/v1/search" +
                "?q=${q.trim()}" +
                "&type=album" +
                "&limit=$limit"

        val json = fetchJson(url) ?: return null
        return gson.fromJson(json, SpotifyAlbumSearchResponse::class.java)
    }

    suspend fun searchTracks(q: String, limit: Int = 20): SpotifyTrackSearchResponse? {
        val url = "https://api.spotify.com/v1/search" +
                "?q=${q.trim()}" +
                "&type=track" +
                "&limit=$limit"

        val json = fetchJson(url) ?: return null
        return gson.fromJson(json, SpotifyTrackSearchResponse::class.java)
    }
}