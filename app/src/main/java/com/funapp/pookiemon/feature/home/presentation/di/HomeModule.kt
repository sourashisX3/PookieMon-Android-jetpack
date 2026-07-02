package com.funapp.pookiemon.feature.home.presentation.di

import com.funapp.pookiemon.feature.home.data.repository.HomeRepositoryImpl
import com.funapp.pookiemon.feature.home.data.datasource.remote.PokeApiService
import com.funapp.pookiemon.feature.home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun providePokeApiService(retrofit: Retrofit): PokeApiService {
        return retrofit.create(PokeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(impl: HomeRepositoryImpl): HomeRepository {
        return impl
    }
}
