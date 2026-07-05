package com.funapp.pookiemon.feature.berry.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.berry.domain.use_case.GetBerryDetailUseCase
import com.funapp.pookiemon.feature.berry.presentation.events.BerryDetailUiEvent
import com.funapp.pookiemon.feature.berry.presentation.states.BerryDetailUiState
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
class BerryDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBerryDetailUseCase: GetBerryDetailUseCase,
) : ViewModel() {

    private val berryId: Int = savedStateHandle.get<Int>("berryId") ?: 1

    private val _uiState = MutableStateFlow(BerryDetailUiState())
    val uiState: StateFlow<BerryDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), BerryDetailUiState())

    fun onEvent(event: BerryDetailUiEvent) {
        when (event) {
            BerryDetailUiEvent.RetryClicked -> loadDetail(forceRefresh = true)
        }
    }

    private fun loadDetail(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getBerryDetailUseCase(berryId, forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(berry = result.data, isLoading = false)
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}
