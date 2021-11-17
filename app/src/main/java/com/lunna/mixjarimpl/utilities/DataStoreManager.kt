package com.lunna.mixjarimpl.utilities

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.lunna.mixjarimpl.viewmodels.MixjarViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val USER_DATASTORE ="mixjar_datastore"
class DataStoreManager(val context: Context) {

    companion object {
        private val USERNAME = preferencesKey<String>("username")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = USER_DATASTORE
    )

    suspend fun saveToDataStore(username:String) {
        dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }
    suspend fun removeFromDataStore() {

        dataStore.edit {preferences ->
            preferences.clear()
        }
    }

    val username: Flow<String> = dataStore.data
        .map { preferences ->
            val username = preferences[USERNAME] ?: ""
            username
        }
    //4

}

