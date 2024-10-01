package com.alexmprog.common.utils.format

actual fun Double.toFormattedString(format: String, minDigit: Int, maxDigit: Int): String =
    try {
        toString()
    } catch (e: IllegalArgumentException) {
        toString()
    }