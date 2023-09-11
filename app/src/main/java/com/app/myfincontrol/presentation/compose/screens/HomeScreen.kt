package com.app.myfincontrol.presentation.compose.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberPlainTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.Currency
import com.app.myfincontrol.data.sources.UserStore
import com.app.myfincontrol.presentation.compose.components.AddTransactionComponent
import com.app.myfincontrol.presentation.compose.components.AdvicesComponent
import com.app.myfincontrol.presentation.compose.components.FeedComponent
import com.app.myfincontrol.presentation.compose.components.HomeMainBoxComponent
import com.app.myfincontrol.presentation.compose.components.NavigationComponent
import com.app.myfincontrol.presentation.compose.components.PlainTooltipComponent
import com.app.myfincontrol.presentation.compose.components.alerts.DebugModeAlertComponent
import com.app.myfincontrol.presentation.utils.UtilsCompose
import com.app.myfincontrol.presentation.viewModels.HomeViewModel
import com.app.myfincontrol.presentation.viewModels.events.DebugEvents
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    store: UserStore,
    snackBarHostState: SnackbarHostState
) {
    val vm = hiltViewModel<HomeViewModel>()
    val onEventsTransaction = vm::onEventsTransaction
    val onEventDebugMode = vm::onEventDebugMode

    val state = vm.states.collectAsState()

    val sheetState = rememberModalBottomSheetState()

    val scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()

    val darkMode = store.darkModeState.collectAsState(initial = false)
    val hideBalanceState = store.hideBalanceState.collectAsState(initial = false)
    val adviceBoxState = store.adviceBox.collectAsState(initial = false)
    val debugModeState = store.debugModeState.collectAsState(initial = false)

    val tooltipState = rememberPlainTooltipState()

    val feedPager = vm.feedPager.flow.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(text = stringResource(id = R.string.app_name))
                        }
                        AnimatedVisibility(
                            visible = scrollState.value > 100,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically(),
                        ) {
                            if (state.value.selectedProfile != null) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .padding(
                                                top = 4.dp,
                                                start = 16.dp,
                                                end = 16.dp,
                                                bottom = 4.dp
                                            )
                                    ) {
                                        Text(
                                            text = if (hideBalanceState.value)
                                                "\uD83E\uDD11 \uD83E\uDD11 \uD83E\uDD11"
                                            else
                                                "${
                                                    UtilsCompose.Symbols.currencySymbolComponent(
                                                        state.value.selectedProfile!!.currency
                                                    )
                                                } ${
                                                    UtilsCompose.Numbers.formatBigDecimalWithSpaces(
                                                        state.value.balance
                                                    )
                                                }",
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }

                        }
                    }
                },
                actions = {
                    Column {
                        IconButton(onClick = {
                            scope.launch {
                                store.setDarkMode()
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = if (darkMode.value) R.drawable.ic_baseline_light_mode_24 else R.drawable.ic_baseline_dark_mode_24),
                                contentDescription = stringResource(
                                    id = R.string.dark_mode
                                )
                            )
                        }
                    }
                    if (debugModeState.value) {
                        Column {
                            IconButton(onClick = {
                                onEventDebugMode(DebugEvents.ShowAlertDebugMode)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_settings_suggest_24),
                                    contentDescription = stringResource(
                                        id = R.string.debug_mode
                                    )
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            PlainTooltipComponent(
                tooltip = { Text(text = stringResource(id = R.string.add_transaction)) },
                tooltipState = tooltipState,
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .tooltipTrigger(),
                    onClick = { scope.launch { sheetState.show() } }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(id = R.string.add_transaction)
                    )
                }
            }
        },
        bottomBar = {
            NavigationComponent(navController = navController)
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                //.background(backgroundColorBrush)
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxSize(),
        ) {

            DebugModeAlertComponent(
                state = state.value.debugModeShow,
                onEvent = onEventDebugMode,
                snackBarHostState = snackBarHostState
            )

            val (mainBox, adviceBox, feedBox, financeCharts) = createRefs()

            if (sheetState.isVisible) {
                ModalBottomSheet(
                    modifier = Modifier
                        .wrapContentHeight(),
                    onDismissRequest = {
                        scope.launch { sheetState.hide() }
                    },
                    sheetState = sheetState,
                ) {
                    AddTransactionComponent(
                        sheetState = sheetState,
                        feedPager = feedPager,
                        onEvents = onEventsTransaction
                    )
                }
            }

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(mainBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            ) {
                HomeMainBoxComponent(
                    profileName = state.value.selectedProfile?.name ?: "...",
                    balance = state.value.balance,
                    store = store,
                    currency = state.value.selectedProfile?.currency ?: Currency.USD,
                    snackBarHostState = snackBarHostState,
                    onEventsTransaction = onEventsTransaction,
                )
            }
            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(adviceBox) {
                        top.linkTo(mainBox.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                if (adviceBoxState.value) {
                    AdvicesComponent()
                }
            }

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(financeCharts) {
                        top.linkTo(adviceBox.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .background(
//                            color = MaterialTheme.colorScheme.tertiaryContainer,
//                            shape = RoundedCornerShape(20.dp)
//                        )
//                        .fillMaxWidth()
//                        .heightIn(min = 200.dp, max = 300.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    ChartsComponent(data = emptyList())
//                }
            }

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(feedBox) {
                        top.linkTo(financeCharts.bottom)
                        start.linkTo(parent.start)
                    }
            ) {
                Row {
                    FeedComponent(
                        feedPager = feedPager,
                        hideBalanceState = hideBalanceState.value,
                        debugModeState = debugModeState.value,
                        onEvens = onEventsTransaction,
                        snackBarHostState = snackBarHostState
                    )
                }
            }
        }
    }
}