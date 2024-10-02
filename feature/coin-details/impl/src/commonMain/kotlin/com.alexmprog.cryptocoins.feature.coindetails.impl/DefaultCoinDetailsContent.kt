package com.alexmprog.cryptocoins.feature.coindetails.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.alexmprog.common.utils.format.toFormattedString
import com.alexmprog.cryptocoins.domain.coins.model.CoinDetails
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsComponent
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsContent
import com.alexmprog.cryptocoins.core.ui.components.CoinContent
import com.alexmprog.cryptocoins.core.ui.components.CoinPriceChart
import com.alexmprog.cryptocoins.core.ui.components.LoadingItem
import com.alexmprog.cryptocoins.core.ui.components.LoadingView
import org.jetbrains.compose.resources.stringResource

class DefaultCoinDetailsContent : CoinDetailsContent {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun invoke(component: CoinDetailsComponent, modifier: Modifier) {
        val state by component.state.collectAsState()
        Scaffold(topBar = {
            TopAppBar(title = { Text(stringResource(Res.string.details)) },
                navigationIcon = {
                    IconButton(onClick = { component.onBackPressed() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    }
                })
        }) { innerPaddings ->
            CoinDetailScreen(Modifier.padding(innerPaddings).fillMaxSize(), state)
        }
    }
}

@Composable
private fun CoinDetailScreen(modifier: Modifier, coinDetailsState: CoinDetailsComponent.State) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 0.dp)
                .verticalScroll(rememberScrollState())
        ) {
            coinDetailsState.coin?.let {
                CoinContent(
                    name = it.name,
                    symbol = it.symbol,
                    imageUrl = it.imageUrl,
                    price = it.currentPrice,
                    priceChangePercentage24h = it.priceChangePercentage24h,
                )
            }
            Box(
                Modifier.fillMaxWidth().height(200.dp)
                    .padding(horizontal = 8.dp, vertical = 0.dp)
            ) {
                coinDetailsState.coinChart?.let {
                    CoinPriceChart(Modifier.fillMaxSize(), it.prices)
                } ?: run {
                    LoadingView()
                }
            }
            coinDetailsState.coinDetails?.let { CoinDetails(it) } ?: run {
                LoadingItem()
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CoinDetails(details: CoinDetails) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextColumn(stringResource(Res.string.market_cap), details.marketCap?.toFormattedString())
        TextColumn(stringResource(Res.string.high_24h), details.high24h?.toString())
        TextColumn(stringResource(Res.string.low_24h), details.low24h?.toString())
        TextColumn(stringResource(Res.string.rank), "#${details.marketCapRank}")
    }
    details.description?.let {
        Text(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            text = it.trim(),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    details.categories?.let {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            it.forEach { TextChip(it) }
        }
    }
}

@Composable
private fun TextColumn(title: String, message: String?) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = message ?: "-",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun TextChip(title: String) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp)),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}