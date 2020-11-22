package com.mgc.sharesanalyse.base

import android.annotation.SuppressLint
import com.mgc.sharesanalyse.entity.DealDetailTableBean
import com.mgc.sharesanalyse.utils.BigDecimalUtils
import com.mgc.sharesanalyse.utils.DateUtils
import com.mgc.sharesanalyse.utils.FormatterEnum
import com.mgc.sharesanalyse.utils.GsonHelper
import java.util.*
import kotlin.collections.ArrayList

fun String.toSinaCode(): String {
    return if (this.toInt() >= 600000) "sh$this" else "sz$this"
}



fun Float.getPercent(begin: Float):Float {
    return BigDecimalUtils.div(BigDecimalUtils.sub(this, begin), begin) * 100f
}

fun <T> String.json2Array(cls: Class<T>?): ArrayList<T>? {
    return GsonHelper.parseArray(this, cls)
}

fun <T> ArrayList<T>.array2Json(): String {
    return GsonHelper.toJson(this)
}

fun List<DealDetailTableBean>.sortDDBeanDescByAllsize() {
    Collections.sort(this, object : Comparator<DealDetailTableBean> {
        override fun compare(p0: DealDetailTableBean, p1: DealDetailTableBean): Int {
            return p1.allsize.compareTo(p0.allsize)
        }
    })
}


fun ArrayList<String>.getWeekDayS(time: Long, requestSize: Int) {
    var pair = DateUtils.isWeekDay(time)
    if (pair.first) {
        this.add(pair.second)
        if (this.size < requestSize) {
            this.getWeekDayS(time - 24 * 60 * 60 * 1000, requestSize)
        }
    } else if (this.size < requestSize) {
        this.getWeekDayS(time - 24 * 60 * 60 * 1000, requestSize)
    }
}


@SuppressLint("DefaultLocale")
fun Double.toKeep6(): Double {
    return java.lang.String.format("%.6f", this).toDouble()
}

fun String.toCodeHDD(date:String,formatterEnum: FormatterEnum): String {
    if (this.toInt() > 600000) {
        return "SH_CHDD_" + DateUtils.changeFormatter(DateUtils.parse(date,formatterEnum),FormatterEnum.YYMM)
    } else if (this.toInt() < 300000) {
        return "SZ_CHDD_" + DateUtils.changeFormatter(DateUtils.parse(date,formatterEnum),FormatterEnum.YYMM)
    } else {
        return "CY_CHDD_" + DateUtils.changeFormatter(DateUtils.parse(date,formatterEnum),FormatterEnum.YYMM)
    }
}



fun ArrayList<String>.sortStringDateAsc(formatterEnum: FormatterEnum) {
    Collections.sort(this, object : Comparator<String> {
        override fun compare(p0: String, p1: String): Int {
            return DateUtils.parse(
                p0,
                formatterEnum
            ).compareTo(
                DateUtils.parse(
                    p1,
                    formatterEnum
                )
            )
        }
    })
}


fun String.CHDD2YYMM():String {
    return this.replace("CY_CHDD_", "").replace("SH_CHDD_", "").replace("SZ_CHDD_", "")
}

fun String.percent2Float():Float {
    try {
        return this.replace("%", "").toFloat()
    } catch (e: Exception) {
        return 0.toFloat()
    }
}