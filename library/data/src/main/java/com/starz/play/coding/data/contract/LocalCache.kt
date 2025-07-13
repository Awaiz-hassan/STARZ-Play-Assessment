package com.starz.play.coding.data.contract

interface LocalCache {
    suspend fun saveLastQuery(query: String)
    suspend fun getLastQuery(): String
}