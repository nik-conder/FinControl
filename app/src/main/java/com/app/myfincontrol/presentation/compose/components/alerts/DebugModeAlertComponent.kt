package com.app.myfincontrol.presentation.compose.components.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.presentation.viewModels.events.DebugEvents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugModeAlertComponent(
    state: Boolean,
    onEvent: (DebugEvents) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    if (state) {
        val scope = rememberCoroutineScope()
        AlertDialog(
            onDismissRequest = {
                onEvent(DebugEvents.ShowAlertDebugMode)
            }
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Column() {
                    LazyColumn(userScrollEnabled = true) {
                        item {
                            TextButton(onClick = {
                                scope.launch {
                                    snackBarHostState.showSnackbar("Будет сгенерировано 10 транзакций")
                                }
                                onEvent(DebugEvents.GenerateTransactions(TransactionType.INCOME, 10))
                            }) {
                                Text("Сгенерировать транзакции -")
                            }
                        }
                        item {
                            TextButton(onClick = {
                                scope.launch {
                                    snackBarHostState.showSnackbar("Будет сгенерировано 10 транзакций")
                                }
                                onEvent(DebugEvents.GenerateTransactions(TransactionType.EXPENSE, 10))
                            }) {
                                Text("Сгенерировать транзакции +")
                            }
                        }
                        item {
                            TextButton(onClick = {
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = "Short snackbar",
                                        duration= SnackbarDuration.Short
                                    )
                                }
                            }) {
                                Text("Show snackbar SHORT")
                            }
                        }
                        item {
                            TextButton(onClick = {
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = "Long snackbar",
                                        duration= SnackbarDuration.Long
                                    )
                                }
                            }) {
                                Text("Show snackbar LONG")
                            }
                        }
                        item {
                            TextButton(onClick = {
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = "Short snackbar with action",
                                        actionLabel = "Test action",
                                        duration= SnackbarDuration.Short
                                    )
                                }
                            }) {
                                Text("Show snackbar with Action")
                            }
                        }
                    }
                }
            }
        }
    }
    }