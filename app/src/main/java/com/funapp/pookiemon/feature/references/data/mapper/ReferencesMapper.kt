package com.funapp.pookiemon.feature.references.data.mapper

import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.AbilityDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.NatureDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.TypeDto
import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.model.Nature
import com.funapp.pookiemon.feature.references.domain.model.PokemonType

fun AbilityDto.toDomain(): Ability = Ability(
    id = id,
    name = name,
    isMainSeries = is_main_series,
    generation = generation?.name,
)

fun TypeDto.toDomain(): PokemonType = PokemonType(
    id = id,
    name = name,
    doubleDamageTo = damage_relations?.double_damage_to?.map { it.name } ?: emptyList(),
    halfDamageTo = damage_relations?.half_damage_to?.map { it.name } ?: emptyList(),
    noDamageTo = damage_relations?.no_damage_to?.map { it.name } ?: emptyList(),
    doubleDamageFrom = damage_relations?.double_damage_from?.map { it.name } ?: emptyList(),
    halfDamageFrom = damage_relations?.half_damage_from?.map { it.name } ?: emptyList(),
    noDamageFrom = damage_relations?.no_damage_from?.map { it.name } ?: emptyList(),
)

fun NatureDto.toDomain(): Nature = Nature(
    id = id,
    name = name,
    increasedStat = increased_stat?.name,
    decreasedStat = decreased_stat?.name,
    likesFlavor = likes_flavor?.name,
    hatesFlavor = hates_flavor?.name,
)
