package com.mgc.sharesanalyse.base

import com.mgc.sharesanalyse.utils.BigDecimalUtils
import com.mgc.sharesanalyse.utils.GsonHelper
import java.util.ArrayList

fun String.toSinaCode(): String {
    return if (this.toInt() >= 600000) "sh$this" else "sz$this"
}

fun Double.getPercent(begin: Double) {
    BigDecimalUtils.div(BigDecimalUtils.sub(this,begin),begin)*100
}

fun <T> String.json2Array(cls: Class<T>?): ArrayList<T>? {
    return GsonHelper.parseArray(this, cls)
}

fun <T> ArrayList<T>.array2Json(): String {
    return GsonHelper.toJson(this)
}