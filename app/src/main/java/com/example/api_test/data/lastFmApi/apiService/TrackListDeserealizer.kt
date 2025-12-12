package com.example.api_test.data.lastFmApi.apiService

import com.example.api_test.data.lastFmApi.apiModels.Track
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class TrackListDeserializer : JsonDeserializer<List<Track>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): List<Track> {
        return when {
            json.isJsonObject -> listOf(context.deserialize(json, Track::class.java))
            json.isJsonArray -> json.asJsonArray.map { context.deserialize<Track>(it, Track::class.java) }
            else -> emptyList()
        }
    }
}

