package com.app.myfincontrol.presentation.compose.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.myfincontrol.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val iconResource: ImageVector
) {
    object Home : Screen(route = "homeScreen", R.string.title_feed, iconResource = Icons.Outlined.Home)
    object Settings : Screen(route = "settingsScreen",  R.string.title_settings, iconResource = Icons.Outlined.Settings)
    object Login : Screen(route = "loginScreen", R.string.title_feed, iconResource = Icons.Outlined.Lock)
    object CreateProfile : Screen(route = "createProfileScreen", R.string.title_feed, iconResource = Icons.Outlined.Person)
    object Charts: Screen(route = "chartsScreen", R.string.title_charts, iconResource = Icons.Outlined.Info)
}