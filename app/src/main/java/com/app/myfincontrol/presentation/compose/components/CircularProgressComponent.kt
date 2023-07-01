package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(text = "\uD83D\uDE34", fontSize = 48.sp)
        }
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            CircularProgressIndicator()
        }

        Row(
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            Text(text = "Подождите немного, пожалуйста...", fontSize = 16.sp)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CircularProgressComponentPreview() {
    CircularProgressComponent()
}