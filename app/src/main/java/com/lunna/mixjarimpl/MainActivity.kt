package com.lunna.mixjarimpl

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.models.TagResponse
import com.mixsteroids.mixjar.models.UserResponse
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.Observer

class MainActivity : ComponentActivity() {

    private  val mixjarViewModel by viewModels<MixjarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mixjarViewModel.getTag()
        setContent {
            MixjarImplTheme {
                // A surface container using the 'background' color from the theme
                val tags:TagResponse? by mixjarViewModel.tags.observeAsState()

                Surface(color = MaterialTheme.colors.background) {

                    tags?.let { Greeting(it?.name) }
                }
            }
        }


    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MixjarImplTheme {
        Greeting("Android")
    }
}