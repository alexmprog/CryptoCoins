package com.alexmprog.cryptocoins.feature.coindetails.api

import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.domain.coins.model.CoinChart
import com.alexmprog.cryptocoins.domain.coins.model.CoinDetails
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface CoinDetailsComponent {

    data class State(
        val coin: Coin? = null,
        val coinDetails: CoinDetails? = null,
        val coinChart: CoinChart? = null
    )

    val state: StateFlow<State>

    fun onBackPressed()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            coin: Coin,
            onFinished: () -> Unit,
        ): CoinDetailsComponent
    }
}