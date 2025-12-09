package com.example.api_test.api.apiModels

data class ArtistSearchResponse(
    val results: ArtistResults
)

data class ArtistResults(
    val artistmatches: ArtistMatches
)

data class ArtistMatches(
    val artist: List<ArtistMini>
)

data class ArtistMini(
    val name: String,
    val listeners: String? = null
)