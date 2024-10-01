package com.alexmprog.cryptocoins.feature.coindetails.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface CoinDetailsContent {

    @Composable
    operator fun invoke(component: CoinDetailsComponent, modifier: Modifier)
}