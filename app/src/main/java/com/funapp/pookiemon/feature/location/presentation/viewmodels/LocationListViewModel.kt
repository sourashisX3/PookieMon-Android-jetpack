package com.funapp.pookiemon.feature.location.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.location.domain.use_case.GetLocationListUseCase
import com.funapp.pookiemon.feature.location.presentation.events.LocationListUiEvent
import com.funapp.pookiemon.feature.location.presentation.states.LocationListUiState
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
class LocationListViewModel @Inject constructor(
    private val getLocationListUseCase: GetLocationListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationListUiState())
    val uiState: StateFlow<LocationListUiState> = _uiState
        .onStart { loadLocations() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LocationListUiState())

    fun onEvent(event: LocationListUiEvent) {
        when (event) {
            LocationListUiEvent.LoadLocations -> loadLocations(forceRefresh = true)
            LocationListUiEvent.LoadMoreLocations -> loadMoreLocations()
            is LocationListUiEvent.LocationClicked -> { }
            LocationListUiEvent.RetryClicked -> loadLocations(forceRefresh = true)
        }
    }

    private fun loadLocations(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, currentOffset = 0) }
            when (val result = getLocationListUseCase(forceRefresh = forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            locations = result.data,
                            isLoading = false,
                            hasMore = result.data.size >= GetLocationListUseCase.PAGE_SIZE,
                            currentOffset = GetLocationListUseCase.PAGE_SIZE,
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun loadMoreLocations() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore || !currentState.hasMore) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            when (val result = getLocationListUseCase(offset = currentState.currentOffset)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            locations = it.locations + result.data,
                            isLoadingMore = false,
                            hasMore = result.data.size >= GetLocationListUseCase.PAGE_SIZE,
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
