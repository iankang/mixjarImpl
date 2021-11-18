package com.lunna.mixjarimpl.utilities

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.lunna.mixjarimpl.viewmodels.MixjarViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val USER_DATASTORE ="mixjar_datastore"
class DataStoreManager(private val context: Context) {

    companion object {
        private val Context.dataStoree : DataStore<Preferences>  by   preferencesDataStore(
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
            preferences.clear()
        }
    }

    val username: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[USERNAME]
        }
    //4

}

