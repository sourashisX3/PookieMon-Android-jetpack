package com.funapp.pookiemon.feature.evolution.data.datasource.remote

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.evolution.data.datasource.remote.dto.EvolutionChainResponseDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokemonApiService
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonSpeciesDto
import javax.inject.Inject

class EvolutionRemoteDataSource @Inject constructor(
    private val evolutionApiService: EvolutionApiService,
    private val pokemonApiService: PokemonApiService,
) {

    suspend fun getEvolutionChain(id: Int): ApiResult<EvolutionChainResponseDto> {
        return try {
            val response = evolutionApiService.getEvolutionChain(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load evolution chain")
        }
    }

    suspend fun getPokemonSpecies(id: Int): ApiResult<PokemonSpeciesDto> {
        return try {
            val response = pokemonApiService.getPokemonSpecies(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load pokemon species")
        }
    }
}
