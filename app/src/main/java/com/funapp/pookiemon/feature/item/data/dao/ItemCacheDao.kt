package com.funapp.pookiemon.feature.item.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.funapp.pookiemon.feature.item.data.entity.CachedItemEntity
import com.funapp.pookiemon.feature.item.data.entity.CachedItemListEntity

@Dao
interface ItemCacheDao {

    @Upsert
    suspend fun upsertItem(item: CachedItemEntity)

    @Upsert
    suspend fun upsertItemList(list: CachedItemListEntity)

    @Query("SELECT * FROM cached_item WHERE id = :id")
    suspend fun getItem(id: Int): CachedItemEntity?

    @Query("SELECT * FROM cached_item_list WHERE `offset` = :offset")
    suspend fun getItemList(offset: Int): CachedItemListEntity?

    @Query("DELETE FROM cached_item WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredItems(expiryThreshold: Long)

    @Query("DELETE FROM cached_item_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredItemList(expiryThreshold: Long)
}
