package com.funapp.pookiemon.core.di

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiConstants
import com.funapp.pookiemon.core.config.network.AuthInterceptor
import com.funapp.pookiemon.core.config.network.EnvironmentConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val NETWORK_TAG = "PokeAPI"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = false
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(
            logger = Logger { message ->
                Log.d(NETWORK_TAG, message)
            },
        ).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val urlLogger = Interceptor { chain ->
            val request = chain.request()
            val url = request.url
            Log.d(NETWORK_TAG, ">>> ${request.method} [${url.scheme}][${url.host}]${url.encodedPath}?${url.encodedQuery}")
            val response = chain.proceed(request)
            Log.d(NETWORK_TAG, "<<< ${response.code} [${url.scheme}][${url.host}]${url.encodedPath} (${response.request.tag()})")
            response
        }

        return OkHttpClient.Builder()
            .addInterceptor(urlLogger)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(ApiConstants.TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(ApiConstants.TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(ApiConstants.TIMEOUT_WRITE, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
        environmentConfig: EnvironmentConfig,
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        Log.d(NETWORK_TAG, "Retrofit base: [${environmentConfig.currentEnvironment.name}] ${environmentConfig.baseUrl}")

        return Retrofit.Builder()
            .baseUrl(environmentConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
