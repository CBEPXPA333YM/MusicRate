package com.example.api_test.localdb.entity

import androidx.room.Entity
import com.example.api_test.ui.SmartType

@Entity(
    tableName = "favorites",
    primaryKeys = ["id", "type"]
)
data class FavoritesEntity(
    val id: Long,
    val type: SmartType,
    val title: String,
    val subtitle: String?,
    val imageUrl: String?
)