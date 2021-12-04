package com.lunna.mixjarimpl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lunna.mixjarimpl.db.entities.FollowersEntity
import com.lunna.mixjarimpl.pagingSources.FollowersRemoteMediator
import com.lunna.mixjarimpl.pagingSources.FollowersSource
import com.lunna.mixjarimpl.repository.FollowersPagingRepository
import com.lunna.mixjarimpl.repository.FollowersRepository
import kotlinx.coroutines.flow.Flow

class FollowersViewModel(
    private val followersRepository: FollowersRepository,
    private val followersPagingRepository: FollowersPagingRepository
):ViewModel() {

    val TAG: String = FollowersViewModel::class.java.simpleName

    @ExperimentalPagingApi
    fun followers(username:String): Flow<PagingData<FollowersEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 10, enablePlaceholders = false, initialLoadSize = 20),
            initialKey = 0,
            remoteMediator = FollowersRemoteMediator(username,followersRepository,followersPagingRepository),
            pagingSourceFactory = {followersRepository.getAllFollowersByMainUser(username)}
        ).flow
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