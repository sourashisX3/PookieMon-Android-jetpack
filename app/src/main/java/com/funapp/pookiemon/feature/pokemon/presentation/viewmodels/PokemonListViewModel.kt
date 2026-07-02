package com.funapp.pookiemon.feature.pokemon.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.pokemon.domain.use_case.GetPokemonListUseCase
import com.funapp.pookiemon.feature.pokemon.presentation.events.PokemonListUiEvent
import com.funapp.pookiemon.feature.pokemon.presentation.states.PokemonListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    init {
        loadPokemon()
    }

    fun onEvent(event: PokemonListUiEvent) {
        when (event) {
            PokemonListUiEvent.LoadPokemon -> loadPokemon(forceRefresh = true)
            PokemonListUiEvent.LoadMorePokemon -> loadMorePokemon()
            is PokemonListUiEvent.PokemonClicked -> { }
            PokemonListUiEvent.RetryClicked -> loadPokemon(forceRefresh = true)
        }
    }

    private fun loadPokemon(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, currentOffset = 0) }

            when (val result = getPokemonListUseCase(forceRefresh = forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            pokemonList = result.data,
                            isLoading = false,
                            hasMore = result.data.size >= GetPokemonListUseCase.PAGE_SIZE,
                            currentOffset = GetPokemonListUseCase.PAGE_SIZE,
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

    private fun loadMorePokemon() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore || !currentState.hasMore) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }

            when (val result = getPokemonListUseCase(offset = currentState.currentOffset)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            pokemonList = it.pokemonList + result.data,
                            isLoadingMore = false,
                            hasMore = result.data.size >= GetPokemonListUseCase.PAGE_SIZE,
                            currentOffset = it.currentOffset + result.data.size,
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoadingMore = false, error = result.message) }
                }
            }
        }
    }
}
