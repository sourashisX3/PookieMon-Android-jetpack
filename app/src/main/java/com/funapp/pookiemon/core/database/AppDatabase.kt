package com.funapp.pookiemon.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.funapp.pookiemon.feature.item.data.dao.ItemCacheDao
import com.funapp.pookiemon.feature.item.data.entity.CachedItemEntity
import com.funapp.pookiemon.feature.item.data.entity.CachedItemListEntity
import com.funapp.pookiemon.feature.move.data.dao.MoveCacheDao
import com.funapp.pookiemon.feature.move.data.entity.CachedMoveEntity
import com.funapp.pookiemon.feature.move.data.entity.CachedMoveListEntity
import com.funapp.pookiemon.feature.pokemon.data.dao.PokemonCacheDao
import com.funapp.pookiemon.feature.pokemon.data.entity.CachedPokemonEntity
import com.funapp.pookiemon.feature.pokemon.data.entity.CachedPokemonListEntity

@Database(
    entities = [CachedPokemonEntity::class, CachedPokemonListEntity::class, CachedItemEntity::class, CachedItemListEntity::class, CachedMoveEntity::class, CachedMoveListEntity::class],
    version = 4,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonCacheDao(): PokemonCacheDao
    abstract fun itemCacheDao(): ItemCacheDao
    abstract fun moveCacheDao(): MoveCacheDao
}
