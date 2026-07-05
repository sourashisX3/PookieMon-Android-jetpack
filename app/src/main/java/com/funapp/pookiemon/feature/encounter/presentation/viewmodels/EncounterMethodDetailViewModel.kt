package com.funapp.pookiemon.feature.encounter.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.encounter.domain.use_case.GetEncounterMethodDetailUseCase
import com.funapp.pookiemon.feature.encounter.presentation.events.EncounterMethodDetailUiEvent
import com.funapp.pookiemon.feature.encounter.presentation.states.EncounterMethodDetailUiState
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
class EncounterMethodDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getEncounterMethodDetailUseCase: GetEncounterMethodDetailUseCase,
) : ViewModel() {

    private val methodId: Int = savedStateHandle.get<Int>("methodId") ?: 1

    private val _uiState = MutableStateFlow(EncounterMethodDetailUiState())
    val uiState: StateFlow<EncounterMethodDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), EncounterMethodDetailUiState())

    fun onEvent(event: EncounterMethodDetailUiEvent) {
        when (event) {
            EncounterMethodDetailUiEvent.RetryClicked -> loadDetail(forceRefresh = true)
        }
    }

    private fun loadDetail(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getEncounterMethodDetailUseCase(methodId, forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(method = result.data, isLoading = false)
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}
