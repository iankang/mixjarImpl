package com.lunna.mixjarimpl

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lunna.mixjarimpl.screens.FeedScreen
import com.lunna.mixjarimpl.viewmodels.FeedViewModel
import com.lunna.mixjarimpl.viewmodels.MixjarViewModel

class MainActivity : ComponentActivity() {

    val mixjarViewModel by viewModels<MixjarViewModel>()
    val feedViewModel by viewModels<FeedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mixjarViewModel.getTag()
        mixjarViewModel.getTrending()


        setContent {
            MixjarImplTheme {

               AppScreen(mixjarViewModel, feedViewModel)
            }
        }


    }
}

@Composable
fun Navigation(navController:NavHostController, mixjarViewModel: MixjarViewModel, feedViewModel: FeedViewModel){
   NavHost(navController,startDestination = NavigationItem.Feed.route){
       composable(NavigationItem.Feed.route){
           FeedScreen(feedViewModel)
       }
       composable(NavigationItem.Trending.route){
           TrendingScreen(viewModel = mixjarViewModel)
       }
       composable(NavigationItem.Listens.route){
           GenericScreen("Listens")
       }
       composable(NavigationItem.Playlists.route){
           GenericScreen("Playlists")
       }
       composable(NavigationItem.Profile.route){
           GenericScreen("Profile")
       }
   }
}

@Composable
fun TopBar(mixjarViewModel: MixjarViewModel){
    TopAppBar(
        title = { Text(
            text = stringResource(R.string.app_name),
            fontSize = 18.sp)
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        actions = {
            TopAppBarDropdownMenu(mixjarViewModel)
        }
    )
}

@Composable
fun TopAppBarDropdownMenu(mixjarViewModel: MixjarViewModel) {
    val expanded = remember { mutableStateOf(false) } // 1

    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true // 2
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More Menu"
            )
        }
    }

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        DropdownMenuItem(onClick = {
            mixjarViewModel.removeFromDataStore()
            expanded.value = false // 3
        }) {
            Text("Logout")
        }

//        Divider()
//
//        DropdownMenuItem(onClick = {
//            expanded.value = false
//        }) {
//            Text("Second item")
//        }
//
//        Divider()
//
//        DropdownMenuItem(onClick = {
//            expanded.value = false
//        }) {
//            Text("Third item")
//        }
//
//        Divider()
//
//        DropdownMenuItem(onClick = {
//            expanded.value = false
//        }) {
//            Text("Fourth item")
//        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Feed,
        NavigationItem.Trending,
        NavigationItem.Listens,
        NavigationItem.Playlists,
        NavigationItem.Profile
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor =  Color.White
    ){
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = null)},
                label = {Text(text = item.title)},
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route){
                                saveState =true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

            )
        }
    }
}

@Composable
fun MainScreen(
    mixjarViewModel: MixjarViewModel,
    feedViewModel: FeedViewModel){
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar(mixjarViewModel)},
        bottomBar = { BottomNavigationBar(navController)}
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) {
            Navigation(navController, mixjarViewModel,feedViewModel)
        }
    }
}


@Preview(name = "dayBottomNav")
@Preview(name = "nightBottomNav",uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BottomNavigationPreview(){
    MixjarImplTheme {
//        BottomNavigationBar(navController)
    }

}
@Composable
fun AppScreen( mixjarViewModel: MixjarViewModel, feedViewModel: FeedViewModel) {
    mixjarViewModel.getUserNameDataStore()
    if (mixjarViewModel.userNameDataStore.value.isEmpty()) {
        LoginScreen(mixjarViewModel)
    } else {
        MainScreen(mixjarViewModel,feedViewModel)
    }
}


@Composable
fun LoginScreen(mixjarViewModel: MixjarViewModel) {
    Column(
         modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val userNameState = rememberSaveable{ mutableStateOf("")}
        Surface(
            border = BorderStroke(2.dp, MaterialTheme.colors.primary),
            modifier = Modifier.padding(8.dp)
        ) {
            TextField(
                value = userNameState.value,
                onValueChange = { userNameState.value = it },
                label = { Text("Username") },
                placeholder = { Text("Username") },
            )

        }


        Row(horizontalArrangement = Arrangement.End) {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    println("Logged in!")
                    mixjarViewModel.userNameState.value = userNameState.value
                    mixjarViewModel.saveToDataStore(username = userNameState.value)

                }
            ){
                Text(text = "Login",
                    color = MaterialTheme.colors.secondary)
            }
        }
        if (userNameState.value.isEmpty()) {
            Text(
                text = "Please enter mixcloud username!",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Preview(showBackground = true,
uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MixjarImplTheme {
//     LoginScreen(UserInfo(false,"kangethe"))
    }
}