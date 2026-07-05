package com.funapp.pookiemon.feature.evolution.domain.repository

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.evolution.domain.model.EvolutionChain

interface EvolutionRepository {
    suspend fun getEvolutionChain(speciesId: Int, forceRefresh: Boolean = false): ApiResult<EvolutionChain>
}
