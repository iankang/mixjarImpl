package com.lunna.mixjarimpl.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.lunna.mixjarimpl.db.entities.FollowersEntity
import com.lunna.mixjarimpl.pagingSources.FollowersRemoteMediator
import com.lunna.mixjarimpl.pagingSources.FollowersSource
import com.lunna.mixjarimpl.repository.FollowersPagingRepository
import com.lunna.mixjarimpl.repository.FollowersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FollowersViewModel(
    private val followersRepository: FollowersRepository,
    private val followersPagingRepository: FollowersPagingRepository
):ViewModel() {

    private val TAG: String = FollowersViewModel::class.java.simpleName
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    private val followersRepositoryPagingSource:MutableState<PagingSource<Int,FollowersEntity>?> = mutableStateOf(value = null)

    @ExperimentalPagingApi
    fun followers(username:String): Flow<PagingData<FollowersEntity>> {
            return Pager(
               config = PagingConfig(pageSize = 20, prefetchDistance = 10, enablePlaceholders = false, initialLoadSize = 20),
               initialKey = 0,
               remoteMediator = FollowersRemoteMediator(username,followersRepository,followersPagingRepository),
               pagingSourceFactory = {followersRepository.getAllFollowersByMainUser(username)}
           ).flow
                .cachedIn(coroutineScope)
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