package com.mgc.sharesanalyse.viewmodel

import android.text.TextUtils
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.entity.*
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList

class NewApiViewModel : BaseViewModel() {

    val debug = true

    var parentBasePath =
        DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYY_MM) + "/newapi/"

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
        var bean =
            DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.queryById(code.toLong())


        if (null != bean) {
            val today = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
            LogUtil.d("today:$today bean.date:${bean.date} ${today != bean.date}")
            if (today != bean.date || debug) {
                requestHisPriceHq(false, start, end, code)
            } else {
                LogUtil.d("cache_${Datas.hisHqUrl}--------$code")
                loadState.value = LoadState.Success(REQUEST_HIS_HQ, bean.json)
            }
        } else {
            requestHisPriceHq(true, start, end, code)
        }


    }

    private fun jsoupParseMineInfoHtml(json: String): ArrayList<InfoDateBean> {
        LogUtil.d("jsoupParseMineInfoHtml")
        val document = Jsoup.parse(json)
        val tagmains = document.body().getElementById("main")
            .getElementById("main").getElementById("center").getElementsByClass("tagmain")
        val title_cls = tagmains.get(0).getElementsByClass("title_cls")
        val date_cls = tagmains.get(0).getElementsByClass("date_cls")
        val mineInfoList = ArrayList<InfoDateBean>()
        for (index in 0 until title_cls.size) {
            val bean = InfoDateBean()
            bean.date = date_cls[index].text()
            bean.info = title_cls[index].text()
            mineInfoList.add(bean)
        }
        return mineInfoList
    }

    private fun requestHisPriceHq(isInsert: Boolean, start: String, end: String, code: String) {

        val xq1 = RetrofitManager.reqApi.getXQInfo(code, "1")
        val xq2 = RetrofitManager.reqApi.getXQInfo(code, "2")
        val xq3 = RetrofitManager.reqApi.getXQInfo(code, "3")
        val mine = RetrofitManager.reqApi.getSinaMineInfo(code)
        val result: Deferred<String>
        if (start.isEmpty() && end.isEmpty()) {
            result = RetrofitManager.reqApi.getHisHq("cn_$code")
        } else {
            result = RetrofitManager.reqApi.getHisHq("cn_$code", start, end)
        }

        launch({
            updateHisPriceInfo(
                result.await(),
                xq1.await(),
                xq2.await(),
                xq3.await(),
                mine.await(),
                code,
                isInsert
            )
        })
    }

    private fun updateHisPriceInfo(
        json: String,
        await1: XQInfoBean,
        await2: XQInfoBean,
        await3: XQInfoBean,
        mine: String,
        code: String,
        isInsert: Boolean
    ) {

        LogUtil.d("updateHisPriceInfo code:$code")
        val mineInfoList = jsoupParseMineInfoHtml(mine)
        val XQInfoList = ArrayList<InfoDateBean>()
        val XQInfoList1 = getXQInfo(await1)
        val XQInfoList2 = getXQInfo(await2)
        val XQInfoList3 = getXQInfo(await3)
        XQInfoList.addAll(XQInfoList1)
        XQInfoList.addAll(XQInfoList2)
        XQInfoList.addAll(XQInfoList3)
        LogUtil.d("getXQInfo!!! XQInfoList size ${XQInfoList.size}")
        val mineInfoJson = GsonHelper.toJson(mineInfoList)
        val xqInfoJson = GsonHelper.toJson(XQInfoList)

        val bean = PricesHisGDBean()
        bean.code = code
        if (isInsert) {
            bean.id = code.toLong()
        }
        bean.date = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
        bean.json = json
        bean.mineInfo = mineInfoJson
        bean.xqInfo = xqInfoJson
        LogUtil.d("xqInfo:\n$xqInfoJson")
        if (isInsert) {
            DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.insert(bean)
        } else {
            DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.update(bean)
        }

        loadState.value = LoadState.Success(REQUEST_HIS_HQ, json)
    }

    private fun getXQInfo(xq1Bean: XQInfoBean): ArrayList<InfoDateBean> {
        val list = ArrayList<InfoDateBean>()
        LogUtil.d("getXQInfo!!! size ${list.size}")
        xq1Bean.list.forEach {
            if (!TextUtils.isEmpty(it.title)) {
                val infoDateBean = InfoDateBean()
                infoDateBean.info = it.title
                infoDateBean.date = DateUtils.format(
                    it.created_at,
                    FormatterEnum.YYYYMMDD__HH_MM_SS
                )
                list.add(infoDateBean)
            }
        }
        LogUtil.d("getXQInfo!!! size ${list.size}")
        return list


    }

    //    0日期	1开盘	2收盘	3涨跌额	4涨跌幅	5最低	6最高	7成交量(手)	8成交金额(万)	9换手率
    fun getHisHqAnalyseResult(
        hqList: List<List<String>>,
        codeSum: String, code: String, stat: List<String>
    ) {
//        val baseDays = hqList.size/2
        val baseDays = 15
        var baseDealAmount = 0.toDouble()
        var baseDealPercent = 0.toDouble()
        for (index in (hqList.size - 1) downTo baseDays) {
            baseDealAmount = baseDealAmount + getHisHqDayDealAmount(hqList[index])
            baseDealPercent = baseDealPercent + getHisHqDayDealPercent(hqList[index])
        }
        val baseAvgDealAmount =
            BigDecimalUtils.div(baseDealAmount, (hqList.size - baseDays).toDouble())
        val baseAvgDealPercent =
            BigDecimalUtils.div(baseDealPercent, (hqList.size - baseDays).toDouble())
        val basePrices = getHisHqDayClosePrice(hqList[baseDays])
        var needLog = false
        var logStr = codeSum
        var conformSize = 0
        for (index in 0 until baseDays) {
            val dealTimes =
                BigDecimalUtils.div(getHisHqDayDealAmount(hqList[index]), baseAvgDealAmount)
            val percentTimes =
                BigDecimalUtils.div(getHisHqDayDealPercent(hqList[index]), baseAvgDealPercent)
            val diffPrices = BigDecimalUtils.sub(
                getHisHqDayClosePrice(hqList[index]),
                basePrices
            )
            //连续三天3，b时机 待验证
            if (dealTimes >= 3 && percentTimes >= 3) {
                conformSize++
            }
            if (conformSize >= 2) {
                needLog = true
            }
            val addStr =
                "day:${getHisHqDay(hqList[index])}---dealTimes:$dealTimes---percentTimes:$percentTimes---colsePrices:${getHisHqDayClosePrice(
                    hqList[index]
                )}---difPrices:${diffPrices}---percent:${BigDecimalUtils.div(
                    diffPrices,
                    basePrices
                ) * 100}%---curPercent:${getcurPercent(hqList[index])}"
            logStr = logStr.putTogetherAndChangeLineLogic(addStr)
        }
        LogUtil.d("getPriceHisFileLog conformSize:${conformSize} code:$code")
        if (needLog) {
            //                            2涨跌额	3涨跌幅	4最低	5最高	6成交量(手)	7成交金额(万)	8换手率
            val bean = PriceHisRecordGDBean()
            bean.code = code
            bean.id = code.toLong()
            bean.conformSize = conformSize
            bean.dealAmount = stat[7].toDouble()
            bean.dealAvgAmount = stat[7].toDouble() / hqList.size
            bean.turnOverRate = stat[8].replace("%", "").toDouble()
            bean.result =
                "===conformSize:$conformSize===dealAvgAmount:${bean.dealAvgAmount}===turnOverRate:${bean.turnOverRate}===\n$$logStr"
            bean.baseComparePercent = BigDecimalUtils.div(
                BigDecimalUtils.sub(
                    getHisHqDayClosePrice(hqList[0]),
                    basePrices
                ),
                basePrices
            ) * 100
            val result = DaoUtilsStore.getInstance().priceHisRecordGDBeanCommonDaoUtils.insert(bean)
            LogUtil.d("getPriceHisFileLog result:${result} code:$code")
//            LogUtil.d("------needLog---\n$logStr")

        }
        loadState.value = LoadState.GoNext(REQUEST_HIS_HQ)

    }

    fun getHisHqDay(dayDatas: List<String>) = dayDatas[0]
    fun getcurPercent(dayDatas: List<String>) = dayDatas[4]
    fun getHisHqDayClosePrice(dayDatas: List<String>) = dayDatas[2].toDouble()
    fun getHisHqDayDealAmount(dayDatas: List<String>) = dayDatas[8].toDouble()
    fun getHisHqDayDealPercent(dayDatas: List<String>) =
        if (dayDatas[9] == "-") 0.toDouble() else dayDatas[9].replace("%", "").toDouble()

    fun setFilelogPath(formatToDay: String) {
        pathDate = formatToDay
    }

    fun getPriceHisFileLog() {
        var list = DaoUtilsStore.getInstance().priceHisRecordGDBeanCommonDaoUtils.queryAll()
        LogUtil.d("getPriceHisFileLog list size:${list.size}")
        Collections.sort(list, object : Comparator<PriceHisRecordGDBean> {
            override fun compare(p0: PriceHisRecordGDBean, p1: PriceHisRecordGDBean): Int {
                return p1.conformSize.compareTo(p0.conformSize)
            }
        })
        var txtname: String
        list.forEach {
            if (it.id > 600000) {
                txtname = "sh_"
            } else if (it.id > 300000) {
                txtname = "cy_"
            } else {
                txtname = "sz_"
            }
            FileLogUtil.d("${parentBasePath}${txtname}hishq$pathDate", it.result + "\n")
        }

    }


}