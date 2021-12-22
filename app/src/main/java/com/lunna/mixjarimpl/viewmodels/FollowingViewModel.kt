package com.lunna.mixjarimpl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.lunna.mixjarimpl.db.entities.FollowingEntity
import com.lunna.mixjarimpl.pagingSources.FollowingRemoteMediator
import com.lunna.mixjarimpl.repository.FollowingPagingRepository
import com.lunna.mixjarimpl.repository.FollowingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

class FollowingViewModel(
    private val followingRepository: FollowingRepository,
    private val followingPagingRepository: FollowingPagingRepository
) : ViewModel(){

    private val TAG:String = FollowingViewModel::class.java.simpleName
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    @ExperimentalPagingApi
    fun following(username:String): Flow<PagingData<FollowingEntity>>{
        return Pager(
            config = PagingConfig(pageSize = 16, prefetchDistance = 16, enablePlaceholders = false, initialLoadSize = 50, maxSize = 50),
            initialKey = 0,
            remoteMediator = FollowingRemoteMediator(username,followingRepository,followingPagingRepository),
            pagingSourceFactory = {followingRepository.getAllFollowersByMainUser(username)}
        ).flow
            .cachedIn(coroutineScope)
    }

}