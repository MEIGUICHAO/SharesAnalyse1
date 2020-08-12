package com.mgc.sharesanalyse

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.entity.Month8Data
import com.mgc.sharesanalyse.entity.Month8DataDao
import com.mgc.sharesanalyse.entity.StocksBean
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import com.mgc.sharesanalyse.viewmodel.MainViewModel
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

    var stocksCode = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.loadState.observe(this, Observer {
            when (it) {
                is LoadState.Success -> {

                    for (index in 0..9) {
                        val data = viewModel.sharesDats.value?.get(index)
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
                                    requestDatas(viewModel)
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
            requestDatas(viewModel)
        }
        btnClassify.setOnClickListener {
            filterStocks()
        }
        btnCopyDB.setOnClickListener {
            //databases文件夹目录
            val path = "/data/data/" + getPackageName() + "/databases/" + DaoManager.DB_NAME

            FileUtil.UnZipAssetsFolder(this, Datas.DBName + ".zip", path)
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
                        if (db == null) {
                            db = DaoManager.getsHelper().getWritableDb()
                        }
                        LogUtil.d("insertTableStringBuilder classifyTables")
                        CommonDaoUtils.classifyTables(db, Datas.tableName + stocksCode)
                    }
                    mProgress++
                }
            }
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
                                if (db == null) {
                                    db = DaoManager.getsHelper().getWritableDb()
                                }
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
        if (db == null) {
            db = DaoManager.getsHelper().getWritableDb()
        }
        val lastStockBean = CommonDaoUtils.query(db, Datas.tableName + stocksCode)
        if (null != lastStockBean) {
            stocksBean.perStocks = BigDecimalUtils.sub(
                stocksBean.dealStocks.toDouble(),
                lastStockBean.dealStocks.toDouble()
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


}
