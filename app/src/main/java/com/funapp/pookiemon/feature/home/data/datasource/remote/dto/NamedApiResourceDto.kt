package com.funapp.pookiemon.feature.home.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NamedApiResourceDto(
    val name: String,
    val url: String,
)
