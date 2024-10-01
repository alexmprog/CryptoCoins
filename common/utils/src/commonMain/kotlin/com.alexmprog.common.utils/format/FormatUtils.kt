package com.alexmprog.common.utils.format

expect fun Double.toFormattedString(
    format: String = "#,###",
    minDigit: Int = 0,
    maxDigit: Int = 2
): String