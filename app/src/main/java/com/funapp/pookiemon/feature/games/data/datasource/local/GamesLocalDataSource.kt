package com.funapp.pookiemon.feature.games.data.datasource.local

import com.funapp.pookiemon.feature.games.data.dao.GamesCacheDao
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.GenerationDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.GenerationListResponseDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.PokedexDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.PokedexListResponseDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.VersionListResponseDto
import com.funapp.pookiemon.feature.games.data.entity.CachedGenerationEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedGenerationListEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedPokedexEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedPokedexListEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedVersionEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedVersionListEntity
import com.funapp.pookiemon.feature.games.data.mapper.toDomain
import com.funapp.pookiemon.feature.games.domain.model.Generation
import com.funapp.pookiemon.feature.games.domain.model.Pokedex
import com.funapp.pookiemon.feature.games.domain.model.Version
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesLocalDataSource @Inject constructor(
    private val cacheDao: GamesCacheDao,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cacheTtl = 1000L * 60 * 60 * 24

    suspend fun getCachedGenerationList(offset: Int): List<Generation>? {
        val cached = cacheDao.getGenerationList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<GenerationListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            Generation(id = id, name = resource.name, mainRegion = null, versions = emptyList(), pokemonSpecies = emptyList())
        }
    }

    suspend fun cacheGenerationList(offset: Int, dto: GenerationListResponseDto) {
        val jsonString = json.encodeToString(GenerationListResponseDto.serializer(), dto)
        cacheDao.upsertGenerationList(CachedGenerationListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedGenerationDetail(id: Int): Generation? {
        val cached = cacheDao.getGeneration(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<GenerationDto>(cached.generationJson)
        return dto.toDomain()
    }

    suspend fun cacheGenerationDetail(dto: GenerationDto) {
        val jsonString = json.encodeToString(GenerationDto.serializer(), dto)
        cacheDao.upsertGeneration(CachedGenerationEntity(id = dto.id, generationJson = jsonString))
    }

    suspend fun getCachedVersionList(offset: Int): List<Version>? {
        val cached = cacheDao.getVersionList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<VersionListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            Version(id = id, name = resource.name, versionGroup = null)
        }
    }

    suspend fun cacheVersionList(offset: Int, dto: VersionListResponseDto) {
        val jsonString = json.encodeToString(VersionListResponseDto.serializer(), dto)
        cacheDao.upsertVersionList(CachedVersionListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedPokedexList(offset: Int): List<Pokedex>? {
        val cached = cacheDao.getPokedexList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<PokedexListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            Pokedex(id = id, name = resource.name, isMainSeries = false, region = null)
        }
    }

    suspend fun cachePokedexList(offset: Int, dto: PokedexListResponseDto) {
        val jsonString = json.encodeToString(PokedexListResponseDto.serializer(), dto)
        cacheDao.upsertPokedexList(CachedPokedexListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedPokedexDetail(id: Int): Pokedex? {
        val cached = cacheDao.getPokedex(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<PokedexDto>(cached.pokedexJson)
        return dto.toDomain()
    }

    suspend fun cachePokedexDetail(dto: PokedexDto) {
        val jsonString = json.encodeToString(PokedexDto.serializer(), dto)
        cacheDao.upsertPokedex(CachedPokedexEntity(id = dto.id, pokedexJson = jsonString))
    }

    private fun isExpired(cachedAt: Long): Boolean {
        return System.currentTimeMillis() - cachedAt > cacheTtl
    }
}
