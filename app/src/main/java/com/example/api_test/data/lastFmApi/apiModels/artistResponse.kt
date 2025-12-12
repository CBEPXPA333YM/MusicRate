package com.example.api_test.data.lastFmApi.apiModels

import com.google.gson.annotations.SerializedName

data class ArtistResponse(
    val artist: Artist?
)

data class Artist(
    val name: String?,
    val url: String?,
    val listeners: String?,
    val playcount: String?,
    val image: List<ArtistImage>?,
    val bio: ArtistBio?
)

data class ArtistImage(
    @SerializedName("#text")
    val url: String?,
    val size: String?
)

data class ArtistBio(
    val published: String?,
    val summary: String?,
    val content: String?
)
