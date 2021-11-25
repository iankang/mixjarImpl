package com.lunna.mixjarimpl.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lunna.mixjarimpl.db.entities.ProfileEntity
import com.lunna.mixjarimpl.repository.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository):ViewModel() {

    val job = Job()
    val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    private val TAG:String = ProfileViewModel::class.java.simpleName

    var profileMutableState:MutableState<ProfileEntity?> = mutableStateOf(null)

    fun addProfileByKey(key:String){
        coroutineScope.launch {
            profileRepository.addProfile(key)
        }
    }

    fun getProfileByKey(key:String){
        coroutineScope.launch {
            profileMutableState = profileRepository.getProfile(key)
            Log.e(TAG,profileMutableState.value.toString())
        }
    }
}