package com.funapp.pookiemon.feature.location.data.datasource.local

import com.funapp.pookiemon.feature.location.data.dao.LocationCacheDao
import com.funapp.pookiemon.feature.location.data.datasource.remote.dto.LocationDto
import com.funapp.pookiemon.feature.location.data.datasource.remote.dto.LocationListResponseDto
import com.funapp.pookiemon.feature.location.data.entity.CachedLocationEntity
import com.funapp.pookiemon.feature.location.data.entity.CachedLocationListEntity
import com.funapp.pookiemon.feature.location.data.mapper.toDomain
import com.funapp.pookiemon.feature.location.domain.model.Location
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationLocalDataSource @Inject constructor(
    private val cacheDao: LocationCacheDao,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cacheTtl = 1000L * 60 * 60 * 24

    suspend fun getCachedLocationList(offset: Int): List<Location>? {
        val cached = cacheDao.getLocationList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<LocationListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            Location(
                id = id,
                name = resource.name,
                region = null,
                areas = emptyList(),
            )
        }
    }

    suspend fun cacheLocationList(offset: Int, dto: LocationListResponseDto) {
        val jsonString = json.encodeToString(LocationListResponseDto.serializer(), dto)
        cacheDao.upsertLocationList(CachedLocationListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedLocationDetail(id: Int): Location? {
        val cached = cacheDao.getLocation(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<LocationDto>(cached.locationJson)
        return dto.toDomain()
    }

    suspend fun cacheLocationDetail(dto: LocationDto) {
        val locationJson = json.encodeToString(LocationDto.serializer(), dto)
        cacheDao.upsertLocation(CachedLocationEntity(id = dto.id, locationJson = locationJson))
    }

    private fun isExpired(cachedAt: Long): Boolean {
        return System.currentTimeMillis() - cachedAt > cacheTtl
    }
}
