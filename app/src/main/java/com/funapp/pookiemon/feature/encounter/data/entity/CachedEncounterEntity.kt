package com.funapp.pookiemon.feature.encounter.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_encounter_method")
data class CachedEncounterMethodEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "method_json") val methodJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "cached_encounter_method_list")
data class CachedEncounterMethodListEntity(
    @PrimaryKey val offset: Int,
    @ColumnInfo(name = "list_json") val listJson: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
)
