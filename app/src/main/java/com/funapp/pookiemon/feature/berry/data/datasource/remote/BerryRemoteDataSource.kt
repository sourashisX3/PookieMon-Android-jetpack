package com.funapp.pookiemon.feature.berry.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryDto
import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryListResponseDto
import javax.inject.Inject

class BerryRemoteDataSource @Inject constructor(
    private val apiService: BerryApiService,
) {

    suspend fun getBerryList(limit: Int = 20, offset: Int = 0): ApiResult<BerryListResponseDto> {
        return try {
            val response = apiService.getBerryList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load berry list")
        }
    }

    suspend fun getBerryDetail(id: Int): ApiResult<BerryDto> {
        return try {
            val response = apiService.getBerry(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load berry detail")
        }
    }
}
