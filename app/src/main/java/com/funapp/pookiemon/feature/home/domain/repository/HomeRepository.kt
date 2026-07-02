package com.funapp.pookiemon.feature.home.domain.repository

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.home.domain.model.Pokemon
import com.funapp.pookiemon.feature.home.domain.model.PokemonListItem
import com.funapp.pookiemon.feature.home.domain.model.PokemonSpecies

interface HomeRepository {
    suspend fun getPokemonList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<PokemonListItem>>
    suspend fun getPokemonDetail(id: Int, forceRefresh: Boolean = false): ApiResult<Pokemon>
    suspend fun getPokemonSpecies(id: Int, forceRefresh: Boolean = false): ApiResult<PokemonSpecies>
}
