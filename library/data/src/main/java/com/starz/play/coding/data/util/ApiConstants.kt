package com.starz.play.coding.data.util

object ApiConstants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val CACHE_SIZE_BYTES = 10L * 1024 * 1024 // 10 MB
    const val TIMEOUT_SECONDS = 30L

    // Endpoints
    const val SEARCH_MULTI = "search/multi"

    // Query Params
    const val QUERY = "query"
    const val INCLUDE_ADULT = "include_adult"
    const val LANGUAGE = "language"
    const val PAGE = "page"
}