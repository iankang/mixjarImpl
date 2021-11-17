package com.lunna.mixjarimpl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lunna.mixjarimpl.pagingSources.UserFeedSource
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.UserFeedData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class FeedViewModel:ViewModel() {

    private val mixCloud = MixCloud()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val feed: Flow<PagingData<UserFeedData>> = Pager(PagingConfig(pageSize = 20)){
        UserFeedSource("ian-kangethe",mixCloud)
    }.flow
}