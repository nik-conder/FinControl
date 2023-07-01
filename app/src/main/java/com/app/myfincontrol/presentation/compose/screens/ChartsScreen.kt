package com.app.myfincontrol.presentation.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.myfincontrol.R
import com.app.myfincontrol.presentation.compose.components.ChartsComponent
import com.app.myfincontrol.presentation.compose.components.HeaderComponent
import com.app.myfincontrol.presentation.compose.components.NavigationComponent
import com.app.myfincontrol.presentation.viewModels.ChartsViewModel
import com.himanshoe.charty.bar.model.BarData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartsScreen(
    navController: NavHostController,
) {
    val vm = hiltViewModel<ChartsViewModel>()
    /*
    val onEvents = vm::onEvents
    val state = vm.states.collectAsState()
     */

    val barDataList = listOf(
        BarData(1, 245f),
        BarData(2, 234f),
        BarData(3, 153f),
        BarData(4, 123f),
        BarData(5, 123f),
        BarData(6, 123f),
        BarData(7, 123f),
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_charts),
                    )
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
            val (incomesBox, expensesBox) = createRefs()

            BoxWithConstraints(
                modifier = Modifier
                    .constrainAs(incomesBox) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .heightIn(min = 250.dp, max = 300.dp),
                ) {
                    Row() {
                        HeaderComponent(title = "Доходы")
                    }
                    Row {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ChartsComponent(data = barDataList)
                        }
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
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .heightIn(min = 250.dp, max = 300.dp),
                ) {
                    Row() {
                        HeaderComponent(title = "Расходы")
                    }
                    Row {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ChartsComponent(data = barDataList)
                        }
                    }
                }
            }
        }
    }
}