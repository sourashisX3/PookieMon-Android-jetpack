package com.funapp.pookiemon.feature.encounter.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.encounter.domain.model.EncounterMethod
import com.funapp.pookiemon.feature.encounter.domain.repository.EncounterRepository
import javax.inject.Inject

class GetEncounterMethodDetailUseCase @Inject constructor(
    private val repository: EncounterRepository,
) {
    suspend operator fun invoke(id: Int, forceRefresh: Boolean = false): ApiResult<EncounterMethod> {
        return repository.getEncounterMethodDetail(id, forceRefresh)
    }
}
