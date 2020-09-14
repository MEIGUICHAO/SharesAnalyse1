package com.mgc.sharesanalyse.viewmodel

import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.entity.PricesHisGDBean
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
        var bean = DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.queryById(code.toLong())

        if (null != bean) {
            val today = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
            LogUtil.d("today:$today bean.date:${bean.date} ${today != bean.date}")
            if (today != bean.date) {
                requestHisPriceHq(false,start, end, code)
            } else {
                LogUtil.d("cache_${Datas.hisHqUrl}--------$code")
                loadState.value = LoadState.Success(REQUEST_HIS_HQ, bean.json)
            }
        } else {
            requestHisPriceHq(true,start, end, code)
        }


    }

    private fun requestHisPriceHq(isInsert:Boolean,start: String, end: String, code: String) {
        var result: Deferred<String>
        if (start.isEmpty() && end.isEmpty()) {
            result = RetrofitManager.reqApi.getHisHq("cn_$code")
        } else {
            result = RetrofitManager.reqApi.getHisHq("cn_$code", start, end)
        }

        launch({
            var json = result.await()
            var bean = PricesHisGDBean()
            bean.code = code
            if (isInsert) {
                bean.id = code.toLong()
            }
            bean.date =  DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
            bean.json = json
            var dbName = DaoManager.getDbName()
            if (isInsert) {
                DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.insert(bean)
            } else {
                DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.update(bean)
            }

            loadState.value = LoadState.Success(REQUEST_HIS_HQ, json)
        })
    }

    //    0日期	1开盘	2收盘	3涨跌额	4涨跌幅	5最低	6最高	7成交量(手)	8成交金额(万)	9换手率
    fun getHisHqAnalyseResult(
        hqList: List<List<String>>,
        code: String
    ) {
//        val baseDays = hqList.size/2
        val baseDays = 10
        var baseDealAmount = 0.toDouble()
        var baseDealPercent = 0.toDouble()
        for (index in (hqList.size - 1) downTo baseDays) {
            baseDealAmount = baseDealAmount + getHisHqDayDealAmount(hqList[index])
            baseDealPercent = baseDealPercent + getHisHqDayDealPercent(hqList[index])
        }
        val baseAvgDealAmount = BigDecimalUtils.div(baseDealAmount, (hqList.size-baseDays).toDouble())
        val baseAvgDealPercent = BigDecimalUtils.div(baseDealPercent, (hqList.size-baseDays).toDouble())
        val basePrices = getHisHqDayClosePrice(hqList[baseDays])
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
            LogUtil.d("getHisHqAnalyseResult-------code:$code \n dealTimes:$dealTimes \n  percentTimes:$percentTimes  \n baseAvgDealAmount:$baseAvgDealAmount" +
                    "\n currentAmount:${getHisHqDayDealAmount(hqList[index])} \ncurrentDealPercent:${getHisHqDayDealPercent(hqList[index])} \nbaseAvgDealPercent:$baseAvgDealPercent")
            val addStr =
                "day:${getHisHqDay(hqList[index])}---dealTimes:$dealTimes---percentTimes:$percentTimes---colsePrices:${getHisHqDayClosePrice(
                    hqList[index]
                )}---difPrices:${diffPrices}---percent:${BigDecimalUtils.div(
                    diffPrices,
                    basePrices
                ) * 100}%"
            logStr = logStr.putTogetherAndChangeLineLogic(addStr)
        }
        if (needLog) {
            LogUtil.d("------needLog---\n$logStr")
            FileLogUtil.d("${parentBasePath}hishq$pathDate",logStr)
        }
        loadState.value = LoadState.GoNext(REQUEST_HIS_HQ)

    }

    fun getHisHqDay(dayDatas: List<String>) = dayDatas[0]
    fun getHisHqDayClosePrice(dayDatas: List<String>) = dayDatas[2].toDouble()
    fun getHisHqDayDealAmount(dayDatas: List<String>) = dayDatas[8].toDouble()
    fun getHisHqDayDealPercent(dayDatas: List<String>) = if (dayDatas[9]=="-") 0.toDouble() else dayDatas[9].replace("%", "").toDouble()
    fun setFilelogPath(formatToDay: String) {
        pathDate = formatToDay
    }
}