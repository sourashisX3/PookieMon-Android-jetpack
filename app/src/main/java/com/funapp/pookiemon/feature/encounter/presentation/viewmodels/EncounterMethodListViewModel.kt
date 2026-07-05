package com.funapp.pookiemon.feature.encounter.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.encounter.domain.use_case.GetEncounterMethodListUseCase
import com.funapp.pookiemon.feature.encounter.presentation.events.EncounterMethodListUiEvent
import com.funapp.pookiemon.feature.encounter.presentation.states.EncounterMethodListUiState
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
class EncounterMethodListViewModel @Inject constructor(
    private val getEncounterMethodListUseCase: GetEncounterMethodListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EncounterMethodListUiState())
    val uiState: StateFlow<EncounterMethodListUiState> = _uiState
        .onStart { loadMethods() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), EncounterMethodListUiState())

    fun onEvent(event: EncounterMethodListUiEvent) {
        when (event) {
            EncounterMethodListUiEvent.LoadMethods -> loadMethods(forceRefresh = true)
            EncounterMethodListUiEvent.LoadMoreMethods -> loadMoreMethods()
            is EncounterMethodListUiEvent.MethodClicked -> { }
            EncounterMethodListUiEvent.RetryClicked -> loadMethods(forceRefresh = true)
        }
    }

    private fun loadMethods(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, currentOffset = 0) }
            when (val result = getEncounterMethodListUseCase(forceRefresh = forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            methods = result.data,
                            isLoading = false,
                            hasMore = result.data.size >= GetEncounterMethodListUseCase.PAGE_SIZE,
                            currentOffset = GetEncounterMethodListUseCase.PAGE_SIZE,
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun loadMoreMethods() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore || !currentState.hasMore) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            when (val result = getEncounterMethodListUseCase(offset = currentState.currentOffset)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            methods = it.methods + result.data,
                            isLoadingMore = false,
                            hasMore = result.data.size >= GetEncounterMethodListUseCase.PAGE_SIZE,
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
