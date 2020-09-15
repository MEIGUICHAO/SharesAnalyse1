package com.mgc.sharesanalyse.viewmodel

import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.entity.BWCQPResultBean
import com.mgc.sharesanalyse.entity.PriceHisRecordGDBean
import com.mgc.sharesanalyse.entity.PricesHisGDBean
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

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
        val baseAvgDealAmount = BigDecimalUtils.div(baseDealAmount, (hqList.size-baseDays).toDouble())
        val baseAvgDealPercent = BigDecimalUtils.div(baseDealPercent, (hqList.size-baseDays).toDouble())
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
            if (dealTimes >= 5 && percentTimes >= 5) {
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
            bean.dealAvgAmount = stat[7].toDouble()/hqList.size
            bean.turnOverRate = stat[8].replace("%","").toDouble()
            bean.result = "===conformSize:$conformSize===dealAvgAmount:${bean.dealAvgAmount}===turnOverRate:${bean.turnOverRate}===\n$$logStr"
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
    fun getHisHqDayDealPercent(dayDatas: List<String>) = if (dayDatas[9]=="-") 0.toDouble() else dayDatas[9].replace("%", "").toDouble()
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
        var txtname:String
        list.forEach {
            if (it.id > 600000) {
                txtname = "sh_"
            } else if (it.id > 300000) {
                txtname = "cy_"
            } else {
                txtname = "sz_"
            }
            FileLogUtil.d("${parentBasePath}${txtname}hishq$pathDate",it.result+"\n")
        }

    }



    var bwcTag = "###########"
    var requestBody =
        "{\"userId\":\"20200429090359-43c4c27f70_user\",\"openid\":\"oOLE444a8L_g7Fu5pMl074lNDMVo\",\"businessId\":\"20200428151950-5fc381e926_business\",\"overbearfoodId\":\"$bwcTag\",\"serviceNoStr\":\"api_overbear_sign_up\",\"phoneNumber\":\"13316940915\",\"buyChannel\":\"autonomy\",\"shareUserId\":\"\"}"
    var adb = Triple<Boolean, Boolean, Boolean>(false,false,false)

    fun bwc() {

        var beanDeferred = RetrofitManager.reqApi.getBwcDpList()
        launch({
            var bean = beanDeferred.await()
            var slbID = "20200826094759-aede32d710_business"//三里半
            var gscfwID = "20200907094507-15576a4e7c_business"//"广式茶点·炒粉"
            var dpjID = "20200908165211-ead7c9e1af_business"//"舌尖上的大盘鸡"
            val foodList = ArrayList<String>()
            bean.data.forEach {
                LogUtil.d("shopName:${it.shopName}")
                if (it.businessId == slbID || it.businessId == gscfwID || it.businessId == dpjID) {
                    foodList.add(it.overbearFoodEntityId)
                }
            }

            var qiangFP = ArrayList<Deferred<BWCQPResultBean>>()
            Observable.interval(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).subscribe {
                    val judeTime = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)+" 00:30:00"
                    var timeStamp =  DateUtils.parse(judeTime,FormatterEnum.YYYYMMDD__HH_MM_SS)

                    if (System.currentTimeMillis() < timeStamp) {
//                        var result1：Def
                        if (!adb.first) {
                            var body1 = requestBody.replace(bwcTag,foodList[0])
                            var result1 = RetrofitManager.reqApi.qiangBwc(body1)
                        }
                        if (!adb.second) {
                            var body2 = requestBody.replace(bwcTag,foodList[1])
                            var result2 =RetrofitManager.reqApi.qiangBwc(body2)
                        }
                        if (!adb.third) {
                            var body3 = requestBody.replace(bwcTag,foodList[2])
                            var result3 = RetrofitManager.reqApi.qiangBwc(body3)
                        }
                        launch {
                            var resultBean1 = result1.await()
                            if (resultBean1.code == 1 || resultBean1.code == -1) {
                                adb.first = true
                            }
                        }

                    } else {
                        LogUtil.d("not begin")
                    }
                    qiangFP.forEach {

                    }
                }
        })

    }
}