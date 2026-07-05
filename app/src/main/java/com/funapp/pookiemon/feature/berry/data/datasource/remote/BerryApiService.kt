package com.funapp.pookiemon.feature.berry.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiConstants
import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryDto
import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BerryApiService {
    @GET(ApiConstants.BERRY)
    suspend fun getBerryList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): BerryListResponseDto

    @GET("${ApiConstants.BERRY}/{id}")
    suspend fun getBerry(
        @Path("id") id: Int,
    ): BerryDto
}
