package com.example.api_test.recommendationsApi

import com.example.api_test.ui.SmartItem

data class RecommendationsUiState(
    val isLoading: Boolean = false,
    val items: List<SmartItem> = emptyList(),
    val error: String? = null
)