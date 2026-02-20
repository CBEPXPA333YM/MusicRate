package com.example.api_test.localdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insert(item: FavoritesEntity)

    @Query("DELETE FROM favorites WHERE id=:id AND type=:type")
    suspend fun delete(id: Long, type: SmartType): Int

    @Query("SELECT * FROM favorites")
    fun getAll(): Flow<List<FavoritesEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id=:id AND type=:type)")
    fun isFavorite(id: Long, type: SmartType): Flow<Boolean>
}