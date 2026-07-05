package com.funapp.pookiemon.feature.berry.data.repository

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.berry.data.datasource.local.BerryLocalDataSource
import com.funapp.pookiemon.feature.berry.data.datasource.remote.BerryRemoteDataSource
import com.funapp.pookiemon.feature.berry.data.mapper.toDomain
import com.funapp.pookiemon.feature.berry.domain.model.Berry
import com.funapp.pookiemon.feature.berry.domain.repository.BerryRepository
import javax.inject.Inject

class BerryRepositoryImpl @Inject constructor(
    private val remoteDataSource: BerryRemoteDataSource,
    private val localDataSource: BerryLocalDataSource,
) : BerryRepository {

    override suspend fun getBerryList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<Berry>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedBerryList(offset)
            if (cached != null) {
                Log.d("BerryRepository", "Cache hit for list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("BerryRepository", "Fetching network for list offset=$offset")
        return when (val result = remoteDataSource.getBerryList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheBerryList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    Berry(
                        id = id,
                        name = resource.name,
                        growthTime = 0,
                        maxHarvest = 0,
                        size = 0,
                        smoothness = 0,
                        firmness = "",
                        flavors = emptyList(),
                    )
                }
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getBerryDetail(id: Int, forceRefresh: Boolean): ApiResult<Berry> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedBerryDetail(id)
            if (cached != null) {
                Log.d("BerryRepository", "Cache hit for detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("BerryRepository", "Fetching network for detail id=$id")
        return when (val result = remoteDataSource.getBerryDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cacheBerryDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedBerryDetail(id)
                if (stale != null) {
                    Log.d("BerryRepository", "Stale cache fallback for detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }
}
