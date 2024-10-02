package com.alexmprog.cryptocoins.domain.coins.model

data class CoinDetails(
    val id: String,
    val description: String? = null,
    val categories: List<String>? = null,
    val marketCap: Long? = null,
    val marketCapRank: Int? = null,
    val high24h: Double? = null,
    val low24h: Double? = null,
)