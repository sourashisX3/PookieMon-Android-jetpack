package com.funapp.pookiemon.feature.item.data.mapper

import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemDto
import com.funapp.pookiemon.feature.item.domain.model.Item

fun ItemDto.toDomain(): Item {
    val enEffect = effect_entries.firstOrNull { it.language.name == "en" }
    val enFlavor = flavor_text_entries.firstOrNull { it.language.name == "en" }

    return Item(
        id = id,
        name = name,
        cost = cost,
        flingPower = fling_power,
        flingEffect = fling_effect?.name,
        category = category.name,
        effect = enEffect?.effect,
        shortEffect = enEffect?.short_effect,
        flavorText = enFlavor?.text,
        spriteUrl = sprites?.default,
        heldByPokemon = held_by_pokemon.map { it.pokemon.name },
        attributes = attributes.map { it.name },
    )
}
