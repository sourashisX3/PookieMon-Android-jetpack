package com.funapp.pookiemon.feature.berry.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.berry.data.datasource.remote.dto.BerryListResponseDto
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

data class BerryListUiState(
    val berries: List<Berry> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class BerryListViewModel @Inject constructor(
    private val apiService: PokeApiService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BerryListUiState())
    val uiState: StateFlow<BerryListUiState> = _uiState
        .onStart { loadBerries() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), BerryListUiState())

    fun loadBerries() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val response = apiService.getBerryList(20, 0)
                val items = response.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    if (id == null) null else Berry(id = id, name = resource.name, growthTime = 0, maxHarvest = 0, size = 0, smoothness = 0, firmness = "", flavors = emptyList())
                }
                _uiState.value = BerryListUiState(berries = items, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = BerryListUiState(error = e.message ?: "Failed to load berries", isLoading = false)
            }
        }
    }
}
