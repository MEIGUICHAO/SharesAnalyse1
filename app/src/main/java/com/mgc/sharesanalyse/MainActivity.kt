package com.mgc.sharesanalyse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mgc.sharesanalyse.entity.Month8Data
import com.mgc.sharesanalyse.entity.Month8DataDao
import com.mgc.sharesanalyse.entity.StocksBean
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import com.mgc.sharesanalyse.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {


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


                    GlobalScope.launch {

                        withContext(Dispatchers.IO) {

                            runBlocking {
                                delay(1000 * 30)
                                viewModel.requestData()
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
            viewModel.requestData()
        }
        btnClassify.setOnClickListener {
            for (index in 0..9) {
                val queryAll = DaoUtilsStore.getInstance().month8DataDaoUtils.queryByQueryBuilder(
                    Month8DataDao.Properties.Name.eq(index.toString())
                )
                queryAll.forEach {
                    var bean = it
                    val replace = it.json.replace("var hq_str_sh\"", "").replace("\"", "")
                    val split = replace.split(";")
                    split.forEach {
                        LogUtil.d("Classify json:$it")
                        if (it.contains(",")) {
                            val split1 = it.split(",")
                            setStcokBean(split1, bean)
                        }
                    }
                }
            }

        }
        btnCopyDB.setOnClickListener{
            //databases文件夹目录
            val path = "/data/data/" + getPackageName() + "/databases/"+ DaoManager.DB_NAME
            FileUtil.copyAssets2Sdcard(this, "greendaotest", path)
        }
    }

    private fun setStcokBean(
        split: List<String>,
        bean: Month8Data
    ) {
        val stocksBean = StocksBean()
        stocksBean.timeStamp = bean.timeStamp
        val nameSplit = split[0].replace("var hq_str_sh", "").replace("\n", "").split("=")
        stocksBean.stocksName = nameSplit[1]
        stocksBean.stocksCode = nameSplit[0]
        LogUtil.d("Classify stocksCode:${nameSplit[0]} stocksName:${nameSplit[1]}")
        stocksBean.openPrice = split[1]
        stocksBean.currentPrice = split[3]
        stocksBean.hightestPrice = split[4]
        stocksBean.lowestPrice = split[5]
        stocksBean.dealAmount = split[9]
        stocksBean.dealStocks = split[8]

        stocksBean.buy1 = split[11]
        stocksBean.buy1Nums = split[12]
        stocksBean.buy2 = split[13]
        stocksBean.buy2Nums = split[14]
        stocksBean.buy3 = split[15]
        stocksBean.buy3Nums = split[16]
        stocksBean.buy4 = split[17]
        stocksBean.buy4Nums = split[18]
        stocksBean.buy5 = split[19]
        stocksBean.buy5Nums = split[20]

        stocksBean.sale1 = split[21]
        stocksBean.sale1Nums = split[20]
        stocksBean.sale2 = split[23]
        stocksBean.sale2Nums = split[22]
        stocksBean.sale3 = split[25]
        stocksBean.sale3Nums = split[24]
        stocksBean.sale4 = split[27]
        stocksBean.sale4Nums = split[26]
        stocksBean.sale5 = split[29]
        stocksBean.sale5Nums = split[28]
        DaoUtilsStore.getInstance().stocksBeanDaoUtils.insert(stocksBean)
    }

}
