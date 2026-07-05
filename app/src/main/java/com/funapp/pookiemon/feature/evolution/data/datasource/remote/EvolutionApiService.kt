package com.funapp.pookiemon.feature.evolution.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiConstants
import com.funapp.pookiemon.feature.evolution.data.datasource.remote.dto.EvolutionChainResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface EvolutionApiService {
    @GET("${ApiConstants.EVOLUTION_CHAIN}/{id}")
    suspend fun getEvolutionChain(@Path("id") id: Int): EvolutionChainResponseDto
}
