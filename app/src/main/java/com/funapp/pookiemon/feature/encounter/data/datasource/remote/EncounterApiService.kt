package com.funapp.pookiemon.feature.encounter.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiConstants
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto.EncounterMethodDto
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto.EncounterMethodListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EncounterApiService {
    @GET(ApiConstants.ENCOUNTER_METHOD)
    suspend fun getEncounterMethodList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): EncounterMethodListResponseDto

    @GET("${ApiConstants.ENCOUNTER_METHOD}/{id}")
    suspend fun getEncounterMethod(
        @Path("id") id: Int,
    ): EncounterMethodDto
}
