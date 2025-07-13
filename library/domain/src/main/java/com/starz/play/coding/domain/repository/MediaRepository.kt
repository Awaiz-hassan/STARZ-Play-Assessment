package com.starz.play.coding.domain.repository

import com.starz.play.coding.domain.model.search.SearchResult


interface MediaRepository {
    suspend fun search(query: String): Result<SearchResult>
    suspend fun saveLastQuery(query: String)
    suspend fun getLastQuery(): String
}