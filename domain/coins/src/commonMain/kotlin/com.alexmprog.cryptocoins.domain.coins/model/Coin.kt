package com.alexmprog.cryptocoins.domain.coins.model

import kotlinx.serialization.Serializable

@Serializable
data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val currentPrice: Double,
    val priceChangePercentage24h: Double,
    val imageUrl: String
)