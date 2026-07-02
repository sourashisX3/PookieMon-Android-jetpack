package com.funapp.pookiemon.feature.pokemon.data.datasource.local

import com.funapp.pookiemon.core.config.network.pokemonArtworkUrl
import com.funapp.pookiemon.feature.pokemon.data.dao.PokemonCacheDao
import com.funapp.pookiemon.feature.pokemon.data.entity.CachedPokemonEntity
import com.funapp.pookiemon.feature.pokemon.data.entity.CachedPokemonListEntity
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonListResponseDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonSpeciesDto
import com.funapp.pookiemon.feature.pokemon.domain.model.Pokemon
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonListItem
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonSpecies
import com.funapp.pookiemon.feature.pokemon.data.mapper.toDomain
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonLocalDataSource @Inject constructor(
    private val cacheDao: PokemonCacheDao,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cacheTtl = 1000L * 60 * 60 * 24 // 24 hours

    suspend fun getCachedPokemonList(offset: Int): List<PokemonListItem>? {
        val cached = cacheDao.getPokemonList(offset) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<PokemonListResponseDto>(cached.listJson)
        return dto.results.mapNotNull { resource ->
            val id = resource.url
                .trimEnd('/')
                .split('/')
                .lastOrNull()
                ?.toIntOrNull()
                ?: return@mapNotNull null
            PokemonListItem(
                id = id,
                name = resource.name,
                spriteUrl = pokemonArtworkUrl(id),
                types = emptyList(),
            )
        }
    }

    suspend fun cachePokemonList(offset: Int, dto: PokemonListResponseDto) {
        val jsonString = json.encodeToString(PokemonListResponseDto.serializer(), dto)
        cacheDao.upsertPokemonList(
            CachedPokemonListEntity(offset = offset, listJson = jsonString)
        )
    }

    suspend fun getCachedPokemonDetail(id: Int): Pokemon? {
        val cached = cacheDao.getPokemon(id) ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<PokemonDto>(cached.pokemonJson)
        return dto.toDomain()
    }

    suspend fun getCachedPokemonSpecies(id: Int): PokemonSpecies? {
        val cached = cacheDao.getPokemon(id) ?: return null
        val speciesJson = cached.speciesJson ?: return null
        if (isExpired(cached.cachedAt)) return null
        val dto = json.decodeFromString<PokemonSpeciesDto>(speciesJson)
        return dto.toDomain()
    }

    suspend fun cachePokemonDetail(pokemon: PokemonDto, species: PokemonSpeciesDto?) {
        val pokemonJson = json.encodeToString(PokemonDto.serializer(), pokemon)
        val speciesJson = species?.let {
            json.encodeToString(PokemonSpeciesDto.serializer(), it)
        }
        cacheDao.upsertPokemon(
            CachedPokemonEntity(
                id = pokemon.id,
                pokemonJson = pokemonJson,
                speciesJson = speciesJson,
            )
        )
    }

    suspend fun updateCachedSpecies(id: Int, species: PokemonSpeciesDto) {
        val existing = cacheDao.getPokemon(id)
        val speciesJson = json.encodeToString(PokemonSpeciesDto.serializer(), species)
        if (existing != null) {
            cacheDao.upsertPokemon(existing.copy(speciesJson = speciesJson))
        } else {
            cacheDao.upsertPokemon(
                CachedPokemonEntity(id = id, pokemonJson = "{}", speciesJson = speciesJson)
            )
        }
    }

    private fun isExpired(cachedAt: Long): Boolean {
        return System.currentTimeMillis() - cachedAt > cacheTtl
    }
}
