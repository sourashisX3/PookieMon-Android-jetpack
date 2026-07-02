package com.funapp.pookiemon.feature.move.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.move.domain.use_case.GetMoveDetailUseCase
import com.funapp.pookiemon.feature.move.presentation.events.MoveDetailUiEvent
import com.funapp.pookiemon.feature.move.presentation.states.MoveDetailUiState
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
class MoveDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMoveDetailUseCase: GetMoveDetailUseCase,
) : ViewModel() {

    private val moveId: Int = savedStateHandle.get<Int>("moveId") ?: 1

    private val _uiState = MutableStateFlow(MoveDetailUiState())
    val uiState: StateFlow<MoveDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MoveDetailUiState())

    fun onEvent(event: MoveDetailUiEvent) {
        when (event) {
            MoveDetailUiEvent.RetryClicked -> loadDetail(forceRefresh = true)
        }
    }

    private fun loadDetail(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getMoveDetailUseCase(moveId, forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(move = result.data, isLoading = false)
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}
