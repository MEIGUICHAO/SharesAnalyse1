package com.mgc.sharesanalyse.base

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.mgc.sharesanalyse.entity.DealDetailAmountSizeBean
import com.mgc.sharesanalyse.entity.DealDetailBean
import com.mgc.sharesanalyse.utils.BigDecimalUtils
import com.mgc.sharesanalyse.utils.GsonHelper
import java.util.ArrayList

fun String.toSinaCode(): String {
    return if (this.toInt() >= 600000) "sh$this" else "sz$this"
}

fun Double.getPercent(begin: Double) {
    BigDecimalUtils.div(BigDecimalUtils.sub(this,begin),begin)*100
}

fun String.json2Array(clazz: Class<Any>): ArrayList<out Class<Any>>? {
    return GsonHelper.parseArray(this,clazz::class.java)
}