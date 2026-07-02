package com.funapp.pookiemon.feature.item.data.datasource.local

import com.funapp.pookiemon.feature.item.data.dao.ItemCacheDao
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemDto
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemListResponseDto
import com.funapp.pookiemon.feature.item.data.entity.CachedItemEntity
import com.funapp.pookiemon.feature.item.data.entity.CachedItemListEntity
import com.funapp.pookiemon.feature.item.data.mapper.toDomain
import com.funapp.pookiemon.feature.item.domain.model.Item
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemLocalDataSource @Inject constructor(
    private val cacheDao: ItemCacheDao,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cacheTtl = 1000L * 60 * 60 * 24

    suspend fun getCachedItemList(offset: Int): List<Item>? {
        val cached = cacheDao.getItemList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<ItemListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: return@mapNotNull null
            val spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${resource.name}.png"
            Item(
                id = id,
                name = resource.name,
                cost = 0,
                flingPower = null,
                flingEffect = null,
                category = "",
                effect = null,
                shortEffect = null,
                flavorText = null,
                spriteUrl = spriteUrl,
                heldByPokemon = emptyList(),
                attributes = emptyList(),
            )
        }
    }

    suspend fun cacheItemList(offset: Int, dto: ItemListResponseDto) {
        val jsonString = json.encodeToString(ItemListResponseDto.serializer(), dto)
        cacheDao.upsertItemList(CachedItemListEntity(offset = offset, listJson = jsonString))
    }

    suspend fun getCachedItemDetail(id: Int): Item? {
        val cached = cacheDao.getItem(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<ItemDto>(cached.itemJson)
        return dto.toDomain()
    }

    suspend fun cacheItemDetail(dto: ItemDto) {
        val itemJson = json.encodeToString(ItemDto.serializer(), dto)
        cacheDao.upsertItem(CachedItemEntity(id = dto.id, itemJson = itemJson))
    }

    private fun isExpired(cachedAt: Long): Boolean {
        return System.currentTimeMillis() - cachedAt > cacheTtl
    }
}
