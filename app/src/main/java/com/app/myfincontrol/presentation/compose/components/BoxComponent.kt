package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BoxComponent(
    paddingValues: PaddingValues = PaddingValues(16.dp),
    header: @Composable ColumnScope.() -> Unit, // todo
    content: @Composable (ColumnScope.() -> Unit)
) {

    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        Row {
            Column(
                content = header
            )
        }
        Row {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(16.dp),
                content = content
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun TwedfsdPrevie() {
    Column(
        modifier = Modifier.width(100.dp)
    ) {
        Text(
            buildAnnotatedString {
                append("USD")

                withStyle(style = SpanStyle(color = Color.Green)) {
                    append("110 руб")
                }
                append(" | ")

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                    append("100 руб")
                }
            },
            modifier = Modifier.basicMarquee(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
