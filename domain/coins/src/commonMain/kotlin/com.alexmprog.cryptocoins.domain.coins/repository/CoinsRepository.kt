package com.alexmprog.cryptocoins.domain.coins.repository

import app.cash.paging.PagingData
import com.alexmprog.common.utils.resource.Error
import com.alexmprog.common.utils.resource.Resource
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.domain.coins.model.CoinChart
import com.alexmprog.cryptocoins.domain.coins.model.CoinDetails
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {

    fun getCoins(): Flow<PagingData<Coin>>

    suspend fun getCoinDetails(id: String): Resource<CoinDetails, Error>

    suspend fun getCoinChart(id: String): Resource<CoinChart, Error>
}