package com.funapp.pookiemon.feature.item.presentation.di

import com.funapp.pookiemon.feature.item.data.datasource.remote.ItemApiService
import com.funapp.pookiemon.feature.item.data.repository.ItemRepositoryImpl
import com.funapp.pookiemon.feature.item.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ItemModule {

    @Provides
    @Singleton
    fun provideItemApiService(retrofit: Retrofit): ItemApiService =
        retrofit.create(ItemApiService::class.java)

    @Provides
    @Singleton
    fun provideItemRepository(impl: ItemRepositoryImpl): ItemRepository = impl
}
