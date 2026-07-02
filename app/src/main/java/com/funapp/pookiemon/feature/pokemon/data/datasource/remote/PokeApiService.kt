package com.funapp.pookiemon.feature.pokemon.data.datasource.remote

import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonListResponseDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonSpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): PokemonListResponseDto

    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int,
    ): PokemonDto

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: Int,
    ): PokemonSpeciesDto
}
