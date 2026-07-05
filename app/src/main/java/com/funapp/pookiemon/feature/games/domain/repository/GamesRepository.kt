package com.funapp.pookiemon.feature.games.domain.repository

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.games.domain.model.Generation
import com.funapp.pookiemon.feature.games.domain.model.Pokedex
import com.funapp.pookiemon.feature.games.domain.model.Version

interface GamesRepository {
    suspend fun getGenerationList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<Generation>>
    suspend fun getGenerationDetail(id: Int, forceRefresh: Boolean = false): ApiResult<Generation>
    suspend fun getVersionList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<Version>>
    suspend fun getPokedexList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<Pokedex>>
    suspend fun getPokedexDetail(id: Int, forceRefresh: Boolean = false): ApiResult<Pokedex>
}
