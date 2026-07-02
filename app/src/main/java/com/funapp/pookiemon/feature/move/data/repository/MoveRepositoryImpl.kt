package com.funapp.pookiemon.feature.move.data.repository

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.move.data.datasource.local.MoveLocalDataSource
import com.funapp.pookiemon.feature.move.data.datasource.remote.MoveRemoteDataSource
import com.funapp.pookiemon.feature.move.data.mapper.toDomain
import com.funapp.pookiemon.feature.move.domain.model.Move
import com.funapp.pookiemon.feature.move.domain.repository.MoveRepository
import javax.inject.Inject

class MoveRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoveRemoteDataSource,
    private val localDataSource: MoveLocalDataSource,
) : MoveRepository {

    override suspend fun getMoveList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<Move>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedMoveList(offset)
            if (cached != null) {
                Log.d("MoveRepository", "Cache hit for list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("MoveRepository", "Fetching network for list offset=$offset")
        return when (val result = remoteDataSource.getMoveList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheMoveList(offset, result.data)
                val moves = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
                    Move(
                        id = id,
                        name = resource.name,
                        accuracy = null,
                        effectChance = null,
                        pp = null,
                        priority = 0,
                        power = null,
                        effect = null,
                        shortEffect = null,
                        flavorText = null,
                        type = null,
                        damageClass = null,
                        target = null,
                        generation = null,
                        contestType = null,
                    )
                }
                ApiResult.success(moves)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getMoveDetail(id: Int, forceRefresh: Boolean): ApiResult<Move> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedMoveDetail(id)
            if (cached != null) {
                Log.d("MoveRepository", "Cache hit for detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("MoveRepository", "Fetching network for detail id=$id")
        return when (val result = remoteDataSource.getMoveDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cacheMoveDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedMoveDetail(id)
                if (stale != null) {
                    Log.d("MoveRepository", "Stale cache fallback for detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }
}
