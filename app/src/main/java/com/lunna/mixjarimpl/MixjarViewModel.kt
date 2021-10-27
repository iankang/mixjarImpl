package com.lunna.mixjarimpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.TagResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MixjarViewModel:ViewModel() {

    private val mixCloud = MixCloud()

    private var _tags = MutableLiveData<TagResponse?>()
    val tags: LiveData<TagResponse?> = _tags

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
}