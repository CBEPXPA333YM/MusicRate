package com.example.api_test.localdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.localdb.repo.FavoritesRepository
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
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

    fun getFavorite(id: Long, type: SmartType): Flow<FavoritesEntity?> {
        return repository.getFavorite(id, type)
    }

    fun updateRating(id: Long, type: SmartType, rating: Int) {
        ratingUpdates.tryEmit(Triple(id, type, rating))
    }

    private val ratingUpdates = MutableSharedFlow<Triple<Long, SmartType, Int>>(extraBufferCapacity = 1)

    init {
        viewModelScope.launch {
            ratingUpdates
                .debounce(400)
                .collect { (id, type, rating) ->
                    repository.updateRating(id, type, rating)
                }
        }
    }
}
