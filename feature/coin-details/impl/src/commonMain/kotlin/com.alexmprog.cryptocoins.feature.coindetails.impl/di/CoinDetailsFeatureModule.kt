package com.alexmprog.cryptocoins.feature.coindetails.impl.di

import com.alexmprog.cryptocoins.common.dispatchers.CommonDefaultDispatcher
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsComponent
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsContent
import com.alexmprog.cryptocoins.feature.coindetails.impl.DefaultCoinDetailsComponent
import com.alexmprog.cryptocoins.feature.coindetails.impl.DefaultCoinDetailsContent
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val coinDetailsFeatureModule
    get() = DI.Module("coinDetailsFeatureModule") {
        bindSingleton<CoinDetailsComponent.Factory> {
            DefaultCoinDetailsComponent.Factory(
                instance(),
                instance(),
                instance(CommonDefaultDispatcher)
            )
        }
        bindSingleton<CoinDetailsContent> { DefaultCoinDetailsContent() }
    }