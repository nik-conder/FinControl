package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.TooltipBoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlainTooltipComponent(
    tooltip:  @Composable () -> Unit,
    tooltipState: PlainTooltipState,
    content: @Composable() (TooltipBoxScope.() -> Unit)
) {
    PlainTooltipBox(
        tooltip = tooltip,
        tooltipState = tooltipState,
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        content = content
    )
}