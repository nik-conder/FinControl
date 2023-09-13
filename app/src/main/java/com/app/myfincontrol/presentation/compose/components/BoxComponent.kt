package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxComponent(
    paddingValues: PaddingValues = PaddingValues(16.dp),
    header: @Composable ColumnScope.() -> Unit, // todo
    content: @Composable (ColumnScope.() -> Unit)
) {

    Column (
        modifier = Modifier
            .padding(paddingValues)
    ) {
        Row {
            Column (
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