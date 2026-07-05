package com.funapp.pookiemon.feature.location.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.location.domain.model.Location
import com.funapp.pookiemon.feature.location.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(
    private val repository: LocationRepository,
) {
    companion object {
        const val PAGE_SIZE = 20
    }

    suspend operator fun invoke(offset: Int = 0, forceRefresh: Boolean = false): ApiResult<List<Location>> {
        return repository.getLocationList(PAGE_SIZE, offset, forceRefresh)
    }
}
