package com.funapp.pookiemon.feature.berry.data.mapper

import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryDto
import com.funapp.pookiemon.feature.berry.domain.model.Berry
import com.funapp.pookiemon.feature.berry.domain.model.BerryFlavor

fun BerryDto.toDomain(): Berry = Berry(
    id = id,
    name = name,
    growthTime = growth_time,
    maxHarvest = max_harvest,
    size = size,
    smoothness = smoothness,
    firmness = firmness.name,
    flavors = flavors.map { BerryFlavor(name = it.flavor.name, potency = it.potency) },
)
