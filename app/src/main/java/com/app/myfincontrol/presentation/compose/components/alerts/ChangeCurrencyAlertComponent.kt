package com.app.myfincontrol.presentation.compose.components.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.app.myfincontrol.presentation.compose.components.HeaderComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeCurrencyAlertComponent(
    state: Boolean
) {
    AlertDialog(onDismissRequest = {  }) {
        Column() {
            Row {
                HeaderComponent(title = "Выберите валюту")
            }
            Row() {

            }
        }
    }
}