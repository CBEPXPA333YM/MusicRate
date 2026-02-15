package com.example.api_test.localdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.api_test.localdb.entity.FavoriteEntity
import com.example.api_test.ui.SmartType
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(item: FavoriteEntity)

    @Delete
    suspend fun delete(item: FavoriteEntity)

    @Query("SELECT * FROM favorites")
    fun getAll(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id=:id AND type=:type)")
    fun isFavorite(id: Long, type: SmartType): Flow<Boolean>
}