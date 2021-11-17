package com.lunna.mixjarimpl.pagingSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.UserFeedData
import com.mixsteroids.mixjar.models.UserFeedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserFeedSource(
    private val userName:String,
    private val mixCloud: MixCloud
): PagingSource<Int,UserFeedData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserFeedData> {
       return try {
           val nextPage = params.key ?: 0
           withContext(Dispatchers.IO) {
               Log.e("UserFeedUsername", userName)
               Log.e("UserFeedPage", nextPage.toString())
               var userFeedResponse = mixCloud.getUserFeed(userName, nextPage)
               Log.e("UserFeedSource", userFeedResponse.toString())
               LoadResult.Page(
                   data = userFeedResponse?.data ?: emptyList(),
                   prevKey = if (nextPage == 0) null else nextPage.minus(1),
                   nextKey = if (userFeedResponse?.paging?.next == null) null else nextPage.plus(1)
               )
           }
       }catch (e:Exception){
           Log.e("PagingSourceError",e.message.toString())
           LoadResult.Error(e)
       }
    }

    override fun getRefreshKey(state: PagingState<Int, UserFeedData>): Int? {
        TODO("Not yet implemented")
    }
}