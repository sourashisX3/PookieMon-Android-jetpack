package com.funapp.pookiemon.feature.move.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.move.domain.use_case.GetMoveListUseCase
import com.funapp.pookiemon.feature.move.presentation.events.MoveListUiEvent
import com.funapp.pookiemon.feature.move.presentation.states.MoveListUiState
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
class MoveListViewModel @Inject constructor(
    private val getMoveListUseCase: GetMoveListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoveListUiState())
    val uiState: StateFlow<MoveListUiState> = _uiState
        .onStart { loadMoves() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MoveListUiState())

    fun onEvent(event: MoveListUiEvent) {
        when (event) {
            MoveListUiEvent.LoadMoves -> loadMoves(forceRefresh = true)
            MoveListUiEvent.LoadMoreMoves -> loadMoreMoves()
            is MoveListUiEvent.MoveClicked -> { }
            MoveListUiEvent.RetryClicked -> loadMoves(forceRefresh = true)
        }
    }

    private fun loadMoves(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, currentOffset = 0) }
            when (val result = getMoveListUseCase(forceRefresh = forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            moves = result.data,
                            isLoading = false,
                            hasMore = result.data.size >= GetMoveListUseCase.PAGE_SIZE,
                            currentOffset = GetMoveListUseCase.PAGE_SIZE,
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun loadMoreMoves() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore || !currentState.hasMore) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            when (val result = getMoveListUseCase(offset = currentState.currentOffset)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            moves = it.moves + result.data,
                            isLoadingMore = false,
                            hasMore = result.data.size >= GetMoveListUseCase.PAGE_SIZE,
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
