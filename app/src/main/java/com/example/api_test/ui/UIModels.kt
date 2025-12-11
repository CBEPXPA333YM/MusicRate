package com.example.api_test.ui

data class AlbumInfoUi(
    val name: String,
    val coverUrl: String?,
    val trackCount: Int,
    val tracks: List<TrackUi>
)
data class TrackUi(
    val name: String,
    val url: String?
)

data class SmartItem(
    val title: String,
    val subtitle: String? = null,
    val imageUrl: String? = null
)