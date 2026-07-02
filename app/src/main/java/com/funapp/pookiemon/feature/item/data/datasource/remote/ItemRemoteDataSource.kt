package com.funapp.pookiemon.feature.item.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemCategoryDto
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemDto
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemListResponseDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokeApiService
import javax.inject.Inject

class ItemRemoteDataSource @Inject constructor(
    private val apiService: PokeApiService,
) {

    suspend fun getItemList(limit: Int = 20, offset: Int = 0): ApiResult<ItemListResponseDto> {
        return try {
            val response = apiService.getItemList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load item list")
        }
    }

    suspend fun getItemDetail(id: Int): ApiResult<ItemDto> {
        return try {
            val response = apiService.getItem(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load item detail")
        }
    }

    suspend fun getItemCategory(id: Int): ApiResult<ItemCategoryDto> {
        return try {
            val response = apiService.getItemCategory(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load item category")
        }
    }
}
