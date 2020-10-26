package com.mgc.sharesanalyse.viewmodel

import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.utils.BigDecimalUtils
import com.mgc.sharesanalyse.utils.DateUtils
import com.mgc.sharesanalyse.utils.FormatterEnum
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: (e: Throwable) -> Unit = {},
    onComplete: () -> Unit = {}
) {
    viewModelScope.launch(CoroutineExceptionHandler { _, e -> onError(e) }) {
        try {
            block.invoke(this)
        } finally {
            onComplete()
        }
    }
}

fun String.toDiv100(): String {
    return (this.toDouble() / 100.0f).toString()
}


fun String.toDiv10000(): String {
    return (this.toDouble() / 10000.0f).toString()
}

fun SparseArray<String>.getDBValue(key: Int): String {
    var str = if (this[key].isNullOrEmpty()) "" else this[key]
    return str
}

fun Int.toLog(tag: String): String {
    return if (this > 0) "$tag:${this}" else ""
}

fun String.toLogCompare(): Int {
    return this.substringAfter("s:").split(",")[0].toInt()
}

fun String.toDateCompare(): Long {
    return DateUtils.parse(this.replace("logAlone.txt", ""), FormatterEnum.YYYY_MM_DD)
}

fun String.toShareDBDateCompare(): Long {
    return DateUtils.parse(
        this.replace("sharesDB_", "").replace("sh_", "").replace("szMain_", "")
            .replace("szSmall_", ""), FormatterEnum.YYYY_MM_DD
    )
}

fun String.toLogSumSizeCompare(tag: String): Int {
    return this.split(tag).size
}

fun SparseArray<String>.putLogSum(key: Int, value: String) {
    var mValue = if (this.get(key).isNullOrEmpty()) value else this.get(key) + "\n" + value
    this.put(key, mValue)
}

fun String.putTogetherAndChangeLineLogic(addString: String):String {
    return if (this.isEmpty()) addString else "$this\n$addString"
}


fun String.logAlongSumtoTimeStamp(): Long {
    return DateUtils.parse(
        this.replace("sh_", "").replace("szMS_", "").replace("szSU_", "").replace("logAloneSum_", "").replace(".txt", "").replace("_", ":"),
        FormatterEnum.YYYYMMDD__HH_MM_SS
    )
}

fun String.logAlongSumtoTimeStampYMD(): Long {
    var first = this.replace("sh_", "").replace("szMS_", "").replace("szSU_", "")
    var date = first.replace("logAloneSum_", "").replace(".txt", "").replace("_", ":")
    var end = date.replace("sh:", "").replace("szSU:", "").replace("szMS:", "")
    return DateUtils.parse(end, FormatterEnum.YYYYMMDD__HH_MM_SS)
}


fun String.toTimeStampYMD(): Long {
    return DateUtils.parse(this, FormatterEnum.YYYY_MM_DD)
}

fun String.toClassifyPath(dbName: String): String {
    var code = this.split("---n:")[0].replace("---c:","").toDouble()
    var path = "sh"
    if (code > 300000 && code < 600000) {
        path = "szSU"
    } else if (code < 600000) {
        path = "szMS"
    }
    return (DateUtils.format(
        System.currentTimeMillis(),
        FormatterEnum.YYYY_MM
    ) + "/" + path + "/" +dbName.replace("sharesDB_","")+ "logAlone")
}

fun String.toLogSumPath(): String {
    var code = this.toDouble()
    var path = "sh"
    if (code > 300000 && code < 600000) {
        path = "szSU"
    } else if (code < 600000) {
        path = "szMS"
    }
    return "${DateUtils.format(
        System.currentTimeMillis(),
        FormatterEnum.YYYY_MM
    ) + "/" + path}/logSum/logAloneSum_${DateUtils.format(
        System.currentTimeMillis(),
        FormatterEnum.YYYYMMDD__HH_MM_SS
    )}"
}

fun String.paAdapter():String {
    var beginNum = this.replace(",", "").replace("ms", "").toInt()
    var base = when (beginNum) {
        100 -> 20
        50 -> 10
        20 -> 4
        10 -> 2
        else -> 1
    }
    return ",${base * Datas.limitPerAmount / 100}ms"
}

fun String.paSimpleAdapter():String {
    var beginNum = this.replace("pa>", "").replace("m", "").toInt()
    var base = when (beginNum) {
        100 -> 20
        50 -> 10
        20 -> 4
        10 -> 2
        else -> 1
    }
    return "pa>${base * Datas.limitPerAmount / 100}m"
}