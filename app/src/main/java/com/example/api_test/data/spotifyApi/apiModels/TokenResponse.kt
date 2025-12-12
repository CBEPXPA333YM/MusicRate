package com.example.api_test.data.spotifyApi.apiModels

data class SpotifyTokenResponse (
    val access_token: String,
    val token_type: String,
    val expires_in: Long
)