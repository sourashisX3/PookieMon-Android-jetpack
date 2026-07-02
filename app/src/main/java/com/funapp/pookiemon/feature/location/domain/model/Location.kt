package com.funapp.pookiemon.feature.location.domain.model

data class Location(
    val id: Int,
    val name: String,
    val region: String?,
    val areas: List<String>,
)
