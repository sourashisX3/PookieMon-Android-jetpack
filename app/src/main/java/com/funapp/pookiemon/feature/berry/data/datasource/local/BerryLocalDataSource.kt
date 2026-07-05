package com.funapp.pookiemon.feature.berry.data.datasource.local

import com.funapp.pookiemon.feature.berry.data.dao.BerryCacheDao
import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryDto
import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryListResponseDto
import com.funapp.pookiemon.feature.berry.data.entity.CachedBerryEntity
import com.funapp.pookiemon.feature.berry.data.entity.CachedBerryListEntity
import com.funapp.pookiemon.feature.berry.data.mapper.toDomain
import com.funapp.pookiemon.feature.berry.domain.model.Berry
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BerryLocalDataSource @Inject constructor(
    private val cacheDao: BerryCacheDao,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cacheTtl = 1000L * 60 * 60 * 24

    suspend fun getCachedBerryList(offset: Int): List<Berry>? {
        val cached = cacheDao.getBerryList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<BerryListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            Berry(
                id = id,
                name = resource.name,
                growthTime = 0,
                maxHarvest = 0,
                size = 0,
                smoothness = 0,
                firmness = "",
                flavors = emptyList(),
            )
        }
    }

    suspend fun cacheBerryList(offset: Int, dto: BerryListResponseDto) {
        val jsonString = json.encodeToString(BerryListResponseDto.serializer(), dto)
        cacheDao.upsertBerryList(CachedBerryListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedBerryDetail(id: Int): Berry? {
        val cached = cacheDao.getBerry(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<BerryDto>(cached.berryJson)
        return dto.toDomain()
    }

    suspend fun cacheBerryDetail(dto: BerryDto) {
        val berryJson = json.encodeToString(BerryDto.serializer(), dto)
        cacheDao.upsertBerry(CachedBerryEntity(id = dto.id, berryJson = berryJson))
    }

    private fun isExpired(cachedAt: Long): Boolean {
        return System.currentTimeMillis() - cachedAt > cacheTtl
    }
}
