package com.lunna.mixjarimpl.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lunna.mixjarimpl.R
import com.lunna.mixjarimpl.db.entities.ProfileEntity
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.lunna.mixjarimpl.utilities.DataStoreManager
import com.lunna.mixjarimpl.utilities.LoadingView
import com.lunna.mixjarimpl.viewmodels.ProfileViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
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
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onBackground,
        fontSize = 24.sp
    )
}

@Composable
fun ProfileComposable(profileEntity: ProfileEntity){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.e("profileScreen",profileEntity.toString())
        profilePictureImage(profileEntity)
        UserNameTitle(profileEntity.username)
    }
}
@Composable
@Preview(name = "day", showBackground = true)
@Preview(name = "night", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
fun ProfileComposablePreview(){
    Column {
        UserNameTitle("Kangethe")
    }
}

@Composable
fun profilePictureImage(profileEntity: ProfileEntity){
    GlideImage(
//                imageModel = item?.pictures?.extra_large,
        imageModel = profileEntity.pictureUrl,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)                       // clip to the circle shape
            .border(2.dp, Color.Gray, CircleShape),

        circularReveal = CircularReveal(duration = 250),
        alignment = Alignment.Center,
        contentScale = ContentScale.FillBounds,)
}


@Preview(name = "statsday", showBackground = true)
@Preview(name = "statsnight", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun statsRow(){
    MixjarImplTheme {
        Row(
            modifier = Modifier
                .size(width = 150.dp, height = 150.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically),
                backgroundColor = MaterialTheme.colors.onPrimary,
                contentColor = MaterialTheme.colors.primary
            ) {
                Text(
                    modifier = Modifier
                    .fillMaxHeight()
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = Color.DarkGray,
                            )
                        ) {
                            append("Listens\n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        ) {
                            append("1,850")
                        }
                    },

                lineHeight = 36.sp,
                style = TextStyle(

                )
                )
            }
        }
    }
}