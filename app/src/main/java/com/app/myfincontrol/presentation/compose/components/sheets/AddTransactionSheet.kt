package com.app.myfincontrol.presentation.compose.components.sheets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.presentation.compose.components.AddTransactionComponent
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents

@Composable
fun AddTransactionSheet(
    onEvents: (TransactionEvents) -> Unit
) {
    Box(
        modifier = Modifier
            .heightIn(min = 350.dp)
            //.padding(32.dp)
    ) {
        AddTransactionComponent(onEvents = onEvents)
    }
}