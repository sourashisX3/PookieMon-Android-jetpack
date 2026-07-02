package com.funapp.pookiemon.feature.references.presentation

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

data class ReferencesUiState(
    val abilities: List<Ability> = emptyList(),
    val types: List<PokemonType> = emptyList(),
    val natures: List<Nature> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class ReferencesViewModel @Inject constructor(
    private val apiService: PokeApiService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReferencesUiState())
    val uiState: StateFlow<ReferencesUiState> = _uiState
        .onStart { loadAll() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ReferencesUiState())

    fun loadAll() {
        viewModelScope.launch {
            _uiState.value = ReferencesUiState(isLoading = true)
            try {
                val abilResponse = apiService.getAbilityList(20, 0)
                val abilities = abilResponse.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    if (id == null) null else Ability(id = id, name = resource.name, isMainSeries = false, generation = null)
                }

                val typeResponse = apiService.getTypeList(20, 0)
                val types = typeResponse.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    if (id == null) null else PokemonType(id = id, name = resource.name, doubleDamageTo = emptyList(), halfDamageTo = emptyList(), noDamageTo = emptyList(), doubleDamageFrom = emptyList(), halfDamageFrom = emptyList(), noDamageFrom = emptyList())
                }

                val natResponse = apiService.getNatureList(20, 0)
                val natures = natResponse.results.mapNotNull { resource ->
                    val id = resource.url.trimEnd('/').split('/').lastOrNull()?.toIntOrNull()
                    if (id == null) null else Nature(id = id, name = resource.name, increasedStat = null, decreasedStat = null, likesFlavor = null, hatesFlavor = null)
                }

                _uiState.value = ReferencesUiState(abilities = abilities, types = types, natures = natures, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = ReferencesUiState(error = e.message, isLoading = false)
            }
        }
    }
}
