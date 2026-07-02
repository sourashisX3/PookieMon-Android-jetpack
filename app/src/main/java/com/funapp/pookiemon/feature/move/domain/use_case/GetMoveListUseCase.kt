package com.funapp.pookiemon.feature.move.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.move.domain.model.Move
import com.funapp.pookiemon.feature.move.domain.repository.MoveRepository
import javax.inject.Inject

class GetMoveListUseCase @Inject constructor(
    private val repository: MoveRepository,
) {
    companion object {
        const val PAGE_SIZE = 20
    }

    suspend operator fun invoke(offset: Int = 0, forceRefresh: Boolean = false): ApiResult<List<Move>> {
        return repository.getMoveList(PAGE_SIZE, offset, forceRefresh)
    }
}
