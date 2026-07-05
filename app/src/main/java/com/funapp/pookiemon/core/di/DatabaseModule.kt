package com.funapp.pookiemon.core.di

import android.content.Context
import androidx.room.Room
import com.funapp.pookiemon.core.database.AppDatabase
import com.funapp.pookiemon.feature.berry.data.dao.BerryCacheDao
import com.funapp.pookiemon.feature.encounter.data.dao.EncounterCacheDao
import com.funapp.pookiemon.feature.evolution.data.dao.EvolutionCacheDao
import com.funapp.pookiemon.feature.games.data.dao.GamesCacheDao
import com.funapp.pookiemon.feature.item.data.dao.ItemCacheDao
import com.funapp.pookiemon.feature.location.data.dao.LocationCacheDao
import com.funapp.pookiemon.feature.move.data.dao.MoveCacheDao
import com.funapp.pookiemon.feature.pokemon.data.dao.PokemonCacheDao
import com.funapp.pookiemon.feature.references.data.dao.ReferenceCacheDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pookiemon_database",
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonCacheDao(database: AppDatabase): PokemonCacheDao {
        return database.pokemonCacheDao()
    }

    @Provides
    @Singleton
    fun provideItemCacheDao(database: AppDatabase): ItemCacheDao {
        return database.itemCacheDao()
    }

    @Provides
    @Singleton
    fun provideMoveCacheDao(database: AppDatabase): MoveCacheDao {
        return database.moveCacheDao()
    }

    @Provides
    @Singleton
    fun provideBerryCacheDao(database: AppDatabase): BerryCacheDao {
        return database.berryCacheDao()
    }

    @Provides
    @Singleton
    fun provideEncounterCacheDao(database: AppDatabase): EncounterCacheDao {
        return database.encounterCacheDao()
    }

    @Provides
    @Singleton
    fun provideEvolutionCacheDao(database: AppDatabase): EvolutionCacheDao {
        return database.evolutionCacheDao()
    }

    @Provides
    @Singleton
    fun provideGamesCacheDao(database: AppDatabase): GamesCacheDao {
        return database.gamesCacheDao()
    }

    @Provides
    @Singleton
    fun provideLocationCacheDao(database: AppDatabase): LocationCacheDao {
        return database.locationCacheDao()
    }

    @Provides
    @Singleton
    fun provideReferenceCacheDao(database: AppDatabase): ReferenceCacheDao {
        return database.referenceCacheDao()
    }
}
