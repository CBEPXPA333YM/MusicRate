package com.example.api_test.lastFmApi.apiModels

import com.google.gson.annotations.SerializedName

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
    val listeners: String? = null,
    val image: List<ArtistImageDto>?
)

data class ArtistImageDto(
    @SerializedName("#text")
    val url: String?,
    val size: String?
)