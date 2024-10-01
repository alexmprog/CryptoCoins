package com.alexmprog.common.logger

object Logger {

    private var isEnabled: Boolean = true

    fun init(isEnabled: Boolean = true) {
        Logger.isEnabled = isEnabled
    }

    fun log(tag: String, message: String) {
        if (!isEnabled) return
        platformLog(tag = tag, message = message)
    }
}
