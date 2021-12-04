package com.lunna.mixjarimpl.pagingSources

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lunna.mixjarimpl.db.entities.FollowersEntity
import com.lunna.mixjarimpl.db.entities.FollowersPagingEntity
import com.lunna.mixjarimpl.repository.FollowersPagingRepository
import com.lunna.mixjarimpl.repository.FollowersRepository
import com.lunna.mixjarimpl.repository.toFollowersEntity
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.UserFollowersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InvalidObjectException
import java.lang.Exception

@ExperimentalPagingApi
class FollowersRemoteMediator(
    private val username:String,
    private val followersRepository: FollowersRepository,
    private val followersPagingRepository: FollowersPagingRepository
    ):RemoteMediator<Int,FollowersEntity>() {

    private val DEFAULT_PAGE_INDEX :Int = 0
    private val mixCloud =MixCloud()
    private val TAG: String? = FollowersRemoteMediator::class.simpleName

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FollowersEntity>
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
                val response = mixCloud.getUserFollowers(username = username,page = page)
                Log.d("$TAG response",response.toString())
                val isEndOfList = response?.paging?.next == null

                if(loadType == LoadType.REFRESH){
                    followersPagingRepository.deleteAll()
                    followersRepository.deleteAllFollowers()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response?.data?.map {
                    Log.d("$TAG keys ",it.toString())
                    FollowersPagingEntity(key = it?.key!!, previousKey = prevKey,nextKey = nextKey)
                }

                if (keys != null) {
                    followersPagingRepository.insertKeys(keys)
                }
                response?.data?.forEach {
                    followersRepository.addFollower(it?.toFollowersEntity(username))
                }
                MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }


        }catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }
    private  fun getFirstRemoteKey(state: PagingState<Int, FollowersEntity>): FollowersPagingEntity? {
        return state.pages
            .firstOrNull{ it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { followerPaging -> followersPagingRepository.getPagingByKey(followerPaging.key) }
    }

    private  fun getLastRemoteKey(state: PagingState<Int, FollowersEntity>): FollowersPagingEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { followerPaging -> followersPagingRepository.getPagingByKey(followerPaging.key) }
    }

    private fun getClosestRemoteKey(state: PagingState<Int, FollowersEntity>): FollowersPagingEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.key?.let { keyId ->
                followersPagingRepository.getPagingByKey(keyId)
            }
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    private fun getKeyPageData(loadType: LoadType, state: PagingState<Int, FollowersEntity>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                Log.d("$TAG: refresh","refresh")
                    val remoteKeys = getClosestRemoteKey(state)
                    remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                Log.d("$TAG: append","append")
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey ?: 0
            }
            LoadType.PREPEND -> {
                Log.d("$TAG: append","append")
                MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }
}
