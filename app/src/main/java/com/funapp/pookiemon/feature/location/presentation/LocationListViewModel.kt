package com.funapp.pookiemon.feature.location.presentation

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

data class LocationListUiState(
    val locations: List<Location> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class LocationListViewModel @Inject constructor(
    private val apiService: PokeApiService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationListUiState())
    val uiState: StateFlow<LocationListUiState> = _uiState
        .onStart { loadLocations() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LocationListUiState())

    fun loadLocations() {
        viewModelScope.launch {
            _uiState.value = LocationListUiState(isLoading = true)
            try {
                val response = apiService.getLocationList(20, 0)
                val locations = response.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    if (id == null) null else Location(id = id, name = resource.name, region = null, areas = emptyList())
                }
                _uiState.value = LocationListUiState(locations = locations, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = LocationListUiState(error = e.message, isLoading = false)
            }
        }
    }
}
