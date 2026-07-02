package com.funapp.pookiemon.feature.encounter.data.mapper

import com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto.EncounterMethodDto
import com.funapp.pookiemon.feature.encounter.domain.model.EncounterMethod

fun EncounterMethodDto.toDomain(): EncounterMethod = EncounterMethod(
    id = id,
    name = name,
    order = order,
)
