package com.funapp.pookiemon.feature.encounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.feature.encounter.data.mapper.toDomain
import com.funapp.pookiemon.feature.encounter.domain.model.EncounterMethod
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokeApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EncounterListUiState(
    val methods: List<EncounterMethod> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class EncounterListViewModel @Inject constructor(
    private val apiService: PokeApiService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EncounterListUiState())
    val uiState: StateFlow<EncounterListUiState> = _uiState
        .onStart { loadMethods() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), EncounterListUiState())

    fun loadMethods() {
        viewModelScope.launch {
            _uiState.value = EncounterListUiState(isLoading = true)
            try {
                val response = apiService.getEncounterMethodList(20, 0)
                val methods = response.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    if (id == null) null else EncounterMethod(id = id, name = resource.name, order = 0)
                }
                _uiState.value = EncounterListUiState(methods = methods, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = EncounterListUiState(error = e.message, isLoading = false)
            }
        }
    }
}
