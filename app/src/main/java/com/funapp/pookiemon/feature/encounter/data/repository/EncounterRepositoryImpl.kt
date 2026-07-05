package com.funapp.pookiemon.feature.encounter.data.repository

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.encounter.data.datasource.local.EncounterLocalDataSource
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.EncounterRemoteDataSource
import com.funapp.pookiemon.feature.encounter.data.mapper.toDomain
import com.funapp.pookiemon.feature.encounter.domain.model.EncounterMethod
import com.funapp.pookiemon.feature.encounter.domain.repository.EncounterRepository
import javax.inject.Inject

class EncounterRepositoryImpl @Inject constructor(
    private val remoteDataSource: EncounterRemoteDataSource,
    private val localDataSource: EncounterLocalDataSource,
) : EncounterRepository {

    override suspend fun getEncounterMethodList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<EncounterMethod>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedEncounterMethodList(offset)
            if (cached != null) {
                Log.d("EncounterRepository", "Cache hit for list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("EncounterRepository", "Fetching network for list offset=$offset")
        return when (val result = remoteDataSource.getEncounterMethodList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheEncounterMethodList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    EncounterMethod(
                        id = id,
                        name = resource.name,
                        order = 0,
                    )
                }
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getEncounterMethodDetail(id: Int, forceRefresh: Boolean): ApiResult<EncounterMethod> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedEncounterMethodDetail(id)
            if (cached != null) {
                Log.d("EncounterRepository", "Cache hit for detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("EncounterRepository", "Fetching network for detail id=$id")
        return when (val result = remoteDataSource.getEncounterMethodDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cacheEncounterMethodDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedEncounterMethodDetail(id)
                if (stale != null) {
                    Log.d("EncounterRepository", "Stale cache fallback for detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }
}
