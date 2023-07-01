package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotProfileComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Text(
                text = "Not Profile",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 24.dp)
        ) {
            Text(
                text = "Вы не создали или не выбрали профиль",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 24.dp)
        ) {
            Text(
                text = "\uD83E\uDEE5",
                fontSize = 48.sp
            )
        }
    }
}