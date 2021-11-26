package com.lunna.mixjarimpl.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lunna.mixjarimpl.db.entities.ProfileEntity
import com.lunna.mixjarimpl.repository.ProfileRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(private val profileRepository: ProfileRepository):ViewModel() {

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(job)

    private val TAG:String = ProfileViewModel::class.java.simpleName

    private val _profileMutableState = MutableStateFlow<ProfileEntity?>(null)
    val profileMutableState:StateFlow<ProfileEntity?> = _profileMutableState

    private val _isLoading = MutableStateFlow(true)
    val isLoading:StateFlow<Boolean> = _isLoading


    fun addProfileByKey(key:String){
        coroutineScope.launch {
            _isLoading.value = true
            profileRepository.addProfile(key)
            val userResponse = profileRepository.getProfile(key)
            _profileMutableState.value = userResponse
            _isLoading.value = false
        }
    }

    fun getProfileByKey(key:String){
        coroutineScope.launch {

            Log.e(TAG+"profVM",profileMutableState.value.toString())
        }
    }
}