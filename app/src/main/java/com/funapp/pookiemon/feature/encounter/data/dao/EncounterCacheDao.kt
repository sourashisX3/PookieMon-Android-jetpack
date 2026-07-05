package com.funapp.pookiemon.feature.encounter.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.funapp.pookiemon.feature.encounter.data.entity.CachedEncounterMethodEntity
import com.funapp.pookiemon.feature.encounter.data.entity.CachedEncounterMethodListEntity

@Dao
interface EncounterCacheDao {

    @Upsert
    suspend fun upsertEncounterMethod(method: CachedEncounterMethodEntity)

    @Upsert
    suspend fun upsertEncounterMethodList(list: CachedEncounterMethodListEntity)

    @Query("SELECT * FROM cached_encounter_method WHERE id = :id")
    suspend fun getEncounterMethod(id: Int): CachedEncounterMethodEntity?

    @Query("SELECT * FROM cached_encounter_method_list WHERE `offset` = :offset")
    suspend fun getEncounterMethodList(offset: Int): CachedEncounterMethodListEntity?

    @Query("DELETE FROM cached_encounter_method WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredEncounterMethods(expiryThreshold: Long)

    @Query("DELETE FROM cached_encounter_method_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredEncounterMethodList(expiryThreshold: Long)
}
