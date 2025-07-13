package com.starz.play.coding.challenge.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starz.play.coding.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    init {
        restoreLastQuery()
        observeQueryChanges()
    }

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.QueryChanged -> _query.value = intent.query
            is SearchIntent.Clear -> {
                _query.value = ""
                _searchState.value = SearchState.Idle
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeQueryChanges() {
        viewModelScope.launch {
            _query.debounce(400)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collect { performSearch(it) }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _searchState.value = SearchState.Loading
            repository.saveLastQuery(query)
            repository.search(query)
                .onSuccess { _searchState.value = SearchState.Success(it) }
                .onFailure { _searchState.value = SearchState.Error(it.message ?: "Unknown error") }
        }
    }

    private fun restoreLastQuery() {
        viewModelScope.launch {
            val last = repository.getLastQuery()
            if (last.isNotBlank()) {
                _query.value = last
            }
        }
    }
}

