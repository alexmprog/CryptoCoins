package com.alexmprog.cryptocoins.domain.coins.usecase

import app.cash.paging.PagingData
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.domain.coins.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow

class GetCoinsUseCase(private val coinsRepository: CoinsRepository) {

    operator fun invoke(): Flow<PagingData<Coin>> = coinsRepository.getCoins()
}