package com.funapp.pookiemon.feature.games.data.mapper

import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.GenerationDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.PokedexDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.VersionDto
import com.funapp.pookiemon.feature.games.domain.model.Generation
import com.funapp.pookiemon.feature.games.domain.model.Pokedex
import com.funapp.pookiemon.feature.games.domain.model.Version

fun GenerationDto.toDomain(): Generation = Generation(
    id = id,
    name = name,
    mainRegion = main_region?.name,
    versions = versions.map { it.name },
    pokemonSpecies = pokemon_species.map { it.name },
)

fun VersionDto.toDomain(): Version = Version(
    id = id,
    name = name,
    versionGroup = version_group?.name,
)

fun PokedexDto.toDomain(): Pokedex = Pokedex(
    id = id,
    name = name,
    isMainSeries = is_main_series,
    region = region?.name,
)
