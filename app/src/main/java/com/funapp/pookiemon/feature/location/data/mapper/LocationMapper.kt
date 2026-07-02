package com.funapp.pookiemon.feature.location.data.mapper

import com.funapp.pookiemon.feature.location.data.datasource.remote.dto.LocationDto
import com.funapp.pookiemon.feature.location.domain.model.Location

fun LocationDto.toDomain(): Location = Location(
    id = id,
    name = name,
    region = region?.name,
    areas = areas.map { it.name },
)
