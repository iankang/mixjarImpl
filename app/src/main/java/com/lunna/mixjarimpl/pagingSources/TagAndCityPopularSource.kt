package com.lunna.mixjarimpl.pagingSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.CityAndTagPopularResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TagAndCityPopularSource(
    private val mixCloud: MixCloud
) : PagingSource<Int, CityAndTagPopularResponseData>() {
    override fun getRefreshKey(state: PagingState<Int, CityAndTagPopularResponseData>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CityAndTagPopularResponseData> {
        return try {
            var nextPage = params.key ?: 0
            withContext(Dispatchers.IO) {
                var popularInArea = mixCloud.getTagAndCityPopular("reggae", "nairobi", nextPage)
                Log.e("popularInArea", popularInArea?.data.toString())
                LoadResult.Page(
                    data = popularInArea?.data?.requireNoNulls() ?: emptyList(),
                    prevKey = if (nextPage == 0) null else nextPage.minus(1),
                    nextKey = if (popularInArea?.paging?.next == null) null else nextPage.plus(1)
                )
            }
        } catch (e: Exception) {
            Log.e("PagingErrorPopularArea", e.message.toString())
            LoadResult.Error(e)
        }
    }

}