package com.funapp.pookiemon.feature.references.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.model.Nature
import com.funapp.pookiemon.feature.references.domain.model.PokemonType
import com.funapp.pookiemon.feature.references.domain.repository.ReferencesRepository
import javax.inject.Inject

data class ReferencesListData(
    val abilities: List<Ability>,
    val types: List<PokemonType>,
    val natures: List<Nature>,
)

class GetReferencesListUseCase @Inject constructor(
    private val repository: ReferencesRepository,
) {
    companion object {
        const val PAGE_SIZE = 20
    }

    suspend operator fun invoke(offset: Int = 0, forceRefresh: Boolean = false): ApiResult<ReferencesListData> {
        val abilResult = repository.getAbilityList(PAGE_SIZE, offset, forceRefresh)
        if (abilResult is ApiResult.Error) return abilResult

        val typeResult = repository.getTypeList(PAGE_SIZE, offset, forceRefresh)
        if (typeResult is ApiResult.Error) return typeResult

        val natResult = repository.getNatureList(PAGE_SIZE, offset, forceRefresh)
        if (natResult is ApiResult.Error) return natResult

        return ApiResult.success(
            ReferencesListData(
                abilities = abilResult.getOrNull() ?: emptyList(),
                types = typeResult.getOrNull() ?: emptyList(),
                natures = natResult.getOrNull() ?: emptyList(),
            )
        )
    }
}
