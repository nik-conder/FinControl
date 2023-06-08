package com.app.myfincontrol.presentation.compose.screens

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.myfincontrol.dataStore
import com.app.myfincontrol.presentation.compose.components.CreateProfileComponent
import com.app.myfincontrol.presentation.compose.components.LoginComponent
import com.app.myfincontrol.presentation.viewModels.LoginViewModel
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents

@Composable
fun LoginScreen(
    navController: NavController
) {

    val vm = hiltViewModel<LoginViewModel>()

    val context = LocalContext.current

    val onEvents = vm::onEvents
    val states = vm.states.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val (loginBox) = createRefs()

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(loginBox) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (states.value.isLoading) {
                    if (states.value.profilesList.isNotEmpty()) {
                        LoginComponent(onEvents = onEvents, profilesList = states.value.profilesList)
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                            ) {
                                Text(
                                    text = "Я не могу найти ни один профиль \uD83D\uDE15",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Row() {
                                TextButton(onClick = {
                                    onEvents.invoke(LoginEvents.NewProfile)
                                }) {
                                    Text(text = "Создать новый")
                                }
                            }
                        }
                    }

                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            CircularProgressIndicator()
                        }
                        Row(
                            modifier = Modifier
                                .padding(top = 32.dp)
                        ) {
                            Text(text = "Loading...")
                        }
                    }
                }
            }
        }
    }
}