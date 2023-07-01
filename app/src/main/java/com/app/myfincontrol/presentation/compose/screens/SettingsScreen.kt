package com.app.myfincontrol.presentation.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.myfincontrol.R
import com.app.myfincontrol.UserStore
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.presentation.compose.components.HeaderComponent
import com.app.myfincontrol.presentation.compose.components.NavigationComponent
import com.app.myfincontrol.presentation.compose.components.NotProfileComponent
import com.app.myfincontrol.presentation.compose.components.SwitchComponent
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.app.myfincontrol.presentation.viewModels.SettingsViewModel
import com.app.myfincontrol.presentation.viewModels.events.SettingsEvents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    store: UserStore
) {

    val vm = hiltViewModel<SettingsViewModel>()
    val onEvents = vm::onEvents
    val state = vm.states.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_settings),
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar() {
                NavigationComponent(navController = navController)
            }
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                //.background(backgroundColorBrush)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(1f),
        ) {
            val (profileBox, settingsBox, notProfileBox) = createRefs()

            if (state.value.selectedProfile == null) {
                NotProfileComponent()
            } else {
                BoxWithConstraints(
                    modifier = Modifier
                        .constrainAs(profileBox) {
                            top.linkTo(parent.top, 16.dp)
                            start.linkTo(parent.start, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Row() {
                            HeaderComponent(title = "Profile")
                        }
                        Row {
                            ProfileBox(
                                profile = state.value.selectedProfile!!,
                                onEvents = onEvents,
                                navController = navController
                            )
                        }
                    }
                }
                BoxWithConstraints(
                    modifier = Modifier
                        .constrainAs(settingsBox) {
                            top.linkTo(profileBox.bottom, 16.dp)
                            bottom.linkTo(parent.bottom, 16.dp)
                            start.linkTo(parent.start, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Row() {
                            HeaderComponent(title = "Settings")
                        }
                        Row {
                            SettingsBox(
                                onEvents = onEvents,
                                store = store
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsBox(
    store: UserStore,
    onEvents: (SettingsEvents) -> Unit
) {
    val scope = rememberCoroutineScope()
    val darkMode = store.getDarkMode.collectAsState(initial = false)
    val hideBalance = store.hideBalance.collectAsState(initial = false)

    Column() {
        Row() {
           SwitchComponent(
               title = "Тёмная тема",
               state = darkMode.value,
               onValueChange = {
                   scope.launch {
                       store.setDarkMode()
                   }
               }
           )
        }
        Row() {
            SwitchComponent(
                title = "Автоматический вход",
                description = "...",
                state = false,
                enabled = false,
                onValueChange = { }
            )
        }
        Row() {
            SwitchComponent(
                title = "Блок \"Полезные советы\"",
                state = false,
                enabled = false,
                onValueChange = { }
            )
        }
        Row() {
            SwitchComponent(
                title = "Баланс скрыт по умолчанию",
                description = "Опция позволит скрыть баланс при открытии приложения",
                state = hideBalance.value,
                onValueChange = { value ->
                    scope.launch {
                        store.setHideBalance()
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileBox(
    profile: Profile,
    onEvents: (SettingsEvents) -> Unit,
    navController: NavHostController,
) {
    Column() {
        Row() {
            Text(
                text = profile.name,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row() {
            TextButton(onClick = {
                onEvents.invoke(SettingsEvents.DeleteProfile)
                navController.navigate(Screen.Login.route)
            }) {
                Icon(imageVector = Icons.Outlined.Clear, contentDescription = "")
                Text(text = "Удалить")
            }
        }
    }
}