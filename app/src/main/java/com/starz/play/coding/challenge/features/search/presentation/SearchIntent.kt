package com.starz.play.coding.challenge.features.search.presentation

sealed interface SearchIntent {
    data class QueryChanged(val query: String) : SearchIntent
    object Clear : SearchIntent
}