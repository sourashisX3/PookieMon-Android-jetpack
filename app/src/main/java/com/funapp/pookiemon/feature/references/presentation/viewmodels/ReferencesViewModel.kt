package com.funapp.pookiemon.feature.references.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.references.domain.use_case.GetReferencesListUseCase
import com.funapp.pookiemon.feature.references.presentation.events.ReferencesUiEvent
import com.funapp.pookiemon.feature.references.presentation.states.ReferencesUiState
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
class ReferencesViewModel @Inject constructor(
    private val getReferencesListUseCase: GetReferencesListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReferencesUiState())
    val uiState: StateFlow<ReferencesUiState> = _uiState
        .onStart { loadData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ReferencesUiState())

    fun onEvent(event: ReferencesUiEvent) {
        when (event) {
            ReferencesUiEvent.LoadData -> loadData(forceRefresh = true)
            ReferencesUiEvent.RetryClicked -> loadData(forceRefresh = true)
        }
    }

    private fun loadData(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getReferencesListUseCase(forceRefresh = forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            abilities = result.data.abilities,
                            types = result.data.types,
                            natures = result.data.natures,
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
