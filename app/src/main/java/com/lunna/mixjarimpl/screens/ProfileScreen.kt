package com.lunna.mixjarimpl.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lunna.mixjarimpl.db.entities.ProfileEntity
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.lunna.mixjarimpl.utilities.DataStoreManager
import com.lunna.mixjarimpl.utilities.LoadingView
import com.lunna.mixjarimpl.viewmodels.ProfileViewModel
import org.koin.androidx.compose.viewModel

@Composable
@Preview(name = "day")
@Preview(name = "night", uiMode = UI_MODE_NIGHT_YES)
fun ProfileScreenPreview(){
    MixjarImplTheme {
       ProfileComposablePreview()
    }
}

@Composable
fun ProfileScreen(username: String?){
    val profileViewModel by viewModel<ProfileViewModel>()

    if (username != null) {
        profileViewModel.addProfileByKey(username)
    }
    val isLoading by profileViewModel.isLoading.collectAsState()
    val profileEntity by profileViewModel.profileMutableState.collectAsState()
    MixjarImplTheme {
        when {
            isLoading -> LoadingView(
                modifier = Modifier.fillMaxSize()
            )
            else -> profileEntity?.let { ProfileComposable(it) }
        }

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
fun ProfileComposable(profileEntity: ProfileEntity){
    Column {
        Log.e("profileScreen",profileEntity.toString())
        UserNameTitle("kangethe")
    }
}
@Composable
fun ProfileComposablePreview(){
    Column {
        UserNameTitle("Kangethe")
    }
}