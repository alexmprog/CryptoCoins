package com.alexmprog.cryptocoins.domain.coins.usecase

import com.alexmprog.common.utils.resource.Error
import com.alexmprog.common.utils.resource.Resource
import com.alexmprog.cryptocoins.domain.coins.model.CoinDetails
import com.alexmprog.cryptocoins.domain.coins.repository.CoinsRepository

class GetCoinDetailsUseCase(private val coinsRepository: CoinsRepository) {

    suspend operator fun invoke(id: String): Resource<CoinDetails, Error> =
        coinsRepository.getCoinDetails(id)
}