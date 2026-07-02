package com.funapp.pookiemon.feature.games.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.feature.games.data.mapper.toDomain
import com.funapp.pookiemon.feature.games.domain.model.Generation
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokeApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GamesDetailUiState(
    val generation: Generation? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class GamesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiService: PokeApiService,
) : ViewModel() {

    private val genId: Int = savedStateHandle.get<Int>("genId") ?: 1

    private val _uiState = MutableStateFlow(GamesDetailUiState())
    val uiState: StateFlow<GamesDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), GamesDetailUiState())

    private fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = GamesDetailUiState(isLoading = true)
            try {
                val dto = apiService.getGeneration(genId)
                _uiState.value = GamesDetailUiState(generation = dto.toDomain(), isLoading = false)
            } catch (e: Exception) {
                _uiState.value = GamesDetailUiState(error = e.message, isLoading = false)
            }
        }
    }
}
