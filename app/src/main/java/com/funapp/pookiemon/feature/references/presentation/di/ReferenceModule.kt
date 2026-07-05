package com.funapp.pookiemon.feature.references.presentation.di

import com.funapp.pookiemon.feature.references.data.datasource.remote.ReferencesApiService
import com.funapp.pookiemon.feature.references.data.repository.ReferencesRepositoryImpl
import com.funapp.pookiemon.feature.references.domain.repository.ReferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReferenceModule {

    @Provides
    @Singleton
    fun provideReferencesApiService(retrofit: Retrofit): ReferencesApiService {
        return retrofit.create(ReferencesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideReferencesRepository(impl: ReferencesRepositoryImpl): ReferencesRepository = impl
}
