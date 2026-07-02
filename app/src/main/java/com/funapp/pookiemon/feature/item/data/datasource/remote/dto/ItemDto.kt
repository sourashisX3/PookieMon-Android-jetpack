package com.funapp.pookiemon.feature.item.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: Int,
    val name: String,
    val cost: Int = 0,
    val fling_power: Int? = null,
    val fling_effect: NamedApiResource? = null,
    val attributes: List<NamedApiResource> = emptyList(),
    val category: NamedApiResource,
    val effect_entries: List<ItemEffectEntryDto> = emptyList(),
    val flavor_text_entries: List<ItemFlavorTextDto> = emptyList(),
    val names: List<ItemNameDto> = emptyList(),
    val sprites: ItemSpritesDto? = null,
    val held_by_pokemon: List<ItemHolderPokemonDto> = emptyList(),
)

@Serializable
data class ItemEffectEntryDto(
    val effect: String,
    val short_effect: String,
    val language: NamedApiResource,
)

@Serializable
data class ItemFlavorTextDto(
    val text: String,
    val language: NamedApiResource,
    val version_group: NamedApiResource? = null,
)

@Serializable
data class ItemNameDto(
    val name: String,
    val language: NamedApiResource,
)

@Serializable
data class ItemSpritesDto(
    val default: String? = null,
)

@Serializable
data class ItemHolderPokemonDto(
    val pokemon: NamedApiResource,
    val version_details: List<ItemHolderVersionDto> = emptyList(),
)

@Serializable
data class ItemHolderVersionDto(
    val rarity: Int,
    val version: NamedApiResource,
)

@Serializable
data class ItemListResponseDto(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<NamedApiResource>,
)

@Serializable
data class ItemCategoryDto(
    val id: Int,
    val name: String,
    val items: List<NamedApiResource> = emptyList(),
    val names: List<ItemNameDto> = emptyList(),
)

@Serializable
data class NamedApiResource(
    val name: String,
    val url: String,
)
