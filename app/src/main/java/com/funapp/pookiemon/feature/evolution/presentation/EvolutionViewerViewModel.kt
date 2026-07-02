package com.funapp.pookiemon.feature.evolution.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.feature.evolution.data.datasource.remote.dto.ChainLinkDto
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokeApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EvolutionViewerUiState(
    val chain: List<String>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

private fun flattenChainLink(link: ChainLinkDto): List<String> {
    val result = mutableListOf<String>()
    result.add(link.species.name)
    link.evolves_to.forEach { child ->
        result.addAll(flattenChainLink(child))
    }
    return result
}

@HiltViewModel
class EvolutionViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiService: PokeApiService,
) : ViewModel() {

    private val speciesId: Int = savedStateHandle.get<Int>("speciesId") ?: 1

    private val _uiState = MutableStateFlow(EvolutionViewerUiState())
    val uiState: StateFlow<EvolutionViewerUiState> = _uiState
        .onStart { loadEvolution() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), EvolutionViewerUiState())

    private fun loadEvolution() {
        viewModelScope.launch {
            _uiState.value = EvolutionViewerUiState(isLoading = true)
            try {
                val speciesDto = apiService.getPokemonSpecies(speciesId)
                val evolutionChainId = speciesDto.evolution_chain?.url?.trimEnd('/')?.split('/')?.lastOrNull()?.toIntOrNull() ?: run {
                    _uiState.value = EvolutionViewerUiState(error = "Evolution chain not found", isLoading = false)
                    return@launch
                }
                val chainDto = apiService.getEvolutionChain(evolutionChainId)
                val names = flattenChainLink(chainDto.chain)
                _uiState.value = EvolutionViewerUiState(chain = names, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = EvolutionViewerUiState(error = e.message ?: "Failed to load evolution chain", isLoading = false)
            }
        }
    }
}
