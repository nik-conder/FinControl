package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.FinControlTheme

@Composable
fun SwitchComponent(
    title: String,
    description: String? = null,
    state: Boolean,
    enabled: Boolean = true,
    onValueChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
            //.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row() {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (!description.isNullOrEmpty()) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Column() {
            Switch(
                checked = state,
                onCheckedChange = { onValueChange },
                enabled = enabled,
                thumbContent = {
                    Icon(
                        modifier = Modifier
                            .size(18.dp),
                        imageVector = if (!enabled) {
                            Icons.Outlined.Lock
                        } else {
                            if (state) {
                                Icons.Outlined.Check
                            } else Icons.Outlined.Clear
                               },
                        contentDescription = ""
                    )
                }
            )
        }
    }
}