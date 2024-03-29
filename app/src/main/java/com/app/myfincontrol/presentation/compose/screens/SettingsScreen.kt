package com.app.myfincontrol.presentation.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.myfincontrol.R
import com.app.myfincontrol.data.entities.InfoPageType
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.sources.UserStore
import com.app.myfincontrol.presentation.compose.components.BoxComponent
import com.app.myfincontrol.presentation.compose.components.HeaderComponent
import com.app.myfincontrol.presentation.compose.components.InfoBlockComponent
import com.app.myfincontrol.presentation.compose.components.InfoPageComponent
import com.app.myfincontrol.presentation.compose.components.NavigationComponent
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
            TopAppBar(title = { Text(text = stringResource(id = R.string.settings)) })
        },
        bottomBar = {
            NavigationComponent(navController = navController)
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                //.background(backgroundColorBrush)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(1f),
        ) {
            val (profileBox, settingsBox) = createRefs()

            if (state.value.selectedProfile == null) {
                InfoPageComponent(InfoPageType.NOT_PROFILE, navController)
            } else {
                BoxWithConstraints(
                    modifier = Modifier
                        .constrainAs(profileBox) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    ProfileBox(
                        profile = state.value.selectedProfile!!,
                        onEvents = onEvents,
                        navController = navController
                    )
                }
                BoxWithConstraints(
                    modifier = Modifier
                        .constrainAs(settingsBox) {
                            top.linkTo(profileBox.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    SettingsBox(
                        store = store
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsBox(
    store: UserStore
) {
    val scope = rememberCoroutineScope()
    val hideBalanceState = store.hideBalanceState.collectAsState(initial = false)
    val darkModeState = store.darkModeState.collectAsState(initial = false)
    val adviceBoxState = store.adviceBox.collectAsState(initial = false)
    val debugModeState = store.debugModeState.collectAsState(initial = false)

    val paddingValues = PaddingValues(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)

    BoxComponent(
        header = {
            HeaderComponent(
                title = stringResource(id = R.string.settings),
            )
        },
        content = {
            Column {
                Row {
                    SwitchComponent(
                        title = stringResource(id = R.string.dark_mode),
                        state = darkModeState.value,
                        onValueChange = {
                            scope.launch {
                                store.setDarkMode()
                            }
                        }
                    )
                }
                Row {
                    Divider()
                }
                Row {
                    SwitchComponent(
                        title = stringResource(R.string.block_useful_tips_setting),
                        description = stringResource(R.string.block_useful_tips_setting_description),
                        state = adviceBoxState.value,
                        onValueChange = {
                            scope.launch {
                                store.setAdviceBox()
                            }
                        }
                    )
                }
                Row {
                    Divider()
                }
                Row {
                    SwitchComponent(
                        title = stringResource(R.string.hide_balance_setting),
                        description = stringResource(R.string.hide_balance_setting_description),
                        state = hideBalanceState.value,
                        onValueChange = {
                            scope.launch {
                                store.sethideBalanceState()
                            }
                        }
                    )
                }
                Row {
                    Divider()
                }
                Row {
                    SwitchComponent(
                        title = stringResource(R.string.debug_mode),
                        state = debugModeState.value,
                        onValueChange = {
                            scope.launch {
                                store.setDebugMode()
                            }
                        })
                }
                Row {
                    Divider()
                }
                Row {
                    SwitchComponent(
                        title = stringResource(R.string.automatic_login),
                        description = stringResource(R.string.automatic_login_description),
                        state = false,
                        enabled = false,
                        onValueChange = { }
                    )
                }
            }
        }
    )
}

@Composable
fun ProfileBox(
    profile: Profile,
    onEvents: (SettingsEvents) -> Unit,
    navController: NavHostController,
) {
    val paddingValues = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp)

    Column {
        BoxComponent(
            header = {
                HeaderComponent(
                    title = stringResource(id = R.string.profile),
                )
            },
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "\uD83D\uDC64", fontSize = 24.sp)
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = profile.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        InfoBlockComponent(
                            text = profile.currency.name
                        )
                    }
                }
                Row {
                    Column {
                        TextButton(onClick = {
                            onEvents.invoke(SettingsEvents.DeleteProfile)
                            navController.navigate(Screen.Login.route)
                        }) {
                            //Icon(imageVector = Icons.Outlined.Clear, contentDescription = "")
                            Text(text = stringResource(R.string.delete))
                        }
                    }
                    Column {
                        TextButton(onClick = {
                            onEvents.invoke(SettingsEvents.Logout)
                            navController.navigate(Screen.Login.route)
                        }) {
                            Text(text = stringResource(R.string.logout))
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {

    val context = LocalContext.current
    val userStore = UserStore(context)
    SettingsBox(store = userStore)
}