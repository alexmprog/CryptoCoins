package com.alexmprog.cryptocoins

import android.app.Application
import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.DIAware

class App : Application(), DIAware {

    override val di: DI = DI {
        extend(di = sharedCommonDI, copy = Copy.All)
    }
}