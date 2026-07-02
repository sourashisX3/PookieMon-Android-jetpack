package com.funapp.pookiemon.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.funapp.pookiemon.core.database.entity.CachedPokemonEntity
import com.funapp.pookiemon.core.database.entity.CachedPokemonListEntity

@Dao
interface PokemonCacheDao {

    @Upsert
    suspend fun upsertPokemon(pokemon: CachedPokemonEntity)

    @Upsert
    suspend fun upsertPokemonList(list: CachedPokemonListEntity)

    @Query("SELECT * FROM cached_pokemon WHERE id = :id")
    suspend fun getPokemon(id: Int): CachedPokemonEntity?

    @Query("SELECT * FROM cached_pokemon_list WHERE `offset` = :offset")
    suspend fun getPokemonList(offset: Int): CachedPokemonListEntity?

    @Query("DELETE FROM cached_pokemon WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredPokemon(expiryThreshold: Long)

    @Query("DELETE FROM cached_pokemon_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredPokemonList(expiryThreshold: Long)

    @Query("DELETE FROM cached_pokemon")
    suspend fun clearAllPokemon()

    @Query("DELETE FROM cached_pokemon_list")
    suspend fun clearAllPokemonList()
}
