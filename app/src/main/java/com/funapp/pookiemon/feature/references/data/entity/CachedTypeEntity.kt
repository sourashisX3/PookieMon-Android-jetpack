package com.funapp.pookiemon.feature.references.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_type")
data class CachedTypeEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "type_json") val typeJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "cached_type_list")
data class CachedTypeListEntity(
    @PrimaryKey val offset: Int,
    @ColumnInfo(name = "list_json") val listJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)
