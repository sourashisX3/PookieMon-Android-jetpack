package com.funapp.pookiemon.feature.location.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.location.data.datasource.remote.dto.LocationDto
import com.funapp.pookiemon.feature.location.data.datasource.remote.dto.LocationListResponseDto
import javax.inject.Inject

class LocationRemoteDataSource @Inject constructor(
    private val apiService: LocationApiService,
) {

    suspend fun getLocationList(limit: Int = 20, offset: Int = 0): ApiResult<LocationListResponseDto> {
        return try {
            val response = apiService.getLocationList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load location list")
        }
    }

    suspend fun getLocationDetail(id: Int): ApiResult<LocationDto> {
        return try {
            val response = apiService.getLocation(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load location detail")
        }
    }
}
