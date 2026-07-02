package com.funapp.pookiemon.feature.move.domain.model

data class Move(
    val id: Int,
    val name: String,
    val accuracy: Int?,
    val effectChance: Int?,
    val pp: Int?,
    val priority: Int,
    val power: Int?,
    val effect: String?,
    val shortEffect: String?,
    val flavorText: String?,
    val type: String?,
    val damageClass: String?,
    val target: String?,
    val generation: String?,
    val contestType: String?,
)
