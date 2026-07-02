package com.funapp.pookiemon.feature.move.presentation.di

import com.funapp.pookiemon.feature.move.data.repository.MoveRepositoryImpl
import com.funapp.pookiemon.feature.move.domain.repository.MoveRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoveModule {

    @Provides
    @Singleton
    fun provideMoveRepository(impl: MoveRepositoryImpl): MoveRepository = impl
}
