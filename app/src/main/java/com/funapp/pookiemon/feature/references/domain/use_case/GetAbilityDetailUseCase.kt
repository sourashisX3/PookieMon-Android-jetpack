package com.funapp.pookiemon.feature.references.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.repository.ReferencesRepository
import javax.inject.Inject

class GetAbilityDetailUseCase @Inject constructor(
    private val repository: ReferencesRepository,
) {
    suspend operator fun invoke(id: Int, forceRefresh: Boolean = false): ApiResult<Ability> {
        return repository.getAbilityDetail(id, forceRefresh)
    }
}
