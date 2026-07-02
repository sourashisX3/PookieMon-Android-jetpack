package com.funapp.pookiemon.feature.berry.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.feature.berry.data.mapper.toDomain
import com.funapp.pookiemon.feature.berry.domain.model.Berry
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokeApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BerryDetailUiState(
    val berry: Berry? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class BerryDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiService: PokeApiService,
) : ViewModel() {

    private val berryId: Int = savedStateHandle.get<Int>("berryId") ?: 1

    private val _uiState = MutableStateFlow(BerryDetailUiState())
    val uiState: StateFlow<BerryDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), BerryDetailUiState())

    private fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = BerryDetailUiState(isLoading = true)
            try {
                val dto = apiService.getBerry(berryId)
                _uiState.value = BerryDetailUiState(berry = dto.toDomain(), isLoading = false)
            } catch (e: Exception) {
                _uiState.value = BerryDetailUiState(error = e.message ?: "Failed to load berry", isLoading = false)
            }
        }
    }
}
