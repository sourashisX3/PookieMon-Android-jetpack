package com.funapp.pookiemon.feature.pokemon.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_pokemon")
data class CachedPokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "pokemon_json") val pokemonJson: String,
    @ColumnInfo(name = "species_json") val speciesJson: String?,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "cached_pokemon_list")
data class CachedPokemonListEntity(
    @PrimaryKey val offset: Int,
    @ColumnInfo(name = "list_json") val listJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)
