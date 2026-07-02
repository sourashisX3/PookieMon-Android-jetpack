package com.funapp.pookiemon.feature.move.data.mapper

import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveDto
import com.funapp.pookiemon.feature.move.domain.model.Move

fun MoveDto.toDomain(): Move {
    val enEffect = effect_entries.firstOrNull { it.language.name == "en" }
    val enFlavor = flavor_text_entries.firstOrNull { it.language.name == "en" }

    return Move(
        id = id,
        name = name,
        accuracy = accuracy,
        effectChance = effect_chance,
        pp = pp,
        priority = priority,
        power = power,
        effect = enEffect?.effect,
        shortEffect = enEffect?.short_effect,
        flavorText = enFlavor?.flavor_text,
        type = type?.name,
        damageClass = damage_class?.name,
        target = target?.name,
        generation = generation?.name,
        contestType = contest_type?.name,
    )
}
