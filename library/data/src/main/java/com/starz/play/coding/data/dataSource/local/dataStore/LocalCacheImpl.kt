package com.starz.play.coding.data.dataSource.local.dataStore


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.starz.play.coding.data.contract.LocalCache
import com.starz.play.coding.data.util.StorageKeys.LAST_QUERY_KEY
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class LocalCacheImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalCache {

    private val lastQueryKey = stringPreferencesKey(LAST_QUERY_KEY)

    override suspend fun saveLastQuery(query: String) {
        dataStore.edit { prefs ->
            prefs[lastQueryKey] = query
        }
    }

    override suspend fun getLastQuery(): String {
        return dataStore.data.first()[lastQueryKey] ?: ""
    }
}