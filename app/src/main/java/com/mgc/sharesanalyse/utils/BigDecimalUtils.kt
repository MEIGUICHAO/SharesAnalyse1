package com.mgc.sharesanalyse.utils

import java.math.BigDecimal

object BigDecimalUtils {


    // 需要精确至小数点后几位
    const val DECIMAL_POINT_NUMBER:Int = 3

    // 加法运算
    fun add(d1:Double,d2:Double):Double = BigDecimal(d1).add(BigDecimal(d2)).setScale(DECIMAL_POINT_NUMBER,BigDecimal.ROUND_DOWN).toDouble()

    // 减法运算
    fun sub(d1:Double,d2: Double):Double = BigDecimal(d1).subtract(BigDecimal(d2)).setScale(DECIMAL_POINT_NUMBER,BigDecimal.ROUND_DOWN).toDouble()

    // 乘法运算
    fun mul(d1:Double,d2: Double,decimalPoint:Int):Double = BigDecimal(d1).multiply(BigDecimal(d2)).setScale(decimalPoint,BigDecimal.ROUND_DOWN).toDouble()

    // 除法运算
    fun div(d1:Double,d2: Double):Double = BigDecimal(d1).divide(BigDecimal(d2),DECIMAL_POINT_NUMBER,BigDecimal.ROUND_HALF_UP).toDouble()

}