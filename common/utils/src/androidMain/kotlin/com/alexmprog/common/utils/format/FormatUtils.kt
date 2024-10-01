package com.alexmprog.common.utils.format

import java.text.DecimalFormat

actual fun Double.toFormattedString(format: String, minDigit: Int, maxDigit: Int): String =
    try {
        DecimalFormat(format).apply {
            minimumIntegerDigits = 1
            minimumFractionDigits = minDigit
            maximumFractionDigits = maxDigit
        }.format(this)
    } catch (e: IllegalArgumentException) {
        toString()
    }