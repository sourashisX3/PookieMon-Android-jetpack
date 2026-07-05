package com.funapp.pookiemon.feature.references.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.model.Nature
import com.funapp.pookiemon.feature.references.domain.model.PokemonType
import com.funapp.pookiemon.feature.references.domain.use_case.GetAbilityDetailUseCase
import com.funapp.pookiemon.feature.references.domain.use_case.GetNatureDetailUseCase
import com.funapp.pookiemon.feature.references.domain.use_case.GetTypeDetailUseCase
import com.funapp.pookiemon.feature.references.presentation.events.RefDetailUiEvent
import com.funapp.pookiemon.feature.references.presentation.states.RefDetailUiState
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
class ReferencesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAbilityDetailUseCase: GetAbilityDetailUseCase,
    private val getTypeDetailUseCase: GetTypeDetailUseCase,
    private val getNatureDetailUseCase: GetNatureDetailUseCase,
) : ViewModel() {

    private val abilityId: Int = savedStateHandle.get<Int>("abilityId") ?: -1
    private val typeId: Int = savedStateHandle.get<Int>("typeId") ?: -1
    private val natureId: Int = savedStateHandle.get<Int>("natureId") ?: -1

    private val _uiState = MutableStateFlow(RefDetailUiState())
    val uiState: StateFlow<RefDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RefDetailUiState())

    fun onEvent(event: RefDetailUiEvent) {
        when (event) {
            RefDetailUiEvent.RetryClicked -> loadDetail(forceRefresh = true)
        }
    }

    private fun loadDetail(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when {
                abilityId > 0 -> {
                    when (val result = getAbilityDetailUseCase(abilityId, forceRefresh)) {
                        is ApiResult.Success -> {
                            _uiState.update { it.copy(ability = result.data, isLoading = false) }
                        }
                        is ApiResult.Error -> {
                            _uiState.update { it.copy(isLoading = false, error = result.message) }
                        }
                    }
                }
                typeId > 0 -> {
                    when (val result = getTypeDetailUseCase(typeId, forceRefresh)) {
                        is ApiResult.Success -> {
                            _uiState.update { it.copy(type = result.data, isLoading = false) }
                        }
                        is ApiResult.Error -> {
                            _uiState.update { it.copy(isLoading = false, error = result.message) }
                        }
                    }
                }
                natureId > 0 -> {
                    when (val result = getNatureDetailUseCase(natureId, forceRefresh)) {
                        is ApiResult.Success -> {
                            _uiState.update { it.copy(nature = result.data, isLoading = false) }
                        }
                        is ApiResult.Error -> {
                            _uiState.update { it.copy(isLoading = false, error = result.message) }
                        }
                    }
                }
                else -> {
                    _uiState.update { it.copy(error = "Invalid reference", isLoading = false) }
                }
            }
        }
    }
}
