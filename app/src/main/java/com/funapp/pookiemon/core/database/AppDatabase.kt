package com.funapp.pookiemon.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.funapp.pookiemon.core.database.entity.CachedPokemonEntity
import com.funapp.pookiemon.core.database.entity.CachedPokemonListEntity

@Database(
    entities = [CachedPokemonEntity::class, CachedPokemonListEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonCacheDao(): PokemonCacheDao
}
