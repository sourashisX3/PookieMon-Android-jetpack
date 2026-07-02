package com.funapp.pookiemon.feature.move.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.move.domain.model.Move
import com.funapp.pookiemon.feature.move.domain.repository.MoveRepository
import javax.inject.Inject

class GetMoveDetailUseCase @Inject constructor(
    private val repository: MoveRepository,
) {
    suspend operator fun invoke(id: Int, forceRefresh: Boolean = false): ApiResult<Move> {
        return repository.getMoveDetail(id, forceRefresh)
    }
}
