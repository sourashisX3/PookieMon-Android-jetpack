package com.funapp.pookiemon.core.di

import android.content.Context
import com.funapp.pookiemon.PookieMonApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: PookieMonApp): Context = app.applicationContext
}
