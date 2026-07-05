package com.funapp.pookiemon.feature.evolution.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.evolution.domain.use_case.GetEvolutionChainUseCase
import com.funapp.pookiemon.feature.evolution.presentation.events.EvolutionViewerUiEvent
import com.funapp.pookiemon.feature.evolution.presentation.states.EvolutionViewerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvolutionViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getEvolutionChainUseCase: GetEvolutionChainUseCase,
) : ViewModel() {

    private val speciesId: Int = savedStateHandle.get<Int>("speciesId") ?: 1

    private val _uiState = MutableStateFlow(EvolutionViewerUiState())
    val uiState: StateFlow<EvolutionViewerUiState> = _uiState
        .onStart { loadEvolution() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), EvolutionViewerUiState())

    fun onEvent(event: EvolutionViewerUiEvent) {
        when (event) {
            EvolutionViewerUiEvent.RetryClicked -> loadEvolution(forceRefresh = true)
        }
    }

    private fun loadEvolution(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getEvolutionChainUseCase(speciesId, forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(chain = result.data, isLoading = false)
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}
