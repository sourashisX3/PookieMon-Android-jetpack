package com.funapp.pookiemon.feature.move.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveDamageClassDto
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveDto
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveListResponseDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokeApiService
import javax.inject.Inject

class MoveRemoteDataSource @Inject constructor(
    private val apiService: PokeApiService,
) {

    suspend fun getMoveList(limit: Int = 20, offset: Int = 0): ApiResult<MoveListResponseDto> {
        return try {
            val response = apiService.getMoveList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load move list")
        }
    }

    suspend fun getMoveDetail(id: Int): ApiResult<MoveDto> {
        return try {
            val response = apiService.getMove(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load move detail")
        }
    }

    suspend fun getMoveDamageClass(id: Int): ApiResult<MoveDamageClassDto> {
        return try {
            val response = apiService.getMoveDamageClass(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load move damage class")
        }
    }
}
