package com.mgc.sharesanalyse.viewmodel

import android.database.Cursor
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.base.*
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
    val dayMillis = (24 * 60 * 60 * 1000).toLong()

    //        val DealDetailDays = 14//15天
    val DealDetailDays = 13//15天

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
    var hqCurCode = "1"

    val DEAL_DETAIL_NEXT_DATE = 1
    val DEAL_DETAIL_NEXT_CODE = 2
    val CHECK_DEAL_DETAIL = 3
    val CHECK_ALL_CODE = 4
    val CHECK_HQ_CODE = 5
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
                            LogUtil.d("@@@requestDealDetail!!!")
                        }

                    }
                    CHECK_ALL_CODE -> {
                        if (msg.arg1 == GetCodeIndex) {
                            getAllCodeApi()
                        }
                    }
                    CHECK_HQ_CODE -> {
                        val code = msg.obj as String
                        if (code == hqCurCode) {
                            getHisHq(code)
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
        var msg = mHandler.obtainMessage()
        msg.what = CHECK_ALL_CODE
        msg.arg1 = GetCodeIndex
        mHandler.sendMessageDelayed(msg, 10 * 1000)
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
        } else {
            initCodeList()
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
            LogUtil.d("@@@requestDealDetail!!!")
        }

    }

    private fun requestDealDetail(
        code: String,
        date: String
    ) {

        var needNetRequest =
            !DBUtils.queryItemIsExsitByCode(
                "${Datas.dealDetailTableName}$date".replace(
                    "-",
                    ""
                ), code
            )
        LogUtil.d("requestDealDetail needNetRequest:${needNetRequest},code:$code,date:$date")

        if (needNetRequest) {
            val message = mHandler.obtainMessage()
            message.arg1 = code.toInt()
            curDateStr = date
            message.arg2 = DateUtils.parse(date, FormatterEnum.YYYY_MM_DD).toInt() / 1000
            curCode = code.toInt()
            curDate = DateUtils.parse(date, FormatterEnum.YYYY_MM_DD).toInt() / 1000
            message.what = CHECK_DEAL_DETAIL
            mHandler.sendMessageDelayed(message, 50 * 1000)
//            requestDealDetailNext(code)
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
        RetrofitManager.baseUrlInterceptor.referer = "${Datas.sinaVipStockUrlRefer}${code.toSinaCode()}"
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
        var tableBean = DealDetailTableBean()
        tableBean.code = code
        LogUtil.d("requestDealDetail")
        if (json != "[]" && json != "false") {
            var sinaDealList = GsonHelper.parseArray(json, SinaDealDatailBean::class.java)
            tableBean.allsize = sinaDealList.size
            var pair = classifyDealDetail(sinaDealList)
            tableBean.name = sinaDealList[0].name
            tableBean.sizeBean = pair.first
            tableBean.percent = pair.second
            DBUtils.insertDealDetail2DateTable(
                "${Datas.dealDetailTableName}$date".replace("-", ""),
                tableBean
            )
        } else {
            DBUtils.insertEmptyDealDetail2DateTable(
                "${Datas.dealDetailTableName}$date".replace("-", ""),
                tableBean
            )
        }
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
                LogUtil.d("@@@requestDealDetail!!! pair:${pair.first},${pair.second}")
            } else {
                requestDealDetailNext(code)
                LogUtil.d("requestDealDetailNext")
            }
        }
    }

    private fun judeWeekDay(): Pair<Boolean, String> {
        val dateMillis = DateUtils.parse(
            dealDetailBeginDate,
            FormatterEnum.YYYY_MM_DD
        ) - dealDetailIndex * dayMillis
        dealDetailIndex++
        val pair = DateUtils.isWeekDay(dateMillis)
        if (pair.first) {
            DealDetailDaysWeekDayIndex = DealDetailDaysWeekDayIndex + 1
        }
        if (DealDetailDaysWeekDayIndex > DealDetailDays) {
            needGoOn = false
        }
        return pair
    }

    private fun classifyDealDetail(sinaDealList: java.util.ArrayList<SinaDealDatailBean>): Pair<DealDetailAmountSizeBean, Float> {

        var percent = sinaDealList[0].price.toFloat()
            .getPercent(sinaDealList[sinaDealList.size - 1].price.toFloat())
        val dealDetailAmountSizeBean = DealDetailAmountSizeBean()
        dealDetailAmountSizeBean.m100List = ArrayList()
        dealDetailAmountSizeBean.m50List = ArrayList()
        dealDetailAmountSizeBean.m30List = ArrayList()
        dealDetailAmountSizeBean.m10List = ArrayList()
        dealDetailAmountSizeBean.m5List = ArrayList()
        dealDetailAmountSizeBean.m1List = ArrayList()
        LogUtil.d("classifyDealDetail")
        sinaDealList.forEach {
            if (BigDecimalUtils.mul(
                    it.price.toFloat(),
                    it.volume.toFloat(),
                    3
                ) >= 100 * 1000000
            ) {
                val m100 = M100()
                dealDetailAmountSizeBean.m100Size = dealDetailAmountSizeBean.m100Size + 1
                m100.amount = it.price.toFloat() * it.volume.toFloat() / 10000
                m100.time = it.ticktime
                m100.price = it.price.toFloat()
                dealDetailAmountSizeBean.m100List.add(m100)
            } else if (BigDecimalUtils.mul(
                    it.price.toFloat(),
                    it.volume.toFloat(),
                    3
                ) >= 50 * 1000000
            ) {
                val m50 = DealDetailAmountSizeBean.M50()
                dealDetailAmountSizeBean.m50Size = dealDetailAmountSizeBean.m50Size + 1
                m50.amount = it.price.toFloat() * it.volume.toFloat() / 10000
                m50.time = it.ticktime
                m50.price = it.price.toFloat()
                dealDetailAmountSizeBean.m50List.add(m50)
            } else if (BigDecimalUtils.mul(
                    it.price.toFloat(),
                    it.volume.toFloat(),
                    3
                ) >= 30 * 1000000
            ) {
                val m30 = DealDetailAmountSizeBean.M30()
                dealDetailAmountSizeBean.m30Size = dealDetailAmountSizeBean.m30Size + 1
                m30.amount = it.price.toFloat() * it.volume.toFloat() / 10000
                m30.time = it.ticktime
                m30.price = it.price.toFloat()
                dealDetailAmountSizeBean.m30List.add(m30)
            } else if (BigDecimalUtils.mul(
                    it.price.toFloat(),
                    it.volume.toFloat(),
                    3
                ) >= 10 * 1000000
            ) {
                val m10 = DealDetailAmountSizeBean.M10()
                dealDetailAmountSizeBean.m10Size = dealDetailAmountSizeBean.m10Size + 1
                m10.amount = it.price.toFloat() * it.volume.toFloat() / 10000
                m10.time = it.ticktime
                m10.price = it.price.toFloat()
                dealDetailAmountSizeBean.m10List.add(m10)

            } else if (BigDecimalUtils.mul(
                    it.price.toFloat(),
                    it.volume.toFloat(),
                    3
                ) >= 5 * 1000000
            ) {
                val m5 = DealDetailAmountSizeBean.M5()
                dealDetailAmountSizeBean.m5Size = dealDetailAmountSizeBean.m5Size + 1
                m5.amount = it.price.toFloat() * it.volume.toFloat() / 10000
                m5.time = it.ticktime
                m5.price = it.price.toFloat()
                dealDetailAmountSizeBean.m5List.add(m5)
            } else if (BigDecimalUtils.mul(
                    it.price.toFloat(),
                    it.volume.toFloat(),
                    3
                ) >= 1 * 1000000
            ) {
                val m1 = DealDetailAmountSizeBean.M1()
                dealDetailAmountSizeBean.m1Size = dealDetailAmountSizeBean.m1Size + 1
                m1.amount = it.price.toFloat() * it.volume.toFloat() / 10000
                m1.time = it.ticktime
                m1.price = it.price.toFloat()
                dealDetailAmountSizeBean.m1List.add(m1)
            } else if (BigDecimalUtils.mul(
                    it.price.toFloat(),
                    it.volume.toFloat(),
                    3
                ) >= 0.5 * 1000000
            ) {
                dealDetailAmountSizeBean.m05Size = dealDetailAmountSizeBean.m05Size + 1

            } else if (BigDecimalUtils.mul(
                    it.price.toFloat(),
                    it.volume.toFloat(),
                    3
                ) >= 0.1 * 1000000
            ) {
                dealDetailAmountSizeBean.m01Size = dealDetailAmountSizeBean.m01Size + 1
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
            DBUtils.queryHHqBeanByCode(getHHQTableName(), code)
//            DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.queryById(code.toLong())


        if (null != bean) {
            LogUtil.d("cache_${Datas.sohuStockUrl}--------$code")
            loadState.value = LoadState.Success(REQUEST_HIS_HQ, bean.json)
        } else {
            val msg = mHandler.obtainMessage()
            msg.what = CHECK_HQ_CODE
            msg.obj = code
            hqCurCode = code
            mHandler.sendMessageDelayed(msg, 20 * 1000)
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

        LogUtil.d("updateInTxHisPriceInfo")
        val bean = PricesHisGDBean()
        bean.code = code
        bean.date = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
        bean.json = json
//        DaoUtilsStore.getInstance().pricesHisGDBeanCommonDaoUtils.updateOrInsertById(bean,code.toLong())
        DBUtils.insertHHq2DateTable(getHHQTableName(), bean)
        loadState.value = LoadState.Success(REQUEST_HIS_HQ, json)

    }

    fun getHHQTableName(): String {
//        return "HHQ_" + if (DateUtils.ifAfterToday1530()) DateUtils.formatToDay(FormatterEnum.YYYYMMDD) else DateUtils.formatYesterDay(
//            FormatterEnum.YYYYMMDD
        return "HHQ_" + DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
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
        codeSum: String, code: String, stat: List<String>?
    ) {
//        val baseDays = hqList.size/2
        var baseDays = 15
        var baseDealAmount = 0.toFloat()
        var baseDealPercent = 0.toFloat()
        baseDays = if (baseDays >= hqList.size) hqList.size - 1 else baseDays
        for (index in (hqList.size - 1) downTo baseDays) {
            baseDealAmount = baseDealAmount + getHisHqDayDealAmount(hqList[index])
            baseDealPercent = baseDealPercent + getHisHqDayDealPercent(hqList[index])
        }
        var baseAvgDealAmount =
            BigDecimalUtils.div(baseDealAmount, (hqList.size - baseDays).toFloat())
        var baseAvgDealPercent =
            BigDecimalUtils.div(baseDealPercent, (hqList.size - baseDays).toFloat())
        val basePrices = getHisHqDayClosePrice(hqList[baseDays])
        var needLog = false
        var logStr = codeSum
        var conformSize = 0
        var txtname = ""
        if (code.toInt() > 600000) {
            txtname = "sh_"
        } else if (code.toInt() > 300000) {
            txtname = "cy_"
        } else {
            txtname = "sz_"
        }
        FileLogUtil.d("${parentBasePath}${txtname}_before0930", "============$code============")
        for (index in 0 until baseDays) {
            DBUtils.updateDDPercentByCode(
                "DD_${getHisHqDay(hqList[index]).replace("-", "")}",
                code,
                getcurPercent(hqList[index]).replace("%", "")
            )
            if (baseAvgDealAmount == 0.toFloat()) {
                baseAvgDealAmount = 1.toFloat()
            }
            if (baseAvgDealPercent == 0.toFloat()) {
                baseAvgDealPercent = 1.toFloat()
            }
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
            if (dealTimes >= 2 && percentTimes >= 2) {
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
                ) * 100}%---curPercent:${getcurPercent(hqList[index])}---dealAmount:${getHisHqDayDealAmount(
                    hqList[index]
                )}"

            logStr = logStr.putTogetherAndChangeLineLogic(addStr)
            var bean = DBUtils.queryDealDetailByCode(
                "DD_${getHisHqDay(hqList[index]).replace("-", "")}",
                code
            )


            bean?.let {

                logStr = logStr.putTogetherAndChangeLineLogic(
                    "DealDetail-->" + getHisHqDay(hqList[index]).replace(
                        "-",
                        ""
                    ) + bean.getToString(getHisHqDayDealAmount(hqList[index]))
                )
                if (null != bean.sizeBean.m100List && bean.sizeBean.m100List.size > 0) {
                    logStr =
                        logStr.putTogetherAndChangeLineLogic(">=100m size = ${bean.sizeBean.m100List.size}:")
                    var str = "   "
                    FileLogUtil.d("${parentBasePath}${txtname}_before0930", "\n")
                    for (i in bean.sizeBean.m100List.size - 1 downTo 0) {
                        logFileBefor930(
                            txtname,
                            getHisHqDay(hqList[index]),
                            bean.sizeBean.m100List.get(i).time,
                            code,
                            getcurPercent(hqList[index]),
                            bean.sizeBean.m100List.get(i).amount.toString()
                        )
                        str =
                            str + "(amount=${bean.sizeBean.m100List.get(i).amount},prices=${bean.sizeBean.m100List.get(
                                i
                            ).price},time=${bean.sizeBean.m100List.get(i).time})---"
                    }
                    logStr = logStr.putTogetherAndChangeLineLogic(str)
                }
                if (null != bean.sizeBean.m50List && bean.sizeBean.m50List.size > 0) {
                    logStr =
                        logStr.putTogetherAndChangeLineLogic(">=50m size = ${bean.sizeBean.m50List.size}:")

                    var str = "   "
                    FileLogUtil.d("${parentBasePath}${txtname}_before0930", "\n")
                    for (i in bean.sizeBean.m50List.size - 1 downTo 0) {
                        logFileBefor930(
                            txtname,
                            getHisHqDay(hqList[index]),
                            bean.sizeBean.m50List.get(i).time,
                            code,
                            getcurPercent(hqList[index]),
                            bean.sizeBean.m50List.get(i).amount.toString()
                        )
                        str =
                            str + "(amount=${bean.sizeBean.m50List.get(i).amount},prices=${bean.sizeBean.m50List.get(
                                i
                            ).price},time=${bean.sizeBean.m50List.get(i).time})---"
                    }
                    logStr = logStr.putTogetherAndChangeLineLogic(str)
                }
                if (null != bean.sizeBean.m30List && bean.sizeBean.m30List.size > 0) {
                    logStr =
                        logStr.putTogetherAndChangeLineLogic(">=30m size = ${bean.sizeBean.m30List.size}:")

                    var str = "   "
                    FileLogUtil.d("${parentBasePath}${txtname}_before0930", "\n")
                    for (i in bean.sizeBean.m30List.size - 1 downTo 0) {
                        logFileBefor930(
                            txtname,
                            getHisHqDay(hqList[index]),
                            bean.sizeBean.m30List.get(i).time,
                            code,
                            getcurPercent(hqList[index]),
                            bean.sizeBean.m30List.get(i).amount.toString()
                        )
                        str =
                            str + "(amount=${bean.sizeBean.m30List.get(i).amount},prices=${bean.sizeBean.m30List.get(
                                i
                            ).price},time=${bean.sizeBean.m30List.get(i).time})---"
                    }
                    logStr = logStr.putTogetherAndChangeLineLogic(str)
                }
                if (null != bean.sizeBean.m10List && bean.sizeBean.m10List.size > 0) {
                    logStr =
                        logStr.putTogetherAndChangeLineLogic(">=10m size = ${bean.sizeBean.m10List.size}:")

                    var str = "   "
                    FileLogUtil.d("${parentBasePath}${txtname}_before0930", "\n")
                    for (i in bean.sizeBean.m10List.size - 1 downTo 0) {
                        logFileBefor930(
                            txtname,
                            getHisHqDay(hqList[index]),
                            bean.sizeBean.m10List.get(i).time,
                            code,
                            getcurPercent(hqList[index]),
                            bean.sizeBean.m10List.get(i).amount.toString()
                        )
                        str =
                            str + "(amount=${bean.sizeBean.m10List.get(i).amount},prices=${bean.sizeBean.m10List.get(
                                i
                            ).price},time=${bean.sizeBean.m10List.get(i).time})---"
                    }
                    logStr = logStr.putTogetherAndChangeLineLogic(str)
                }
                if (null != bean.sizeBean.m5List && bean.sizeBean.m5List.size > 0) {
                    logStr =
                        logStr.putTogetherAndChangeLineLogic(">=5m size = ${bean.sizeBean.m5List.size}:")

                    var str = "   "
                    FileLogUtil.d("${parentBasePath}${txtname}_before0930", "\n")
                    for (i in bean.sizeBean.m5List.size - 1 downTo 0) {
                        logFileBefor930(
                            txtname,
                            getHisHqDay(hqList[index]),
                            bean.sizeBean.m5List.get(i).time,
                            code,
                            getcurPercent(hqList[index]),
                            bean.sizeBean.m5List.get(i).amount.toString()
                        )

                        str =
                            str + "(amount=${bean.sizeBean.m5List.get(i).amount},prices=${bean.sizeBean.m5List.get(
                                i
                            ).price},time=${bean.sizeBean.m5List.get(i).time})---"
                    }
                    logStr = logStr.putTogetherAndChangeLineLogic(str)
                }
                if (null != bean.sizeBean.m1List && bean.sizeBean.m1List.size > 0) {
                    logStr =
                        logStr.putTogetherAndChangeLineLogic(">=1m size = ${bean.sizeBean.m1List.size}:")

                    var str = "   "
                    FileLogUtil.d("${parentBasePath}${txtname}_before0930", "\n")
                    for (i in bean.sizeBean.m1List.size - 1 downTo 0) {
                        logFileBefor930(
                            txtname,
                            getHisHqDay(hqList[index]),
                            bean.sizeBean.m1List.get(i).time,
                            code,
                            getcurPercent(hqList[index]),
                            bean.sizeBean.m1List.get(i).amount.toString()
                        )
                        str =
                            str + "(amount=${bean.sizeBean.m1List.get(i).amount},prices=${bean.sizeBean.m1List.get(
                                i
                            ).price},time=${bean.sizeBean.m1List.get(i).time})---"
                    }
                    logStr = logStr.putTogetherAndChangeLineLogic(str)
                }

                logStr =
                    logStr.putTogetherAndChangeLineLogic("----------------------------------------------------------------------------------------------------------------------")
            }
        }
        LogUtil.d("getPriceHisFileLog conformSize:${conformSize} code:$code")
        if (needLog) {
            //                            2涨跌额	3涨跌幅	4最低	5最高	6成交量(手)	7成交金额(万)	8换手率
            val bean = PriceHisRecordGDBean()
            bean.code = code
            bean.id = code.toLong()
            bean.conformSize = conformSize
            bean.dealAmount = stat?.get(7)?.toFloat() ?: 0.toFloat()
            bean.dealAvgAmount = stat?.get(7)?.toFloat() ?: 0.toFloat() / hqList.size
            var tostr = stat?.get(8)?.replace("%", "")
            tostr?.let {
                var str = it
                if (str.isEmpty()) {
                    str = "0"
                }
                bean.turnOverRate = str.toFloat()
            }

            bean.result =
                "===conformSize:$conformSize===dealAvgAmount:${bean.dealAvgAmount}===turnOverRate:${bean.turnOverRate}===\n$$logStr" +
                        "\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"

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

    fun logFileBefor930(
        txtname: String,
        curDay: String,
        time: String,
        code: String,
        percent: String,
        amount: String
    ) {

        var time = curDay + " $time"
        if (DateUtils.parse(
                time,
                FormatterEnum.YYYY_MM_DD__HH_MM_SS
            ) < DateUtils.parse(curDay + " 09:30:00", FormatterEnum.YYYY_MM_DD__HH_MM_SS)
        ) {
            var befor930Txt = "code:$code,$time,$percent,amount:$amount------"
            FileLogUtil.d("${parentBasePath}${txtname}_before0930", befor930Txt)
        }
    }

    fun getHisHqDay(dayDatas: List<String>) = dayDatas[0]
    fun getcurPercent(dayDatas: List<String>) = dayDatas[4]
    fun getcurPercentFloat(dayDatas: List<String>) = dayDatas[4].replace("%", "").toFloat()
    fun getHisHqDayClosePrice(dayDatas: List<String>) = dayDatas[2].toFloat()
    fun getHisHqDayOpenPrice(dayDatas: List<String>) = dayDatas[1].toFloat()
    fun getHisHqDayDealAmount(dayDatas: List<String>) = dayDatas[8].toFloat()
    fun getHisHqDayWholeDealAmount(dayDatas: List<String>) = dayDatas[8].toFloat() * 10000
    fun getHisHqDayDealVolume(dayDatas: List<String>) = dayDatas[7].toFloat() * 100
    fun getHisHqDayTurnRate(dayDatas: List<String>) = dayDatas[9]
    fun getHisHqDayTurnRateFloat(dayDatas: List<String>) = dayDatas[9].replace("%", "").toFloat()
    fun getHisHqDayDealPercent(dayDatas: List<String>) =
        if (dayDatas[9] == "-") 0.toFloat() else dayDatas[9].replace("%", "").toFloat()

    fun setFilelogPath(formatToDay: String) {
        pathDate = formatToDay
    }

    val detailCodeList = ArrayList<String>()
    fun getPriceHisFileLog() {
        var date =
            if (DateUtils.ifAfterToday1530()) DateUtils.formatToDay(FormatterEnum.YYYYMMDD) else DateUtils.formatYesterDay(
                FormatterEnum.YYYYMMDD
            )
        SPUtils.put(Datas.SPGetHQCodeDate, date)
        //300185！！！
        detailCodeList.clear()
        var list = DaoUtilsStore.getInstance().priceHisRecordGDBeanCommonDaoUtils.queryAll()
        LogUtil.d("getPriceHisFileLog list size:${list.size}")
        sortpriceHisRecordGDBean(list)

//        var list = DaoUtilsStore.getInstance().allCodeGDBeanDaoUtils.queryAll()
        list.forEach {
            detailCodeList.add(it.code)
        }
        logDealDetailHqSum()
//        if (mActivity is NewApiActivity) {
//            (mActivity as NewApiActivity).requestDealDetailBtn()
//        }

    }

    private fun sortpriceHisRecordGDBean(list: List<PriceHisRecordGDBean>?) {
        Collections.sort(list, object : Comparator<PriceHisRecordGDBean> {
            override fun compare(p0: PriceHisRecordGDBean, p1: PriceHisRecordGDBean): Int {
                return p1.result.split("---percent:")[1].split("%---curPercent")[0].toFloat()
                    .compareTo(p0.result.split("---percent:")[1].split("%---curPercent")[0].toFloat())
            }
        })
    }

    fun logDealDetailHqSum() {
        needGoOn = false
        LogUtil.d("complete==========================")
        var list = DaoUtilsStore.getInstance().priceHisRecordGDBeanCommonDaoUtils.queryAll()
        var txtname: String
        val hhqList = getDDList().second
        for (i in 0 until hhqList.size - 1) {
            DBUtils.dropTable(hhqList[i])
        }

        sortpriceHisRecordGDBean(list)
        list.forEach {
            if (it.id > 600000) {
                txtname = "sh_"
            } else if (it.id > 300000) {
                txtname = "cy_"
            } else {
                txtname = "sz_"
            }
            FileLogUtil.d("${parentBasePath}${txtname}hishq$pathDate", "\n" + it.result)
        }
        LogUtil.d("logDealDetailHqSum---completed----------------------------")

    }

    fun foreachDDInfo() {

//        var shList = ArrayList<DealDetailTableBean>()
//        var szList = ArrayList<DealDetailTableBean>()
//        var szCyList = ArrayList<DealDetailTableBean>()
//        var list = DaoUtilsStore.getInstance().allCodeGDBeanDaoUtils.queryAll()
//        list.forEach {
//            var ddBean = DBUtils.queryDealDetailByCode("DD_20200924", it.code)
//            ddBean?.let {
//                if (it.code.toFloat() > 600000) {
//                    shList.add(it)

//                } else if (it.code.toFloat() > 300000) {
//                    szCyList.add(it)
//                } else {
//                    szList.add(it)
//                }
//            }
//        }
//        LogUtil.d("shList size:${shList.size}")
//        shList.sortDDBeanDescByAllsize()
//        szCyList.sortDDBeanDescByAllsize()
//        szList.sortDDBeanDescByAllsize()
//        LogUtil.d("szCyList size:${szCyList.size}")
//        LogUtil.d("szList size:${szList.size}")
//        shList.forEach {
//            FileLogUtil.d("${parentBasePath}sh_hishq$pathDate", it.toString())
//        }
//        szCyList.forEach {
//            FileLogUtil.d("${parentBasePath}cy_hishq$pathDate", it.toString())
//        }
//        szList.forEach {
//            FileLogUtil.d("${parentBasePath}sz_hishq$pathDate", it.toString())
//        }

    }

    fun getSumDD() {

//        DBUtils.dropTable(Datas.sdd+"2010")
//        DBUtils.dropTable(Datas.sddALL)
        val sddTableNameALL = Datas.sddALL
        val sddTableName = Datas.sdd + DateUtils.formatToDay(FormatterEnum.YYMM)
        val pair = getDDList()
        val ddlist = pair.first
        val hhqlist = pair.second
        var codelist = DaoUtilsStore.getInstance().allCodeGDBeanDaoUtils.queryAll()

        val initHHQbean =
            DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size-1], 1.toString())
        val initHHQbean1 =
            DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size-1], 601216.toString())
        val initHHQbean2 =
            DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size-1], 858.toString())
        val initHHQbean3 =
            DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size-1], 300059.toString())
        val hisHqBeans = GsonHelper.parseArray(initHHQbean?.json, HisHqBean::class.java)[0].hq
        val hisHqBeans1 = GsonHelper.parseArray(initHHQbean1?.json, HisHqBean::class.java)[0].hq
        val hisHqBeans2 = GsonHelper.parseArray(initHHQbean2?.json, HisHqBean::class.java)[0].hq
        val hisHqBeans3 = GsonHelper.parseArray(initHHQbean3?.json, HisHqBean::class.java)[0].hq
        val hhbeansList = listOf(hisHqBeans,hisHqBeans1,hisHqBeans2,hisHqBeans3)
        var hhqBeginIndex = 0
        var hhqVeryBeginIndex = 0
        var hhqMonthVeryBeginIndex = 0
        var vbts = DateUtils.parse(
            ddlist[0].replace(
                Datas.dealDetailTableName,
                ""
            ), FormatterEnum.YYYYMMDD
        )

        val sumDDBeginBean =
            DBUtils.querySumDDBeanByCode(sddTableNameALL, codelist[0].code)
        var sumDateIndexTs = 0.toLong()
        var sumDateBeginIndex = 0
        if (null != sumDDBeginBean) {
            sumDateIndexTs = DateUtils.parse(sumDDBeginBean.sizeBean.date, FormatterEnum.YYYYMMDD)
            for (i in 0 until ddlist.size - 1) {
                val date = ddlist[i].replace(Datas.dealDetailTableName,"")
                val curts = DateUtils.parse(date, FormatterEnum.YYYYMMDD)
                if (sumDateIndexTs >= curts) {
                    sumDateBeginIndex = i
                }
            }
        }
        val lastDate = DBUtils.querySumDDBeanLastDate(sddTableNameALL)
        if (!lastDate.isEmpty()) {
            val lastDatets = DateUtils.parse(lastDate, FormatterEnum.YYYYMMDD)
            //need test
            if (sumDateIndexTs == lastDatets) {
                sumDateBeginIndex++
            }
        }
        LogUtil.d("querySumDDBeanLastDate:$lastDate")

        val curMonthTS = DateUtils.parse(DateUtils.formatToDay(FormatterEnum.YYYY_MM)+"-01",FormatterEnum.YYYY_MM_DD)
        val curYearTS = DateUtils.parse(DateUtils.formatToDay(FormatterEnum.YYYY)+"-01-01",FormatterEnum.YYYY_MM_DD)

        hhbeansList.forEach {

            for (index in 0 until hisHqBeans.size-1) {

                LogUtil.d("hhbeansList date:${hisHqBeans[index][0].replace("-", "")}")
                if (ddlist[0].replace(
                        Datas.dealDetailTableName,
                        ""
                    ) == hisHqBeans[index][0].replace("-", "")
                ) {
                    if (index > hhqBeginIndex) {
                        hhqBeginIndex = index
                        hhqVeryBeginIndex = index
                        LogUtil.d("hhqBeginIndex@@:$hhqBeginIndex")
                    }
                }
                if (DateUtils.parse(hisHqBeans[index][0], FormatterEnum.YYYY_MM_DD) >= curMonthTS) {
                    if (index > hhqMonthVeryBeginIndex) {
                        hhqMonthVeryBeginIndex = index
                        LogUtil.d("hhqMonthVeryBeginIndex@@:$hhqMonthVeryBeginIndex")
                    }
                }
            }
        }

        if (sumDateBeginIndex > 0) {
            hhqBeginIndex = ddlist.size - sumDateBeginIndex
        }
        LogUtil.d("sumDateBeginIndex:$sumDateBeginIndex ddlist.size:${ddlist.size} hhqBeginIndex:$hhqBeginIndex")

        for (ddidnex in sumDateBeginIndex until ddlist.size)  {
//        for (ddidnex in sumDateBeginIndex until ddlist.size)  {
//        for (ddidnex in 0 until 4) {
            val date = ddlist[ddidnex].replace(
                Datas.dealDetailTableName,
                ""
            )

            LogUtil.d("for date:$date ,hhqBeginIndex:$hhqBeginIndex")
            val curIndexTs = DateUtils.parse(date,FormatterEnum.YYYYMMDD)
            for (codeidnex in 0 until 1) {
//            for (codeidnex in 0 until codelist.size) {
                LogUtil.d("${codelist[codeidnex].code}curIndexTs:$curIndexTs,curIndexTs_date:$date,curYearTS:$curYearTS,curYearTS_date:${DateUtils.formatToDay(FormatterEnum.YYYY)+"-01-01"},curMonthTS:$curMonthTS,curMonthTS_date:${DateUtils.formatToDay(FormatterEnum.YYYY_MM)+"-01"}")
                LogUtil.d("${codelist[codeidnex].code}curIndexTs >= curYearTS:${curIndexTs >= curYearTS},curIndexTs >= curMonthTS:${curIndexTs >= curMonthTS}")
                if (curIndexTs >= curYearTS) {
                    if (curIndexTs >= curMonthTS) {
                        operaSddDB(
                            ddlist,
                            ddidnex,
                            codelist,
                            codeidnex,
                            sddTableName,
                            date,
                            hhqlist,
                            hhqBeginIndex,
                            sumDateIndexTs,
                            hhqMonthVeryBeginIndex,
                            curMonthTS
                        )
                    }
                    operaSddDB(
                        ddlist,
                        ddidnex,
                        codelist,
                        codeidnex,
                        sddTableNameALL,
                        date,
                        hhqlist,
                        hhqBeginIndex,
                        sumDateIndexTs,
                        hhqVeryBeginIndex,
                        vbts
                    )
                }
            }
            hhqBeginIndex--
            if (hhqBeginIndex < 0) {
                break
            }
        }



//        DBUtils.dropTable(Datas.shdd+DateUtils.formatToDay(FormatterEnum.YYMM))
//        DBUtils.dropTable(Datas.shdd+"2009")
//        DBUtils.dropTable(Datas.shddAll)
//        operaSHDDAndCHDD(
//            hhqlist,
//            hhqVeryBeginIndex,
//            sumDateBeginIndex,
//            ddlist,
//            codelist,
//            curYearTS,
//            curMonthTS
//        )
        //    0日期	1开盘	2收盘	3涨跌额	4涨跌幅	5最低	6最高	7成交量(手)	8成交金额(万)	9换手率

    }

    private fun operaSHDDAndCHDD(
        hhqlist: ArrayList<String>,
        hhqVeryBeginIndex: Int,
        sumDateBeginIndex: Int,
        ddlist: ArrayList<String>,
        codelist: MutableList<AllCodeGDBean>,
        curYearTS: Long,
        curMonthTS: Long
    ) {
        //TODO
        var hhqBeginIndex = hhqVeryBeginIndex
        for (ddidnex in 0 until ddlist.size) {
    //        for (ddidnex in 0 until 4) {
            val date = ddlist[ddidnex].replace(
                Datas.dealDetailTableName,
                ""
            )
            LogUtil.d("date:$date")
            val curIndexTs = DateUtils.parse(date, FormatterEnum.YYYYMMDD)
    //            for (codeidnex in 0 until 40) {
//            for (codeidnex in 0 until codelist.size step 1) {
            for (codeidnex in 0 until codelist.size step 50) {
                val ddBean =
                    DBUtils.queryDealDetailByCode(ddlist[ddidnex], codelist[codeidnex].code)
                ddBean?.let {

                    val bean =
                        DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size - 1], ddBean.code)
                    val hisHqBean = GsonHelper.parseArray(bean?.json, HisHqBean::class.java)

                    if (hhqBeginIndex >= hisHqBean[0].hq.size) {
                        return@let
                    }
                    val hhqbean = hisHqBean[0].hq[hhqBeginIndex]

                    //TODO test
                    if (curIndexTs >= curYearTS) {
                        insertOrUpdateCodeHDD(hhqbean, ddBean, date)
                        val curMonthSHHDTBName =
                            Datas.shdd + DateUtils.changeFormatter(DateUtils.parse(date,FormatterEnum.YYYYMMDD),FormatterEnum.YYMM)
                        operaSHDDMonth(
                            curMonthSHHDTBName,
                            codelist,
                            codeidnex,
                            date,
                            hhqbean,
                            ddBean,false
                        )
                        operaSHDDMonth(
                            Datas.shddAll,
                            codelist,
                            codeidnex,
                            date,
                            hhqbean,
                            ddBean,true
                        )
                    }

                }
//                "( NAME , DATE , OP , CP , PP , P , AUP ,TR, DV , DA , AS " +
//                        ", M100S , M50S , M30S , M10S , M5S , M1S , M05S , M01S , L01S , PP100M , PP50M , PP30M , PP10M , PP5M , PP1M , PPL1M " +
//                        ", DAA , DA5000 , DA1000 , DA500 , DA100 ) "
                //    0日期	1开盘	2收盘	3涨跌额	4涨跌幅	5最低	6最高	7成交量(手)	8成交金额(万)	9换手率

            }
            hhqBeginIndex--
            if (hhqBeginIndex < 0) {
                break
            }
        }
    }

    private fun operaSHDDMonth(
        curMonthSHHDTBName: String,
        codelist: MutableList<AllCodeGDBean>,
        codeidnex: Int,
        date: String,
        hhqbean: MutableList<String>,
        ddBean: DealDetailTableBean,
        isAll: Boolean
    ) {
        val shddBean =
            DBUtils.querySHDDByCode(curMonthSHHDTBName, codelist[codeidnex].code)

        if (null == shddBean) {
            insertSHDDBean(curMonthSHHDTBName, hhqbean, ddBean, date)
        } else {
            updateSHDDBean(
                curMonthSHHDTBName,
                hhqbean,
                ddBean,
                date,
                isAll,
                DateUtils.changeFormatter(
                    DateUtils.parse(date, FormatterEnum.YYYYMMDD),
                    FormatterEnum.YYMM
                )
            )
        }
    }

    private fun updateSHDDBean(tbName:String,hhqbean: List<String>, ddBean: DealDetailTableBean, date: String,isAll:Boolean,shddDateYM:String) {
        val lastSHDDBean = DBUtils.querySHDDByCode(tbName,ddBean.code)!!
        if (date != lastSHDDBean.date) {
            val curSHDDBean = SHDDBean()
            curSHDDBean.c = ddBean.code
            curSHDDBean.n = ddBean.name
            curSHDDBean.date = lastSHDDBean.date
            val op = DBUtils.queryOPByCHDD(ddBean.code, isAll, shddDateYM)
            if (op > 0) {
                val diffP = BigDecimalUtils.sub(getHisHqDayClosePrice(hhqbean),op)
                curSHDDBean.aup = (BigDecimalUtils.safeDiv(diffP, op) * 100)
                LogUtil.d("$tbName $isAll updateSHDDBean----,date:$date,diffP:$diffP,op:$op,cp:${getHisHqDayClosePrice(hhqbean)},aup:${curSHDDBean.aup},YM:$shddDateYM,lastSHDDBean.mper:${lastSHDDBean.mper},${curSHDDBean.aup > lastSHDDBean.mper}")
            }

            curSHDDBean.lper = lastSHDDBean.lper
            curSHDDBean.lpd = lastSHDDBean.lpd
            curSHDDBean.mpd =  lastSHDDBean.mpd
            if (curSHDDBean.aup > lastSHDDBean.mper) {
                curSHDDBean.mper = curSHDDBean.aup
                curSHDDBean.mpd = date
                curSHDDBean.mp = getHisHqDayClosePrice(hhqbean)
            }
            if (curSHDDBean.aup < lastSHDDBean.lper) {
                curSHDDBean.lper = curSHDDBean.aup
                curSHDDBean.lpd = date
                curSHDDBean.lp = getHisHqDayClosePrice(hhqbean)
            }

            curSHDDBean.autr = lastSHDDBean.autr + getHisHqDayTurnRateFloat(hhqbean)
            curSHDDBean.av = lastSHDDBean.av + getAvValue(getHisHqDayDealVolume(hhqbean))
            LogUtil.d("curSHDDBean.av:${curSHDDBean.av},lastSHDDBean.av:${lastSHDDBean.av},getAvValue:${getAvValue(getHisHqDayDealVolume(hhqbean))}")
            curSHDDBean.ad = lastSHDDBean.ad + getHisHqDayWholeDealAmount(hhqbean)/Datas.NUM_100M
            val avJsonBean = GsonHelper.parse(lastSHDDBean.avj,AvJsonBean::class.java)

            val mSizeBean = ddBean.sizeBean
            curSHDDBean.aV100 = avJsonBean.aV100 + mSizeBean.m100List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value + BigDecimalUtils.div(it.amount*10000, it.price)
                }
                getAvValue(value)
            }
            curSHDDBean.aV50 = avJsonBean.aV50+mSizeBean.m50List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value + BigDecimalUtils.div(it.amount*10000,it.price)
                }
                getAvValue(value )
            }
            curSHDDBean.aV30 = avJsonBean.aV30+mSizeBean.m30List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value + BigDecimalUtils.div(it.amount*10000,it.price)
                }
                getAvValue(value )
            }
            curSHDDBean.aV10 = avJsonBean.aV10+mSizeBean.m10List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value + BigDecimalUtils.div(it.amount*10000,it.price)
                }
                getAvValue(value)
            }
            curSHDDBean.aV5 = avJsonBean.aV5+mSizeBean.m5List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value + BigDecimalUtils.div(it.amount*10000,it.price)
                }
                getAvValue(value)
            }
            curSHDDBean.aV1 = avJsonBean.aV1+mSizeBean.m1List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value + BigDecimalUtils.div(it.amount*10000,it.price)
                }
                getAvValue(value)
            }
            curSHDDBean.aD100 =lastSHDDBean.aD100+ mSizeBean.m100List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value+ it.amount
                }
                getAdValue(value)
            }
            curSHDDBean.aD50 = lastSHDDBean.aD50+mSizeBean.m50List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value+ it.amount
                }
                getAdValue(value)
            }
            curSHDDBean.aD30 = lastSHDDBean.aD30+mSizeBean.m30List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value+ it.amount
                }
                getAdValue(value)
            }
            curSHDDBean.aD10 = lastSHDDBean.aD10+mSizeBean.m10List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value+ it.amount
                }
                getAdValue(value)
            }
            curSHDDBean.aD5 = lastSHDDBean.aD5+mSizeBean.m5List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value+ it.amount
                }
                getAdValue(value)
            }
            curSHDDBean.aD1 = lastSHDDBean.aD1+mSizeBean.m1List.run {
                var value = 0.toFloat()
                this?.forEach {
                    value = value+ it.amount
                }
                getAdValue(value)
            }
            curSHDDBean.pp = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.ad,curSHDDBean.av)
            curSHDDBean.pP100 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD100,curSHDDBean.aV100)
            curSHDDBean.pP50 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD50,curSHDDBean.aV50)
            curSHDDBean.pP30 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD30,curSHDDBean.aV30)
            curSHDDBean.pP10 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD10,curSHDDBean.aV10)
            curSHDDBean.pP5 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD5,curSHDDBean.aV5)
            curSHDDBean.pP1 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD1,curSHDDBean.aV1)
            curSHDDBean.avj = getAvJson(curSHDDBean)

            LogUtil.d("shddBean.pp:${curSHDDBean.pp}")
            LogUtil.d("shddBean.ad:${curSHDDBean.ad}")
            LogUtil.d("shddBean.av:${curSHDDBean.av}")
            DBUtils.insertOrUpdateSHDDTable(tbName,date,curSHDDBean)
        }


    }

    private fun insertSHDDBean(tbName:String,hhqbean: List<String>, ddBean: DealDetailTableBean, date: String) {
        val shddBean = SHDDBean()
        shddBean.c = ddBean.code
        shddBean.n = ddBean.name
        shddBean.mpd = date
        shddBean.lpd = date
        shddBean.date = date
        shddBean.aup = getcurPercentFloat(hhqbean)
        shddBean.mper = getcurPercentFloat(hhqbean)
        shddBean.lper = getcurPercentFloat(hhqbean)
        shddBean.mp = getHisHqDayClosePrice(hhqbean)
        shddBean.lp = getHisHqDayClosePrice(hhqbean)
        shddBean.av = getAvValue(getHisHqDayDealVolume(hhqbean))
        shddBean.ad = getHisHqDayWholeDealAmount(hhqbean)/Datas.NUM_100M
        shddBean.autr = getHisHqDayTurnRateFloat(hhqbean)
        val mSizeBean = ddBean.sizeBean
        shddBean.aV100 = mSizeBean.m100List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value + BigDecimalUtils.div(it.amount*10000,it.price)
            }
            getAvValue(value)
        }
        shddBean.aV50 = mSizeBean.m50List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value + BigDecimalUtils.div(it.amount*10000,it.price)
            }
            getAvValue(value)
        }
        LogUtil.d("shddBean.aV50:${shddBean.aV50}")
        shddBean.aV30 = mSizeBean.m30List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value + BigDecimalUtils.div(it.amount*10000,it.price)
            }
            getAvValue(value)
        }
        shddBean.aV10 = mSizeBean.m10List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value + BigDecimalUtils.div(it.amount * 10000, it.price)
            }
            getAvValue(value)
        }
        shddBean.aV5 = mSizeBean.m5List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value + BigDecimalUtils.div(it.amount*10000,it.price)
            }
            getAvValue(value)
        }
        shddBean.aV1 = mSizeBean.m1List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value + BigDecimalUtils.div(it.amount*10000,it.price)
            }
            getAvValue(value)
        }
        shddBean.aD100 = mSizeBean.m100List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value+ it.amount
            }
            getAdValue(value)
        }
        shddBean.aD50 = mSizeBean.m50List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value+ it.amount
            }
            getAdValue(value)
        }
        shddBean.aD30 = mSizeBean.m30List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value+ it.amount
            }
            getAdValue(value)
        }
        shddBean.aD10 = mSizeBean.m10List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value+ it.amount
            }
            getAdValue(value)
        }

        shddBean.aD5 = mSizeBean.m5List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value+ it.amount
            }
            getAdValue(value)
        }
        shddBean.aD1 = mSizeBean.m1List.run {
            var value = 0.toFloat()
            this?.forEach {
                value = value+ it.amount
            }
            getAdValue(value)
        }
        shddBean.pp = BigDecimalUtils.getPPBySafeDiv(shddBean.ad,shddBean.av)
        shddBean.pP100 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD100,shddBean.aV100)
        shddBean.pP50 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD50,shddBean.aV50)
        shddBean.pP30 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD30,shddBean.aV30)
        shddBean.pP10 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD10,shddBean.aV10)
        shddBean.pP5 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD5,shddBean.aV5)
        shddBean.pP1 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD1,shddBean.aV1)
        shddBean.avj = getAvJson(shddBean)

        LogUtil.d("shddBean.pp:${shddBean.pp}")
        LogUtil.d("shddBean.ad:${shddBean.ad}")
        LogUtil.d("shddBean.av:${shddBean.av}")

        DBUtils.insertOrUpdateSHDDTable(tbName,date,shddBean)

    }

    private fun getAvJson(shddBean: SHDDBean):String {
        val avJB = AvJsonBean()
        avJB.av = shddBean.av
        avJB.aV100 = shddBean.aV100
        avJB.aV50 = shddBean.aV50
        avJB.aV30 = shddBean.aV30
        avJB.aV10 = shddBean.aV10
        avJB.aV5 = shddBean.aV5
        avJB.aV1 = shddBean.aV1
        return GsonHelper.toJson(avJB)
    }

    private fun getAdValue(value: Float) = String.format("%.4f", value * 10000 / Datas.NUM_100M).toFloat()

    private fun insertOrUpdateCodeHDD(
        hhqbean: List<String>,
        ddBean: DealDetailTableBean,
        date: String
    ) {
        DBUtils.switchDBName(ddBean.code.toCodeHDD(date,FormatterEnum.YYYYMMDD))
        val tbName = Datas.CHDD + ddBean.code
        LogUtil.d("tbName:$tbName")
        val codeHDDBean = CodeHDDBean()
        codeHDDBean.name = ddBean.name
        codeHDDBean.`as` = ddBean.allsize.toFloat()
        val mSizeBean = ddBean.sizeBean
        codeHDDBean.dA5000 = mSizeBean.getGt5000() / Datas.NUM_100M
        codeHDDBean.dA1000 = mSizeBean.getGt1000() / Datas.NUM_100M
        codeHDDBean.dA500 = mSizeBean.getGt500() / Datas.NUM_100M
        codeHDDBean.dA100 = mSizeBean.getGt100() / Datas.NUM_100M
        codeHDDBean.date = date
        codeHDDBean.da = getHisHqDayWholeDealAmount(hhqbean) / Datas.NUM_100M
        codeHDDBean.op = getHisHqDayOpenPrice(hhqbean)
        codeHDDBean.cp = getHisHqDayClosePrice(hhqbean)
        codeHDDBean.pp = BigDecimalUtils.div(
            getHisHqDayWholeDealAmount(hhqbean),
            getHisHqDayDealVolume(hhqbean)
        )
        codeHDDBean.p = getcurPercentFloat(hhqbean)
        codeHDDBean.tr = getHisHqDayTurnRate(hhqbean)
        codeHDDBean.dv = getAvValue(getHisHqDayDealVolume(hhqbean))
        codeHDDBean.m100S = mSizeBean.m100Size
        codeHDDBean.m50S = mSizeBean.m50Size
        codeHDDBean.m30S = mSizeBean.m30Size
        codeHDDBean.m10S = mSizeBean.m10Size
        codeHDDBean.m5S = mSizeBean.m5Size
        codeHDDBean.m1S = mSizeBean.m1Size
        codeHDDBean.m05S = mSizeBean.m05Size
        codeHDDBean.m01S = mSizeBean.m01Size
        codeHDDBean.l01S = mSizeBean.l01Size
        codeHDDBean.pP100M = mSizeBean.m100List.run {
            var amount = 0.toFloat()
            var volume = 0.toFloat()
            this?.forEach {
                amount = amount + it.amount
                volume = volume + BigDecimalUtils.div(it.amount, it.price)
            }
            if (volume > 0) {
                BigDecimalUtils.div(amount, volume)
            } else {
                0.toFloat()
            }
        }
        var auV = 0.toFloat()
        codeHDDBean.pP50M = mSizeBean.m50List.run {
            var amount = 0.toFloat()
            var volume = 0.toFloat()
            this?.forEach {
                amount = amount + it.amount
                volume = volume + BigDecimalUtils.div(it.amount, it.price)
            }
            auV = auV + volume
            if (volume > 0) {
                BigDecimalUtils.div(amount, volume)
            } else {
                0.toFloat()
            }
        }
        codeHDDBean.pP10M = mSizeBean.m10List.run {
            var amount = 0.toFloat()
            var volume = 0.toFloat()
            this?.forEach {
                amount = amount + it.amount
                volume = volume + BigDecimalUtils.div(it.amount, it.price)
            }
            auV = auV + volume
            if (volume > 0) {
                BigDecimalUtils.div(amount, volume)
            } else {
                0.toFloat()
            }
        }
        codeHDDBean.pP30M = mSizeBean.m30List.run {
            var amount = 0.toFloat()
            var volume = 0.toFloat()
            this?.forEach {
                amount = amount + it.amount
                volume = volume + BigDecimalUtils.div(it.amount, it.price)
            }
            auV = auV + volume
            if (volume > 0) {
                BigDecimalUtils.div(amount, volume)
            } else {
                0.toFloat()
            }
        }
        codeHDDBean.pP5M = mSizeBean.m5List.run {
            var amount = 0.toFloat()
            var volume = 0.toFloat()
            this?.forEach {
                amount = amount + it.amount
                volume = volume + BigDecimalUtils.div(it.amount, it.price)
            }
            auV = auV + volume
            if (volume > 0) {
                BigDecimalUtils.div(amount, volume)
            } else {
                0.toFloat()
            }
        }

        codeHDDBean.pP1M = mSizeBean.m1List.run {
            var amount = 0.toFloat()
            var volume = 0.toFloat()
            this?.forEach {
                amount = amount + it.amount
                volume = volume + BigDecimalUtils.div(it.amount, it.price)
            }
            auV = auV + volume
            if (volume > 0) {
                BigDecimalUtils.div(amount, volume)
            } else {
                0.toFloat()
            }
        }
        val leftV = BigDecimalUtils.sub(getHisHqDayDealVolume(hhqbean), auV)
        if (leftV > 0) {
            codeHDDBean.ppL1M = BigDecimalUtils.div(
                BigDecimalUtils.sub(
                    getHisHqDayWholeDealAmount(hhqbean),
                    mSizeBean.getGt100()
                ), leftV
            )
        }
        val beginOP = DBUtils.queryFirstOPByCodeHDD(tbName)
        if (beginOP > 0) {
            val diff = BigDecimalUtils.sub(getHisHqDayClosePrice(hhqbean),beginOP)
            codeHDDBean.aup = BigDecimalUtils.div(diff, beginOP) * 100
        }
        DBUtils.insertCodeHDD(tbName, codeHDDBean)
    }

    private fun getAvValue(value: Float) = String.format("%.4f", value / Datas.NUM_WAN).toFloat()

    private fun operaSddDB(
        ddlist: ArrayList<String>,
        ddidnex: Int,
        codelist: MutableList<AllCodeGDBean>,
        codeidnex: Int,
        sddTableNameALL: String,
        date: String,
        hhqlist: ArrayList<String>,
        hhqBeginIndex: Int,
        sumDateIndexTs: Long,
        hhqVeryBeginIndex: Int,
        vbts: Long
    ) {

        val ddBean =
            DBUtils.queryDealDetailByCode(ddlist[ddidnex], codelist[codeidnex].code)

        ddBean?.let {
            LogUtil.d("ddBean m10Size:${ddBean.sizeBean?.m10Size}")
            val sumDDBean =
                DBUtils.querySumDDBeanByCode(sddTableNameALL, codelist[codeidnex].code)
            DBUtils.insertOrUpdateSumDD2DateTable(
                date, sddTableNameALL, sumDDBean, ddBean
            )

            val bean =
                DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size - 1], codelist[codeidnex].code)
            val hisHqBean = GsonHelper.parseArray(bean?.json, HisHqBean::class.java)
            val hhqbean = hisHqBean[0].hq


            if (null == sumDDBean) {
                if (hhqBeginIndex < hhqbean.size) {
                    DBUtils.setSDDPercent(
                        sddTableNameALL,
                        getcurPercentFloat(hhqbean[hhqBeginIndex]),
                        codelist[codeidnex].code
                    )
                }

            } else {

                val mSumDateIndexTs =
                    DateUtils.parse(date, FormatterEnum.YYYYMMDD)
                if (sumDateIndexTs == mSumDateIndexTs) {
                    LogUtil.d("相同跳过 ${date}")
                    return@let
                }
                var changeVeryBeginIndex = hhqVeryBeginIndex
                while (changeVeryBeginIndex >= hhqbean.size) {
                    changeVeryBeginIndex--
                }
                var verybeginTS = DateUtils.parse(
                    getHisHqDay(hhqbean[changeVeryBeginIndex]),
                    FormatterEnum.YYYY_MM_DD
                )
                if (hhqBeginIndex >= hhqbean.size) {
                    return@let
                }
                val curTs =
                    DateUtils.parse(getHisHqDay(hhqbean[hhqBeginIndex]), FormatterEnum.YYYY_MM_DD)

                if (verybeginTS < vbts) {
                    while (verybeginTS < vbts) {
                        changeVeryBeginIndex--
                        if (changeVeryBeginIndex < 0) {
                            return@let
                        }
                        verybeginTS = DateUtils.parse(
                            getHisHqDay(hhqbean[changeVeryBeginIndex]),
                            FormatterEnum.YYYY_MM_DD
                        )
                    }
                    setSDDPercent(
                        hhqBeginIndex,
                        hhqbean,
                        changeVeryBeginIndex,
                        curTs,
                        vbts,
                        sddTableNameALL,
                        codelist,
                        codeidnex,
                        sumDDBean,
                        date
                    )
                    LogUtil.d("set_SDDPercent")

                } else {
                    LogUtil.d("set_SDDPercent")
                    setSDDPercent(
                        hhqBeginIndex,
                        hhqbean,
                        hhqVeryBeginIndex,
                        curTs,
                        verybeginTS,
                        sddTableNameALL,
                        codelist,
                        codeidnex,
                        sumDDBean,
                        date
                    )


//                    LogUtil.d(
//                        "ddlist:" + ddlist[ddidnex] + ",code:${codelist[codeidnex].code},hhqBeginIndex:$hhqBeginIndex,hhqDate:${getHisHqDay(
//                            hhqbean[hhqBeginIndex]
//                        )}"
//                    )
                }

            }

            LogUtil.d("${codelist[codeidnex].code} operaSddDB tableName:$sddTableNameALL")

        }
    }


    private fun setSDDPercent(
        hhqBeginIndex: Int,
        hhqbean: MutableList<MutableList<String>>,
        hhqVeryBeginIndex: Int,
        curTs: Long,
        verybeginTS: Long,
        sddTableName: String,
        codelist: MutableList<AllCodeGDBean>,
        codeidnex: Int,
        sumDDBean: SumDDBean,
        date: String
    ) {
        if (hhqBeginIndex < hhqbean.size && hhqVeryBeginIndex < hhqbean.size && curTs >= verybeginTS) {
            LogUtil.d("maxMax:${hhqBeginIndex}----------${hhqVeryBeginIndex}")
            LogUtil.d("maxMax:${hhqbean[hhqBeginIndex]}----------${hhqbean[hhqVeryBeginIndex]}")
            val beginClosePrice = getHisHqDayClosePrice(hhqbean[hhqVeryBeginIndex])
            val diffPrices = BigDecimalUtils.sub(
                getHisHqDayClosePrice(hhqbean[hhqBeginIndex]),
                getHisHqDayClosePrice(hhqbean[hhqVeryBeginIndex])
            )
            val percent = BigDecimalUtils.div(
                diffPrices,
                beginClosePrice
            ) * 100
            LogUtil.d("$sddTableName set_SDDPercent,code:${codelist[codeidnex].code},date:$date,beginClosePrice:$beginClosePrice,diffPrices:$diffPrices,percent:${percent}")
            LogUtil.d("$sddTableName set_SDDPercent,hhqBeginIndex:$hhqBeginIndex,cur:${hhqbean[hhqBeginIndex]}\n ,begin:${hhqbean[hhqVeryBeginIndex]}")
            DBUtils.setSDDPercent(
                sddTableName,
                percent,
                codelist[codeidnex].code
            )
            if (percent > sumDDBean.maxPer) {
                DBUtils.setSDDMaxPercent(
                    sddTableName,
                    percent,
                    date,
                    codelist[codeidnex].code
                )
            }
            if (percent < sumDDBean.lowPer) {
                DBUtils.setSDDLowPercent(
                    sddTableName,
                    percent,
                    date,
                    codelist[codeidnex].code
                )
            }
        }
    }

    private fun getDDList(): Pair<ArrayList<String>, ArrayList<String>> {
        var result = false
        var cursor: Cursor? = null
        var ddList = ArrayList<String>()
        var hhqList = ArrayList<String>()
        val curYts = DateUtils.parse(DateUtils.formatToDay(FormatterEnum.YYYY)+"0101", FormatterEnum.YYYYMMDD)
        val curYEndts = DateUtils.parse(DateUtils.formatToDay(FormatterEnum.YYYY)+"1230", FormatterEnum.YYYYMMDD)
        try {
            val sql =
                "select name from Sqlite_master  where type ='table'"

            cursor = DBUtils.db.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                var name = cursor.getString(cursor.getColumnIndex("name"))
                val sqlstr =
                    "select * from $name"
                val mCursor = DBUtils.db.rawQuery(sqlstr, null)
                LogUtil.d("foreachDBTable mCursor:${mCursor.count}")
                if (name.contains("DD_") && !name.contains("SDD_")) {
                    val ts = DateUtils.parse(name.replace("DD_", ""), FormatterEnum.YYYYMMDD)
                    if (ts >= curYts && ts < curYEndts) {
                        ddList.add(name)
                    }
                }
                if (name.contains("HHQ")) {
                    hhqList.add(name)
                }

            }
        } catch (e: Exception) {
            // TODO: handle exception
        } finally {
            cursor?.close()
        }

        Collections.sort(ddList, object : Comparator<String> {
            override fun compare(p0: String, p1: String): Int {
                return DateUtils.parse(
                    p0.replace(Datas.dealDetailTableName, ""),
                    FormatterEnum.YYYYMMDD
                ).compareTo(
                    DateUtils.parse(
                        p1.replace(Datas.dealDetailTableName, ""),
                        FormatterEnum.YYYYMMDD
                    )
                )
            }
        })
        Collections.sort(hhqList, object : Comparator<String> {
            override fun compare(p0: String, p1: String): Int {
                return DateUtils.parse(
                    p0.replace("HHQ_", ""),
                    FormatterEnum.YYYYMMDD
                ).compareTo(
                    DateUtils.parse(
                        p1.replace("HHQ_", ""),
                        FormatterEnum.YYYYMMDD
                    )
                )
            }
        })
        return Pair(ddList, hhqList)

    }


}