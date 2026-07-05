package com.funapp.pookiemon.feature.encounter.data.datasource.local

import com.funapp.pookiemon.feature.encounter.data.dao.EncounterCacheDao
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto.EncounterMethodDto
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto.EncounterMethodListResponseDto
import com.funapp.pookiemon.feature.encounter.data.entity.CachedEncounterMethodEntity
import com.funapp.pookiemon.feature.encounter.data.entity.CachedEncounterMethodListEntity
import com.funapp.pookiemon.feature.encounter.data.mapper.toDomain
import com.funapp.pookiemon.feature.encounter.domain.model.EncounterMethod
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncounterLocalDataSource @Inject constructor(
    private val cacheDao: EncounterCacheDao,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cacheTtl = 1000L * 60 * 60 * 24

    suspend fun getCachedEncounterMethodList(offset: Int): List<EncounterMethod>? {
        val cached = cacheDao.getEncounterMethodList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<EncounterMethodListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            EncounterMethod(
                id = id,
                name = resource.name,
                order = 0,
            )
        }
    }

    suspend fun cacheEncounterMethodList(offset: Int, dto: EncounterMethodListResponseDto) {
        val jsonString = json.encodeToString(EncounterMethodListResponseDto.serializer(), dto)
        cacheDao.upsertEncounterMethodList(CachedEncounterMethodListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedEncounterMethodDetail(id: Int): EncounterMethod? {
        val cached = cacheDao.getEncounterMethod(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<EncounterMethodDto>(cached.methodJson)
        return dto.toDomain()
    }

    suspend fun cacheEncounterMethodDetail(dto: EncounterMethodDto) {
        val methodJson = json.encodeToString(EncounterMethodDto.serializer(), dto)
        cacheDao.upsertEncounterMethod(CachedEncounterMethodEntity(id = dto.id, methodJson = methodJson))
    }

    private fun isExpired(cachedAt: Long): Boolean {
        return System.currentTimeMillis() - cachedAt > cacheTtl
    }
}
