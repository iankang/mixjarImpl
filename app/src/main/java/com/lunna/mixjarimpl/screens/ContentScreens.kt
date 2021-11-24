package com.lunna.mixjarimpl

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.lunna.mixjarimpl.utilities.ErrorItem
import com.lunna.mixjarimpl.utilities.LoadingItem
import com.lunna.mixjarimpl.utilities.LoadingView
import com.lunna.mixjarimpl.viewmodels.MixjarViewModel
import com.mixsteroids.mixjar.models.CityAndTagPopularResponseData
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun TrendingScreen(viewModel: MixjarViewModel){
    val popularInArea = viewModel.getPopularInYourArea()
    val popularItems: LazyPagingItems<CityAndTagPopularResponseData> = popularInArea.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .wrapContentSize(Alignment.Center)
    ) {
        val popular = popularItems
            LazyColumn {
                items(popular) { cityAndTagPopularResponseData ->

                    PopularItem(
                        mixName = cityAndTagPopularResponseData?.name ?:"unknown",
                        imgUrl = cityAndTagPopularResponseData?.pictures?.extra_large ?: "unknown",
                        playCount = cityAndTagPopularResponseData?.play_count?.toString()?: "0",
                        listenCount = cityAndTagPopularResponseData?.listener_count?.toString()?: "0"
                    )
                }

                popular.apply {
                    when{
                        loadState.refresh is LoadState.Loading ->{
                            item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                        }
                        loadState.append is LoadState.Loading ->{
                            item { LoadingItem() }
                        }

                        loadState.refresh is LoadState.Error ->{
                            val e = popular.loadState.refresh as LoadState.Error
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage!!,
                                    modifier = Modifier.fillParentMaxSize(),
                                    onClickRetry = {retry()}
                                )
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = popular.loadState.append as LoadState.Error
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
}

//@Preview(name = "trendingScreenDay")
//@Preview(name = "trendingScreenNight",uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun TrendingScreenPreview(){
//    MixjarImplTheme {
//        TrendingScreen()
//    }
//}
@Composable
fun GenericScreen(text:String){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .wrapContentSize(Alignment.Center)
    ) {

    }
}

@Preview(name = "genericScreenDay")
@Preview(name = "genericScreenNight",uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GenericScreenPreview(){
    MixjarImplTheme {
        GenericScreen("listens")
    }
}


@Composable
fun PopularItem(mixName:String, imgUrl:String,playCount:String,listenCount:String?){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {  }
    ){
        Column(
            modifier = Modifier.padding(15.dp)
        ){
                Text(
                    text = mixName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )


            GlideImage(
//                imageModel = item?.pictures?.extra_large,
                imageModel = imgUrl,
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(duration = 250)
            )

            Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = "Play Count: $playCount"
                )
                Text(
                    text = "Listen Count: $listenCount"
                )
            }

        }
    }
}


@Composable
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun PreviewPopularItem(){
    MixjarImplTheme {
        PopularItem(
            "lyta",
            "https://thumbnailer.mixcloud.com/unsafe/600x600/extaudio/f/2/7/d/8bdc-325c-4b2e-9b5f-d2f246c0b01f",
        "1243",
        "6353")
    }
}