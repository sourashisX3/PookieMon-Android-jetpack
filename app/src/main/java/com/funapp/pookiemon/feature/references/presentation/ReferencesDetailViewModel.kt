package com.funapp.pookiemon.feature.references.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.feature.pokemon.data.datasource.remote.PokeApiService
import com.funapp.pookiemon.feature.references.data.mapper.toDomain
import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.model.Nature
import com.funapp.pookiemon.feature.references.domain.model.PokemonType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ReferenceType {
    data class AbilityRef(val id: Int) : ReferenceType
    data class TypeRef(val id: Int) : ReferenceType
    data class NatureRef(val id: Int) : ReferenceType
}

data class RefDetailUiState(
    val ability: Ability? = null,
    val type: PokemonType? = null,
    val nature: Nature? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class ReferencesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiService: PokeApiService,
) : ViewModel() {

    private val abilityId: Int = savedStateHandle.get<Int>("abilityId") ?: -1
    private val typeId: Int = savedStateHandle.get<Int>("typeId") ?: -1
    private val natureId: Int = savedStateHandle.get<Int>("natureId") ?: -1

    private val _uiState = MutableStateFlow(RefDetailUiState())
    val uiState: StateFlow<RefDetailUiState> = _uiState
        .onStart { loadDetail() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RefDetailUiState())

    private fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = RefDetailUiState(isLoading = true)
            try {
                when {
                    abilityId > 0 -> {
                        val dto = apiService.getAbility(abilityId)
                        _uiState.value = RefDetailUiState(ability = dto.toDomain(), isLoading = false)
                    }
                    typeId > 0 -> {
                        val dto = apiService.getType(typeId)
                        _uiState.value = RefDetailUiState(type = dto.toDomain(), isLoading = false)
                    }
                    natureId > 0 -> {
                        val dto = apiService.getNature(natureId)
                        _uiState.value = RefDetailUiState(nature = dto.toDomain(), isLoading = false)
                    }
                    else -> {
                        _uiState.value = RefDetailUiState(error = "Invalid reference", isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RefDetailUiState(error = e.message, isLoading = false)
            }
        }
    }
}
