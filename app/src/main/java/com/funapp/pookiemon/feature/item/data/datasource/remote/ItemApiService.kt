package com.funapp.pookiemon.feature.item.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiConstants
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemCategoryDto
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemDto
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemApiService {

    @GET(ApiConstants.ITEM)
    suspend fun getItemList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): ItemListResponseDto

    @GET("${ApiConstants.ITEM}/{id}")
    suspend fun getItem(
        @Path("id") id: Int,
    ): ItemDto

    @GET("${ApiConstants.ITEM_CATEGORY}/{id}")
    suspend fun getItemCategory(
        @Path("id") id: Int,
    ): ItemCategoryDto
}
