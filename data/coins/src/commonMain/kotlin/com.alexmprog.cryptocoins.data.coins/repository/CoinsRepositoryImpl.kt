package com.alexmprog.cryptocoins.data.coins.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.alexmprog.common.utils.resource.Error
import com.alexmprog.common.utils.resource.Resource
import com.alexmprog.common.utils.resource.map
import com.alexmprog.cryptocoins.data.coins.network.CoinChartDto
import com.alexmprog.cryptocoins.data.coins.network.CoinDetailsDto
import com.alexmprog.cryptocoins.data.coins.network.CoinDto
import com.alexmprog.cryptocoins.data.coins.network.CoinService
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.domain.coins.model.CoinChart
import com.alexmprog.cryptocoins.domain.coins.model.CoinDetails
import com.alexmprog.cryptocoins.domain.coins.repository.CoinsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class CoinsRepositoryImpl(
    private val coinService: CoinService,
    private val coroutineDispatcher: CoroutineDispatcher
) : CoinsRepository {

    companion object{
        private const val USD_CURRENCY = "usd"
        private const val SORT_ORDER = "market_cap_desc"
    }

    override fun getCoins(): Flow<PagingData<Coin>> = Pager(
        config = PagingConfig(pageSize = 25, initialLoadSize = 25, enablePlaceholders = false),
        pagingSourceFactory = {
            ResourcePagingResource { page, pageSize ->
                coinService.getCoins(USD_CURRENCY, SORT_ORDER, pageSize, page).map { it.map { it.toModel() } }
            }
        }
    ).flow.flowOn(coroutineDispatcher)

    override suspend fun getCoinDetails(id: String): Resource<CoinDetails, Error> =
        withContext(coroutineDispatcher) {
            coinService.getCoinDetails(id).map { it.toModel() }
        }

    override suspend fun getCoinChart(id: String): Resource<CoinChart, Error> =
        withContext(coroutineDispatcher) {
            coinService.getCoinChart(id, USD_CURRENCY, 1).map { it.toModel() }
        }
}

internal fun CoinDto.toModel(): Coin =
    Coin(id, name, symbol, currentPrice, priceChangePercentage24h, imageUrl)

internal fun CoinDetailsDto.toModel(): CoinDetails =
    CoinDetails(
        id = id,
        description = description?.value,
        categories = categories,
        marketCap = marketData?.marketCap?.usd,
        marketCapRank = marketCapRank ?: marketData?.marketCapRank,
        high24h = marketData?.high24h?.usd,
        low24h = marketData?.low24h?.usd
    )

internal fun CoinChartDto.toModel(): CoinChart = CoinChart(
    prices = prices.map { pair -> Pair(pair[0].toInt(), pair[1]) }
)