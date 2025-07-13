package com.starz.play.coding.challenge.features.search.presentation

import com.starz.play.coding.domain.model.search.SearchResult

sealed interface SearchState {
    object Idle : SearchState
    object Loading : SearchState
    data class Success(val result: SearchResult) : SearchState
    data class Error(val message: String) : SearchState
}