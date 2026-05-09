package com.example.api_test.recommendationsApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api_test.data.deezerApi.ApiService.DeezerService
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.localdb.dao.FavoritesDao

class RecommendationsViewModelFactory(
    private val repo: RecommendationsService,
    private val favoritesDao: FavoritesDao,
    private val deezerService: DeezerService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecommendationsViewModel(repo, deezerService, favoritesDao) as T
    }
}