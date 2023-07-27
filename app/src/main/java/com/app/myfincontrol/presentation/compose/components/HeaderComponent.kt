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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

/**
 * Header component.
 *
 * @param title Platform specific [String] parameters.
 * @param textStyle Platform specific [TextStyle] parameters.
 * @param paddingValues Platform specific [PaddingValues] parameters.
 *
 */

@Composable
fun HeaderComponent(
    title: String,
    textStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    paddingValues: PaddingValues =
        PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
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