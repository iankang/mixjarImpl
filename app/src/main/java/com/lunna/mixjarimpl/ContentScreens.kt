package com.lunna.mixjarimpl

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme


@Composable
fun TrendingScreen(viewModel: MixjarViewModel){
    viewModel.getTrending()
    Log.e("trendingPopular",viewModel.trendingPopular.value.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Trending Screen",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
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
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
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

