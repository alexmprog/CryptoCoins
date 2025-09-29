package com.alexmprog.cryptocoins.feature.coindetails.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.arkivanov.decompose.ComponentContext

interface CoinDetailsComponent {

    @Composable
    fun Content(modifier: Modifier)

    data class Args(val coin: Coin)

    interface Actions {
        fun onBack()
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            args: Args,
            actions: Actions,
        ): CoinDetailsComponent
    }
}