package com.funapp.pookiemon.feature.berry.domain.repository

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.berry.domain.model.Berry

interface BerryRepository {
    suspend fun getBerryList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<Berry>>
    suspend fun getBerryDetail(id: Int, forceRefresh: Boolean = false): ApiResult<Berry>
}
