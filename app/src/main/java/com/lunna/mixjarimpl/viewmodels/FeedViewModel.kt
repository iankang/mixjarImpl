package com.lunna.mixjarimpl.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lunna.mixjarimpl.pagingSources.UserFeedSource
import com.lunna.mixjarimpl.utilities.DataStoreManager
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.UserFeedData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FeedViewModel(application: Application):AndroidViewModel(application) {

    private val mixCloud = MixCloud()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val dataStore = DataStoreManager(application)
    private val userNameDataStore: MutableState<String> = mutableStateOf("")

//    val feed: Flow<PagingData<UserFeedData>> = Pager(PagingConfig(pageSize = 20)){
//        Log.e("FeedViewModel","source")
//        UserFeedSource(dataStore.username.collectAsState(initial = "spartacus").value!!,mixCloud)
//    }.flow
    fun feed(username:String): Flow<PagingData<UserFeedData>> {
        return Pager(PagingConfig(pageSize = 20)){
            Log.e("FeedViewModel","source")
            UserFeedSource(username,mixCloud)
        }.flow
    }
    private fun getUserNameDataStore(){
        coroutineScope.launch {
            userNameDataStore.value = dataStore.username.first().toString()
            Log.e("FeedVMgetUname",dataStore.username.first().toString())
        }
    }
}