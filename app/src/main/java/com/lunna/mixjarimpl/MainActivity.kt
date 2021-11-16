package com.lunna.mixjarimpl

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lunna.mixjarimpl.models.UserInfo

class MainActivity : ComponentActivity() {

    val mixjarViewModel by viewModels<MixjarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mixjarViewModel.getTag()
        mixjarViewModel.getTrending()
        setContent {
            MixjarImplTheme {
                val infoState = rememberSaveable{mixjarViewModel.userInfoMutableState}
               AppScreen(userInfo = infoState, mixjarViewModel = mixjarViewModel)
            }
        }


    }
}

@Composable
fun Navigation(navController:NavHostController, mixjarViewModel: MixjarViewModel){
   NavHost(navController,startDestination = NavigationItem.Trending.route){
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
fun MainScreen(mixjarViewModel: MixjarViewModel){
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar()},
        bottomBar = { BottomNavigationBar(navController)}
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) {
            Navigation(navController, mixjarViewModel)
        }
    }
}
//@Composable
//@Preview(name = "mainScreenDay")
//@Preview(name = "mainScreenNight", uiMode = UI_MODE_NIGHT_YES)
//fun MainScreenPreview(){
//    MixjarImplTheme {
//        MainScreen()
//    }
//}

@Preview(name = "dayBottomNav")
@Preview(name = "nightBottomNav",uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BottomNavigationPreview(){
    MixjarImplTheme {
//        BottomNavigationBar(navController)
    }

}
@Composable
fun AppScreen(userInfo: MutableState<UserInfo?>, mixjarViewModel: MixjarViewModel) {
    if (userInfo.value?.userName == null) {
        LoginScreen(userInfo)
    } else {
        MainScreen(mixjarViewModel=mixjarViewModel )
    }
}

@Composable
fun UserInfoScreen(userInfo: UserInfo) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Username: ${userInfo.userName}",
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.h6
        )

    }
}

@Composable
fun LoginScreen(userInfo: MutableState<UserInfo?>) {
    Column(
         modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val userNameState = rememberSaveable { mutableStateOf("") }
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


        val password = rememberSaveable { mutableStateOf("") }
        val passwordVisibility = remember { mutableStateOf(false) }
        Surface(
            border = BorderStroke(2.dp, MaterialTheme.colors.primary),
            modifier = Modifier.padding(8.dp)
        ) {


            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                placeholder = { Text("Password") },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisibility.value)
                        Icons.Filled.ThumbUp
                    else Icons.Filled.Delete

                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(imageVector = image, "")
                    }
                }
            )
        }


        if (userNameState.value.isNotEmpty()
            && password.value.isNotEmpty()
        ) {
            Row(horizontalArrangement = Arrangement.End) {
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        println("Logged in!")
                        userInfo.value?.userName = userNameState.value
                        userInfo.value?.userLoggedIn = true
                    }
                ){
                    Text(text = "Login",
                    color = MaterialTheme.colors.primary)
                }
            }
        } else {
            Text(
                text = "Please enter both username and password!",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary,
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