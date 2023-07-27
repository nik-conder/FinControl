package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.compose.FinControlTheme

@Composable
fun BoxComponent(
    content: @Composable() (ColumnScope.() -> Unit)
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(20.dp)
            )
            .wrapContentSize()
            .padding(16.dp),
        content = content
    )
}

@PreviewLightDark
@Composable
fun BoxComponentPreview() {
   FinControlTheme {
       BoxComponent {
           Text(text = "dskfjsdfl")
       }
   }
}