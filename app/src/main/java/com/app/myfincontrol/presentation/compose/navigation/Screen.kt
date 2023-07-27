package com.app.myfincontrol.presentation.compose.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.app.myfincontrol.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val iconResource: Int
) {
    object Home : Screen(route = "homeScreen", R.string.feed, iconResource = R.drawable.ic_local_fire_department_48px)
    object Settings : Screen(route = "settingsScreen",  R.string.settings, iconResource = R.drawable.ic_settings_48px)
    object Login : Screen(route = "loginScreen", R.string.feed, iconResource = R.drawable.ic_local_fire_department_48px)
    object CreateProfile : Screen(route = "createProfileScreen", R.string.feed, iconResource = R.drawable.ic_local_fire_department_48px)
    object Statistics: Screen(route = "statisticsScreen", R.string.statistics, iconResource = R.drawable.ic_bar_chart_48px)
}