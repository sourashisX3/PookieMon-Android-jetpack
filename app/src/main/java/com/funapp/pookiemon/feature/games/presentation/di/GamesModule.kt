package com.funapp.pookiemon.feature.games.presentation.di

import com.funapp.pookiemon.feature.games.data.datasource.remote.GamesApiService
import com.funapp.pookiemon.feature.games.data.repository.GamesRepositoryImpl
import com.funapp.pookiemon.feature.games.domain.repository.GamesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GamesModule {

    @Provides
    @Singleton
    fun provideGamesApiService(retrofit: Retrofit): GamesApiService {
        return retrofit.create(GamesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGamesRepository(impl: GamesRepositoryImpl): GamesRepository = impl
}
