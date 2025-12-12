package com.example.api_test.data.deezerApi.apiModels

// ---------------------------------------
// DTO: Artist
// ---------------------------------------
data class DeezerArtist(
    val id: Long,
    val name: String,
    val picture_small: String?,
    val picture_medium: String?,
    val picture_big: String?
)

// ---------------------------------------
// DTO: Album
// ---------------------------------------
data class DeezerAlbum(
    val id: Long,
    val title: String,
    val cover_small: String?,
    val cover_medium: String?,
    val cover_big: String?,
    val artist: DeezerArtist?
)

// ---------------------------------------
// DTO: Track
// ---------------------------------------
data class DeezerTrack(
    val id: Long,
    val title: String,
    val preview: String?,      // 30-секундный превью
    val artist: DeezerArtist?,
    val album: DeezerAlbum?
)