package com.mgc.sharesanalyse.base

import android.os.Binder
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mgc.sharesanalyse.entity.*
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import com.mgc.sharesanalyse.viewmodel.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.greenrobot.greendao.database.Database
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

open class BaseBinder:Binder() {

    //    yyyyMMdd HH:mm:ss
    var beginTime =
        DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYYMMDD) + " 09:30:00"
    var endTime =
        DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYYMMDD) + " 15:02:00"
    var noonBreakBegin =
        DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYYMMDD) + " 11:30:00"
    var noonBreakEnd =
        DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYYMMDD) + " 13:00:00"

    var db: Database? = null

    var filterStocks = ""
    var filterAnalyseStocks = ""
    var logStrList = ArrayList<String>()
    var needJudeSize = true

    var stocksCode = ""
    var splitStr = "####"
    var splitLogSumStr = "!!!!"

    var PA10TimesSpareArray = SparseArray<String>()
    var PAGe100MillionSpareArray = SparseArray<String>()
    var PAGe50MillionSpareArray = SparseArray<String>()
    var PAGe20MillionSpareArray = SparseArray<String>()
    var PAGe10MillionSpareArray = SparseArray<String>()
    var PAGe5MillionSpareArray = SparseArray<String>()

    var PSGe1000MillionSpareArray = SparseArray<String>()
    var PSGe100MillionSpareArray = SparseArray<String>()
    var PerPricesGtCurSpareArray = SparseArray<String>()
    var CurGtPerPricesSpareArray = SparseArray<String>()
    var LogSumSpareArray = SparseArray<String>()
    var tenTimesSize = 0
    var ge100mSize = 0
    var ge50mSize = 0
    var ge20mSize = 0
    var ge10mSize = 0
    var ge5mSize = 0
    var gt1000TimesSize = 0
    var gt100TimesSize = 0
    var PPGtCurSize = 0
    var CurGtPPSize = 0
    var sizeCount = 0
    var fileNameList: ArrayList<String>? = null
    var isInit = true
    var intervalTime = 25.toLong()
    var parentPath = DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYY_MM)
    //    var logResultPath = parentPath + "/" +
//            DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYY_MM_DD) + "logResult"
    var logAlonePath = parentPath + "/" +
            DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYY_MM_DD) + "logAlone"
    var logAloneSumEndSplitStr = "==============================================="
    var requestTime = 0.toLong()
    var dbNameEndStr = ""
    var isDebug = true
    var needRecordJson = false
    var viewModel: MainViewModel? = null
    var activity:AppCompatActivity? = null

    fun setViewModel(context: AppCompatActivity, viewmodel: MainViewModel) {
        viewModel = viewmodel
        activity = context

        viewModel!!.loadState.observe(activity!!, androidx.lifecycle.Observer {

            when (it) {
                is LoadState.Success -> {

                    LogUtil.d("mViewModel!! size():${viewModel!!.sharesDats.value!!.size()}")
                    for (index in 0..viewModel!!.sharesDats.value!!.size() - 1) {
                        val data = viewModel!!.sharesDats.value?.get(index)
                        var month8Data = Month8Data()
                        month8Data.json = data
                        month8Data.name = index.toString()
                        month8Data.timeStamp = System.currentTimeMillis()
                        month8Data.ymd =
                            DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYYMMDD)
                        DaoUtilsStore.getInstance().month8DataDaoUtils.insert(month8Data)
                    }
                    if ((System.currentTimeMillis() >= DateUtils.parse(
                            beginTime,
                            FormatterEnum.YYYYMMDD__HH_MM_SS
                        ) &&
                                System.currentTimeMillis() <= DateUtils.parse(
                            noonBreakBegin,
                            FormatterEnum.YYYYMMDD__HH_MM_SS
                        )) ||
                        (System.currentTimeMillis() >= DateUtils.parse(
                            noonBreakEnd,
                            FormatterEnum.YYYYMMDD__HH_MM_SS
                        ) &&
                                System.currentTimeMillis() <= DateUtils.parse(
                            endTime,
                            FormatterEnum.YYYYMMDD__HH_MM_SS
                        )) || isDebug
                    ) {
                        classifyStocks()
//                        tvTime.setText(DateUtils.formatToDay(FormatterEnum.HH_MM_SS))

                    }
                    if (System.currentTimeMillis() <= DateUtils.parse(
                            endTime,
                            FormatterEnum.YYYYMMDD__HH_MM_SS
                        )|| isDebug
                    ) {
                        GlobalScope.launch {

                            withContext(Dispatchers.IO) {

                                runBlocking {
                                    intervalTime = 0.toLong()
                                    LogUtil.d("requestDatas requestTime:$requestTime ,diff:${(System.currentTimeMillis() - requestTime) / 1000}")
                                    if (System.currentTimeMillis() - requestTime >= (30 * 1000)) {
                                        intervalTime = 0.toLong()
                                    } else {
                                        intervalTime = System.currentTimeMillis() - requestTime
                                        delay(30 * 1000 - intervalTime)
                                    }
                                    requestDatas(viewModel!!)
                                }
                            }
                        }
                    }


                }
                is LoadState.Fail -> {

                }
                is LoadState.Loading -> {

                }
            }

        })
    }


    private fun classifyStocks() {
        for (index in 0..(viewModel!!.sharesDats.value!!.size() - 1)) {
            val queryAll = DaoUtilsStore.getInstance().month8DataDaoUtils.queryByQueryBuilder(
                Month8DataDao.Properties.Name.eq(index.toString())
            )
            queryAll.forEach {
                var bean = it
                val replace = it.json.replace("var hq_str_sh\"", "").replace("var hq_str_sz\"", "").replace("\"", "")
                val split = replace.split(";")
                var mProgress = 0
                split.forEach {
                    if (it.contains(",")) {
                        val split3 = it.split(",")
                        val stcokBean = setStcokBean(split3, bean)
                    }
                    mProgress++
                }
            }
        }
        LogUtil.d("classifyStocks complete!!!")
    }


    private fun logSum() {
        var logAloneList = FileUtil.getFileNameList(
            FileLogUtil.FilePath + "/$parentPath",
            "logResult.txt",
            "logRecordSum.txt",
            "logAloneSum.txt"
        )
        var additcionSparseArray = SparseArray<String>()
        LogSumSpareArray.clear()

        Collections.sort(logAloneList, object : java.util.Comparator<String> {
            override fun compare(p0: String, p1: String): Int {
                return p1.toDateCompare().compareTo(p0.toDateCompare())
            }
        })
        val logSumList =
            FileUtil.getFileNameList(FileLogUtil.FilePath + "/$parentPath/logSum", "666666")
        var logSumLimitTimeStamp = 0.toLong()
        if (logSumList.size > 0) {
            LogUtil.d("logSumList size:${logSumList.size},file name:${logSumList.get(0)}")
            Collections.sort(logSumList, object : Comparator<String> {
                override fun compare(p0: String, p1: String): Int {
                    return p1.logAlongSumtoTimeStamp().compareTo(p0.logAlongSumtoTimeStamp())
                }
            })
            logSumLimitTimeStamp = logSumList[0].logAlongSumtoTimeStampYMD()
            FileUtil.getStringByFile(
                FileLogUtil.FilePath + "/$parentPath/logSum/" + logSumList.get(0),
                0,
                object :
                    FileUtil.IOStringListener {
                    override fun finish(index: Int, content: String) {
                        val logSumSplit = content.split(logAloneSumEndSplitStr)
                        logSumSplit.forEach {
                            if (it.contains(splitLogSumStr)) {
                                var code =
                                    it.split(splitLogSumStr)[0].split(",p:")[0].replace("---", "")
                                LogUtil.d(
                                    "additcionSparseArray!!!${it.split(splitLogSumStr)[1].contains(
                                        "    "
                                    )}"
                                )
                                var value = it.split(splitLogSumStr)[1].replace("    ", splitStr)
                                value = value.replace("$it---n:", "\r\n$it---n:")
                                additcionSparseArray.put(code.toInt(), value)
                            }
                        }
                    }

                })
        }


        foreachLogAloneIOString(0, logAloneList, logSumLimitTimeStamp)

        var logname = "$parentPath/logSum/logAloneSum_${DateUtils.format(
            System.currentTimeMillis(),
            FormatterEnum.YYYYMMDD__HH_MM_SS
        )}"
        LogUtil.d("logSum logname:$logname")
        LogUtil.d("logSum LogSumSpareArray.size:${LogSumSpareArray.size()}")
        val logSumArray = ArrayList<String>()
        viewModel!!.stocksArray.forEach {
            var codeSumResult = LogSumSpareArray[it.toInt()]
            var addictionStr = additcionSparseArray[it.toInt()]
            if (!addictionStr.isNullOrEmpty()) {
                addictionStr = addictionStr.replace("!!(", "$splitStr!!(")
                codeSumResult =
                    (if (codeSumResult.isNullOrEmpty()) "" else (codeSumResult)) + addictionStr.replace(
                        "$it---n:",
                        "\n$it---n:"
                    )
            }

            if (null != codeSumResult) {

                val split = codeSumResult.split("$it---n:")
                LogUtil.d("logSum codeSumResult size:${split.size}")
                var endValue = 0.toDouble()
                var maxValue = 0.toDouble()
                var maxValueDate = ""
                for (index in 0 until split.size) {
                    var s = split[index]
                    if (s.contains(splitStr)) {
                        s = s.split(splitStr)[0]
                    }
                    if (s.contains(",c:")) {
                        LogUtil.d("logSum for value:$s")
                        var curValue = s.split(",c:")[1].split(",p:")[0].toDouble()
                        if (endValue == 0.toDouble()) {
                            endValue = curValue
                        }
                        if (curValue > maxValue) {
                            maxValue = curValue
                            maxValueDate = s.split("%,")[1]
                        }
                    }
                }
                var startValue = 0.toDouble()
                for (index in split.size - 1 downTo 0) {
                    if (split[index].contains(",c:")) {
                        startValue = split[index].split(",c:")[1].split(",p:")[0].toDouble()
                        break
                    }
                }

                logSumArray.add(
                    "\n---$it,p:${endValue.getPercent(startValue)}%,max:$maxValue,maxP:${maxValue.getPercent(
                        startValue
                    )}%,maxDate:$maxValueDate$splitLogSumStr\n$codeSumResult\n$logAloneSumEndSplitStr"
                )
                LogUtil.d("logSum LogSumSpareArray:$codeSumResult")
            }
        }
        Collections.sort(logSumArray, object : Comparator<String> {
            override fun compare(p0: String, p1: String): Int {
                return p1.toLogSumSizeCompare("---n").compareTo(p0.toLogSumSizeCompare("---n"))
            }
        })
        logSumArray.forEach {
            FileLogUtil.d(logname, it.replace(splitStr, "\n    "))
        }
        LogUtil.d("----------logSum complete-------------")

    }





    private fun foreachLogAloneIOString(
        index: Int,
        logList: java.util.ArrayList<String>,
        limitTimeStamp: Long
    ) {
        if (logList.size > 0 && index < logList.size) {
            FileUtil.getStringByFile(
                FileLogUtil.FilePath + "/$parentPath/" + logList.get(index),
                index,
                object :
                    FileUtil.IOStringListener {
                    override fun finish(index: Int, content: String) {
                        LogUtil.d("foreachIOString $index size${logList.size}:$content")
                        val split = content.split("---c:")
                        LogUtil.d("foreachIOString forEach size:${split.size}")
                        val date = logList[index - 1].replace("logAlone.txt", "")
                        split.forEach {
                            if (it.contains("---n")) {
                                var reslut = it.replace(
                                    "!!!",
                                    ",$date}"
                                )
                                LogUtil.d("foreachIOString forEach:$reslut")
                                LogUtil.d("foreachIOString limitTimeStamp:$limitTimeStamp,date:$date,date TimeStamp:${date.toTimeStampYMD()},boolean:${date.toTimeStampYMD() > limitTimeStamp}")
                                val sumStr = reslut.split("---")
                                if (date.toTimeStampYMD() > limitTimeStamp) {
                                    LogSumSpareArray.putLogSum(sumStr[0].toInt(), reslut)
                                }
                            }
                        }
                        if (index < logList.size) {
                            foreachLogAloneIOString(index, logList, limitTimeStamp)
                        }
                    }

                })
        }

    }






    private fun logResult() {
        filterAnalyseStocks = ""
        val shCodeList = ResUtil.getSArray(viewModel!!.viewModelCodeName)
//        FileLogUtil.d(
//            logResultPath,
//            "!!!!!!!!!!start_______${DateUtils.format(
//                System.currentTimeMillis(),
//                FormatterEnum.YYYYMMDD__HH_MM_SS
//            )}"
//        )
        var logALoneList = ArrayList<String>()
        shCodeList.forEach {
            sizeCount = 0
            var code = it.split(splitStr)[0]
            var name = it.split(splitStr)[1]
            logStrList.clear()
            needJudeSize = true
            var tag = "logResult_" + code
            val perAmountList =
                DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.queryByQueryBuilder(
                    AnalysePerAmountBeanDao.Properties.Code.eq(code.toInt())
                )
            val perStockList =
                DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.queryByQueryBuilder(
                    AnalysePerStocksBeanDao.Properties.Code.eq(code.toInt())
                )
            val perPricesList =
                DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.queryByQueryBuilder(
                    AnalysePerPricesBeanDao.Properties.Code.eq(code.toInt())
                )
            val lastBean = CommonDaoUtils.queryLast(db, code.toLong())
            perAmountList.forEach {
                if (!filterAnalyseStocks.contains(it.code.toString())) {
                    filterAnalyseStocks =
                        filterAnalyseStocks + "_" + it.code.toString()
                }
                logStrList.add("------------------------------------------------------------------------------------------------")
                ge100mSize = logBySplite(it.ge100million, "pa_ge100m")
                if (logStrList.size > 1) {
                    needJudeSize = false
                }
                tenTimesSize = logBySplite(it.tenTimesLast, "pa_10Last")
                ge50mSize = logBySplite(it.ge50million, "pa_ge50m")
                ge20mSize = logBySplite(it.ge20million, "pa_ge20m")
                ge10mSize = logBySplite(it.ge10million, "pa_ge10m")
                ge5mSize = logBySplite(it.ge5million, "pa_ge5m")
            }
            perStockList.forEach {
                if (!filterAnalyseStocks.contains(it.code.toString())) {
                    filterAnalyseStocks =
                        filterAnalyseStocks + "_" + it.code.toString()
                }
                logStrList.add("————————————————————————————————————————————————————————————————————————————————————————————————————")
                gt1000TimesSize = logBySplite(it.gt1000times, "ps_gt1000times")
                gt100TimesSize = logBySplite(it.gt100times, "ps_gt100times")
            }
            perPricesList.forEach {
                if (!filterAnalyseStocks.contains(it.code.toString())) {
                    filterAnalyseStocks =
                        filterAnalyseStocks + "_" + it.code.toString()
                }
                logStrList.add("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                PPGtCurSize = logBySplite(it.perPricesGtCur, "pp_perPricesGtCur")
                CurGtPPSize = logBySplite(it.curGtPerPrices, "pp_curGtPerPrices")
            }


            if (sizeCount >= Datas.limitSize || !needJudeSize) {
                logSize(code, name, lastBean, logALoneList)
                logStrList.forEach {
//                    FileLogUtil.d(logResultPath, it)
                    Log.d(tag, it)
                }
//                FileLogUtil.d(logResultPath, "----close prices-----:${lastBean.current}!!!")
//                FileLogUtil.d(
//                    logResultPath,
//                    "=============================================================================================================="
//                )
                LogUtil.d(
                    tag,
                    "==========================================================Complete===================================================="
                )
            }

        }

        Collections.sort(logALoneList, object : Comparator<String> {
            override fun compare(p0: String, p1: String): Int {
                return p1.toLogCompare().compareTo(p0.toLogCompare())
            }
        })
        logALoneList.forEach {
            FileLogUtil.d(
                logAlonePath,
                it
            )
        }
    }

    private fun logAloneAdd(
        limitStr: String,
        logAloneStr: String
    ): String {
        var addStr = ""
        LogUtil.d("logAloneAdd limitStr:${limitStr}")
        LogUtil.d("logAloneAdd:${limitStr.contains(")") && limitStr.contains(":(")}")
        if (limitStr.contains(")") && limitStr.contains(":(")) {
            when (limitStr.split(":(")[0]) {
                "pa_10Last" -> {
                    addStr = limiWhenLog10ts(logAloneStr, addStr, "pa>10Last", limitStr)
                }
                "pa_ge100m" -> {
                    addStr = limiWhenLog(logAloneStr, addStr, "pa>100m", limitStr)
                }
                "pa_ge50m" -> {
                    addStr = limiWhenLog(logAloneStr, addStr, "pa>50m", limitStr)
                }
                "pa_ge20m" -> {
                    addStr = limiWhenLog(logAloneStr, addStr, "pa>20m", limitStr)
                }
                "pa_ge10m" -> {
                    addStr = limiWhenLog(logAloneStr, addStr, "pa>10m", limitStr)
                }
                "pa_ge5m" -> {
                    addStr = limiWhenLog(logAloneStr, addStr, "pa>5m", limitStr)
                }
                "ps_gt1000times" -> {
                    addStr = limiWhenLog(logAloneStr, addStr, "ps>1000ts", limitStr)
                }
                "ps_gt100times" -> {
                    addStr = limiWhenLog(logAloneStr, addStr, "ps>100ts", limitStr)
                }
                "pp_perPricesGtCur" -> {
                    addStr = limiWhenLogPP(logAloneStr, addStr, "pp>cur", limitStr)
                }
                "pp_curGtPerPrices" -> {
                    addStr = limiWhenLogPP(logAloneStr, addStr, "pp<cur", limitStr)
                }

            }
        }
        return logAloneStr + addStr
    }

    private fun limiWhenLog10ts(
        logAloneStr: String,
        addStr: String,
        lable: String,
        limitStr: String
    ): String {
        var addStr1 = addStr
        if (!logAloneStr.contains(lable)) {
            addStr1 = "$splitStr\n" + lable
        }
        addStr1 = addStr1 + limitStr.replace("perAmount:", "").replace("lastPerAmount:", "")
        return addStr1
    }

    private fun limiWhenLog(
        logAloneStr: String,
        addStr: String,
        lable: String,
        limitStr: String
    ): String {
        var addStr1 = addStr
        if (!logAloneStr.contains(lable)) {
            addStr1 = "$splitStr\n" + lable
        }
        var time = limitStr.split(":(")[1].split(",")[0]
        var attribute = limitStr.split(":(")[1].split(",")[1].split(",cur:")[0]
        var percent = limitStr.split(",p:")[1].split(",h/l")[0]
        addStr1 = addStr1 + "($time,$attribute,$percent)"
        return addStr1
    }


    private fun limiWhenLogPP(
        logAloneStr: String,
        addStr: String,
        lable: String,
        limitStr: String
    ): String {
        LogUtil.d("logAloneAdd limiWhenLogPP:$limitStr")
        var addStr1 = addStr
        if (!logAloneStr.contains(lable)) {
            addStr1 = "$splitStr\n" + lable
        }
        var time = limitStr.split(":(")[1].split(",")[0]
        LogUtil.d("logAloneAdd limiWhenLogPP:${limitStr.split(":(")[1]}")
        LogUtil.d("logAloneAdd limiWhenLogPP:${limitStr.split(":(")[1].split("dif:")[1]}")
        var attribute = limitStr.split(":(")[1].split("dif:")[1]
        addStr1 = addStr1 + "($time,$attribute)"
        return addStr1
    }

    private fun logSize(
        code: String,
        name: String,
        lastBean: StocksBean,
        logALoneList: ArrayList<String>
    ) {
//        FileLogUtil.d(
//            logResultPath,
//            "---c:${code}---n:$name,s:${logStrList.size}${tenTimesSize.toLog(",10ts")}${ge100mSize.toLog(
//                ",100ms"
//            )},${ge50mSize.toLog("50ms")}${ge20mSize.toLog(",20ms")}${ge10mSize.toLog(",10ms")}${ge5mSize.toLog(
//                ",5ms"
//            )}" +
//                    "${gt1000TimesSize.toLog(",1000ts")}${gt100TimesSize.toLog(",100ts")}${PPGtCurSize.toLog(
//                        ",ppGt"
//                    )}${CurGtPPSize.toLog(",curGt")},ys:${lastBean.close},o:${lastBean.open},c:${lastBean.current},p:${getCurPercent(
//                        lastBean.current,
//                        lastBean.close
//                    )}!!!"
//        )
        var logAloneStr =
            "---c:${code}---n:$name,s:${logStrList.size}${tenTimesSize.toLog(",10ts")}${ge100mSize.toLog(
                ",100ms"
            )}${ge50mSize.toLog(",50ms")}${ge20mSize.toLog(",20ms")}${ge10mSize.toLog(",10ms")}${ge5mSize.toLog(
                ",5ms"
            )}" +
                    "${gt1000TimesSize.toLog(",1000ts")}${gt100TimesSize.toLog(",100ts")}${PPGtCurSize.toLog(
                        ",ppGt"
                    )}${CurGtPPSize.toLog(",curGt")},ys:${lastBean.close},o:${lastBean.open},c:${lastBean.current},p:${getCurPercent(
                        lastBean.current,
                        lastBean.close
                    )}!!!"

        logStrList.forEach {
            logAloneStr = logAloneAdd(it, logAloneStr)
        }
        logALoneList.add(logAloneStr)

    }

    private fun logBySplite(it: String?, key: String): Int {
        it?.let {
            if (!it.isEmpty()) {
                val temTimesLastList = it.split(splitStr)
//                var tempList = ArrayList<String>()
//                temTimesLastList.forEach {
//                    if (!it.contains("13:23:16")) {
//                        tempList.add(it)
//                    }
//                }
//                logStrList.add("$key size:${tempList.size}")
//                sizeCount = sizeCount + tempList.size
//                tempList.forEach {
//                    logStrList.add("$key:$it")
//                }
//                return tempList.size
                logStrList.add("$key size:${temTimesLastList.size}")
                sizeCount = sizeCount + temTimesLastList.size
                temTimesLastList.forEach {
                    logStrList.add("$key:$it")
                }
                return temTimesLastList.size
            }
        }
        return 0
    }

    private fun requestDatas(viewModel: MainViewModel) {
        LogUtil.d("requestDatas!!!")
        val queryAll = DaoUtilsStore.getInstance().month8DataDaoUtils.queryAll()
        if (queryAll.size > 0) {
            DaoUtilsStore.getInstance().month8DataDaoUtils.deleteAll()
        }
        requestTime = System.currentTimeMillis()
        viewModel.requestData()
    }





    private fun getAddLog(it: StocksBean) =
        ",cur:${it.current},open:${it.open},h:${it.hightest},l:${it.lowest},p:${getCurPercent(
            it.current,
            it.close
        )},h/l:${getHLPercent(it.hightest, it.lowest)},sp:${getStopPrices(it.close)}"


    private fun getSimpleAddLog(it: StocksBean) =
        ",cur:${it.current},open:${it.open},p:${getCurPercent(
            it.current,
            it.close
        )},sp:${getStopPrices(it.close)}"

    private fun getStopPrices(open: Double?): String {
        if (null != open) {
            return BigDecimalUtils.add(open, BigDecimalUtils.mul(open, 0.1, 2)).toString()
        } else return "0"
    }

    private fun getHLPercent(hightest: String?, lowest: String?): String {
        val hightest = hightest?.toDoubleOrNull()
        val lowest = lowest?.toDoubleOrNull()
        if (null != hightest && null != lowest && lowest > 0) {
            val df = DecimalFormat("#,##0.00")
            return df.format(
                BigDecimalUtils.div(
                    BigDecimalUtils.sub(hightest, lowest),
                    lowest
                ) * 100
            ) + "%"
        } else return "0"
    }

    private fun getCurPercent(current: Double, open: Double?): String {
        if (null != open && open > 0) {
            val df = DecimalFormat("#,##0.00")
            return (df.format(
                (BigDecimalUtils.div(
                    BigDecimalUtils.sub(current, open),
                    open
                ) * 100)
            ) + "%")
        } else return "0%"
    }

    private fun getCurPercentDouble(current: Double, open: Double): Double {
        if (open == 0.toDouble()) {
            return 0.toDouble()
        }
        val result = BigDecimalUtils.div(
            BigDecimalUtils.sub(current, open),
            open
        ) * 100
        return result
    }


    private fun analyseNeedReturn(): Boolean {
        val toDayStr = DateUtils.format(
            System.currentTimeMillis(),
            FormatterEnum.YYYY_MM_DD
        )
        DaoManager.DB_NAME
        if (System.currentTimeMillis() < DateUtils.parse(
                endTime,

                FormatterEnum.YYYYMMDD__HH_MM_SS
            ) && DaoManager.DB_NAME.contains(toDayStr)
        ) {
            Toast.makeText(activity, "wait", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun setSpareArrayData(map: SparseArray<String>, stocksCode: String, str: String) {
        if (!filterAnalyseStocks.contains(stocksCode)) {
            filterAnalyseStocks =
                filterAnalyseStocks + "_" + stocksCode
        }
        if (map[stocksCode.toInt()].isNullOrEmpty()) {
            map.put(stocksCode.toInt(), str)
        } else {
            map.put(
                stocksCode.toInt(),
                map[stocksCode.toInt()] + splitStr + str
            )
        }
    }


    private fun setStcokBean(
        split: List<String>,
        bean: Month8Data
    ): StocksBean {
        val nameSplit = split[0].replace("var hq_str_sz", "").replace("var hq_str_sh", "").replace("\n", "").split("=")
        stocksCode = nameSplit[0]
        var isInsert = false


        var stocksBean = DaoUtilsStore.getInstance().stocksBeanDaoUtils.queryById(stocksCode.toLong())
        if (null == stocksBean) {
            isInsert = true
            stocksBean = StocksBean()
            stocksBean.id = stocksCode.toLong()
        }

        var stocksJsonBean = DaoUtilsStore.getInstance().stocksJsonBeanCommonDaoUtils.queryById(stocksCode.toLong())


        if (null == stocksJsonBean) {
            stocksJsonBean = StocksJsonBean()
            stocksJsonBean.id = stocksCode.toLong()
        }

        var sbRecordBean = RecordBean()



        stocksBean.time = DateUtils.format(bean.timeStamp, FormatterEnum.HH_MM_SS)
        stocksBean.open = split[1].toDouble()
        stocksBean.close = split[2].toDouble()
        stocksBean.current = split[3].toDouble()
        stocksBean.hightest = split[4]
        stocksBean.lowest = split[5]
        stocksBean.dealAmount = split[9].toDiv10000()
        stocksBean.dealStocks = split[8].toDiv100()

        var b1 = BigDecimalUtils.mul(split[11].toDouble(), split[10].toDouble(),3)

        var b2 = BigDecimalUtils.mul(split[13].toDouble(), split[12].toDouble(),3)

        var b3 = BigDecimalUtils.mul(split[15].toDouble(), split[14].toDouble(),3)

        var b4 = BigDecimalUtils.mul(split[17].toDouble(), split[16].toDouble(),3)

        var b5 = BigDecimalUtils.mul(split[19].toDouble(), split[18].toDouble(),3)


        var s1 = BigDecimalUtils.mul(split[21].toDouble(), split[20].toDouble(),3)

        var s2 = BigDecimalUtils.mul(split[23].toDouble(), split[22].toDouble(),3)

        var s3 = BigDecimalUtils.mul(split[25].toDouble(), split[24].toDouble(),3)

        var s4 = BigDecimalUtils.mul(split[27].toDouble(), split[26].toDouble(),3)

        var s5 = BigDecimalUtils.mul(split[29].toDouble(), split[28].toDouble(),3)

        stocksJsonBean.time = DateUtils.format(bean.timeStamp, FormatterEnum.HH_MM_SS)

        sbRecordBean.time = DateUtils.format(bean.timeStamp, FormatterEnum.HH_MM_SS)

        sbRecordBean.bAmount = (b1 + b2 + b3 + b4 + b5)/10000.0f

        sbRecordBean.sAmount = (s1 + s2 + s3 + s4 + s5)/10000.0f

        sbRecordBean.bsDiffAmount = BigDecimalUtils.sub(sbRecordBean.bAmount,sbRecordBean.sAmount)

        if ((sbRecordBean.sAmount + sbRecordBean.bAmount) != 0.toDouble()) {
            sbRecordBean.bPercent = BigDecimalUtils.div(
                sbRecordBean.bAmount,
                sbRecordBean.sAmount + sbRecordBean.bAmount
            )
        }


        stocksBean.buy1 = split[11] + "_" + split[10].toDiv100()
        stocksBean.buy2 = split[13] + "_" + split[12].toDiv100()
        stocksBean.buy3 = split[15] + "_" + split[14].toDiv100()
        stocksBean.buy4 = split[17] + "_" + split[16].toDiv100()
        stocksBean.buy5 = split[19] + "_" + split[18].toDiv100()

        stocksBean.sale1 = split[21] + "_" + split[20].toDiv100()
        stocksBean.sale2 = split[23] + "_" + split[22].toDiv100()
        stocksBean.sale3 = split[25] + "_" + split[24].toDiv100()
        stocksBean.sale4 = split[27] + "_" + split[26].toDiv100()
        stocksBean.sale5 = split[29] + "_" + split[28].toDiv100()

        val lastStockBean = CommonDaoUtils.queryLast(db, stocksCode.toLong())

        if (null != lastStockBean) {
            stocksBean.perStocks = BigDecimalUtils.sub(
                stocksBean.dealStocks.toDouble(),
                lastStockBean.dealStocks.toDouble()
            )
            stocksBean.perAmount = BigDecimalUtils.sub(
                stocksBean.dealAmount.toDouble(),
                lastStockBean.dealAmount.toDouble()
            )
            if (stocksBean.perStocks.toDouble() > 0) {
                stocksBean.perPrice = BigDecimalUtils.mul(
                    BigDecimalUtils.div(
                        stocksBean.perAmount,
                        stocksBean.perStocks.toDouble()
                    ), 100.toDouble(), 4
                )
            } else {
                stocksBean.perPrice = 0.toDouble()
            }
        } else {
            stocksBean.perStocks = stocksBean.dealStocks.toDouble()
            stocksBean.perAmount = stocksBean.dealAmount.toDouble()
            if (stocksBean.dealStocks.toDouble() > 0) {
                stocksBean.perPrice = BigDecimalUtils.mul(
                    BigDecimalUtils.div(
                        stocksBean.dealAmount.toDouble(),
                        stocksBean.dealStocks.toDouble()
                    ), 100.toDouble(), 3
                )
            } else {
                stocksBean.perPrice = 0.toDouble()
            }
        }

        val sizeBeanList =
            DaoUtilsStore.getInstance().analyseSizeBeanDaoUtils.queryByQueryBuilder(
                AnalyseSizeBeanDao.Properties.Code.eq(stocksCode.toInt())
            )

        var sizeBean: AnalyseSizeBean
        if (sizeBeanList.size > 0) {
            sizeBean = sizeBeanList[0]
        } else {
            sizeBean = AnalyseSizeBean()
            sizeBean.code = stocksCode.toInt()
        }
        if (split[11].toDouble() <= 0 || split[13].toDouble() <= 0 || split[15].toDouble() <= 0 || split[17].toDouble() <= 0 || split[19].toDouble() <= 0) {
            val b0Log = getB0Log(split)
            sizeBean.b0 = sizeBean.b0 + b0Log.second
            var str =
                "(${stocksBean.time},b0 size:${sizeBean.b0}${b0Log.first}${getSimpleAddLog(
                    stocksBean
                )})"
            sizeBean.b0Str =
                if (sizeBean.b0Str.isNullOrEmpty()) str else sizeBean.b0Str + splitStr + str
        }
        if (split[21].toDouble() <= 0 || split[23].toDouble() <= 0 || split[25].toDouble() <= 0 || split[27].toDouble() <= 0 || split[29].toDouble() <= 0) {
            var s0log = getS0Log(split)
            sizeBean.s0 = sizeBean.s0 + s0log.second
            var str =
                "(${stocksBean.time},s0 size:${sizeBean.s0}${s0log.first}${getSimpleAddLog(
                    stocksBean
                )})"
            sizeBean.s0Str =
                if (sizeBean.s0Str.isNullOrEmpty()) str else sizeBean.s0Str + splitStr + str
        }
        var sizeRecord = sizeBean.countSize
        if (null != lastStockBean) {
            updatePA(lastStockBean, stocksBean, sizeBean)
            updatePP(stocksBean, sizeBean)
        }
        updatePS(stocksBean, sizeBean)

        sizeBean.percent = getCurPercentDouble(stocksBean.current, stocksBean.close)
        sizeBean.current = stocksBean.current
        updateOrInsertSizeBean(sizeBeanList, sizeBean)

        stocksBean.json = ""

        stocksJsonBean.amount = stocksBean.dealAmount.toDouble()

        stocksJsonBean.percent = getCurPercentDouble(stocksBean.current, stocksBean.close)

        if (needRecordJson) {
            if (!stocksJsonBean.jsonRecord.isNullOrEmpty()) {

                var stockListBean = GsonHelper.getInstance()
                    .fromJson(stocksJsonBean.jsonRecord, StockListBean::class.java)
                stockListBean.stocksBeanList.add(stocksBean)
                stocksJsonBean.jsonRecord = GsonHelper.toJson(stockListBean)
            } else {

                var stockListBean = StockListBean()
                stockListBean.stocksBeanList = ArrayList()
                stockListBean.stocksBeanList.add(stocksBean)
                stocksJsonBean.jsonRecord = GsonHelper.toJson(stockListBean)
            }
        }


        if (!stocksJsonBean.sbRecord.isNullOrEmpty()) {
            LogUtil.d("setStcokBean sbRecord:${stocksJsonBean.sbRecord}")
            var sBRecordBean = GsonHelper.getInstance()
                .fromJson(stocksJsonBean.sbRecord, SBRecordBean::class.java)
            sBRecordBean.recordBeans.add(sbRecordBean)
            stocksJsonBean.sbRecord = GsonHelper.toJson(sBRecordBean)
        } else {

            var sBRecordBeanList = SBRecordBean()
            sBRecordBeanList.recordBeans = ArrayList()
            sBRecordBeanList.recordBeans.add(sbRecordBean)
            stocksJsonBean.sbRecord = GsonHelper.toJson(sBRecordBeanList)
        }


        DaoUtilsStore.getInstance().stocksJsonBeanCommonDaoUtils.updateOrInsertById(stocksJsonBean,stocksCode.toLong())
        stocksBean.json = GsonHelper.toJson(stocksBean)

        if (isInsert) {
            val insert = DaoUtilsStore.getInstance().stocksBeanDaoUtils.insert(stocksBean)
        } else {
            val update = DaoUtilsStore.getInstance().stocksBeanDaoUtils.update(stocksBean)
        }

        return stocksBean
    }

    private fun getB0Log(split: List<String>): Pair<String, Int> {
        var size = 0
        var str = ""
        size++
        if (split[11].toDouble() <= 0) {
            str = str + ",01"
        }
        if (split[13].toDouble() <= 0) {
            str = str + ",02"
        }
        if (split[15].toDouble() <= 0) {
            str = str + ",03"
        }
        if (split[17].toDouble() <= 0) {
            str = str + ",04"
        }
        if (split[19].toDouble() <= 0) {
            str = str + ",05"
        }
        return Pair(str, size)
    }

    private fun getS0Log(split: List<String>): Pair<String, Int> {
        var size = 0
        var str = ""
        size++
        if (split[21].toDouble() <= 0) {
            str = str + ",01"
        }
        if (split[23].toDouble() <= 0) {
            str = str + ",02"
        }
        if (split[25].toDouble() <= 0) {
            str = str + ",03"
        }
        if (split[27].toDouble() <= 0) {
            str = str + ",04"
        }
        if (split[29].toDouble() <= 0) {
            str = str + ",05"
        }
        return Pair(str, size)
    }

    private fun updatePP(
        bean: StocksBean,
        sizeBean: AnalyseSizeBean
    ) {
        val PPList =
            DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.queryByQueryBuilder(
                AnalysePerPricesBeanDao.Properties.Code.eq(stocksCode.toInt())
            )

        var PPBean: AnalysePerPricesBean

        if (PPList.size > 0) {
            PPBean = PPList[0]
        } else {
            PPBean = AnalysePerPricesBean()
            PPBean.code = stocksCode.toInt()
        }

        if (bean.perPrice > bean.current && BigDecimalUtils.sub(
                bean.perPrice,
                bean.current
            ) >= 0.2 && bean.perAmount.toDouble() > Datas.limitPerAmount.toDouble()
        ) {

            var str =
                "(${bean.time},pp>cur:${bean.perPrice},pa:${bean.perAmount},cur:${bean.current},dif:${BigDecimalUtils.sub(
                    bean.perPrice,
                    bean.current
                )})"
            PPBean.perPricesGtCur =
                if (PPBean.perPricesGtCur.isNullOrEmpty()) str else PPBean.perPricesGtCur + splitStr + str
            updateOrInsertBean(PPList, PPBean)

            val analyseSize = setAnalyseSize(
                sizeBean,
                sizeBean.ppGtCurSize,
                sizeBean.ppGtCurSizeStr,
                bean,
                "ppGtCurSize"
            )
            sizeBean.countSize = analyseSize.first
            sizeBean.ppGtCurSize = analyseSize.second
            sizeBean.ppGtCurSizeStr = analyseSize.third
        }
        if (bean.current > bean.perPrice && BigDecimalUtils.sub(
                bean.current,
                bean.perPrice
            ) >= 0.2 && bean.perAmount.toDouble() > Datas.limitPerAmount
        ) {

            var str =
                "(${bean.time},pp<cur:${bean.perPrice},pa:${bean.perAmount},cur:${bean.current},dif:${BigDecimalUtils.sub(
                    bean.current,
                    bean.perPrice
                )})"
            PPBean.curGtPerPrices =
                if (PPBean.curGtPerPrices.isNullOrEmpty()) str else PPBean.curGtPerPrices + splitStr + str
            updateOrInsertBean(PPList, PPBean)

            val analyseSize = setAnalyseSize(
                sizeBean,
                sizeBean.curGtPPSize,
                sizeBean.curGtPPSizeStr,
                bean,
                "ppGtCurSize"
            )
            sizeBean.countSize = analyseSize.first
            sizeBean.curGtPPSize = analyseSize.second
            sizeBean.curGtPPSizeStr = analyseSize.third
        }


    }

    private fun updateOrInsertSizeBean(
        PPList: MutableList<AnalyseSizeBean>,
        PPBean: AnalyseSizeBean
    ) {
        if (PPList.size > 0) {
            val update = DaoUtilsStore.getInstance().analyseSizeBeanDaoUtils.update(PPBean)
        } else {
            val insert = DaoUtilsStore.getInstance().analyseSizeBeanDaoUtils.insert(PPBean)
        }
    }


    private fun updateOrInsertBean(
        PPList: MutableList<AnalysePerPricesBean>,
        PPBean: AnalysePerPricesBean
    ) {
        if (PPList.size > 0) {
            val update = DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.update(PPBean)
        } else {
            val insert = DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.insert(PPBean)
        }
    }

    private fun updatePA(
        lastStockBean: StocksBean,
        stocksBean: StocksBean,
        sizeBean: AnalyseSizeBean
    ) {
        if (lastStockBean.perAmount > 0 && stocksBean.perAmount > 0) {
            val PAList =
                DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.queryByQueryBuilder(
                    AnalysePerAmountBeanDao.Properties.Code.eq(stocksCode.toInt())
                )
            var PABean: AnalysePerAmountBean
            if (PAList.size > 0) {
                PABean = PAList[0]
            } else {
                PABean = AnalysePerAmountBean()
                PABean.code = stocksCode.toInt()
            }
            var lastPerAmount = lastStockBean.perAmount.toDouble()
            if (lastPerAmount > 100) {
                if (BigDecimalUtils.div(stocksBean.perAmount.toDouble(), lastPerAmount) > 10) {
                    var str =
                        "(${stocksBean.time},perAmount:${stocksBean.perAmount},lastPerAmount:$lastPerAmount)"
                    PABean.tenTimesLast =
                        if (PABean.tenTimesLast.isNullOrEmpty()) str else PABean.tenTimesLast + splitStr + str
                    val analyseSize = setAnalyseSize(
                        sizeBean,
                        sizeBean.tenTimesSize,
                        sizeBean.tenTimesSizeStr,
                        stocksBean,
                        "tenTimesLastSize"
                    )
                    sizeBean.countSize = analyseSize.first
                    sizeBean.tenTimesSize = analyseSize.second
                    sizeBean.tenTimesSizeStr = analyseSize.third
                }
            }

            if (stocksBean.perAmount.toDouble() >= 10000) {
                var str =
                    "(${stocksBean.time},pa:${stocksBean.perAmount}${getAddLog(stocksBean)})"
                PABean.ge100million =
                    if (PABean.ge100million.isNullOrEmpty()) str else PABean.ge100million + splitStr + str
                updateOrInsertPABean(PAList, PABean)
                val analyseSize = setAnalyseSize(
                    sizeBean,
                    sizeBean.ge100mSize,
                    sizeBean.ge100mSizeStr,
                    stocksBean,
                    "ge100mSize"
                )
                sizeBean.countSize = analyseSize.first
                sizeBean.ge100mSize = analyseSize.second
                sizeBean.ge100mSizeStr = analyseSize.third
            } else if (stocksBean.perAmount.toDouble() >= 5000) {
                var str =
                    "(${stocksBean.time},pa:${stocksBean.perAmount}${getAddLog(stocksBean)})"
                PABean.ge50million =
                    if (PABean.ge50million.isNullOrEmpty()) str else PABean.ge50million + splitStr + str
                updateOrInsertPABean(PAList, PABean)
                val analyseSize = setAnalyseSize(
                    sizeBean,
                    sizeBean.ge50mSize,
                    sizeBean.ge50mSizeStr,
                    stocksBean,
                    "ge50mSize"
                )
                sizeBean.countSize = analyseSize.first
                sizeBean.ge50mSize = analyseSize.second
                sizeBean.ge50mSizeStr = analyseSize.third
            } else if (stocksBean.perAmount.toDouble() >= 2000) {
                var str =
                    "(${stocksBean.time},pa:${stocksBean.perAmount}${getAddLog(stocksBean)})"
                PABean.ge20million =
                    if (PABean.ge20million.isNullOrEmpty()) str else PABean.ge20million + splitStr + str
                updateOrInsertPABean(PAList, PABean)
                val analyseSize = setAnalyseSize(
                    sizeBean,
                    sizeBean.ge20mSize,
                    sizeBean.ge20mSizeStr,
                    stocksBean,
                    "ge20mSize"
                )
                sizeBean.countSize = analyseSize.first
                sizeBean.ge20mSize = analyseSize.second
                sizeBean.ge20mSizeStr = analyseSize.third
            } else if (stocksBean.perAmount.toDouble() >= 1000) {
                var str =
                    "(${stocksBean.time},pa:${stocksBean.perAmount}${getAddLog(stocksBean)})"
                PABean.ge10million =
                    if (PABean.ge10million.isNullOrEmpty()) str else PABean.ge10million + splitStr + str
                updateOrInsertPABean(PAList, PABean)
                val analyseSize = setAnalyseSize(
                    sizeBean,
                    sizeBean.ge10mSize,
                    sizeBean.ge10mSizeStr,
                    stocksBean,
                    "ge10mSize"
                )
                sizeBean.countSize = analyseSize.first
                sizeBean.ge10mSize = analyseSize.second
                sizeBean.ge10mSizeStr = analyseSize.third
            } else if (stocksBean.perAmount.toDouble() >= 500) {
                var str =
                    "(${stocksBean.time},pa:${stocksBean.perAmount}${getAddLog(stocksBean)})"
                PABean.ge5million =
                    if (PABean.ge5million.isNullOrEmpty()) str else PABean.ge5million + splitStr + str
                updateOrInsertPABean(PAList, PABean)
                val analyseSize = setAnalyseSize(
                    sizeBean,
                    sizeBean.ge5mSize,
                    sizeBean.ge5mSizeStr,
                    stocksBean,
                    "ge5mSize"
                )
                sizeBean.countSize = analyseSize.first
                sizeBean.ge5mSize = analyseSize.second
                sizeBean.ge5mSizeStr = analyseSize.third
            }


        }
    }

    private fun setAnalyseSize(
        sizeBean: AnalyseSizeBean,
        attributeSize: Int,
        attributeSizeStr: String?,
        stocksBean: StocksBean,
        tag: String
    ): Triple<Int, Int, String?> {

        var size = attributeSize
        size = size + 1
        sizeBean.countSize = sizeBean.countSize + 1
        var originalStr = attributeSizeStr
        var str =
            "(${stocksBean.time},${tag}:$size,countSize:${sizeBean.countSize}${getSimpleAddLog(
                stocksBean
            )})"
        originalStr = if (originalStr.isNullOrEmpty()) str else originalStr + splitStr + str
        return Triple(sizeBean.countSize, size, originalStr)
    }


    private fun updateOrInsertPABean(
        PAList: MutableList<AnalysePerAmountBean>,
        PABean: AnalysePerAmountBean
    ) {
        if (PAList.size > 0) {
            val update = DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.update(PABean)
        } else {
            val insert = DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.insert(PABean)
        }
    }


    private fun updatePS(
        it: StocksBean,
        sizeBean: AnalyseSizeBean
    ) {

        val PSList =
            DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.queryByQueryBuilder(
                AnalysePerStocksBeanDao.Properties.Code.eq(stocksCode.toInt())
            )

        var PSBean: AnalysePerStocksBean
        if (PSList.size > 0) {
            PSBean = PSList[0]
        } else {
            PSBean = AnalysePerStocksBean()
            PSBean.code = stocksCode.toInt()
        }

        val buy1Stocks = it.buy1.split("_")[1]
        val buy1StocksValue = buy1Stocks.toDoubleOrNull()
        var bean = it

        buy1StocksValue?.let {
            if (it > 0 && bean.perAmount != null && bean.perAmount.toDouble() > Datas.limitPerAmount && bean.perPrice > bean.current) {
                if (BigDecimalUtils.div(
                        bean.perStocks.toDouble(),
                        buy1StocksValue.toDouble()
                    ) >= Datas.limitPerAmount
                ) {
                    var str =
                        "(${bean.time},ps:${bean.perStocks},buy1:$buy1StocksValue${getAddLog(
                            bean
                        )})"
                    PSBean.gt1000times =
                        if (PSBean.gt1000times.isNullOrEmpty()) str else PSBean.gt1000times + splitStr + str
                    updateOrInsertPSbean(PSList, PSBean)

                    val analyseSize = setAnalyseSize(
                        sizeBean,
                        sizeBean.gt1000TimesSize,
                        sizeBean.gt1000TimesSizeStr,
                        bean,
                        "gt1000TimesSize"
                    )
                    sizeBean.countSize = analyseSize.first
                    sizeBean.gt1000TimesSize = analyseSize.second
                    sizeBean.gt1000TimesSizeStr = analyseSize.third
                } else if (BigDecimalUtils.div(
                        bean.perStocks.toDouble(),
                        buy1StocksValue.toDouble()
                    ) >= 100 && bean.perPrice > bean.current
                ) {
                    var str =
                        "(${bean.time},ps:${bean.perStocks},buy1:$buy1StocksValue${getAddLog(
                            bean
                        )})"
                    PSBean.gt100times =
                        if (PSBean.gt100times.isNullOrEmpty()) str else PSBean.gt100times + splitStr + str
                    updateOrInsertPSbean(PSList, PSBean)

                    val analyseSize = setAnalyseSize(
                        sizeBean,
                        sizeBean.gt100TimesSize,
                        sizeBean.gt100TimesSizeStr,
                        bean,
                        "gt100TimesSize"
                    )
                    sizeBean.countSize = analyseSize.first
                    sizeBean.gt100TimesSize = analyseSize.second
                    sizeBean.gt100TimesSizeStr = analyseSize.third
                }
            }

        }

    }

    private fun updateOrInsertPSbean(
        PSList: MutableList<AnalysePerStocksBean>,
        PSBean: AnalysePerStocksBean
    ) {
        if (PSList.size > 0) {
            val update = DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.update(PSBean)
        } else {
            val insert = DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.insert(PSBean)
        }
    }

}