package com.funapp.pookiemon.feature.pokemon.data.mapper

import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonDto
import com.funapp.pookiemon.feature.pokemon.domain.model.Pokemon
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonAbility
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonSprites
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonStat
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonType

fun PokemonDto.toDomain(): Pokemon {
    val sprites = this.sprites?.let { spriteDto ->
        PokemonSprites(
            frontDefault = spriteDto.front_default
                ?: spriteDto.other?.officialArtwork?.front_default
                ?: spriteDto.other?.home?.front_default
                ?: "",
            frontShiny = spriteDto.front_shiny
                ?: spriteDto.other?.officialArtwork?.front_shiny,
            artworkDefault = spriteDto.other?.officialArtwork?.front_default,
            artworkShiny = spriteDto.other?.officialArtwork?.front_shiny,
        )
    }

    return Pokemon(
        id = id,
        name = name,
        height = height,
        weight = weight,
        baseExperience = base_experience,
        types = types.map { typeDto ->
            PokemonType(
                name = typeDto.type.name,
                slot = typeDto.slot,
            )
        },
        stats = stats.map { statDto ->
            PokemonStat(
                name = statDto.stat.name,
                baseStat = statDto.base_stat,
                effort = statDto.effort,
            )
        },
        abilities = abilities.mapNotNull { abilityDto ->
            abilityDto.ability?.let { resource ->
                PokemonAbility(
                    name = resource.name,
                    isHidden = abilityDto.is_hidden,
                )
            }
        },
        sprites = sprites,
        speciesUrl = species.url,
    )
}
