package com.example.api_test.data.deezerApi.ApiService

import android.util.Log
import com.example.api_test.data.deezerApi.apiModels.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class DeezerService {

    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder().create()

    // -----------------------------
    //   GENERIC REQUEST
    // -----------------------------
    private suspend fun fetch(url: String): String? =
        withContext(Dispatchers.IO) {
            Log.d("Deezer", "→ Request: $url")

            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            Log.d("Deezer", "← Status: ${response.code}")

            val body = response.body?.string()
            Log.d("Deezer", "← JSON: $body")

            body
        }

    // -----------------------------
    //   SEARCH ARTIST
    // -----------------------------
    suspend fun searchArtists(query: String, limit: Int = 20): DeezerArtistSearchResponse? {
        val url = "https://api.deezer.com/search/artist?q=$query&limit=$limit"
        val json = fetch(url)
        return json?.let { gson.fromJson(it, DeezerArtistSearchResponse::class.java) }
    }

    // -----------------------------
    //   SEARCH ALBUM
    // -----------------------------
    suspend fun searchAlbums(query: String, limit: Int = 20): DeezerAlbumSearchResponse? {
        val url = "https://api.deezer.com/search/album?q=$query&limit=$limit"
        val json = fetch(url)
        return json?.let { gson.fromJson(it, DeezerAlbumSearchResponse::class.java) }
    }

    // -----------------------------
    //   SEARCH TRACK
    // -----------------------------
    suspend fun searchTracks(query: String, limit: Int = 20): DeezerTrackSearchResponse? {
        val url = "https://api.deezer.com/search/track?q=$query&limit=$limit"
        val json = fetch(url)
        return json?.let { gson.fromJson(it, DeezerTrackSearchResponse::class.java) }
    }


}