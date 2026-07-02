package com.funapp.pookiemon.feature.berry.domain.model

data class Berry(
    val id: Int,
    val name: String,
    val growthTime: Int,
    val maxHarvest: Int,
    val size: Int,
    val smoothness: Int,
    val firmness: String,
    val flavors: List<BerryFlavor>,
)

data class BerryFlavor(
    val name: String,
    val potency: Int,
)
