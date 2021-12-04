package com.lunna.mixjarimpl.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.lunna.mixjarimpl.BottomNavigationBar
import com.lunna.mixjarimpl.Navigation
import com.lunna.mixjarimpl.R
import com.lunna.mixjarimpl.TopAppBarDropdownMenu
import com.lunna.mixjarimpl.viewmodels.FeedViewModel
import com.lunna.mixjarimpl.viewmodels.MixjarViewModel
import org.koin.androidx.compose.viewModel


@ExperimentalMaterialApi
@OptIn(ExperimentalPagingApi::class)
@Composable
fun MainScreen(
    username: String?
) {
    val mixjarViewModel by viewModel<MixjarViewModel>()
    val feedViewModel by viewModel<FeedViewModel>()

    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
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

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 18.sp
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        actions = {
            TopAppBarDropdownMenu()
        }
    )
}
