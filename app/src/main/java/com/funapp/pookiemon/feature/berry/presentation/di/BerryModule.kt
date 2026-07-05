package com.funapp.pookiemon.feature.berry.presentation.di

import com.funapp.pookiemon.feature.berry.data.datasource.local.BerryLocalDataSource
import com.funapp.pookiemon.feature.berry.data.datasource.remote.BerryApiService
import com.funapp.pookiemon.feature.berry.data.datasource.remote.BerryRemoteDataSource
import com.funapp.pookiemon.feature.berry.data.repository.BerryRepositoryImpl
import com.funapp.pookiemon.feature.berry.domain.repository.BerryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BerryModule {

    @Provides
    @Singleton
    fun provideBerryApiService(retrofit: retrofit2.Retrofit): BerryApiService = retrofit.create(BerryApiService::class.java)

    @Provides
    @Singleton
    fun provideBerryRepository(impl: BerryRepositoryImpl): BerryRepository = impl
}
