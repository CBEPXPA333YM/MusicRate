package com.example.api_test.data.spotifyApi.apiModels


data class SpotifyArtist(
    val name: String,
    val popularity: Int,
    val followers: Followers,
    val images: List<SpotifyImage>
)

data class Followers(
    val total: Int
)