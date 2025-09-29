package com.alexmprog.cryptocoins.feature.coinlist.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.arkivanov.decompose.ComponentContext

interface CoinListComponent {

    @Composable
    fun Content(modifier: Modifier)

    interface Actions {
        fun onCoinClicked(coin: Coin)
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            actions: Actions,
        ): CoinListComponent
    }
}