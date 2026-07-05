package com.funapp.pookiemon.feature.games.data.repository

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.games.data.datasource.local.GamesLocalDataSource
import com.funapp.pookiemon.feature.games.data.datasource.remote.GamesRemoteDataSource
import com.funapp.pookiemon.feature.games.data.mapper.toDomain
import com.funapp.pookiemon.feature.games.domain.model.Generation
import com.funapp.pookiemon.feature.games.domain.model.Pokedex
import com.funapp.pookiemon.feature.games.domain.model.Version
import com.funapp.pookiemon.feature.games.domain.repository.GamesRepository
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val remoteDataSource: GamesRemoteDataSource,
    private val localDataSource: GamesLocalDataSource,
) : GamesRepository {

    override suspend fun getGenerationList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<Generation>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedGenerationList(offset)
            if (cached != null) {
                Log.d("GamesRepository", "Cache hit for generation list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("GamesRepository", "Fetching network for generation list offset=$offset")
        return when (val result = remoteDataSource.getGenerationList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheGenerationList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    Generation(id = id, name = resource.name, mainRegion = null, versions = emptyList(), pokemonSpecies = emptyList())
                }
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getGenerationDetail(id: Int, forceRefresh: Boolean): ApiResult<Generation> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedGenerationDetail(id)
            if (cached != null) {
                Log.d("GamesRepository", "Cache hit for generation detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("GamesRepository", "Fetching network for generation detail id=$id")
        return when (val result = remoteDataSource.getGenerationDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cacheGenerationDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedGenerationDetail(id)
                if (stale != null) {
                    Log.d("GamesRepository", "Stale cache fallback for generation detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }

    override suspend fun getVersionList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<Version>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedVersionList(offset)
            if (cached != null) {
                Log.d("GamesRepository", "Cache hit for version list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("GamesRepository", "Fetching network for version list offset=$offset")
        return when (val result = remoteDataSource.getVersionList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheVersionList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    Version(id = id, name = resource.name, versionGroup = null)
                }
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getPokedexList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<Pokedex>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedPokedexList(offset)
            if (cached != null) {
                Log.d("GamesRepository", "Cache hit for pokedex list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("GamesRepository", "Fetching network for pokedex list offset=$offset")
        return when (val result = remoteDataSource.getPokedexList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cachePokedexList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    Pokedex(id = id, name = resource.name, isMainSeries = false, region = null)
                }
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getPokedexDetail(id: Int, forceRefresh: Boolean): ApiResult<Pokedex> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedPokedexDetail(id)
            if (cached != null) {
                Log.d("GamesRepository", "Cache hit for pokedex detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("GamesRepository", "Fetching network for pokedex detail id=$id")
        return when (val result = remoteDataSource.getPokedexDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cachePokedexDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedPokedexDetail(id)
                if (stale != null) {
                    Log.d("GamesRepository", "Stale cache fallback for pokedex detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }
}
