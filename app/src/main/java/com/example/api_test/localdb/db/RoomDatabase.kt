package com.example.api_test.localdb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.api_test.localdb.dao.FavoriteDao
import com.example.api_test.localdb.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}