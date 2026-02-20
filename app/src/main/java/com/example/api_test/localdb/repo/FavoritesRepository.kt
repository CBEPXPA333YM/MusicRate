package com.example.api_test.localdb.repo

import android.util.Log
import com.example.api_test.localdb.dao.FavoritesDao
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(
    private val favoriteDao: FavoritesDao
) {

    // Проверка избранного
    fun isFavorite(id: Long, type: SmartType): Flow<Boolean> =
        favoriteDao.isFavorite(id, type)

    // Добавить
    suspend fun insertFavorites(item: FavoritesEntity) {
        favoriteDao.insert(item)
    }

    // Удалить
    suspend fun deleteFavorites(item: FavoritesEntity) {
        favoriteDao.delete(item.id, item.type)
        Log.d("FAV", "Repository: deleting id=${item.id}, type=${item.type}, title=${item.title}")
        val rowsDeleted = favoriteDao.delete(item.id, item.type)
        Log.d("FAV", "Repository: rowsDeleted = $rowsDeleted")
    }

    fun getAllFavorites():  Flow<List<FavoritesEntity>> = favoriteDao.getAll()
}
