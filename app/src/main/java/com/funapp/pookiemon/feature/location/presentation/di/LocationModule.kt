package com.funapp.pookiemon.feature.location.presentation.di

import com.funapp.pookiemon.feature.location.data.datasource.local.LocationLocalDataSource
import com.funapp.pookiemon.feature.location.data.datasource.remote.LocationApiService
import com.funapp.pookiemon.feature.location.data.datasource.remote.LocationRemoteDataSource
import com.funapp.pookiemon.feature.location.data.repository.LocationRepositoryImpl
import com.funapp.pookiemon.feature.location.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationApiService(retrofit: retrofit2.Retrofit): LocationApiService = retrofit.create(LocationApiService::class.java)

    @Provides
    @Singleton
    fun provideLocationRepository(impl: LocationRepositoryImpl): LocationRepository = impl
}
