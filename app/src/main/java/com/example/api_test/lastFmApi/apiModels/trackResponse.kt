package com.example.api_test.lastFmApi.apiModels

data class TrackResponse(
    val track: TrackInfo?
)

data class TrackInfo(
    val name: String?,
    val artist: SimpleArtist?,
    val album: SimpleAlbum?,
    val url: String?,
    val duration: String?,
    val listeners: String?,
    val playcount: String?,
    val wiki: TrackWiki?
)

data class SimpleArtist(
    val name: String?,
    val url: String?
)

data class SimpleAlbum(
    val title: String?,
    val url: String?
)

data class TrackWiki(
    val published: String?,
    val summary: String?,
    val content: String?
)
