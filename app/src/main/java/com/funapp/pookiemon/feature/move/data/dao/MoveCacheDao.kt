package com.funapp.pookiemon.feature.move.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.funapp.pookiemon.feature.move.data.entity.CachedMoveEntity
import com.funapp.pookiemon.feature.move.data.entity.CachedMoveListEntity

@Dao
interface MoveCacheDao {

    @Upsert
    suspend fun upsertMove(move: CachedMoveEntity)

    @Upsert
    suspend fun upsertMoveList(list: CachedMoveListEntity)

    @Query("SELECT * FROM cached_move WHERE id = :id")
    suspend fun getMove(id: Int): CachedMoveEntity?

    @Query("SELECT * FROM cached_move_list WHERE `offset` = :offset")
    suspend fun getMoveList(offset: Int): CachedMoveListEntity?

    @Query("DELETE FROM cached_move WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredMoves(expiryThreshold: Long)

    @Query("DELETE FROM cached_move_list WHERE cached_at < :expiryThreshold")
    suspend fun clearExpiredMoveList(expiryThreshold: Long)
}
