package com.funapp.pookiemon.feature.references.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.funapp.pookiemon.feature.references.data.entity.CachedAbilityEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedAbilityListEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedNatureEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedNatureListEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedTypeEntity
import com.funapp.pookiemon.feature.references.data.entity.CachedTypeListEntity

@Dao
interface ReferenceCacheDao {

    @Upsert
    suspend fun upsertAbility(entity: CachedAbilityEntity)

    @Upsert
    suspend fun upsertAbilityList(entity: CachedAbilityListEntity)

    @Upsert
    suspend fun upsertType(entity: CachedTypeEntity)

    @Upsert
    suspend fun upsertTypeList(entity: CachedTypeListEntity)

    @Upsert
    suspend fun upsertNature(entity: CachedNatureEntity)

    @Upsert
    suspend fun upsertNatureList(entity: CachedNatureListEntity)

    @Query("SELECT * FROM cached_ability WHERE id = :id")
    suspend fun getAbility(id: Int): CachedAbilityEntity?

    @Query("SELECT * FROM cached_ability_list WHERE `offset` = :offset")
    suspend fun getAbilityList(offset: Int): CachedAbilityListEntity?

    @Query("SELECT * FROM cached_type WHERE id = :id")
    suspend fun getType(id: Int): CachedTypeEntity?

    @Query("SELECT * FROM cached_type_list WHERE `offset` = :offset")
    suspend fun getTypeList(offset: Int): CachedTypeListEntity?

    @Query("SELECT * FROM cached_nature WHERE id = :id")
    suspend fun getNature(id: Int): CachedNatureEntity?

    @Query("SELECT * FROM cached_nature_list WHERE `offset` = :offset")
    suspend fun getNatureList(offset: Int): CachedNatureListEntity?

    @Query("DELETE FROM cached_ability WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredAbilities(expiryThreshold: Long)

    @Query("DELETE FROM cached_ability_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredAbilityList(expiryThreshold: Long)

    @Query("DELETE FROM cached_type WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredTypes(expiryThreshold: Long)

    @Query("DELETE FROM cached_type_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredTypeList(expiryThreshold: Long)

    @Query("DELETE FROM cached_nature WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredNatures(expiryThreshold: Long)

    @Query("DELETE FROM cached_nature_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredNatureList(expiryThreshold: Long)
}
