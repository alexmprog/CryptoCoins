package com.alexmprog.cryptocoins.feature.coinlist.impl.di

import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListComponent
import com.alexmprog.cryptocoins.feature.coinlist.impl.CoinListComponentInternalImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val coinListFeatureModule
    get() = DI.Module("coinListFeatureModule") {
        bindSingleton<CoinListComponent.Factory> { CoinListComponentInternalImpl.Factory(instance()) }
    }