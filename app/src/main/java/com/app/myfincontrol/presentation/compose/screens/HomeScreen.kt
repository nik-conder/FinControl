package com.app.myfincontrol.presentation.compose.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.Currency
import com.app.myfincontrol.presentation.compose.components.AdvicesComponent
import com.app.myfincontrol.presentation.compose.components.BoxChart
import com.app.myfincontrol.presentation.compose.components.FeedComponent
import com.app.myfincontrol.presentation.compose.components.HomeMainBoxComponent
import com.app.myfincontrol.presentation.compose.components.NavigationComponent
import com.app.myfincontrol.presentation.compose.components.NotProfileComponent
import com.app.myfincontrol.presentation.compose.components.currencySymbolComponent
import com.app.myfincontrol.presentation.compose.components.sheets.AddTransactionSheet
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.app.myfincontrol.presentation.viewModels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val vm = hiltViewModel<HomeViewModel>()
    val onEvents = vm::onEvents
    val onEventsTransaction = vm::onEventsTransaction

    val state = vm.states.collectAsState()

    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val scrollState = rememberScrollState()


    //val feedPagingItems = vm.transactionsFlow.collectAsLazyPagingItems()

//    LaunchedEffect(!states.value.isLogin) {
//        navController.navigate(Screen.Login.route)
//    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                title =  {
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
                         visible = scrollState.value > 400,
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
                                         .padding(8.dp)
                                 ) {
                                     Text(text = if (state.value.hideBalance) "\uD83E\uDD11 \uD83E\uDD11 \uD83E\uDD11" else "${currencySymbolComponent(
                                         state.value.selectedProfile!!.currency)} ${state.value.balance}", fontSize = 14.sp)
                                 }
                             }
                         }

                     }
                 }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.ic_baseline_light_mode_24 else R.drawable.ic_baseline_dark_mode_24), contentDescription = stringResource(
                            id = R.string.title_dark_mode
                        ))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheet.value = !showBottomSheet.value
            }) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = stringResource(id = R.string.add_transaction_content_description))
            }
        },
        bottomBar = {
           BottomAppBar() {
               NavigationComponent(navController = navController)
           }
        }
        ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                //.background(backgroundColorBrush)
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxSize(1f),
        ) {
            val (mainBox, adviceBox, feedBox, financeCharts, notProfileBox) = createRefs()
            if (showBottomSheet.value) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet.value = false
                    },
                    sheetState = sheetState,
                ) {
                    AddTransactionSheet(onEvents = onEventsTransaction)
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
                    currency = state.value.selectedProfile?.currency ?: Currency.USD,
                    onEventsTransaction = onEventsTransaction,
                    hideBalance = state.value.hideBalance
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
                AdvicesComponent()
            }

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(financeCharts) {
                        top.linkTo(adviceBox.bottom)
                        start.linkTo(parent.start)
                    }
            ) {
                BoxChart()
            }

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(feedBox) {
                        top.linkTo(financeCharts.bottom)
                        start.linkTo(parent.start)
                    }
            ) {
                Row() {
                    FeedComponent(vm.feedPager, state.value.hideBalance, onEventsTransaction)
                }
            }
        }
    }
}