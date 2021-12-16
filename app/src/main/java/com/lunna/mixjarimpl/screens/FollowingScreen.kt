package com.lunna.mixjarimpl.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lunna.mixjarimpl.R
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.lunna.mixjarimpl.utilities.LoadingItem
import com.skydoves.landscapist.ImageBySource
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalFoundationApi
@Composable
fun FollowingScreen() {
    MixjarImplTheme {
        FollowingList()
    }
}

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
fun followImage(username:String) {
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
                text = username,
                modifier = Modifier.align(Alignment.BottomCenter),
                color = Color.White
            )
        }
    }
}

@Composable
@Preview(name = "image")
@Preview(name = "imageDark", uiMode = UI_MODE_NIGHT_YES)
fun followImagePreview() {
    MixjarImplTheme {

    followImage("kangethe")

    }
}

@ExperimentalFoundationApi
@Composable
@Preview(name = "followingList")
@Preview(name = "followingList", uiMode = UI_MODE_NIGHT_YES)
fun FollowingList(){
    val list = listOf<String>(
        "mtu",
        "mkali",
        "ni",
        "mgani"
    )
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(16.dp)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items = list, itemContent = { item ->
            followImage(item)
        })
    }
}