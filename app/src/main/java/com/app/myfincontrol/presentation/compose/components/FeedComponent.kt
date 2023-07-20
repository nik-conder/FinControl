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
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.app.myfincontrol.R
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FeedComponent(
    feedPager: Pager<Int, Transaction>,
    hideBalanceState: Boolean,
    debugModeState: Boolean,
    onEvens: (TransactionEvents) -> Unit
) {

    val scope = rememberCoroutineScope()
    val feedPagingItems = feedPager.flow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Row() {
            HeaderComponent(
                title = stringResource(id = R.string.feed) + " " + if (debugModeState) feedPagingItems.itemCount.toString() else "",
                paddingValues = PaddingValues(top = 16.dp)
            )
        }
        if (debugModeState) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(text = "Generate:")
                }
                Column() {
                    TextButton(onClick = { onEvens(TransactionEvents.GenerateEvents(TransactionType.INCOME)) }) {
                        Text(text = "+")
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    TextButton(onClick = { onEvens(TransactionEvents.GenerateEvents(TransactionType.EXPENSE)) }) {
                        Text(text = "-")
                    }
                }
                Column() {
                    IconButton(onClick = {
                        scope.launch {
                            feedPagingItems.refresh()
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "")
                    }
                }

                Column() {
                    IconButton(onClick = {
                        scope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.KeyboardArrowUp, contentDescription = "")
                    }
                }
                Column() {
                    IconButton(onClick = {
                        scope.launch {
                            listState.animateScrollToItem(index = feedPagingItems.itemCount)
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.KeyboardArrowDown, contentDescription = "")
                    }
                }
            }

        }

        Row() {
            LazyColumn(
                modifier = Modifier
                    .height(600.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = listState
            ) {
                if (feedPagingItems.itemCount == 0) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp, bottom = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row() {
                                Text(text = stringResource(id = R.string.no_data), style = MaterialTheme.typography.bodyMedium)
                            }
                            Row {
                                Text(text = "\uD83D\uDE43", fontSize = 48.sp)
                            }
                        }
                    }
                }

                items(count = feedPagingItems.itemCount) { item ->
                    val item = feedPagingItems[item]
                    var dismissState = rememberDismissState(
                        initialValue = DismissValue.Default,
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                onEvens(TransactionEvents.DeleteTransaction(item!!.id))
                                scope.launch {
                                    delay(500)
                                    feedPagingItems.refresh()
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
                                }
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
                            feedPagingItems.itemKey { item!!.id }
                            TransactionComponent(
                                transaction = item!!,
                                hideBalanceState = hideBalanceState,
                                debugModeState = debugModeState
                            )
                        })
                }

                when (feedPagingItems.loadState.prepend) {
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
                                Text(text = " ${(feedPagingItems.loadState.append as LoadState.Error).error.localizedMessage}")
                            }
                        }
                    }
                    is LoadState.NotLoading -> {}
                }

                when (feedPagingItems.loadState.refresh) {
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
                                Text(text = " ${(feedPagingItems.loadState.append as LoadState.Error).error.localizedMessage}")
                            }
                        }
                    }
                    is LoadState.NotLoading -> {}
                }
                when (feedPagingItems.loadState.append) {
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
                                Text(text = " ${(feedPagingItems.loadState.append as LoadState.Error).error.localizedMessage}")
                            }
                        }
                    }
                    is LoadState.NotLoading -> {
                        if (feedPagingItems.itemCount > 0) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 32.dp, bottom = 32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row() {
                                        Text(text = stringResource(id = R.string.no_data), style = MaterialTheme.typography.bodyMedium)
                                    }
                                    Row {
                                        Text(text = "\uD83E\uDD2B", fontSize = 48.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}