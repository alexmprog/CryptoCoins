package com.alexmprog.cryptocoins.data.coins.di

import com.alexmprog.cryptocoins.common.dispatchers.CommonIoDispatcher
import com.alexmprog.cryptocoins.data.coins.network.CoinService
import com.alexmprog.cryptocoins.data.coins.network.CoinServiceImpl
import com.alexmprog.cryptocoins.data.coins.repository.CoinsRepositoryImpl
import com.alexmprog.cryptocoins.domain.coins.repository.CoinsRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val coinsDataModule
    get() = DI.Module("coinsDataModule") {
        bind<CoinService>() with provider { CoinServiceImpl(instance()) }
        bind<CoinsRepository>() with provider {
            CoinsRepositoryImpl(instance(), instance(CommonIoDispatcher))
        }
    }