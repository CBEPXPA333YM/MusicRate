package com.example.api_test.api.apiModels

import com.google.gson.annotations.SerializedName

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
    val artist: String,
    val image: List<AlbumImageDto>?
)

data class AlbumImageDto(
    @SerializedName("#text")
    val url: String?,
    val size: String?
)
