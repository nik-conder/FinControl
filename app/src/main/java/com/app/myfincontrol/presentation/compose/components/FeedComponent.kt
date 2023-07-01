package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedComponent(
    feedPager: Pager<Int, Transaction>,
    hideBalance: Boolean,
    onEvens: (TransactionEvents) -> Unit
) {

    val scroll = rememberScrollState()

    val scope = rememberCoroutineScope()

    val feedPagingItems = feedPager.flow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()



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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                TextButton(onClick = {
                    scope.launch {
                        onEvens(TransactionEvents.GenerateEvents)
                    }
                }) {
                    Text(text = "Generate")
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

        Row() {
            LazyColumn(
                modifier = Modifier
                    //.fillMaxHeight(0.5f)
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
                                Text(text = "Вы ещё не добавляли транзакции..", style = MaterialTheme.typography.bodyMedium)
                            }
                            Row {
                                Text(text = "\uD83D\uDE43", fontSize = 48.sp)
                            }
                        }
                    }
                }

                items(count = feedPagingItems.itemCount) {
                    val item = feedPagingItems[it]
                    feedPagingItems.itemKey { item!!.id }
                    TransactionComponent(transaction = item!!, hideBalance = hideBalance)
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
                                        Text(text = "Дальше только тишина..", style = MaterialTheme.typography.bodyMedium)
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