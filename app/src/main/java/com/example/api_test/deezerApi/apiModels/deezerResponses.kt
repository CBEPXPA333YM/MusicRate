package com.example.api_test.data.deezerApi.apiModels

// Deezer всегда возвращает массив data + total

data class DeezerArtistSearchResponse(
    val data: List<DeezerArtist>?,
    val total: Int
)

data class DeezerAlbumSearchResponse(
    val data: List<DeezerAlbum>?,
    val total: Int
)

data class DeezerTrackSearchResponse(
    val data: List<DeezerTrack>?,
    val total: Int
)