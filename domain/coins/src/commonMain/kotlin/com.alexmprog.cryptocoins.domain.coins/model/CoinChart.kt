package com.alexmprog.cryptocoins.domain.coins.model

data class CoinChart(
    val prices: List<Pair<Int, Double>>
)