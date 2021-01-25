package com.mgc.sharesanalyse.base

import android.annotation.SuppressLint
import com.mgc.sharesanalyse.entity.*
import com.mgc.sharesanalyse.utils.*
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

fun List<ReasoningRevBean>.sortReasoningRevBeanByP() {
    Collections.sort(this, object : Comparator<ReasoningRevBean> {
        override fun compare(p0: ReasoningRevBean, p1: ReasoningRevBean): Int {
            return p1.p.compareTo(p0.p)
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

fun ArrayList<String>.sortIntSlilitDesc() {
    Collections.sort(this, object : Comparator<String> {
        override fun compare(p0: String, p1: String): Int {
            return p1.split("###")[1].compareTo( p0.split("###")[1])
        }
    })
}

fun ArrayList<Float>.sortFloatAsc() {
    Collections.sort(this, object : Comparator<Float> {
        override fun compare(p0: Float, p1: Float): Int {
            return p0.compareTo(p1)
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

fun ArrayList<CodeHDDBean>.sortAscByDate() {
    Collections.sort(this, object : Comparator<CodeHDDBean> {
        override fun compare(p0: CodeHDDBean, p1: CodeHDDBean): Int {
            return p1.date.toInt().compareTo(p0.date.toInt())
        }
    })
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

fun ArrayList<ReasoningRevBean>.sortDescReasoningByDate(){
    Collections.sort(this, object : Comparator<ReasoningRevBean> {
        override fun compare(p0: ReasoningRevBean, p1: ReasoningRevBean): Int {
            return p1.d.toInt().compareTo(
                p0.d.toInt()
            )
        }
    })
}

fun String.toWYCode(): String {
    if (this.toInt() > 600000) {
        return "0$this"
    } else {
        return "1${this.toCompleteCode()}"
    }
}

fun ArrayList<BaseReverseImp>.getCodeList(): ArrayList<String> {
    val mNextCodeList = ArrayList<String>()
    this.forEach {
        if (it is ReverseKJsonBean) {
            mNextCodeList.add(it.code.toString()+"###"+it.date.toString())
        }
    }
    return mNextCodeList
}

fun ArrayList<String>.getCodeArrayAndLimitSQL(needFirstAnd: Boolean): String {
//    val array = arrayOfNulls<String>(this.size*2)
//    for (i in 0 until this.size ) {
//        array[i*2] = this[i].split("###")[0]
//        array[i*2+1] = "'${ this[i].split("###")[1] }'"
//    }
    var addSql = ""
    this.forEach {
        if (needFirstAnd) {
            addSql =if (addSql.isEmpty()) "AND ( (CODE =${it.split("###")[0]} AND DATE = '${it.split("###")[1]}')" else addSql + " OR (CODE =${it.split("###")[0]} AND DATE = '${it.split("###")[1]}')"
        } else {
            addSql = if (addSql.isEmpty()) "(CODE =${it.split("###")[0]} AND DATE = '${it.split("###")[1]}')"
            else addSql + " OR (CODE =${it.split("###")[0]} AND DATE ='${it.split("###")[1]}')"
        }
    }
    return if (needFirstAnd) addSql+")" else addSql
}


fun ReasoningRevBean.getF_TSql(dT: Int): String {
    val fList = arrayOf("36","30","25","20","15","10","05","03")
    var sql = ""
    fList.forEach {
        if (it.toInt() != dT) {
            sql = sql + " AND F${it}_T = ${when (it.toInt()) {
                36->this.f36_T
                30->this.f30_T
                25->this.f25_T
                20->this.f20_T
                15->this.f15_T
                10->this.f10_T
                5->this.f05_T
                else->this.f03_T
            }}"
        }
    }
    return sql
}

fun ArrayList<BaseReverseImp>.getAllJudgeDerbyList(tbDerbyName: String): ArrayList<BaseReverseImp> {
    val mDerbyCodeList = this.getCodeList()
    val derbyAddstr = mDerbyCodeList.getCodeArrayAndLimitSQL(false)
    val derbyList = DBUtils.getFilterAllByDerbyTbName(
        Datas.REVERSE_KJ_DB,
        "SELECT * FROM $tbDerbyName WHERE $derbyAddstr", null
    )
    return derbyList!!
}

fun Float.toGetReasoningJudgeDFloat(): Float {
    var value = this
    var limit = 0.toFloat()
    if (this > 0.toFloat()) {
        limit = ((this/10).toInt()*10+10).toFloat()

    } else {
         limit = ((this/10).toInt()*10).toFloat()
    }
    if (value + 1 >= limit) {
        value = limit
    } else {
        value = value + 1
    }
    return value
}

fun Float.toGetReasoningJudgeXFloat(): Float {
    var value = this
    var limit = 0.toFloat()
    if (this > 0.toFloat()) {
        limit = ((this/10).toInt()*10).toFloat()
    } else {
        limit = ((this/10).toInt()*10-10).toFloat()
    }
    if (value - 1 <= limit) {
        value = limit
    } else {
        value = value -1
    }
    return value
}

fun Float.toGetSimpleX(): Int {
    return ((this/10).toInt()*10)
}