package com.lunna.mixjarimpl.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.lunna.mixjarimpl.db.entities.FollowersEntity
import com.lunna.mixjarimpl.db.entities.ProfileEntity
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.lunna.mixjarimpl.viewmodels.FollowersViewModel
import com.mixsteroids.mixjar.models.UserFollowersData
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.viewModel

@ExperimentalPagingApi
@Composable
//@Preview(name = "day")
//@Preview(name = "night", uiMode = UI_MODE_NIGHT_YES)
fun FollowersScreen(username: String?,onClick: (userFollowersData: FollowersEntity) -> Unit){
    MixjarImplTheme {
        followerListItems(username,onClick)
    }
}

@ExperimentalPagingApi
@Composable
fun followerListItems(
    username:String?,
    onClick: (userFollowersData:FollowersEntity) -> Unit){

    val followersViewModel by viewModel<FollowersViewModel>()

    val items: LazyPagingItems<FollowersEntity> = username?.let { followersViewModel.followers(it).collectAsLazyPagingItems()}!!

    LazyColumn {
        items(items){value: FollowersEntity? ->
            Row(
                modifier = Modifier.fillParentMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 16.dp)
                    .pointerInput(Unit){
                                       detectTapGestures{
                                           if (value != null) {
                                               onClick(value)
                                           }
                                       }


                    },
            horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                followerImage(value?.pictureUrl)
                Text(
                    text = value?.name!!
                )
            }
        }
    }

}

@Composable
fun followerImage(pictureUrl:String?) {
    GlideImage(
//                imageModel = item?.pictures?.extra_large,
        imageModel = pictureUrl,
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)                       // clip to the circle shape
            .border(2.dp, Color.Gray, CircleShape),

        circularReveal = CircularReveal(duration = 250),
        alignment = Alignment.Center,
        contentScale = ContentScale.FillBounds,
    )
}
