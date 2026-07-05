package com.funapp.pookiemon.feature.references.domain.repository

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.model.Nature
import com.funapp.pookiemon.feature.references.domain.model.PokemonType

interface ReferencesRepository {
    suspend fun getAbilityList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<Ability>>
    suspend fun getAbilityDetail(id: Int, forceRefresh: Boolean = false): ApiResult<Ability>
    suspend fun getTypeList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<PokemonType>>
    suspend fun getTypeDetail(id: Int, forceRefresh: Boolean = false): ApiResult<PokemonType>
    suspend fun getNatureList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<Nature>>
    suspend fun getNatureDetail(id: Int, forceRefresh: Boolean = false): ApiResult<Nature>
}
