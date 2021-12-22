package com.lunna.mixjarimpl.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.paging.compose.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lunna.mixjarimpl.R
import com.lunna.mixjarimpl.db.entities.FollowingEntity
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.lunna.mixjarimpl.viewmodels.FollowingViewModel
import org.koin.androidx.compose.viewModel

//@ExperimentalFoundationApi
//@Composable
//fun FollowingScreen() {
//    MixjarImplTheme {
//        FollowingList()
//    }
//}

//
//@Composable
//fun FollowingItem(){
//
//    Box(
//        contentAlignment = Alignment.Center
//    ) {
//
//        GlideImage(
//            imageModel = ,
//            modifier = Modifier
//                .size(100.dp)
//                .clip(RoundedCornerShape(10.dp)),
//            // shows a progress indicator when loading an image.
//            loading = {
//                LoadingItem()
//            },
//            // shows an error text message when request failed.
//            failure = {
//                Text(text = "image request failed.")
//            })
//        Text(
//            text = "username",
//            modifier = Modifier.align(Alignment.BottomCenter),
//            color = MaterialTheme.colors.onSecondary
//        )
//    }
//}

@Composable
fun followImage(followingItem:FollowingEntity) {
    MixjarImplTheme {

        Box(
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(R.drawable.ic_baseline_image_24),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Text(
                text = followingItem.name!!,
                modifier = Modifier.align(Alignment.BottomCenter),
                color = Color.White
            )
        }
    }
}

//@Composable
//@Preview(name = "image")
//@Preview(name = "imageDark", uiMode = UI_MODE_NIGHT_YES)
//fun followImagePreview() {
//    MixjarImplTheme {
//
//    followImage("kangethe")
//
//    }
//}

@ExperimentalPagingApi
@ExperimentalFoundationApi
@Composable
fun FollowingList(username: String){
    val list = listOf<String>(
        "mtu",
        "mkali",
        "ni",
        "mgani"
    )

    val followingViewModel by viewModel<FollowingViewModel>()

    val following: LazyPagingItems<FollowingEntity> = followingViewModel.following(username).collectAsLazyPagingItems()
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(16.dp)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        androidx.paging.compose.items(
            items = following,
           key = {following:FollowingEntity -> following.key}){
           value: FollowingEntity? ->
           if (value != null) {
               followImage(value)
           }
       }
    }
}