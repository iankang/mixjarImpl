package com.lunna.mixjarimpl

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    private  val mixjarViewModel by viewModels<MixjarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mixjarViewModel.getTag()
        setContent {
            MixjarImplTheme {
                MainScreen()
            }
        }


    }
}

@Composable
fun Navigation(navController:NavHostController){
   NavHost(navController,startDestination = NavigationItem.Trending.route){
       composable(NavigationItem.Trending.route){
           TrendingScreen()
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

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
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
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar()},
        bottomBar = { BottomNavigationBar(navController)}
    ) {
        Navigation(navController)
    }
}
@Composable
@Preview(name = "mainScreenDay")
@Preview(name = "mainScreenNight", uiMode = UI_MODE_NIGHT_YES)
fun MainScreenPreview(){
    MixjarImplTheme {
        MainScreen()
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

@Preview(showBackground = true,
uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    MixjarImplTheme {
     TopBar()
    }
}