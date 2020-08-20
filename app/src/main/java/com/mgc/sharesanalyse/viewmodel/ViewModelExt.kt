package com.mgc.sharesanalyse.viewmodel

import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

fun String.toDiv100():String{
    return (this.toDouble()/100.0f).toString()
}


fun String.toDiv10000():String{
    return (this.toDouble()/10000.0f).toString()
}

fun SparseArray<String>.getDBValue(key: Int): String {
    var str = if(this[key].isNullOrEmpty())  "" else  this[key]
    return str
}

fun Int.toLog(tag:String):String {
    return if (this>0) "$tag:${this}"  else ""
}

fun String.toLogCompare():Int {
    return this.substringAfter("s:").split(",")[0].toInt()
}

fun String.toDateCompare():Long {
    return DateUtils.parse(this.replace("logAlone.txt",""),FormatterEnum.YYYYMMDD)
}

fun String.toShareDBDateCompare():Long {
    return DateUtils.parse(this.replace("sharesDB_",""),FormatterEnum.YYYY_MM_DD)
}

fun SparseArray<String>.putLogSum(key: Int,value:String) {
   var mValue =  if (this.get(key).isNullOrEmpty()) value else this.get(key) + "\n" + value
    this.put(key, mValue)

}