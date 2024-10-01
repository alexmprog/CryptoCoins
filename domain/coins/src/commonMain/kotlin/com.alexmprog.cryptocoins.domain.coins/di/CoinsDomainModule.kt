package com.alexmprog.cryptocoins.domain.coins.di

import com.alexmprog.cryptocoins.domain.coins.usecase.GetCoinChartUseCase
import com.alexmprog.cryptocoins.domain.coins.usecase.GetCoinDetailsUseCase
import com.alexmprog.cryptocoins.domain.coins.usecase.GetCoinsUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val coinsDomainModule
    get() = DI.Module("coinsDomainModule") {
        bind<GetCoinsUseCase>() with provider { GetCoinsUseCase(instance()) }
        bind<GetCoinDetailsUseCase>() with provider { GetCoinDetailsUseCase(instance()) }
        bind<GetCoinChartUseCase>() with provider { GetCoinChartUseCase(instance()) }
    }