package com.funapp.pookiemon.core.di

import android.content.Context
import androidx.room.Room
import com.funapp.pookiemon.core.database.AppDatabase
import com.funapp.pookiemon.feature.item.data.dao.ItemCacheDao
import com.funapp.pookiemon.feature.move.data.dao.MoveCacheDao
import com.funapp.pookiemon.feature.pokemon.data.dao.PokemonCacheDao
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
}
