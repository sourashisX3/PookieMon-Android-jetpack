package com.funapp.pookiemon.feature.games.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_generation")
data class CachedGenerationEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "generation_json") val generationJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "cached_generation_list")
data class CachedGenerationListEntity(
    @PrimaryKey val offset: Int,
    @ColumnInfo(name = "list_json") val listJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)
