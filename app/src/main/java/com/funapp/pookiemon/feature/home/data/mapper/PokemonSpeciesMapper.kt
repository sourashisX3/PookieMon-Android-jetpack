package com.funapp.pookiemon.feature.home.data.mapper

import com.funapp.pookiemon.feature.home.data.datasource.remote.dto.PokemonSpeciesDto
import com.funapp.pookiemon.feature.home.domain.model.PokemonSpecies

fun PokemonSpeciesDto.toDomain(): PokemonSpecies {
    val enFlavorText = flavor_text_entries
        .firstOrNull { it.language.name == "en" }
        ?.flavor_text
        ?.replace("\n", " ")
        ?.replace("\u000c", "\n")

    val enGenus = genera
        .firstOrNull { it.language.name == "en" }
        ?.genus

    val evoChainId = evolution_chain?.url
        ?.split("/")
        ?.dropLastWhile { it.isEmpty() }
        ?.lastOrNull()
        ?.toIntOrNull()

    return PokemonSpecies(
        id = id,
        name = name,
        flavorText = enFlavorText,
        genus = enGenus,
        color = color?.name,
        habitat = habitat?.name,
        isLegendary = is_legendary,
        isMythical = is_mythical,
        evolutionChainId = evoChainId,
        evolvesFromSpecies = evolves_from_species?.name,
    )
}
