package com.app.myfincontrol.presentation.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.app.myfincontrol.R
import com.app.myfincontrol.data.entities.InfoPageType
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedComponent(
    feedPager: LazyPagingItems<Transaction>,
    hideBalanceState: Boolean,
    debugModeState: Boolean,
    onEvens: (TransactionEvents) -> Unit,
    snackBarHostState: SnackbarHostState
) {

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val textDeleteTransaction = stringResource(id = R.string.delete_transaction)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Row {
            HeaderComponent(
                title = stringResource(id = R.string.feed) + " " + if (debugModeState) feedPager.itemCount.toString() else ""
            )
        }
        if (debugModeState) {
            Row {
                TextButton(onClick = { feedPager.refresh() }) {
                    Text(text = "Refresh")
                }
            }
        }
        Row {
            LazyColumn(
                modifier = Modifier
                    .height(600.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = listState
            ) {
                if (feedPager.itemCount == 0) {
                    item {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){
                            InfoPageComponent(InfoPageType.NO_DATA)
                        }
                    }
                }

                items(count = feedPager.itemCount) { item ->
                    val element = feedPager[item]
                    val dismissState = rememberDismissState(
                        initialValue = DismissValue.Default,
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = textDeleteTransaction,
                                        duration = SnackbarDuration.Short
                                    )
                                    onEvens(TransactionEvents.DeleteTransaction(element!!.id))
                                    delay(500)
                                    feedPager.refresh()
                                }
                                false
                            } else {
                                false
                            }
                        }
                    )


                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> MaterialTheme.colorScheme.onSecondary
                                    DismissValue.DismissedToEnd -> MaterialTheme.colorScheme.primary
                                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.error
                                }, label = ""
                            )
                            val alignment = when (direction) {
                                DismissDirection.StartToEnd -> Alignment.CenterStart
                                DismissDirection.EndToStart -> Alignment.CenterEnd
                            }
                            val icon = when (direction) {
                                DismissDirection.StartToEnd -> Icons.Default.Done
                                DismissDirection.EndToStart -> Icons.Default.Delete
                            }
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color, RoundedCornerShape(20.dp))
                                    .padding(10.dp),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Localized description",
                                )
                            }
                        },
                        dismissContent = {
                            feedPager.itemKey { element!!.id }
                            TransactionComponent(
                                transaction = element!!,
                                hideBalanceState = hideBalanceState
                            )
                        })
                }

                when (feedPager.loadState.prepend) {
                    is LoadState.Loading -> {
                        item { CircularProgressComponent() }
                    }

                    is LoadState.Error -> {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = " ${(feedPager.loadState.append as LoadState.Error).error.localizedMessage}")
                            }
                        }
                    }

                    is LoadState.NotLoading -> {}
                }

                when (feedPager.loadState.refresh) {
                    is LoadState.Loading -> {
                        item {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ){
                                InfoPageComponent(InfoPageType.LOADING)
                            }
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = " ${(feedPager.loadState.append as LoadState.Error).error.localizedMessage}")
                            }
                        }
                    }

                    is LoadState.NotLoading -> {}
                }
                when (feedPager.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ){
                                InfoPageComponent(InfoPageType.LOADING)
                            }
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = " ${(feedPager.loadState.append as LoadState.Error).error.localizedMessage}")
                            }
                        }
                    }

                    is LoadState.NotLoading -> {}
                }
            }
        }
    }
}