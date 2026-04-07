package com.example.api_test.recommendationsApi

data class RecommendationRequest(
    val tracks: List<TrackInput>,
    val top_k: Int
)

data class TrackInput(
    val artist_name: String?,
    val track_name: String,
    val rating: Int
)


typealias RecommendationsResponse = List<RecommendationItem>

data class RecommendationItem(
    val track_artist: String,
    val track_name: String,
    val track_popularity: Int
)