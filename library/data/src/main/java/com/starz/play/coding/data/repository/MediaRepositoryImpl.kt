package com.starz.play.coding.data.repository

import com.starz.play.coding.data.contract.LocalCache
import com.starz.play.coding.data.contract.RemoteCallExecutor
import com.starz.play.coding.data.dataSource.remote.apiService.ApiService
import com.starz.play.coding.data.mapper.toDomain
import com.starz.play.coding.data.mapper.toSearchResult
import com.starz.play.coding.domain.model.search.SearchResult
import com.starz.play.coding.domain.repository.MediaRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val localCache: LocalCache
) : MediaRepository, RemoteCallExecutor() {

    override suspend fun search(query: String): Result<SearchResult> = withContext(IO) {
        localCache.saveLastQuery(query)

        return@withContext safeApiCall { apiService.searchMulti(query) }
            .mapCatching { dto ->
                dto.results.orEmpty()
                    .mapNotNull { it.toDomain() }
                    .toSearchResult()
            }
    }

    override suspend fun saveLastQuery(query: String) = withContext(IO) {
        localCache.saveLastQuery(query)
    }

    override suspend fun getLastQuery(): String = withContext(IO){
        return@withContext localCache.getLastQuery()
    }
}
