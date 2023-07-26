package com.app.myfincontrol.presentation.compose.components.alerts

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.presentation.viewModels.events.StatisticsEvents
import com.example.compose.FinControlTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataExchangeAlertComponent(
    state: Boolean,
    sort: ChartSort,
    snackBarHostState: SnackbarHostState,
    onEvents: (StatisticsEvents) -> Unit
) {
    val scope = rememberCoroutineScope()
    val dropdownMenuFileState = remember { mutableStateOf(false) }
    val dropdownMenuSortState = remember { mutableStateOf(false) }

    if (state) {
        AlertDialog(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp)),
            onDismissRequest = {
                onEvents(StatisticsEvents.DataExchangeAlert)
            }
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Экспорт данных",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "Формат файла:",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        Column() {
                            TextButton(onClick = {
                                dropdownMenuFileState.value = !dropdownMenuFileState.value
                            }) {
                                Text(text = "CSV")
                            }
                            DropdownMenu(
                                expanded = dropdownMenuFileState.value,
                                onDismissRequest = {
                                    dropdownMenuFileState.value = !dropdownMenuFileState.value
                                }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = "CSV") },
                                    onClick = { /*TODO*/ }
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "Период:",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        Column() {
                            TextButton(onClick = {
                                dropdownMenuSortState.value = !dropdownMenuSortState.value
                            }) {
                                Text(text = stringResource(id = R.string.for_all_time))
                            }
                            DropdownMenu(
                                expanded = dropdownMenuSortState.value,
                                onDismissRequest = {
                                    dropdownMenuSortState.value = !dropdownMenuSortState.value
                                }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(id = R.string.for_all_time))
                                    },
                                    onClick = {
                                        // todo
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(id = R.string.for_year))
                                    },
                                    onClick = {
                                        // todo
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(id = R.string.for_quarter))
                                    },
                                    onClick = {
                                        // todo
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(id = R.string.for_month))
                                    },
                                    onClick = {
                                        // todo
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(id = R.string.for_week))
                                    },
                                    onClick = {
                                        // todo
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(id = R.string.for_day))
                                    },
                                    onClick = {
                                        // todo
                                    }
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Данные будут экспортированы в CSV файл в папку Documents за всё время ${stringResource(id = R.string.for_all_time)}",
                            style = MaterialTheme.typography.bodyMedium,
                            softWrap = true
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            scope.launch {
                                snackBarHostState.showSnackbar(
                                    message = "Сохранено в Documents"
                                )
                            }
                        }) {
                            Text(text = "Экспорт")
                        }
                    }
                }
            }

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = false, showBackground = true
)
@Composable
fun DataExchangeAlertPreview() {
    FinControlTheme() {
        DataExchangeAlertComponent(state = true, onEvents = {}, sort = ChartSort.DAY, snackBarHostState = SnackbarHostState())
    }
}

@Preview(locale = "ru-rRU",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun DataExchangeAlertPreview2() {
    FinControlTheme() {
        DataExchangeAlertComponent(state = true, onEvents = {}, sort = ChartSort.DAY, snackBarHostState = SnackbarHostState())
    }
}