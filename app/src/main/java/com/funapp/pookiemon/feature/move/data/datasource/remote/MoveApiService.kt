package com.funapp.pookiemon.feature.move.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiConstants
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveDamageClassDto
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveDto
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoveApiService {

    @GET(ApiConstants.MOVE)
    suspend fun getMoveList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): MoveListResponseDto

    @GET("${ApiConstants.MOVE}/{id}")
    suspend fun getMove(
        @Path("id") id: Int,
    ): MoveDto

    @GET("${ApiConstants.MOVE_DAMAGE_CLASS}/{id}")
    suspend fun getMoveDamageClass(
        @Path("id") id: Int,
    ): MoveDamageClassDto
}
