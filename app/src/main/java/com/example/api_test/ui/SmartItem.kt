package com.example.api_test.ui

enum class SmartType{
    ARTIST,
    ALBUM,
    TRACK
}

data class SmartItem(
    val type: SmartType,
    val id: String? = null,
    val title: String,
    val subtitle: String? = null,
    val imageUrl: String? = null,
    val previewUrl: String? = null
)