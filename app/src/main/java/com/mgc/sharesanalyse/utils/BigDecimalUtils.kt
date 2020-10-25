package com.mgc.sharesanalyse.utils

import com.mgc.sharesanalyse.base.Datas
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

object BigDecimalUtils {


    // 需要精确至小数点后几位
    const val DECIMAL_POINT_NUMBER:Int = 4

    // 加法运算
    fun add(d1:Double,d2:Double):Double = d1+d2

    // 减法运算
    fun sub(d1:Double,d2: Double):Double = BigDecimal(d1).subtract(BigDecimal(d2)).setScale(2,BigDecimal.ROUND_HALF_UP).toDouble()

    // 乘法运算
    fun mul(d1:Double,d2: Double,decimalPoint:Int):Double = BigDecimal(d1).multiply(BigDecimal(d2)).setScale(decimalPoint,BigDecimal.ROUND_DOWN).toDouble()

    // 除法运算
    fun div(d1:Double,d2: Double):Double = kotlin.run {
        val result = BigDecimal(d1).divide(BigDecimal(d2) ,4, BigDecimal.ROUND_HALF_UP).setScale(DECIMAL_POINT_NUMBER, BigDecimal.ROUND_HALF_UP)
//        String.format("%.4f",result).toDouble()
        result.setScale(4, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    // 除法运算
    fun safeDiv(d1:Double,d2: Double):Double = kotlin.run {
        val result = if (d2>0) BigDecimal(d1).divide(BigDecimal(d2),4, BigDecimal.ROUND_HALF_UP).toDouble() else 0.toDouble()
        val nf: NumberFormat = NumberFormat.getNumberInstance()
        nf.setMaximumFractionDigits(4)
        nf.setRoundingMode(RoundingMode.UP)
        nf.format(result).toDouble()
    }

    fun getPPBySafeDiv(d1:Double,d2: Double):Double = kotlin.run {
        val result = if (d2>0) BigDecimal(d1* Datas.NUM_100M).divide(BigDecimal(d2*Datas.NUM_WAN) ,4, BigDecimal.ROUND_HALF_UP).setScale(DECIMAL_POINT_NUMBER, BigDecimal.ROUND_HALF_UP).toDouble() else 0.toDouble()
        String.format("%.2f",result).toDouble()
    }

}