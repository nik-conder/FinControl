package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents


@Composable
fun LoginComponent(
    onEvents: (LoginEvents) -> Unit,
    profilesList: List<Profile>,
    navController: NavController,
    selectedProfile: Int
) {

    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Выберите профиль",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
           ChangeProfileComponent(
               profilesList = profilesList,
               onEvents = onEvents,
               selectedProfile = selectedProfile,
               navController = navController
           )
        }
    }
}

@Composable
fun ChangeProfileComponent(
    onEvents: (LoginEvents) -> Unit,
    profilesList: List<Profile>,
    selectedProfile: Int,
    navController: NavController
) {

    LaunchedEffect(true) {
        println("выбранный профиль $selectedProfile")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.5f),
                userScrollEnabled = true
            ) {
                items(items = profilesList) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(
                                color = if (selectedProfile == it.uid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(
                                enabled = selectedProfile != it.uid,
                                role = Role.RadioButton
                            ) {
                                onEvents.invoke(LoginEvents.SelectProfile(it.uid))
                            },
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column (
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        color = if (selectedProfile == it.uid) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(20.dp)
                                    ),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text =  it.currency.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (selectedProfile == it.uid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f),
                            ) {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 18.sp,
                                    color = if (selectedProfile == it.uid) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                                )
                            }

                            if (selectedProfile == it.uid) {
                                Column(
                                    horizontalAlignment = Alignment.End,
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = "Checked profile",
                                        tint = MaterialTheme.colorScheme.primaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Row() {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                TextButton(onClick = {
                    navController.navigate(Screen.CreateProfile.route)
                }) {
                    Text(text = "Новый профиль")
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                TextButton(
                    enabled = selectedProfile != 0,
                    onClick = {
                        onEvents.invoke(LoginEvents.Login)
                    }) {
                    Text(text = "Далее")
                }
            }
        }
    }
}