package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents

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
            Text(text = "Выберите профиль")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            LazyColumn() {
                items(items = profilesList) {
                    Text(text = "${it.name} ${it.currency}")
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    OutlinedButton(onClick = {
                        onEvents.invoke(LoginEvents.Login(profile = profilesList[0]))

                    }) {
                        Text(text = "Продолжить")
                    }
                }
                Row() {
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Text(text = "Создать новый")
                    }
                }
            }
        }
    }
}