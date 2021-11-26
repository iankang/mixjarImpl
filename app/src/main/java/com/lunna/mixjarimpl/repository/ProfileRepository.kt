package com.lunna.mixjarimpl.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.dao.ProfileDAO
import com.lunna.mixjarimpl.db.entities.ProfileEntity
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.UserResponse
import com.mixsteroids.mixjar.utils.Mixcloud
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class ProfileRepository(
    private val db: MixjarImplDB
) {
    private val TAG: String? = ProfileRepository::class.simpleName
    private val mixCloud: MixCloud = MixCloud()

//    private val profileDB = db.profileDAO

    private val _userResponse = MutableStateFlow<UserResponse?>(null)
    val userResponse:StateFlow<UserResponse?> = _userResponse


    val mutableStateOfProfile:MutableState<ProfileEntity?> = mutableStateOf(null)


     fun getProfileOnline(key:String): UserResponse? {
        val userResponse:UserResponse? = mixCloud.getUser(key)
        Log.e("$TAG getProfileOnline",userResponse.toString())
       return userResponse
    }

     fun addProfile(key:String?){
            if (key != null) {
                val userResponse:UserResponse? = getProfileOnline(key)
                Log.e(TAG,userResponse.toString())
                db.profileDAO.insertProfile(userResponse?.toProfileEntity())
            }else{
                Log.e(TAG,"key is empty")
            }
    }

    fun getProfile(key: String): ProfileEntity? {
            val userResponse= db.profileDAO.getProfileByKey(key)
            Log.e("$TAG getProf", userResponse.toString())
            mutableStateOfProfile.value = userResponse
        return userResponse
    }

    fun deleteProfile(key: String?){
        if (key != null) {
            db.profileDAO.deleteSingleProfileByKey(key)
        }
    }
}

private fun UserResponse.toProfileEntity() = key?.let {
    ProfileEntity(
        key = it,
        url = url,
        username = username,
        pictureUrl = pictures?.extra_large,
        biog= biog,
        createdTime = created_time,
        followerCount = follower_count,
        followingCount = following_count,
        cloudCastCount = cloudcast_count,
        favoriteCount = favorite_count,
        listenCount = listen_count,
        is_pro = is_pro,
        is_premium = is_premium,
        city = city,
        country = country,
        type = type
    )
}