package com.funapp.pookiemon.feature.evolution.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.evolution.domain.model.EvolutionChain
import com.funapp.pookiemon.feature.evolution.domain.repository.EvolutionRepository
import javax.inject.Inject

class GetEvolutionChainUseCase @Inject constructor(
    private val repository: EvolutionRepository,
) {
    suspend operator fun invoke(speciesId: Int, forceRefresh: Boolean = false): ApiResult<EvolutionChain> {
        return repository.getEvolutionChain(speciesId, forceRefresh)
    }
}
