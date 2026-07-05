package com.funapp.pookiemon.feature.games.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.games.domain.use_case.GetGamesListUseCase
import com.funapp.pookiemon.feature.games.presentation.events.GamesUiEvent
import com.funapp.pookiemon.feature.games.presentation.states.GamesUiState
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
class GamesViewModel @Inject constructor(
    private val getGamesListUseCase: GetGamesListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GamesUiState())
    val uiState: StateFlow<GamesUiState> = _uiState
        .onStart { loadData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), GamesUiState())

    fun onEvent(event: GamesUiEvent) {
        when (event) {
            GamesUiEvent.LoadData -> loadData(forceRefresh = true)
            GamesUiEvent.RetryClicked -> loadData(forceRefresh = true)
        }
    }

    private fun loadData(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getGamesListUseCase(forceRefresh = forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            generations = result.data.generations,
                            versions = result.data.versions,
                            pokedexes = result.data.pokedexes,
                            isLoading = false,
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}
