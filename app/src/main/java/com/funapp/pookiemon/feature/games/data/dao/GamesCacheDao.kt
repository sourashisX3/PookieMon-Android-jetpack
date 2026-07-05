package com.funapp.pookiemon.feature.games.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.funapp.pookiemon.feature.games.data.entity.CachedGenerationEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedGenerationListEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedPokedexEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedPokedexListEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedVersionEntity
import com.funapp.pookiemon.feature.games.data.entity.CachedVersionListEntity

@Dao
interface GamesCacheDao {

    @Upsert
    suspend fun upsertGeneration(entity: CachedGenerationEntity)

    @Upsert
    suspend fun upsertGenerationList(entity: CachedGenerationListEntity)

    @Upsert
    suspend fun upsertVersion(entity: CachedVersionEntity)

    @Upsert
    suspend fun upsertVersionList(entity: CachedVersionListEntity)

    @Upsert
    suspend fun upsertPokedex(entity: CachedPokedexEntity)

    @Upsert
    suspend fun upsertPokedexList(entity: CachedPokedexListEntity)

    @Query("SELECT * FROM cached_generation WHERE id = :id")
    suspend fun getGeneration(id: Int): CachedGenerationEntity?

    @Query("SELECT * FROM cached_generation_list WHERE `offset` = :offset")
    suspend fun getGenerationList(offset: Int): CachedGenerationListEntity?

    @Query("SELECT * FROM cached_version WHERE id = :id")
    suspend fun getVersion(id: Int): CachedVersionEntity?

    @Query("SELECT * FROM cached_version_list WHERE `offset` = :offset")
    suspend fun getVersionList(offset: Int): CachedVersionListEntity?

    @Query("SELECT * FROM cached_pokedex WHERE id = :id")
    suspend fun getPokedex(id: Int): CachedPokedexEntity?

    @Query("SELECT * FROM cached_pokedex_list WHERE `offset` = :offset")
    suspend fun getPokedexList(offset: Int): CachedPokedexListEntity?

    @Query("DELETE FROM cached_generation WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredGenerations(expiryThreshold: Long)

    @Query("DELETE FROM cached_generation_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredGenerationList(expiryThreshold: Long)

    @Query("DELETE FROM cached_version WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredVersions(expiryThreshold: Long)

    @Query("DELETE FROM cached_version_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredVersionList(expiryThreshold: Long)

    @Query("DELETE FROM cached_pokedex WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredPokedex(expiryThreshold: Long)

    @Query("DELETE FROM cached_pokedex_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredPokedexList(expiryThreshold: Long)
}
