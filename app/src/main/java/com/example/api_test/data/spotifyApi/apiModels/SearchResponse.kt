package com.example.api_test.data.spotifyApi.apiModels

data class SpotifySearchResponse(
    val artists: SpotifyArtistSearchItems?,
    val albums: SpotifyAlbumSearchItems?,
    val tracks: SpotifyTrackSearchItems?
)

data class SpotifyArtistSearchItems(
    val items: List<SpotifyArtist>?
)

data class SpotifyAlbumSearchItems(
    val items: List<SpotifyAlbum>?
)

data class SpotifyTrackSearchItems(
    val items: List<SpotifyTrack>?
)

// --- Отдельные Search Методы

data class SpotifyArtistSearchResponse(
    val artists: SpotifyArtistSearchItems?
)

data class SpotifyAlbumSearchResponse(
    val albums: SpotifyAlbumSearchItems?
)

data class SpotifyTrackSearchResponse(
    val tracks: SpotifyTrackSearchItems?
)