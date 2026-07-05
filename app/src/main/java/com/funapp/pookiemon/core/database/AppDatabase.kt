package com.funapp.pookiemon.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.funapp.pookiemon.feature.berry.data.dao.BerryCacheDao
import com.funapp.pookiemon.feature.berry.data.entity.CachedBerryEntity
import com.funapp.pookiemon.feature.berry.data.entity.CachedBerryListEntity
import com.funapp.pookiemon.feature.encounter.data.dao.EncounterCacheDao
import com.funapp.pookiemon.feature.encounter.data.entity.CachedEncounterMethodEntity
import com.funapp.pookiemon.feature.encounter.data.entity.CachedEncounterMethodListEntity
import com.funapp.pookiemon.feature.evolution.data.dao.EvolutionCacheDao
import com.funapp.pookiemon.feature.evolution.data.entity.CachedEvolutionEntity
import com.funapp.pookiemon.feature.games.data.dao.GamesCacheDao
import com.funapp.pookiemon.feature.games.data.entity.CachedGenerationEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedGenerationListEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedPokedexEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedPokedexListEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedVersionEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedVersionListEntity
import com.funapp.pookiemon.feature.item.data.dao.ItemCacheDao
import com.funapp.pookiemon.feature.item.data.entity.CachedItemEntity
import com.funapp.pookiemon.feature.item.data.entity.CachedItemListEntity
import com.funapp.pookiemon.feature.location.data.dao.LocationCacheDao
import com.funapp.pookiemon.feature.location.data.entity.CachedLocationEntity
import com.funapp.pookiemon.feature.location.data.entity.CachedLocationListEntity
import com.funapp.pookiemon.feature.move.data.dao.MoveCacheDao
import com.funapp.pookiemon.feature.move.data.entity.CachedMoveEntity
import com.funapp.pookiemon.feature.move.data.entity.CachedMoveListEntity
import com.funapp.pookiemon.feature.pokemon.data.dao.PokemonCacheDao
import com.funapp.pookiemon.feature.pokemon.data.entity.CachedPokemonEntity
import com.funapp.pookiemon.feature.pokemon.data.entity.CachedPokemonListEntity
import com.funapp.pookiemon.feature.references.data.dao.ReferenceCacheDao
import com.funapp.pookiemon.feature.references.data.entity.CachedAbilityEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedAbilityListEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedNatureEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedNatureListEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedTypeEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedTypeListEntity

@Database(
    entities = [
        CachedPokemonEntity::class, CachedPokemonListEntity::class,
        CachedItemEntity::class, CachedItemListEntity::class,
        CachedMoveEntity::class, CachedMoveListEntity::class,
        CachedBerryEntity::class, CachedBerryListEntity::class,
        CachedEncounterMethodEntity::class, CachedEncounterMethodListEntity::class,
        CachedEvolutionEntity::class,
        CachedGenerationEntity::class, CachedGenerationListEntity::class,
        CachedVersionEntity::class, CachedVersionListEntity::class,
        CachedPokedexEntity::class, CachedPokedexListEntity::class,
        CachedLocationEntity::class, CachedLocationListEntity::class,
        CachedAbilityEntity::class, CachedAbilityListEntity::class,
        CachedTypeEntity::class, CachedTypeListEntity::class,
        CachedNatureEntity::class, CachedNatureListEntity::class,
    ],
    version = 5,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonCacheDao(): PokemonCacheDao
    abstract fun itemCacheDao(): ItemCacheDao
    abstract fun moveCacheDao(): MoveCacheDao
    abstract fun berryCacheDao(): BerryCacheDao
    abstract fun encounterCacheDao(): EncounterCacheDao
    abstract fun evolutionCacheDao(): EvolutionCacheDao
    abstract fun gamesCacheDao(): GamesCacheDao
    abstract fun locationCacheDao(): LocationCacheDao
    abstract fun referenceCacheDao(): ReferenceCacheDao
}
