package com.lunna.mixjarimpl.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.lunna.mixjarimpl.BottomNavigationBar
import com.lunna.mixjarimpl.Navigation
import com.lunna.mixjarimpl.TopBar
import com.lunna.mixjarimpl.viewmodels.FeedViewModel
import com.lunna.mixjarimpl.viewmodels.MixjarViewModel
import org.koin.androidx.compose.viewModel

@Composable
fun MainScreen(
    username: String?
) {
    val mixjarViewModel by viewModel<MixjarViewModel>()
    val feedViewModel by viewModel<FeedViewModel>()

    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar(mixjarViewModel) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) {
            Navigation(
                navController,
                mixjarViewModel,
                feedViewModel,
            username)
        }
    }
}
