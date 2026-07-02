package com.funapp.pookiemon.feature.item.domain.model

data class Item(
    val id: Int,
    val name: String,
    val cost: Int,
    val flingPower: Int?,
    val flingEffect: String?,
    val category: String,
    val effect: String?,
    val shortEffect: String?,
    val flavorText: String?,
    val spriteUrl: String?,
    val heldByPokemon: List<String>,
    val attributes: List<String>,
)
