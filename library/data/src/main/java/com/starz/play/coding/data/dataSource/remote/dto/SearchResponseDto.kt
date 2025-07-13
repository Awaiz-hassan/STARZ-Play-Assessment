package com.starz.play.coding.data.dataSource.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: ArrayList<ResultDto>? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null
)