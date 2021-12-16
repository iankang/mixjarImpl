package com.lunna.mixjarimpl.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lunna.mixjarimpl.db.entities.FollowingEntity

class FollowingSource(
    username:String?
): PagingSource<Int, FollowingEntity>() {
    override fun getRefreshKey(state: PagingState<Int, FollowingEntity>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowingEntity> {
        TODO("Not yet implemented")
    }
}