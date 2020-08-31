package com.mgc.sharesanalyse.viewmodel

import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgc.sharesanalyse.utils.BigDecimalUtils
import com.mgc.sharesanalyse.utils.DateUtils
import com.mgc.sharesanalyse.utils.FormatterEnum
import com.mgc.sharesanalyse.utils.LogUtil
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

fun Double.getPercent(start: Double): Double {
    if (start > 0.toDouble()) {
        return BigDecimalUtils.mul(
            BigDecimalUtils.div(BigDecimalUtils.sub(this, start), start),
            100.toDouble(),
            2
        )
    }
    return 0.toDouble()
}

fun String.logAlongSumtoTimeStamp(): Long {
    return DateUtils.parse(
        this.replace("logAloneSum_", "").replace(".txt", "").replace("_", ":"),
        FormatterEnum.YYYYMMDD__HH_MM_SS
    )
}

fun String.logAlongSumtoTimeStampYMD(): Long {
    var date = this.replace("logAloneSum_", "").replace(".txt", "").replace("_", ":")
    return DateUtils.parse(date, FormatterEnum.YYYYMMDD__HH_MM_SS)
}


fun String.toTimeStampYMD(): Long {
    return DateUtils.parse(this, FormatterEnum.YYYY_MM_DD)
}

fun String.toClassifyPath(): String {
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
    ) + "/" + path + "/" +
            DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYY_MM_DD) + "logAlone")
}