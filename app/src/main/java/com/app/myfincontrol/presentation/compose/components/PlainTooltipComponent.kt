package com.app.myfincontrol.presentation.compose.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable

@Composable
fun PlainTooltipComponent(
    tooltipState: TooltipState,
    tooltip:  @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    PlainTooltip(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        content = content
    )
}