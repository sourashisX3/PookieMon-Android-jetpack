package com.funapp.pookiemon.feature.berry.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.berry.domain.model.Berry
import com.funapp.pookiemon.feature.berry.domain.repository.BerryRepository
import javax.inject.Inject

class GetBerryListUseCase @Inject constructor(
    private val repository: BerryRepository,
) {
    companion object {
        const val PAGE_SIZE = 20
    }

    suspend operator fun invoke(offset: Int = 0, forceRefresh: Boolean = false): ApiResult<List<Berry>> {
        return repository.getBerryList(PAGE_SIZE, offset, forceRefresh)
    }
}
