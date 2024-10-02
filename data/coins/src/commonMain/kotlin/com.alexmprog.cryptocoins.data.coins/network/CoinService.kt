package com.alexmprog.cryptocoins.data.coins.network

import com.alexmprog.common.utils.resource.Error
import com.alexmprog.common.utils.resource.Resource
import com.alexmprog.cryptocoins.core.network.fetch
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.appendPathSegments

internal interface CoinService {

    suspend fun getCoins(
        currency: String, order: String, size: Int, page: Int
    ): Resource<List<CoinDto>, Error>

    suspend fun getCoinDetails(id: String): Resource<CoinDetailsDto, Error>

    suspend fun getCoinChart(
        id: String, currency: String, days: Int,
    ): Resource<CoinChartDto, Error>
}

internal class CoinServiceImpl(private val httpClient: HttpClient) : CoinService {

    companion object {
        private const val BASE_URL = "https://api.coingecko.com/api/v3/"
        // TODO: use interceptor in real application
        private const val TEST_API_KEY = ""
        private const val HEADER = "x-cg-demo-api-key"
        private const val COINS = "coins"
        private const val MARKETS = "markets"
        private const val VS_CURRENCY = "vs_currency"
        private const val ORDER = "order"
        private const val PER_PAGE = "per_page"
        private const val PAGE = "page"
        private const val MARKET_CHART = "market_chart"
        private const val DAYS = "days"
    }

    override suspend fun getCoins(
        currency: String, order: String, size: Int, page: Int
    ): Resource<List<CoinDto>, Error> =
        httpClient.fetch<List<CoinDto>> {
            url {
                url(BASE_URL)
                appendPathSegments(COINS, MARKETS)
                header(HEADER, TEST_API_KEY)
                parameters.append(VS_CURRENCY, currency)
                parameters.append(ORDER, order)
                parameters.append(PER_PAGE, size.toString())
                parameters.append(PAGE, page.toString())
            }
        }

    override suspend fun getCoinDetails(id: String): Resource<CoinDetailsDto, Error> =
        httpClient.fetch<CoinDetailsDto> {
            url {
                url(BASE_URL)
                appendPathSegments(COINS, id)
                header(HEADER, TEST_API_KEY)
            }
        }

    override suspend fun getCoinChart(
        id: String,
        currency: String,
        days: Int
    ): Resource<CoinChartDto, Error> = httpClient.fetch<CoinChartDto> {
        url {
            url(BASE_URL)
            appendPathSegments(COINS, id, MARKET_CHART)
            header(HEADER, TEST_API_KEY)
            parameters.append(VS_CURRENCY, currency)
            parameters.append(DAYS, days.toString())
        }
    }
}
