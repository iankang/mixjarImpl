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
               config = PagingConfig(pageSize = 16, prefetchDistance = 16, enablePlaceholders = false, initialLoadSize = 50, maxSize = 50),
               initialKey = 0,
               remoteMediator = FollowersRemoteMediator(username,followersRepository,followersPagingRepository),
               pagingSourceFactory = {followersRepository.getAllFollowersByMainUser(username)}
           ).flow
                .cachedIn(coroutineScope)
    }


}