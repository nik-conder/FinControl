package com.app.myfincontrol.presentation.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.myfincontrol.R
import com.app.myfincontrol.data.Currency
import com.app.myfincontrol.presentation.compose.components.AdvicesComponent
import com.app.myfincontrol.presentation.compose.components.FeedComponent
import com.app.myfincontrol.presentation.compose.components.FinancialChartsComponent
import com.app.myfincontrol.presentation.compose.components.HomeMainBoxComponent
import com.app.myfincontrol.presentation.compose.components.NavigationComponent
import com.app.myfincontrol.presentation.viewModels.HomeViewModel
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val vm = hiltViewModel<HomeViewModel>()
    val onEvents = vm::onEvents
    val onEventsTransaction = vm::onEventsTransaction

    val states = vm.states.collectAsState()



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
                Text(text = stringResource(id = R.string.app_name))
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
                .verticalScroll(rememberScrollState())
                .fillMaxSize(1f),
        ) {
            val (mainBox, adviceBox, feedBox, financeCharts) = createRefs()

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(mainBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            ) {
                HomeMainBoxComponent(
                    profileName = states.value.selectedProfile?.name ?: "...",
                    balance = states.value.balance,
                    currency = states.value.selectedProfile?.currency ?: Currency.USD,
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
                AdvicesComponent()
            }

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(financeCharts) {
                        top.linkTo(adviceBox.bottom)
                        start.linkTo(parent.start)
                    }
            ) {
                FinancialChartsComponent()
            }

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(feedBox) {
                        top.linkTo(financeCharts.bottom)
                        start.linkTo(parent.start)
                    }
            ) {
                Column() {
                    Row() {
                        TextButton(onClick = { onEventsTransaction.invoke(TransactionEvents.GenerateEvents) }) {
                            Text(text = "Generate events")
                        }
                    }
                    Row() {
                        FeedComponent(vm.feedDataSource)
                    }
                }
            }
        }
    }
}