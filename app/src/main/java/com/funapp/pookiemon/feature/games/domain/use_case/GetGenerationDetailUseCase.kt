package com.funapp.pookiemon.feature.games.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.games.domain.model.Generation
import com.funapp.pookiemon.feature.games.domain.repository.GamesRepository
import javax.inject.Inject

class GetGenerationDetailUseCase @Inject constructor(
    private val repository: GamesRepository,
) {
    suspend operator fun invoke(id: Int, forceRefresh: Boolean = false): ApiResult<Generation> {
        return repository.getGenerationDetail(id, forceRefresh)
    }
}
