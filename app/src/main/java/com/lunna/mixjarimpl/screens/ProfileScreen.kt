package com.lunna.mixjarimpl.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lunna.mixjarimpl.db.entities.ProfileEntity
import com.lunna.mixjarimpl.ui.theme.MixjarImplTheme
import com.lunna.mixjarimpl.utilities.LoadingView
import com.lunna.mixjarimpl.utilities.NotFound
import com.lunna.mixjarimpl.viewmodels.ProfileViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.viewModel

@Composable
@Preview(name = "day")
@Preview(name = "night", uiMode = UI_MODE_NIGHT_YES)
fun ProfileScreenPreview() {
    MixjarImplTheme {
        ProfileComposablePreview()
    }
}

@ExperimentalMaterialApi
@Composable
fun ProfileScreen(
    username: String?,
    onClickListener : (string:String) -> Unit
) {
    val profileViewModel by viewModel<ProfileViewModel>()

    if (username != null) {
        profileViewModel.getProfileByKey(username)
    }
    val isLoading by profileViewModel.isLoading.collectAsState()
    val profileEntity by profileViewModel.profileMutableState.collectAsState()
    MixjarImplTheme {
        when {
            isLoading -> LoadingView(
                modifier = Modifier.fillMaxSize()
            )
            else -> profileEntity?.let { ProfileComposable(it,onClickListener) } ?: NotFound(
                "Profile Screen",
                username
            )
        }

    }
}

@Composable
fun UserNameTitle(username: String?) {
    Text(
        text = username!!,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onBackground,
        fontSize = 24.sp
    )
}

@Composable
fun bioText(bio: String?) {
    Row {
        Text(
            text = bio ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun ProfileComposable(
    profileEntity: ProfileEntity,
    onClickListener : (string:String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.e("profileScreen", profileEntity.toString())
        profilePictureImage(profileEntity)
        UserNameTitle(profileEntity.username)
        bioText(profileEntity.biog)
        statsRow(
            profileEntity.followingCount.toString(),
            profileEntity.followerCount.toString(),
            profileEntity.favoriteCount.toString(),
            profileEntity.listenCount.toString(),
            profileEntity.cloudCastCount ?: 0,
            onClickListener
        )
    }
}

@Composable
@Preview(name = "day", showBackground = true)
@Preview(name = "night", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
fun ProfileComposablePreview() {
    MixjarImplTheme {
        Column {
            UserNameTitle("Kangethe")
            bioText("internet sensation")
        }
    }
}

@Composable
fun profilePictureImage(profileEntity: ProfileEntity) {
    GlideImage(
//                imageModel = item?.pictures?.extra_large,
        imageModel = profileEntity.pictureUrl,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)                       // clip to the circle shape
            .border(2.dp, Color.Gray, CircleShape),

        circularReveal = CircularReveal(duration = 250),
        alignment = Alignment.Center,
        contentScale = ContentScale.FillBounds,
    )
}


@ExperimentalMaterialApi
@Composable
fun statsRow(
    followingText: String = "0",
    followersText: String = "0",
    likesText: String = "0",
    listensText: String = "0",
    cloudCastCount: Int = 0,
    onClickListener : (string:String) -> Unit
) {
    MixjarImplTheme {
        Column {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                statCard(
                    "Following",
                    followingText,
                    onClickListener= {onClickListener("following")}
                )
                statCard(
                    "Followers",
                    followersText,
                    onClickListener= {onClickListener("followers")}
                )

            }

            if (cloudCastCount > 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    statCard(
                        "shows",
                        cloudCastCount.toString(),
                        modifier = Modifier.fillMaxWidth(),
                        onClickListener= {onClickListener("shows")}
                    )

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                statCard(
                    "Likes",
                    likesText,
                    onClickListener= {onClickListener("likes")}
                )
                statCard(
                    "Listens",
                    listensText,
                    onClickListener= {onClickListener("listens")}
                )

            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun statCard(
    title: String? = "title",
    value: String? = "0",
    modifier: Modifier = Modifier
        .padding(18.dp),
    onClickListener : (string:String?) -> Unit
) {
    Card(
        onClick = {onClickListener("following")},
        modifier = modifier,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 12.dp,

    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                ) {
                    append("$title \n")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Normal,

                        )
                ) {
                    if (value != null) {
                        append("%,d".format(value.toBigInteger()))
                    }
                }
            },
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}