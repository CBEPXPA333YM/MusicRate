package com.example.api_test.data.spotifyApi.apiModels

data class SpotifyAlbum(
    val name: String,
    val release_date: String,
    val total_tracks: Int,
    val images: List<SpotifyImage>,
    val artists: List<SpotifyArtistShort>
)

data class SpotifyArtistShort(
    val name: String
)