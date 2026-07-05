package com.funapp.pookiemon.feature.references.data.datasource.local

import com.funapp.pookiemon.feature.references.data.dao.ReferenceCacheDao
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.AbilityDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.AbilityListResponseDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.NatureDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.NatureListResponseDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.TypeDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.TypeListResponseDto
import com.funapp.pookiemon.feature.references.data.entity.CachedAbilityEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedAbilityListEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedNatureEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedNatureListEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedTypeEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedTypeListEntity
import com.funapp.pookiemon.feature.references.data.mapper.toDomain
import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.model.Nature
import com.funapp.pookiemon.feature.references.domain.model.PokemonType
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReferencesLocalDataSource @Inject constructor(
    private val cacheDao: ReferenceCacheDao,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cacheTtl = 1000L * 60 * 60 * 24

    suspend fun getCachedAbilityList(offset: Int): List<Ability>? {
        val cached = cacheDao.getAbilityList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<AbilityListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            Ability(id = id, name = resource.name, isMainSeries = false, generation = null)
        }
    }

    suspend fun cacheAbilityList(offset: Int, dto: AbilityListResponseDto) {
        val jsonString = json.encodeToString(AbilityListResponseDto.serializer(), dto)
        cacheDao.upsertAbilityList(CachedAbilityListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedAbilityDetail(id: Int): Ability? {
        val cached = cacheDao.getAbility(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<AbilityDto>(cached.abilityJson)
        return dto.toDomain()
    }

    suspend fun cacheAbilityDetail(dto: AbilityDto) {
        val jsonString = json.encodeToString(AbilityDto.serializer(), dto)
        cacheDao.upsertAbility(CachedAbilityEntity(id = dto.id, abilityJson = jsonString))
    }

    suspend fun getCachedTypeList(offset: Int): List<PokemonType>? {
        val cached = cacheDao.getTypeList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<TypeListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            PokemonType(id = id, name = resource.name, doubleDamageTo = emptyList(), halfDamageTo = emptyList(), noDamageTo = emptyList(), doubleDamageFrom = emptyList(), halfDamageFrom = emptyList(), noDamageFrom = emptyList())
        }
    }

    suspend fun cacheTypeList(offset: Int, dto: TypeListResponseDto) {
        val jsonString = json.encodeToString(TypeListResponseDto.serializer(), dto)
        cacheDao.upsertTypeList(CachedTypeListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedTypeDetail(id: Int): PokemonType? {
        val cached = cacheDao.getType(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<TypeDto>(cached.typeJson)
        return dto.toDomain()
    }

    suspend fun cacheTypeDetail(dto: TypeDto) {
        val jsonString = json.encodeToString(TypeDto.serializer(), dto)
        cacheDao.upsertType(CachedTypeEntity(id = dto.id, typeJson = jsonString))
    }

    suspend fun getCachedNatureList(offset: Int): List<Nature>? {
        val cached = cacheDao.getNatureList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<NatureListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            Nature(id = id, name = resource.name, increasedStat = null, decreasedStat = null, likesFlavor = null, hatesFlavor = null)
        }
    }

    suspend fun cacheNatureList(offset: Int, dto: NatureListResponseDto) {
        val jsonString = json.encodeToString(NatureListResponseDto.serializer(), dto)
        cacheDao.upsertNatureList(CachedNatureListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedNatureDetail(id: Int): Nature? {
        val cached = cacheDao.getNature(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<NatureDto>(cached.natureJson)
        return dto.toDomain()
    }

    suspend fun cacheNatureDetail(dto: NatureDto) {
        val jsonString = json.encodeToString(NatureDto.serializer(), dto)
        cacheDao.upsertNature(CachedNatureEntity(id = dto.id, natureJson = jsonString))
    }

    private fun isExpired(cachedAt: Long): Boolean {
        return System.currentTimeMillis() - cachedAt > cacheTtl
    }
}
