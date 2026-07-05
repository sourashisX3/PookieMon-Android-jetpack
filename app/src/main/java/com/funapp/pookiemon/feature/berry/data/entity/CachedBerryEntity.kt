package com.funapp.pookiemon.feature.berry.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_berry")
data class CachedBerryEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "berry_json") val berryJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "cached_berry_list")
data class CachedBerryListEntity(
    @PrimaryKey val offset: Int,
    @ColumnInfo(name = "list_json") val listJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)
