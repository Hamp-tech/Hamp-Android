package com.hamp.extensions

import java.math.BigDecimal

fun Double.round() = BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
