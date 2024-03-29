package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp

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
            .wrapContentHeight()
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (!description.isNullOrEmpty()) {
                Row {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Switch(
                checked = state,
                onCheckedChange = onValueChange,
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