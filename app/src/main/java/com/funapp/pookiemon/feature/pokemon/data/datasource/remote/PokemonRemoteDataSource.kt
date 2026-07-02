package com.funapp.pookiemon.feature.pokemon.data.datasource.remote

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.core.config.network.pokemonArtworkUrl
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonListResponseDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonSpeciesDto
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonListItem
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val apiService: PokeApiService,
) {

    suspend fun getPokemonList(limit: Int = 20, offset: Int = 0): ApiResult<PokemonListResponseDto> {
        return try {
            val response = apiService.getPokemonList(limit, offset)
            ApiResult.success(response)
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load Pokemon list")
        }
    }

    suspend fun getPokemonDetail(id: Int): ApiResult<PokemonDto> {
        return try {
            val response = apiService.getPokemon(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            Log.e("PokemonDataSource", "Detail failed: id=$id", e)
            ApiResult.error(e.message ?: "Failed to load Pokemon detail")
        }
    }

    suspend fun getPokemonSpecies(id: Int): ApiResult<PokemonSpeciesDto> {
        return try {
            val response = apiService.getPokemonSpecies(id)
            ApiResult.success(response)
        } catch (e: Exception) {
            Log.e("PokemonDataSource", "Species failed: id=$id", e)
            ApiResult.error(e.message ?: "Failed to load Pokemon species")
        }
    }
}

fun PokemonListResponseDto.toPokemonListItems(): List<PokemonListItem> {
    return results.mapNotNull { resource ->
        val id = resource.url
            .trimEnd('/')
            .split('/')
            .lastOrNull()
            ?.toIntOrNull()
            ?: return@mapNotNull null
        PokemonListItem(
            id = id,
            name = resource.name,
            spriteUrl = pokemonArtworkUrl(id),
            types = emptyList(),
        )
    }
}
