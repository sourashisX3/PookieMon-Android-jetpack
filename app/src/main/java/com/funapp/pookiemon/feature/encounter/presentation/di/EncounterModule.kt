package com.funapp.pookiemon.feature.encounter.presentation.di

import com.funapp.pookiemon.feature.encounter.data.datasource.local.EncounterLocalDataSource
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.EncounterApiService
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.EncounterRemoteDataSource
import com.funapp.pookiemon.feature.encounter.data.repository.EncounterRepositoryImpl
import com.funapp.pookiemon.feature.encounter.domain.repository.EncounterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncounterModule {

    @Provides
    @Singleton
    fun provideEncounterApiService(retrofit: retrofit2.Retrofit): EncounterApiService = retrofit.create(EncounterApiService::class.java)

    @Provides
    @Singleton
    fun provideEncounterRepository(impl: EncounterRepositoryImpl): EncounterRepository = impl
}
