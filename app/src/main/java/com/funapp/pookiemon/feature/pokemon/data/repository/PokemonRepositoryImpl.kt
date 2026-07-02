package com.funapp.pookiemon.feature.pokemon.data.repository

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.pokemon.data.datasource.local.PokemonLocalDataSource
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokemonRemoteDataSource
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.toPokemonListItems
import com.funapp.pookiemon.feature.pokemon.data.mapper.toDomain
import com.funapp.pookiemon.feature.pokemon.domain.model.Pokemon
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonListItem
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonSpecies
import com.funapp.pookiemon.feature.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource,
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<PokemonListItem>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedPokemonList(offset)
            if (cached != null) {
                Log.d("PokemonRepository", "Cache hit for list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("PokemonRepository", "Fetching network for list offset=$offset")
        return when (val result = remoteDataSource.getPokemonList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cachePokemonList(offset, result.data)
                ApiResult.success(result.data.toPokemonListItems())
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getPokemonDetail(id: Int, forceRefresh: Boolean): ApiResult<Pokemon> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedPokemonDetail(id)
            if (cached != null) {
                Log.d("PokemonRepository", "Cache hit for detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("PokemonRepository", "Fetching network for detail id=$id")
        return when (val result = remoteDataSource.getPokemonDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cachePokemonDetail(result.data, null)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedPokemonDetail(id)
                if (stale != null) {
                    Log.d("PokemonRepository", "Stale cache fallback for detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }

    override suspend fun getPokemonSpecies(id: Int, forceRefresh: Boolean): ApiResult<PokemonSpecies> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedPokemonSpecies(id)
            if (cached != null) {
                Log.d("PokemonRepository", "Cache hit for species id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("PokemonRepository", "Fetching network for species id=$id")
        return when (val result = remoteDataSource.getPokemonSpecies(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.updateCachedSpecies(id, result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedPokemonSpecies(id)
                if (stale != null) {
                    Log.d("PokemonRepository", "Stale cache fallback for species id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }
}
