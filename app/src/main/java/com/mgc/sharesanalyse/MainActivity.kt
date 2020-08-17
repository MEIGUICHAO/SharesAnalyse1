package com.mgc.sharesanalyse

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mgc.sharesanalyse.base.App
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.entity.*
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import com.mgc.sharesanalyse.viewmodel.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.greenrobot.greendao.database.Database
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
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

    var viewModel: MainViewModel? = null
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




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel!!.loadState.observe(this, Observer {
            when (it) {
                is LoadState.Success -> {

                    LogUtil.d("viewModel!! size():${viewModel!!.sharesDats.value!!.size()}")
                    for (index in 0..viewModel!!.sharesDats.value!!.size()-1) {
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
                        ))
                    ) {
                        classifyStocks()
                    }
                    if (System.currentTimeMillis() <= DateUtils.parse(
                            endTime,
                            FormatterEnum.YYYYMMDD__HH_MM_SS
                        )
                    ) {
                        GlobalScope.launch {

                            withContext(Dispatchers.IO) {

                                runBlocking {
                                    delay(1000 * 25)
                                    requestDatas(viewModel!!)
                                }
                            }
                        }
                    }


                }
                is LoadState.Fail -> {

                }
                is LoadState.Loading -> {
                    btnRequest.isClickable = false
                }
            }
        })
        btnRequest.setOnClickListener {
            requestDatas(viewModel!!)
        }
        btnClassify.setOnClickListener {
            filterStocks()
        }
        btnCopyDB.setOnClickListener {
            //databases文件夹目录
            val path = "/data/data/" + getPackageName() + "/databases/" + DaoManager.DB_NAME

            FileUtil.UnZipAssetsFolder(this, Datas.DBName + ".zip", path)
        }
        btnAnalyse.setOnClickListener {
            App.getSinglePool().execute({
                analyseAll()
            })
        }
        btnLogResult.setOnClickListener {
            logResult()
        }
    }

    private fun logResult() {
        filterAnalyseStocks = ""
        val shCodeList = ResUtil.getSArray(R.array.stocks_code_name)
        FileLogUtil.d(
            "logResult",
            "!!!!!!!!!!start_______${DateUtils.format(
                System.currentTimeMillis(),
                FormatterEnum.YYYYMMDD__HH_MM_SS
            )}"
        )
        shCodeList.forEach {
            sizeCount = 0
            var code = it.split(splitStr)[0]
            var name = it.split(splitStr)[1]
            logStrList.clear()
            needJudeSize = true
            var tag = "logResult_" + code
            val perAmountList = DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.queryByQueryBuilder(AnalysePerAmountBeanDao.Properties.Code.eq(code))
            val perStockList = DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.queryByQueryBuilder(AnalysePerStocksBeanDao.Properties.Code.eq(code))
            val perPricesList = DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.queryByQueryBuilder(AnalysePerPricesBeanDao.Properties.Code.eq(code))
            getDB()
            val lastBean = CommonDaoUtils.queryLast(db, Datas.tableName + code)
            perAmountList.forEach {
                if (!filterAnalyseStocks.contains(it.code.toString())) {
                    filterAnalyseStocks =
                        filterAnalyseStocks + "_" + it.code.toString()
                }
                logStrList.add("------------------------------------------------------------------------------------------------")
                ge100mSize = logBySplite(it.ge100million,  "pa_ge100m")
                if (logStrList.size > 1) {
                    needJudeSize = false
                }
                tenTimesSize = logBySplite(it.tenTimesLast,  "pa_10Last")
                ge50mSize = logBySplite(it.ge50million,  "pa_ge50m")
                ge20mSize = logBySplite(it.ge20million,  "pa_ge20m")
                ge10mSize = logBySplite(it.ge10million,  "pa_ge10m")
                ge5mSize = logBySplite(it.ge5million,  "pa_ge5m")
            }
            perStockList.forEach {
                if (!filterAnalyseStocks.contains(it.code.toString())) {
                    filterAnalyseStocks =
                        filterAnalyseStocks + "_" + it.code.toString()
                }
                logStrList.add("————————————————————————————————————————————————————————————————————————————————————————————————————")
                gt1000TimesSize = logBySplite(it.gt1000times,  "ps_gt1000times")
                gt100TimesSize = logBySplite(it.gt100times,  "ps_gt100times")
            }
            perPricesList.forEach {
                if (!filterAnalyseStocks.contains(it.code.toString())) {
                    filterAnalyseStocks =
                        filterAnalyseStocks + "_" + it.code.toString()
                }
                logStrList.add("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                PPGtCurSize = logBySplite(it.perPricesGtCur,  "pp_perPricesGtCur")
                CurGtPPSize = logBySplite(it.curGtPerPrices,  "ps_curGtPerPrices")
            }
            if (sizeCount >= Datas.limitSize || !needJudeSize) {
                logSize(code, name, lastBean)
                logStrList.forEach {
                    FileLogUtil.d("logResult", it)
                    Log.d(tag, it)
                }
                FileLogUtil.d("logResult", "----close prices-----:${lastBean.current}!!!")
                FileLogUtil.d(
                    "logResult",
                    "=============================================================================================================="
                )
                LogUtil.d(
                    tag,
                    "==========================================================Complete===================================================="
                )
            }

        }
    }

    private fun logSize(
        code: String,
        name: String,
        lastBean: StocksBean
    ) {
        FileLogUtil.d(
            "logResult",
            "---c:${code}---n:$name,s:${logStrList.size}${tenTimesSize.toLog(",10ts")}${ge100mSize.toLog(",100ms")},${ge50mSize.toLog("50ms")}${ge20mSize.toLog(",20ms")}${ge10mSize.toLog(",10ms")}${ge5mSize.toLog(",5ms")}" +
                    "${gt1000TimesSize.toLog(",1000ts")}${gt100TimesSize.toLog(",100ts")}${PPGtCurSize.toLog(",ppGt")}${CurGtPPSize.toLog(",curGt")},o:${lastBean.open},c:${lastBean.current},p:${getCurPercent(
                        lastBean.current,
                        lastBean.open
                    )}!!!"
        )
        FileLogUtil.d(
            "logAlone",
            "---c:${code}---n:$name,s:${logStrList.size}${tenTimesSize.toLog(",10ts")}${ge100mSize.toLog(",100ms")}${ge50mSize.toLog(",50ms")}${ge20mSize.toLog(",20ms")}${ge10mSize.toLog(",10ms")}${ge5mSize.toLog(",5ms")}" +
                    "${gt1000TimesSize.toLog(",1000ts")}${gt100TimesSize.toLog(",100ts")}${PPGtCurSize.toLog(",ppGt")}${CurGtPPSize.toLog(",curGt")},o:${lastBean.open},c:${lastBean.current},p:${getCurPercent(
                        lastBean.current,
                        lastBean.open
                    )}!!!"
        )
    }

    private fun logBySplite(it: String?,  key: String): Int {
        it?.let {
            if (!it.isEmpty()) {
                val temTimesLastList = it.split(splitStr)
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
        val queryAll = DaoUtilsStore.getInstance().month8DataDaoUtils.queryAll()
        if (queryAll.size > 0) {
            DaoUtilsStore.getInstance().month8DataDaoUtils.deleteAll()
        }
        viewModel.requestData()
    }

    private fun classifyStocks() {
        LogUtil.d("Classify stocksArray.size:${viewModel!!.sharesDats.value!!.size()-1}")
        for (index in 0..(viewModel!!.sharesDats.value!!.size()-1)) {
            val queryAll = DaoUtilsStore.getInstance().month8DataDaoUtils.queryByQueryBuilder(
                Month8DataDao.Properties.Name.eq(index.toString())
            )
            LogUtil.d("Classify queryAll.size:${queryAll.size}")
            queryAll.forEach {
                var bean = it
                val replace = it.json.replace("var hq_str_sh\"", "").replace("\"", "")
                val split = replace.split(";")
                var mProgress = 0
                split.forEach {
                    LogUtil.d("Classify json:$it")
                    LogUtil.d("Classify json progress index:$index all_size:${queryAll.size} mProgress:$mProgress")
                    if (it.contains(",")) {
                        val split3 = it.split(",")
                        val stcokBean = setStcokBean(split3, bean)
                        getDB()
                        LogUtil.d("insertTableStringBuilder classifyTables")
                        CommonDaoUtils.classifyTables(db, Datas.tableName + stocksCode)
                    }
                    mProgress++
                }
            }
        }
        LogUtil.d("classifyStocks complete!!!")
    }


    private fun analyseAll() {
        if (analyseNeedReturn()) return

        resetSpareArray()
//        val shCodeList = listOf("600127")
        val shCodeList = ResUtil.getSArray(R.array.stocks_code)
        shCodeList.forEach {
            var stocksCode = it
            val queryPerAmount =
                CommonDaoUtils.queryStocks(Datas.tableName + stocksCode)
            LogUtil.d("analysePerAmount queryPerAmount size:${queryPerAmount.size}")

            var lastPerAmount = 0.toDouble()
            queryPerAmount.forEach {
                if (it.perAmount != null) {
                    LogUtil.d("analysePerAmount json:${it.toString()} lastPerAmount:$lastPerAmount")
                    if (lastPerAmount > 100) {
                        if (BigDecimalUtils.div(it.perAmount.toDouble(), lastPerAmount) > 10) {
                            var str =
                                "(${it.time},pa:${it.perAmount},lastPerAmount:$lastPerAmount)"
                            setSpareArrayData(PA10TimesSpareArray, stocksCode, str)
                        }
                    }
                    lastPerAmount = it.perAmount.toDouble()
                    LogUtil.d("analysePerAmount it.perAmount:${it.perAmount},it.perAmount.toDouble() >= ${Datas.limitPerAmount}:${it.perAmount.toDouble() >= Datas.limitPerAmount}")

                    if (it.perAmount.toDouble() >= 10000) {
                        var str = "(${it.time},pa:${it.perAmount}${getAddLog(it)})"
                        setSpareArrayData(PAGe100MillionSpareArray, stocksCode, str)
                    } else if (it.perAmount.toDouble() >= 5000) {
                        var str = "(${it.time},pa:${it.perAmount}${getAddLog(it)})"
                        setSpareArrayData(PAGe50MillionSpareArray, stocksCode, str)
                    } else if (it.perAmount.toDouble() >= 2000) {
                        var str = "(${it.time},pa:${it.perAmount}${getAddLog(it)})"
                        setSpareArrayData(PAGe20MillionSpareArray, stocksCode, str)
                    } else if (it.perAmount.toDouble() >= 1000) {
                        var str = "(${it.time},pa:${it.perAmount}${getAddLog(it)})"
                        setSpareArrayData(PAGe10MillionSpareArray, stocksCode, str)
                    }else if (it.perAmount.toDouble() >= 500) {
                        var str = "(${it.time},pa:${it.perAmount}${getAddLog(it)})"
                        setSpareArrayData(PAGe5MillionSpareArray, stocksCode, str)
                    }


                    val buy1Stocks = it.buy1.split("_")[1]
                    val buy1StocksValue = buy1Stocks.toDoubleOrNull()
                    var bean = it
                    buy1StocksValue?.let {
                        if (it > 0 && bean.perAmount != null && bean.perAmount.toDouble() > Datas.limitPerAmount) {
                            if (BigDecimalUtils.div(
                                    bean.perStocks.toDouble(),
                                    buy1StocksValue.toDouble()
                                ) >= 1000
                            ) {
                                var str =
                                    "(${bean.time},ps:${bean.perStocks},buy1:$buy1StocksValue${getAddLog(
                                        bean
                                    )})"
                                setSpareArrayData(PSGe1000MillionSpareArray, stocksCode, str)
                            } else if (BigDecimalUtils.div(
                                    bean.perStocks.toDouble(),
                                    buy1StocksValue.toDouble()
                                ) >= 100
                            ) {
                                var str =
                                    "(${bean.time},ps:${bean.perStocks},buy1:$buy1StocksValue${getAddLog(
                                        bean
                                    )})"
                                setSpareArrayData(PSGe100MillionSpareArray, stocksCode, str)
                            }
                        }

                    }
                    if (it.perPrice > it.current && BigDecimalUtils.sub(
                            it.perPrice,
                            it.current
                        ) >= 0.2&& bean.perAmount.toDouble() > Datas.limitPerAmount
                    ) {
                        var str =
                            "(${it.time},pp:${it.perPrice},pa:${it.perAmount},cur:${it.current},dif:${BigDecimalUtils.sub(it.perPrice,it.current)})"
                        setSpareArrayData(PerPricesGtCurSpareArray, stocksCode, str)
                    }
                    if (it.current > it.perPrice && BigDecimalUtils.sub(
                            it.current,
                            it.perPrice
                        ) >= 0.2&& bean.perAmount.toDouble() > Datas.limitPerAmount
                    ) {
                        var str =
                            "(${it.time},pp:${it.perPrice},pa:${it.perAmount},cur:${it.current},dif:${BigDecimalUtils.sub(it.current,it.perPrice)})"
                        setSpareArrayData(CurGtPerPricesSpareArray, stocksCode, str)
                    }
                }
            }
        }
        DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.deleteAll()
        DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.deleteAll()
        DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.deleteAll()
        shCodeList.forEach {
            LogUtil.d("----code:$it-----")
            LogUtil.d("------------------------------------------------")
            if (it.isEmpty()) return@forEach
            if (!PA10TimesSpareArray[it.toInt()].isNullOrEmpty() || !PAGe100MillionSpareArray[it.toInt()].isNullOrEmpty()
                || !PAGe50MillionSpareArray[it.toInt()].isNullOrEmpty() || !PAGe20MillionSpareArray[it.toInt()].isNullOrEmpty()
                || !PAGe10MillionSpareArray[it.toInt()].isNullOrEmpty()|| !PAGe5MillionSpareArray[it.toInt()].isNullOrEmpty()
            ) {
                val analysePerAmountBean = AnalysePerAmountBean()
                analysePerAmountBean.code = it.toInt()
                analysePerAmountBean.tenTimesLast = PA10TimesSpareArray.getDBValue(it.toInt())
                analysePerAmountBean.ge100million = PAGe100MillionSpareArray.getDBValue(it.toInt())
                analysePerAmountBean.ge50million = PAGe50MillionSpareArray.getDBValue(it.toInt())
                analysePerAmountBean.ge20million = PAGe20MillionSpareArray.getDBValue(it.toInt())
                analysePerAmountBean.ge10million = PAGe10MillionSpareArray.getDBValue(it.toInt())
                analysePerAmountBean.ge5million = PAGe5MillionSpareArray.getDBValue(it.toInt())
                DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.insert(analysePerAmountBean)
            }
            if (!PSGe1000MillionSpareArray[it.toInt()].isNullOrEmpty() || !PSGe100MillionSpareArray[it.toInt()].isNullOrEmpty()) {
                val analysePerStocksBean = AnalysePerStocksBean()
                analysePerStocksBean.code = it.toInt()
                analysePerStocksBean.gt1000times = PSGe1000MillionSpareArray.getDBValue(it.toInt())
                analysePerStocksBean.gt100times = PSGe100MillionSpareArray.getDBValue(it.toInt())
                DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.insert(analysePerStocksBean)
            }
            if (!PerPricesGtCurSpareArray[it.toInt()].isNullOrEmpty()||!CurGtPerPricesSpareArray[it.toInt()].isNullOrEmpty()) {
                val analysePerPricesBean = AnalysePerPricesBean()
                analysePerPricesBean.code = it.toInt()
                analysePerPricesBean.perPricesGtCur = PerPricesGtCurSpareArray.getDBValue(it.toInt())
                analysePerPricesBean.curGtPerPrices = CurGtPerPricesSpareArray.getDBValue(it.toInt())
                DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.insert(analysePerPricesBean)
            }
            LogUtil.d("=======================================================")
        }
        LogUtil.d("filterAnalyseStocks:" + filterAnalyseStocks)
    }

    private fun resetSpareArray() {
        PA10TimesSpareArray.clear()
        PAGe100MillionSpareArray.clear()
        PAGe50MillionSpareArray.clear()
        PAGe20MillionSpareArray.clear()
        PAGe10MillionSpareArray.clear()
        PAGe5MillionSpareArray.clear()
        PA10TimesSpareArray.clear()
        PSGe1000MillionSpareArray.clear()
        PSGe100MillionSpareArray.clear()
        PerPricesGtCurSpareArray.clear()
        CurGtPerPricesSpareArray.clear()
        filterAnalyseStocks = ""
    }

    private fun getAddLog(it: StocksBean) =
        ",cur:${it.current},open:${it.open},h:${it.hightest},l:${it.lowest},p:${getCurPercent(it.current,it.open)},h/l:${getHLPercent(it.hightest,it.lowest)},sp:${getStopPrices(it.open)}"


    private fun getSimpleAddLog(it: StocksBean) =
        ",cur:${it.current},open:${it.open},p:${getCurPercent(it.current,it.open)},sp:${getStopPrices(it.open)}"

    private fun getStopPrices(open: Double?): String {
        if (null != open) {
            return  BigDecimalUtils.add(open,BigDecimalUtils.mul(open,0.1,2)).toString()
        } else return "0"
    }

    private fun getHLPercent(hightest: String?, lowest: String?): String {
        val hightest = hightest?.toDoubleOrNull()
        val lowest = lowest?.toDoubleOrNull()
        if (null != hightest && null != lowest&&lowest>0) {
            val df = DecimalFormat("#.00")
            return df.format(BigDecimalUtils.div(BigDecimalUtils.sub(hightest,lowest),lowest)*100)+"%"
        } else return "0"
    }

    private fun getCurPercent(current: Double, open: Double?): String {
        if (null != open && open > 0) {
            val df = DecimalFormat("#.00")
            return df.format(
                (BigDecimalUtils.div(
                    BigDecimalUtils.sub(current, open),
                    open
                ) * 100)
            ) + "%"
        } else return "0"
    }


    private fun analyseNeedReturn(): Boolean {
        getDB()
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
            Toast.makeText(this, "wait", Toast.LENGTH_SHORT).show()
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

    private fun filterStocks() {
        if (System.currentTimeMillis() < DateUtils.parse(
                endTime,
                FormatterEnum.YYYYMMDD__HH_MM_SS
            ) && Datas.DBName.contains(
                DateUtils.format(
                    System.currentTimeMillis(),
                    FormatterEnum.YYYY_MM_DD
                )
            )
        ) {
            Toast.makeText(this, "wait", Toast.LENGTH_SHORT).show()
            return
        }
        val shCodeList = ResUtil.getSArray(R.array.stocks_code_name)
        filterStocks = ""
        shCodeList.forEach {
            val code = it.split(splitStr)[0]
            val name = it.split(splitStr)[1]
            getDB()
            val lastStockBean = CommonDaoUtils.queryLast(db, Datas.tableName + code)
            if (null == lastStockBean){
                return@forEach
            }
            if (lastStockBean.open > 0) {
                val div = BigDecimalUtils.div(
                    BigDecimalUtils.sub(
                        lastStockBean.current,
                        lastStockBean.open.toDouble()
                    ), lastStockBean.open.toDouble()
                )
                LogUtil.d("filterStocks div:$div")
                if (div >= 0.1) {
                    CommonDaoUtils.renameTable(db, Datas.tableName + code, "AAA_10")
                    LogUtil.d("filterStocks!!! AAA_10 name:$name,code:$code")
                    FileLogUtil.d("filterStocks","AAA_10 name:$name,code:$code")
                } else if (div >= 0.9) {
                    CommonDaoUtils.renameTable(db, Datas.tableName + code, "BBB_9")
                    LogUtil.d("filterStocks!!! BBB_9 name:$name,code:$code")
                    FileLogUtil.d("filterStocks","BBB_9 name:$name,code:$code")
                } else if (div >= 0.08) {
                    CommonDaoUtils.renameTable(db, Datas.tableName + code, "CCC_8")
                    LogUtil.d("filterStocks!!! CCC_8 name:$name,code:$code")
                    FileLogUtil.d("filterStocks","CCC_8 name:$name,code:$code")
                } else if (div >= 0.07) {
                    CommonDaoUtils.renameTable(db, Datas.tableName + code, "DDD_7")
                    LogUtil.d("filterStocks!!! DDD_7 name:$name,code:$code")
                    FileLogUtil.d("filterStocks","DDD_7 name:$name,code:$code")
                } else if (div >= 0.06) {
                    CommonDaoUtils.renameTable(db, Datas.tableName + code, "EEE_6")
                    LogUtil.d("filterStocks!!! EEE_6 name:$name,code:$code")
                    FileLogUtil.d("filterStocks","EEE_6 name:$name,code:$code")
                } else if (div >= 0.05) {
                    CommonDaoUtils.renameTable(db, Datas.tableName + code, "FFF_5")
                    LogUtil.d("filterStocks!!! FFF_5 name:$name,code:$code")
                    FileLogUtil.d("filterStocks","FFF_5 name:$name,code:$code")
                } else if (div <= -0.1) {
                    CommonDaoUtils.renameTable(db, Datas.tableName + code, "HHHH_neg10")
                    LogUtil.d("filterStocks!!! HHHH_neg10 name:$name,code:$code")
                    FileLogUtil.d("filterStocks","HHHH_neg10 name:$name,code:$code")
                }
            }
        }
        LogUtil.d("filterStocks:$filterStocks")


    }

    private fun setStcokBean(
        split: List<String>,
        bean: Month8Data
    ): StocksBean {
        var stocksBean: StocksBean
        val queryAll = DaoUtilsStore.getInstance().stocksBeanDaoUtils.queryAll()
        if (queryAll.size > 0) {
            stocksBean = queryAll[0]
        } else {
            stocksBean = StocksBean()
        }


        stocksBean.time = DateUtils.format(bean.timeStamp, FormatterEnum.HH_MM_SS)
        val nameSplit = split[0].replace("var hq_str_sh", "").replace("\n", "").split("=")
        stocksCode = nameSplit[0]
        LogUtil.d("Classify stocksCode:${nameSplit[0]} stocksName:${nameSplit[1]}")
        stocksBean.open = split[1].toDouble()
        stocksBean.current = split[3].toDouble()
        stocksBean.hightest = split[4]
        stocksBean.lowest = split[5]
        stocksBean.dealAmount = split[9].toDiv10000()
        stocksBean.dealStocks = split[8].toDiv100()

        stocksBean.buy1 = split[11] + "_" + split[12].toDiv100()
        stocksBean.buy2 = split[13] + "_" + split[14].toDiv100()
        stocksBean.buy3 = split[15] + "_" + split[16].toDiv100()
        stocksBean.buy4 = split[17] + "_" + split[18].toDiv100()
        stocksBean.buy5 = split[19] + "_" + split[20].toDiv100()

        stocksBean.sale1 = split[21] + "_" + split[20].toDiv100()
        stocksBean.sale2 = split[23] + "_" + split[22].toDiv100()
        stocksBean.sale3 = split[25] + "_" + split[24].toDiv100()
        stocksBean.sale4 = split[27] + "_" + split[26].toDiv100()
        stocksBean.sale5 = split[29] + "_" + split[28].toDiv100()
        getDB()
        val lastStockBean = CommonDaoUtils.queryLast(db, Datas.tableName + stocksCode)
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
                        BigDecimalUtils.sub(
                            stocksBean.dealAmount.toDouble(),
                            lastStockBean.dealAmount.toDouble()
                        ),
                        stocksBean.perStocks.toDouble()
                    ), 100.toDouble(), 3
                )
            } else {
                stocksBean.perPrice = 0.toDouble()
            }
        } else {
            stocksBean.perStocks = stocksBean.dealStocks.toDouble()
            if (stocksBean.dealStocks.toDouble() > 0) {
                stocksBean.perPrice = BigDecimalUtils.mul(BigDecimalUtils.div(
                    stocksBean.dealAmount.toDouble(),
                    stocksBean.dealStocks.toDouble()
                ),100.toDouble(),3)
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
        var sizeRecord = sizeBean.countSize
        LogUtil.d("updatePA")
        if (null != lastStockBean) {
            updatePA(lastStockBean, stocksBean,sizeBean)
        }
        LogUtil.d("updatePS")
        updatePS(stocksBean,sizeBean)
        updatePP(stocksBean,sizeBean)
        if (sizeBean.countSize > sizeRecord) {
            updateOrInsertSizeBean(sizeBeanList,sizeBean)
        }
        if (queryAll.size > 0) {
            val update = DaoUtilsStore.getInstance().stocksBeanDaoUtils.update(stocksBean)
            LogUtil.d("insertTableStringBuilder update:" + update)
        } else {
            val insert = DaoUtilsStore.getInstance().stocksBeanDaoUtils.insert(stocksBean)
            LogUtil.d("insertTableStringBuilder insert:" + insert)
        }
        return stocksBean
    }

    private fun updatePP(
        bean: StocksBean,
        sizeBean: AnalyseSizeBean
    ) {
        if (BigDecimalUtils.div(bean.current, bean.perPrice) > 60) {
            return
        }
        LogUtil.d("updatePA")
        val PPList =
            DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.queryByQueryBuilder(
                AnalysePerAmountBeanDao.Properties.Code.eq(stocksCode.toInt())
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
            ) >= 0.2&& bean.perAmount.toDouble() > Datas.limitPerAmount
        ) {
            var str =
                "(${bean.time},pp>cur:${bean.perPrice},pa:${bean.perAmount},cur:${bean.current},dif:${BigDecimalUtils.sub(bean.perPrice,bean.current)})"
            PPBean.perPricesGtCur = if (PPBean.perPricesGtCur.isNullOrEmpty()) str else PPBean.perPricesGtCur + splitStr + str
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
            ) >= 0.2&& bean.perAmount.toDouble() > Datas.limitPerAmount
        ) {
            var str =
                "(${bean.time},pp<cur:${bean.perPrice},pa:${bean.perAmount},cur:${bean.current},dif:${BigDecimalUtils.sub(bean.current,bean.perPrice)})"
            PPBean.curGtPerPrices = if (PPBean.curGtPerPrices.isNullOrEmpty()) str else PPBean.curGtPerPrices + splitStr + str
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
            LogUtil.d("insert analyseSizeBeanDaoUtils update:" + update)
        } else {
            val insert = DaoUtilsStore.getInstance().analyseSizeBeanDaoUtils.insert(PPBean)
            LogUtil.d("insert analyseSizeBeanDaoUtils insert:" + insert)
        }
    }


    private fun updateOrInsertBean(
        PPList: MutableList<AnalysePerPricesBean>,
        PPBean: AnalysePerPricesBean
    ) {
        if (PPList.size > 0) {
            val update = DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.update(PPBean)
            LogUtil.d("insert analysePerPricesBeanDaoUtils update:" + update)
        } else {
            val insert = DaoUtilsStore.getInstance().analysePerPricesBeanDaoUtils.insert(PPBean)
            LogUtil.d("insert analysePerPricesBeanDaoUtils insert:" + insert)
        }
    }

    private fun updatePA(
        lastStockBean: StocksBean,
        stocksBean: StocksBean,
        sizeBean: AnalyseSizeBean
    ) {
        if (lastStockBean.perAmount>0 && stocksBean.perAmount>0) {
            LogUtil.d("updatePA")
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
            LogUtil.d("analysePerAmount it.perAmount:${stocksBean.perAmount},it.perAmount.toDouble() >= ${Datas.limitPerAmount}:${stocksBean.perAmount.toDouble() >= Datas.limitPerAmount}")

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
        tenTimesSize: Int,
        tenTimesSizeStr: String?,
        stocksBean: StocksBean,
        tag: String
    ): Triple<Int,Int, String?> {

        var size = tenTimesSize
        size = size + 1
        sizeBean.countSize = sizeBean.countSize + size
        var originalStr = tenTimesSizeStr
        var str = "(${stocksBean.time},${tag}:$size,countSize:${sizeBean.countSize}${getSimpleAddLog(stocksBean)})"
        originalStr = if (originalStr.isNullOrEmpty()) originalStr else originalStr + splitStr + str
        return Triple(sizeBean.countSize,size, originalStr)
    }


    private fun updateOrInsertPABean(
        PAList: MutableList<AnalysePerAmountBean>,
        PABean: AnalysePerAmountBean
    ) {
        if (PAList.size > 0) {
            val update = DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.update(PABean)
            LogUtil.d("insert analysePerAmountBeanDaoUtils update:" + update)
        } else {
            val insert = DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.insert(PABean)
            LogUtil.d("insert analysePerAmountBeanDaoUtils insert:" + insert)
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
            LogUtil.d("insert analysePerStocksBeanDaoUtils update:" + update)
        } else {
            val insert = DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.insert(PSBean)
            LogUtil.d("insert analysePerStocksBeanDaoUtils insert:" + insert)
        }
    }

    private fun getDB() {
        if (db == null) {
            db = DaoManager.getsHelper().getWritableDb()
        }
    }


}
