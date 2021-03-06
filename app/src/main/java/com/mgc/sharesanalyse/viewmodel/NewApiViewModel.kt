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
import kotlinx.android.synthetic.main.activity_new_api.*
import kotlinx.coroutines.Deferred
import org.jsoup.Jsoup
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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

    var curCode = -1
    var curDate = 1
    var curDateStr = "1"
    var hqCurCode = "1"

    val DEAL_DETAIL_NEXT_DATE = 1
    val DEAL_DETAIL_NEXT_CODE = 2
    val CHECK_DEAL_DETAIL = 3
    val CHECK_ALL_CODE = 4
    val CHECK_HQ_CODE = 5
    val CHECK_HOLDER = 6
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
                            if (!(mActivity as NewApiActivity).isCurrentHq) {
                                getHisHq(code)
                            } else {
                                getCurrentHq(code, (mActivity as NewApiActivity).lastDealDay)
                            }
                        }

                    }
                    CHECK_HOLDER -> {

                        getHolderChange()
//                        val code = (msg.arg1 as Int) + 1
//                        if (curCode == code ) {
//                            getHolderChange()
//                        } else  {
//                            Thread.sleep(500)
//                            val arg2 = msg.obj as String
//                            LogUtil.d("hoderCodeIndex-->code:$code,curCode:$curCode,arg2:$arg2")
//                            val btnTxt = (mActivity as NewApiActivity).btnGetHolderChange.text.toString()
//                            if (arg2.equals(btnTxt)) {
//                                getHolderChange()
//                            }
//                        }

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
        LogUtil.d("start-->$start,end-->$end")
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

    fun getCurrentHq(
        code: String,
        lastDealDay: String
    ) {
        var bean =
            DBUtils.queryHHqBeanByCode(getCurrentHQTableName(), code)

        if (null != bean) {
            LogUtil.d("cache_${Datas.sohuStockUrl}--------$code")
            loadState.value = LoadState.Success(REQUEST_HIS_HQ, bean.json)
        } else {
            val msg = mHandler.obtainMessage()
            msg.what = CHECK_HQ_CODE
            msg.obj = code
            hqCurCode = code
            mHandler.sendMessageDelayed(msg, 20 * 1000)
            val lastDate = DBUtils.getLastCHDDBeanDate(code, "DD_${code.toCompleteCode()}")
            LogUtil.d("lastDate:$lastDate,lastDealDay:$lastDealDay")
            if (lastDealDay.equals(lastDate)) {
                requestCurrentHq(true, code)
            } else {
                loadState.value = LoadState.GoNext(REQUEST_HIS_HQ)
                (mActivity as NewApiActivity).setBtnCurHQInfo("$code-->not series")
            }
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

    private fun requestCurrentHq(isinsertInTx: Boolean, code: String) {

        launch({
            val result: Deferred<String> = RetrofitManager.reqApi.getWyStockData(code.toWYCode())
            updateInTxCurHQInfo(
                result.await(),
                code
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

    private fun updateInTxCurHQInfo(
        json: String,
        code: String
    ) {
        LogUtil.d("updateInTxHisPriceInfo:$code")
        val bean = PricesHisGDBean()
        val mJson =
            json.replace("_ntes_quote_callback({\"${code.toWYCode()}\":", "").replace(" });", "")
        LogUtil.d("updateInTxHisPriceInfo:$json")
        LogUtil.d("updateInTxHisPriceInfo:$mJson")
//        停牌
//        _ntes_quote_callback({"1000670": {"time": "2020/04/26 08:00:02", "code": "1000670", "name": "*ST\u76c8\u65b9", "update": "2020/04/26 08:00:02"} });
        val mwybean = GsonHelper.parse(mJson, WYHQBean::class.java)
        LogUtil.d("updateInTxHisPriceInfo:$code")
        val hhbean = HisHqBean()
        //    0日期	1开盘	2收盘	3涨跌额	4涨跌幅	5最低	6最高	7成交量(手)	8成交金额(万)	9换手率
        val MutableList = mutableListOf<String>(
            mwybean.time.split(" ")[0].replace("/", "-"),
            mwybean.open.toString(),
            mwybean.price.toString(),
            mwybean.updown.toString(),
            (mwybean.percent.toFloat() * 100).toKeep2().toString(),
            mwybean.low.toString(),
            mwybean.high.toString(),
            (mwybean.volume / 1000000).toFloat().toKeep2().toString(),
            (mwybean.turnover / 10000).toFloat().toKeep2().toString(),
            0.toString()
        )
        hhbean.hq = arrayListOf(MutableList)
        hhbean.code = code
        LogUtil.d("updateInTxHisPriceInfo:$code")
        val hhlist = arrayListOf(hhbean)

        bean.code = code
        bean.date = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
        val insertJson = GsonHelper.toJson(hhlist)
        bean.json = insertJson
        LogUtil.d("updateInTxHisPriceInfo:$code")
        DBUtils.insertHHq2DateTable(getCurrentHQTableName(), bean)
        LogUtil.d("updateInTxHisPriceInfo:$code")
        loadState.value = LoadState.Success(REQUEST_HIS_HQ, insertJson)
    }

    fun getHHQTableName(): String {
//        return "HHQ_" + if (DateUtils.ifAfterToday1530()) DateUtils.formatToDay(FormatterEnum.YYYYMMDD) else DateUtils.formatYesterDay(
//            FormatterEnum.YYYYMMDD
        return "HHQ_" + DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
    }

    fun getCurrentHQTableName(): String {
//        return "HHQ_" + if (DateUtils.ifAfterToday1530()) DateUtils.formatToDay(FormatterEnum.YYYYMMDD) else DateUtils.formatYesterDay(
//            FormatterEnum.YYYYMMDD
        return "CurHQ_" + DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
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
    fun getPriceHisFileLog(isCurrentHq: Boolean) {
        var date =
            if (DateUtils.ifAfterToday1530()) DateUtils.formatToDay(FormatterEnum.YYYYMMDD) else DateUtils.formatYesterDay(
                FormatterEnum.YYYYMMDD
            )
        SPUtils.put(Datas.SPGetHQCodeDate, date)
        logDealDetailHqSum(isCurrentHq)
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

    fun logDealDetailHqSum(currentHq: Boolean) {
        needGoOn = false
        LogUtil.d("complete==========================")
        if (currentHq) {

        } else {
            val hhqList = getDDList(currentHq).second
            for (i in 0 until hhqList.size - 1) {
                DBUtils.dropTable(hhqList[i])
            }
            (mActivity as NewApiActivity).setBtnHHQInfo("hhq_complete")
        }
        App.getSinglePool().execute {
            DBUtils.switchDBName(Datas.dataNamesDefault)
            getSumDD(currentHq)
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


    fun getSumDD(currentHq: Boolean) {

        val sddTableNameALL = Datas.sddALL
        val sddTableName = Datas.sdd + DateUtils.formatToDay(FormatterEnum.YYMM)
        val pair = getDDList(currentHq)
        val ddlist = pair.first
        val hhqlist = pair.second
        if (hhqlist.size < 1) {
            (mActivity as NewApiActivity).setBtnSumDDInfo("list==0")
            return
        }
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

        //TODO 重新
//        if (!Datas.DEBUG) {
//            (mActivity as NewApiActivity).revAllJudgeResult()
//        }
        updateReasoningTB()


        //    0日期	1开盘	2收盘	3涨跌额	4涨跌幅	5最低	6最高	7成交量(手)	8成交金额(万)	9换手率

    }

    public fun reverseResult() {
        val (mList, codelist) = getCHDDDateListAndCodeList()
        codelist.forEach { code ->
            if (!Datas.REVERSE_DEBUG || (Datas.REVERSE_DEBUG && code.toInt() > Datas.REVERSE_BEGIN_CODE)) {
                mList.forEach {
                    val date = "20${it}01"
//                if (date.toInt() >= 20201201) {
//                }
                    if (it <= DateUtils.formatToDay(FormatterEnum.YYMM)) {
                        LogUtil.d("reverseResult-->$it")
                        (mActivity as NewApiActivity).setBtnReverseInfo("Reverse_$code _${date}")
                        getReverseChddBeans(code, date)
                    }
                }
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
                if (i == gapIndex) {
                    getLastTwoMonthList(it, i, code, mCHDDList)
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
                            for (i in ggRemoveList.size - 1 downTo 0) {
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

                            for (i in bgRemoveList.size - 1 downTo 0) {
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
                        if (gg.gap_OldCpRate > 2) {
                            gglist.add(0, gg)
                        }
                    } else if (curDMAJ.amp < lastDMAJ.alp) {
                        LogUtil.d("gap!!BG $code ----- ${curDBean.date} curDMAJ.amp:${curDMAJ.amp} lastDMAJ.alp:${lastDMAJ.alp}")
                        if (null == bglist) {
                            bglist = ArrayList<GapJsonBean.GG>()
                        }
                        val bg = GapJsonBean.GG()
                        bg.topPrice = curDMAJ.amp
                        bg.bottomPrice = lastDMAJ.alp
                        setComonGG(bg, curDMAJ, curDBean, lastDMAJ)
                        bg.bottomPrice = lastDMAJ.aacp
                        bg.gap_OldCpRate =
                            -((lastDMAJ.alp - curDMAJ.amp) / curDMAJ.amp * 100).toKeep2()
                        if (bg.gap_OldCpRate < -2) {
                            bglist.add(0, bg)
                        }
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
                        setGGBGRate(curDBean, curDMAJ, true)
                        setGGBGRate(curDBean, curDMAJ, false)
                        DBUtils.updateCHDDGapJson(
                            "DD_${code.toCompleteCode()}",
                            code.toCompleteCode(),
                            curDBean
                        )
                        mCHDDList.removeAt(0)
                        mCHDDList.add(0, curDBean)
                        curDBean.gaP_J?.let {
                            it.ggList?.let {
                                it.forEach {
                                    insertGapRecord2DB(code, it, curDBean, mCHDDList, true)
                                }

                            }
                            it.bgList?.let {
                                it.forEach {
                                    insertGapRecord2DB(code, it, curDBean, mCHDDList, false)
                                }
                            }
                        }
//                        mCHDDList.forEach {
//                            LogUtil.d("----\n${it.date}")
//                        }
//                        LogUtil.d("======================")
                    }
                }
            }
        }
    }

    private fun setGGBGRate(
        curDBean: CodeHDDBean,
        curDMAJ: CodeHDDBean.P_MA_J,
        isGG: Boolean
    ) {
        if ((isGG && null != curDBean.gaP_J.ggList && curDBean.gaP_J.ggList.size > 0) || (!isGG && null != curDBean.gaP_J.bgList && curDBean.gaP_J.bgList.size > 0)) {
            (if (isGG) curDBean.gaP_J.ggList else curDBean.gaP_J.bgList).forEach {
                it.date = curDBean.date.toInt()
                it.mlRange = ((it.mp - it.lp) / it.lp * 100).toKeep2()
                it.coRange = ((it.cp - it.op) / it.op * 100).toKeep2()
            }
        }
    }

    private fun insertGapRecord2DB(
        code: String,
        it: GapJsonBean.GG,
        curDBean: CodeHDDBean,
        mCHDDList: ArrayList<CodeHDDBean>,
        isGG: Boolean
    ) {
        val gapRecordBean =
            setGapRecordBeanData(code, it, curDBean, mCHDDList)
        if (gapRecordBean.b_D == gapRecordBean.date) {
            DBUtils.insertGapRecordJTable(
                (if (isGG) Datas.GAP_GG else Datas.GAP_BG) + Datas.GAP_BEGIN,
                gapRecordBean,
                curDBean.date
            )
        }
        DBUtils.insertGapRecordJTable(
            (if (isGG) Datas.GAP_GG else Datas.GAP_BG) + "LIST", gapRecordBean, curDBean.date
        )
    }

    private fun setGapRecordBeanData(
        code: String,
        it: GapJsonBean.GG,
        curDBean: CodeHDDBean,
        mCHDDList: ArrayList<CodeHDDBean>
    ): GapRecordBean {
        val gapRecordBean = GapRecordBean()
        gapRecordBean.code = code.toInt()
        gapRecordBean.b_D =
            DateUtils.changeFromDefaultFormatter(it.b_D.toString(), FormatterEnum.MMDD).toInt()
        gapRecordBean.date =
            DateUtils.changeFromDefaultFormatter(it.date.toString(), FormatterEnum.MMDD).toInt()
        gapRecordBean.n = curDBean.name
        gapRecordBean.gaP_RATE = it.gap_OldCpRate
        gapRecordBean.mL_RANGE = it.mlRange
        gapRecordBean.cO_RANGE = it.coRange
        gapRecordBean.json = GsonHelper.toJson(curDBean)
        if (mCHDDList.size >= 5) {
            val clList = ArrayList<Float>()
            val cmList = ArrayList<Float>()
            val mlList = ArrayList<Float>()
            val (a, b, c) = setComonGapRecordBean(0, 5, clList, mCHDDList, cmList, mlList)
            gapRecordBean.cD05LM = a
            gapRecordBean.d05LM = b
            gapRecordBean.d05OC = c
            if (mCHDDList.size >= 10) {
                val (a, b, c) = setComonGapRecordBean(5, 10, clList, mCHDDList, cmList, mlList)
                gapRecordBean.cD10LM = a
                gapRecordBean.d10LM = b
                gapRecordBean.d10OC = c
            }
            if (mCHDDList.size >= 15) {
                val (a, b, c) = setComonGapRecordBean(10, 15, clList, mCHDDList, cmList, mlList)
                gapRecordBean.cD15LM = a
                gapRecordBean.d15LM = b
                gapRecordBean.d15OC = c
            }
            if (mCHDDList.size >= 20) {
                val (a, b, c) = setComonGapRecordBean(15, 20, clList, mCHDDList, cmList, mlList)
                gapRecordBean.cD20LM = a
                gapRecordBean.d20LM = b
                gapRecordBean.d20OC = c
            }
            if (mCHDDList.size >= 30) {
                val (a, b, c) = setComonGapRecordBean(20, 30, clList, mCHDDList, cmList, mlList)
                gapRecordBean.cD30LM = a
                gapRecordBean.d30LM = b
                gapRecordBean.d30OC = c
            }
        }
        return gapRecordBean
    }

    fun setComonGapRecordBean(
        begin: Int, end: Int,
        clList: ArrayList<Float>,
        mCHDDList: ArrayList<CodeHDDBean>,
        cmList: ArrayList<Float>,
        mlList: ArrayList<Float>
    ): Triple<Float, Float, Float> {
        for (i in begin until end) {
            clList.add(mCHDDList[i].p_MA_J.alp)
            cmList.add(mCHDDList[i].p_MA_J.amp)
            mlList.add(mCHDDList[i].p_MA_J.aacp)
            mlList.add(mCHDDList[i].p_MA_J.aaop)
        }
        clList.sortFloatDateDesc()
        cmList.sortFloatDateDesc()
        mlList.sortFloatDateDesc()
        val cDLM =
            ((cmList[0] - clList[end - 1]) / clList[end - 1] * 100).toKeep2()
        val dLM =
            ((mlList[0] - mlList[end * 2 - 1]) / mlList[end * 2 - 1] * 100).toKeep2()
        val dOC =
            ((mCHDDList[0].cp - mCHDDList[end - 1].op) / mCHDDList[end - 1].op * 100).toKeep2()
        return Triple(cDLM, dLM, dOC)
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
            val date1 = DBUtils.getLastCHDDBeanDate(code, "DD_${code.toCompleteCode()}")
            hisHqBean?.let {
                try {
                    LogUtil.d("${hisHqBean[0].hq.size}")
                } catch (e: Exception) {
                    return@let
                }
                for (ddidnex in hisHqBean[0].hq.size - 1 downTo 0) {
                    //        for (ddidnex in 0 until 4) {
                    val date = getHisHqDay(hisHqBean[0].hq[ddidnex]).replace("-", "")
                    if (date.isEmpty() || date1.isEmpty()) {
                        continue
                    }
                    LogUtil.d("date--->$date,date1--->$date1,code:$code")
                    if (date.toInt() <= date1.toInt()) {
//                        (mActivity as NewApiActivity).setBtnSumDDInfo("CODE_DD_${date}_${code}_skip")
                        continue
                    }
                    if (ddidnex == 0) {
                        val date = getHisHqDay(hisHqBean[0].hq[ddidnex]).replace("-", "")
                    }
//                    val curIndexTs = DateUtils.parse(date, FormatterEnum.YYYYMMDD)
//                    if (curIndexTs >= curYearTS) {
//                    }
                    if (!date1.isEmpty()) {
                        if (date.toInt() > date1.toInt()) {
                            insertOrUpdateCodeHDD(
                                hisHqBean[0].hq,
                                ddidnex,
                                code,
                                namelist[codeidnex],
                                date
                            )
                            (mActivity as NewApiActivity).setBtnSumDDInfo("CODE_DD_${date}_${code}")
                            reasoningResult(true, code)
                            getReverseChddBeans(code, date)
                        } else {
                            (mActivity as NewApiActivity).setBtnSumDDInfo("CODE_DD_${date}_${code}_skip")
                        }
                    }
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

//        var dbName = code.toCodeHDD(date, FormatterEnum.YYYYMMDD)
//        var chddDDBeanList =
//            DBUtils.queryCHDDByTableName("DD_${code.toCompleteCode()}", dbName)
//        chddDDBeanList?.let {
//            updateCodeHDDGapInfo(code, date, it.size - 1)
//        }
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

        LogUtil.d("date:$date,month-->$month")
        if (month == yearlimit) {
            month = yearlimit - 100 + 12

            LogUtil.d("month:$month")
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
    ) {
        var dbName = code.toCodeHDD(date, FormatterEnum.YYYYMMDD)
        var chddDDBeanList = DBUtils.queryCHDDByTableName("DD_${code.toCompleteCode()}", dbName)
//        if (!dbName.equals("SZ_CHDD_2101")) {
//            return
//        }
        var mCHDDList = ArrayList<CodeHDDBean>()
        LogUtil.d("dbName:$dbName ,date:$date,tb:${"DD_${code.toInt()}"}----size:${chddDDBeanList?.size}")
        chddDDBeanList?.let {
            for (i in 0 until it.size) {
                mCHDDList.add(0, it[i])
                if (i == 0) {
                    LogUtil.d("date:${mCHDDList[0].date}")
                    getLastTwoMonthList(it, i, code, mCHDDList)
//                    mCHDDList.forEach {
//                        LogUtil.d(it.date)
//                    }
                }
                val requestBean = it[i]
                val targetBeanList = ArrayList<CodeHDDBean>()
                if (mCHDDList.size > Datas.REV_DAYS) {
                    val targetBean = mCHDDList[Datas.REV_DAYS]
                    val afterBean = mCHDDList[Datas.REV_DAYS - 1]
                    val ROP = requestBean.p_MA_J.aacp
                    val TOP = targetBean.p_MA_J.aacp

                    //20避免新股一字板
                    var needInsert = true
                    if (ROP > TOP && ROP >= 1.3 * TOP && mCHDDList.size > 20) {
                        if (mCHDDList.size > (70 + Datas.REV_DAYS)) {
                            val revKJOCOOBean =
                                DataSettingUtils.getRevKJOCOOBean(
                                    Datas.REV_DAYS,
                                    mCHDDList,
                                    code,
                                    targetBean,
                                    requestBean
                                )
                            for (x in Datas.REV_DAYS downTo 0) {
                                if (mCHDDList[x].p_MA_J.aacp < TOP * 0.95) {
                                    needInsert = false
                                    break
                                }
                            }
                            if (needInsert) {
                                DBUtils.insertOCOOBean(
                                    revKJOCOOBean,
                                    if (ROP >= 1.5 * TOP) Datas.REV_OC_OO_50 else (if (ROP >= 1.3 * TOP) Datas.REV_OC_OO_30 else Datas.REV_OC_OO_10)
                                )
                            }
                        }

                        if (ROP < 1.3 * TOP) {
                            return@let
                        }

                        //TODO OCOO测试暂时注释 begin
                        if (mCHDDList.size > Datas.REV_DAYS + 1) {
                            insertRevBean(
                                Datas.REV_DAYS,
                                Datas.REV_DAYS,
                                targetBeanList,
                                mCHDDList,
                                Datas.REV_DAYS + 1,
                                Datas.REV_DAYS + 1,
                                afterBean,
                                targetBean,
                                requestBean,
                                code,
                                (if (ROP >= 1.5 * TOP) Datas.REVERSE_TB_P50_11 else Datas.REVERSE_TB_P30_11),
                                ""
                            )
                        }
                        val foreachLimitList = getForeachLimitList()
                        val foreachTBNAmeList = arrayListOf(
                            arrayOf(Datas.REVERSE_TB_P50_33, Datas.REVERSE_TB_P30_33),
                            arrayOf(Datas.REVERSE_TB_P50_55, Datas.REVERSE_TB_P30_55),
                            arrayOf(Datas.REVERSE_TB_P50_10, Datas.REVERSE_TB_P30_10),
                            arrayOf(Datas.REVERSE_TB_P50_15, Datas.REVERSE_TB_P30_15),
                            arrayOf(Datas.REVERSE_TB_P50_20, Datas.REVERSE_TB_P30_20),
                            arrayOf(Datas.REVERSE_TB_P50_25, Datas.REVERSE_TB_P30_25),
                            arrayOf(Datas.REVERSE_TB_P50_30, Datas.REVERSE_TB_P30_30),
                            arrayOf(Datas.REVERSE_TB_P50_36, Datas.REVERSE_TB_P30_36)
                        )
                        val foreachREVTRTBNAmeList = arrayListOf(
                            arrayOf(Datas.REV_TR_TB_P50_33, Datas.REV_TR_TB_P30_33),
                            arrayOf(Datas.REV_TR_TB_P50_55, Datas.REV_TR_TB_P30_55),
                            arrayOf(Datas.REV_TR_TB_P50_10, Datas.REV_TR_TB_P30_10),
                            arrayOf(Datas.REV_TR_TB_P50_15, Datas.REV_TR_TB_P30_15),
                            arrayOf(Datas.REV_TR_TB_P50_20, Datas.REV_TR_TB_P30_20),
                            arrayOf(Datas.REV_TR_TB_P50_25, Datas.REV_TR_TB_P30_25),
                            arrayOf(Datas.REV_TR_TB_P50_30, Datas.REV_TR_TB_P30_30),
                            arrayOf(Datas.REV_TR_TB_P50_36, Datas.REV_TR_TB_P30_36)
                        )
                        insertReverseKJSLL(code, targetBean, requestBean)

                        for (i in 0 until foreachLimitList.size) {
                            if (mCHDDList.size > foreachLimitList[i][0]) {
                                insertRevBean(
                                    foreachLimitList[i][1],
                                    foreachLimitList[i][2],
                                    targetBeanList,
                                    mCHDDList,
                                    foreachLimitList[i][3],
                                    foreachLimitList[i][4],
                                    afterBean,
                                    targetBean,
                                    requestBean,
                                    code,
                                    if (ROP >= 1.5 * TOP) foreachTBNAmeList[i][0] else foreachTBNAmeList[i][1],
                                    if (ROP >= 1.5 * TOP) foreachREVTRTBNAmeList[i][0] else foreachREVTRTBNAmeList[i][1]
                                )
                            } else {
                                break
                            }
                        }
                        //TODO OCOO测试暂时注释 end
                    }
                }
            }


        }
    }


    private fun getForeachLimitList(): ArrayList<Array<Int>> {
        val foreachLimitList = arrayListOf(
            arrayOf(
                6 + Datas.REV_DAYS,
                1 + Datas.REV_DAYS,
                2 + Datas.REV_DAYS,
                3 + Datas.REV_DAYS,
                5 + Datas.REV_DAYS
            ),//33
            arrayOf(
                10 + Datas.REV_DAYS,
                3 + Datas.REV_DAYS,
                4 + Datas.REV_DAYS,
                5 + Datas.REV_DAYS,
                9 + Datas.REV_DAYS
            ),//55
            arrayOf(
                20 + Datas.REV_DAYS,
                5 + Datas.REV_DAYS,
                9 + Datas.REV_DAYS,
                10 + Datas.REV_DAYS,
                19 + Datas.REV_DAYS
            ),//10
            arrayOf(
                30 + Datas.REV_DAYS,
                10 + Datas.REV_DAYS,
                14 + Datas.REV_DAYS,
                15 + Datas.REV_DAYS,
                29 + Datas.REV_DAYS
            ),//15
            arrayOf(
                40 + Datas.REV_DAYS,
                15 + Datas.REV_DAYS,
                19 + Datas.REV_DAYS,
                20 + Datas.REV_DAYS,
                39 + Datas.REV_DAYS
            ),//20
            arrayOf(
                50 + Datas.REV_DAYS,
                20 + Datas.REV_DAYS,
                24 + Datas.REV_DAYS,
                25 + Datas.REV_DAYS,
                49 + Datas.REV_DAYS
            ),//25
            arrayOf(
                60 + Datas.REV_DAYS,
                25 + Datas.REV_DAYS,
                29 + Datas.REV_DAYS,
                30 + Datas.REV_DAYS,
                59 + Datas.REV_DAYS
            ),//30
            arrayOf(
                72 + Datas.REV_DAYS,
                30 + Datas.REV_DAYS,
                35 + Datas.REV_DAYS,
                36 + Datas.REV_DAYS,
                71 + Datas.REV_DAYS
            )//36
        )
        return foreachLimitList
    }

    private fun insertReverseKJSLL(
        code: String,
        targetBean: CodeHDDBean,
        requestBean: CodeHDDBean
    ) {
        val reverseKJSLLBean = ReverseKJSLLBean()
        reverseKJSLLBean.code = code.toInt()
        reverseKJSLLBean.n = targetBean.name
        reverseKJSLLBean.d_D = requestBean.date
        try {
            reverseKJSLLBean.date = targetBean.date
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
    }


    private fun insertRevBean(
        targetBeanBegin: Int,
        targetBeanEnd: Int,
        targetBeanList: ArrayList<CodeHDDBean>,
        mCHDDList: ArrayList<CodeHDDBean>,
        oldBeanBegin: Int,
        oldBeanEnd: Int,
        afterBean: CodeHDDBean,
        targetBean: CodeHDDBean,
        requestBean: CodeHDDBean,
        code: String,
        revName: String,
        trkslName: String
    ) {
        for (i in targetBeanBegin..targetBeanEnd) {
            targetBeanList.add(mCHDDList[i])
        }
        val oldBeanList = ArrayList<CodeHDDBean>()
        for (i in oldBeanBegin..oldBeanEnd) {
            oldBeanList.add(mCHDDList[i])
        }

        oldBeanList.sortAscByDate()
        targetBeanList.sortAscByDate()

        val reverseKJsonBean = newReverseKJsonBean(
            afterBean,
            targetBean,
            requestBean
        )
        val OC = oldBeanList[0].p_MA_J.aacp
        val OO = oldBeanList[oldBeanList.size - 1].p_MA_J.aacp


        val C = targetBean.p_MA_J.aacp
        val O = targetBeanList[targetBeanList.size - 1].p_MA_J.aaop

        val OM = oldBeanList.getRevBeansOM()
        val OL = oldBeanList.getRevBeansOL()
        val M = targetBeanList.getRevBeansOM()
        val L = targetBeanList.getRevBeansOL()
        oldBeanList.sortAscByDate()
        targetBeanList.sortAscByDate()
        val ma = getMAValueByIndexDay(mCHDDList, targetBeanEnd)
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
            OO,
            OL,
            ma
        )
        DBUtils.insertReverseKJTable(
            revName,
            reverseKJsonBean,
            targetBean.date
        )
        val dt = revName.split("RTB_")[1].split("_")[1]
        reverseKJsonBean.d_T = dt
        reverseKJsonBean.p_T = if (revName.contains("_50_")) "50" else "30"
        DBUtils.insertRevCode(
            code,
            reverseKJsonBean,
            targetBean.date
        )
        if (targetBeanBegin >= 6 && !trkslName.isEmpty()) {
            val trKSllBean = TR_K_SLL_Bean()
            trKSllBean.setCODE(reverseKJsonBean.code)
            trKSllBean.n = reverseKJsonBean.n
            trKSllBean.date = reverseKJsonBean.date
            trKSllBean.d_D = reverseKJsonBean.d_D
            DataSettingUtils.setTRDatas(trKSllBean, targetBeanBegin, oldBeanEnd, mCHDDList)
            DBUtils.insertReverseKJTable(trkslName, trKSllBean, targetBean.date)
            trKSllBean.d_T = dt
            trKSllBean.p_T = if (revName.contains("_50_")) "50" else "30"
            DBUtils.insertRevCode(
                code,
                trKSllBean,
                targetBean.date
            )

        }
    }

    private fun getMAValueByIndexDay(
        mCHDDList: ArrayList<CodeHDDBean>,
        targetBeanEnd: Int
    ): Int {
        val maList = ArrayList<String>()
        val MA05 = "1_${mCHDDList[targetBeanEnd].p_MA_J.d05}"
        val MA10 = "2_${mCHDDList[targetBeanEnd].p_MA_J.d10}"
        val MA15 = "3_${mCHDDList[targetBeanEnd].p_MA_J.d15}"
        val MA20 = "4_${mCHDDList[targetBeanEnd].p_MA_J.d20}"
        val MA30 = "5_${mCHDDList[targetBeanEnd].p_MA_J.d30}"
        val MA60 = "6_${mCHDDList[targetBeanEnd].p_MA_J.d60}"
        val MA72 = "7_${mCHDDList[targetBeanEnd].p_MA_J.d72}"
        val OP = "8_${mCHDDList[targetBeanEnd].p_MA_J.aaop}"
        val CP = "9_${mCHDDList[targetBeanEnd].p_MA_J.aacp}"
        maList.add(MA05)
        maList.add(MA10)
        maList.add(MA15)
        maList.add(MA20)
        maList.add(MA30)
        maList.add(MA60)
        maList.add(MA72)
        maList.add(OP)
        maList.add(CP)
        val ma = maList.getMADesc()
        return ma
    }

    private fun getSimpleMAValueByIndexDay(
        mCHDDList: ArrayList<CodeHDDBean>,
        targetBeanEnd: Int
    ): Int {
        val maList = ArrayList<String>()
        val MA05 = "1_${mCHDDList[targetBeanEnd].p_MA_J.d05}"
        val MA20 = "4_${mCHDDList[targetBeanEnd].p_MA_J.d20}"
        val OP = "8_${mCHDDList[targetBeanEnd].p_MA_J.aaop}"
        val CP = "9_${mCHDDList[targetBeanEnd].p_MA_J.aacp}"
        maList.add(MA05)
        maList.add(MA20)
        maList.add(OP)
        maList.add(CP)
        val ma = maList.getMADesc()
        return ma
    }


    private fun getLastTwoMonthList(
        it: ArrayList<CodeHDDBean>,
        i: Int,
        code: String,
        mCHDDList: ArrayList<CodeHDDBean>
    ) {
        getLastMonthList(it, i, code, mCHDDList)
        val limit = (DateUtils.formatToDay(FormatterEnum.YYYY) + "0100").toInt()
        val dateLong = DateUtils.parse(it[i].date, FormatterEnum.YYYYMMDD)
        val dd = DateUtils.format(dateLong, FormatterEnum.DD)
        it[i].date.toInt()
        LogUtil.d("=======================================================")
        for (index in 1..4) {
            var curForeachDate = it[i].date.toInt() - 100 * index
            if (curForeachDate < limit) {


                val curYear = DateUtils.formatToDay(FormatterEnum.YYYY)
                val lastYear = (curYear.toInt() - 1)
                val dateChange = when (curForeachDate) {
                    ("${curYear}00$dd".toInt()) -> "${lastYear}12$dd"
                    ("${lastYear}99$dd".toInt()) -> "${lastYear}11$dd"
                    ("${lastYear}98$dd".toInt()) -> "${lastYear}10$dd"
                    ("${lastYear}97$dd".toInt()) -> "${lastYear}09$dd"
                    else -> curForeachDate.toString()

                }
                if ("${curYear}0401".toInt() > it[i].date.toInt() && it[i].date.toInt() > "${curYear}0101".toInt()) {
                    LogUtil.d("dateChange-->$dateChange,date-->${it[i].date}")
                }
//                val dateChange = when (curForeachDate) {
//                    ("202100$dd".toInt())-> "202012$dd"
//                    ("202099$dd".toInt())-> "202011$dd"
//                    ("202098$dd".toInt())-> "202010$dd"
//                    ("202097$dd".toInt())-> "202009$dd"
//                    else -> curForeachDate
//
//                }
                getLastMonthChddList((dateChange).toString(), code)?.let {
                    for (i in (it.size - 1) downTo 0) {
                        mCHDDList.add(it[i])
                    }
                }
            } else {
                getLastMonthChddList((it[i].date.toInt() - 100 * index).toString(), code)?.let {
                    for (i in (it.size - 1) downTo 0) {
                        mCHDDList.add(it[i])
                    }
                }
            }
        }
        LogUtil.d("=======================================================")
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
        AO = getAO(afterBean, targetBean, AO)
        reverseKJsonBean.ao = AO
        reverseKJsonBean.n = targetBean.name
        reverseKJsonBean.d_D = requestBean.date.toInt()
        return reverseKJsonBean
    }

    private fun getAO(
        afterBean: CodeHDDBean,
        targetBean: CodeHDDBean,
        AO: String
    ): String {
        var AO1 = AO
        if (afterBean.op >= targetBean.p_MA_J.amp) {
            AO1 = AO1 + "1"
        } else {
            AO1 = AO1 + "0"
        }
        if (afterBean.op >= targetBean.p_MA_J.aacp) {
            AO1 = AO1 + "1"
        } else {
            AO1 = AO1 + "0"
        }
        if (afterBean.op >= targetBean.p_MA_J.aaop) {
            AO1 = AO1 + "1"
        } else {
            AO1 = AO1 + "0"
        }
        if (afterBean.op >= targetBean.p_MA_J.alp) {
            AO1 = AO1 + "1"
        } else {
            AO1 = AO1 + "0"
        }
        return AO1
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
        OL: Float,
        ma: Int
    ) {
        reverseKJsonBean.code = code.toInt()
        reverseKJsonBean.date = targetBean.date.toInt()
        reverseKJsonBean.curP = ((requestBean.cp - targetBean.cp) / targetBean.cp * 100).toKeep2()
        reverseKJsonBean.oM_M = ((OM - M) / OM * 100).toKeep2()
        reverseKJsonBean.oM_C = ((OM - C) / OM * 100).toKeep2()
        reverseKJsonBean.oM_P = ((OM - O) / OM * 100).toKeep2()
        reverseKJsonBean.oM_L = ((OM - L) / OM * 100).toKeep2()
        reverseKJsonBean.oC_M = ((OC - M) / OC * 100).toKeep2()
        reverseKJsonBean.oC_C = ((OC - C) / OC * 100).toKeep2()
        reverseKJsonBean.oC_P = ((OC - O) / OC * 100).toKeep2()
        reverseKJsonBean.oC_L = ((OC - L) / OC * 100).toKeep2()
        reverseKJsonBean.oO_M = ((OP - M) / OP * 100).toKeep2()
        reverseKJsonBean.oO_C = ((OP - C) / OP * 100).toKeep2()
        reverseKJsonBean.oO_P = ((OP - O) / OP * 100).toKeep2()
        reverseKJsonBean.oO_L = ((OP - L) / OP * 100).toKeep2()
        reverseKJsonBean.oL_M = ((OL - M) / OL * 100).toKeep2()
        reverseKJsonBean.oL_C = ((OL - C) / OL * 100).toKeep2()
        reverseKJsonBean.oL_P = ((OL - O) / OL * 100).toKeep2()
        reverseKJsonBean.oL_L = ((OL - L) / OL * 100).toKeep2()
        reverseKJsonBean.oM_OC = ((OM - OC) / OM * 100).toKeep2()
        reverseKJsonBean.oM_OP = ((OM - OP) / OM * 100).toKeep2()
        reverseKJsonBean.oM_OL = ((OM - OL) / OM * 100).toKeep2()
        reverseKJsonBean.oC_OP = ((OC - OP) / OC * 100).toKeep2()
        reverseKJsonBean.oC_OL = ((OC - OL) / OC * 100).toKeep2()
        reverseKJsonBean.oP_OL = ((OP - OL) / OP * 100).toKeep2()
        reverseKJsonBean.m_C = ((M - C) / M * 100).toKeep2()
        reverseKJsonBean.m_P = ((M - O) / M * 100).toKeep2()
        reverseKJsonBean.m_L = ((M - L) / M * 100).toKeep2()
        reverseKJsonBean.c_P = ((C - O) / C * 100).toKeep2()
        reverseKJsonBean.c_L = ((C - L) / C * 100).toKeep2()
        reverseKJsonBean.p_L = ((O - L) / O * 100).toKeep2()
        reverseKJsonBean.ma = ma
        if (null != targetBean.gaP_J) {
            reverseKJsonBean.gs = targetBean.gaP_J.aggs
            reverseKJsonBean.gj = GsonHelper.toJson(targetBean.gaP_J)
        }
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

    private fun getDDList(currentHq: Boolean): Pair<ArrayList<String>, ArrayList<String>> {
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
                if (name.contains("HHQ") && !currentHq) {
                    hhqList.add(name)
                } else if (name.contains("CurHQ") && currentHq) {
                    LogUtil.d("CurHQ name-->$name")
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

    var countLimit = 600
    fun filterRev() {
        DBUtils.switchDBName(Datas.REVERSE_KJ_DB)
        val smallerMap = HashMap<String, String>()
        val biggerMap = HashMap<String, String>()
        val indexNameList = arrayListOf(
            "OM_M",
            "OM_C",
            "OM_P",
            "OM_L",
            "OC_M",
            "OC_C",
            "OC_P",
            "OC_L",
            "OO_M",
            "OO_C",
            "OO_P",
            "OO_L",
            "OL_M",
            "OL_C",
            "OL_P",
            "OL_L"
        )
        val indexDerbyNameList = arrayListOf(
            "OM_OC",
            "OM_OP",
            "OM_OL",
            "OC_OP",
            "OC_OL",
            "OP_OL",
            "M_C",
            "M_P",
            "M_L",
            "C_P",
            "C_L",
            "P_L"
        )
        //        val indexNameList = arrayListOf("S_A_TR","S_R_TR","S_B_TR","S_C_TR","K_A_TR","K_R_TR","K_B_TR","K_C_TR","K_SL_A_TR","K_SL_R_TR","K_SL_B_TR","K_SL_C_TR")
//        val mTBList = getAllRevTBNameList()
        val mTBList = arrayListOf(Datas.REVERSE_TB_P50_36)
        mTBList.forEach { tbName ->
            smallerMap.clear()
            biggerMap.clear()
            var inedexList = indexNameList
            LogUtil.d("tbName:$tbName")
            if (tbName.contains(Datas.Derby)) {
                inedexList = indexDerbyNameList
            }
            inedexList.forEach {
                val newTBName = tbName.replace("A_RTB_", Datas.AA_FILTER_)
                FileLogUtil.d("${parentBasePath}FilerRev", newTBName)
                if (TextUtils.isEmpty(biggerMap.get(it)) && TextUtils.isEmpty(smallerMap.get(it))) {
                    if (it.equals(indexNameList[0])) {
                        countLimit = 600
                    }
                    val indexType = it
                    val stepValue = 10
                    val mCountList: ArrayList<BaseReverseImp> = ArrayList()
                    (mActivity as NewApiActivity).setBtnRevFilterInfo("$tbName _ $it _ $countLimit")

                    while (fiterRevTableByIndex(
                            tbName,
                            indexType,
                            stepValue,
                            biggerMap,
                            smallerMap,
                            mCountList
                        )
                    )
                        LogUtil.d("filterRev======fiterRevResult:${it.equals(indexNameList[indexNameList.size - 1])} countList:${mCountList.size} countLimit:${countLimit}")
                    if (it.equals(indexNameList[indexNameList.size - 1])) {
                        DBUtils.copyFilterTB2NewTB(newTBName, mCountList, 1)
                        (mActivity as NewApiActivity).setBtnRevFilterInfo("$tbName _ $it _ $countLimit _finish")
                        LogUtil.d("finish======fiterRevResult:$newTBName")
                    }
                }
            }
        }


    }

    private fun getAllRevTBNameList(): ArrayList<String> {
        val mTBList = ArrayList<String>()
        val tbList = arrayListOf(
            Datas.REVERSE_TB_P30_33,
            Datas.REVERSE_TB_P50_33,
            Datas.REVERSE_TB_P30_55,
            Datas.REVERSE_TB_P50_55,
            Datas.REVERSE_TB_P30_10,
            Datas.REVERSE_TB_P50_10
            ,
            Datas.REVERSE_TB_P30_15,
            Datas.REVERSE_TB_P50_15,
            Datas.REVERSE_TB_P30_20,
            Datas.REVERSE_TB_P50_20,
            Datas.REVERSE_TB_P30_25,
            Datas.REVERSE_TB_P50_25
            ,
            Datas.REVERSE_TB_P30_30,
            Datas.REVERSE_TB_P50_30,
            Datas.REVERSE_TB_P30_36,
            Datas.REVERSE_TB_P50_36
        )
        mTBList.addAll(tbList)
        tbList.forEach {
            mTBList.add(Datas.Derby + it)
        }
        return mTBList
    }

    private fun fiterRevTableByIndex(
        tbName: String,
        indexType: String,
        stepValue: Int,
        biggerMap: HashMap<String, String>,
        smallerMap: HashMap<String, String>,
        mCountList: ArrayList<BaseReverseImp>
    ): Boolean {
        val maxMinPari =
            DBUtils.selectMaxMinValueByTbAndColumn(tbName, indexType, Datas.REVERSE_KJ_DB)
        val minValue = (maxMinPari.first.toFloat() / 1).toInt()
        val maxValue = (maxMinPari.second.toFloat() / 1).toInt()
        var rateResult = 0
        var result = ""
        var range = 0
        var biggerResult = 0
        var smallerResult = 0
        LogUtil.d("$countLimit==============================================================$indexType")
        for (i in minValue..maxValue step stepValue) {
            for (y in maxValue downTo i step stepValue) {
                biggerMap.put(indexType, i.toString())
                smallerMap.put(indexType, y.toString())
                val countList = DBUtils.queryRevLimit(
                    tbName,
                    smallerMap,
                    biggerMap,
                    if (tbName.contains(Datas.Derby)) 2 else 1
                )
                countList?.let {
                    val rate = it.size / (y - i)
                    if (it.size >= countLimit && rate > rateResult) {
                        range = (y - i)
                        rateResult = rate
                        biggerResult = y
                        smallerResult = i
                        mCountList.clear()
                        mCountList.addAll(it)
                        result =
                            "maxValue------>$y,minValue----->$i,count----->${it.size},mCountList.size:${mCountList.size}  countLimit:$countLimit"
                    }
                }
            }
        }
        LogUtil.d("$indexType @@fiterRevResult:$result---range:$range")
        if (TextUtils.isEmpty(result)) {
            if (countLimit - 10 > 0) {
                countLimit = countLimit - 10
                return true
            } else {
                return false
            }

        } else {
            biggerMap.put(indexType, smallerResult.toString())
            smallerMap.put(indexType, biggerResult.toString())
            FileLogUtil.d(
                "${parentBasePath}FilerRev",
                "$indexType finish======fiterRevResult:$result mCountList:${mCountList.size} countLimit:$countLimit"
            )
            LogUtil.d("$indexType finish======fiterRevResult:$result mCountList:${mCountList.size} countLimit:$countLimit")
            return false
        }
    }


    fun getOtherResultByFilterTb(tbName: String) {
        if (tbName.contains(Datas.AA_FILTER_)) {
            val pair =
                DBUtils.selectMaxMinValueByTbAndColumn(tbName, "OM_M", Datas.REV_FILTERDB + "2020")
            var rangeMax = (pair.second.toFloat() / 10).toInt() * 10 + 10 - Datas.FILTER_PROGRESS
            var rangeMin = (pair.first.toFloat() / 10).toInt() * 10 - 10
            val rangeList = ArrayList<String>()
            for (i in rangeMin..rangeMax step Datas.FILTER_PROGRESS) {
                val list = DBUtils.getAAFilterAllByTbName(
                    "SELECT * FROM $tbName WHERE OM_M >=? AND OM_M<?",
                    arrayOf(i.toString(), (i + Datas.FILTER_PROGRESS).toString())
                )
                LogUtil.d("==========$i=======${i + Datas.FILTER_PROGRESS}")
                list?.forEach {
                    if (it is ReverseKJsonBean) {
                        LogUtil.d(it.n)
                        val name = DBUtils.createOtherBBTBDataByOriginCodeAndDate(
                            Datas.REV_FILTERDB + "2020",
                            it.code,
                            it.date.toString(),
                            1,
                            tbName,
                            "_R_${Math.abs(i)}_${Math.abs(i + Datas.FILTER_PROGRESS)}"
                        )
                        LogUtil.d(name)
                        if (!rangeList.contains(name)) {
                            rangeList.add(name)
                        }
                    }
                }
            }

            rangeList.forEach { rangename ->
                val range = rangename.split("_R_")[1]
                val list = DBUtils.getAAFilterAllByTbName("SELECT * FROM $rangename", null)
                val mTBList = getAllRevTBNameList()
                val trList = getAllRevTRTBNameList()
                trList.forEach {
                    mTBList.add(it)
                }
                list?.forEach {
                    if (it is ReverseKJsonBean) {
                        mTBList.forEach { tbName ->
                            (mActivity as NewApiActivity).setBtnCopyFilterTBResult(tbName + "_" + it.code)
                            DBUtils.createOtherBBTBDataByOriginCodeAndDate(
                                Datas.REVERSE_KJ_DB,
                                it.code,
                                it.date.toString(),
                                kotlin.run {
                                    if (tbName.contains("TR")) {
                                        3
                                    } else if (tbName.contains(Datas.Derby)) {
                                        2
                                    } else {
                                        1
                                    }
                                },
                                tbName,
                                "_R_$range"
                            )
                        }

                    }
                }
            }
            (mActivity as NewApiActivity).setBtnCopyFilterTBResult("copy_finish")
        }

    }

    private fun getAllRevTRTBNameList(): ArrayList<String> {
        return arrayListOf(
            Datas.REV_TR_TB_P30_11,
            Datas.REV_TR_TB_P50_11,
            Datas.REV_TR_TB_P30_33,
            Datas.REV_TR_TB_P50_33,
            Datas.REV_TR_TB_P30_55,
            Datas.REV_TR_TB_P50_55,
            Datas.REV_TR_TB_P30_10,
            Datas.REV_TR_TB_P50_10,
            Datas.REV_TR_TB_P30_15,
            Datas.REV_TR_TB_P50_15,
            Datas.REV_TR_TB_P30_20,
            Datas.REV_TR_TB_P50_20,
            Datas.REV_TR_TB_P30_25,
            Datas.REV_TR_TB_P50_25,
            Datas.REV_TR_TB_P30_30,
            Datas.REV_TR_TB_P50_30,
            Datas.REV_TR_TB_P30_36,
            Datas.REV_TR_TB_P50_36
        )
    }

    fun logBBRangeFile() {
        val list = DBUtils.getAllCopyBBTBNameList()

        val indexNameList = arrayListOf(
            "OM_M",
            "OM_C",
            "OM_P",
            "OM_L",
            "OC_M",
            "OC_C",
            "OC_P",
            "OC_L",
            "OO_M",
            "OO_C",
            "OO_P",
            "OO_L",
            "OL_M",
            "OL_C",
            "OL_P",
            "OL_L"
        )
        val indexDerbyNameList = arrayListOf(
            "OM_OC",
            "OM_OP",
            "OM_OL",
            "OC_OP",
            "OC_OL",
            "OP_OL",
            "M_C",
            "M_P",
            "M_L",
            "C_P",
            "C_L",
            "P_L"
        )
        val indextrNameList = arrayListOf(
            "S_A_TR",
            "S_R_TR",
            "S_B_TR",
            "S_C_TR",
            "K_A_TR",
            "K_R_TR",
            "K_B_TR",
            "K_C_TR",
            "K_SL_A_TR",
            "K_SL_R_TR",
            "K_SL_B_TR",
            "K_SL_C_TR"
        )
        val date = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)
        val p50FilterBBKjRangeBean = P50FilterBBKJRangeBean()
        var mDFilter = P50FilterBBKJRangeBean.DFilter()
        DataSettingUtils.setP50BeanInit(mDFilter)
        (mActivity as NewApiActivity).setBtnLogBBRangeFile("copy_finish")
        list.forEach {
            val count = DBUtils.queryTBCountByTBAndDB(it, Datas.REV_FILTERDB + "2020")
            if (count >= 5) {
                val filename = "${parentBasePath}$count _bb_range$date"
                (mActivity as NewApiActivity).setBtnLogBBRangeFile(it)
                val endStr = it.split("_R_")[1]
                val rangeArray = endStr.split("_")
                var rangeMin = rangeArray[0].toInt()
                if ((rangeArray[0].toInt() - rangeArray[1].toInt()) > 0) {
                    rangeMin = -rangeArray[0].toInt()
                }

                val mFileName = filename + "_R_" + endStr
                FileLogUtil.d(mFileName, "------------------------$it")
                mDFilter.count = count
                kotlin.run {
                    if (it.contains(Datas.Derby)) {
                        indexDerbyNameList
                    } else if (it.contains("TR")) {
                        indextrNameList
                    } else {
                        indexNameList
                    }
                }.forEach { indexStr ->
                    val pair = DBUtils.selectMaxMinValueByTbAndColumn(
                        it,
                        indexStr,
                        Datas.REV_FILTERDB + "2020"
                    )
                    FileLogUtil.d(mFileName, "${indexStr}->min:${pair.first},max:${pair.second}")
                    mDFilter =
                        DataSettingUtils.setP50FilterMaxMinValue(indexStr, it, mDFilter, pair)
                    if (indexStr.equals("OM_M") || indexStr.equals("OM_C")) {
                        LogUtil.d("$indexStr mDFilter-->${GsonHelper.toJson(mDFilter)}")
                    }
                }
                DataSettingUtils.setFilterBeanType(rangeMin, mDFilter, p50FilterBBKjRangeBean)
            }

        }
        val json = DBUtils.insertFilterResultJson(p50FilterBBKjRangeBean)

        FileLogUtil.d("${parentBasePath}rangeJson$date", json)
        (mActivity as NewApiActivity).setBtnLogBBRangeFile("FINISH")
    }

    fun reasoningResult(isLive: Boolean, mCode: String = "") {
        if ((Datas.SKIP_300000 && (mCode.toInt() > 600000 || mCode.toInt() < 300000)) || !Datas.SKIP_300000) {
            LogUtil.d("reasoningResult-->$mCode")
            val (mList, codelist) = getCHDDDateListAndCodeList()
            val (foreachLimitList, p50FilterBBKJRangeBean) = getReasoningForeachLimitListAndP50Bean()
            if (isLive) {
                getReasoningResult(mList, mCode, isLive, foreachLimitList, p50FilterBBKJRangeBean)
            } else {
                codelist.forEach { code ->
                    getReasoningResult(mList, code, isLive, foreachLimitList, p50FilterBBKJRangeBean)
                }
            }
            if (!isLive) {
                LogUtil.d("reasoningResult-->$isLive")
                (mActivity as NewApiActivity).setBtnResoning("Resoning_Finish")
            }
        }
    }

    private fun getReasoningForeachLimitListAndP50Bean(): Pair<ArrayList<Array<Int>>, P50FilterBBKJRangeBean?> {
        val foreachLimitList = getReasoningForeachLimitList()
        val json = DBUtils.getFilterResultJsonByType("50")
        var p50FilterBBKJRangeBean: P50FilterBBKJRangeBean? = null
        if (!json.isNullOrEmpty()) {
            p50FilterBBKJRangeBean = GsonHelper.parse(json, P50FilterBBKJRangeBean::class.java)
        }
        return Pair(foreachLimitList, p50FilterBBKJRangeBean)
    }

    private fun getReasoningForeachLimitList(): ArrayList<Array<Int>> {
        val initList = arrayOf(5, 10, 15, 20, 25, 30, 36)
//        val initList = arrayOf(3, 5, 10, 15, 20, 25, 30, 36)
        val foreachLimitList = ArrayList<Array<Int>>()
        initList.forEach {
            //target end  old:begin end
            foreachLimitList.add(arrayOf(it - 1, it, 2 * it - 1, it))
        }
        return foreachLimitList
    }

    private fun getReasoningResult(
        mList: ArrayList<String>,
        code: String,
        isLive: Boolean,
        foreachLimitList: ArrayList<Array<Int>>,
        p50FilterBBKJRangeBean: P50FilterBBKJRangeBean?
    ) {
        val mCHDDList = getCHDDCodeAllList(mList, code)
        if (mCHDDList.size >= 77) {
            if (!isLive) {
                for (i in 72..mCHDDList.size - 1) {
                    if (mCHDDList[i].date.toInt() >= Datas.REASONING_BEGIN_DATE) {
                        insertReasoning(
                            isLive,
                            foreachLimitList,
                            i,
                            mCHDDList,
                            p50FilterBBKJRangeBean,
                            code
                        )
//                        insertOCOOReasoning(i,mCHDDList,code)
                    }
                }

            } else {
                LogUtil.d("liveDay-->${mCHDDList[mCHDDList.size - 1].date},code--->$code")
                insertReasoning(
                    isLive,
                    foreachLimitList,
                    mCHDDList.size - 1,
                    mCHDDList,
                    p50FilterBBKJRangeBean,
                    code
                )

//                insertOCOOReasoning(mCHDDList.size - 1,mCHDDList,code)
            }
        }
    }

    private fun getCHDDCodeAllList(
        mList: ArrayList<String>,
        code: String
    ): ArrayList<CodeHDDBean> {
        val mCHDDList = ArrayList<CodeHDDBean>()
        mList.forEach {
            val date = "20${it}01"
            var month = DateUtils.changeFormatter(
                DateUtils.parse(date, FormatterEnum.YYYYMMDD),
                FormatterEnum.YYMM
            ).toInt()
            val dbName = code.toCodeHDD(month.toString(), FormatterEnum.YYMM)
            val codeList = DBUtils.queryCHDDByTableName("DD_${code.toCompleteCode()}", dbName)
            codeList?.let {
                mCHDDList.addAll(it)
            }
        }
        return mCHDDList
    }


    private fun getOldBeanList(
        i: Int,
        foreachLimitList: ArrayList<Array<Int>>,
        x: Int,
        mCHDDList: ArrayList<CodeHDDBean>
    ): ArrayList<CodeHDDBean> {
        val oldBeanList = ArrayList<CodeHDDBean>()
        val endBegin = i - foreachLimitList[x][1]
        val endEnd = i - foreachLimitList[x][2]
        for (z in endBegin downTo endEnd) {
            oldBeanList.add(mCHDDList[z])
        }
        return oldBeanList
    }

    private fun getTargetBeanList(
        i: Int,
        foreachLimitList: ArrayList<Array<Int>>,
        x: Int,
        mCHDDList: ArrayList<CodeHDDBean>
    ): ArrayList<CodeHDDBean> {
        val beinBegin = i
        val targetBeanList = ArrayList<CodeHDDBean>()
        val beinEnd = i - foreachLimitList[x][0]
        for (y in beinBegin downTo beinEnd) {
            targetBeanList.add(mCHDDList[y])
        }
        return targetBeanList
    }

    val rangeArray = arrayOf(
        "R_N70_N60",
        "R_N50_N40",
        "R_N40_N30",
        "R_N30_N20",
        "R_N20_N10",
        "R_N10_0",
        "R_0_10",
        "R_10_20"
    )
    val dayArray = arrayOf("D36_", "D30_", "D25_", "D20_", "D15_", "D10_", "D05_", "D03_")
    val mtbList = ArrayList<String>()
    fun getReasoningTBList(): ArrayList<String> {
        if (mtbList.size < 1) {
            rangeArray.forEach { range ->
                dayArray.forEach { day ->
                    mtbList.add("$day$range")
                }

            }
        }
        return mtbList
    }

    fun revOCOOJudgeResult() {
//        val dayList = arrayOf(3, 5, 10)
        val dayList = arrayOf(3, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70)
//        val tagList = arrayOf("OC")
        val tagList = arrayOf("OC", "OO", "PP", "PPP")
        val tbList = arrayOf(Datas.REV_OC_OO_30, Datas.REV_OC_OO_50)//

        (mActivity as NewApiActivity).setBtnRevAllTb("OC_OO_Begin")
        tbList.forEach {
            DBUtils.switchDBName(Datas.REVERSE_KJ_DB)
            if (DBUtils.tabbleIsExist(it)) {

                val (rangeMax, rangeMin) = DataSettingUtils.getRangeMaxMinByColumm(
                    it,
                    Datas.REVERSE_KJ_DB,
                    "OC${dayList[dayList.size - 1]}", true
                )
//            FILTER_OC_OO_PROGRESS

                LogUtil.d("rangeMax-->${rangeMax},rangeMin-->${rangeMin}")
                for (i in rangeMin until rangeMax step Datas.FILTER_OC_OO_PROGRESS) {


                    LogUtil.d("getFilterAllByTbName!!!")
                    val querySQL =
                        "SELECT * FROM $it WHERE _ID in (select max(_ID) from $it group by CODE,DATE) AND OC${dayList[dayList.size - 1]} >=$i AND OC${dayList[dayList.size - 1]}<${(i + Datas.FILTER_OC_OO_PROGRESS)} ${Datas.debugEndstr} ${Datas.reasoning_debug_end_str}"
                    val list = DBUtils.getFilterAllByTbName(
                        Datas.REVERSE_KJ_DB,
                        querySQL,
                        null, true
                    )

                    if (null == list) {
                        continue
                    }

                    (mActivity as NewApiActivity).setBtnRevAllTb("$it->$i--$rangeMax")
                    LogUtil.d("codeInfo-->OC_OO_$i--$rangeMax")

                    if (list.size > 0) {
                        val insertTB =
                            if (it.contains("30")) Datas.ALL_OC_OO_30 else Datas.ALL_OC_OO_50
                        if (list.size == 1) {
                            val reasoningAllJudgeBean =
                                DataSettingUtils.getReasoningOCOOJudgeBean(
                                    list,
                                    i,
                                    i + Datas.FILTER_OC_OO_PROGRESS
                                )
//                            val codeInfo =
//                                list.getCodeList().getCodeArrayAndLimitSQL(true) + Datas.debugEndstr + Datas.reasoning_debug_end_str

//                            LogUtil.d("codeInfo--(${reasoningAllJudgeBean.oC70_X}-${reasoningAllJudgeBean.oC70_D}):\n$codeInfo")
                            DBUtils.insertOCOOJudgeTB(reasoningAllJudgeBean, insertTB)
                        } else {
                            val dateRangeIndex = dayList.size - 2
                            val date = dayList[dateRangeIndex]

                            if (dateRangeIndex >= 0) {
                                DataSettingUtils.revOCOOlReasoning(
                                    tagList,
                                    0,
                                    dayList,
                                    dateRangeIndex,
                                    list,
                                    date,
                                    it,
                                    insertTB
                                )
                            }
                        }

                    }
                }
            }
        }

        (mActivity as NewApiActivity).setBtnRevAllTb("OCOO_Finish")
    }

    fun revAllJudgeResult() {

        LogUtil.d("revAllJudgeResult")
        val dayList = arrayOf(3, 5, 10, 15, 20, 25, 30, 36)
        val ptList = arrayOf(50, 30)
        (mActivity as NewApiActivity).setBtnRevAllTb("begin")
        LogUtil.d("debugEndstr-->${Datas.debugEndstr}")
        ptList.forEach { pt ->

            LogUtil.d("revAllJudgeResult")
            val tbName = "A_RTB_${pt}_36"
            val tbDerbyName = "Derby_A_RTB_${pt}_36"
            (mActivity as NewApiActivity).setBtnRevAllTb(tbName)
            val (rangeMax, rangeMin) = DataSettingUtils.getRangeMaxMin(
                tbName,
                Datas.REVERSE_KJ_DB
            )
            LogUtil.d("revAllJudgeResult")
            var nextContinue = 0
            for (i in rangeMin..rangeMax step Datas.FILTER_PROGRESS) {
                (mActivity as NewApiActivity).setBtnRevAllTb("OM_M $pt-> $i --> $rangeMax ")
                if (nextContinue > 0) {
                    nextContinue--
                    continue
                }
                var date = 36
                LogUtil.d("revAllJudgeResult")
                var dateRangeIndex = dayList.size - 1

                var list = DBUtils.getFilterAllByTbName(
                    Datas.REVERSE_KJ_DB,
                    "SELECT * FROM $tbName WHERE OM_M >=? AND OM_M<? ${Datas.debugEndstr} ${Datas.reasoning_debug_end_str}",
                    arrayOf(
                        i.toString(),
                        (i + Datas.FILTER_PROGRESS).toString()
                    )
                )

                if (null == list) {
                    continue
                }

                while (list!!.size < 2 && (i + (nextContinue + 1) * Datas.FILTER_PROGRESS) <= rangeMax) {
                    nextContinue++
                    list = DBUtils.getFilterAllByTbName(
                        Datas.REVERSE_KJ_DB,
                        "SELECT * FROM $tbName WHERE OM_M >=? AND OM_M<? ${Datas.debugEndstr} ${Datas.reasoning_debug_end_str}",
                        arrayOf(
                            i.toString(),
                            ((i + (nextContinue + 1) * Datas.FILTER_PROGRESS)).toString()
                        )
                    )
                }
//                list?.forEach {
//                    LogUtil.d("$tbName code:$it")
//                }
                if (list.size > 1) {
                    LogUtil.d("revAllJudgeResult")
                    val derbyList = list.getAllJudgeDerbyList(tbDerbyName)
                    val reasoningAllJudgeBean =
                        DataSettingUtils.getReasoningAllJudgeBean(list, derbyList, date)
                    reasoningAllJudgeBean.f36_T = i
                    val insertTB = "All_${pt}"
                    DBUtils.insertAllJudgeTB(reasoningAllJudgeBean, insertTB)
                    dateRangeIndex = dayList.size - 2
                    date = dayList[dateRangeIndex]
                    LogUtil.d("nextTbName!!!-->($i,${(i + Datas.FILTER_PROGRESS)})")
                    if (dateRangeIndex > 0) {
                        DataSettingUtils.revAllReasoning30(
                            pt,
                            dayList,
                            dateRangeIndex,
                            list,
                            dayList[dateRangeIndex],
                            insertTB,
                            arrayListOf(i, 0, 0, 0, 0, 0, 0, 0)
                        )
                    }

                }
            }

        }

        (mActivity as NewApiActivity).setBtnRevAllTb("Finish")
    }
//    fun revAllJudgeResult() {
//
//        LogUtil.d("revAllJudgeResult")
//        val dayList = arrayOf(3, 5, 10, 15, 20, 25, 30, 36)
//        val tagList = arrayOf("OM_M", "OC_M", "OO_M", "OL_M")
////        val tagList = arrayOf("OM_M","OM_C", "OM_P", "OM_L", "OC_M", "OC_C","OC_P", "OC_L", "OO_M", "OO_C", "OO_P", "OO_L")
////        val tagList = arrayOf("OM_M","OM_C","OM_L")
//        val ptList = arrayOf(50, 30)
//        (mActivity as NewApiActivity).setBtnRevAllTb("begin")
//        LogUtil.d("debugEndstr-->${Datas.debugEndstr}")
//        ptList.forEach { pt ->
//
//            LogUtil.d("revAllJudgeResult")
//            val tbName = "A_RTB_${pt}_36"
//            val tbDerbyName = "Derby_A_RTB_${pt}_36"
//            val (rangeMax, rangeMin) = DataSettingUtils.getRangeMaxMin(
//                tbName,
//                Datas.REVERSE_KJ_DB
//            )
//            LogUtil.d("revAllJudgeResult")
//            var nextContinue = 0
//            for (i in rangeMin..rangeMax step Datas.FILTER_PROGRESS) {
//                (mActivity as NewApiActivity).setBtnRevAllTb("OM_M $pt-> $i --> $rangeMax ")
//                if (nextContinue > 0) {
//                    nextContinue--
//                    continue
//                }
//                var date = 36
//                LogUtil.d("revAllJudgeResult")
//                var dateRangeIndex = dayList.size - 1
//
//
//                var list = DBUtils.getFilterAllByTbName(
//                    Datas.REVERSE_KJ_DB,
//                    "SELECT * FROM $tbName WHERE _ID in (select max(_ID) from $tbName group by CODE,DATE) AND  OM_M >=? AND OM_M<? ${Datas.debugEndstr} ${Datas.reasoning_debug_end_str}",
//                    arrayOf(
//                        i.toString(),
//                        (i + Datas.FILTER_PROGRESS).toString()
//                    )
//                )
//                if (null == list) {
//                    continue
//                }
//
//                while (list!!.size < 2 && (i + (nextContinue + 1) * Datas.FILTER_PROGRESS) < rangeMax) {
//                    nextContinue++
//                    LogUtil.d("nextContinue-->$nextContinue")
//                    list = DBUtils.getFilterAllByTbName(
//                        Datas.REVERSE_KJ_DB,
//                        "SELECT * FROM $tbName  WHERE _ID in (select max(_ID) from $tbName group by CODE,DATE) AND OM_M >=? AND OM_M<? ${Datas.debugEndstr} ${Datas.reasoning_debug_end_str}",
//                        arrayOf(
//                            i.toString(),
//                            ((i + (nextContinue + 1) * Datas.FILTER_PROGRESS)).toString()
//                        )
//                    )
//                }
////                list?.forEach {
////                    LogUtil.d("$tbName code:$it")
////                }
//                if (list.size > 0) {
//                    LogUtil.d("revAllJudgeResult")
////                    val derbyList = list.getAllJudgeDerbyList(tbDerbyName)
////                    val reasoningAllJudgeBean =
////                        DataSettingUtils.getReasoningAllJudgeBean(list, derbyList, date)
////                    reasoningAllJudgeBean.f36_T = i
//                    val insertTB = "All_${pt}"
////                    DBUtils.insertAllJudgeTB(reasoningAllJudgeBean, insertTB)
//                    dateRangeIndex = dayList.size - 1
//                    var tagIndex = 0
//                    LogUtil.d("nextTbName!!!-->($i,${((i + (nextContinue + 1) * Datas.FILTER_PROGRESS))})")
//                    if (dateRangeIndex > 0) {
//                        DataSettingUtils.revAllReasoning30(
//                            pt,
//                            tagList,
//                            tagIndex+1,
//                            dayList,
//                            dateRangeIndex,
//                            list,
//                            dayList[dateRangeIndex],
//                            insertTB,
//                            arrayListOf(i, 0, 0, 0, 0, 0, 0, 0),
//                            "OM_M-->($i,${((i + (nextContinue + 1) * Datas.FILTER_PROGRESS))})"
//                        )
//                    }
//
//                }
//            }
//
//        }
//
//        (mActivity as NewApiActivity).setBtnRevAllTb("Finish")
//    }


    fun reasoningAll() {
        val (mList, codelist) = getCHDDDateListAndCodeList()
        val foreachLimitList = getReasoningForeachLimitList()
//        val mColdeList = DBUtils.getCodeListByTBName(Datas.All_Reasoning_50)
        codelist.forEach { code ->
            val mCHDDList = getCHDDCodeAllList(mList, code)
            (mActivity as NewApiActivity).setBtnReasoningAll("all_$code")

            if (mCHDDList.size > 77) {
                for (i in 72..mCHDDList.size - 1) {
                    if (mCHDDList[i].date.toInt() >= Datas.REASONING_BEGIN_DATE) {
                        if (Datas.reasoning_debug && (mCHDDList[i].date.toInt() < Datas.reasoning_debug_begin_day || mCHDDList[i].date.toInt() > Datas.reasoning_debug_end_day)) {
                            continue
                        }
                        //TODO CESHI

//                        if (mCHDDList[i].date.equals(code.d)) {
//                            insertOCOOReasoning(i,mCHDDList,code.code.toString(),true)
//                        }
                        insertAllReasoning(
                            false,
                            foreachLimitList,
                            i,
                            mCHDDList,
                            code
                        )
//                        if (mCHDDList[i].date.equals("20200727")) {
//                        }
//                        insertOCOOReasoning(i,mCHDDList,code)
                    }
                }
            }
        }

        (mActivity as NewApiActivity).setBtnReasoningAll("all_finish!!")
//        (mActivity as NewApiActivity).setBtnGetAll30("all_finish!!")
//        (mActivity as NewApiActivity).setBtnGetAll50("all_finish!!")
    }

    private fun insertOCOOReasoning(
        i: Int,
        mCHDDList: java.util.ArrayList<CodeHDDBean>,
        code: String,
        is50: Boolean
    ) {
        if (70 <= mCHDDList.size) {
            LogUtil.d("code-->${code},date-->${mCHDDList[i].date}")
            val ocooBean = DataSettingUtils.getInsertRevKJOCOOBean(i, mCHDDList)
            insertOCOOReasoning5030Bean(
                is50,
                ocooBean,
                i,
                mCHDDList,
                code,
                if (is50) Datas.ALL_Reaoning_OC_OO_50 else Datas.ALL_Reaoning_OC_OO_30
            )
        }
    }

    private fun insertOCOOReasoning5030Bean(
        is50: Boolean,
        ocooBean: ReverseKJsonBean,
        i: Int,
        mCHDDList: java.util.ArrayList<CodeHDDBean>,
        code: String,
        tbName: String
    ) {
        val pair = DBUtils.getReasoningOCOOJudgeBeanByOCOOBean(is50, ocooBean)
        val triple = pair.second
        if (triple.first) {
            val bean50 = DataSettingUtils.getOCOOReasoningRevBean(i, mCHDDList)
            bean50.rrate = triple.third.rr
            bean50.frate = triple.third.fr
            bean50.size = triple.third.size
            bean50.j_ID = pair.first
            LogUtil.d("j_ID-->${bean50.j_ID}")
            setReasoningRevBeanBasicInfo(bean50, code, mCHDDList, i, 0)
            if (is50) {
                (mActivity as NewApiActivity).setOCOO50("OCOO_50_${code}_${mCHDDList[i].date}")
            } else {
                (mActivity as NewApiActivity).setOCOO30("OCOO_30_${code}_${mCHDDList[i].date}")
            }
            DBUtils.insertOCOOReasoningBean(bean50, tbName, triple.second)
        }
    }

    private fun insertAllReasoning(
        isLive: Boolean,
        foreachLimitList: java.util.ArrayList<Array<Int>>,
        i: Int,
        mCHDDList: java.util.ArrayList<CodeHDDBean>,
        code: String
    ) {

        var fitlerType = 10086
        val allReasoning50Bean = ReasoningRevBean()
        val allReasoning30Bean = ReasoningRevBean()
        var continue50 = true
        var continue30 = true
        for (x in foreachLimitList.size - 1 downTo 0) {
            val targetBeanList = getTargetBeanList(i, foreachLimitList, x, mCHDDList)
            LogUtil.d("date-->${targetBeanList[0].date}")
            val oldBeanList = getOldBeanList(i, foreachLimitList, x, mCHDDList)
            val OM = oldBeanList.getRevBeansOM()
            val M = targetBeanList.getRevBeansOM()
            val allOM_M = ((OM - M) / OM) * 100
            val pair = DataSettingUtils.filterAllReasoning(
                allOM_M.toKeep2(),
                foreachLimitList[x][0],
                targetBeanList,
                oldBeanList,
                allReasoning50Bean,
                allReasoning30Bean,
                continue50,
                continue30
            )
            continue50 = pair.first
            continue30 = pair.second
            if (!continue50 && !continue30) {
                break
            }
            if (x == 0) {
                if (continue50) {
                    setReasoningRevBeanBasicInfo(allReasoning50Bean, code, mCHDDList, i, fitlerType)
                    DBUtils.insertReasoningAllTB(allReasoning50Bean, true)
                    insertOCOOReasoning(i, mCHDDList, code, true)
                }

                if (continue30) {

                    setReasoningRevBeanBasicInfo(
                        allReasoning30Bean,
                        code,
                        mCHDDList,
                        i,
                        fitlerType
                    )
                    DBUtils.insertReasoningAllTB(allReasoning30Bean, false)
                    insertOCOOReasoning(i, mCHDDList, code, false)
                    /*--------------------------------------------------------------*/
//                    continue30 = f36AddictionJudge(allReasoning30Bean, mCHDDList, i, continue30)
//                    if (continue30) {
//                        setReasoningRevBeanBasicInfo(
//                            allReasoning30Bean,
//                            code,
//                            mCHDDList,
//                            i,
//                            fitlerType
//                        )
//                        DBUtils.insertReasoningAllTB(allReasoning30Bean, false)
//                        /*--------------------------------------------------------------*/
////                        getAddContinue30Str(allReasoning30Bean, mCHDDList, i)
//                    }
                }
            }
        }
    }

    private fun insertReasoning(
        isLive: Boolean,
        foreachLimitList: ArrayList<Array<Int>>,
        i: Int,
        mCHDDList: ArrayList<CodeHDDBean>,
        p50FilterBBKJRangeBean: P50FilterBBKJRangeBean?,
        code: String
    ) {
        val mP50Bean = P50FilterBBKJRangeBean()
        val mDFilter = P50FilterBBKJRangeBean.DFilter()
//        var needContinue = true
        var originOM_M = -10086.toFloat()
        var fitlerType = 10086
        val allReasoning50Bean = ReasoningRevBean()
        val allReasoning30Bean = ReasoningRevBean()
        var continue50 = true
        var continue30 = true
        for (x in foreachLimitList.size - 1 downTo 0) {
            val targetBeanList = getTargetBeanList(i, foreachLimitList, x, mCHDDList)
            val oldBeanList = getOldBeanList(i, foreachLimitList, x, mCHDDList)
            val OM = oldBeanList.getRevBeansOM()
            val M = targetBeanList.getRevBeansOM()
            if (originOM_M == -10086.toFloat()) {
                originOM_M = ((OM - M) / OM) * 100
                fitlerType = DataSettingUtils.getFilterP50ResultType(originOM_M)
            }

            val allOM_M = ((OM - M) / OM) * 100
//            if (needContinue) {
//                needContinue = DataSettingUtils.filterP50Result(
//                    fitlerType,
//                    originOM_M,
//                    mDFilter,
//                    p50FilterBBKJRangeBean!!,
//                    foreachLimitList[x][0],
//                    targetBeanList,
//                    oldBeanList
//                )
//            }

            val pair = DataSettingUtils.filterAllReasoning(
                allOM_M.toKeep2(),
                foreachLimitList[x][0],
                targetBeanList,
                oldBeanList,
                allReasoning50Bean,
                allReasoning30Bean,
                continue50,
                continue30
            )
            continue50 = pair.first
            continue30 = pair.second

            if (!continue50 && !continue30) {
                break
            }
            if (x == 0) {
//                if (needContinue) {
//                    DataSettingUtils.setFilterP50ResultType(originOM_M, mDFilter, mP50Bean)
//                    val reasoningRevBean = ReasoningRevBean()
//                    setReasoningRevBeanBasicInfo(reasoningRevBean, code, mCHDDList, i, fitlerType)
//                    reasoningRevBean.fitlertype = fitlerType.toString()
//                    reasoningRevBean.json = GsonHelper.toJson(mP50Bean)
//                    (mActivity as NewApiActivity).setBtnResoning("code:${code},date:${mCHDDList[i].date}")
//                    val DneedInsert = DBUtils.insertReasoningRevTB(reasoningRevBean)
//                    if (!DneedInsert && !isLive) {
//                        LogUtil.d("===insertTBByFilterType===")
//                        mP50Bean.insertTBByFilterType(
//                            code,
//                            mCHDDList[i].date,
//                            getReasoningTBList(),
//                            reasoningRevBean.p
//                        )
//                    }
//                }
                if (continue50) {
                    (mActivity as NewApiActivity).setBtnReasoningAll("all_50_code:${code},date:${mCHDDList[i].date}")
                    (mActivity as NewApiActivity).setBtnGetAll50("all_50_code:${code},date:${mCHDDList[i].date}")
                    setReasoningRevBeanBasicInfo(allReasoning50Bean, code, mCHDDList, i, fitlerType)
                    DBUtils.insertReasoningAllTB(allReasoning50Bean, true)
                    insertOCOOReasoning(mCHDDList.size - 1, mCHDDList, code, true)
                }
                if (continue30) {

                    (mActivity as NewApiActivity).setBtnReasoningAll("all_30_code:${code},date:${mCHDDList[i].date}")
                    (mActivity as NewApiActivity).setBtnGetAll30("all_30_code:${code},date:${mCHDDList[i].date}")
                    setReasoningRevBeanBasicInfo(
                        allReasoning30Bean,
                        code,
                        mCHDDList,
                        i,
                        fitlerType
                    )
                    DBUtils.insertReasoningAllTB(allReasoning30Bean, false)
                    insertOCOOReasoning(mCHDDList.size - 1, mCHDDList, code, false)
//                    continue30 = f36AddictionJudge(allReasoning30Bean, mCHDDList, i, continue30)
//                    if (continue30) {
//                        (mActivity as NewApiActivity).setBtnReasoningAll("all_30_code:${code},date:${mCHDDList[i].date}")
//                        (mActivity as NewApiActivity).setBtnGetAll30("all_30_code:${code},date:${mCHDDList[i].date}")
//                        setReasoningRevBeanBasicInfo(
//                            allReasoning30Bean,
//                            code,
//                            mCHDDList,
//                            i,
//                            fitlerType
//                        )
//                        DBUtils.insertReasoningAllTB(allReasoning30Bean, false)
//                    }
                }
            }
            //                        logStr = logStr+"-${foreachLimitList[x][3]}-beinBegin:${mCHDDList[beinBegin].date},beinEnd:${mCHDDList[beinEnd].date},endBegin:${mCHDDList[endBegin].date},endEnd:${mCHDDList[endEnd].date},targetBeanList:${targetBeanList.size},oldBeanList:${oldBeanList.size}\n"
        }
    }

    private fun f36AddictionJudge(
        allReasoning30Bean: ReasoningRevBean,
        mCHDDList: ArrayList<CodeHDDBean>,
        i: Int,
        continue30: Boolean
    ): Boolean {
        var continue301 = continue30
        when (allReasoning30Bean.f36_T) {
            -30, -20, -10, 0, 10 -> {
                setAllReasoningBeanJudgeData(allReasoning30Bean, mCHDDList, i)
                continue301 = addContinue30(allReasoning30Bean, continue301)
            }
            /*--------------------------------------------------------------*/
            //                        else -> continue30 = false
        }
        return continue301
    }


    var addJudge_30Str = ""
    var addJudge_20Str = ""
    var addJudge_10Str = ""
    var addJudge0Str = ""
    var addJudge10Str = ""
    val continue30KuiMap = HashMap<String, Int>()
    val continue30Map = HashMap<String, Int>()
    val continue30AOMap = HashMap<String, Int>()
    val continue30MA1Map = HashMap<String, Int>()
    val continue30AOMA1Map = HashMap<String, Int>()

    fun sortContinue30Map() {
        val list = ArrayList<String>()
        val yingList = ArrayList<String>()
        continue30KuiMap.forEach { (key, value) ->
            list.add("${key}###${value}")
        }
        continue30Map.forEach { (key, value) ->
            yingList.add("${key}###${value}")
        }

        list.sortIntSlilitDesc()
        yingList.sortIntSlilitDesc()
        val pathTime = DateUtils.formatToDay(FormatterEnum.YYYYMMDD__HH_MM)
        list.forEach {
            val key = it.split("###")[0]
            LogUtil.d("${key}-->${continue30KuiMap[key]}")
            LogUtil.d("${key}-->${continue30Map[key]}")
            FileLogUtil.d(
                "${parentBasePath}kui_continue30map${pathTime}",
                "---------------------------------------"
            )
            FileLogUtil.d(
                "${parentBasePath}kui_continue30map${pathTime}",
                "${continue30KuiMap[key]}-->${key}"
            )
            FileLogUtil.d(
                "${parentBasePath}kui_continue30map${pathTime}",
                "${continue30Map[key]}-->${key}"
            )
        }
        yingList.forEach {
            val key = it.split("###")[0]
            LogUtil.d("${key}-->${continue30KuiMap[key]}")
            LogUtil.d("${key}-->${continue30Map[key]}")
            FileLogUtil.d(
                "${parentBasePath}ying_continue30map${pathTime}",
                "---------------------------------------"
            )
            FileLogUtil.d(
                "${parentBasePath}ying_continue30map${pathTime}",
                "${continue30Map[key]}-->${key}"
            )
            FileLogUtil.d(
                "${parentBasePath}ying_continue30map${pathTime}",
                "${continue30KuiMap[key]}-->${key}"
            )
        }
    }

    private fun addContinue30(
        allReasoning30Bean: ReasoningRevBean,
        continue30: Boolean
    ): Boolean {
        var continue301 = continue30

        when ("${allReasoning30Bean.mA_1}&&${allReasoning30Bean.mA_3}") {
            "8914&&9814", "9814&&9814", "9814&&8914", "9814&&9184", "4198&&4189", "4189&&4189", "8914&&9184", "4189&&4198", "8914&&8914"
            ->
                when ("${allReasoning30Bean.mA_1}&&${allReasoning30Bean.mA_3}&&${allReasoning30Bean.mA_5}") {
                    "4189&&4198&&4918",
                    "4189&&4189&&1498",
                    "4189&&4189&&1489",
                    "4189&&4198&&1894",
                    "8914&&8914&&1894",
                    "8914&&9814&&8194",
                    "9814&&9814&&1984",
                    "9814&&8914&&8149",
                    "4189&&4189&&8419",
                    "4189&&4189&&4981",
                    "8914&&9814&&1498",
                    "9814&&9814&&4189",
                    "8914&&8914&&1948",
                    "8914&&9814&&8419",
                    "4198&&4189&&8194",
                    "8914&&8914&&9841",
                    "9814&&8914&&1498",
                    "8914&&8914&&9184",
                    "9814&&9814&&1948",
                    "9814&&8914&&8941",
                    "4189&&4189&&4198",
                    "8914&&9814&&8941",
                    "9814&&9814&&8419",
                    "8914&&9814&&4198",
                    "9814&&9184&&9184",
                    "4189&&4189&&4891",
                    "4198&&4189&&1849",
                    "4189&&4198&&4981",
                    "9814&&9814&&1489",
                    "8914&&8914&&9418",
                    "9814&&8914&&4981" -> {
                        continue301 = true
                    }
                    "9814&&9184&&9814",
                    "9814&&9814&&9814",
                    "8914&&9814&&9814",
                    "9814&&9814&&8914",
                    "9814&&8914&&9814",
                    "8914&&9814&&9184",
                    "9814&&8914&&8914",
                    "8914&&9814&&8914",
                    "9814&&9814&&8194",
                    "9814&&9184&&8914",
                    "9814&&9814&&9184",
                    "9814&&9814&&9481",
                    "8914&&9184&&8914",
                    "8914&&9814&&9841",
                    "9814&&8914&&9841",
                    "8914&&9814&&9418" -> {
                        when (getAddjudgeStr(allReasoning30Bean)) {
                            "9814&&9814&&9184&&8914",
                            "9814&&9814&&8914&&8194",
                            "9814&&9814&&8914&&1894",
                            "8914&&9814&&9418&&4918",
                            "9814&&9814&&9814&&1984",
                            "9814&&9184&&9814&&4198",
                            "9814&&9814&&9814&&9481",
                            "8914&&9184&&8914&&9148",
                            "9814&&9184&&8914&&8194",
                            "8914&&9814&&8914&&9148",
                            "8914&&9814&&8914&&9184",
                            "9814&&9814&&9814&&4189",
                            "8914&&9814&&9841&&1894",
                            "9814&&9814&&9481&&4198",
                            "9814&&8914&&8914&&8194",
                            "8914&&9814&&8914&&1894" -> {
                                continue301 = true
                            }
                            else -> {
                                continue301 = false
                            }

                        }
                    }
                    else -> {
                        continue301 = false
                    }
                }
            "1894&&8194",
            "9814&&9841",
            "9184&&1984",
            "1894&&8914",
            "9841&&9418",
            "4918&&4189",
            "4189&&4819",
            "4819&&4918",
            "4981&&4918",
            "9814&&8941",
            "1498&&1489",
            "8149&&8914",
            "9148&&1489",
            "1984&&8194",
            "9418&&4189",
            "9148&&1849",
            "1849&&1984",
            "9418&&4198",
            "1849&&8914",
            "9814&&1948",
            "8194&&1894",
            "9814&&9418",
            "4918&&1498",
            "1489&&1894",
            "9148&&8914",
            "9481&&9481",
            "4981&&4198"
            -> continue301 = true
            else -> continue301 = false

        }
        return continue301
    }

    private fun getAddContinue30Str(
        allReasoning30Bean: ReasoningRevBean,
        mCHDDList: java.util.ArrayList<CodeHDDBean>,
        i: Int
    ) {

        when (allReasoning30Bean.f36_T) {
            -30, -20, -10, 0, 10 -> {
//                setAllReasoningBeanJudgeData(allReasoning30Bean, mCHDDList, i)
                val continue30Str = getAddjudgeStr(allReasoning30Bean)
                if (allReasoning30Bean.p < 0) {
                    if (null == continue30KuiMap.get(continue30Str)) {
                        continue30KuiMap.put(continue30Str, 1)
                    } else {
                        continue30KuiMap.put(
                            continue30Str,
                            continue30KuiMap.get(continue30Str)!! + 1
                        )
                    }
                } else if (allReasoning30Bean.p > 0) {
                    if (null == continue30Map.get(continue30Str)) {
                        continue30Map.put(continue30Str, 1)
                    } else {
                        continue30Map.put(continue30Str, continue30Map.get(continue30Str)!! + 1)
                    }
                }

            }
        }
    }

    private fun setAllReasoningBeanJudgeData(
        allReasoning30Bean: ReasoningRevBean,
        mCHDDList: java.util.ArrayList<CodeHDDBean>,
        i: Int
    ) {
        allReasoning30Bean.mA_1 = getSimpleMAValueByIndexDay(mCHDDList, i)
        //                    if (i + 1 < mCHDDList.size) {
        //                        allReasoning30Bean.ao = getAO(mCHDDList[i+1],mCHDDList[i],"")
        //                    }
        allReasoning30Bean.mA_3 = getSimpleMAValueByIndexDay(mCHDDList, i - 1)
        allReasoning30Bean.mA_5 = getSimpleMAValueByIndexDay(mCHDDList, i - 5)
        allReasoning30Bean.mA_10 = getSimpleMAValueByIndexDay(mCHDDList, i - 10)
    }

    private fun getAddjudgeStr(
        allReasoning30Bean: ReasoningRevBean
    ): String {
        val tempStr =
            "${allReasoning30Bean.mA_1}&&${allReasoning30Bean.mA_3}&&${allReasoning30Bean.mA_5}&&${allReasoning30Bean.mA_10}"
//                        "&&"+
//                "(allReasoning30Bean.f36_T==${allReasoning30Bean.f36_T}&&allReasoning30Bean.f30_T==${allReasoning30Bean.f30_T}&&allReasoning30Bean.f25_T==${allReasoning30Bean.f25_T}&&" +
//                        "allReasoning30Bean.f20_T==${allReasoning30Bean.f20_T}&&allReasoning30Bean.f15_T==${allReasoning30Bean.f15_T}&&" +
//                        "allReasoning30Bean.f10_T==${allReasoning30Bean.f10_T}&&allReasoning30Bean.f05_T==${allReasoning30Bean.f05_T}&&" +
//                        "allReasoning30Bean.f03_T==${allReasoning30Bean.f03_T})"
//
//        var result = addjudgestr
//        if (!result.contains(tempStr)) {
//            result  = if (addjudgestr.isEmpty()) tempStr else "$addjudgestr||$tempStr"
//        }
//        return result
        return tempStr
    }

    private fun setReasoningRevBeanBasicInfo(
        reasoningRevBean: ReasoningRevBean,
        code: String,
        mCHDDList: ArrayList<CodeHDDBean>,
        i: Int,
        fitlerType: Int
    ) {
        reasoningRevBean.code = code.toInt()
        reasoningRevBean.n = mCHDDList[i].name
        reasoningRevBean.d = mCHDDList[i].date
        setReasoningPMpLpCpOpDdInfo(i, mCHDDList, reasoningRevBean, code, fitlerType)
        setAllReasoningBeanJudgeData(reasoningRevBean, mCHDDList, i)
    }

    private fun setReasoningPMpLpCpOpDdInfo(
        i: Int,
        mCHDDList: ArrayList<CodeHDDBean>,
        reasoningRevBean: ReasoningRevBean,
        code: String,
        fitlerType: Int,
        isUpdateDD: Boolean = true
    ) {
        var targetIndex = -1
        if (i + Datas.REV_DAYS < mCHDDList.size) {
            val pList = ArrayList<Float>()
            for (m in (i + 1)..(i + Datas.REV_DAYS)) {
                if (mCHDDList[m].date.toInt() > reasoningRevBean.d.toInt()) {
                    if (targetIndex == -1) {
                        targetIndex = m - 1
                        LogUtil.d("code->$code,targetIndex-->${mCHDDList[targetIndex].date},reasoningDate->${reasoningRevBean.d}")
                    }
                    pList.add(
                        (BigDecimalUtils.safeDiv(
                            (mCHDDList[m].cp - mCHDDList[targetIndex].cp),
                            mCHDDList[targetIndex].cp
                        ) * 100).toKeep2()
                    )
                }
            }
            if (pList.size > 0) {
                reasoningRevBean.p = pList[pList.size - 1]
                reasoningRevBean.after_O_P = (BigDecimalUtils.safeDiv(
                    (mCHDDList[i + 1].op - mCHDDList[targetIndex].cp),
                    mCHDDList[targetIndex].cp
                ) * 100).toKeep2()
                reasoningRevBean.after_C_P = (BigDecimalUtils.safeDiv(
                    (mCHDDList[i + 1].cp - mCHDDList[targetIndex].cp),
                    mCHDDList[targetIndex].cp
                ) * 100).toKeep2()
                pList.sortFloatAsc()
                reasoningRevBean.lp = pList[0]
                reasoningRevBean.mp = pList[pList.size - 1]

                LogUtil.d("code:${code},date:${mCHDDList[i].date},fitlerType:$fitlerType-->${reasoningRevBean.p},mp${reasoningRevBean.mp},lp${reasoningRevBean.lp}")

                if (isUpdateDD) {
                    reasoningRevBean.d_D = mCHDDList[i + Datas.REV_DAYS].date
                }
            }
        }
    }

//        String[] rangeArray = ["R_N70_N60","R_N50_N40","R_N40_N30","R_N30_N20","R_N20_N10","R_N10_0","R_0_10","R_10_20"];
//        String[] dayArray = ["36","30","25","20","15","10","5","3"];
//        ArrayList mtbList = new ArrayList<String>();
//    fun getAllChddByCodeName() {
//        for (index in 1..4) {
//            getLastMonthChddList((it[i].date.toInt() - 100 * index).toString(), code)?.let {
//                for (i in (it.size - 1) downTo 0) {
//                    mCHDDList.add(it[i])
//                }
//            }
//        }
//    }

    //更新P
    fun updateReasoningTB() {
//        2 -> "All_Reasoning_30"
//        else -> "All_Reasoning_50"
        val tb30 = "All_Reasoning_30"
        val tb50 = "All_Reasoning_50"
        val r30List = DBUtils.getNullDDFromReasoningTB(tb30)
        val r50List = DBUtils.getNullDDFromReasoningTB(tb50)
        val ocoo50List = DBUtils.getNullDDFromReasoningTB(Datas.ALL_Reaoning_OC_OO_50)
        val ocoo30List = DBUtils.getNullDDFromReasoningTB(Datas.ALL_Reaoning_OC_OO_30)
        val (mList, codelist) = getCHDDDateListAndCodeList()
        r30List.forEach {
//            if (it.code != 600970) {
//                return@forEach
//            }
            val mCHDDList = getCHDDCodeAllList(mList, it.code.toString())
            for (i in mCHDDList.size - 2 * Datas.REV_DAYS..mCHDDList.size - 1) {
                setUpdateReasonigInfo(mCHDDList, i, it, tb30, 30)
            }
        }
        r50List.forEach {
//            if (it.code != 600850) {
//                return@forEach
//            }
            val mCHDDList = getCHDDCodeAllList(mList, it.code.toString())
            for (i in mCHDDList.size - 2 * Datas.REV_DAYS..mCHDDList.size - 1) {
                setUpdateReasonigInfo(mCHDDList, i, it, tb50, 50)
            }
        }
        ocoo30List.forEach {
//            if (it.code != 600478) {
//                return@forEach
//            }
            val mCHDDList = getCHDDCodeAllList(mList, it.code.toString())
            for (i in mCHDDList.size - 2 * Datas.REV_DAYS..mCHDDList.size - 1) {
                setUpdateReasonigInfo(mCHDDList, i, it, Datas.ALL_Reaoning_OC_OO_30, 30)
            }
        }
        ocoo50List.forEach {
//            if (it.code != 600598) {
//                return@forEach
//            }
            val mCHDDList = getCHDDCodeAllList(mList, it.code.toString())
            for (i in mCHDDList.size - 2 * Datas.REV_DAYS..mCHDDList.size - 1) {
                setUpdateReasonigInfo(mCHDDList, i, it, Datas.ALL_Reaoning_OC_OO_50, 50)
            }
        }

        (mActivity as NewApiActivity).setBtnResoning("updateReasoning_Finish")

    }

    private fun setUpdateReasonigInfo(
        mCHDDList: ArrayList<CodeHDDBean>,
        i: Int,
        it: ReasoningRevBean,
        tb30: String,
        fitlerType: Int
    ) {
        if (mCHDDList[i - Datas.REV_DAYS].date == it.d) {
            it.d_D = mCHDDList[i].date
        }
        if (i == mCHDDList.size - 1) {
            LogUtil.d("${it.code.toString()}--update_30--${mCHDDList[i - Datas.REV_DAYS].date}--${mCHDDList[i].date}")
            setReasoningPMpLpCpOpDdInfo(
                i - Datas.REV_DAYS,
                mCHDDList,
                it,
                it.code.toString(),
                fitlerType, false
            )
            DBUtils.updateReasoning(tb30, it)
            (mActivity as NewApiActivity).setBtnResoning("$fitlerType-->code:${it.code},date:${mCHDDList[i - Datas.REV_DAYS].date}")
        }
    }

    var hoderCodeIndex = -1
    var holderTB = "${Datas.HOLDER_TB}${DateUtils.formatToDay(FormatterEnum.YYYYMMDD)}"
    var holderCode = -1
    fun getHolderChange() {
//        DBUtils.switchDBName(Datas.OTHER_DB)
//        DBUtils.dropTable(holderTB)
//        return
        if (hoderCodeIndex == 0) {
            val holderLastCode = DBUtils.queryHolderTBLastCode(holderTB)
            if (!holderLastCode.isNullOrEmpty()) {
                holderCode = holderLastCode.toInt()
            }
            DBUtils.sqlCompleteListener = object : DBUtils.SQLCompleteListener {
                override fun onComplete() {
                    if (hoderCodeIndex < codeNameList.size) {
                        hoderCodeIndex++
                        Thread.sleep(1000)
                        val code = codeNameList[hoderCodeIndex].split(splitTag)[0]
                        (mActivity as NewApiActivity).setBtnGetHolderChange("holder_$code")
                        var msg = mHandler.obtainMessage()
                        val btnTxt = (mActivity as NewApiActivity).btnGetHolderChange.text.toString()
                        msg.what = CHECK_HOLDER
                        curCode = code.toInt()
                        msg.arg1 = code.toInt()
                        msg.obj = btnTxt
                        mHandler.sendMessageDelayed(msg, 15 * 1000)
                        getHolderChange()
                    } else {
                        curCode = -1
                    }
                }

            }
        }
        launch({
            if (hoderCodeIndex < codeNameList.size) {
                val code = codeNameList[hoderCodeIndex].split(splitTag)[0]
                if (code.toInt() > holderCode) {
                    val name = codeNameList[hoderCodeIndex].split(splitTag)[1]
                    var result = RetrofitManager.reqApi.getHolerChangeData("(securitycode%3D$code)")


                    var json = result.await()
                    mHandler.removeMessages(CHECK_HOLDER)
                    if (json.equals("[]")) {
                        hoderCodeIndex++
                        getHolderChange()
                        return@launch
                    }
                    try {
                        var holderjsonbean =
                            GsonHelper.parseArray(json, HolderChangeJsonBean::class.java)
                        val holderbean = HolderChangeBean()

//            val holderNum = 0f
//            val holderChangeNum = 0f
//            val holderChangeRate = 0f
//            val holderAvgAmount = 0f
//            val marketCap = 0f
//            val endDate: String? = null
//            val noticeDate: String? = null
//            val closePirce = 0f

                        if (holderjsonbean.size > 0) {
                            holderbean.code = code.toInt()
                            holderbean.name = name

                            holderbean.holderNum =
                                (holderjsonbean[0].holderNum.toFloat() / Datas.WAN).toKeep4()
                            holderbean.holderChangeNum =
                                (holderjsonbean[0].holderNumChange.toFloat() / Datas.WAN).toKeep4()
                            holderbean.holderChangeRate =
                                (holderjsonbean[0].holderNumChangeRate.toFloat()).toKeep2()
                            holderbean.holderAvgAmount =
                                (holderjsonbean[0].holderAvgCapitalisation.toFloat() / Datas.WAN).toKeep2()
                            holderbean.marketCap =
                                (holderjsonbean[0].totalCapitalisation.toFloat() / Datas.NUM_100M).toKeep2()
                            holderbean.endDate =
                                (holderjsonbean[0].endDate).split("T")[0].replace("-", "")
                            holderbean.noticeDate =
                                (holderjsonbean[0].noticeDate).split("T")[0].replace("-", "")
                            holderbean.closePirce = holderjsonbean[0].closePrice.toFloat().toKeep2()
                            if (holderjsonbean.size > 1) {
                                holderbean.holderNum2 =
                                    (holderjsonbean[1].holderNum.toFloat() / Datas.WAN).toKeep4()
                                holderbean.holderChangeNum2 =
                                    (holderjsonbean[1].holderNumChange.toFloat() / Datas.WAN).toKeep4()
                                holderbean.holderChangeRate2 =
                                    (holderjsonbean[1].holderNumChangeRate.toFloat()).toKeep2()
                                holderbean.holderAvgAmount2 =
                                    (holderjsonbean[1].holderAvgCapitalisation.toFloat() / Datas.WAN).toKeep2()
                                holderbean.marketCap2 =
                                    (holderjsonbean[1].totalCapitalisation.toFloat() / Datas.NUM_100M).toKeep2()
                                holderbean.endDate2 =
                                    (holderjsonbean[1].endDate).split("T")[0].replace("-", "")
                                holderbean.noticeDate2 =
                                    (holderjsonbean[1].noticeDate).split("T")[0].replace("-", "")
                                holderbean.closePirce2 =
                                    holderjsonbean[1].closePrice.toFloat().toKeep2()
                            }
                            if (holderjsonbean.size > 2) {
                                holderbean.holderNum3 =
                                    (holderjsonbean[2].holderNum.toFloat() / Datas.WAN).toKeep4()
                                holderbean.holderChangeNum3 =
                                    (holderjsonbean[2].holderNumChange.toFloat() / Datas.WAN).toKeep4()
                                holderbean.holderChangeRate3 =
                                    (holderjsonbean[2].holderNumChangeRate.toFloat()).toKeep2()
                                holderbean.holderAvgAmount3 =
                                    (holderjsonbean[2].holderAvgCapitalisation.toFloat() / Datas.WAN).toKeep2()
                                holderbean.marketCap3 =
                                    (holderjsonbean[2].totalCapitalisation.toFloat() / Datas.NUM_100M).toKeep2()
                                holderbean.endDate3 =
                                    (holderjsonbean[2].endDate).split("T")[0].replace("-", "")
                                holderbean.noticeDate3 =
                                    (holderjsonbean[2].noticeDate).split("T")[0].replace("-", "")
                                holderbean.closePirce3 =
                                    holderjsonbean[2].closePrice.toFloat().toKeep2()
                            }
                            try {

                                var curTS = System.currentTimeMillis()
                                var date = ""
                                while (date.isNullOrEmpty()) {
                                    curTS = curTS - dayMillis
                                    if (DateUtils.isWeekDay(curTS).first) {
                                        date = DateUtils.format(curTS, FormatterEnum.YYYYMMDD)
                                    }
                                }
                                val lastCP =
                                    DBUtils.queryCPByCodeAndDate(holderbean.code.toString(), date)
                                holderbean.lastCP = lastCP.toFloat()
                                val cpList = arrayListOf(
                                    holderbean.closePirce,
                                    holderbean.closePirce2,
                                    holderbean.closePirce3
                                )
                                cpList.sortFloatAsc()
                                val hlrate = ((cpList[cpList.size - 1] - cpList[0]) / cpList[0] * 100).toKeep2()
                                holderbean.hlRate = hlrate
                                cpList.add(holderbean.lastCP)
                                cpList.sortFloatAsc()
                                holderbean.hL_R_A = ((cpList[cpList.size - 1] - cpList[0]) / cpList[0] * 100).toKeep2()
                                holderbean.lastP =
                                    ((lastCP.toFloat() - holderbean.closePirce) / holderbean.closePirce * 100).toKeep2()


                                DBUtils.insertHolderBean(holderTB, holderbean)
                            } catch (e: java.lang.Exception) {

                                hoderCodeIndex++
                                getHolderChange()
                            }
                        }
                    } catch (e: Exception) {

                        hoderCodeIndex++
                        getHolderChange()
                    }

                } else {
                    hoderCodeIndex++
                    getHolderChange()
                }
            }
        })
    }


}