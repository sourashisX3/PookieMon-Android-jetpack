package com.funapp.pookiemon.feature.evolution.data.datasource.local

import com.funapp.pookiemon.feature.evolution.data.dao.EvolutionCacheDao
import com.funapp.pookiemon.feature.evolution.data.datasource.remote.dto.EvolutionChainResponseDto
import com.funapp.pookiemon.feature.evolution.data.entity.CachedEvolutionEntity
import com.funapp.pookiemon.feature.evolution.data.mapper.toDomain
import com.funapp.pookiemon.feature.evolution.domain.model.EvolutionChain
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EvolutionLocalDataSource @Inject constructor(
    private val cacheDao: EvolutionCacheDao,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cacheTtl = 1000L * 60 * 60 * 24

    suspend fun getCachedEvolution(speciesId: Int): EvolutionChain? {
        val cached = cacheDao.getEvolution(speciesId) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<EvolutionChainResponseDto>(cached.chainJson)
        return dto.toDomain()
    }

    suspend fun cacheEvolution(speciesId: Int, dto: EvolutionChainResponseDto) {
        val jsonString = json.encodeToString(EvolutionChainResponseDto.serializer(), dto)
        cacheDao.upsertEvolution(CachedEvolutionEntity(speciesId = speciesId, chainJson = jsonString))
    }

    private fun isExpired(cachedAt: Long): Boolean {
        return System.currentTimeMillis() - cachedAt > cacheTtl
    }
}
