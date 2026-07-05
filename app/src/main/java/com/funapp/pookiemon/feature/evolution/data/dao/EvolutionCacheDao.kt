package com.funapp.pookiemon.feature.evolution.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.funapp.pookiemon.feature.evolution.data.entity.CachedEvolutionEntity

@Dao
interface EvolutionCacheDao {

    @Upsert
    suspend fun upsertEvolution(entity: CachedEvolutionEntity)

    @Query("SELECT * FROM cached_evolution WHERE speciesId = :speciesId")
    suspend fun getEvolution(speciesId: Int): CachedEvolutionEntity?

    @Query("DELETE FROM cached_evolution WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredEvolution(expiryThreshold: Long)
}
