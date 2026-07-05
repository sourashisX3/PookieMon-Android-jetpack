package com.funapp.pookiemon.feature.games.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiConstants
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.GenerationDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.GenerationListResponseDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.PokedexDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.PokedexListResponseDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.VersionListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApiService {
    @GET(ApiConstants.GENERATION)
    suspend fun getGenerationList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): GenerationListResponseDto

    @GET("${ApiConstants.GENERATION}/{id}")
    suspend fun getGeneration(@Path("id") id: Int): GenerationDto

    @GET(ApiConstants.VERSION)
    suspend fun getVersionList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): VersionListResponseDto

    @GET(ApiConstants.POKEDEX)
    suspend fun getPokedexList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): PokedexListResponseDto

    @GET("${ApiConstants.POKEDEX}/{id}")
    suspend fun getPokedex(@Path("id") id: Int): PokedexDto
}
