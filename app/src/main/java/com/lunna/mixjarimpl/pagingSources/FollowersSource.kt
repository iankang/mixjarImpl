package com.lunna.mixjarimpl.pagingSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.repository.FollowersRepository
import com.lunna.mixjarimpl.repository.toFollowersEntity
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.UserFollowersData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FollowersSource(
    val username:String): PagingSource<Int, UserFollowersData>() {

    val TAG:String = FollowersSource::class.java.simpleName

    val mixCloud = MixCloud()

    override fun getRefreshKey(state: PagingState<Int, UserFollowersData>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserFollowersData> {
        return try {
            val nextPage = params.key ?: 0
            withContext(Dispatchers.IO){
                val followersResponse = mixCloud.getUserFollowers(username,nextPage)
                Log.e("$TAG followersRspn",followersResponse?.data.toString())
//                followersResponse?.data?.forEach { followerData ->
//                    followersRepository.addFollower(followerData.toFollowersEntity(username))
//                }
                LoadResult.Page(
                    data = followersResponse?.data ?: emptyList(),
                    prevKey = if (nextPage == 0) null else nextPage.minus(1),
                    nextKey = if(followersResponse?.paging?.next == null) null else nextPage.plus(1)
                )
            }
        }catch (e:Exception){
            Log.e(TAG,e.message.toString())
            LoadResult.Error(e)
        }
    }
}