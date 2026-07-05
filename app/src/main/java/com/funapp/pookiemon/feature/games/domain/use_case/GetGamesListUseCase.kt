package com.funapp.pookiemon.feature.games.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.games.domain.model.Generation
import com.funapp.pookiemon.feature.games.domain.model.Pokedex
import com.funapp.pookiemon.feature.games.domain.model.Version
import com.funapp.pookiemon.feature.games.domain.repository.GamesRepository
import javax.inject.Inject

data class GamesListData(
    val generations: List<Generation>,
    val versions: List<Version>,
    val pokedexes: List<Pokedex>,
)

class GetGamesListUseCase @Inject constructor(
    private val repository: GamesRepository,
) {
    companion object {
        const val PAGE_SIZE = 20
    }

    suspend operator fun invoke(offset: Int = 0, forceRefresh: Boolean = false): ApiResult<GamesListData> {
        val genResult = repository.getGenerationList(PAGE_SIZE, offset, forceRefresh)
        if (genResult is ApiResult.Error) return genResult

        val verResult = repository.getVersionList(PAGE_SIZE, offset, forceRefresh)
        if (verResult is ApiResult.Error) return verResult

        val dexResult = repository.getPokedexList(PAGE_SIZE, offset, forceRefresh)
        if (dexResult is ApiResult.Error) return dexResult

        return ApiResult.success(
            GamesListData(
                generations = genResult.getOrNull() ?: emptyList(),
                versions = verResult.getOrNull() ?: emptyList(),
                pokedexes = dexResult.getOrNull() ?: emptyList(),
            )
        )
    }
}
