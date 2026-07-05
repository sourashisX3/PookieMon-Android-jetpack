package com.funapp.pookiemon.feature.references.data.repository

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.references.data.datasource.local.ReferencesLocalDataSource
import com.funapp.pookiemon.feature.references.data.datasource.remote.ReferencesRemoteDataSource
import com.funapp.pookiemon.feature.references.data.mapper.toDomain
import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.model.Nature
import com.funapp.pookiemon.feature.references.domain.model.PokemonType
import com.funapp.pookiemon.feature.references.domain.repository.ReferencesRepository
import javax.inject.Inject

class ReferencesRepositoryImpl @Inject constructor(
    private val remoteDataSource: ReferencesRemoteDataSource,
    private val localDataSource: ReferencesLocalDataSource,
) : ReferencesRepository {

    override suspend fun getAbilityList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<Ability>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedAbilityList(offset)
            if (cached != null) {
                Log.d("ReferencesRepository", "Cache hit for ability list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("ReferencesRepository", "Fetching network for ability list offset=$offset")
        return when (val result = remoteDataSource.getAbilityList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheAbilityList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    Ability(id = id, name = resource.name, isMainSeries = false, generation = null)
                }
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getAbilityDetail(id: Int, forceRefresh: Boolean): ApiResult<Ability> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedAbilityDetail(id)
            if (cached != null) {
                Log.d("ReferencesRepository", "Cache hit for ability detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("ReferencesRepository", "Fetching network for ability detail id=$id")
        return when (val result = remoteDataSource.getAbilityDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cacheAbilityDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedAbilityDetail(id)
                if (stale != null) {
                    Log.d("ReferencesRepository", "Stale cache fallback for ability detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }

    override suspend fun getTypeList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<PokemonType>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedTypeList(offset)
            if (cached != null) {
                Log.d("ReferencesRepository", "Cache hit for type list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("ReferencesRepository", "Fetching network for type list offset=$offset")
        return when (val result = remoteDataSource.getTypeList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheTypeList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    PokemonType(id = id, name = resource.name, doubleDamageTo = emptyList(), halfDamageTo = emptyList(), noDamageTo = emptyList(), doubleDamageFrom = emptyList(), halfDamageFrom = emptyList(), noDamageFrom = emptyList())
                }
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getTypeDetail(id: Int, forceRefresh: Boolean): ApiResult<PokemonType> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedTypeDetail(id)
            if (cached != null) {
                Log.d("ReferencesRepository", "Cache hit for type detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("ReferencesRepository", "Fetching network for type detail id=$id")
        return when (val result = remoteDataSource.getTypeDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cacheTypeDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedTypeDetail(id)
                if (stale != null) {
                    Log.d("ReferencesRepository", "Stale cache fallback for type detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }

    override suspend fun getNatureList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<Nature>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedNatureList(offset)
            if (cached != null) {
                Log.d("ReferencesRepository", "Cache hit for nature list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("ReferencesRepository", "Fetching network for nature list offset=$offset")
        return when (val result = remoteDataSource.getNatureList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheNatureList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    Nature(id = id, name = resource.name, increasedStat = null, decreasedStat = null, likesFlavor = null, hatesFlavor = null)
                }
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getNatureDetail(id: Int, forceRefresh: Boolean): ApiResult<Nature> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedNatureDetail(id)
            if (cached != null) {
                Log.d("ReferencesRepository", "Cache hit for nature detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("ReferencesRepository", "Fetching network for nature detail id=$id")
        return when (val result = remoteDataSource.getNatureDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cacheNatureDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedNatureDetail(id)
                if (stale != null) {
                    Log.d("ReferencesRepository", "Stale cache fallback for nature detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }
}
