package com.lunna.mixjarimpl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lunna.mixjarimpl.db.entities.FollowersEntity
import com.lunna.mixjarimpl.pagingSources.FollowersSource
import com.lunna.mixjarimpl.repository.FollowersRepository
import com.mixsteroids.mixjar.models.UserFollowersData
import com.mixsteroids.mixjar.utils.Mixcloud
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

class FollowersViewModel(
    private val followersRepository: FollowersRepository
):ViewModel() {

    val TAG: String = FollowersViewModel::class.java.simpleName


    fun followers(username:String): Flow<PagingData<UserFollowersData>>{
        return Pager(PagingConfig(20)){
            FollowersSource(username)
        }.flow
    }

//    fun letDoggoImagesFlowDb(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<DoggoImageModel>> {
//        if (appDatabase == null) throw IllegalStateException("Database is not initialized")
//
//        val pagingSourceFactory = { appDatabase.getDoggoImageModelDao().getAllDoggoModel() }
//        return Pager(
//            config = pagingConfig,
//            pagingSourceFactory = pagingSourceFactory,
//            remoteMediator = DoggoMediator(doggoApiService, appDatabase)
//        ).flow
//    }

}