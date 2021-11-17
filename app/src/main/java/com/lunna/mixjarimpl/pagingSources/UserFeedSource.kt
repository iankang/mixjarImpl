package com.lunna.mixjarimpl.pagingSources

import androidx.paging.PagingSource
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.UserFeedData
import com.mixsteroids.mixjar.models.UserFeedResponse

class UserFeedSource(
    private val userName:String,
    private val mixCloud: MixCloud
): PagingSource<Int,UserFeedData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserFeedData> {
       return try {
           val nextPage = params.key ?: 0

           val userFeedResponse = mixCloud.getUserFeed(userName, nextPage)

           LoadResult.Page(
               data = userFeedResponse?.data!!,
               prevKey = if(nextPage == 0) null else nextPage.minus(1),
               nextKey = if(userFeedResponse.paging?.next == null) null else nextPage.plus(1)
           )
       }catch (e:Exception){
           LoadResult.Error(e)
       }
    }
}