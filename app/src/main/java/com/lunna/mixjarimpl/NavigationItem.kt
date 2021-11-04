package com.lunna.mixjarimpl

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title:String){
    object Trending: NavigationItem("trending",Icons.Outlined.Home,"Trending")
    object Playlists: NavigationItem("playlists",Icons.Outlined.List,"Playlists")
    object Listens: NavigationItem("listens",Icons.Outlined.ThumbUp,"Listens")
    object Profile: NavigationItem("profile",Icons.Default.Person,"profile")
}
