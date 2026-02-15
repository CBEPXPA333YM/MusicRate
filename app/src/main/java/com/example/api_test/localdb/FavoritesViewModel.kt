package com.example.api_test.localdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.localdb.repo.FavoritesRepository
import com.example.api_test.ui.SmartItem
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class FavoritesViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {

    val favorites: StateFlow<List<FavoritesEntity>> =
        repository.allFavorites
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

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
}
