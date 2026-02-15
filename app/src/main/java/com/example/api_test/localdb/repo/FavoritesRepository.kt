package com.example.api_test.localdb.repo

import com.example.api_test.localdb.dao.FavoritesDao
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(
    private val favoriteDao: FavoritesDao
) {

    // Получить все избранные
    val allFavorites: Flow<List<FavoritesEntity>> =
        favoriteDao.getAll()

    // Проверка избранного
    fun isFavorite(id: Long, type: SmartType): Flow<Boolean> =
        favoriteDao.isFavorite(id, type)

    // Добавить
    suspend fun insertFavorites(item: FavoritesEntity) {
        favoriteDao.insert(item)
    }

    // Удалить
    suspend fun deleteFavorites(item: FavoritesEntity) {
        favoriteDao.delete(item)
    }
}
