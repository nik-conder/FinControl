package com.app.myfincontrol.presentation.compose.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.TooltipBoxScope
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlainTooltipComponent(
    tooltipState: PlainTooltipState,
    tooltip: @Composable () -> Unit,
    content: @Composable (TooltipBoxScope) -> Unit
) {
    PlainTooltipBox(
        tooltipState = tooltipState,
        tooltip = tooltip,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        content = content
    )
}