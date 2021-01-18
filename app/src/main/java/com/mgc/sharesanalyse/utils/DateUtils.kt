@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.mgc.sharesanalyse.utils

import android.annotation.SuppressLint
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.base.sortStringDateAsc
import com.mgc.sharesanalyse.base.toCodeHDD
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


enum class FormatterEnum(val format: String) {
    YYYY_MM_DD("yyyy-MM-dd"),
    YYYY_MM("yyyy-MM"),
    YYYYMMDD("yyyyMMdd"),
    MMDD("MMdd"),
    YYYY("yyyy"),
    YYMM("yyMM"),
    YY("yy"),
    DD("dd"),
    YYYYMM("yyyyMM"),
    YYYY_MM_DD__HH_MM("yyyy-MM-dd HH:mm"),
    YYYY_MM_DD__HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    YYYYMMDD__HH_MM("yyyyMMdd HH:mm"),
    YYYYMMDD__HH_MM_SS("yyyyMMdd HH:mm:ss"),
    HH_MM_SS("HH:mm:ss")
}

object DateUtils {
    val calendar = Calendar.getInstance()
    val holiday = arrayListOf("2020-05-01","2020-05-02","2020-05-03","2020-05-04","2020-05-05","2020-06-25","2020-06-26","2020-06-27","2020-10-01","2020-10-02","2020-10-03","2020-10-04","2020-10-05","2020-10-06"
        ,"2020-10-07","2020-10-08")

    @SuppressLint("SimpleDateFormat")
    fun parse(dateStr: String, formatter: FormatterEnum): Long {
        val simpleDateFormat = SimpleDateFormat(formatter.format)
        val date: Date = simpleDateFormat.parse(dateStr)
        val ts = date.time
        return  ts
    }

    @SuppressLint("SimpleDateFormat")
    fun format(timestamp: Long, formatter: FormatterEnum): String {
        val simpleDateFormat = SimpleDateFormat(formatter.format)
        val date = Date(timestamp)
        return simpleDateFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatToDay(formatter: FormatterEnum): String {
        val simpleDateFormat = SimpleDateFormat(formatter.format)
        val date = Date(System.currentTimeMillis())
        return simpleDateFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun changeFormatter(timestamp: Long,formatter: FormatterEnum): String {
        val simpleDateFormat = SimpleDateFormat(formatter.format)
        val date = Date(timestamp)
        return simpleDateFormat.format(date)
    }

    fun changeFromDefaultFormatter(date: String,formatter: FormatterEnum): String {
        val timestamp = parse(date,FormatterEnum.YYYYMMDD)
        val simpleDateFormat = SimpleDateFormat(formatter.format)
        val date = Date(timestamp)
        return simpleDateFormat.format(date)
    }


    @SuppressLint("SimpleDateFormat")
    fun formatYesterDay(formatter: FormatterEnum): String {
        val simpleDateFormat = SimpleDateFormat(formatter.format)
        val date = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
        return simpleDateFormat.format(date)
    }

    fun ifAfterToday1530():Boolean {
        var date = "${formatToDay(FormatterEnum.YYYYMMDD)} 15:02:00"
        return System.currentTimeMillis() > parse(date, FormatterEnum.YYYYMMDD__HH_MM_SS)
    }


    @SuppressLint("SimpleDateFormat")
    fun formatYesterDayTimeStamp(): Long {
        val date = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
        return date.time
    }

    @SuppressLint("SimpleDateFormat")
    fun isWeekDay(timestamp: Long):Pair<Boolean,String> {
        val simpleDateFormat = SimpleDateFormat(FormatterEnum.YYYY_MM_DD.format)
        val date = format(timestamp,FormatterEnum.YYYY_MM_DD)
        calendar.time = simpleDateFormat.parse(date)
        LogUtil.d("isWeekDay date:$date")
        LogUtil.d("isWeekDay calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY:${calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY}")
        LogUtil.d("isWeekDay calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY:${calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY}")
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            var isWorkDay = true
            holiday.forEach {
                if (date == it) {
                    isWorkDay = false
                }
            }
            return Pair(isWorkDay, date)
        } else {
            return Pair(false, date)
        }

    }



}