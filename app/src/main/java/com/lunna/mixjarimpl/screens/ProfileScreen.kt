package com.lunna.mixjarimpl.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lunna.mixjarimpl.db.entities.ProfileEntity
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.lunna.mixjarimpl.utilities.DataStoreManager
import com.lunna.mixjarimpl.viewmodels.ProfileViewModel
import org.koin.androidx.compose.viewModel

@Composable
@Preview()
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun ProfileScreenPreview(){
    MixjarImplTheme {
       ProfileComposablePreview()
    }
}

@Composable
fun ProfileScreen(){
    MixjarImplTheme {
        val context = LocalContext.current
        val dataStore = DataStoreManager(context)
        val profileViewModel by viewModel<ProfileViewModel>()
//        dataStore.username.collectAsState(initial = "").value?.let {
//            profileViewModel.addProfileByKey(
//                it
//            )
//        }
        dataStore.username.collectAsState(initial = "").value?.let { it1 ->
            profileViewModel.getProfileByKey(
                it1
            )
        }
        ProfileComposable(profileViewModel.profileMutableState.value)
    }
}

@Composable
fun UserNameTitle(username:String?){
    Text(
        text = username!!,
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onBackground,
        fontSize = 24.sp
    )
}

@Composable
fun ProfileComposable(profileEntity: ProfileEntity?){
    Column {
        UserNameTitle("Kangethe")
    }
}
@Composable
fun ProfileComposablePreview(){
    Column {
        UserNameTitle("Kangethe")
    }
}