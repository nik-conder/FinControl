package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HeaderComponent(
    title: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    paddingValues: PaddingValues? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues ?: PaddingValues())
    ) {
        Row {
            Text(
                text = title,
                style = textStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}