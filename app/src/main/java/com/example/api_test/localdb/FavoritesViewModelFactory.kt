package com.example.api_test.localdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api_test.localdb.repo.FavoritesRepository

class FavoritesViewModelFactory(
    private val repository: FavoritesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}