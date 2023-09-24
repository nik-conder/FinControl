package com.app.myfincontrol.presentation.compose.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.presentation.compose.components.BoxComponent
import com.app.myfincontrol.presentation.compose.components.ChartsComponent
import com.app.myfincontrol.presentation.compose.components.HeaderComponent
import com.app.myfincontrol.presentation.compose.components.NavigationComponent
import com.app.myfincontrol.presentation.compose.components.alerts.DataExchangeAlertComponent
import com.app.myfincontrol.presentation.viewModels.StatisticsViewModel
import com.app.myfincontrol.presentation.viewModels.events.StatisticsEvents

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {

    val vm = hiltViewModel<StatisticsViewModel>()
    val onEvents = vm::onEvents
    val state = vm.states.collectAsState()
    val chartIncome = state.value.chartIncome
    val chartExpense = state.value.chartExpense
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.statistics),
                    )
                }
            )
        },
        bottomBar = {
            NavigationComponent(navController = navController)
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                //.background(backgroundColorBrush)
                .padding(paddingValues)
                .verticalScroll(verticalScrollState)
                .fillMaxSize(1f),
        ) {

            DataExchangeAlertComponent(
                state = state.value.dataExchangeAlert,
                onEvents = onEvents,
                sort = ChartSort.DAY,
                snackBarHostState = snackBarHostState,
            )

            val (incomesBox, expensesBox) = createRefs()

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(incomesBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {

                BoxComponent(
                    header = {
                        Row {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                HeaderComponent(
                                    title = stringResource(id = R.string.incomes)
                                )
                            }
                            Column {
                                IconButton(
                                    onClick = {
                                        onEvents(
                                            StatisticsEvents.GetChart(
                                                type = TransactionType.INCOME,
                                                sort = state.value.chartCurrentSortIncome
                                            )
                                        )
                                    }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Refresh,
                                        contentDescription = "Refresh"
                                    )
                                }
                            }
                            Column {
                                IconButton(
                                    onClick = {
                                        onEvents(StatisticsEvents.DataExchangeAlert)
                                    }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_download_24),
                                        contentDescription = "" // todo
                                    )
                                }
                            }
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.chart_incomes_of_the) + " " + when (state.value.chartCurrentSortIncome) {
                                ChartSort.WEEK -> stringResource(id = R.string.week)
                                ChartSort.MONTH -> stringResource(id = R.string.month)
                                ChartSort.QUARTER -> stringResource(id = R.string.quarter)
                                ChartSort.YEAR -> stringResource(id = R.string.year)
                                else -> stringResource(id = R.string.day)
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .horizontalScroll(horizontalScrollState),
                    ) {
                        Column {
                            TextButton(
                                onClick = {
                                    onEvents(
                                        StatisticsEvents.GetChart(
                                            type = TransactionType.INCOME,
                                            sort = ChartSort.DAY
                                        )
                                    )
                                },
                            ) {
                                Text(text = stringResource(id = R.string.day))
                            }
                        }
                        Column {
                            TextButton(onClick = {
                                onEvents(
                                    StatisticsEvents.GetChart(
                                        type = TransactionType.INCOME,
                                        sort = ChartSort.WEEK
                                    )
                                )
                            }) {
                                Text(text = stringResource(id = R.string.week))
                            }
                        }
                        Column {
                            TextButton(onClick = {
                                onEvents(
                                    StatisticsEvents.GetChart(
                                        type = TransactionType.INCOME,
                                        sort = ChartSort.MONTH
                                    )
                                )
                            }) {
                                Text(text = stringResource(id = R.string.month))
                            }
                        }
                        Column {
                            TextButton(onClick = {
                                onEvents(
                                    StatisticsEvents.GetChart(
                                        type = TransactionType.INCOME,
                                        sort = ChartSort.QUARTER
                                    )
                                )
                            }) {
                                Text(text = stringResource(id = R.string.quarter))
                            }
                        }
                        Column {
                            TextButton(onClick = {
                                onEvents(
                                    StatisticsEvents.GetChart(
                                        type = TransactionType.INCOME,
                                        sort = ChartSort.YEAR
                                    )
                                )
                            }) {
                                Text(text = stringResource(id = R.string.year))
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        ChartsComponent(data = chartIncome)
                    }
                }
            }
            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(expensesBox) {
                        top.linkTo(incomesBox.bottom, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
            ) {
                BoxComponent(
                    header = {
                        Row {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                HeaderComponent(
                                    title = stringResource(id = R.string.expenses)
                                )
                            }
                            Column {
                                IconButton(
                                    onClick = {
                                        onEvents(
                                            StatisticsEvents.GetChart(
                                                type = TransactionType.EXPENSE,
                                                sort = state.value.chartCurrentSortExpense
                                            )
                                        )
                                    }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Refresh,
                                        contentDescription = "Refresh"
                                    )
                                }
                            }
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = stringResource(id = R.string.chart_expenses_of_the) + " " + when (state.value.chartCurrentSortExpense) {
                                ChartSort.WEEK -> stringResource(id = R.string.week)
                                ChartSort.MONTH -> stringResource(id = R.string.month)
                                ChartSort.QUARTER -> stringResource(id = R.string.quarter)
                                ChartSort.YEAR -> stringResource(id = R.string.year)
                                else -> stringResource(id = R.string.day)
                            },
                        )
                    }

                    Row(
                        modifier = Modifier
                            .horizontalScroll(horizontalScrollState),
                    ) {
                        Column {
                            TextButton(
                                onClick = {
                                    onEvents(
                                        StatisticsEvents.GetChart(
                                            type = TransactionType.EXPENSE,
                                            sort = ChartSort.DAY
                                        )
                                    )
                                },
                            ) {
                                Text(text = stringResource(id = R.string.day))
                            }
                        }
                        Column {
                            TextButton(onClick = {
                                onEvents(
                                    StatisticsEvents.GetChart(
                                        type = TransactionType.EXPENSE,
                                        sort = ChartSort.WEEK
                                    )
                                )
                            }) {
                                Text(text = stringResource(id = R.string.week))
                            }
                        }
                        Column {
                            TextButton(onClick = {
                                onEvents(
                                    StatisticsEvents.GetChart(
                                        type = TransactionType.EXPENSE,
                                        sort = ChartSort.MONTH
                                    )
                                )
                            }) {
                                Text(text = stringResource(id = R.string.month))
                            }
                        }
                        Column {
                            TextButton(onClick = {
                                onEvents(
                                    StatisticsEvents.GetChart(
                                        type = TransactionType.EXPENSE,
                                        sort = ChartSort.QUARTER
                                    )
                                )
                            }) {
                                Text(text = stringResource(id = R.string.quarter))
                            }
                        }
                        Column {
                            TextButton(onClick = {
                                onEvents(
                                    StatisticsEvents.GetChart(
                                        type = TransactionType.EXPENSE,
                                        sort = ChartSort.YEAR
                                    )
                                )
                            }) {
                                Text(text = stringResource(id = R.string.year))
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        ChartsComponent(data = chartExpense)
                    }
                }
            }
        }
    }
}