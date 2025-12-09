package com.example.api_test.api.apiModels

data class AlbumSearchResponse(
    val results: AlbumResultsSearch
)

data class AlbumResultsSearch(
    val albummatches: AlbumMatches
)

data class AlbumMatches(
    val album: List<AlbumMini>
)

data class AlbumMini(
    val name: String,
    val artist: String
)