package com.mgc.sharesanalyse.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*




enum class FormatterEnum(val format: String) {
    YYYY_MM_DD("yyyy-MM-dd"),
    YYYY_MM("yyyy-MM"),
    YYYYMMDD("yyyyMMdd"),
    YYYY_MM_DD__HH_MM("yyyy-MM-dd HH:mm"),
    YYYY_MM_DD__HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    YYYYMMDD__HH_MM("yyyyMMdd HH:mm"),
    YYYYMMDD__HH_MM_SS("yyyyMMdd HH:mm:ss"),
    HH_MM_SS("HH:mm:ss")
}

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    fun parse(dateStr: String, formatter: FormatterEnum): Long {
        val simpleDateFormat = SimpleDateFormat(formatter.format)
        val date: Date = simpleDateFormat.parse(dateStr)
        val ts = date.time
        return  ts
    }

    fun format(timestamp: Long, formatter: FormatterEnum): String {
        val simpleDateFormat = SimpleDateFormat(formatter.format)
        val date = Date(timestamp)
        return simpleDateFormat.format(date)
    }

    fun formatToDay(formatter: FormatterEnum): String {
        val simpleDateFormat = SimpleDateFormat(formatter.format)
        val date = Date(System.currentTimeMillis())
        return simpleDateFormat.format(date)
    }


}