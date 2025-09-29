package com.alexmprog.cryptocoins.feature.coindetails.impl.di

import com.alexmprog.cryptocoins.common.dispatchers.CommonDefaultDispatcher
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsComponent
import com.alexmprog.cryptocoins.feature.coindetails.impl.CoinDetailsComponentInternalImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val coinDetailsFeatureModule
    get() = DI.Module("coinDetailsFeatureModule") {
        bindSingleton<CoinDetailsComponent.Factory> {
            CoinDetailsComponentInternalImpl.Factory(
                instance(),
                instance(),
                instance(CommonDefaultDispatcher)
            )
        }
    }