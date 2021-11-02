package com.lunna.mixjarimpl

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.TagResponse
import com.mixsteroids.mixjar.models.UserResponse
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer

class MainActivity : ComponentActivity() {

    private  val mixjarViewModel by viewModels<MixjarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mixjarViewModel.getTag()
        setContent {
            MixjarImplTheme {
                TopBar()
            }
        }


    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun TopBar(){
    TopAppBar(
        title = { Text(
            text = stringResource(R.string.app_name),
            fontSize = 18.sp)
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MixjarImplTheme {
     TopBar()
    }
}