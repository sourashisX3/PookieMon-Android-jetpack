package com.funapp.pookiemon.feature.move.domain.repository

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.move.domain.model.Move

interface MoveRepository {
    suspend fun getMoveList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<Move>>
    suspend fun getMoveDetail(id: Int, forceRefresh: Boolean = false): ApiResult<Move>
}
