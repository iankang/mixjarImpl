package com.lunna.mixjarimpl.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lunna.mixjarimpl.models.UserInfo
import com.lunna.mixjarimpl.pagingSources.TagAndCityPopularSource
import com.lunna.mixjarimpl.utilities.DataStoreManager
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.CityAndTagPopularResponse
import com.mixsteroids.mixjar.models.CityAndTagPopularResponseData
import com.mixsteroids.mixjar.models.TagResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MixjarViewModel(application: Application):AndroidViewModel(application) {

    private val mixCloud = MixCloud()

    private var _tags = MutableLiveData<TagResponse?>()
    val tags: LiveData<TagResponse?> = _tags

    val popularPostMutableState:MutableState<CityAndTagPopularResponse?> = mutableStateOf(null)

    val userNameState:MutableState<String> = mutableStateOf("")


    private val coroutineScope = CoroutineScope(Dispatchers.IO)


//    private suspend fun gettingTrending():CityAndTagPopularResponse? {
//        val trendingResponse: CityAndTagPopularResponse? = mixCloud.getTagAndCityPopular("reggae","nairobi",1)
//        Log.e("trendingResp",trendingResponse?.data.toString())
//        popularPostMutableState.value = trendingResponse
//        return trendingResponse
//    }
//
//    fun getTrending(){
//        coroutineScope.launch {
//            gettingTrending()
//        }
//    }

    fun getPopularInYourArea(): Flow<PagingData<CityAndTagPopularResponseData>> {
        return Pager(PagingConfig(pageSize = 20)){
            Log.e("popularVM", "paging")
            TagAndCityPopularSource(mixCloud)
        }.flow
    }
}