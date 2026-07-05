package com.funapp.pookiemon.feature.references.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiConstants
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.AbilityDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.AbilityListResponseDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.NatureDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.NatureListResponseDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.TypeDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.TypeListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReferencesApiService {
    @GET(ApiConstants.ABILITY)
    suspend fun getAbilityList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): AbilityListResponseDto

    @GET("${ApiConstants.ABILITY}/{id}")
    suspend fun getAbility(@Path("id") id: Int): AbilityDto

    @GET(ApiConstants.TYPE)
    suspend fun getTypeList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): TypeListResponseDto

    @GET("${ApiConstants.TYPE}/{id}")
    suspend fun getType(@Path("id") id: Int): TypeDto

    @GET(ApiConstants.NATURE)
    suspend fun getNatureList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): NatureListResponseDto

    @GET("${ApiConstants.NATURE}/{id}")
    suspend fun getNature(@Path("id") id: Int): NatureDto
}
