package com.example.api_test.recommendationsApi

import android.util.Log
import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class RecommendationsService {

    private val client = OkHttpClient()
    private val gson = GsonBuilder().create()

    private suspend fun post(url: String, jsonBody: String): String? =
        withContext(Dispatchers.IO) {

            Log.d("RECS_API", "==============================")
            Log.d("RECS_API", "➡️ URL: $url")
            Log.d("RECS_API", "➡️ REQUEST JSON:\n$jsonBody")

            try {

                val body = jsonBody
                    .toRequestBody("application/json".toMediaType())

                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()

                Log.d("RECS_API", "⬅️ HTTP CODE: ${response.code}")

                val responseBody = response.body?.string()

                Log.d("RECS_API", "⬅️ RESPONSE RAW:\n$responseBody")

                if (!response.isSuccessful) {
                    Log.e("RECS_API", "❌ Request failed")
                } else {
                    Log.d("RECS_API", "✅ Request success")
                }

                responseBody

            } catch (e: Exception) {

                Log.e("RECS_API", "❌ NETWORK ERROR", e)
                null
            }
        }

    fun getRecommendations(
        tracks: List<TrackInput>,
        topK: Int = 10
    ): List<RecommendationItem>? {

        val requestObj = RecommendationRequest(
            tracks = tracks,
            top_k = topK
        )

        val json = gson.toJson(requestObj)

        val response = runBlocking {
            post(
                "http://192.168.0.109:5000/api/recommend-weighted",
                json
            )
        }

        Log.d("RECS_REPO", "RAW REQUEST JSON: $json")

        return response?.let {
            parseRecommendations(it)
        }
    }

    private fun parseRecommendations(json: String): List<RecommendationItem> {
        val type = object : TypeToken<List<RecommendationItem>>() {}.type
        return gson.fromJson(json, type)
    }
}