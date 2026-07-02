package com.funapp.pookiemon.feature.item.presentation.di

import com.funapp.pookiemon.feature.item.data.datasource.local.ItemLocalDataSource
import com.funapp.pookiemon.feature.item.data.datasource.remote.ItemRemoteDataSource
import com.funapp.pookiemon.feature.item.data.repository.ItemRepositoryImpl
import com.funapp.pookiemon.feature.item.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ItemModule {

    @Provides
    @Singleton
    fun provideItemRepository(impl: ItemRepositoryImpl): ItemRepository = impl
}
