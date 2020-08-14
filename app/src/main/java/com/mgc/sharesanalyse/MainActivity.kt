package com.mgc.sharesanalyse

import android.os.Bundle
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
import com.mgc.sharesanalyse.viewmodel.MainViewModel
import com.mgc.sharesanalyse.viewmodel.getDBValue
import com.mgc.sharesanalyse.viewmodel.toDiv100
import com.mgc.sharesanalyse.viewmodel.toDiv10000
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.greenrobot.greendao.database.Database

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

    var stocksCode = ""

    var viewModel: MainViewModel? = null
    var PA10TimesSpareArray = SparseArray<String>()
    var PAGe100MillionSpareArray = SparseArray<String>()
    var PAGe50MillionSpareArray = SparseArray<String>()
    var PAGe20MillionSpareArray = SparseArray<String>()
    var PAGe10MillionSpareArray = SparseArray<String>()

    var PSGe1000MillionSpareArray = SparseArray<String>()
    var PSGe100MillionSpareArray = SparseArray<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel!!.loadState.observe(this, Observer {
            when (it) {
                is LoadState.Success -> {

                    for (index in 0..9) {
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
    }

    private fun requestDatas(viewModel: MainViewModel) {
        val queryAll = DaoUtilsStore.getInstance().month8DataDaoUtils.queryAll()
        if (queryAll.size > 0) {
            DaoUtilsStore.getInstance().month8DataDaoUtils.deleteAll()
        }
        viewModel.requestData()
    }

    private fun classifyStocks() {
        for (index in 0..9) {
            val queryAll = DaoUtilsStore.getInstance().month8DataDaoUtils.queryByQueryBuilder(
                Month8DataDao.Properties.Name.eq(index.toString())
            )
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
    }


    private fun analyseAll() {
        if (analyseNeedReturn()) return

        PA10TimesSpareArray.clear()
        PAGe100MillionSpareArray.clear()
        PAGe50MillionSpareArray.clear()
        PAGe20MillionSpareArray.clear()
        PA10TimesSpareArray.clear()
        PSGe1000MillionSpareArray.clear()
        PSGe100MillionSpareArray.clear()
        filterAnalyseStocks = ""
//        val shCodeList = listOf("600127")
        val shCodeList = viewModel!!.getShCodeList()
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
                                "(${it.time},perAmount:${it.perAmount},lastPerAmount:$lastPerAmount)"
                            setSpareArrayData(PA10TimesSpareArray, stocksCode, str)
                        }
                    }
                    lastPerAmount = it.perAmount.toDouble()
                    LogUtil.d("analysePerAmount it.perAmount:${it.perAmount},it.perAmount.toDouble() >= 1000:${it.perAmount.toDouble() >= 1000}")

                    if (it.perAmount.toDouble() >= 10000) {
                        var str = "(${it.time},perAmount:${it.perAmount},current:${it.current},open:${it.open})"
                        setSpareArrayData(PAGe100MillionSpareArray, stocksCode, str)
                    } else if (it.perAmount.toDouble() >= 5000) {
                        var str = "(${it.time},perAmount:${it.perAmount},current:${it.current},open:${it.open})"
                        setSpareArrayData(PAGe50MillionSpareArray, stocksCode, str)
                    } else if (it.perAmount.toDouble() >= 2000) {
                        var str = "(${it.time},perAmount:${it.perAmount},current:${it.current},open:${it.open})"
                        setSpareArrayData(PAGe20MillionSpareArray, stocksCode, str)
                    } else if (it.perAmount.toDouble() >= 1000) {
                        var str = "(${it.time},perAmount:${it.perAmount},current:${it.current},open:${it.open})"
                        setSpareArrayData(PAGe10MillionSpareArray, stocksCode, str)
                    }


                    val buy1Stocks = it.buy1.split("_")[1]
                    val buy1StocksValue = buy1Stocks.toDoubleOrNull()
                    var bean = it
                    buy1StocksValue?.let {
                        if (it > 0 && bean.perAmount != null && bean.perAmount.toDouble() > 1000) {
                            if (BigDecimalUtils.div(
                                    bean.perStocks.toDouble(),
                                    buy1StocksValue.toDouble()
                                ) >= 1000
                            ) {
                                var str =
                                    "(${bean.time},perStocks:${bean.perStocks},buy1StocksValue:$buy1StocksValue,current:${bean.current},open:${bean.open})"
                                setSpareArrayData(PSGe1000MillionSpareArray, stocksCode, str)
                            } else if (BigDecimalUtils.div(
                                    bean.perStocks.toDouble(),
                                    buy1StocksValue.toDouble()
                                ) >= 100
                            ) {
                                var str =
                                    "(${bean.time},perStocks:${bean.perStocks},buy1StocksValue:$buy1StocksValue,current:${bean.current},open:${bean.open})"
                                setSpareArrayData(PSGe100MillionSpareArray, stocksCode, str)
                            }
                        }

                    }
                }
            }
        }
        DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.deleteAll()
        DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.deleteAll()
        shCodeList.forEach {
            LogUtil.d("----code:$it-----")
            LogUtil.d("------------------------------------------------")
            if (it.isEmpty()) return@forEach
            if (!PA10TimesSpareArray[it.toInt()].isNullOrEmpty() || !PAGe100MillionSpareArray[it.toInt()].isNullOrEmpty()
                || !PAGe50MillionSpareArray[it.toInt()].isNullOrEmpty() || !PAGe20MillionSpareArray[it.toInt()].isNullOrEmpty()
                || !PAGe10MillionSpareArray[it.toInt()].isNullOrEmpty()
            ) {
                val analysePerAmountBean = AnalysePerAmountBean()
                analysePerAmountBean.code = it.toInt()
                analysePerAmountBean.tenTimesLast = PA10TimesSpareArray.getDBValue(it.toInt())
                analysePerAmountBean.ge100million = PAGe100MillionSpareArray.getDBValue(it.toInt())
                analysePerAmountBean.ge50million = PAGe50MillionSpareArray.getDBValue(it.toInt())
                analysePerAmountBean.ge20million = PAGe20MillionSpareArray.getDBValue(it.toInt())
                analysePerAmountBean.ge10million = PAGe10MillionSpareArray.getDBValue(it.toInt())
                DaoUtilsStore.getInstance().analysePerAmountBeanDaoUtils.insert(analysePerAmountBean)
            }
            if (!PSGe1000MillionSpareArray[it.toInt()].isNullOrEmpty() || !PSGe100MillionSpareArray[it.toInt()].isNullOrEmpty()) {
                val analysePerStocksBean = AnalysePerStocksBean()
                analysePerStocksBean.code = it.toInt()
                analysePerStocksBean.gt1000times = PSGe1000MillionSpareArray.getDBValue(it.toInt())
                analysePerStocksBean.gt100times = PSGe100MillionSpareArray.getDBValue(it.toInt())
                DaoUtilsStore.getInstance().analysePerStocksBeanDaoUtils.insert(analysePerStocksBean)
            }
            LogUtil.d("=======================================================")
        }
        LogUtil.d("filterAnalyseStocks:" + filterAnalyseStocks)
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
                map[stocksCode.toInt()] + "####" + str
            )
        }
    }

    private fun filterStocks() {
        if (System.currentTimeMillis() < DateUtils.parse(
                endTime,

                FormatterEnum.YYYYMMDD__HH_MM_SS
            )
        ) {
            Toast.makeText(this, "wait", Toast.LENGTH_SHORT).show()
            return
        }
        filterStocks = ""
        for (index in 0..9) {
            val queryAll = DaoUtilsStore.getInstance().month8DataDaoUtils.queryByQueryBuilder(
                Month8DataDao.Properties.Name.eq(index.toString())
            )
            queryAll.forEach {
                val replace = it.json.replace("var hq_str_sh\"", "").replace("\"", "")
                val split = replace.split(";")
                var mProgress = 0
                split.forEach {
                    LogUtil.d("Classify json:$it")
                    LogUtil.d("Classify json progress index:$index all_size:${queryAll.size} mProgress:$mProgress")
                    if (it.contains(",")) {
                        val split3 = it.split(",")
                        val nameSplit =
                            split3[0].replace("var hq_str_sh", "").replace("\n", "").split("=")
                        var stocksCode = nameSplit[0]
                        LogUtil.d("filterStocks${split3[4]}")
                        var hightestPrice = split3[4].toDouble()
                        var lowestPrice = split3[5].toDouble()
                        var openPrice = split3[1].toDouble()
                        if (lowestPrice > 0) {
                            if (BigDecimalUtils.div(
                                    BigDecimalUtils.sub(hightestPrice, lowestPrice),
                                    openPrice
                                ) >= 0.05
                            ) {
                                getDB()
                                CommonDaoUtils.renameTable(db, Datas.tableName + stocksCode)
                                filterStocks = filterStocks + "_" + stocksCode
                            }
                        }
                    }
                    mProgress++
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
        stocksBean.open = split[1]
        stocksBean.current = split[3]
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
        val lastStockBean = CommonDaoUtils.query(db, Datas.tableName + stocksCode)
        if (null != lastStockBean) {
            stocksBean.perStocks = BigDecimalUtils.sub(
                stocksBean.dealStocks.toDouble(),
                lastStockBean.dealStocks.toDouble()
            ).toString()
            stocksBean.perAmount = BigDecimalUtils.sub(
                stocksBean.dealAmount.toDouble(),
                lastStockBean.dealAmount.toDouble()
            ).toString()
            if (stocksBean.perStocks.toDouble() > 0) {
                stocksBean.perPrice = BigDecimalUtils.mul(
                    BigDecimalUtils.div(
                        BigDecimalUtils.sub(
                            stocksBean.dealAmount.toDouble(),
                            lastStockBean.dealAmount.toDouble()
                        ),
                        stocksBean.perStocks.toDouble()
                    ), 100.toDouble(), 3
                ).toString()
            } else {
                stocksBean.perPrice = 0.toString()
            }
        } else {
            stocksBean.perStocks = stocksBean.dealStocks
            if (stocksBean.dealStocks.toDouble() > 0) {
                stocksBean.perPrice = BigDecimalUtils.div(
                    stocksBean.dealAmount.toDouble(),
                    stocksBean.dealStocks.toDouble()
                ).toString()
            } else {
                stocksBean.perPrice = 0.toString()
            }
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

    private fun getDB() {
        if (db == null) {
            db = DaoManager.getsHelper().getWritableDb()
        }
    }


}
