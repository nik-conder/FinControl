package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.myfincontrol.R
import com.app.myfincontrol.data.entities.Transactions
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun FeedComponent(
    feedSource: SharedFlow<PagingData<Transactions>>,
    onEvens: (TransactionEvents) -> Unit
) {
    val listState = rememberLazyListState()
    val scroll = rememberScrollState()

    val scope = rememberCoroutineScope()

    val feedPagingItems = feedSource.collectAsLazyPagingItems()



//    LaunchedEffect(listState) {
//        println("что то происходит...")
//        val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
//
//        while (true) {
//            listState.let {
//                println("it.firstVisibleItemIndex ${it.firstVisibleItemIndex}")
//                println("it.firstVisibleItemScrollOffset ${it.firstVisibleItemScrollOffset}")
//                println("lastVisibleItemIndex $lastVisibleItemIndex")
//
//            }
//            delay(1000)
//        }
//    }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Row() {
            HeaderComponent(title = stringResource(id = R.string.title_feed) + " " + feedPagingItems.itemCount)
        }
        Row {
            Column() {
                TextButton(onClick = {
                    scope.launch {
                        listState.animateScrollToItem(feedPagingItems.itemCount - 1)
                    }
                }) {
                    Text(text = "Bottom scroll")
                }
            }
            Column() {
                TextButton(onClick = {
                    scope.launch {
                        feedPagingItems.refresh()
                    }
                }) {
                    Text(text = "Refresh")
                }
            }

            Column() {
                TextButton(onClick = {
                    scope.launch {
                        onEvens(TransactionEvents.LoadTransactions)
                    }
                }) {
                    Text(text = "Invalidate")
                }
            }

            Column() {
                TextButton(onClick = {
                    scope.launch {
                        feedPagingItems.retry()
                    }
                }) {
                    Text(text = "Retry")
                }
            }
        }

        Row() {
            LazyColumn(
                modifier = Modifier
                    //.fillMaxHeight(0.5f)
                    .height(600.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = listState,
                userScrollEnabled = true
            ) {

                items(
                    items = feedPagingItems.itemSnapshotList.items,
                    key = { it.id ?: 0 }
                ) { item ->
                    TransactionComponent(transactions = item)
                }

                when (feedPagingItems.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                        println("append loading")
                    }
                    is LoadState.Error -> {
                        item {
                            Text(text = "Error")
                            println("append error")
                        }
                    }
                    is LoadState.NotLoading -> {
                        println("append not loading")
                        item {
                            Text(text ="append not loading")
                        }
                    }
                }
            }

            }
        }

}