package com.alexmprog.cryptocoins.feature.coinlist.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListComponent
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListContent
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.thepets.core.ui.components.CoinCard
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

class DefaultCoinListContent : CoinListContent {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun invoke(component: CoinListComponent, modifier: Modifier) {
        val items by rememberUpdatedState(component.model.collectAsLazyPagingItems())
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(topBar = {
            TopAppBar(title = { Text(stringResource(Res.string.cryptocurrency_prices)) },
                actions = {
                    IconButton(onClick = {
                        items.refresh()
                        coroutineScope.launch { lazyListState.scrollToItem(0) }
                    }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    }
                })
        }) { innerPaddings ->
            LazyPagingColumn(
                Modifier.padding(innerPaddings).fillMaxSize(),
                lazyListState,
                data = items
            ) { component.onCoinClicked(it) }
        }
    }
}

@Composable
private fun LazyPagingColumn(
    modifier: Modifier,
    lazyListState: LazyListState,
    data: LazyPagingItems<Coin>,
    onClick: (Coin) -> Unit
) {
    LazyColumn(modifier = modifier, state = lazyListState) {
        items(data.itemCount, key = data.itemKey { it.id }) { index ->
            data[index]?.let {
                CoinCard(
                    modifier = Modifier.fillMaxSize().wrapContentHeight(),
                    name = it.name,
                    symbol = it.symbol,
                    imageUrl = it.imageUrl,
                    price = it.currentPrice,
                    priceChangePercentage24h = it.priceChangePercentage24h,
                ) {
                    onClick(it)
                }
            }
        }
        data.loadState.apply {
            when {
                refresh is LoadStateNotLoading && data.itemCount < 1 -> {
                    item { EmptyItem() }
                }

                refresh is LoadStateLoading -> {
                    item { LoadingView() }
                }

                refresh is LoadStateError -> {
                    item {
                        ErrorView(
                            message = stringResource(Res.string.no_connection),
                            onClickRetry = { data.retry() },
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                    }
                }

                append is LoadStateLoading -> {
                    item { LoadingItem() }
                }

                append is LoadStateError -> {
                    item {
                        ErrorItem(
                            message = stringResource(Res.string.no_connection),
                            onClickRetry = { data.retry() },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyItem() {
    Box(
        modifier = Modifier.fillMaxWidth(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.no_coins),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier.fillMaxWidth(1f)
            .padding(20.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
private fun ErrorItem(message: String, modifier: Modifier = Modifier, onClickRetry: () -> Unit) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.error
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(Res.string.try_again))
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorView(message: String, modifier: Modifier = Modifier, onClickRetry: () -> Unit) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.error
        )
        OutlinedButton(
            onClick = onClickRetry, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(Res.string.try_again))
        }
    }
}