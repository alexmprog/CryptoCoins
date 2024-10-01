package com.alexmprog.cryptocoins.feature.coinlist.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface CoinListContent {

    @Composable
    operator fun invoke(component: CoinListComponent, modifier: Modifier)
}