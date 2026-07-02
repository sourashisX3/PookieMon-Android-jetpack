package com.funapp.pookiemon.feature.pokemon.data.datasource.remote

import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryDto
import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryListResponseDto
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto.EncounterMethodDto
import com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto.EncounterMethodListResponseDto
import com.funapp.pookiemon.feature.evolution.data.datasource.remote.dto.EvolutionChainResponseDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.GenerationDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.GenerationListResponseDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.PokedexDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.PokedexListResponseDto
import com.funapp.pookiemon.feature.games.data.datasource.remote.dto.VersionListResponseDto
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemCategoryDto
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemDto
import com.funapp.pookiemon.feature.item.data.datasource.remote.dto.ItemListResponseDto
import com.funapp.pookiemon.feature.location.data.datasource.remote.dto.LocationDto
import com.funapp.pookiemon.feature.location.data.datasource.remote.dto.LocationListResponseDto
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveDamageClassDto
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveDto
import com.funapp.pookiemon.feature.move.data.datasource.remote.dto.MoveListResponseDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonListResponseDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto.PokemonSpeciesDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.AbilityDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.AbilityListResponseDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.NatureDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.NatureListResponseDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.TypeDto
import com.funapp.pookiemon.feature.references.data.datasource.remote.dto.TypeListResponseDto
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

    @GET("item")
    suspend fun getItemList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): ItemListResponseDto

    @GET("item/{id}")
    suspend fun getItem(
        @Path("id") id: Int,
    ): ItemDto

    @GET("item-category/{id}")
    suspend fun getItemCategory(
        @Path("id") id: Int,
    ): ItemCategoryDto

    @GET("move")
    suspend fun getMoveList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): MoveListResponseDto

    @GET("move/{id}")
    suspend fun getMove(
        @Path("id") id: Int,
    ): MoveDto

    @GET("move-damage-class/{id}")
    suspend fun getMoveDamageClass(
        @Path("id") id: Int,
    ): MoveDamageClassDto

    @GET("berry")
    suspend fun getBerryList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): BerryListResponseDto

    @GET("berry/{id}")
    suspend fun getBerry(
        @Path("id") id: Int,
    ): BerryDto

    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(
        @Path("id") id: Int,
    ): EvolutionChainResponseDto

    @GET("location")
    suspend fun getLocationList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): LocationListResponseDto

    @GET("location/{id}")
    suspend fun getLocation(
        @Path("id") id: Int,
    ): LocationDto

    @GET("encounter-method")
    suspend fun getEncounterMethodList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): EncounterMethodListResponseDto

    @GET("encounter-method/{id}")
    suspend fun getEncounterMethod(
        @Path("id") id: Int,
    ): EncounterMethodDto

    @GET("generation")
    suspend fun getGenerationList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): GenerationListResponseDto

    @GET("generation/{id}")
    suspend fun getGeneration(
        @Path("id") id: Int,
    ): GenerationDto

    @GET("version")
    suspend fun getVersionList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): VersionListResponseDto

    @GET("pokedex")
    suspend fun getPokedexList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): PokedexListResponseDto

    @GET("pokedex/{id}")
    suspend fun getPokedex(
        @Path("id") id: Int,
    ): PokedexDto

    @GET("ability")
    suspend fun getAbilityList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): AbilityListResponseDto

    @GET("ability/{id}")
    suspend fun getAbility(
        @Path("id") id: Int,
    ): AbilityDto

    @GET("type")
    suspend fun getTypeList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): TypeListResponseDto

    @GET("type/{id}")
    suspend fun getType(
        @Path("id") id: Int,
    ): TypeDto

    @GET("nature")
    suspend fun getNatureList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): NatureListResponseDto

    @GET("nature/{id}")
    suspend fun getNature(
        @Path("id") id: Int,
    ): NatureDto
}
