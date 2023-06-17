package com.app.myfincontrol.presentation.compose.navigation

import android.content.Intent.ShortcutIconResource
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.app.myfincontrol.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val iconResource: ImageVector
) {
    object Profile : Screen("profileScreen", R.string.title_profile, iconResource = Icons.Outlined.Person)
    object Home : Screen(route = "homeScreen", R.string.title_feed, iconResource = Icons.Outlined.Home)
    object Settings : Screen(route = "settingsScreen",  R.string.title_settings, iconResource = Icons.Outlined.Settings)
    object Login : Screen(route = "loginScreen", R.string.title_feed, iconResource = Icons.Outlined.Lock)
    object CreateProfile : Screen(route = "createProfileScreen", R.string.title_feed, iconResource = Icons.Outlined.Person)
}