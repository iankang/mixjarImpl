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
import kotlinx.coroutines.withContext

class ProfileRepository(
    private val db: MixjarImplDB
) {
    private val TAG: String? = ProfileRepository::class.simpleName
    private val mixCloud: MixCloud = MixCloud()

    private val profileDB = db.profileDAO

    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse:LiveData<UserResponse>
        get() = _userResponse



    suspend fun getProfileOnline(key:String){
        withContext(Dispatchers.IO){
            val userResponse = mixCloud.getUser(key)
            Log.e(TAG,userResponse.toString())
            _userResponse.postValue(userResponse)
        }
    }

    suspend fun addProfile(key:String?){
        if (key != null) {
            getProfileOnline(key)
        }
        withContext(Dispatchers.IO){
            if (key != null) {


//                val profileEntity = ProfileEntity(
//                    key = userResponse?.key!!,
//                 url= userResponse.url,
//                 username=userResponse.username,
//                 pictureUrl=userResponse.pictures?.extra_large,
//                 biog= userResponse.biog,
//                 createdTime =  userResponse.created_time,
//                 followerCount= userResponse.follower_count,
//                 followingCount= userResponse.following_count,
//                 cloudCastCount= userResponse.cloudcast_count,
//                 favoriteCount= userResponse.favorite_count,
//                 listenCount= userResponse.listen_count,
//                 is_pro= userResponse.is_pro,
//                 is_premium= userResponse.is_premium,
//                 city= userResponse.city,
//                 country= userResponse.country,
//                 type= userResponse.type
//                )

                userResponse.value?.toProfileEntity()?.let { profileDB.insertProfile(it) }
            }else{
                Log.e(TAG,"key is empty")
            }
        }
    }

    suspend fun getProfile(key: String):MutableState<ProfileEntity?>{
        var mutableStateOfProfile:MutableState<ProfileEntity?> = mutableStateOf(null)
        withContext(Dispatchers.IO){

            val userResponse= profileDB.getProfileByKey(key)

            if(userResponse!= null) {
                Log.e("$TAG getProf", userResponse.toString())
            }
            mutableStateOfProfile.value = userResponse
        }
        return mutableStateOfProfile
    }

    suspend fun deleteProfile(key: String?){
        withContext(Dispatchers.IO){
            key?.let { profileDB.deleteSingleProfileByKey(it) }
        }
    }

    private fun UserResponse.toProfileEntity() = ProfileEntity(
        key = key!!,
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