package com.example.api_test.data.spotifyApi.apiModels


data class SpotifyTrack(
    val name: String,
    val popularity: Int,
    val preview_url: String?,
    val album: SpotifyAlbumShort,
    val artists: List<SpotifyArtistShort>
)

data class SpotifyAlbumShort(
    val name: String,
    val images: List<SpotifyImage>
)