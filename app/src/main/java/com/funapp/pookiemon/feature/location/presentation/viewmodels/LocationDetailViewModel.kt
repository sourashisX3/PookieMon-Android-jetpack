package com.funapp.pookiemon.feature.location.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.location.domain.use_case.GetLocationDetailUseCase
import com.funapp.pookiemon.feature.location.presentation.events.LocationDetailUiEvent
import com.funapp.pookiemon.feature.location.presentation.states.LocationDetailUiState
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
class LocationDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLocationDetailUseCase: GetLocationDetailUseCase,
) : ViewModel() {

    private val locationId: Int = savedStateHandle.get<Int>("locationId") ?: 1

    private val _uiState = MutableStateFlow(LocationDetailUiState())
    val uiState: StateFlow<LocationDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LocationDetailUiState())

    fun onEvent(event: LocationDetailUiEvent) {
        when (event) {
            LocationDetailUiEvent.RetryClicked -> loadDetail(forceRefresh = true)
        }
    }

    private fun loadDetail(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getLocationDetailUseCase(locationId, forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(location = result.data, isLoading = false)
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}
