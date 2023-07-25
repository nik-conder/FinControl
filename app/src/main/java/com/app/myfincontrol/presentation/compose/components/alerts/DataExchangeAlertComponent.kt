package com.app.myfincontrol.presentation.compose.components.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.presentation.viewModels.events.DebugEvents
import com.app.myfincontrol.presentation.viewModels.events.StatisticsEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataExchangeAlertComponent(
    state: Boolean,
    onEvents: (StatisticsEvents) -> Unit
) {
    if (state) {
        AlertDialog(
            onDismissRequest = {
                onEvents(StatisticsEvents.DataExchangeAlert)
            }
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Column() {
                    Row() {
                        Text(
                            text = "Экспорт данных",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Row {
                        Text(text = "Данные будут экспортированы в CSV файл в папку Documents")
                    }
                    Row() {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "Экспорт")
                        }
                    }
                }
            }

        }
    }
}