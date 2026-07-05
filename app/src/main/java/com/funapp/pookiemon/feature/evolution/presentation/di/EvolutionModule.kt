package com.funapp.pookiemon.feature.evolution.presentation.di

import com.funapp.pookiemon.feature.evolution.data.datasource.remote.EvolutionApiService
import com.funapp.pookiemon.feature.evolution.data.repository.EvolutionRepositoryImpl
import com.funapp.pookiemon.feature.evolution.domain.repository.EvolutionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EvolutionModule {

    @Provides
    @Singleton
    fun provideEvolutionApiService(retrofit: Retrofit): EvolutionApiService {
        return retrofit.create(EvolutionApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEvolutionRepository(impl: EvolutionRepositoryImpl): EvolutionRepository = impl
}
