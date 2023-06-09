package com.app.myfincontrol.presentation.compose.screens

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.myfincontrol.data.Configuration
import com.app.myfincontrol.presentation.compose.components.CreateProfileComponent
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.app.myfincontrol.presentation.viewModels.HomeViewModel
import com.app.myfincontrol.presentation.viewModels.LoginViewModel

@Composable
fun CreateProfileScreen(
    navController: NavHostController,
) {
    val vm = hiltViewModel<LoginViewModel>()
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
            val (createProfileBox) = createRefs()

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(createProfileBox) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (states.value.profilesList.size <= Configuration.Limits.LIMIT_PROFILES) {
                    CreateProfileComponent(onEvents = onEvents)
                } else {
                    Column() {
                        Row() {
                            Text(
                                text = "Limit",
                            )
                        }
                        Row() {
                            OutlinedButton(onClick = { navController.popBackStack()}) {
                                Text(text = "Back")
                            }
                        }
                    }
                }
            }
        }
    }
}