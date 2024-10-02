package com.alexmprog.cryptocoins

import com.alexmprog.cryptocoins.common.dispatchers.dispatchersModule
import com.alexmprog.cryptocoins.data.coins.di.coinsDataModule
import com.alexmprog.cryptocoins.domain.coins.di.coinsDomainModule
import com.alexmprog.cryptocoins.feature.coindetails.impl.di.coinDetailsFeatureModule
import com.alexmprog.cryptocoins.feature.coinlist.impl.di.coinListFeatureModule
import com.alexmprog.cryptocoins.feature.home.impl.di.homeFeatureModule
import com.alexmprog.cryptocoins.core.network.networkModule
import org.kodein.di.DI

val sharedCommonDI = DI {
    // common
    import(dispatchersModule)
    // core
    import(networkModule)
    // data
    import(coinsDataModule)
    // domain
    import(coinsDomainModule)
    // feature
    import(coinDetailsFeatureModule)
    import(coinListFeatureModule)
    import(homeFeatureModule)
}