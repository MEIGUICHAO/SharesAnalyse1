package com.mgc.sharesanalyse.viewmodel

import android.database.Cursor
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.NewApiActivity
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

    init {
        App.getSinglePool().execute {
            Thread.sleep(300)
            getAllCode()
        }

    }

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
        val upateBean = DaoUtilsStore.getInstance().updateCodeListBeanDaoUtils.queryAll()
        if (upateBean.size < 1) {
            GetCodeIndex = 1
            getAllCodeApi()
        } else {
            val recordDate = upateBean[0].date
            if (recordDate < DateUtils.formatToDay(FormatterEnum.YYYYMM).toInt()) {
                GetCodeIndex = 1
                getAllCodeApi()
            } else {
                (mActivity as NewApiActivity).setAllCodeInfo("code_all_update $recordDate")
                initCodeList()
            }
        }
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
        if (null != bean) {
            LogUtil.d("addCodeToAll size:${bean.size}")
            GetCodeIndex++
            bean.forEach {
                val allCodeGDBean = AllCodeGDBean()
                allCodeGDBean.code = it.code.replace("sh", "").replace("sz", "")
                allCodeGDBean.id = allCodeGDBean.code.toLong()
                allCodeGDBean.name = it.name
                var success = DaoUtilsStore.getInstance().allCodeGDBeanDaoUtils.updateOrInsertById(
                    allCodeGDBean,
                    allCodeGDBean.code.toLong()
                )
            }
            (mActivity as NewApiActivity).setAllCodeInfo("code_all_update---${GetCodeIndex}")
            if (bean.size == 80) {
                getAllCodeApi()
            } else {
                DaoUtilsStore.getInstance().updateCodeListBeanDaoUtils.deleteAll()
                var updateCodeListBean = UpdateCodeListBean()
                updateCodeListBean.date = DateUtils.formatToDay(FormatterEnum.YYYYMM).toInt()
                (mActivity as NewApiActivity).setAllCodeInfo("code_all_update ${updateCodeListBean.date}")
                DaoUtilsStore.getInstance().updateCodeListBeanDaoUtils.insert(updateCodeListBean)
                initCodeList()
            }
        } else {
            DaoUtilsStore.getInstance().updateCodeListBeanDaoUtils.deleteAll()
            var updateCodeListBean = UpdateCodeListBean()
            updateCodeListBean.date = DateUtils.formatToDay(FormatterEnum.YYYYMM).toInt()
            (mActivity as NewApiActivity).setAllCodeInfo("code_all_update ${updateCodeListBean.date}")
            DaoUtilsStore.getInstance().updateCodeListBeanDaoUtils.insert(updateCodeListBean)
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
        (mActivity as NewApiActivity).setBtnDDInfo("code:$code,date:$date")
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
        RetrofitManager.baseUrlInterceptor.referer =
            "${Datas.sinaVipStockUrlRefer}${code.toSinaCode()}"
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

    fun getStartDateByDaysCount(count: Int): String {
        var startValue = ""
        val judeIndex = (DateUtils.formatToDay(FormatterEnum.YYYY) + "00").toInt()
        val toDay = (DateUtils.formatToDay(FormatterEnum.YYYYMM)).toInt()
        val result = toDay - count
        val lastYearBegin =
            (DateUtils.formatToDay(FormatterEnum.YYYY).toInt() - 1).toString() + "12"
        if (result <= judeIndex) {
            startValue = (lastYearBegin.toInt() - (judeIndex - result)).toString() + "01"
        } else {
            startValue =
                (DateUtils.formatToDay(FormatterEnum.YYYYMM)
                    .toInt() - count).toString() + "01"
        }
        return startValue
    }

    //    DateUtils.formatToDay(FormatterEnum.YYYY)+"0501"
    fun getHisHq(
        code: String,
        start: String = getStartDateByDaysCount(Datas.HHQDayCount),
        end: String = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
    ) {
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
    fun getHisHqCurRange(dayDatas: List<String>) = BigDecimalUtils.safeDiv(
        (getHisHqDayHighestPrice(dayDatas) - getHisHqDayLowestPrice(dayDatas)),
        getHisHqDayLowestPrice(dayDatas)
    )

    fun getcurPercentFloat(dayDatas: List<String>) = try {
        dayDatas[4].replace("%", "").toFloat()
    } catch (e: Exception) {
        0.toFloat()
    }

    fun getHisHqDayClosePrice(dayDatas: List<String>) = dayDatas[2].toFloat()
    fun getHisHqDayOpenPrice(dayDatas: List<String>) = dayDatas[1].toFloat()
    fun getHisHqDayLowestPrice(dayDatas: List<String>) = dayDatas[5].toFloat()
    fun getHisHqDayHighestPrice(dayDatas: List<String>) = dayDatas[6].toFloat()
    fun getHisHqDayDealAmount(dayDatas: List<String>) = dayDatas[8].toFloat()
    fun getHisHqDayWholeDealAmount(dayDatas: List<String>) = dayDatas[8].toFloat() * 10000
    fun getHisHqDayDealVolume(dayDatas: List<String>) = dayDatas[7].toFloat() * 100
    fun getHisHqDayTurnRate(dayDatas: List<String>) = dayDatas[9]
    fun getHisHqDayTurnRateFloat(dayDatas: List<String>) = try {
        dayDatas[9].replace("%", "").toFloat()
    } catch (e: Exception) {
        0.toFloat()
    }

    fun getSafeHisHqDayTurnRateFloat(hq: List<MutableList<String>>, hhqBeginIndex: Int) = try {
        if (hhqBeginIndex < hq.size) {
            hq[hhqBeginIndex][9].replace("%", "").toFloat()
        } else {
            0.toFloat()
        }
    } catch (e: Exception) {
        0.toFloat()
    }

    fun getSafeHisHqDayDealAmount(hq: List<MutableList<String>>, hhqBeginIndex: Int) = try {
        if (hhqBeginIndex < hq.size) {
            hq[hhqBeginIndex][8].toFloat() / 10000
        } else {
            0.toFloat()
        }
    } catch (e: Exception) {
        0.toFloat()
    }

    fun getSafeHisHqDayDV(hq: List<MutableList<String>>, hhqBeginIndex: Int) = try {
        if (hhqBeginIndex < hq.size) {
            hq[hhqBeginIndex][7].toFloat() * 100
        } else {
            0.toFloat()
        }
    } catch (e: Exception) {
        0.toFloat()
    }

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
        val hhqList = getDDList().second
        for (i in 0 until hhqList.size - 1) {
            DBUtils.dropTable(hhqList[i])
        }
        (mActivity as NewApiActivity).setBtnHHQInfo("hhq_complete")
        App.getSinglePool().execute {
            DBUtils.switchDBName(Datas.dataNamesDefault)
            getSumDD()
        }

//        sortpriceHisRecordGDBean(list)
//        list.forEach {
//            if (it.id > 600000) {
//                txtname = "sh_"
//            } else if (it.id > 300000) {
//                txtname = "cy_"
//            } else {
//                txtname = "sz_"
//            }
//            FileLogUtil.d("${parentBasePath}${txtname}hishq$pathDate", "\n" + it.result)
//        }

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

        val sddTableNameALL = Datas.sddALL
        val sddTableName = Datas.sdd + DateUtils.formatToDay(FormatterEnum.YYMM)
        val pair = getDDList()
        val ddlist = pair.first
        val hhqlist = pair.second
        var codelist = kotlin.run {
            val list = ArrayList<String>()
            codeNameList.forEach {
                list.add(it.split(splitTag)[0])
            }
            list
        }
        var codeNameList = kotlin.run {
            val list = ArrayList<String>()
            codeNameList.forEach {
                list.add(it.split(splitTag)[1])
            }
            list
        }

//        DBUtils.dropTable("SHDD_2009")
//        DBUtils.dropTable("SHDD_2010")
//        DBUtils.dropTable("SHDD_ALL_2020")
//        var codelist = DaoUtilsStore.getInstance().allCodeGDBeanDaoUtils.queryByQueryBuilder(AllCodeGDBeanDao.Properties.Code.eq(Datas.DEBUG_Code))
        var hhbeansList: ArrayList<(MutableList<(MutableList<String>?)>?)>
        val initHHQbean =
            DBUtils.queryHHqBeanByCode(
                hhqlist[hhqlist.size - 1],
                if (Datas.DEBUG) Datas.DEBUG_Code[0] else 1.toString()
            )
        val hisHqBeans = GsonHelper.parseArray(initHHQbean?.json, HisHqBean::class.java)[0].hq
        if (Datas.DEBUG) {
            hhbeansList = arrayListOf(hisHqBeans)
        } else {

            val initHHQbean1 =
                DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size - 1], 601216.toString())
            val initHHQbean2 =
                DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size - 1], 858.toString())
            val initHHQbean3 =
                DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size - 1], 300059.toString())
            val hisHqBeans1 = GsonHelper.parseArray(initHHQbean1?.json, HisHqBean::class.java)[0].hq
            val hisHqBeans2 = GsonHelper.parseArray(initHHQbean2?.json, HisHqBean::class.java)[0].hq
            val hisHqBeans3 = GsonHelper.parseArray(initHHQbean3?.json, HisHqBean::class.java)[0].hq
            hhbeansList = arrayListOf(hisHqBeans, hisHqBeans1, hisHqBeans2, hisHqBeans3)
        }
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
            DBUtils.querySumDDBeanByCode(sddTableNameALL, codelist[0])
        var sumDateIndexTs = 0.toLong()
        var sumDateBeginIndex = 0
        if (null != sumDDBeginBean) {
            sumDateIndexTs = DateUtils.parse(sumDDBeginBean.sizeBean.date, FormatterEnum.YYYYMMDD)
            for (i in 0 until ddlist.size) {
                val date = ddlist[i].replace(Datas.dealDetailTableName, "")
                val curts = DateUtils.parse(date, FormatterEnum.YYYYMMDD)
                if (sumDateIndexTs >= curts) {
                    sumDateBeginIndex = i
                    LogUtil.d("sumDateBeginIndex:$sumDateBeginIndex,date:$date,indexDate:${sumDDBeginBean.sizeBean.date}")
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

        val curMonthTS = DateUtils.parse(
            DateUtils.formatToDay(FormatterEnum.YYYY_MM) + "-01",
            FormatterEnum.YYYY_MM_DD
        )
        val curYearTS = DateUtils.parse(
            DateUtils.formatToDay(FormatterEnum.YYYY) + "-01-01",
            FormatterEnum.YYYY_MM_DD
        )

        hhbeansList.forEach {

            for (index in 0 until hisHqBeans.size - 1) {

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

//        abondonMethod(
//            sumDateBeginIndex,
//            ddlist,
//            hhqBeginIndex,
//            codelist,
//            curYearTS,
//            curMonthTS,
//            sddTableName,
//            hhqlist,
//            sumDateIndexTs,
//            hhqMonthVeryBeginIndex,
//            sddTableNameALL,
//            hhqVeryBeginIndex,
//            vbts
//        )

        operaSHDDAndCHDD(
            hhqlist,
            hhqVeryBeginIndex,
            codelist,
            codeNameList,
            curYearTS
        )
        //    0日期	1开盘	2收盘	3涨跌额	4涨跌幅	5最低	6最高	7成交量(手)	8成交金额(万)	9换手率

    }

    public fun reverseResult() {
        val (mList, codelist) = getCHDDDateListAndCodeList()
        codelist.forEach { code ->
            mList.forEach {
                val date = "20${it}01"
                (mActivity as NewApiActivity).setBtnReverseInfo("Reverse_$code _${date}")
                getReverseChddBeans(code, date)
            }
        }
    }

    public fun foreachGapResult() {
        val (mList, codelist) = getCHDDDateListAndCodeList()
        codelist.forEach { code ->
            mList.forEach {
                val date = "20${it}01"
                val gapIndex = 0
                updateCodeHDDGapInfo(code, date, gapIndex)
            }
        }
    }

    private fun updateCodeHDDGapInfo(code: String, date: String, gapIndex: Int) {
        (mActivity as NewApiActivity).setBtnGapResultInfo("gap_$code _ $date")
        var dbName = code.toCodeHDD(date, FormatterEnum.YYYYMMDD)
        var chddDDBeanList =
            DBUtils.queryCHDDByTableName("DD_${code.toCompleteCode()}", dbName)
        var mCHDDList = ArrayList<CodeHDDBean>()
        chddDDBeanList?.let {
            for (i in gapIndex until it.size) {
                mCHDDList.add(0, it[i])
                if (i == 0) {
                    getLastMonthList(it, i, code, mCHDDList)
                }
                if (mCHDDList.size > 15) {
                    val gapJsonBean = GapJsonBean()
                    val curDBean = it[i]
                    val lastDBean = mCHDDList[1]
                    val curDMAJ = curDBean.p_MA_J
                    val lastDMAJ = lastDBean.p_MA_J
                    var gglist: ArrayList<GapJsonBean.GG>? = null
                    var bglist: ArrayList<GapJsonBean.GG>? = null
                    LogUtil.d("lastDBean:${GsonHelper.toJson(lastDBean)}")
                    if (null != lastDBean.gaP_J) {
                        if (null != lastDBean.gaP_J.ggList && lastDBean.gaP_J.ggList.size > 0) {
                            gglist = lastDBean.gaP_J.ggList
                            val ggRemoveList = ArrayList<GapJsonBean.GG>()
                            for (i in 0 until gglist.size) {
                                if (curDMAJ.alp < gglist[i].bottomPrice) {
                                    ggRemoveList.add(gglist[i])
                                } else if (curDMAJ.alp < gglist[i].topPrice) {
                                    gglist[i].topPrice = curDMAJ.alp
                                    gglist[i].gap_OldCpRate =
                                        ((gglist[i].topPrice - gglist[i].bottomPrice) / gglist[i].bottomPrice * 100).toKeep2()
                                }
                            }
                            for (i in ggRemoveList.size-1 downTo 0) {
                                gglist.remove(ggRemoveList[i])
                                LogUtil.d("removeAt:${curDBean.date}")
                            }

                        }
                        if (null != lastDBean.gaP_J.bgList && lastDBean.gaP_J.bgList.size > 0) {
                            bglist = lastDBean.gaP_J.bgList
                            val bgRemoveList = ArrayList<GapJsonBean.GG>()
                            for (i in 0 until bglist.size) {
                                if (curDMAJ.amp > bglist[i].topPrice) {
                                    bgRemoveList.add(bglist[i])
                                } else if (curDMAJ.amp > bglist[i].bottomPrice) {
                                    bglist[i].bottomPrice = curDMAJ.amp
                                    bglist[i].gap_OldCpRate =
                                        ((bglist[i].topPrice - bglist[i].bottomPrice) / bglist[i].bottomPrice * 100).toKeep2()
                                }
                            }

                            for (i in bgRemoveList.size-1 downTo 0) {
                                bglist.remove(bgRemoveList[i])
                                LogUtil.d("removeAt bg:${curDBean.date}")
                            }
                        }
                    }
                    if (curDMAJ.alp > lastDMAJ.amp) {
                        LogUtil.d("gap!!GG $code ----- ${curDBean.date} curDMAJ.alp:${curDMAJ.alp} lastDMAJ.amp:${lastDMAJ.amp}")
                        if (null == gglist) {
                            gglist = ArrayList<GapJsonBean.GG>()
                        }
                        val gg = GapJsonBean.GG()
                        gg.bottomPrice = lastDMAJ.amp
                        gg.topPrice = curDMAJ.alp
                        setComonGG(gg, curDMAJ, curDBean, lastDMAJ)
                        gg.gap_OldCpRate =
                            ((curDMAJ.alp - lastDMAJ.amp) / lastDMAJ.amp * 100).toKeep2()
                        gglist.add(0, gg)
                    } else if (curDMAJ.amp < lastDMAJ.alp) {
                        LogUtil.d("gap!!BG $code ----- ${curDBean.date} curDMAJ.amp:${curDMAJ.amp} lastDMAJ.alp:${lastDMAJ.alp}")
                        if (null == bglist) {
                            bglist = ArrayList<GapJsonBean.GG>()
                        }
                        val bg = GapJsonBean.GG()
                        bg.topPrice = curDMAJ.amp
                        bg.bottomPrice = lastDMAJ.alp
                        setComonGG(bg,  curDMAJ, curDBean, lastDMAJ)
                        bg.bottomPrice = lastDMAJ.aacp
                        bg.gap_OldCpRate =
                            ((curDMAJ.amp - lastDMAJ.alp) / lastDMAJ.alp * 100).toKeep2()
                        bglist.add(0, bg)
                    }
                    if (null != gglist && gglist.size > 0) {
                        gapJsonBean.ggList = gglist
                        gapJsonBean.aggs = gglist.size
                    }
                    if (null != bglist && bglist.size > 0) {
                        gapJsonBean.bgList = bglist
                        gapJsonBean.abgs = bglist.size
                    }

                    if ((null != gglist && gglist.size > 0) || (null != bglist && bglist.size > 0)) {
                        curDBean.gaP_J = gapJsonBean

                    } else {
                        curDBean.gaP_J = null
                    }
                    if (null != curDBean.gaP_J && (gapJsonBean.abgs > 0 || gapJsonBean.aggs > 0)) {
                        if (null != curDBean.gaP_J.ggList && curDBean.gaP_J.ggList.size > 0) {
                            curDBean.gaP_J.ggList[0].date = curDBean.date.toInt()
                            curDBean.gaP_J.ggList[0].mlRange =
                                ((curDMAJ.amp - curDMAJ.alp) / curDMAJ.alp * 100).toKeep2()
                            curDBean.gaP_J.ggList[0].coRange =
                                ((curDMAJ.aacp - curDMAJ.aaop) / curDMAJ.aaop * 100).toKeep2()

                        }
                        if (null != curDBean.gaP_J.bgList && curDBean.gaP_J.bgList.size > 0) {
                            curDBean.gaP_J.bgList[0].date = curDBean.date.toInt()
                            curDBean.gaP_J.bgList[0].mlRange =
                                ((curDMAJ.amp - curDMAJ.alp) / curDMAJ.alp * 100).toKeep2()
                            curDBean.gaP_J.bgList[0].coRange =
                                ((curDMAJ.aacp - curDMAJ.aaop) / curDMAJ.aaop * 100).toKeep2()
                        }
                        DBUtils.updateCHDDGapJson(
                            "DD_${code.toCompleteCode()}",
                            code.toCompleteCode(),
                            curDBean
                        )
    //                                var ts = DateUtils.parse(curDBean.date,FormatterEnum.YYYYMMDD)
    //                                while (!DateUtils.isWeekDay(ts).first) {
    //                                    ts = ts + dayMillis
    //                                }

                        mCHDDList.add(0, curDBean)
                    }
                }
            }
        }
    }

    private fun setComonGG(
        gg: GapJsonBean.GG,
        curDMAJ: CodeHDDBean.P_MA_J,
        curDBean: CodeHDDBean,
        lastDMAJ: CodeHDDBean.P_MA_J
    ) {
        gg.b_D = curDBean.date.toInt()
        gg.date = curDBean.date.toInt()
        gg.gapOp = curDMAJ.aaop
        gg.lp = curDMAJ.alp
        gg.mp = curDMAJ.amp
        gg.cp = curDBean.cp
        gg.op = curDMAJ.aaop
        gg.percent = ((curDMAJ.aacp - lastDMAJ.aacp) / lastDMAJ.aacp * 100).toKeep2()
        gg.tr = curDBean.tr.percent2Float()
        gg.gapOp = curDBean.op
    }

    private fun getCHDDDateListAndCodeList(): Pair<ArrayList<String>, ArrayList<String>> {
        val pathList = FileUtil.getFileNameList(Datas.DBPath)
        val mList = ArrayList<String>()
        pathList.forEach {
            if (it.contains("SZ_CHDD_") && !it.contains("journal")) {
                mList.add(it.replace("SZ_CHDD_", ""))
            }
        }
        mList.sortStringDateAsc(FormatterEnum.YYMM)

        val codelist = kotlin.run {
            val list = ArrayList<String>()
            codeNameList.forEach {
                list.add(it.split(splitTag)[0])
            }
            list
        }
        return Pair(mList, codelist)
    }

    private fun abondonMethod(
        sumDateBeginIndex: Int,
        ddlist: ArrayList<String>,
        hhqBeginIndex: Int,
        codelist: ArrayList<String>,
        curYearTS: Long,
        curMonthTS: Long,
        sddTableName: String,
        hhqlist: ArrayList<String>,
        sumDateIndexTs: Long,
        hhqMonthVeryBeginIndex: Int,
        sddTableNameALL: String,
        hhqVeryBeginIndex: Int,
        vbts: Long
    ) {
        var hhqBeginIndex1 = hhqBeginIndex
        for (ddidnex in sumDateBeginIndex until ddlist.size) {
            //        for (ddidnex in sumDateBeginIndex until ddlist.size)  {
            //        for (ddidnex in 0 until 4) {
            val date = ddlist[ddidnex].replace(
                Datas.dealDetailTableName,
                ""
            )

            LogUtil.d("for date:$date ,hhqBeginIndex:$hhqBeginIndex1")
            val curIndexTs = DateUtils.parse(date, FormatterEnum.YYYYMMDD)
            //            for (codeidnex in 0 until 1) {
            for (codeidnex in 0 until codelist.size) {
                (mActivity as NewApiActivity).setBtnSumDDInfo("SDD_${date}_${codelist[codeidnex]}")
                LogUtil.d(
                    "${codelist[codeidnex]}curIndexTs:$curIndexTs,curIndexTs_date:$date,curYearTS:$curYearTS,curYearTS_date:${DateUtils.formatToDay(
                        FormatterEnum.YYYY
                    ) + "-01-01"},curMonthTS:$curMonthTS,curMonthTS_date:${DateUtils.formatToDay(
                        FormatterEnum.YYYY_MM
                    ) + "-01"}"
                )
                LogUtil.d("${codelist[codeidnex]}curIndexTs >= curYearTS:${curIndexTs >= curYearTS},curIndexTs >= curMonthTS:${curIndexTs >= curMonthTS}")
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
                            hhqBeginIndex1,
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
                        hhqBeginIndex1,
                        sumDateIndexTs,
                        hhqVeryBeginIndex,
                        vbts
                    )
                }
            }
            hhqBeginIndex1--
            if (hhqBeginIndex1 < 0) {
                break
            }
        }
    }

    private fun operaSHDDAndCHDD(
        hhqlist: ArrayList<String>,
        hhqVeryBeginIndex: Int,
        codelist: ArrayList<String>,
        namelist: ArrayList<String>,
        curYearTS: Long
    ) {
        //TODO
//        var hhqBeginIndex = hhqVeryBeginIndex
        val allList = DaoUtilsStore.getInstance().allCodeGDBeanDaoUtils.queryAll()
        val allLastCode = allList[allList.size - 1].code

        for (codeidnex in 0 until codelist.size) {
            val code = codelist[codeidnex]
            val bean =
                DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size - 1], code)
            val hisHqBean = GsonHelper.parseArray(bean?.json, HisHqBean::class.java)
            var sumProgessRecordBean = DBUtils.querySumProgressRecordByCode(code)
            var cHDDProgressBean: SumProgessRecordBean.BaseRecordBean? = null
            hisHqBean?.let {
                try {
                    LogUtil.d("${hisHqBean[0].hq.size}")
                } catch (e: Exception) {
                    return@let
                }
                for (ddidnex in 0 until hisHqBean[0].hq.size) {
                    //        for (ddidnex in 0 until 4) {
                    val date = getHisHqDay(hisHqBean[0].hq[ddidnex]).replace("-", "")
                    if (null != sumProgessRecordBean) {
                        if (sumProgessRecordBean!!.getcHDDProgressBean().date >= date.toInt()) {
                            break
                        }
                        cHDDProgressBean = sumProgessRecordBean!!.getcHDDProgressBean()
                    } else {
                        sumProgessRecordBean = SumProgessRecordBean()
                        sumProgessRecordBean!!.code = code.toInt()
                    }
                    if (null == cHDDProgressBean) {
                        cHDDProgressBean = SumProgessRecordBean.BaseRecordBean()
                        sumProgessRecordBean!!.setcHDDProgressBean(cHDDProgressBean)
                    }
                    cHDDProgressBean!!.date = date.toInt()

                    DBUtils.switchDBName(allLastCode.toCodeHDD(date, FormatterEnum.YYYYMMDD))
                    val date1 = DBUtils.queryDateInCodeDDByDbName("${Datas.CHDD}$allLastCode")
                    val date2 = DBUtils.queryDateInSDDDByDbName(
                        Datas.shdd + DateUtils.changeFormatter(
                            DateUtils.parse(
                                date,
                                FormatterEnum.YYYYMMDD
                            ), FormatterEnum.YYMM
                        ), allLastCode
                    )
                    val date3 = DBUtils.queryDateInSDDDByDbName(
                        Datas.shddAllTag + DateUtils.changeFormatter(
                            DateUtils.parse(
                                date,
                                FormatterEnum.YYYYMMDD
                            ),
                            FormatterEnum.YYYY
                        ), allLastCode
                    )
                    LogUtil.d("date:$date,date1:$date1,date2:$date2,date3:$date3")
                    if (!date1.isEmpty() && !date2.isEmpty() && !date3.isEmpty()) {
                        if (DateUtils.parse(date, FormatterEnum.YYYYMMDD) <= DateUtils.parse(
                                date1,
                                FormatterEnum.YYYYMMDD
                            ) &&
                            DateUtils.parse(date, FormatterEnum.YYYYMMDD) <= DateUtils.parse(
                                date2,
                                FormatterEnum.YYYYMMDD
                            ) &&
                            DateUtils.parse(date, FormatterEnum.YYYYMMDD) <= DateUtils.parse(
                                date3,
                                FormatterEnum.YYYYMMDD
                            )
                        ) {
                            continue
                        }
                    }
                    val curIndexTs = DateUtils.parse(date, FormatterEnum.YYYYMMDD)

                    val hhqbean = hisHqBean[0].hq[ddidnex]

                    //TODO test
                    if (curIndexTs >= curYearTS) {
                        if (date1.isEmpty() ||
                            DateUtils.parse(date, FormatterEnum.YYYYMMDD) >= DateUtils.parse(
                                date1,
                                FormatterEnum.YYYYMMDD
                            )
                        ) {
                            insertOrUpdateCodeHDD(
                                hisHqBean[0].hq,
                                ddidnex,
                                code,
                                namelist[codeidnex],
                                date
                            )
                            (mActivity as NewApiActivity).setBtnSumDDInfo("CODE_DD_${date}_${code}")
                        }
                        if ((date2.isEmpty() || date3.isEmpty()) || (DateUtils.parse(
                                date,
                                FormatterEnum.YYYYMMDD
                            ) >= DateUtils.parse(
                                date3,
                                FormatterEnum.YYYYMMDD
                            ) &&
                                    DateUtils.parse(
                                        date,
                                        FormatterEnum.YYYYMMDD
                                    ) >= DateUtils.parse(
                                date2,
                                FormatterEnum.YYYYMMDD
                            ))
                        ) {

                            val curMonthSHHDTBName =
                                Datas.shdd + DateUtils.changeFormatter(
                                    DateUtils.parse(
                                        date,
                                        FormatterEnum.YYYYMMDD
                                    ), FormatterEnum.YYMM
                                )
                            operaSHDDMonth(
                                curMonthSHHDTBName,
                                codelist,
                                codeidnex,
                                date,
                                hhqbean,
                                false, code, namelist[codeidnex]
                            )

                            (mActivity as NewApiActivity).setBtnSumDDInfo("SHDD_${date}_${code}")
                            operaSHDDMonth(
                                Datas.shddAll,
                                codelist,
                                codeidnex,
                                date,
                                hhqbean,
                                true, code, namelist[codeidnex]
                            )
                        }
                    }
                    DBUtils.insertOrUpdateSumProgressRecordTable(sumProgessRecordBean!!)
                }
            }
        }

    }

    private fun operaSHDDMonth(
        curMonthSHHDTBName: String,
        codelist: ArrayList<String>,
        codeidnex: Int,
        date: String,
        hhqbean: MutableList<String>,
        isAll: Boolean, code: String, name: String
    ) {
        val shddBean =
            DBUtils.querySHDDByCode(curMonthSHHDTBName, codelist[codeidnex])

        if (null == shddBean) {
            insertSHDDBean(curMonthSHHDTBName, hhqbean, date, code, name)
        } else {
            updateSHDDBean(
                curMonthSHHDTBName,
                hhqbean,
                date,
                isAll,
                DateUtils.changeFormatter(
                    DateUtils.parse(date, FormatterEnum.YYYYMMDD),
                    FormatterEnum.YYMM
                ), code, name
            )
        }
    }

    private fun updateSHDDBean(
        tbName: String,
        hhqbean: List<String>,
        date: String,
        isAll: Boolean,
        shddDateYM: String, code: String, name: String
    ) {
        val lastSHDDBean = DBUtils.querySHDDByCode(tbName, code)

        val curSHDDBean = SHDDBean()
        curSHDDBean.c = code
        curSHDDBean.n = name
        val op = DBUtils.queryOPByCHDD(code, isAll, shddDateYM)
        if (null == lastSHDDBean) {
            curSHDDBean.date = date
            curSHDDBean.aup = getcurPercentFloat(hhqbean)


            curSHDDBean.lper = curSHDDBean.aup
            curSHDDBean.lpd = date
            curSHDDBean.mpd = date
            curSHDDBean.mp = getHisHqDayClosePrice(hhqbean)
            curSHDDBean.lper = curSHDDBean.aup
            curSHDDBean.lpd = date
            curSHDDBean.lp = getHisHqDayClosePrice(hhqbean)

            curSHDDBean.autr = getHisHqDayTurnRateFloat(hhqbean)
            curSHDDBean.av = getAvValue(getHisHqDayDealVolume(hhqbean))

            curSHDDBean.ad = getHisHqDayWholeDealAmount(hhqbean) / Datas.NUM_100M


            curSHDDBean.pp = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.ad, curSHDDBean.av)
            curSHDDBean.pP100 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD100, curSHDDBean.aV100)
            curSHDDBean.pP50 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD50, curSHDDBean.aV50)
            curSHDDBean.pP30 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD30, curSHDDBean.aV30)
            curSHDDBean.pP10 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD10, curSHDDBean.aV10)
            curSHDDBean.pP5 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD5, curSHDDBean.aV5)
            curSHDDBean.pP1 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD1, curSHDDBean.aV1)
            curSHDDBean.avj = getAvJson(curSHDDBean)
            LogUtil.d("shddBean.pp:${curSHDDBean.pp}")
            LogUtil.d("shddBean.ad:${curSHDDBean.ad}")
            LogUtil.d("shddBean.av:${curSHDDBean.av}")
            DBUtils.insertOrUpdateSHDDTable(tbName, date, curSHDDBean)
        } else {
            curSHDDBean.date = lastSHDDBean.date
            if (date != lastSHDDBean.date) {
                if (op > 0) {
                    val diffP = BigDecimalUtils.sub(getHisHqDayClosePrice(hhqbean), op)
                    curSHDDBean.aup = (BigDecimalUtils.safeDiv(diffP, op) * 100)
                    LogUtil.d(
                        "$tbName $isAll updateSHDDBean----,date:$date,diffP:$diffP,op:$op,cp:${getHisHqDayClosePrice(
                            hhqbean
                        )},aup:${curSHDDBean.aup},YM:$shddDateYM,lastSHDDBean.mper:${lastSHDDBean.mper},${curSHDDBean.aup > lastSHDDBean.mper}"
                    )
                }

                curSHDDBean.lper = lastSHDDBean.lper
                curSHDDBean.lpd = lastSHDDBean.lpd
                curSHDDBean.mpd = lastSHDDBean.mpd
                curSHDDBean.mper = lastSHDDBean.mper
                curSHDDBean.mp = lastSHDDBean.mp
                curSHDDBean.lp = lastSHDDBean.lp
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
//                curSHDDBean.av = lastSHDDBean.av + getAvValue(getHisHqDayDealVolume(hhqbean))
                LogUtil.d(
                    "${tbName}curSHDDBean.autr ${date} :${curSHDDBean.autr},lastSHDDBean.autr:${lastSHDDBean.autr},autr:${getHisHqDayTurnRateFloat(
                        hhqbean
                    )}"
                )
                curSHDDBean.ad =
                    lastSHDDBean.ad + getHisHqDayWholeDealAmount(hhqbean) / Datas.NUM_100M

                LogUtil.d(
                    "${tbName}curSHDDBean.ad ${date} :${curSHDDBean.ad},lastSHDDBean.ad:${lastSHDDBean.ad},ad:${getHisHqDayWholeDealAmount(
                        hhqbean
                    ) / Datas.NUM_100M}"
                )

                curSHDDBean.pp = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.ad, curSHDDBean.av)
                curSHDDBean.pP100 =
                    BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD100, curSHDDBean.aV100)
                curSHDDBean.pP50 =
                    BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD50, curSHDDBean.aV50)
                curSHDDBean.pP30 =
                    BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD30, curSHDDBean.aV30)
                curSHDDBean.pP10 =
                    BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD10, curSHDDBean.aV10)
                curSHDDBean.pP5 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD5, curSHDDBean.aV5)
                curSHDDBean.pP1 = BigDecimalUtils.getPPBySafeDiv(curSHDDBean.aD1, curSHDDBean.aV1)
                curSHDDBean.avj = getAvJson(curSHDDBean)

                LogUtil.d("shddBean.pp:${curSHDDBean.pp}")
                LogUtil.d("shddBean.ad:${curSHDDBean.ad}")
                LogUtil.d("shddBean.av:${curSHDDBean.av}")
                DBUtils.insertOrUpdateSHDDTable(tbName, date, curSHDDBean)
            }

        }


    }

    private fun insertSHDDBean(
        tbName: String,
        hhqbean: List<String>,
        date: String, code: String, name: String
    ) {
        val shddBean = SHDDBean()
        shddBean.c = code
        shddBean.n = name
        shddBean.mpd = date
        shddBean.lpd = date
        shddBean.date = date
        shddBean.aup = getcurPercentFloat(hhqbean)
        shddBean.mper = getcurPercentFloat(hhqbean)
        shddBean.lper = getcurPercentFloat(hhqbean)
        shddBean.mp = getHisHqDayClosePrice(hhqbean)
        shddBean.lp = getHisHqDayClosePrice(hhqbean)
        shddBean.av = getAvValue(getHisHqDayDealVolume(hhqbean))
        shddBean.ad = getHisHqDayWholeDealAmount(hhqbean) / Datas.NUM_100M
        shddBean.autr = getHisHqDayTurnRateFloat(hhqbean)

        shddBean.pp = BigDecimalUtils.getPPBySafeDiv(shddBean.ad, shddBean.av)
        shddBean.pP100 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD100, shddBean.aV100)
        shddBean.pP50 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD50, shddBean.aV50)
        shddBean.pP30 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD30, shddBean.aV30)
        shddBean.pP10 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD10, shddBean.aV10)
        shddBean.pP5 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD5, shddBean.aV5)
        shddBean.pP1 = BigDecimalUtils.getPPBySafeDiv(shddBean.aD1, shddBean.aV1)
        shddBean.avj = getAvJson(shddBean)

        LogUtil.d("shddBean.pp:${shddBean.pp}")
        LogUtil.d("shddBean.ad:${shddBean.ad}")
        LogUtil.d("shddBean.av:${shddBean.av}")

        DBUtils.insertOrUpdateSHDDTable(tbName, date, shddBean)

    }

    private fun getAvJson(shddBean: SHDDBean): String {
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

    private fun getAdValue(value: Float) =
        String.format("%.4f", value * 10000 / Datas.NUM_100M).toFloat()

    private fun insertOrUpdateCodeHDD(
        hq: List<MutableList<String>>,
        hhqBeginIndex: Int,
        code: String,
        name: String,
        date: String
    ) {
        val hhqbean = hq[hhqBeginIndex]
        DBUtils.switchDBName(code.toCodeHDD(date, FormatterEnum.YYYYMMDD))
        val tbName = Datas.CHDD + code
        LogUtil.d("tbName:$tbName")
        val codeHDDBean = CodeHDDBean()
        codeHDDBean.name = name
        codeHDDBean.p_autr_j = CodeHDDBean.P_AUTR_J()
        if (hq.size > hhqBeginIndex + 2) {
            codeHDDBean.p_autr_j.d03 =
                getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex) + getSafeHisHqDayTurnRateFloat(
                    hq,
                    hhqBeginIndex + 1
                ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 2)
        }
        if (hq.size > hhqBeginIndex + 4) {
            codeHDDBean.p_autr_j.d05 = codeHDDBean.p_autr_j.d03 + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 3
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 4)
        }
        if (hq.size > hhqBeginIndex + 9) {
            codeHDDBean.p_autr_j.d10 = codeHDDBean.p_autr_j.d05 + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 5
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 6) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 7
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 8) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 9
            )
        }
        if (hq.size > hhqBeginIndex + 14) {
            codeHDDBean.p_autr_j.d15 = codeHDDBean.p_autr_j.d10 + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 10
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 11) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 12
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 13) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 14
            )
        }
        if (hq.size > hhqBeginIndex + 19) {
            codeHDDBean.p_autr_j.d20 = codeHDDBean.p_autr_j.d15 + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 15
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 16) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 17
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 18) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 19
            )
        }
        if (hq.size > hhqBeginIndex + 24) {
            codeHDDBean.p_autr_j.d25 = codeHDDBean.p_autr_j.d20 + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 20
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 21) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 22
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 23) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 24
            )
        }

        if (hq.size > hhqBeginIndex + 29) {
            codeHDDBean.p_autr_j.d30 = codeHDDBean.p_autr_j.d25 + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 25
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 26) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 27
            ) + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + 28) + getSafeHisHqDayTurnRateFloat(
                hq,
                hhqBeginIndex + 29
            )
        }

        if (hq.size > hhqBeginIndex + 59) {
            codeHDDBean.p_autr_j.d60 = kotlin.run {
                var daNum = 0.toFloat()
                for (i in 30 until 60) {
                    daNum = daNum + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + i)
                }
                codeHDDBean.p_autr_j.d30 + daNum
            }
        }
        if (hq.size > hhqBeginIndex + 71) {
            codeHDDBean.p_autr_j.d72 = kotlin.run {
                var daNum = 0.toFloat()
                for (i in 60 until 72) {
                    daNum = daNum + getSafeHisHqDayTurnRateFloat(hq, hhqBeginIndex + i)
                }
                codeHDDBean.p_autr_j.d60 + daNum
            }
        }


        codeHDDBean.p_DA_J = CodeHDDBean.P_DA_J()
        codeHDDBean.p_DV_J = CodeHDDBean.P_DV_J()

        if (hq.size > hhqBeginIndex + 3) {
            codeHDDBean.p_DV_J.p_3d_DA =
                getSafeHisHqDayDV(hq, hhqBeginIndex + 1) +
                        getSafeHisHqDayDV(hq, hhqBeginIndex + 2) +
                        getSafeHisHqDayDV(hq, hhqBeginIndex + 3)
        }
        if (hq.size > hhqBeginIndex + 5) {
            codeHDDBean.p_DV_J.p_5d_DA = codeHDDBean.p_DV_J.p_3d_DA +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 4) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 5)
        }
        if (hq.size > hhqBeginIndex + 10) {
            codeHDDBean.p_DV_J.p_10d_DA = codeHDDBean.p_DV_J.p_5d_DA +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 6) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 7) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 8) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 9) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 10)
        }
        if (hq.size > hhqBeginIndex + 15) {
            codeHDDBean.p_DV_J.p_15d_DA = codeHDDBean.p_DV_J.p_10d_DA +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 11) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 12) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 13) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 14) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 15)
        }
        if (hq.size > hhqBeginIndex + 20) {
            codeHDDBean.p_DV_J.p_20d_DA = codeHDDBean.p_DV_J.p_15d_DA +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 16) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 17) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 18) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 19) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 20)
        }
        if (hq.size > hhqBeginIndex + 25) {
            codeHDDBean.p_DV_J.p_25d_DA = codeHDDBean.p_DV_J.p_20d_DA +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 21) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 22) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 23) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 24) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 25)
        }
        if (hq.size > hhqBeginIndex + 30) {
            codeHDDBean.p_DV_J.p_30d_DA = codeHDDBean.p_DV_J.p_25d_DA +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 26) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 27) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 28) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 29) +
                    getSafeHisHqDayDV(hq, hhqBeginIndex + 30)
        }

        if (hq.size > hhqBeginIndex + 60) {
            codeHDDBean.p_DV_J.p_60d_DA = kotlin.run {
                var daNum = 0.toFloat()
                for (i in 31 until 61) {
                    daNum = daNum + getSafeHisHqDayDV(hq, hhqBeginIndex + i)
                }
                codeHDDBean.p_DV_J.p_30d_DA + daNum
            }
        }

        if (hq.size > hhqBeginIndex + 72) {
            codeHDDBean.p_DV_J.p_72d_DA = kotlin.run {
                var daNum = 0.toFloat()
                for (i in 61 until 73) {
                    daNum = daNum + getSafeHisHqDayDV(hq, hhqBeginIndex + i)
                }
                codeHDDBean.p_DV_J.p_60d_DA + daNum
            }
        }

        if (hq.size > hhqBeginIndex + 3) {
            codeHDDBean.p_DA_J.d03 =
                getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 1) +
                        getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 2) +
                        getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 3)
        }
        if (hq.size > hhqBeginIndex + 5) {
            codeHDDBean.p_DA_J.d05 = codeHDDBean.p_DA_J.d03 +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 4) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 5)
        }

        if (hq.size > hhqBeginIndex + 10) {
            codeHDDBean.p_DA_J.d10 = codeHDDBean.p_DA_J.d05 +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 6) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 7) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 8) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 9)
            getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 10)
        }
        if (hq.size > hhqBeginIndex + 15) {
            codeHDDBean.p_DA_J.d15 = codeHDDBean.p_DA_J.d10 +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 11) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 12) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 13) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 14) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 15)
        }
        if (hq.size > hhqBeginIndex + 20) {
            codeHDDBean.p_DA_J.d20 = codeHDDBean.p_DA_J.d15 +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 16) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 17) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 18) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 19) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 20)
        }
        if (hq.size > hhqBeginIndex + 25) {
            codeHDDBean.p_DA_J.d25 = codeHDDBean.p_DA_J.d20 +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 21) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 22) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 23) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 24) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 25)
        }
        if (hq.size > hhqBeginIndex + 30) {
            codeHDDBean.p_DA_J.d30 = codeHDDBean.p_DA_J.d25 +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 26) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 27) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 28) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 29) +
                    getSafeHisHqDayDealAmount(hq, hhqBeginIndex + 30)
        }

        if (hq.size > hhqBeginIndex + 60) {
            codeHDDBean.p_DA_J.d60 = kotlin.run {
                var daNum = 0.toFloat()
                for (i in 31 until 61) {
                    daNum = daNum + getSafeHisHqDayDealAmount(hq, hhqBeginIndex + i)
                }
                codeHDDBean.p_DA_J.d30 + daNum
            }
        }

        if (hq.size > hhqBeginIndex + 72) {
            codeHDDBean.p_DA_J.d72 = kotlin.run {
                var daNum = 0.toFloat()
                for (i in 61 until 73) {
                    daNum = daNum + getSafeHisHqDayDealAmount(hq, hhqBeginIndex + i)
                }
                codeHDDBean.p_DA_J.d60 + daNum
            }
        }


        codeHDDBean.p_PP_J = CodeHDDBean.P_PP_J()
        codeHDDBean.p_PP_J.aacp = getHisHqDayClosePrice(hhqbean)
        codeHDDBean.p_PP_J.aaop = getHisHqDayOpenPrice(hhqbean)
        codeHDDBean.p_PP_J.alp = getHisHqDayLowestPrice(hhqbean)
        codeHDDBean.p_PP_J.amp = getHisHqDayHighestPrice(hhqbean)
        codeHDDBean.p_PP_J.d03 = BigDecimalUtils.getPPBySafeDiv(
            codeHDDBean.p_DA_J.d03,
            codeHDDBean.p_DV_J.p_3d_DA / Datas.NUM_WAN
        )
        codeHDDBean.p_PP_J.d05 = BigDecimalUtils.getPPBySafeDiv(
            codeHDDBean.p_DA_J.d05,
            codeHDDBean.p_DV_J.p_5d_DA / Datas.NUM_WAN
        )
        codeHDDBean.p_PP_J.d10 = BigDecimalUtils.getPPBySafeDiv(
            codeHDDBean.p_DA_J.d10,
            codeHDDBean.p_DV_J.p_10d_DA / Datas.NUM_WAN
        )
        codeHDDBean.p_PP_J.d15 = BigDecimalUtils.getPPBySafeDiv(
            codeHDDBean.p_DA_J.d15,
            codeHDDBean.p_DV_J.p_15d_DA / Datas.NUM_WAN
        )
        codeHDDBean.p_PP_J.d20 = BigDecimalUtils.getPPBySafeDiv(
            codeHDDBean.p_DA_J.d20,
            codeHDDBean.p_DV_J.p_20d_DA / Datas.NUM_WAN
        )
        codeHDDBean.p_PP_J.d25 = BigDecimalUtils.getPPBySafeDiv(
            codeHDDBean.p_DA_J.d25,
            codeHDDBean.p_DV_J.p_25d_DA / Datas.NUM_WAN
        )
        codeHDDBean.p_PP_J.d30 = BigDecimalUtils.getPPBySafeDiv(
            codeHDDBean.p_DA_J.d30,
            codeHDDBean.p_DV_J.p_30d_DA / Datas.NUM_WAN
        )
        codeHDDBean.p_PP_J.d60 = BigDecimalUtils.getPPBySafeDiv(
            codeHDDBean.p_DA_J.d60,
            codeHDDBean.p_DV_J.p_60d_DA / Datas.NUM_WAN
        )
        codeHDDBean.p_PP_J.d72 = BigDecimalUtils.getPPBySafeDiv(
            codeHDDBean.p_DA_J.d72,
            codeHDDBean.p_DV_J.p_72d_DA / Datas.NUM_WAN
        )

        codeHDDBean.p_MA_J = CodeHDDBean.P_MA_J()
        codeHDDBean.p_MA_J.aacp = getHisHqDayClosePrice(hhqbean)
        codeHDDBean.p_MA_J.aaop = getHisHqDayOpenPrice(hhqbean)
        codeHDDBean.p_MA_J.alp = getHisHqDayLowestPrice(hhqbean)
        codeHDDBean.p_MA_J.amp = getHisHqDayHighestPrice(hhqbean)
        var beginPrice = 0.toFloat()
        if (hq.size > hhqBeginIndex + 3) {
            codeHDDBean.p_MA_J.d03 = kotlin.run {
                for (i in 1 until 4) {
                    beginPrice = beginPrice + getHisHqDayClosePrice(hq[hhqBeginIndex + i])
                }
                beginPrice / 3
            }
        }
        if (hq.size > hhqBeginIndex + 5) {
            codeHDDBean.p_MA_J.d05 = kotlin.run {
                for (i in 4 until 6) {
                    beginPrice = beginPrice + getHisHqDayClosePrice(hq[hhqBeginIndex + i])
                }
                beginPrice / 5
            }
        }
        if (hq.size > hhqBeginIndex + 10) {
            codeHDDBean.p_MA_J.d10 = kotlin.run {
                for (i in 6 until 11) {
                    beginPrice = beginPrice + getHisHqDayClosePrice(hq[hhqBeginIndex + i])
                }
                beginPrice / 10
            }
        }
        if (hq.size > hhqBeginIndex + 15) {
            codeHDDBean.p_MA_J.d15 = kotlin.run {
                for (i in 11 until 16) {
                    beginPrice = beginPrice + getHisHqDayClosePrice(hq[hhqBeginIndex + i])
                }
                beginPrice / 15
            }
        }
        if (hq.size > hhqBeginIndex + 20) {
            codeHDDBean.p_MA_J.d20 = kotlin.run {
                for (i in 16 until 21) {
                    beginPrice = beginPrice + getHisHqDayClosePrice(hq[hhqBeginIndex + i])
                }
                beginPrice / 20
            }
        }
        if (hq.size > hhqBeginIndex + 25) {
            codeHDDBean.p_MA_J.d25 = kotlin.run {
                for (i in 21 until 26) {
                    beginPrice = beginPrice + getHisHqDayClosePrice(hq[hhqBeginIndex + i])
                }
                beginPrice / 25
            }
        }
        if (hq.size > hhqBeginIndex + 30) {
            codeHDDBean.p_MA_J.d30 = kotlin.run {
                for (i in 26 until 31) {
                    beginPrice = beginPrice + getHisHqDayClosePrice(hq[hhqBeginIndex + i])
                }
                beginPrice / 30
            }
        }
        if (hq.size > hhqBeginIndex + 60) {
            codeHDDBean.p_MA_J.d60 = kotlin.run {
                for (i in 31 until 61) {
                    beginPrice = beginPrice + getHisHqDayClosePrice(hq[hhqBeginIndex + i])
                }
                beginPrice / 60
            }
        }
        if (hq.size > hhqBeginIndex + 72) {
            codeHDDBean.p_MA_J.d72 = kotlin.run {
                for (i in 61 until 73) {
                    beginPrice = beginPrice + getHisHqDayClosePrice(hq[hhqBeginIndex + i])
                }
                beginPrice / 72
            }
        }



        codeHDDBean.date = date
        codeHDDBean.op = getHisHqDayOpenPrice(hhqbean)
        codeHDDBean.cp = getHisHqDayClosePrice(hhqbean)
        codeHDDBean.pp = BigDecimalUtils.div(
            getHisHqDayWholeDealAmount(hhqbean),
            getHisHqDayDealVolume(hhqbean)
        )
        codeHDDBean.p = getcurPercentFloat(hhqbean)
        codeHDDBean.tr = getHisHqDayTurnRate(hhqbean)
        val beginOP = DBUtils.queryFirstOPByCodeHDD(tbName)
        if (beginOP > 0) {
            val diff = BigDecimalUtils.sub(getHisHqDayClosePrice(hhqbean), beginOP)
            codeHDDBean.aup = BigDecimalUtils.div(diff, beginOP) * 100
        }
        operaNewCodeHDD(codeHDDBean, hq, hhqBeginIndex, code, date)
        DBUtils.insertCodeHDD(tbName, codeHDDBean, code, date)

        var dbName = code.toCodeHDD(date, FormatterEnum.YYYYMMDD)
        var chddDDBeanList =
            DBUtils.queryCHDDByTableName("DD_${code.toCompleteCode()}", dbName)
        chddDDBeanList?.let {
            updateCodeHDDGapInfo(code, date, it.size-1)
        }
    }

    private fun operaNewCodeHDD(
        codeHDDBean: CodeHDDBean,
        hq: List<MutableList<String>>,
        hhqBeginIndex: Int,
        code: String,
        date: String
    ) {
        operaKJ(codeHDDBean, hq, hhqBeginIndex, code, date)
    }

    private fun operaKJ(
        codeHDDBean: CodeHDDBean,
        hq: List<MutableList<String>>,
        hhqBeginIndex: Int,
        code: String,
        date: String
    ) {
        codeHDDBean.k_J = KJsonBean()
        codeHDDBean.k_J.slc = KJsonBean.SLC()
        codeHDDBean.k_J.slc.uslc = KJsonBean.USLC()
        codeHDDBean.k_J.slc.dslc = KJsonBean.DSLC()
        codeHDDBean.k_J.sll = KJsonBean.SLL()
        codeHDDBean.k_J.sll.usll = KJsonBean.USLL()
        codeHDDBean.k_J.sll.dsll = KJsonBean.DSLL()
        codeHDDBean.k_J.ctc = KJsonBean.CTC()
        codeHDDBean.k_J.ctc.ctC_U = KJsonBean.CTC_U()
        codeHDDBean.k_J.ctc.ctC_D = KJsonBean.CTC_D()
        var beginUSLC = 0
        var beginDSLC = 0
        var beginUSLLength = 0.toFloat()
        var beginDSLLength = 0.toFloat()

        var chddDDBeanList = getChddBeanList(code.toCompleteCode(), date)
        if (null != chddDDBeanList && chddDDBeanList.size > 0) {
            val curPercent = getcurPercentFloat(hq[hhqBeginIndex])
            val yestPercent = chddDDBeanList[chddDDBeanList.size - 1].p
            val cp = getHisHqDayClosePrice(hq[hhqBeginIndex])
            val oldCp = chddDDBeanList[chddDDBeanList.size - 1].cp
            val op = getHisHqDayOpenPrice(hq[hhqBeginIndex])
            val oldOp = chddDDBeanList[chddDDBeanList.size - 1].op
            val mp = getHisHqDayHighestPrice(hq[hhqBeginIndex])
            val oldMp = chddDDBeanList[chddDDBeanList.size - 1].p_MA_J.amp
            val lp = getHisHqDayLowestPrice(hq[hhqBeginIndex])
            val oldLp = chddDDBeanList[chddDDBeanList.size - 1].p_MA_J.alp
            val oldDate = chddDDBeanList[chddDDBeanList.size - 1].date
            val tr = getHisHqDayTurnRateFloat(hq[hhqBeginIndex])
            val curRange = getHisHqCurRange(hq[hhqBeginIndex])
            val baseType = KJsonBean.BaseType()
            baseType.date = getHisHqDay(hq[hhqBeginIndex]).replace("-", "")
            baseType.tr = tr
            baseType.op = op
            baseType.cp = cp
            baseType.mp = mp
            baseType.lp = lp
            baseType.range = curRange
            var limitPercent = 7.5
            if (code.toInt() > 300000 && code.toInt() < 600000) {
                limitPercent = (limitPercent * 2)
            }
            var filterCodeHDDBean: FilterCodeHDDBean? = null
            if (cp > op && curPercent > 0 && yestPercent < 0 && !(codeHDDBean.p_MA_J.d10 > codeHDDBean.p_MA_J.d20 && codeHDDBean.p_MA_J.d20 > codeHDDBean.p_MA_J.d30) && !(codeHDDBean.p_MA_J.d10 < codeHDDBean.p_MA_J.d20 && codeHDDBean.p_MA_J.d20 < codeHDDBean.p_MA_J.d30)) {
                if (oldMp < cp && oldOp < cp && oldCp > op && oldLp > op && mp <= 1.02 * cp) {
                    filterCodeHDDBean = FilterCodeHDDBean()
                    filterCodeHDDBean.kJ_CTC_TYPE = 11
                    codeHDDBean.k_J.ctc.ctC_U.typeA = baseType
                } else if (oldLp > lp && oldCp < op && oldOp > cp && mp <= 1.02 * cp) {
                    filterCodeHDDBean = FilterCodeHDDBean()
                    filterCodeHDDBean.kJ_CTC_TYPE = 12
                    codeHDDBean.k_J.ctc.ctC_U.typeB = baseType
                } else if (yestPercent < -limitPercent && curPercent > limitPercent) {
                    if ((cp - op) / op >= limitPercent && (oldOp - oldCp) / oldCp >= limitPercent) {
                        filterCodeHDDBean = FilterCodeHDDBean()
                        filterCodeHDDBean.kJ_CTC_TYPE = 13
                        codeHDDBean.k_J.ctc.ctC_U.typeC = baseType
                    }
                }
            } else if (cp < op && curPercent < 0 && yestPercent > 0) {
                if (oldMp < op && oldCp < op && oldOp > cp && oldLp > cp) {
                    filterCodeHDDBean = FilterCodeHDDBean()
                    filterCodeHDDBean.kJ_CTC_TYPE = 21
                    codeHDDBean.k_J.ctc.ctC_D.typeA = baseType
                } else if (oldMp < mp && oldOp < cp && oldCp > op) {
                    filterCodeHDDBean = FilterCodeHDDBean()
                    filterCodeHDDBean.kJ_CTC_TYPE = 22
                    codeHDDBean.k_J.ctc.ctC_D.typeB = baseType
                } else if (curPercent < -limitPercent && yestPercent > limitPercent) {
                    if ((oldCp - oldOp) / oldOp >= limitPercent && (op - cp) / cp >= limitPercent) {
                        filterCodeHDDBean = FilterCodeHDDBean()
                        filterCodeHDDBean.kJ_CTC_TYPE = 23
                        codeHDDBean.k_J.ctc.ctC_D.typeC = baseType
                    }
                }
            }
            filterCodeHDDBean?.let {
                LogUtil.d(
                    "BaseType_old_${code}_${date}_${filterCodeHDDBean.kJ_CTC_TYPE}---> \n${GsonHelper.getInstance()
                        .toJson(chddDDBeanList[chddDDBeanList.size - 1])}"
                )
                LogUtil.d(
                    "BaseType_${code}_${date}_${filterCodeHDDBean.kJ_CTC_TYPE}--> \n${GsonHelper.getInstance()
                        .toJson(baseType)}"
                )
                it.code = code.toInt()
                it.kJ_CTC_J = GsonHelper.toJson(baseType)
                DBUtils.insertOrUpdateFilterCodeHddTable(Datas.FILTER_CODE_TB + date, it, date)
            }
        }

        if (hq.size > hhqBeginIndex + 3) {
            val slPair = kJForeach(
                1,
                4,
                hq,
                hhqBeginIndex,
                beginUSLC,
                beginDSLC,
                beginUSLLength,
                beginDSLLength
            )
            beginUSLC = slPair.first.first
            beginDSLC = slPair.first.second
            beginUSLLength = slPair.second.first
            beginDSLLength = slPair.second.second
            codeHDDBean.k_J.slc.uslc.d03 = beginUSLC
            codeHDDBean.k_J.slc.dslc.d03 = beginDSLC
            codeHDDBean.k_J.sll.usll.d03 = beginUSLLength
            codeHDDBean.k_J.sll.dsll.d03 = beginDSLLength
        }
        if (hq.size > hhqBeginIndex + 5) {
            val slPair = kJForeach(
                4,
                6,
                hq,
                hhqBeginIndex,
                beginUSLC,
                beginDSLC,
                beginUSLLength,
                beginDSLLength
            )
            beginUSLC = slPair.first.first
            beginDSLC = slPair.first.second
            beginUSLLength = slPair.second.first
            beginDSLLength = slPair.second.second
            codeHDDBean.k_J.slc.uslc.d05 = beginUSLC
            codeHDDBean.k_J.slc.dslc.d05 = beginDSLC
            codeHDDBean.k_J.sll.usll.d05 = beginUSLLength
            codeHDDBean.k_J.sll.dsll.d05 = beginDSLLength
        }
        if (hq.size > hhqBeginIndex + 10) {
            val slPair = kJForeach(
                6,
                11,
                hq,
                hhqBeginIndex,
                beginUSLC,
                beginDSLC,
                beginUSLLength,
                beginDSLLength
            )
            beginUSLC = slPair.first.first
            beginDSLC = slPair.first.second
            beginUSLLength = slPair.second.first
            beginDSLLength = slPair.second.second
            codeHDDBean.k_J.slc.uslc.d10 = beginUSLC
            codeHDDBean.k_J.slc.dslc.d10 = beginDSLC
            codeHDDBean.k_J.sll.usll.d10 = beginUSLLength
            codeHDDBean.k_J.sll.dsll.d10 = beginDSLLength
        }
        if (hq.size > hhqBeginIndex + 15) {
            val slPair = kJForeach(
                11,
                16,
                hq,
                hhqBeginIndex,
                beginUSLC,
                beginDSLC,
                beginUSLLength,
                beginDSLLength
            )
            beginUSLC = slPair.first.first
            beginDSLC = slPair.first.second
            beginUSLLength = slPair.second.first
            beginDSLLength = slPair.second.second
            codeHDDBean.k_J.slc.uslc.d15 = beginUSLC
            codeHDDBean.k_J.slc.dslc.d15 = beginDSLC
            codeHDDBean.k_J.sll.usll.d15 = beginUSLLength
            codeHDDBean.k_J.sll.dsll.d15 = beginDSLLength
        }
        if (hq.size > hhqBeginIndex + 20) {
            val slPair = kJForeach(
                16,
                21,
                hq,
                hhqBeginIndex,
                beginUSLC,
                beginDSLC,
                beginUSLLength,
                beginDSLLength
            )
            beginUSLC = slPair.first.first
            beginDSLC = slPair.first.second
            beginUSLLength = slPair.second.first
            beginDSLLength = slPair.second.second
            codeHDDBean.k_J.slc.uslc.d20 = beginUSLC
            codeHDDBean.k_J.slc.dslc.d20 = beginDSLC
            codeHDDBean.k_J.sll.usll.d20 = beginUSLLength
            codeHDDBean.k_J.sll.dsll.d20 = beginDSLLength
        }
        if (hq.size > hhqBeginIndex + 25) {
            val slPair = kJForeach(
                21,
                26,
                hq,
                hhqBeginIndex,
                beginUSLC,
                beginDSLC,
                beginUSLLength,
                beginDSLLength
            )
            beginUSLC = slPair.first.first
            beginDSLC = slPair.first.second
            beginUSLLength = slPair.second.first
            beginDSLLength = slPair.second.second
            codeHDDBean.k_J.slc.uslc.d25 = beginUSLC
            codeHDDBean.k_J.slc.dslc.d25 = beginDSLC
            codeHDDBean.k_J.sll.usll.d25 = beginUSLLength
            codeHDDBean.k_J.sll.dsll.d25 = beginDSLLength
        }
        if (hq.size > hhqBeginIndex + 30) {
            val slPair = kJForeach(
                26,
                31,
                hq,
                hhqBeginIndex,
                beginUSLC,
                beginDSLC,
                beginUSLLength,
                beginDSLLength
            )
            beginUSLC = slPair.first.first
            beginDSLC = slPair.first.second
            beginUSLLength = slPair.second.first
            beginDSLLength = slPair.second.second
            codeHDDBean.k_J.slc.uslc.d30 = beginUSLC
            codeHDDBean.k_J.slc.dslc.d30 = beginDSLC
            codeHDDBean.k_J.sll.usll.d30 = beginUSLLength
            codeHDDBean.k_J.sll.dsll.d30 = beginDSLLength
        }
        if (hq.size > hhqBeginIndex + 60) {
            val slPair = kJForeach(
                31,
                61,
                hq,
                hhqBeginIndex,
                beginUSLC,
                beginDSLC,
                beginUSLLength,
                beginDSLLength
            )
            beginUSLC = slPair.first.first
            beginDSLC = slPair.first.second
            beginUSLLength = slPair.second.first
            beginDSLLength = slPair.second.second
            codeHDDBean.k_J.slc.uslc.d60 = beginUSLC
            codeHDDBean.k_J.slc.dslc.d60 = beginDSLC
            codeHDDBean.k_J.sll.usll.d60 = beginUSLLength
            codeHDDBean.k_J.sll.dsll.d60 = beginDSLLength
        }
        if (hq.size > hhqBeginIndex + 72) {
            val slPair = kJForeach(
                61,
                73,
                hq,
                hhqBeginIndex,
                beginUSLC,
                beginDSLC,
                beginUSLLength,
                beginDSLLength
            )
            beginUSLC = slPair.first.first
            beginDSLC = slPair.first.second
            beginUSLLength = slPair.second.first
            beginDSLLength = slPair.second.second
            codeHDDBean.k_J.slc.uslc.d72 = beginUSLC
            codeHDDBean.k_J.slc.dslc.d72 = beginDSLC
            codeHDDBean.k_J.sll.usll.d72 = beginUSLLength
            codeHDDBean.k_J.sll.dsll.d72 = beginDSLLength
        }
    }

    private fun getChddBeanList(
        code: String,
        date: String
    ): ArrayList<CodeHDDBean>? {
        var dbName = code.toCodeHDD(date, FormatterEnum.YYYYMMDD)
        var chddDDBeanList = DBUtils.queryCHDDByTableName("DD_${code.toCompleteCode()}", dbName)
        if (null == chddDDBeanList || chddDDBeanList.size < 1) {
            chddDDBeanList = getLastMonthChddList(date, code)
        }
        return chddDDBeanList
    }

    private fun getLastMonthChddList(
        date: String,
        code: String
    ): ArrayList<CodeHDDBean>? {
        var dbName: String?
        var month = DateUtils.changeFormatter(
            DateUtils.parse(date, FormatterEnum.YYYYMMDD),
            FormatterEnum.YYMM
        ).toInt() - 1

        if (month == yearlimit) {
            month = yearlimit - 100 + 12
            dbName = code.toCodeHDD(month.toString(), FormatterEnum.YYMM)
        } else {
            dbName = code.toCodeHDD(month.toString(), FormatterEnum.YYMM)
        }
        LogUtil.d("getLastMonthChddList_dbName:$dbName")
        return DBUtils.queryCHDDByTableName("DD_${code.toCompleteCode()}", dbName)
    }

    private fun getReverseChddBeans(
        code: String,
        date: String
    ): ArrayList<CodeHDDBean>? {
        var dbName = code.toCodeHDD(date, FormatterEnum.YYYYMMDD)
        var chddDDBeanList = DBUtils.queryCHDDByTableName("DD_${code.toCompleteCode()}", dbName)
        var mCHDDList = ArrayList<CodeHDDBean>()
        LogUtil.d("dbName:$dbName tb:${"DD_${code.toInt()}"}----size:${chddDDBeanList?.size}")
        chddDDBeanList?.let {
            for (i in 0 until it.size) {
                mCHDDList.add(0, it[i])
                if (i == 0) {
                    getLastTwoMonthList(it, i, code, mCHDDList)
                }

                val requestBean = it[i]
                var oldBean: CodeHDDBean?
                var targetBean: CodeHDDBean?
                if (mCHDDList.size > 6) {
                    targetBean = mCHDDList[5]
                    oldBean = mCHDDList[6]
                    LogUtil.d("code:$code---------------requestBean date:${requestBean.date},targetBean date:${targetBean.date},oldBean date:${oldBean.date}")
                    if (requestBean.date.equals("20200507") || requestBean.date.equals("20200508") || requestBean.date.equals(
                            "20200509"
                        )
                    ) {
                        mCHDDList.forEach {
                            LogUtil.d("$i---------------requestBean:${it.date}")
                        }
                    }
                    val ROP = requestBean.p_MA_J.aacp
                    val TOP = targetBean.p_MA_J.aacp
                    if (ROP > TOP && ROP >= 1.3 * TOP && mCHDDList.size > 20) {
                        var afterBean = mCHDDList[4]

                        val reverseKJsonBean =
                            newReverseKJsonBean(afterBean, targetBean, requestBean)
                        val OM = oldBean.p_MA_J.amp
                        val OP = oldBean.p_MA_J.aaop
                        val OC = oldBean.p_MA_J.aacp
                        val OL = oldBean.p_MA_J.alp

                        val M = targetBean.p_MA_J.amp
                        val O = targetBean.p_MA_J.aaop
                        val C = targetBean.p_MA_J.aacp
                        val L = targetBean.p_MA_J.alp
                        setReverseBeanData(
                            reverseKJsonBean,
                            code,
                            targetBean,
                            requestBean,
                            OM,
                            M,
                            OC,
                            C,
                            O,
                            L,
                            OP,
                            OL
                        )
                        DBUtils.insertReverseKJTable(
                            (if (ROP >= 1.5 * TOP) Datas.REVERSE_TB_P50_11 else Datas.REVERSE_TB_P30_11),
                            reverseKJsonBean,
                            targetBean.date
                        )

                        val reverseKJSLLBean = ReverseKJSLLBean()
                        reverseKJSLLBean.code = code.toInt()
                        reverseKJSLLBean.n = targetBean.name
                        reverseKJSLLBean.d_D = requestBean.date.replace(
                            DateUtils.changeFromDefaultFormatter(
                                requestBean.date,
                                FormatterEnum.YYYY
                            ), ""
                        )
                        try {
                            reverseKJSLLBean.date = DateUtils.changeFormatter(
                                DateUtils.parse(
                                    targetBean.date,
                                    FormatterEnum.YYYYMMDD
                                ), FormatterEnum.MMDD
                            )
                        } catch (e: java.lang.Exception) {
                            reverseKJSLLBean.date = targetBean.date
                        }
                        if (null != targetBean.k_J.SLL) {
                            reverseKJSLLBean.u_D03 = targetBean.k_J.SLL.usll.D03.toKeep4()
                            reverseKJSLLBean.u_D05 = targetBean.k_J.SLL.usll.D05.toKeep4()
                            reverseKJSLLBean.u_D10 = targetBean.k_J.SLL.usll.D10.toKeep4()
                            reverseKJSLLBean.u_D15 = targetBean.k_J.SLL.usll.D15.toKeep4()
                            reverseKJSLLBean.u_D20 = targetBean.k_J.SLL.usll.D20.toKeep4()
                            reverseKJSLLBean.u_D25 = targetBean.k_J.SLL.usll.D25.toKeep4()
                            reverseKJSLLBean.u_D30 = targetBean.k_J.SLL.usll.D30.toKeep4()
                            reverseKJSLLBean.u_D60 = targetBean.k_J.SLL.usll.D60.toKeep4()
                            reverseKJSLLBean.u_D72 = targetBean.k_J.SLL.usll.D72.toKeep4()

                            reverseKJSLLBean.d_D03 = targetBean.k_J.SLL.dsll.D03.toKeep4()
                            reverseKJSLLBean.d_D05 = targetBean.k_J.SLL.dsll.D05.toKeep4()
                            reverseKJSLLBean.d_D10 = targetBean.k_J.SLL.dsll.D10.toKeep4()
                            reverseKJSLLBean.d_D15 = targetBean.k_J.SLL.dsll.D15.toKeep4()
                            reverseKJSLLBean.d_D20 = targetBean.k_J.SLL.dsll.D20.toKeep4()
                            reverseKJSLLBean.d_D25 = targetBean.k_J.SLL.dsll.D25.toKeep4()
                            reverseKJSLLBean.d_D30 = targetBean.k_J.SLL.dsll.D30.toKeep4()
                            reverseKJSLLBean.d_D60 = targetBean.k_J.SLL.dsll.D60.toKeep4()
                            reverseKJSLLBean.d_D72 = targetBean.k_J.SLL.dsll.D72.toKeep4()
                        }
                        DBUtils.insertReverseKJTable(
                            Datas.A_SLL_TB_,
                            reverseKJSLLBean,
                            targetBean.date
                        )
                        if (mCHDDList.size > 11) {
                            var targetBean2 = mCHDDList[6]
                            var targetBean3 = mCHDDList[7]
                            var oldBean331 = mCHDDList[8]
                            var oldBean332 = mCHDDList[9]
                            var oldBean333 = mCHDDList[10]

                            val reverseKJsonBean33 = newReverseKJsonBean(
                                afterBean,
                                targetBean,
                                requestBean
                            )
                            val OM33 = Math.max(
                                Math.max(oldBean331.p_MA_J.amp, oldBean332.p_MA_J.amp),
                                oldBean333.p_MA_J.amp
                            )
                            val OL33 = Math.min(
                                Math.min(oldBean331.p_MA_J.alp, oldBean332.p_MA_J.alp),
                                oldBean333.p_MA_J.alp
                            )
                            val OC33 = oldBean331.p_MA_J.aacp
                            val OO33 = oldBean333.p_MA_J.aaop

                            val M33 = Math.max(
                                Math.max(targetBean.p_MA_J.amp, targetBean2.p_MA_J.amp),
                                targetBean3.p_MA_J.amp
                            )
                            val L33 = Math.min(
                                Math.min(targetBean.p_MA_J.alp, targetBean2.p_MA_J.alp),
                                targetBean3.p_MA_J.alp
                            )
                            val C33 = targetBean.p_MA_J.aacp
                            val O33 = targetBean3.p_MA_J.aaop

                            setReverseBeanData(
                                reverseKJsonBean33,
                                code,
                                targetBean,
                                requestBean,
                                OM33,
                                M33,
                                OC33,
                                C33,
                                O33,
                                L33,
                                OO33,
                                OL33
                            )
                            DBUtils.insertReverseKJTable(
                                (if (ROP >= 1.5 * TOP) Datas.REVERSE_TB_P50_33 else Datas.REVERSE_TB_P30_33),
                                reverseKJsonBean33,
                                targetBean.date
                            )
                            if (mCHDDList.size > 15) {
                                var targetBean4 = mCHDDList[8]
                                var targetBean5 = mCHDDList[9]
                                var oldBean551 = mCHDDList[10]
                                var oldBean552 = mCHDDList[11]
                                var oldBean553 = mCHDDList[12]
                                var oldBean554 = mCHDDList[13]
                                var oldBean555 = mCHDDList[14]

                                val reverseKJsonBean55 = newReverseKJsonBean(
                                    afterBean,
                                    targetBean,
                                    requestBean
                                )
                                val OM55 = Math.max(
                                    Math.max(
                                        Math.max(
                                            oldBean551.p_MA_J.amp,
                                            oldBean552.p_MA_J.amp
                                        ), Math.max(oldBean553.p_MA_J.amp, oldBean554.p_MA_J.amp)
                                    ), oldBean555.p_MA_J.amp
                                )
                                val OL55 = Math.min(
                                    Math.max(
                                        Math.min(
                                            oldBean551.p_MA_J.alp,
                                            oldBean552.p_MA_J.alp
                                        ), Math.min(oldBean553.p_MA_J.alp, oldBean554.p_MA_J.alp)
                                    ), oldBean555.p_MA_J.alp
                                )
                                val OC55 = oldBean551.p_MA_J.aacp
                                val OO55 = oldBean555.p_MA_J.aaop

                                val M55 = Math.max(
                                    Math.max(
                                        Math.max(
                                            targetBean.p_MA_J.amp,
                                            targetBean2.p_MA_J.amp
                                        ), Math.max(targetBean3.p_MA_J.amp, targetBean4.p_MA_J.amp)
                                    ), targetBean5.p_MA_J.amp
                                )
                                val L55 = Math.max(
                                    Math.max(
                                        Math.max(
                                            targetBean.p_MA_J.alp,
                                            targetBean2.p_MA_J.alp
                                        ), Math.max(targetBean3.p_MA_J.alp, targetBean4.p_MA_J.alp)
                                    ), targetBean5.p_MA_J.alp
                                )
                                val C55 = targetBean.p_MA_J.aacp
                                val O55 = targetBean5.p_MA_J.aaop

                                setReverseBeanData(
                                    reverseKJsonBean55,
                                    code,
                                    targetBean,
                                    requestBean,
                                    OM55,
                                    M55,
                                    OC55,
                                    C55,
                                    O55,
                                    L55,
                                    OO55,
                                    OL55
                                )
                                DBUtils.insertReverseKJTable(
                                    (if (ROP >= 1.5 * TOP) Datas.REVERSE_TB_P50_55 else Datas.REVERSE_TB_P30_55),
                                    reverseKJsonBean55,
                                    targetBean.date
                                )
                                if (mCHDDList.size > 25) {
                                    var targetBean6 = mCHDDList[10]
                                    var targetBean7 = mCHDDList[11]
                                    var targetBean8 = mCHDDList[12]
                                    var targetBean9 = mCHDDList[13]
                                    var targetBean10 = mCHDDList[14]
                                    var oldBean101 = mCHDDList[15]
                                    var oldBean102 = mCHDDList[16]
                                    var oldBean103 = mCHDDList[17]
                                    var oldBean104 = mCHDDList[18]
                                    var oldBean105 = mCHDDList[19]
                                    var oldBean106 = mCHDDList[20]
                                    var oldBean107 = mCHDDList[21]
                                    var oldBean108 = mCHDDList[22]
                                    var oldBean109 = mCHDDList[23]
                                    var oldBean1010 = mCHDDList[24]

                                    val reverseKJsonBean10 = newReverseKJsonBean(
                                        afterBean,
                                        targetBean,
                                        requestBean
                                    )
                                    val OM10_1 = Math.max(
                                        Math.max(
                                            Math.max(
                                                oldBean101.p_MA_J.amp,
                                                oldBean102.p_MA_J.amp
                                            ),
                                            Math.max(oldBean103.p_MA_J.amp, oldBean104.p_MA_J.amp)
                                        ), oldBean105.p_MA_J.amp
                                    )
                                    val OM10_2 = Math.max(
                                        Math.max(
                                            Math.max(
                                                oldBean106.p_MA_J.amp,
                                                oldBean107.p_MA_J.amp
                                            ),
                                            Math.max(oldBean108.p_MA_J.amp, oldBean109.p_MA_J.amp)
                                        ), oldBean1010.p_MA_J.amp
                                    )
                                    val OM10 = Math.max(OM10_1, OM10_2)
                                    val OL10_1 = Math.min(
                                        Math.max(
                                            Math.min(
                                                oldBean101.p_MA_J.alp,
                                                oldBean102.p_MA_J.alp
                                            ),
                                            Math.min(oldBean103.p_MA_J.alp, oldBean104.p_MA_J.alp)
                                        ), oldBean105.p_MA_J.alp
                                    )
                                    val OL10_2 = Math.min(
                                        Math.max(
                                            Math.min(
                                                oldBean106.p_MA_J.alp,
                                                oldBean107.p_MA_J.alp
                                            ),
                                            Math.min(oldBean108.p_MA_J.alp, oldBean109.p_MA_J.alp)
                                        ), oldBean1010.p_MA_J.alp
                                    )
                                    val OL10 = Math.min(OL10_1, OL10_2)
                                    val OC10 = oldBean101.p_MA_J.aacp
                                    val OO10 = oldBean1010.p_MA_J.aaop


                                    val M10_1 = Math.max(
                                        Math.max(
                                            Math.max(
                                                targetBean.p_MA_J.amp,
                                                targetBean2.p_MA_J.amp
                                            ),
                                            Math.max(targetBean3.p_MA_J.amp, targetBean4.p_MA_J.amp)
                                        ), targetBean5.p_MA_J.amp
                                    )
                                    val M10_2 = Math.max(
                                        Math.max(
                                            Math.max(
                                                targetBean6.p_MA_J.amp,
                                                targetBean7.p_MA_J.amp
                                            ),
                                            Math.max(targetBean8.p_MA_J.amp, targetBean9.p_MA_J.amp)
                                        ), targetBean10.p_MA_J.amp
                                    )
                                    val M10 = Math.max(M10_1, M10_2)

                                    val L10_1 = Math.min(
                                        Math.max(
                                            Math.min(
                                                targetBean.p_MA_J.alp,
                                                targetBean2.p_MA_J.alp
                                            ),
                                            Math.min(targetBean3.p_MA_J.alp, targetBean4.p_MA_J.alp)
                                        ), targetBean5.p_MA_J.alp
                                    )
                                    val L10_2 = Math.min(
                                        Math.max(
                                            Math.min(
                                                targetBean6.p_MA_J.alp,
                                                targetBean7.p_MA_J.alp
                                            ),
                                            Math.min(targetBean8.p_MA_J.alp, targetBean9.p_MA_J.alp)
                                        ), targetBean10.p_MA_J.alp
                                    )
                                    val L10 = Math.min(L10_1, L10_2)
                                    val C10 = targetBean.p_MA_J.aacp
                                    val O10 = targetBean10.p_MA_J.aaop

                                    setReverseBeanData(
                                        reverseKJsonBean10,
                                        code,
                                        targetBean,
                                        requestBean,
                                        OM10,
                                        M10,
                                        OC10,
                                        C10,
                                        O10,
                                        L10,
                                        OO10,
                                        OL10
                                    )
                                    DBUtils.insertReverseKJTable(
                                        (if (ROP >= 1.5 * TOP) Datas.REVERSE_TB_P50_10 else Datas.REVERSE_TB_P30_10),
                                        reverseKJsonBean10,
                                        targetBean.date
                                    )
                                }
                            }
                        }
                    }
                }


            }
        }


        return chddDDBeanList
    }

    private fun getLastTwoMonthList(
        it: ArrayList<CodeHDDBean>,
        i: Int,
        code: String,
        mCHDDList: ArrayList<CodeHDDBean>
    ) {
        getLastMonthList(it, i, code, mCHDDList)
        val lastLastMonthList =
            getLastMonthChddList((it[i].date.toInt() - 100).toString(), code)
        lastLastMonthList?.let {
            for (i in (it.size - 1) downTo 0) {
                mCHDDList.add(it[i])
            }
        }
    }

    private fun getLastMonthList(
        it: ArrayList<CodeHDDBean>,
        i: Int,
        code: String,
        mCHDDList: ArrayList<CodeHDDBean>
    ) {
        val lastMonthList = getLastMonthChddList((it[i].date.toInt()).toString(), code)
        lastMonthList?.let {
            for (i in (it.size - 1) downTo 0) {
                mCHDDList.add(it[i])
            }
        }
    }

    private fun newReverseKJsonBean(
        afterBean: CodeHDDBean,
        targetBean: CodeHDDBean,
        requestBean: CodeHDDBean
    ): ReverseKJsonBean {
        val reverseKJsonBean = ReverseKJsonBean()
        var AO = ""
        if (afterBean.op >= targetBean.p_MA_J.amp) {
            AO = AO + "1"
        } else {
            AO = AO + "0"
        }
        if (afterBean.op >= targetBean.p_MA_J.aacp) {
            AO = AO + "1"
        } else {
            AO = AO + "0"
        }
        if (afterBean.op >= targetBean.p_MA_J.aaop) {
            AO = AO + "1"
        } else {
            AO = AO + "0"
        }
        if (afterBean.op >= targetBean.p_MA_J.alp) {
            AO = AO + "1"
        } else {
            AO = AO + "0"
        }
        reverseKJsonBean.ao = AO
        reverseKJsonBean.n = targetBean.name
        reverseKJsonBean.d_D = requestBean.date.replace(
            DateUtils.changeFromDefaultFormatter(
                requestBean.date,
                FormatterEnum.YYYY
            ), ""
        )
        return reverseKJsonBean
    }

    private fun setReverseBeanData(
        reverseKJsonBean: ReverseKJsonBean,
        code: String,
        targetBean: CodeHDDBean,
        requestBean: CodeHDDBean,
        OM: Float,
        M: Float,
        OC: Float,
        C: Float,
        O: Float,
        L: Float,
        OP: Float,
        OL: Float
    ) {
        reverseKJsonBean.code = code.toInt()
        reverseKJsonBean.date = targetBean.date.replace(
            DateUtils.changeFromDefaultFormatter(
                targetBean.date,
                FormatterEnum.YYYY
            ), ""
        )
        reverseKJsonBean.curP = ((requestBean.cp - targetBean.cp) / targetBean.cp * 100).toKeep2()
        reverseKJsonBean.oM_M = ((OM - M) / OC * 100).toKeep2()
        reverseKJsonBean.oM_C = ((OM - C) / OC * 100).toKeep2()
        reverseKJsonBean.oM_P = ((OM - O) / OC * 100).toKeep2()
        reverseKJsonBean.oM_L = ((OM - L) / OC * 100).toKeep2()
        reverseKJsonBean.oC_M = ((OC - M) / OC * 100).toKeep2()
        reverseKJsonBean.oC_C = ((OC - C) / OC * 100).toKeep2()
        reverseKJsonBean.oC_P = ((OC - O) / OC * 100).toKeep2()
        reverseKJsonBean.oC_L = ((OC - L) / OC * 100).toKeep2()
        reverseKJsonBean.oO_M = ((OP - M) / OC * 100).toKeep2()
        reverseKJsonBean.oO_C = ((OP - C) / OC * 100).toKeep2()
        reverseKJsonBean.oO_P = ((OP - O) / OC * 100).toKeep2()
        reverseKJsonBean.oO_L = ((OP - L) / OC * 100).toKeep2()
        reverseKJsonBean.oL_M = ((OL - M) / OC * 100).toKeep2()
        reverseKJsonBean.oL_C = ((OL - C) / OC * 100).toKeep2()
        reverseKJsonBean.oL_P = ((OL - O) / OC * 100).toKeep2()
        reverseKJsonBean.oL_L = ((OL - L) / OC * 100).toKeep2()
        reverseKJsonBean.oM_OC = ((OM - OC) / OC * 100).toKeep2()
        reverseKJsonBean.oM_OP = ((OM - OP) / OC * 100).toKeep2()
        reverseKJsonBean.oM_OL = ((OM - OL) / OC * 100).toKeep2()
        reverseKJsonBean.oC_OP = ((OC - OP) / OC * 100).toKeep2()
        reverseKJsonBean.oC_OL = ((OC - OL) / OC * 100).toKeep2()
        reverseKJsonBean.oP_OL = ((OP - OL) / OC * 100).toKeep2()
        reverseKJsonBean.m_C = ((M - C) / OC * 100).toKeep2()
        reverseKJsonBean.m_P = ((M - O) / OC * 100).toKeep2()
        reverseKJsonBean.m_L = ((M - L) / OC * 100).toKeep2()
        reverseKJsonBean.c_P = ((C - O) / OC * 100).toKeep2()
        reverseKJsonBean.c_L = ((C - L) / OC * 100).toKeep2()
        reverseKJsonBean.p_L = ((O - L) / OC * 100).toKeep2()
        var type = 0
        targetBean.k_J.ctc?.ctC_U?.let {
            if (null != it.typeA) {
                type = 11
            } else if (null != it.typeB) {
                type = 12
            } else if (null != it.typeC) {
                type = 13
            }
        }
        targetBean.k_J.ctc?.ctC_D?.let {
            if (null != it.typeA) {
                type = 21
            } else if (null != it.typeB) {
                type = 22
            } else if (null != it.typeC) {
                type = 23
            }
        }
        reverseKJsonBean.type = type
    }

    private fun kJForeach(
        beingIndex: Int,
        endIndex: Int,
        hq: List<MutableList<String>>,
        hhqBeginIndex: Int,
        beginUSLC: Int,
        beginDSLC: Int,
        beginUSLLength: Float,
        beginDSLLength: Float
    ): Pair<Pair<Int, Int>, Pair<Float, Float>> {
        var beginUSLC1 = beginUSLC
        var beginDSLC1 = beginDSLC
        var beginUSLLength1 = beginUSLLength
        var beginDSLLength1 = beginDSLLength
        for (i in beingIndex until endIndex) {
            val cp = getHisHqDayClosePrice(hq[hhqBeginIndex + i])
            val op = getHisHqDayOpenPrice(hq[hhqBeginIndex + i])
            val percent = getcurPercentFloat(hq[hhqBeginIndex + i])
            val mp = getHisHqDayHighestPrice(hq[hhqBeginIndex + i])
            val lp = getHisHqDayLowestPrice(hq[hhqBeginIndex + i])
            if ((percent > 0 && mp > op) || (percent < 0 && mp > cp)) {
                beginUSLC1++
            }
            if ((percent > 0 && lp < op) || (percent < 0 && lp < cp)) {
                beginDSLC1++
            }
            if (percent > 0) {
                beginUSLLength1 = beginUSLLength1 + (mp - cp) / cp
                beginDSLLength1 = beginDSLLength1 + (op - lp) / op
            } else {
                beginUSLLength1 = beginUSLLength1 + (mp - op) / op
                beginDSLLength1 = beginDSLLength1 + (cp - lp) / cp
            }
        }
        val slcP = Pair(beginUSLC1, beginDSLC1)
        val sllP = Pair(beginUSLLength1, beginDSLLength1)
        return Pair(slcP, sllP)

    }

    private fun getAvValue(value: Float) = String.format("%.4f", value / Datas.NUM_WAN).toFloat()

    private fun operaSddDB(
        ddlist: ArrayList<String>,
        ddidnex: Int,
        codelist: ArrayList<String>,
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
            DBUtils.queryDealDetailByCode(ddlist[ddidnex], codelist[codeidnex])

        ddBean?.let {
            LogUtil.d("ddBean m10Size:${ddBean.sizeBean?.m10Size}")
            val sumDDBean =
                DBUtils.querySumDDBeanByCode(sddTableNameALL, codelist[codeidnex])
            DBUtils.insertOrUpdateSumDD2DateTable(
                date, sddTableNameALL, sumDDBean, ddBean
            )

            val bean =
                DBUtils.queryHHqBeanByCode(hhqlist[hhqlist.size - 1], codelist[codeidnex])
            val hisHqBean = GsonHelper.parseArray(bean?.json, HisHqBean::class.java)
            hisHqBean?.let {
                val hhqbean = hisHqBean[0].hq
                if (null == sumDDBean) {
                    if (hhqBeginIndex < hhqbean.size) {
                        DBUtils.setSDDPercent(
                            sddTableNameALL,
                            getcurPercentFloat(hhqbean[hhqBeginIndex]),
                            codelist[codeidnex]
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
                        DateUtils.parse(
                            getHisHqDay(hhqbean[hhqBeginIndex]),
                            FormatterEnum.YYYY_MM_DD
                        )

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
            }

            LogUtil.d("${codelist[codeidnex]} operaSddDB tableName:$sddTableNameALL")

        }
    }


    private fun setSDDPercent(
        hhqBeginIndex: Int,
        hhqbean: MutableList<MutableList<String>>,
        hhqVeryBeginIndex: Int,
        curTs: Long,
        verybeginTS: Long,
        sddTableName: String,
        codelist: ArrayList<String>,
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
            LogUtil.d("$sddTableName set_SDDPercent,code:${codelist[codeidnex]},date:$date,beginClosePrice:$beginClosePrice,diffPrices:$diffPrices,percent:${percent}")
            LogUtil.d("$sddTableName set_SDDPercent,hhqBeginIndex:$hhqBeginIndex,cur:${hhqbean[hhqBeginIndex]}\n ,begin:${hhqbean[hhqVeryBeginIndex]}")
            DBUtils.setSDDPercent(
                sddTableName,
                percent,
                codelist[codeidnex]
            )
            if (percent > sumDDBean.maxPer) {
                DBUtils.setSDDMaxPercent(
                    sddTableName,
                    percent,
                    date,
                    codelist[codeidnex]
                )
            }
            if (percent < sumDDBean.lowPer) {
                DBUtils.setSDDLowPercent(
                    sddTableName,
                    percent,
                    date,
                    codelist[codeidnex]
                )
            }
        }
    }

    private fun getDDList(): Pair<ArrayList<String>, ArrayList<String>> {
        var cursor: Cursor? = null
        var ddList = ArrayList<String>()
        var hhqList = ArrayList<String>()
        val beginDate = getStartDateByDaysCount(Datas.HHQDayCount - 3) + " 12:00:00"
        val endDate = DateUtils.formatToDay(FormatterEnum.YYYYMMDD) + " 12:00:00"
        val beginTS = DateUtils.parse(beginDate, FormatterEnum.YYYYMMDD__HH_MM_SS)
        val endTS = DateUtils.parse(endDate, FormatterEnum.YYYYMMDD__HH_MM_SS)
        val dayTS = 24 * 60 * 60 * 1000
        var coutTS = beginTS
        while (coutTS <= endTS) {
            if (DateUtils.isWeekDay(coutTS).first) {
                ddList.add(DateUtils.format(coutTS, FormatterEnum.YYYYMMDD))
            }
            coutTS += dayTS
        }

        val curYts = DateUtils.parse(
            DateUtils.formatToDay(FormatterEnum.YYYY) + "0101",
            FormatterEnum.YYYYMMDD
        )
        val curYEndts = DateUtils.parse(
            DateUtils.formatToDay(FormatterEnum.YYYY) + "1231",
            FormatterEnum.YYYYMMDD
        )



        try {
            DBUtils.switchDBName(Datas.dataNamesDefault)
            val sql =
                "SELECT * FROM sqlite_master WHERE type ='table'"

            cursor = DBUtils.db.rawQuery(sql, null)
            LogUtil.d("cursor:${cursor.count}")
            while (cursor.moveToNext()) {
                var name = cursor.getString(cursor.getColumnIndex("name"))
                LogUtil.d("dd_name:$name")
                if (name.contains("DD_") && !name.contains("SDD_") && name.startsWith("DD_")) {
                    DBUtils.dropTable(name)
//                    val ts = DateUtils.parse(name.replace("DD_", ""), FormatterEnum.YYYYMMDD)
//                    if (ts >= curYts && ts <= curYEndts) {
//                        ddList.add(name)
//                    }
                }
                if (name.contains("HHQ")) {
                    hhqList.add(name)
                }

            }
        } catch (e: Exception) {
        } finally {
            cursor?.close()
        }


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