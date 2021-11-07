package com.lunna.mixjarimpl

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.mixsteroids.mixjar.models.CityAndTagPopularResponse
import com.mixsteroids.mixjar.models.CityAndTagPopularResponseData
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun TrendingScreen(viewModel: MixjarViewModel){
    viewModel.getTrending()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .wrapContentSize(Alignment.Center)
    ) {

//        trendingPop?.data?.forEach { cityAndTagPopularResponseData ->
//
//        }
        val trendingPop = viewModel.popularPostMutableState.value
        val popular = trendingPop?.data
        if(popular?.isNotEmpty() == true) {
            LazyColumn {
                items(popular) { cityAndTagPopularResponseData ->

                    PopularItem(
                        mixName = cityAndTagPopularResponseData?.name ?:"unknown",
                        imgUrl = cityAndTagPopularResponseData?.pictures?.extra_large ?: "unknown",
                        playCount = cityAndTagPopularResponseData?.play_count?.toString()?: "0",
                        listenCount = cityAndTagPopularResponseData?.listener_count?.toString()?: "0"
                    )
                }
            }
        }else{
            Text(
                text = "Hakuna kitu hapa",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
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