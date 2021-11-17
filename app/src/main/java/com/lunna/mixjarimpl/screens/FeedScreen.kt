package com.lunna.mixjarimpl.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mixsteroids.mixjar.models.UserFeedData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.items
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.lunna.mixjarimpl.utilities.ErrorItem
import com.lunna.mixjarimpl.utilities.LoadingItem
import com.lunna.mixjarimpl.utilities.LoadingScreen
import com.lunna.mixjarimpl.utilities.LoadingView
import com.lunna.mixjarimpl.viewmodels.FeedViewModel
import com.mixsteroids.mixjar.models.UserFeedCloudcasts


@Composable
fun FeedScreen(feedViewModel: FeedViewModel){
        FeedList(feedViewModel.feed)
}
@Composable
@Preview()
fun FeedScreenPreview(){
    MixjarImplTheme {
        FeedListPreview()
    }
}

@Composable
fun FeedList(feed: Flow<PagingData<UserFeedData>>){
    val lazyFeedItems: LazyPagingItems<UserFeedData> = feed.collectAsLazyPagingItems()
    LazyColumn {
       items(lazyFeedItems){userFeedData ->
           userFeedData?.let {FeedItem(it.key) }
       }

        lazyFeedItems.apply {
            when{
                loadState.refresh is LoadState.Loading ->{
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading ->{
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error ->{
                    val e = lazyFeedItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = {retry()}
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyFeedItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage,
                            onClickRetry = {retry()}
                        )
                    }
                }
            }
        }
    }
}
@Composable
@Preview(name = "light")
@Preview(name = "dark",uiMode = UI_MODE_NIGHT_YES)
fun FeedListPreview(){
    MixjarImplTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(10){
                FeedItemPreview()
            }
        }
    }

}

@Composable
fun FeedItem(title:String){
    Text(
        text = title,
        color = MaterialTheme.colors.onSurface,
    )
}

@Composable
fun FeedItemPreview(){
    Text(
        text = "title",
        color = MaterialTheme.colors.onSurface,
    )
}

