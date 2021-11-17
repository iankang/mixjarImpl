package com.lunna.mixjarimpl.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lunna.mixjarimpl.models.UserInfo
import com.lunna.mixjarimpl.utilities.DataStoreManager
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.CityAndTagPopularResponse
import com.mixsteroids.mixjar.models.TagResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MixjarViewModel(application: Application):AndroidViewModel(application) {

    private val mixCloud = MixCloud()

    private var _tags = MutableLiveData<TagResponse?>()
    val tags: LiveData<TagResponse?> = _tags

    private val dataStore = DataStoreManager(application)

    val userNameDataStore:MutableState<String> = mutableStateOf("")

    val popularPostMutableState:MutableState<CityAndTagPopularResponse?> = mutableStateOf(null)

    val userNameState:MutableState<String> = mutableStateOf("")


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun saveToDataStore(username:String?){
        coroutineScope.launch {
            if (username != null) {
                dataStore.saveToDataStore(username)
            }
            getUserNameDataStore()
        }
    }

    fun removeFromDataStore(){
        coroutineScope.launch {
            dataStore.removeFromDataStore()
            userNameDataStore.value = ""
        }
    }

    fun getUserNameDataStore(){
        coroutineScope.launch {
            userNameDataStore.value = dataStore.username.first()
            Log.e("MixjarVMgetUname",dataStore.username.first().toString())
        }
    }

    private fun gettingTags(): TagResponse? {
        Log.e("mixcloud",mixCloud.toString())
        val results:TagResponse? = mixCloud.getTag("reggae")
        Log.e("results",results?.name.toString())
        val kitu = mixCloud.getCity("Nairobi")
        Log.e("kitu",kitu.toString())
        _tags.postValue(results)
        return results
    }

    fun getTag(){
        coroutineScope.launch {
            gettingTags()
        }
    }

    private suspend fun gettingTrending():CityAndTagPopularResponse? {
        val trendingResponse: CityAndTagPopularResponse? = mixCloud.getTagAndCityPopular("reggae","nairobi",1)
        Log.e("trendingResp",trendingResponse?.data.toString())
        popularPostMutableState.value = trendingResponse
        return trendingResponse
    }

    fun getTrending(){
        coroutineScope.launch {
            gettingTrending()
        }
    }
}