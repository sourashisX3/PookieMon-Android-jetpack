package com.funapp.pookiemon.feature.encounter.domain.repository

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.encounter.domain.model.EncounterMethod

interface EncounterRepository {
    suspend fun getEncounterMethodList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<EncounterMethod>>
    suspend fun getEncounterMethodDetail(id: Int, forceRefresh: Boolean = false): ApiResult<EncounterMethod>
}
