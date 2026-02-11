package com.example.api_test.localdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_test.ui.SmartItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*
class FavoritesViewModel(
    private val repo: FavoritesRepository
) : ViewModel() {

    val favorites = repo.favorites.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    fun add(item: SmartItem) = viewModelScope.launch {
        repo.add(item)
    }

    fun remove(item: SmartItem) = viewModelScope.launch {
        repo.remove(item)
    }
}*/
