package com.lunna.mixjarimpl

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lunna.mixjarimpl.models.UserInfo
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.CityAndTagPopularResponse
import com.mixsteroids.mixjar.models.TagResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MixjarViewModel:ViewModel() {

    private val mixCloud = MixCloud()

    private var _tags = MutableLiveData<TagResponse?>()
    val tags: LiveData<TagResponse?> = _tags

    val popularPostMutableState:MutableState<CityAndTagPopularResponse?> = mutableStateOf(null)
    val userInfoMutableState: MutableState<UserInfo?> = mutableStateOf(null)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

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