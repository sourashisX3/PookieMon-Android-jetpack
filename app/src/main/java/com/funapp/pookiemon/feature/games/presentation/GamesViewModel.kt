package com.funapp.pookiemon.feature.games.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.feature.games.data.mapper.toDomain
import com.funapp.pookiemon.feature.games.domain.model.Generation
import com.funapp.pookiemon.feature.games.domain.model.Pokedex
import com.funapp.pookiemon.feature.games.domain.model.Version
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokeApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GamesUiState(
    val generations: List<Generation> = emptyList(),
    val versions: List<Version> = emptyList(),
    val pokedexes: List<Pokedex> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val apiService: PokeApiService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GamesUiState())
    val uiState: StateFlow<GamesUiState> = _uiState
        .onStart { loadAll() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), GamesUiState())

    fun loadAll() {
        viewModelScope.launch {
            _uiState.value = GamesUiState(isLoading = true)
            try {
                val genResponse = apiService.getGenerationList(20, 0)
                val gens = genResponse.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    if (id == null) null else Generation(id = id, name = resource.name, mainRegion = null, versions = emptyList(), pokemonSpecies = emptyList())
                }

                val verResponse = apiService.getVersionList(20, 0)
                val vers = verResponse.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    if (id == null) null else Version(id = id, name = resource.name, versionGroup = null)
                }

                val dexResponse = apiService.getPokedexList(20, 0)
                val dexes = dexResponse.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    if (id == null) null else Pokedex(id = id, name = resource.name, isMainSeries = false, region = null)
                }

                _uiState.value = GamesUiState(generations = gens, versions = vers, pokedexes = dexes, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = GamesUiState(error = e.message, isLoading = false)
            }
        }
    }
}
