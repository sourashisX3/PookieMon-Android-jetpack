package com.funapp.pookiemon.feature.evolution.data.repository

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.evolution.data.datasource.local.EvolutionLocalDataSource
import com.funapp.pookiemon.feature.evolution.data.datasource.remote.EvolutionRemoteDataSource
import com.funapp.pookiemon.feature.evolution.data.mapper.toDomain
import com.funapp.pookiemon.feature.evolution.domain.model.EvolutionChain
import com.funapp.pookiemon.feature.evolution.domain.repository.EvolutionRepository
import javax.inject.Inject

class EvolutionRepositoryImpl @Inject constructor(
    private val remoteDataSource: EvolutionRemoteDataSource,
    private val localDataSource: EvolutionLocalDataSource,
) : EvolutionRepository {

    override suspend fun getEvolutionChain(speciesId: Int, forceRefresh: Boolean): ApiResult<EvolutionChain> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedEvolution(speciesId)
            if (cached != null) {
                Log.d("EvolutionRepository", "Cache hit for speciesId=$speciesId")
                return ApiResult.success(cached)
            }
        }

        Log.d("EvolutionRepository", "Fetching network for speciesId=$speciesId")
        return when (val speciesResult = remoteDataSource.getPokemonSpecies(speciesId)) {
            is ApiResult.Success -> {
                val evolutionUrl = speciesResult.data.evolution_chain?.url
                    ?: return ApiResult.error("Evolution chain not found")
                val chainId = evolutionUrl.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    ?: return ApiResult.error("Invalid evolution chain URL")

                when (val chainResult = remoteDataSource.getEvolutionChain(chainId)) {
                    is ApiResult.Success -> {
                        val domain = chainResult.data.toDomain()
                        localDataSource.cacheEvolution(speciesId, chainResult.data)
                        ApiResult.success(domain)
                    }
                    is ApiResult.Error -> {
                        val stale = localDataSource.getCachedEvolution(speciesId)
                        if (stale != null) {
                            Log.d("EvolutionRepository", "Stale cache fallback for speciesId=$speciesId")
                            ApiResult.success(stale)
                        } else chainResult
                    }
                }
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedEvolution(speciesId)
                if (stale != null) {
                    Log.d("EvolutionRepository", "Stale cache fallback for speciesId=$speciesId")
                    ApiResult.success(stale)
                } else speciesResult
            }
        }
    }
}
