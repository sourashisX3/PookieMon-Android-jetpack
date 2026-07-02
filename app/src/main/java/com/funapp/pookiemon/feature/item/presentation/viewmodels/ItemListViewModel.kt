package com.funapp.pookiemon.feature.item.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.item.domain.use_case.GetItemListUseCase
import com.funapp.pookiemon.feature.item.presentation.events.ItemListUiEvent
import com.funapp.pookiemon.feature.item.presentation.states.ItemListUiState
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
class ItemListViewModel @Inject constructor(
    private val getItemListUseCase: GetItemListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemListUiState())
    val uiState: StateFlow<ItemListUiState> = _uiState
        .onStart { loadItems() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ItemListUiState())

    fun onEvent(event: ItemListUiEvent) {
        when (event) {
            ItemListUiEvent.LoadItems -> loadItems(forceRefresh = true)
            ItemListUiEvent.LoadMoreItems -> loadMoreItems()
            is ItemListUiEvent.ItemClicked -> { }
            ItemListUiEvent.RetryClicked -> loadItems(forceRefresh = true)
        }
    }

    private fun loadItems(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, currentOffset = 0) }
            when (val result = getItemListUseCase(forceRefresh = forceRefresh)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            items = result.data,
                            isLoading = false,
                            hasMore = result.data.size >= GetItemListUseCase.PAGE_SIZE,
                            currentOffset = GetItemListUseCase.PAGE_SIZE,
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun loadMoreItems() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore || !currentState.hasMore) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            when (val result = getItemListUseCase(offset = currentState.currentOffset)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            items = it.items + result.data,
                            isLoadingMore = false,
                            hasMore = result.data.size >= GetItemListUseCase.PAGE_SIZE,
                            currentOffset = it.currentOffset + result.data.size,
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoadingMore = false, error = result.message) }
                }
            }
        }
    }
}
