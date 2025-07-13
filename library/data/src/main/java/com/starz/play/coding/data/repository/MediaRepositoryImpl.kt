package com.starz.play.coding.data.repository

import com.starz.play.coding.data.contract.LocalCache
import com.starz.play.coding.data.contract.RemoteCallExecutor
import com.starz.play.coding.data.dataSource.remote.apiService.ApiService
import com.starz.play.coding.data.mapper.toDomain
import com.starz.play.coding.data.mapper.toSearchResult
import com.starz.play.coding.domain.model.search.SearchResult
import com.starz.play.coding.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val localCache: LocalCache
) : MediaRepository, RemoteCallExecutor() {

    override suspend fun search(query: String): Result<SearchResult> {
        localCache.saveLastQuery(query)

        return safeApiCall { apiService.searchMulti(query) }
            .mapCatching { dto ->
                dto.results.orEmpty()
                    .mapNotNull { it.toDomain() }
                    .toSearchResult()
            }
    }

    override suspend fun saveLastQuery(query: String) {
        localCache.saveLastQuery(query)
    }

    override suspend fun getLastQuery(): String {
        return localCache.getLastQuery()
    }
}
