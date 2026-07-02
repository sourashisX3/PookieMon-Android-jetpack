package com.funapp.pookiemon.core.config.network

import android.util.Log
import com.funapp.pookiemon.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

enum class BuildEnvironment {
    DEV, STAGING, PROD;

    companion object {
        fun fromName(name: String): BuildEnvironment {
            return entries.firstOrNull { it.name == name } ?: PROD
        }
    }
}

@Singleton
class EnvironmentConfig @Inject constructor() {
    val currentEnvironment: BuildEnvironment = BuildEnvironment.fromName(BuildConfig.BUILD_ENV)
    val baseUrl: String get() = BuildConfig.API_BASE_URL

    init {
        Log.d("EnvironmentConfig", "Current environment: ${currentEnvironment.name}, Base URL: $baseUrl")
    }
}
