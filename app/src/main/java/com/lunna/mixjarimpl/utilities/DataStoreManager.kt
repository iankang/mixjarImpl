package com.lunna.mixjarimpl.utilities

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val USER_DATASTORE ="mixjar_datastore"
class DataStoreManager(private val context: Context) {

    companion object {
        private val Context.dataStoree : DataStore<Preferences>  by preferencesDataStore(
            USER_DATASTORE)
        val USERNAME = stringPreferencesKey("username")
    }



    suspend fun saveToDataStore(username:String) {
        context.dataStoree.edit { preferences ->
            preferences[USERNAME] = username
        }
    }
    suspend fun removeFromDataStore() {

        context.dataStoree.edit {preferences ->
            preferences.remove(USERNAME)
        }
    }

    val username: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[USERNAME]
        }
    //4

}

