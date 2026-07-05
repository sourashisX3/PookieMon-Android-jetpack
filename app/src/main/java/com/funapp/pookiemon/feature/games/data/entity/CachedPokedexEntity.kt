package com.funapp.pookiemon.feature.games.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_pokedex")
data class CachedPokedexEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "pokedex_json") val pokedexJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "cached_pokedex_list")
data class CachedPokedexListEntity(
    @PrimaryKey val offset: Int,
    @ColumnInfo(name = "list_json") val listJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)
