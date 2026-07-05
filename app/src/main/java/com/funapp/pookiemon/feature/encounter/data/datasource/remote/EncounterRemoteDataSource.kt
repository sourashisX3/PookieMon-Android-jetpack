package com.funapp.pookiemon.feature.encounter.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto.EncounterMethodDto
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto.EncounterMethodListResponseDto
import javax.inject.Inject

class EncounterRemoteDataSource @Inject constructor(
    private val apiService: EncounterApiService,
) {

    suspend fun getEncounterMethodList(limit: Int = 20, offset: Int = 0): ApiResult<EncounterMethodListResponseDto> {
        return try {
            val response = apiService.getEncounterMethodList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load encounter method list")
        }
    }

    suspend fun getEncounterMethodDetail(id: Int): ApiResult<EncounterMethodDto> {
        return try {
            val response = apiService.getEncounterMethod(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load encounter method detail")
        }
    }
}
