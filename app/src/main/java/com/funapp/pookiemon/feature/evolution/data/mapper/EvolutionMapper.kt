package com.funapp.pookiemon.feature.evolution.data.mapper

import com.funapp.pookiemon.feature.evolution.data.datasource.remote.dto.ChainLinkDto
import com.funapp.pookiemon.feature.evolution.data.datasource.remote.dto.EvolutionChainResponseDto
import com.funapp.pookiemon.feature.evolution.domain.model.EvolutionChain
import com.funapp.pookiemon.feature.evolution.domain.model.EvolutionNode

fun EvolutionChainResponseDto.toDomain(): EvolutionChain {
    fun ChainLinkDto.toNode(): EvolutionNode {
        val speciesId = species.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: 0
        val detail = evolution_details?.firstOrNull()
        return EvolutionNode(
            species = species.name,
            speciesId = speciesId,
            minLevel = detail?.min_level,
            trigger = detail?.trigger?.name,
            item = detail?.item?.name,
            heldItem = detail?.held_item?.name,
            knownMove = detail?.known_move?.name,
            evolvesTo = evolves_to.map { it.toNode() },
        )
    }

    val speciesId = chain.species.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull() ?: 0
    return EvolutionChain(
        id = id,
        species = chain.species.name,
        speciesId = speciesId,
        evolvesTo = chain.evolves_to.map { it.toNode() },
    )
}
