package com.funapp.pookiemon.feature.home.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.home.domain.model.Pokemon
import com.funapp.pookiemon.feature.home.domain.model.PokemonSpecies
import com.funapp.pookiemon.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

data class PokemonDetailData(
    val pokemon: Pokemon,
    val species: PokemonSpecies?,
)

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: HomeRepository,
) {
    suspend operator fun invoke(id: Int, forceRefresh: Boolean = false): ApiResult<PokemonDetailData> {
        return try {
            val pokemonResult = repository.getPokemonDetail(id, forceRefresh)
            if (pokemonResult.isError) {
                return ApiResult.error(
                    (pokemonResult as ApiResult.Error).message
                )
            }
            val pokemon = (pokemonResult as ApiResult.Success).data

            val speciesResult = repository.getPokemonSpecies(id, forceRefresh)
            val species = if (speciesResult.isSuccess) {
                (speciesResult as ApiResult.Success).data
            } else {
                null
            }

            ApiResult.success(PokemonDetailData(pokemon, species))
        } catch (e: Exception) {
            ApiResult.error(e.message ?: "Failed to load Pokemon detail")
        }
    }
}
