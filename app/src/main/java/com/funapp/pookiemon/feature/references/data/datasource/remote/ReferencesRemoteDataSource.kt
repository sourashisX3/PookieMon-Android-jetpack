package com.funapp.pookiemon.feature.references.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.AbilityDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.AbilityListResponseDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.NatureDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.NatureListResponseDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.TypeDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.TypeListResponseDto
import javax.inject.Inject

class ReferencesRemoteDataSource @Inject constructor(
    private val apiService: ReferencesApiService,
) {

    suspend fun getAbilityList(limit: Int = 20, offset: Int = 0): ApiResult<AbilityListResponseDto> {
        return try {
            val response = apiService.getAbilityList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load ability list")
        }
    }

    suspend fun getAbilityDetail(id: Int): ApiResult<AbilityDto> {
        return try {
            val response = apiService.getAbility(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load ability detail")
        }
    }

    suspend fun getTypeList(limit: Int = 20, offset: Int = 0): ApiResult<TypeListResponseDto> {
        return try {
            val response = apiService.getTypeList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load type list")
        }
    }

    suspend fun getTypeDetail(id: Int): ApiResult<TypeDto> {
        return try {
            val response = apiService.getType(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load type detail")
        }
    }

    suspend fun getNatureList(limit: Int = 20, offset: Int = 0): ApiResult<NatureListResponseDto> {
        return try {
            val response = apiService.getNatureList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load nature list")
        }
    }

    suspend fun getNatureDetail(id: Int): ApiResult<NatureDto> {
        return try {
            val response = apiService.getNature(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load nature detail")
        }
    }
}
