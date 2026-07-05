package com.funapp.pookiemon.feature.games.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.GenerationDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.GenerationListResponseDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.PokedexDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.PokedexListResponseDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.VersionListResponseDto
import javax.inject.Inject

class GamesRemoteDataSource @Inject constructor(
    private val apiService: GamesApiService,
) {

    suspend fun getGenerationList(limit: Int = 20, offset: Int = 0): ApiResult<GenerationListResponseDto> {
        return try {
            val response = apiService.getGenerationList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load generation list")
        }
    }

    suspend fun getGenerationDetail(id: Int): ApiResult<GenerationDto> {
        return try {
            val response = apiService.getGeneration(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load generation detail")
        }
    }

    suspend fun getVersionList(limit: Int = 20, offset: Int = 0): ApiResult<VersionListResponseDto> {
        return try {
            val response = apiService.getVersionList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load version list")
        }
    }

    suspend fun getPokedexList(limit: Int = 20, offset: Int = 0): ApiResult<PokedexListResponseDto> {
        return try {
            val response = apiService.getPokedexList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load pokedex list")
        }
    }

    suspend fun getPokedexDetail(id: Int): ApiResult<PokedexDto> {
        return try {
            val response = apiService.getPokedex(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load pokedex detail")
        }
    }
}
