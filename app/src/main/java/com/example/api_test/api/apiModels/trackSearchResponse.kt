package com.example.api_test.api.apiModels

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
    val listeners: String? = null
)