package com.example.api_test.localdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.localdb.repo.FavoritesRepository
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class FavoritesViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {

    fun addFavorite(item: FavoritesEntity) {
        viewModelScope.launch {
            repository.insertFavorites(item)
        }
    }

    fun removeFavorite(item: FavoritesEntity) {
        viewModelScope.launch {
            repository.deleteFavorites(item)
        }
    }

    fun isFavorite(id: Long, type: SmartType): Flow<Boolean> {
        return repository.isFavorite(id, type)
    }

    fun getAllFavorites(): Flow<List<FavoritesEntity>> {
        return repository.getAllFavorites()
    }

}
