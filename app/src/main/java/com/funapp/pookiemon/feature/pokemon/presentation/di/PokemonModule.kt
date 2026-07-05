package com.funapp.pookiemon.feature.pokemon.presentation.di

import com.funapp.pookiemon.feature.pokemon.data.repository.PokemonRepositoryImpl
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokemonApiService
import com.funapp.pookiemon.feature.pokemon.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {

    @Provides
    @Singleton
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository {
        return impl
    }
}
