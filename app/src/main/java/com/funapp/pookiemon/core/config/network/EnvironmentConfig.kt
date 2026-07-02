package com.funapp.pookiemon.core.config.network

import android.util.Log
import com.funapp.pookiemon.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

enum class BuildEnvironment(val baseUrl: String) {
    DEV("https://pokeapi.co/api/v2/"),
    STAGING("https://pokeapi.co/api/v2/"),
    PROD("https://pokeapi.co/api/v2/");

    companion object {
        fun fromName(name: String): BuildEnvironment {
            return entries.firstOrNull { it.name == name } ?: PROD
        }
    }
}

@Singleton
class EnvironmentConfig @Inject constructor() {
    val currentEnvironment: BuildEnvironment = BuildEnvironment.fromName(BuildConfig.BUILD_ENV)
    val baseUrl: String get() = currentEnvironment.baseUrl

    init {
        Log.d("EnvironmentConfig", "Current environment: ${currentEnvironment.name}, Base URL: $baseUrl")
    }
}
