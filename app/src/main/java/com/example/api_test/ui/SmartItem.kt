package com.example.api_test.ui

data class SmartItem(
    val type: SmartType,
    val id: Long,
    val title: String,
    val subtitle: String? = null,
    val imageUrl: String? = null,

    //для Track
    val previewUrl: String? = null,
    val artistId: Long? = null,
    val artistName: String? = null,
    val albumId: Long? = null,
    val albumTitle: String? = null
)