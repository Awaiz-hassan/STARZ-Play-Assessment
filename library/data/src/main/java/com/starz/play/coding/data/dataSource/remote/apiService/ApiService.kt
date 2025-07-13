package com.starz.play.coding.data.dataSource.remote.apiService

import com.starz.play.coding.data.dataSource.remote.dto.SearchResponseDto
import com.starz.play.coding.data.util.ApiConstants.INCLUDE_ADULT
import com.starz.play.coding.data.util.ApiConstants.LANGUAGE
import com.starz.play.coding.data.util.ApiConstants.PAGE
import com.starz.play.coding.data.util.ApiConstants.QUERY
import com.starz.play.coding.data.util.ApiConstants.SEARCH_MULTI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(SEARCH_MULTI)
    suspend fun searchMulti(
        @Query(QUERY) query: String,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(LANGUAGE) language: String = "en-US",
        @Query(PAGE) page: Int = 1
    ): Response<SearchResponseDto>
}