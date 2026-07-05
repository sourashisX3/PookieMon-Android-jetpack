package com.funapp.pookiemon.feature.berry.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.funapp.pookiemon.feature.berry.data.entity.CachedBerryEntity
import com.funapp.pookiemon.feature.berry.data.entity.CachedBerryListEntity

@Dao
interface BerryCacheDao {

    @Upsert
    suspend fun upsertBerry(berry: CachedBerryEntity)

    @Upsert
    suspend fun upsertBerryList(list: CachedBerryListEntity)

    @Query("SELECT * FROM cached_berry WHERE id = :id")
    suspend fun getBerry(id: Int): CachedBerryEntity?

    @Query("SELECT * FROM cached_berry_list WHERE `offset` = :offset")
    suspend fun getBerryList(offset: Int): CachedBerryListEntity?

    @Query("DELETE FROM cached_berry WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredBerries(expiryThreshold: Long)

    @Query("DELETE FROM cached_berry_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredBerryList(expiryThreshold: Long)
}
