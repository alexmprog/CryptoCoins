package com.alexmprog.cryptocoins.feature.coindetails.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
import coil3.compose.rememberAsyncImagePainter
import com.alexmprog.cryptocoins.domain.coins.model.CoinDetails
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsComponent
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsContent
import com.alexmprog.thepets.core.ui.components.CoinPriceChart
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = coinDetailsState.coin?.imageUrl),
                        contentDescription = coinDetailsState.coin?.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                    )
                    Column(
                        modifier = Modifier.weight(1F),
                    ) {
                        Text(
                            text = coinDetailsState.coin?.name ?: "--",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Text(
                            text = "${coinDetailsState.coin?.currentPrice}$",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
            coinDetailsState.coinChart?.let {
                CoinPriceChart(
                    Modifier.fillMaxWidth().height(300.dp).padding(8.dp),
                    it.prices
                )
            }
            coinDetailsState.coinDetails?.let { CoinDetails(it) }
        }
    }
}

@Composable
private fun CoinDetails(details: CoinDetails) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = stringResource(Res.string.market_data),
            style = MaterialTheme.typography.titleLarge,
        )
        TextColumn(stringResource(Res.string.market_cap), formatNumber(details.marketCap))
        TextColumn(stringResource(Res.string.high_24h), details.high24h?.toString())
        TextColumn(stringResource(Res.string.low_24h), details.low24h?.toString())
        TextColumn(stringResource(Res.string.rank), "#${details.marketCapRank}")
    }
}

@Composable
private fun TextColumn(title: String, message: String?) {
    Column(
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

fun formatNumber(number: Long?): String {
    val format = number?.div(BILLION)
    if (format != null) {
        return if (format >= 1) {
            "$${format}B"
        } else {
            "$${format}M"
        }
    }
    return ""
}

private const val BILLION: Long = 1000000000L