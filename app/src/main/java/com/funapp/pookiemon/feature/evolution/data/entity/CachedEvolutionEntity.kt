package com.funapp.pookiemon.feature.evolution.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_evolution")
data class CachedEvolutionEntity(
    @PrimaryKey val speciesId: Int,
    @ColumnInfo(name = "chain_json") val chainJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)
