package com.funapp.pookiemon.feature.move.data.datasource.local

import com.funapp.pookiemon.feature.move.data.dao.MoveCacheDao
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveDto
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveListResponseDto
import com.funapp.pookiemon.feature.move.data.entity.CachedMoveEntity
import com.funapp.pookiemon.feature.move.data.entity.CachedMoveListEntity
import com.funapp.pookiemon.feature.move.data.mapper.toDomain
import com.funapp.pookiemon.feature.move.domain.model.Move
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoveLocalDataSource @Inject constructor(
    private val cacheDao: MoveCacheDao,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cacheTtl = 1000L * 60 * 60 * 24

    suspend fun getCachedMoveList(offset: Int): List<Move>? {
        val cached = cacheDao.getMoveList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<MoveListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
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
    }

    suspend fun cacheMoveList(offset: Int, dto: MoveListResponseDto) {
        val jsonString = json.encodeToString(MoveListResponseDto.serializer(), dto)
        cacheDao.upsertMoveList(CachedMoveListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedMoveDetail(id: Int): Move? {
        val cached = cacheDao.getMove(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<MoveDto>(cached.moveJson)
        return dto.toDomain()
    }

    suspend fun cacheMoveDetail(dto: MoveDto) {
        val moveJson = json.encodeToString(MoveDto.serializer(), dto)
        cacheDao.upsertMove(CachedMoveEntity(id = dto.id, moveJson = moveJson))
    }

    private fun isExpired(cachedAt: Long): Boolean {
        return System.currentTimeMillis() - cachedAt > cacheTtl
    }
}
