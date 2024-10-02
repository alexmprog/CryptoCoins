package com.alexmprog.common.utils.format

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToInt

private const val BILLION: Long = 1000000000L

fun Double.toFormattedString(precision: Int = 2): String =
    if (precision < 1) {
        "${this.roundToInt()}"
    } else {
        val p = 10.0.pow(precision)
        val v = (abs(this) * p).roundToInt()
        val i = floor(v / p)
        var f = "${floor(v - (i * p)).toInt()}"
        while (f.length < precision) f = "0$f"
        val s = if (this < 0) "-" else ""
        "$s${i.toInt()}.$f"
    }

fun Long?.toFormattedString(): String =
    if (this == null) ""
    else {
        val format = div(BILLION)
        if (format >= 1) "$${format}B"
        else "$${format}M"
    }
