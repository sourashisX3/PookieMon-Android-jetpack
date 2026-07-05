package com.funapp.pookiemon.feature.location.domain.repository

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.location.domain.model.Location

interface LocationRepository {
    suspend fun getLocationList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<Location>>
    suspend fun getLocationDetail(id: Int, forceRefresh: Boolean = false): ApiResult<Location>
}
