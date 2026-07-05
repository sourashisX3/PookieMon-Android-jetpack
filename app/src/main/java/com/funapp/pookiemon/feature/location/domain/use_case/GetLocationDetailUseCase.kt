package com.funapp.pookiemon.feature.location.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.location.domain.model.Location
import com.funapp.pookiemon.feature.location.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationDetailUseCase @Inject constructor(
    private val repository: LocationRepository,
) {
    suspend operator fun invoke(id: Int, forceRefresh: Boolean = false): ApiResult<Location> {
        return repository.getLocationDetail(id, forceRefresh)
    }
}
