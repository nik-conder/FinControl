package com.app.myfincontrol.presentation.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.app.myfincontrol.R

@Composable
fun NavigationComponent(
    navController: NavController
) {
    NavigationBar {
        Text(text = "${navController.graph.route?.length}")
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("settingsScreen")
                      },
            label = {
                Text(
                    text = "Settings",
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                )
            }
        )

        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("homeScreen") },
            label = {
                Text(
                    text = "Home",
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home",
                )
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = { /* navController.navigate(item.route) */ },
            label = {
                Text(
                    text = "Charts",
                )
            },
            icon = {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_show_chart_24), contentDescription = "Chars")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {  navController.navigate("profileScreen")  },
            label = {
                Text(
                    text = "Profile",
                )
            },
            icon = {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = "Profile")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {  navController.navigate("loginScreen")  },
            label = {
                Text(
                    text = "Login",
                )
            },
            icon = {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = "Login")
            }
        )

    }
}