package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TagComponent(
    text: String,
    colorBackground: Color,
    colorText: Color,
) {
    Row(
        modifier = Modifier
            .background(
                color = colorBackground,
                shape = RoundedCornerShape(20.dp))
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
    ) {
        Text(
            text = text,
            color = colorText,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}