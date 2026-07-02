package com.funapp.pookiemon.feature.pokemon.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.pokemon.domain.use_case.GetPokemonDetailUseCase
import com.funapp.pookiemon.feature.pokemon.presentation.events.DetailUiEvent
import com.funapp.pookiemon.feature.pokemon.presentation.states.DetailUiState
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
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
) : ViewModel() {

    private val pokemonId: Int = savedStateHandle.get<Int>("pokemonId") ?: 1

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), DetailUiState())

    fun onEvent(event: DetailUiEvent) {
        when (event) {
            is DetailUiEvent.LoadDetail -> {
                pokemonId.let { loadDetail() }
            }
            DetailUiEvent.RetryClicked -> loadDetail(forceRefresh = true)
            DetailUiEvent.NavigateBack -> { }
        }
    }

    private fun loadDetail(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getPokemonDetailUseCase(pokemonId, forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            pokemon = result.data.pokemon,
                            species = result.data.species,
                            isLoading = false,
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }
}
