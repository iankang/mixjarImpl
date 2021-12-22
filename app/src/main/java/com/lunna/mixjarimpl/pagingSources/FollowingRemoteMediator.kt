package com.lunna.mixjarimpl.pagingSources

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lunna.mixjarimpl.db.entities.FollowersEntity
import com.lunna.mixjarimpl.db.entities.FollowersPagingEntity
import com.lunna.mixjarimpl.db.entities.FollowingEntity
import com.lunna.mixjarimpl.db.entities.FollowingPagingEntity
import com.lunna.mixjarimpl.repository.FollowingPagingRepository
import com.lunna.mixjarimpl.repository.FollowingRepository
import com.lunna.mixjarimpl.repository.toFollowersEntity
import com.lunna.mixjarimpl.repository.toFollowingEntity
import com.mixsteroids.mixjar.MixCloud
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InvalidObjectException
import java.lang.Exception

@ExperimentalPagingApi
class FollowingRemoteMediator(
    private val username:String,
    private val followingRepository: FollowingRepository,
    private val followingPagingRepository: FollowingPagingRepository
):RemoteMediator<Int,FollowingEntity>() {

    private val DEFAULT_PAGE_INDEX:Int = 0
    private val mixCloud = MixCloud()
    private val TAG:String? = FollowingRemoteMediator::class.simpleName

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FollowingEntity>
    ): MediatorResult {

        return try{
            Log.d("$TAG start","startingMediator")
            val pageKeyData = getKeyPageData(loadType, state)
            Log.d("$TAG pageKeyData",pageKeyData.toString())
            val page = when (pageKeyData) {
                is MediatorResult.Success -> {
                    return pageKeyData
                }
                else -> {
                    pageKeyData as Int
                }
            }

            withContext(Dispatchers.IO){
                Log.d("$TAG page",page.toString())
                val response = mixCloud.getUserFollowing(username = username, limit = state?.config?.pageSize,page = page)
                val isEndOfList = response?.paging?.next == null
                if(loadType == LoadType.REFRESH){
                    Log.d("$TAG clearData","clear Data")
                    withContext(Dispatchers.IO){
                        followingPagingRepository.deleteAll()
                        followingRepository.deleteAllFollowers()
                    }
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response?.data?.map {
//                    Log.d("$TAG keys ",it.toString())
                    FollowingPagingEntity(key = it?.key!!, previousKey = prevKey,nextKey = nextKey)
                }
                if (keys != null) {
                    followingPagingRepository.insertKeys(keys)
                }
                response?.data?.forEach {
                    withContext(Dispatchers.IO){
                        followingRepository.addFollower(it?.toFollowingEntity(username))
                    }
                }
                MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }
        }catch (e:Exception){
            return MediatorResult.Error(e)
        }

    }

    /**
     * this returns the page key or the final end of list success result
     */
    private fun getKeyPageData(loadType: LoadType, state: PagingState<Int, FollowingEntity>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                Log.d("$TAG: refresh","refresh")
                0
            }
            LoadType.APPEND -> {
                Log.d("$TAG: append","append")
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey ?: 0
            }

            LoadType.PREPEND -> {
                MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    private  fun getLastRemoteKey(state: PagingState<Int, FollowingEntity>): FollowingPagingEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { followerPaging -> followingPagingRepository.getPagingByKey(followerPaging.key) }
    }
}