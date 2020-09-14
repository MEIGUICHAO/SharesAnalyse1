package com.mgc.sharesanalyse.viewmodel

import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import kotlinx.coroutines.Deferred

class NewApiViewModel : BaseViewModel() {

    var parentBasePath = DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYY_MM)+"/newapi/"

    var pathDate = ""

    fun getDealDetail(code: String, date: String) {
        var result = RetrofitManager.reqApi.getDealDetai(code, date)
        launch({
            var json = result.await()
            loadState.value = LoadState.Success(REQUEST_DealDETAIL, json)
        })

    }

    fun getPricehis(code: String, startdate: String, endDate: String) {
        var result = RetrofitManager.reqApi.getPricehis(code, startdate, endDate)
        launch({
            var json = result.await()
            loadState.value = LoadState.Success(REQUEST_PRICESHIS, json)
        })

    }


    fun getHisHq(code: String, start: String = "", end: String = "") {
        var result: Deferred<String>
        if (start.isEmpty() && end.isEmpty()) {
            result = RetrofitManager.reqApi.getHisHq("cn_$code")
        } else {
            result = RetrofitManager.reqApi.getHisHq("cn_$code", start, end)
        }
        launch({
            var json = result.await()
            loadState.value = LoadState.Success(REQUEST_HIS_HQ, json)
        })

    }

    //    0日期	1开盘	2收盘	3涨跌额	4涨跌幅	5最低	6最高	7成交量(手)	8成交金额(万)	9换手率
    fun getHisHqAnalyseResult(
        hqList: List<List<String>>,
        code: String
    ) {
//        val baseDays = hqList.size/2
        val baseDays = 5
        var baseDealAmount = 0.toDouble()
        var baseDealPercent = 0.toDouble()
        for (index in (hqList.size - 1) downTo baseDays) {
            baseDealAmount = baseDealAmount + getHisHqDayDealAmount(hqList[index])
            baseDealPercent = baseDealPercent + getHisHqDayDealPercent(hqList[index])
            LogUtil.d("getHisHqAnalyseResult downTo baseDays $index:------- baseDealAmount:$baseDealAmount  baseDealPercent:$baseDealPercent ")
        }
        val baseAvgDealAmount = BigDecimalUtils.div(baseDealAmount, baseDays.toDouble())
        val baseAvgDealPercent = BigDecimalUtils.div(baseDealPercent, baseDays.toDouble())
        val basePrices = getHisHqDayClosePrice(hqList[baseDays])
        LogUtil.d("baseDays:$baseDays")
        LogUtil.d("basePrices:$basePrices")
        LogUtil.d("baseAvgDealAmount:$baseAvgDealAmount")
        LogUtil.d("baseAvgDealPercent:$baseAvgDealPercent")
        var needLog = false
        var logStr = "========$code============"
        for (index in 0 until baseDays) {
            val dealTimes =
                BigDecimalUtils.div(getHisHqDayDealAmount(hqList[index]), baseAvgDealAmount)
            val percentTimes =
                BigDecimalUtils.div(getHisHqDayDealPercent(hqList[index]), baseAvgDealPercent)
            val diffPrices = BigDecimalUtils.sub(
                getHisHqDayClosePrice(hqList[index]),
                basePrices
            )
            if (!needLog) {
                needLog = dealTimes >= 5 && percentTimes >= 5
            }
            val addStr =
                "day:${getHisHqDay(hqList[index])}---dealTimes:$dealTimes---percentTimes:$percentTimes---colsePrices:${getHisHqDayClosePrice(
                    hqList[index]
                )}---difPrices${diffPrices}---percent:${BigDecimalUtils.div(
                    diffPrices,
                    basePrices
                ) * 100}%"
            logStr = logStr.putTogetherAndChangeLineLogic(addStr)
        }
        if (needLog) {
            FileLogUtil.d("${parentBasePath}hishq$pathDate",logStr)
        }

    }

    fun getHisHqDay(dayDatas: List<String>) = dayDatas[0]
    fun getHisHqDayClosePrice(dayDatas: List<String>) = dayDatas[2].toDouble()
    fun getHisHqDayDealAmount(dayDatas: List<String>) = dayDatas[8].toDouble()
    fun getHisHqDayDealPercent(dayDatas: List<String>) = dayDatas[9].replace("%", "").toDouble()
    fun setFilelogPath(formatToDay: String) {
        pathDate = formatToDay
    }
}