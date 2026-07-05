package com.funapp.pookiemon.feature.location.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.funapp.pookiemon.feature.location.data.entity.CachedLocationEntity
import com.funapp.pookiemon.feature.location.data.entity.CachedLocationListEntity

@Dao
interface LocationCacheDao {

    @Upsert
    suspend fun upsertLocation(location: CachedLocationEntity)

    @Upsert
    suspend fun upsertLocationList(list: CachedLocationListEntity)

    @Query("SELECT * FROM cached_location WHERE id = :id")
    suspend fun getLocation(id: Int): CachedLocationEntity?

    @Query("SELECT * FROM cached_location_list WHERE `offset` = :offset")
    suspend fun getLocationList(offset: Int): CachedLocationListEntity?

    @Query("DELETE FROM cached_location WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredLocations(expiryThreshold: Long)

    @Query("DELETE FROM cached_location_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredLocationList(expiryThreshold: Long)
}
