package com.example.api_test.data.lastFmApi.apiModels

import com.google.gson.annotations.SerializedName

data class TrackSearchResponse(
    val results: TrackResults
)

data class TrackResults(
    val trackmatches: TrackMatches,
)


data class TrackMatches(
    val track: List<TrackMini>
)

data class TrackMini(
    val name: String,
    val artist: String,
    val listeners: String? = null,
    val image: List<TrackImageDto>?
)

data class TrackImageDto(
    @SerializedName("#text")
    val url: String?,
    val size: String?
)