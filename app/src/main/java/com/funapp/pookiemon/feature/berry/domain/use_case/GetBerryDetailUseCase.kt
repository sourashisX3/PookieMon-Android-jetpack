package com.funapp.pookiemon.feature.berry.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.berry.domain.model.Berry
import com.funapp.pookiemon.feature.berry.domain.repository.BerryRepository
import javax.inject.Inject

class GetBerryDetailUseCase @Inject constructor(
    private val repository: BerryRepository,
) {
    suspend operator fun invoke(id: Int, forceRefresh: Boolean = false): ApiResult<Berry> {
        return repository.getBerryDetail(id, forceRefresh)
    }
}
