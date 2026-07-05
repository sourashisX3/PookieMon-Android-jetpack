package com.funapp.pookiemon.feature.berry.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.berry.domain.use_case.GetBerryListUseCase
import com.funapp.pookiemon.feature.berry.presentation.events.BerryListUiEvent
import com.funapp.pookiemon.feature.berry.presentation.states.BerryListUiState
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
class BerryListViewModel @Inject constructor(
    private val getBerryListUseCase: GetBerryListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BerryListUiState())
    val uiState: StateFlow<BerryListUiState> = _uiState
        .onStart { loadBerries() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), BerryListUiState())

    fun onEvent(event: BerryListUiEvent) {
        when (event) {
            BerryListUiEvent.LoadBerries -> loadBerries(forceRefresh = true)
            BerryListUiEvent.LoadMoreBerries -> loadMoreBerries()
            is BerryListUiEvent.BerryClicked -> { }
            BerryListUiEvent.RetryClicked -> loadBerries(forceRefresh = true)
        }
    }

    private fun loadBerries(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, currentOffset = 0) }
            when (val result = getBerryListUseCase(forceRefresh = forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            berries = result.data,
                            isLoading = false,
                            hasMore = result.data.size >= GetBerryListUseCase.PAGE_SIZE,
                            currentOffset = GetBerryListUseCase.PAGE_SIZE,
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun loadMoreBerries() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore || !currentState.hasMore) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            when (val result = getBerryListUseCase(offset = currentState.currentOffset)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            berries = it.berries + result.data,
                            isLoadingMore = false,
                            hasMore = result.data.size >= GetBerryListUseCase.PAGE_SIZE,
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
