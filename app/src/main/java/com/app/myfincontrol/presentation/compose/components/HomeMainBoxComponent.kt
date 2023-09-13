package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.Currency
import com.app.myfincontrol.data.sources.UserStore
import com.app.myfincontrol.presentation.utils.UtilsCompose
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import kotlinx.coroutines.launch
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainBoxComponent(
    store: UserStore,
    profileName: String,
    balance: BigDecimal,
    currency: Currency,
    snackBarHostState: SnackbarHostState,
    onEventsTransaction: (TransactionEvents) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val hideBalanceState = store.hideBalanceState.collectAsState(initial = false)
    val dropdownAmountSortState = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.heightIn(min = 150.dp)
            .wrapContentHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFF0D4E42)),
                    startY = 0f,
                    endY = 400f
                ),
                shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (hideBalanceState.value) "\uD83E\uDD11 \uD83E\uDD11 \uD83E\uDD11" else "${
                            UtilsCompose.Numbers.formatBigDecimalWithSpaces(balance)
                        } ${UtilsCompose.Symbols.currencySymbolComponent(currency)}",
                        fontSize = 42.sp,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontWeight = FontWeight(500)
                    )
                }
                Column {
                    IconButton(
                        modifier = Modifier,
                        onClick = { scope.launch { store.sethideBalanceState() } }
                    ) {
                        Icon(
                            painter = painterResource(id = if (hideBalanceState.value) R.drawable.ic_baseline_visibility_off_24 else R.drawable.ic_baseline_visibility_24),
                            contentDescription = stringResource(id = R.string.visibility_content_description),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = profileName,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Column {

                    TextButton(onClick = {
                        dropdownAmountSortState.value = !dropdownAmountSortState.value
                    }) {
                        Text(text = stringResource(id = R.string.for_all_time))
                    }

                    DropdownMenu(
                        expanded = dropdownAmountSortState.value,
                        onDismissRequest = {
                            dropdownAmountSortState.value = !dropdownAmountSortState.value
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.for_all_time))
                            },
                            onClick = {
                                dropdownAmountSortState.value = !dropdownAmountSortState.value
                                scope.launch {
                                    snackBarHostState.showSnackbar(message = "Выбрано")
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.for_year))
                            },
                            onClick = {
                                dropdownAmountSortState.value = !dropdownAmountSortState.value
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.for_quarter))
                            },
                            onClick = {
                                dropdownAmountSortState.value = !dropdownAmountSortState.value
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.for_month))
                            },
                            onClick = {
                                dropdownAmountSortState.value = !dropdownAmountSortState.value
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.for_week))
                            },
                            onClick = {
                                dropdownAmountSortState.value = !dropdownAmountSortState.value
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.for_day))
                            },
                            onClick = {
                                dropdownAmountSortState.value = !dropdownAmountSortState.value
                            }
                        )
                    }
                }
            }
        }
    }
}