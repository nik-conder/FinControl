package com.app.myfincontrol.presentation.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.data.Currency
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents
import com.example.compose.FinControlTheme


@Composable
fun LoginComponent(
    onEvents: (LoginEvents) -> Unit,
    profilesList: List<Profile>
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
           ChangeProfileComponent(profilesList = profilesList, onEvents = onEvents)
        }
    }
}

@Composable
fun ChangeProfileComponent(
    onEvents: (LoginEvents) -> Unit,
    profilesList: List<Profile>
) {
    val selectedProfile = remember { mutableIntStateOf(0) }

    if (profilesList.isNotEmpty()) {
        LaunchedEffect(true)  {
            selectedProfile.intValue = 1
        }
    }

    LaunchedEffect(selectedProfile.intValue) {
        if (profilesList.isNotEmpty()) {
            println("Выбран профиль: ${selectedProfile.intValue}")
            println("${profilesList[selectedProfile.intValue - 1]}")
        }
    }


    Column() {
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
                                color = if (selectedProfile.value == it.uid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(
                                enabled = selectedProfile.value != it.uid,
                                role = Role.RadioButton
                            ) {
                                selectedProfile.value = it.uid
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
                                        color = if (selectedProfile.value == it.uid) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(20.dp)
                                    ),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text =  it.currency.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (selectedProfile.value == it.uid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
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
                                    color = if (selectedProfile.value == it.uid) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                                )
                            }

                            if (selectedProfile.value == it.uid) {
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

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                TextButton(onClick = {
                    onEvents.invoke(LoginEvents.NewProfile)
                }) {
                    Text(text = "Новый профиль")
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                TextButton(onClick = {
                    onEvents.invoke(LoginEvents.Login(profile = profilesList[selectedProfile.intValue - 1]))

                }) {
                    Text(text = "Далее")
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun LoginComponentPreview() {
    FinControlTheme(
        //useDarkTheme = AppCompatDelegate.getDefaultNightMode()
    ) {
        val listProfiles = listOf(
            Profile(uid = 1, name = "Иванов Иван Иванович", currency = Currency.RUB),
            Profile(uid = 2,name = "Петров Петр Петрович", currency = Currency.RUB),
            Profile(uid = 3,name = "Сидоров Сидор Сидорович", currency = Currency.RUB)
        )
        LoginComponent(
            onEvents = {},
            profilesList = listProfiles
        )
    }
}

@Preview(showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun LoginComponentDarkPreview() {
    FinControlTheme(
        //useDarkTheme = AppCompatDelegate.getDefaultNightMode()
    ) {
        val listProfiles = listOf(
            Profile(uid = 1, name = "Иванов Иван Иванович", currency = Currency.RUB),
            Profile(uid = 2,name = "Петров Петр Петрович", currency = Currency.RUB),
            Profile(uid = 3,name = "Сидоров Сидор Сидорович", currency = Currency.RUB)
        )
        LoginComponent(
            onEvents = {},
            profilesList = listProfiles
        )
    }
}