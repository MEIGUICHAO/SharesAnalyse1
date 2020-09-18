package com.mgc.sharesanalyse.base

import com.mgc.sharesanalyse.utils.BigDecimalUtils

fun String.toSinaCode(): String {
    return if (this.toInt() > 600000) "sh$this" else "sz$this"
}

fun Double.getPercent(begin: Double) {
    BigDecimalUtils.div(BigDecimalUtils.sub(this,begin),begin)*100
}