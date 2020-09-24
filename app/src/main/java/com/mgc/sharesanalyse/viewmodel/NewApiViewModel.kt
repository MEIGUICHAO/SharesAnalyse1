package com.mgc.sharesanalyse.viewmodel

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.NewApiActivity
import com.mgc.sharesanalyse.base.App
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.base.toSinaCode
import com.mgc.sharesanalyse.entity.*
import com.mgc.sharesanalyse.entity.DealDetailAmountSizeBean.M100
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import kotlinx.coroutines.Deferred
import org.jsoup.Jsoup
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class NewApiViewModel : BaseViewModel() {

    val debug = false
    var needGoOn = false
    var dealDetailIndex = 0
    val dayMillis = 24 * 60 * 60 * 1000

    val DealDetailDays = 14//15天
//    val DealDetailDays = 1//15天

    //    val DealDetailDays = 1
    var DealDetailDaysWeekDayIndex = 0
    var dealDetailBeginDate = ""

    var parentBasePath =
        DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYY_MM) + "/newapi/"

    var pathDate = ""
    var rd: Random? = null

    var curCode = 1
    var curDate = 1
    var curDateStr = "1"

    val DEAL_DETAIL_NEXT_DATE = 1
    val DEAL_DETAIL_NEXT_CODE = 2
    val CHECK_DEAL_DETAIL = 3
    var GetCodeIndex = 0

    private val mHandler: Handler =
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    DEAL_DETAIL_NEXT_DATE -> {
                        requestDealDetailNext(msg.obj.toString())
                        if (!needGoOn) {
                            LogUtil.d("requestDealDetail")
                            loadState.value = LoadState.GoNext(REQUEST_DealDETAIL)
                        }
                    }
                    DEAL_DETAIL_NEXT_CODE -> {
                        loadState.value = LoadState.GoNext(REQUEST_DealDETAIL)
                    }
                    CHECK_DEAL_DETAIL -> {
                        if (msg.arg1 == curCode && msg.arg2 == curDate && needGoOn) {
                            val code = if (curCode < 300000) {
                                DecimalFormat("000000").format(curCode)
                            } else curCode.toString()

                            requestDealDetail(
                                code,
                                curDateStr
                            )
                        }

                    }

                }
            }
        }

    fun getAllCode() {
        GetCodeIndex = 1
        getAllCodeApi()


    }

    private fun getAllCodeApi() {
        var json = RetrofitManager.reqApi.getAllCode(GetCodeIndex.toString())
        launch({
            addCodeToAll(json.await())
        })
    }

    private fun addCodeToAll(json: String) {
        var bean = GsonHelper.parseArray(json, SinaCodeListBean::class.java)
        LogUtil.d("addCodeToAll size:${bean.size}")
        GetCodeIndex++
        bean.forEach {
            LogUtil.d("getAllCode success")
            val allCodeGDBean = AllCodeGDBean()
            allCodeGDBean.code = it.code.replace("sh", "").replace("sz", "")
            LogUtil.d("getAllCode success")
            allCodeGDBean.id = allCodeGDBean.code.toLong()
            allCodeGDBean.name = it.name
            LogUtil.d("getAllCode success")
            var success = DaoUtilsStore.getInstance().allCodeGDBeanDaoUtils.updateOrInsertById(
                allCodeGDBean,
                allCodeGDBean.code.toLong()
            )
            LogUtil.d("getAllCode success:$success")
        }
        if (GetCodeIndex < 52) {
            getAllCodeApi()
        }

    }

    fun getDealDetail(code: String, date: String) {
        dealDetailBeginDate = date
        dealDetailIndex = 0
        DealDetailDaysWeekDayIndex = 1
        App.getAnalysePool().execute {
            LogUtil.d("getDealDetail")
            needGoOn = true
            requestDealDetail(code, date)
        }

    }

    private fun requestDealDetail(
        code: String,
        date: String
    ) {
        LogUtil.d("requestDealDetail")

        var needNetRequest =
            !DBUtils.queryDealDetailIsExsitByCode("${Datas.dealDetailTableName}$date".replace("-",""), code)

        if (needNetRequest) {
            val message = mHandler.obtainMessage()
            message.arg1 = code.toInt()
            curDateStr = date
            message.arg2 = DateUtils.parse(date, FormatterEnum.YYYY_MM_DD).toInt() / 1000
            curCode = code.toInt()
            curDate = DateUtils.parse(date, FormatterEnum.YYYY_MM_DD).toInt() / 1000
            message.what = CHECK_DEAL_DETAIL
            mHandler.sendMessageDelayed(message, 20 * 1000)
            launchNetDealDetail(code, date)
        } else {
            LogUtil.d("skip needNetRequest!!!:$needNetRequest code:$code---date:$date")
            val message = mHandler.obtainMessage()
            message.obj = code
            message.what = DEAL_DETAIL_NEXT_DATE
            mHandler.sendMessage(message)
        }
    }

    private fun launchNetDealDetail(
        code: String,
        date: String
    ) {
        var result = RetrofitManager.reqApi.getDealDetai(code.toSinaCode(), date)
        launch({
            var json = result.await()
            analyseDealDetailJson(json, code, date)

        })
    }

    private fun analyseDealDetailJson(
        json: String,
        code: String,
        date: String
    ) {
        var sinaDealList = GsonHelper.parseArray(json, SinaDealDatailBean::class.java)
        var tableBean = DealDetailTableBean()
        tableBean.code = code
        tableBean.allsize = sinaDealList.size
        LogUtil.d("requestDealDetail")
        if (json != "[]") {
            var pair = classifyDealDetail(sinaDealList)
            tableBean.sizeBean = pair.first
            tableBean.percent = pair.second
        }
        DBUtils.insertDealDetail2DateTable("${Datas.dealDetailTableName}$date".replace("-",""), tableBean)
        handlerDealDetailNextDate(code)
    }

    private fun handlerDealDetailNextDate(code: String) {
        val message = mHandler.obtainMessage()
        message.obj = code
        message.what = DEAL_DETAIL_NEXT_DATE
        mHandler.sendMessageDelayed(message, getRandomTime(700).toLong())
    }

    fun getRandomTime(range: Int): Int {
        if (null == rd) {
            rd = Random()
        }
        return rd!!.nextInt(range) + 500
    }

    private fun requestDealDetailNext(code: String) {

        val pair = judeWeekDay()
        if (needGoOn) {
            if (pair.first) {
//                var dealDetailBean: DealDetailBean =DaoUtilsStore.getInstance().dealDetailBeanCommonDaoUtils.queryByQueryBuilder(DealDetailBeanDao.Properties.Id.eq(code.toLong()))[0]
                requestDealDetail(code, pair.second)
            } else {
                requestDealDetailNext(code)
                LogUtil.d("requestDealDetailNext")
            }
        }
    }

    private fun judeWeekDay(): Pair<Boolean, String> {
        dealDetailIndex++
        val dateMillis = DateUtils.parse(
            dealDetailBeginDate,
            FormatterEnum.YYYY_MM_DD
        ) - dealDetailIndex * dayMillis
        val pair = DateUtils.isWeekDay(dateMillis)
        if (pair.first) {
            DealDetailDaysWeekDayIndex = DealDetailDaysWeekDayIndex + 1
        }
        if (DealDetailDaysWeekDayIndex > DealDetailDays) {
            needGoOn = false
        }
        return pair
    }

    private fun classifyDealDetail(sinaDealList: java.util.ArrayList<SinaDealDatailBean>): Pair<DealDetailAmountSizeBean, Double> {
        var percent = sinaDealList[0].price.toDouble()
            .getPercent(sinaDealList[sinaDealList.size - 1].price.toDouble())
        val dealDetailAmountSizeBean = DealDetailAmountSizeBean()
        dealDetailAmountSizeBean.m100List = ArrayList()
        dealDetailAmountSizeBean.m50List = ArrayList()
        dealDetailAmountSizeBean.m30List = ArrayList()
        dealDetailAmountSizeBean.m10List = ArrayList()
        dealDetailAmountSizeBean.m5List = ArrayList()
        dealDetailAmountSizeBean.m1List = ArrayList()
        dealDetailAmountSizeBean.m05List = ArrayList()
        dealDetailAmountSizeBean.m01List = ArrayList()
        LogUtil.d("classifyDealDetail")
        sinaDealList.forEach {
            if (BigDecimalUtils.mul(
                    it.price.toDouble(),
                    it.volume.toDouble(),
                    3
                ) >= 100 * 1000000
            ) {
                val m100 = M100()
                dealDetailAmountSizeBean.m100Size = dealDetailAmountSizeBean.m100Size + 1
                m100.amount = it.price.toDouble() * it.volume.toDouble()
                m100.time = it.ticktime
                m100.price = it.price.toDouble()
                dealDetailAmountSizeBean.m100List.add(m100)
            } else if (BigDecimalUtils.mul(
                    it.price.toDouble(),
                    it.volume.toDouble(),
                    3
                ) >= 50 * 1000000
            ) {
                val m50 = DealDetailAmountSizeBean.M50()
                dealDetailAmountSizeBean.m50Size = dealDetailAmountSizeBean.m50Size + 1
                m50.amount = it.price.toDouble() * it.volume.toDouble()
                m50.time = it.ticktime
                m50.price = it.price.toDouble()
                dealDetailAmountSizeBean.m50List.add(m50)
            } else if (BigDecimalUtils.mul(
                    it.price.toDouble(),
                    it.volume.toDouble(),
                    3
                ) >= 30 * 1000000
            ) {
                val m30 = DealDetailAmountSizeBean.M30()
                dealDetailAmountSizeBean.m30Size = dealDetailAmountSizeBean.m30Size + 1
                m30.amount = it.price.toDouble() * it.volume.toDouble()
                m30.time = it.ticktime
                m30.price = it.price.toDouble()
                dealDetailAmountSizeBean.m30List.add(m30)
            } else if (BigDecimalUtils.mul(
                    it.price.toDouble(),
                    it.volume.toDouble(),
                    3
                ) >= 10 * 1000000
            ) {
                val m10 = DealDetailAmountSizeBean.M10()
                dealDetailAmountSizeBean.m10Size = dealDetailAmountSizeBean.m10Size + 1
                m10.amount = it.price.toDouble() * it.volume.toDouble()
                m10.time = it.ticktime
                m10.price = it.price.toDouble()
                dealDetailAmountSizeBean.m10List.add(m10)

            } else if (BigDecimalUtils.mul(
                    it.price.toDouble(),
                    it.volume.toDouble(),
                    3
                ) >= 5 * 1000000
            ) {
                val m5 = DealDetailAmountSizeBean.M5()
                dealDetailAmountSizeBean.m5Size = dealDetailAmountSizeBean.m5Size + 1
                m5.amount = it.price.toDouble() * it.volume.toDouble()
                m5.time = it.ticktime
                m5.price = it.price.toDouble()
                dealDetailAmountSizeBean.m5List.add(m5)
            } else if (BigDecimalUtils.mul(
                    it.price.toDouble(),
                    it.volume.toDouble(),
                    3
                ) >= 1 * 1000000
            ) {
                val m1 = DealDetailAmountSizeBean.M1()
                dealDetailAmountSizeBean.m1Size = dealDetailAmountSizeBean.m1Size + 1
                m1.amount = it.price.toDouble() * it.volume.toDouble()
                m1.time = it.ticktime
                m1.price = it.price.toDouble()
                dealDetailAmountSizeBean.m1List.add(m1)
            } else if (BigDecimalUtils.mul(
                    it.price.toDouble(),
                    it.volume.toDouble(),
                    3
                ) >= 0.5 * 1000000
            ) {
                val m05 = DealDetailAmountSizeBean.M05()
                dealDetailAmountSizeBean.m05Size = dealDetailAmountSizeBean.m05Size + 1
                m05.amount = it.price.toDouble() * it.volume.toDouble()
                m05.time = it.ticktime
                m05.price = it.price.toDouble()
                dealDetailAmountSizeBean.m05List.add(m05)

            } else if (BigDecimalUtils.mul(
                    it.price.toDouble(),
                    it.volume.toDouble(),
                    3
                ) >= 0.1 * 1000000
            ) {
                val m01 = DealDetailAmountSizeBean.M01()
                dealDetailAmountSizeBean.m01Size = dealDetailAmountSizeBean.m01Size + 1
                m01.amount = it.price.toDouble() * it.volume.toDouble()
                m01.time = it.ticktime
                m01.price = it.price.toDouble()
                dealDetailAmountSizeBean.m01List.add(m01)
            }


        }
        LogUtil.d("classifyDealDetail")
        return Pair(dealDetailAmountSizeBean, percent)
    }

    fun getPricehis(code: String, startdate: String, endDate: String) {
        var result = RetrofitManager.reqApi.getPricehis(code.toSinaCode(), startdate, endDate)
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
                LogUtil.d("cache_${Datas.sohuStockUrl}--------$code")
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

    private fun requestHisPriceHq(isinsertInTx: Boolean, start: String, end: String, code: String) {

        launch({
//            val xq1 = RetrofitManager.reqApi.getXQInfo(code, "1")
//            val xq2 = requestXQInfo(code,"2",xq1.await())
//            val xq3 = requestXQInfo(code,"3",xq2.await())
//            var xq1Bean:XQInfoBean? = GsonHelper.parse(xq1.await(),XQInfoBean::class.java)
//            var xq2Bean:XQInfoBean? = GsonHelper.parse(xq2.await(),XQInfoBean::class.java)
//            var xq3Bean:XQInfoBean? = GsonHelper.parse(xq3.await(),XQInfoBean::class.java)

//            val mine = RetrofitManager.reqApi.getSinaMineInfo(code)
            val result: Deferred<String>
            if (start.isEmpty() && end.isEmpty()) {
                result = RetrofitManager.reqApi.getHisHq("cn_$code")
            } else {
                result = RetrofitManager.reqApi.getHisHq("cn_$code", start, end)
            }
            updateInTxHisPriceInfo(
                result.await(),
                code,
                isinsertInTx
            )
        })


    }

    private fun requestXQInfo(code: String, page: String, json: String): Deferred<String> {
        return RetrofitManager.reqApi.getXQInfo(code, page)
    }


    private fun updateInTxHisPriceInfo(
        json: String,
        code: String,
        isinsertInTx: Boolean
    ) {

//        LogUtil.d("updateInTxHisPriceInfo code:$code")
//        XQInfoList.addAll(XQInfoList1)
//        XQInfoList.addAll(XQInfoList2)
//        XQInfoList.addAll(XQInfoList3)
//        LogUtil.d("getXQInfo!!! XQInfoList size ${XQInfoList.size}")
//        val mineInfoJson = GsonHelper.toJson(mineInfoList)
//        val xqInfoJson = GsonHelper.toJson(XQInfoList)

        val bean = PricesHisGDBean()
        bean.code = code
        if (isinsertInTx) {
            bean.id = code.toLong()
        }
        bean.date = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
        bean.json = json
//        bean.mineInfo = mineInfoJson
//        bean.xqInfo = xqInfoJson
//        LogUtil.d("xqInfo:\n$xqInfoJson")
        if (isinsertInTx) {
            DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.insertInTx(bean)
        } else {
            DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.updateInTx(bean)
        }
        loadState.value = LoadState.Success(REQUEST_HIS_HQ, json)

    }

    private fun getXQInfo(xq1Bean: XQInfoBean?): ArrayList<InfoDateBean> {
        val list = ArrayList<InfoDateBean>()
        LogUtil.d("getXQInfo!!! size ${list.size}")
        xq1Bean?.list?.forEach {
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
            var needLogTag = ""
            if (dealTimes >= 3 && percentTimes >= 3) {
                conformSize++
                needLogTag = "!!!!!"
            }
            if (conformSize >= 2) {
                needLog = true
            }
            val addStr =
                "${needLogTag} day:${getHisHqDay(hqList[index])}---dealTimes:$dealTimes---percentTimes:$percentTimes---colsePrices:${getHisHqDayClosePrice(
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
            val result =
                DaoUtilsStore.getInstance().priceHisRecordGDBeanCommonDaoUtils.updateOrInsertById(
                    bean,
                    code.toLong()
                )
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

    val detailCodeList = ArrayList<String>()
    fun getPriceHisFileLog() {
        //300185！！！
        detailCodeList.clear()
//        var list = DaoUtilsStore.getInstance().priceHisRecordGDBeanCommonDaoUtils.queryAll()
//        LogUtil.d("getPriceHisFileLog list size:${list.size}")
//        Collections.sort(list, object : Comparator<PriceHisRecordGDBean> {
//            override fun compare(p0: PriceHisRecordGDBean, p1: PriceHisRecordGDBean): Int {
//                return p1.conformSize.compareTo(p0.conformSize)
//            }
//        })

        var list = DaoUtilsStore.getInstance().allCodeGDBeanDaoUtils.queryAll()
        list.forEach {
            detailCodeList.add(it.code)
        }
        if (mActivity is NewApiActivity) {
            (mActivity as NewApiActivity).requestDealDetailBtn()
        }

    }

    fun logDealDetailHqSum() {
        needGoOn = false
        LogUtil.d("complete==========================")
//        var list = DaoUtilsStore.getInstance().priceHisRecordGDBeanCommonDaoUtils.queryAll()
//        var txtname: String
//        list.forEach {
//            if (it.id > 600000) {
//                txtname = "sh_"
//            } else if (it.id > 300000) {
//                txtname = "cy_"
//            } else {
//                txtname = "sz_"
//            }
//            val dealDetailBean = DaoUtilsStore.getInstance().dealDetailBeanCommonDaoUtils.queryById(it.id)
//            var list = GsonHelper.parseArray(dealDetailBean.wholeJson15,DealDetailAmountSizeBean::class.java)
//            var dealDetailStr = ""
//            list.forEach {
//                dealDetailStr = it.m05List
//            }
//
//            FileLogUtil.d("${parentBasePath}${txtname}hishq$pathDate", it.result + "\n")
//        }
    }


}