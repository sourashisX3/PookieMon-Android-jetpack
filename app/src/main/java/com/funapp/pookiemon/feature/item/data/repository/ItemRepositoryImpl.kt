package com.funapp.pookiemon.feature.item.data.repository

import android.util.Log
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.item.data.datasource.local.ItemLocalDataSource
import com.funapp.pookiemon.feature.item.data.datasource.remote.ItemRemoteDataSource
import com.funapp.pookiemon.feature.item.data.mapper.toDomain
import com.funapp.pookiemon.feature.item.domain.model.Item
import com.funapp.pookiemon.feature.item.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val remoteDataSource: ItemRemoteDataSource,
    private val localDataSource: ItemLocalDataSource,
) : ItemRepository {

    override suspend fun getItemList(limit: Int, offset: Int, forceRefresh: Boolean): ApiResult<List<Item>> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedItemList(offset)
            if (cached != null) {
                Log.d("ItemRepository", "Cache hit for list offset=$offset")
                return ApiResult.success(cached)
            }
        }

        Log.d("ItemRepository", "Fetching network for list offset=$offset")
        return when (val result = remoteDataSource.getItemList(limit, offset)) {
            is ApiResult.Success -> {
                localDataSource.cacheItemList(offset, result.data)
                val items = result.data.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                        ?: return@mapNotNull null
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
                ApiResult.success(items)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getItemDetail(id: Int, forceRefresh: Boolean): ApiResult<Item> {
        if (!forceRefresh) {
            val cached = localDataSource.getCachedItemDetail(id)
            if (cached != null) {
                Log.d("ItemRepository", "Cache hit for detail id=$id")
                return ApiResult.success(cached)
            }
        }

        Log.d("ItemRepository", "Fetching network for detail id=$id")
        return when (val result = remoteDataSource.getItemDetail(id)) {
            is ApiResult.Success -> {
                val domain = result.data.toDomain()
                localDataSource.cacheItemDetail(result.data)
                ApiResult.success(domain)
            }
            is ApiResult.Error -> {
                val stale = localDataSource.getCachedItemDetail(id)
                if (stale != null) {
                    Log.d("ItemRepository", "Stale cache fallback for detail id=$id")
                    ApiResult.success(stale)
                } else result
            }
        }
    }
}
