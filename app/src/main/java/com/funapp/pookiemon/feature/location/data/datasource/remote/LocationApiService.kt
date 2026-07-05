package com.funapp.pookiemon.feature.location.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiConstants
import com.funapp.pookiemon.feature.location.data.datasource.remote.dto.LocationDto
import com.funapp.pookiemon.feature.location.data.datasource.remote.dto.LocationListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApiService {
    @GET(ApiConstants.LOCATION)
    suspend fun getLocationList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): LocationListResponseDto

    @GET("${ApiConstants.LOCATION}/{id}")
    suspend fun getLocation(
        @Path("id") id: Int,
    ): LocationDto
}
