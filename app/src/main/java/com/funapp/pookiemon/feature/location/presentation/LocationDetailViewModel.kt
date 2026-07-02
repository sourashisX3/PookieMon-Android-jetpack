package com.funapp.pookiemon.feature.location.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.feature.location.data.mapper.toDomain
import com.funapp.pookiemon.feature.location.domain.model.Location
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokeApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LocationDetailUiState(
    val location: Location? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiService: PokeApiService,
) : ViewModel() {

    private val locationId: Int = savedStateHandle.get<Int>("locationId") ?: 1

    private val _uiState = MutableStateFlow(LocationDetailUiState())
    val uiState: StateFlow<LocationDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LocationDetailUiState())

    private fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = LocationDetailUiState(isLoading = true)
            try {
                val dto = apiService.getLocation(locationId)
                _uiState.value = LocationDetailUiState(location = dto.toDomain(), isLoading = false)
            } catch (e: Exception) {
                _uiState.value = LocationDetailUiState(error = e.message, isLoading = false)
            }
        }
    }
}
