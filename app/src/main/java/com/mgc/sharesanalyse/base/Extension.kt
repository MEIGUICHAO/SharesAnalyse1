package com.mgc.sharesanalyse.base

import android.annotation.SuppressLint
import com.mgc.sharesanalyse.entity.CodeHDDBean
import com.mgc.sharesanalyse.entity.DealDetailTableBean
import com.mgc.sharesanalyse.utils.BigDecimalUtils
import com.mgc.sharesanalyse.utils.DateUtils
import com.mgc.sharesanalyse.utils.FormatterEnum
import com.mgc.sharesanalyse.utils.GsonHelper
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun String.toSinaCode(): String {
    return if (this.toInt() >= 600000) "sh$this" else "sz$this"
}

fun String.toCompleteCode(): String {
    return if (this.toInt() < 300000) kotlin.run {
        var code = this
        while (code.length < 6) {
            code = "0" + code
        }
        code
    } else this
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
fun Double.toKeep4(): Double {
    return java.lang.String.format("%.6f", this).toDouble()
}

fun Float.toKeep2(): Float {
    return java.lang.String.format("%.2f", this).toFloat()
}

fun Float.toKeep4(): Float {
    return java.lang.String.format("%.4f", this).toFloat()
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

fun ArrayList<Float>.sortFloatDateDesc() {
    Collections.sort(this, object : Comparator<Float> {
        override fun compare(p0: Float, p1: Float): Int {
            return p1.compareTo(p0)
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

fun ArrayList<CodeHDDBean>.getRevBeansOM(): Float {
    Collections.sort(this, object : Comparator<CodeHDDBean> {
        override fun compare(p0: CodeHDDBean, p1: CodeHDDBean): Int {
            return p1.p_MA_J.amp.compareTo(p0.p_MA_J.amp)
        }
    })
    return this[0].p_MA_J.amp
}

fun ArrayList<CodeHDDBean>.getRevBeansOL(): Float {
    Collections.sort(this, object : Comparator<CodeHDDBean> {
        override fun compare(p0: CodeHDDBean, p1: CodeHDDBean): Int {
            return p0.p_MA_J.alp.compareTo(p1.p_MA_J.alp)
        }
    })
    return this[0].p_MA_J.alp
}

fun ArrayList<String>.getMADesc(): Int {
    Collections.sort(this, object : Comparator<String> {
        override fun compare(p0: String, p1: String): Int {
            return p1.split("_")[1].compareTo(p0.split("_")[1])
        }
    })
    var maArray = ""
    this.forEach {
        maArray = maArray+ it.split("_")[0]
    }
    return maArray.toInt()
}

fun String.getFilterSameNameSQL():String {
    return "select * from $this where _ID in (select min(_ID) from $this where 1 = 1 group by N) "
}


fun String.getQuerySql(smallerMap:HashMap<String,String>, biggerMap:HashMap<String,String>):Pair<String,Array<String?>> {
    var querySql = ""
    val valueArray = arrayOfNulls<String>(smallerMap.size + biggerMap.size)
    var index = 0
    smallerMap.forEach {
        querySql = querySql + " AND ${it.key}<=?"
        valueArray.set(index,it.value)
        index++
    }
    biggerMap.forEach {
        querySql = querySql + " AND ${it.key}>?"
        valueArray.set(index,it.value)
        index++
    }
    return Pair(this.getFilterSameNameSQL()+querySql, valueArray)
}

fun String.copyFilterTB2NewTB(oldTB: String,smallerMap:HashMap<String,String>, biggerMap:HashMap<String,String>): String {
    var querySql = ""
    smallerMap.forEach {
        querySql = querySql + " AND ${it.key} <= ${it.value}"
    }
    biggerMap.forEach {
        querySql = querySql + " AND ${it.key} > ${it.value}"
    }
    return "CREATE TABLE $this AS (SELECT COLUMNS FROM $oldTB  WHERE _ID IN (SELECT MIN(_ID) FROM $oldTB WHERE 1 = 1 GROUP BY N) $querySql)"
}


