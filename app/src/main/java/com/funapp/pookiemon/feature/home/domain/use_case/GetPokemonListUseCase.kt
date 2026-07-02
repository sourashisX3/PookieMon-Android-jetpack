package com.funapp.pookiemon.feature.home.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.home.domain.model.PokemonListItem
import com.funapp.pookiemon.feature.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: HomeRepository,
) {
    companion object {
        const val PAGE_SIZE = 20
    }

    suspend operator fun invoke(offset: Int = 0, forceRefresh: Boolean = false): ApiResult<List<PokemonListItem>> {
        return repository.getPokemonList(PAGE_SIZE, offset, forceRefresh)
    }
}
