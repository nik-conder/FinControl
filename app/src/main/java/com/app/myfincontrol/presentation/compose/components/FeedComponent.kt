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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.myfincontrol.R
import com.app.myfincontrol.data.entities.Transactions
import com.app.myfincontrol.data.sources.FeedDataSource
import kotlinx.coroutines.launch

@Composable
fun FeedComponent(
    feedDataSource: FeedDataSource
) {
    val listState = rememberLazyListState()
    val scroll = rememberScrollState()

    val scope = rememberCoroutineScope()


    val feedDataSourcePaging = remember {
        Pager(
            PagingConfig(pageSize = 30),
            pagingSourceFactory = { feedDataSource }
        ).flow.cachedIn(scope)
    }.collectAsLazyPagingItems()


    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Row() {
            HeaderComponent(title = stringResource(id = R.string.title_feed) + " " + feedDataSourcePaging.itemCount)
        }

        Row() {
            LazyColumn(
                modifier = Modifier
                    //.fillMaxHeight(0.5f)
                    .height(600.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = rememberLazyListState(),
            ) {
                items(feedDataSourcePaging.itemSnapshotList) { item ->
                    if (item != null) {
                        TransactionComponent(transactions = item)
                    } else {
                        Text(text = "Нет данных")
                    }
                }

                feedDataSource.apply {
                    when (feedDataSourcePaging.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }
                        }
                        is LoadState.NotLoading -> {
                            scope.launch {
                                //feedDataSource.load(feedDataSourcePaging.loadState.append.endOfPaginationReached)
                            }
                        }
                        is LoadState.Error -> {
                            val errorState = feedDataSourcePaging.loadState.append as LoadState.Error
                            item {
                                Text(
                                    text = "Ошибка: ${errorState.error}",
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }
                        }
                        else -> {
                            // Do nothing
                        }
                    }
                }
            }
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Box(
//                    modifier = Modifier
//                        .background(
//                            color = MaterialTheme.colorScheme.tertiaryContainer,
//                            shape = RoundedCornerShape(20.dp)
//                        )
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                        .padding(16.dp)
//
//                ) {
//                    Box() {
//                        Text(text = "Нет данных")
//                    }
//
//                }
//            }
        }
    }
}