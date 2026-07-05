package com.funapp.pookiemon.feature.location.data.repository

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.location.data.datasource.local.LocationLocalDataSource
import com.funapp.pookiemon.feature.location.data.datasource.remote.LocationRemoteDataSource
import com.funapp.pookiemon.feature.location.data.mapper.toDomain
import com.funapp.pookiemon.feature.location.domain.model.Location
import com.funapp.pookiemon.feature.location.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val remoteDataSource: LocationRemoteDataSource,
    private val localDataSource: LocationLocalDataSource,
) : LocationRepository {

    override suspend fun getLocationList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<Location>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedLocationList(offset)
            if (cached != null) {
                Log.d("LocationRepository", "Cache hit for list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("LocationRepository", "Fetching network for list offset=$offset")
        return when (val result = remoteDataSource.getLocationList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheLocationList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    Location(
                        id = id,
                        name = resource.name,
                        region = null,
                        areas = emptyList(),
                    )
                }
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getLocationDetail(id: Int, forceRefresh: Boolean): ApiResult<Location> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedLocationDetail(id)
            if (cached != null) {
                Log.d("LocationRepository", "Cache hit for detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("LocationRepository", "Fetching network for detail id=$id")
        return when (val result = remoteDataSource.getLocationDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cacheLocationDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedLocationDetail(id)
                if (stale != null) {
                    Log.d("LocationRepository", "Stale cache fallback for detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }
}
