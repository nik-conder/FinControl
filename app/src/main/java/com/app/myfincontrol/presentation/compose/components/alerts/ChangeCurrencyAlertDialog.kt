package com.app.myfincontrol.presentation.compose.components.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.R
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeCurrencyAlertDialog(
    state: Boolean,
    onEvents: (LoginEvents) -> Unit
) {
    if (state) {
        AlertDialog(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp)),
            onDismissRequest = {
                onEvents(LoginEvents.ChangeCurrencyAlert)
            }
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(stringResource(R.string.in_developing))
            }
        }
    }

}