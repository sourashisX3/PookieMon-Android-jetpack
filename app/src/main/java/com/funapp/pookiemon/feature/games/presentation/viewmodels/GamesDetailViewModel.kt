package com.funapp.pookiemon.feature.games.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.games.domain.use_case.GetGenerationDetailUseCase
import com.funapp.pookiemon.feature.games.presentation.events.GamesDetailUiEvent
import com.funapp.pookiemon.feature.games.presentation.states.GamesDetailUiState
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
class GamesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGenerationDetailUseCase: GetGenerationDetailUseCase,
) : ViewModel() {

    private val genId: Int = savedStateHandle.get<Int>("genId") ?: 1

    private val _uiState = MutableStateFlow(GamesDetailUiState())
    val uiState: StateFlow<GamesDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), GamesDetailUiState())

    fun onEvent(event: GamesDetailUiEvent) {
        when (event) {
            GamesDetailUiEvent.RetryClicked -> loadDetail(forceRefresh = true)
        }
    }

    private fun loadDetail(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getGenerationDetailUseCase(genId, forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(generation = result.data, isLoading = false)
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}
