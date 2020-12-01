package com.mgc.sharesanalyse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mgc.sharesanalyse.base.App
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.entity.HisHqBean
import com.mgc.sharesanalyse.entity.PriceHisBean
import com.mgc.sharesanalyse.entity.SinaDealDatailBean
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import com.mgc.sharesanalyse.viewmodel.NewApiViewModel
import kotlinx.android.synthetic.main.activity_new_api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.File
import java.nio.charset.Charset

class NewApiActivity : AppCompatActivity() {

    lateinit var viewModel: NewApiViewModel
    var progressIndex = 0
    var judeWeekDayIndex = 0
    var dealDetailBeginDateTimeStamp =
        if (DateUtils.ifAfterToday1530()) System.currentTimeMillis() else {
            var ysdts = DateUtils.formatYesterDayTimeStamp()
            while (!DateUtils.isWeekDay(ysdts).first) {
                ysdts = ysdts - 24 * 60 * 60 * 1000
            }
            ysdts
        }
    var dealDetailBeginDate =
        if (DateUtils.ifAfterToday1530()) DateUtils.formatToDay(FormatterEnum.YYYY_MM_DD) else {
            var ysdts = DateUtils.formatYesterDayTimeStamp()
            while (!DateUtils.isWeekDay(ysdts).first) {
                ysdts = ysdts - 24 * 60 * 60 * 1000
            }
            DateUtils.format(
                ysdts,
                FormatterEnum.YYYY_MM_DD
            )
        }

    var needLogAfterDD = false


    private fun unzipForeach(zipList: ArrayList<String>, index: Int) {
        val path = "/data/data/" + getPackageName() + "/databases"
        GlobalScope.launch(Dispatchers.IO) {
            ZipUtils.UnZipFolder(this@NewApiActivity,zipList[index],path)
        }
    }

    private fun copyDB() {
        var zipList = ArrayList<String>()
        var listpath = assets.list("")
        listpath!!.forEach {
            if (it.contains(".zip")) {
                LogUtil.d("listpath:$it")
                zipList.add(it)
            }
        }
        if (zipList.size > 0) {
            unzipForeach(zipList, 0)
        }
        LogUtil.d("unzipForeach zip copy complete!!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_api)
        DaoManager.setDbName(Datas.dataNamesDefault)
        viewModel = ViewModelProvider(this).get(NewApiViewModel::class.java)
        viewModelObserve()

        btnGetALlCode.setOnClickListener {

//            val sddTableName = Datas.sdd + DateUtils.formatToDay(FormatterEnum.YYMM)
//            val sddTableNameAll = Datas.sddALL
//            DBUtils.dropTable(sddTableName)
//            DBUtils.dropTable(sddTableNameAll)
//            DBUtils.dropTable("HHQ_20201021")
//            DBUtils.dropTable("DD_20201013")
//            DBUtils.foreachDBTable()
//            viewModel.getAllCode()
        }

        btnCopy.setOnClickListener {
            copyDB()
        }
        btnRequestDealDetail.setOnClickListener {
            btnRequestDealDetail.setText("DealDetail_Working")
            LogUtil.d("dealDetailBeginDate:$dealDetailBeginDate")
            needLogAfterDD = false
            viewModel.detailCodeList.clear()
            var list = DaoUtilsStore.getInstance().allCodeGDBeanDaoUtils.queryAll()
            list.forEach {
                if (Datas.DEBUG) {
                    Datas.DEBUG_Code.forEach { code->
                        if (it.code.contains(code)) {
                            viewModel.detailCodeList.add(it.code)
                        }
                    }

                } else {
                    viewModel.detailCodeList.add(it.code)
                }

            }

            requestDealDetailBtn()
        }
        btnRequestPricehis.setOnClickListener {
            viewModel.getPricehis("601216", "2020-09-11", "2020-09-11")
        }
        btnRequestHisHq.setOnClickListener {
            clickHHQ()
        }
        btnBwcList.setOnClickListener {
            viewModel.bwc()
        }
        btnRequestSumDd.setOnClickListener {
            App.getSinglePool().execute {
                viewModel.getSumDD()
            }
        }
        btnReverse.setOnClickListener {
            App.getSinglePool().execute{
                viewModel.reverseResult()
            }
        }

    }



    fun setBtnReverseInfo(info: String) {
        btnGetALlCode.post {
            btnReverse.setText(info)
        }
    }


    fun setAllCodeInfo(info: String) {
        btnGetALlCode.post {
            btnGetALlCode.setText(info)
        }
    }

    fun setBtnDDInfo(info: String) {
        btnRequestDealDetail.post {
            btnRequestDealDetail.setText(info)
        }
    }
    fun setBtnHHQInfo(info: String) {
        btnRequestHisHq.post {
            btnRequestHisHq.setText(info)
        }
    }

    fun setBtnSumDDInfo(info: String) {
        btnRequestSumDd.post {
            btnRequestSumDd.setText(info)
        }
    }

    fun clickHHQ() {
        DBUtils.switchDBName(Datas.dataNamesDefault)
        btnRequestHisHq.setText("HHQ_WORKING")
        needLogAfterDD = true
        judeWeekDayIndex = 0
        progressIndex = 0
        viewModel.setFilelogPath(DateUtils.formatToDay(FormatterEnum.YYYYMMDD__HH_MM_SS))
        getHisHq()
    }

    fun requestDealDetailBtn() {
        DBUtils.switchDBName(Datas.dataNamesDefault)
        var pair = DateUtils.isWeekDay(dealDetailBeginDateTimeStamp)
        LogUtil.d("requestDealDetailBtn:${pair.first},${pair.second}")
        if (pair.first) {
            progressIndex = 0
            var code = viewModel.detailCodeList[progressIndex]
            viewModel.getDealDetail(code, dealDetailBeginDate)
        } else {
            judeWeekDayIndex++
            dealDetailBeginDateTimeStamp =
                dealDetailBeginDateTimeStamp - judeWeekDayIndex * 24 * 60 * 60 * 1000
            requestDealDetailBtn()
        }
    }

    private fun getHisHq() {
        var code = viewModel.codeNameList[progressIndex].split("####")[0].replace("sz", "")
            .replace("sh", "")
        viewModel.getHisHq(code)
    }


    private fun viewModelObserve() {
        viewModel.setActivity(this)
        viewModel.loadState.observe(this, Observer {
            when (it) {
                is LoadState.Success -> {
                    when (it.type) {
                        viewModel.REQUEST_DealDETAIL -> {
                            val dealList =
                                GsonHelper.parseArray(it.json, SinaDealDatailBean::class.java)

                            LogUtil.d("dealList size:${dealList.size} name:${dealList.get(0).name}")
                        }
                        viewModel.REQUEST_PRICESHIS -> {
                            val document = Jsoup.parse(it.json)
                            val chars = document.body()
                                .getElementsByClass("main")[0].getElementsByTag("tbody")[0].getElementsByTag(
                                "tr"
                            )
                            val priceHisDealList = ArrayList<PriceHisBean>()
                            chars.forEach {
                                val priceHisBean = PriceHisBean()
                                priceHisBean.dealPrice =
                                    it.getElementsByTag("td")[0].text().toFloat()
                                priceHisBean.dealStocks =
                                    it.getElementsByTag("td")[1].text().toFloat()
                                priceHisBean.dealPercent = it.getElementsByTag("td")[2].text()
                                priceHisDealList.add(priceHisBean)
                            }
                            priceHisDealList.forEach {
                                LogUtil.d("chars size:${priceHisDealList.size} dealPrice:${it.dealPrice} dealStocks:${it.dealStocks} dealPercent:${it.dealPercent}")
                            }
                        }
                        viewModel.REQUEST_HIS_HQ -> {

                            try {
                                val hisHqBean = GsonHelper.parseArray(it.json, HisHqBean::class.java)
                                var sumStr = ""
                                if (null != hisHqBean[0].stat) {
                                    sumStr =
                                        "${viewModel.codeNameList[progressIndex]}===累计:${hisHqBean[0].stat[1]},pencent:${hisHqBean[0].stat[3]},lowest:${hisHqBean[0].stat[4]},highest:${hisHqBean[0].stat[5]}"
                                    sumStr =
                                        String(sumStr.toByteArray(), Charset.forName("UTF-8")).replace(
                                            "��",
                                            "至"
                                        )
                                }

                                setBtnHHQInfo("hhq_"+hisHqBean[0].code.replace("cn_", ""))
                                viewModel.getHisHqAnalyseResult(
                                    hisHqBean[0].hq,
                                    sumStr,
                                    hisHqBean[0].code.replace("cn_", ""),
                                    (if (null == hisHqBean[0].stat) null else hisHqBean[0].stat)
                                )

                            } catch (e: Exception) {
                                hHqGoNext()

                            }
                        }
                    }
                }
                is LoadState.Fail -> {

                }
                is LoadState.Loading -> {

                }
                is LoadState.GoNext -> {
                    when (it.type) {
                        viewModel.REQUEST_HIS_HQ -> {
                            hHqGoNext()
                        }
                        viewModel.REQUEST_DealDETAIL -> {
                            progressIndex++
                            LogUtil.d("-----${viewModel.detailCodeList.size}-----progressIndex:$progressIndex")
//                            if (progressIndex < 2) {
                            if (progressIndex < viewModel.detailCodeList.size) {
                                var code = viewModel.detailCodeList[progressIndex]
                                viewModel.getDealDetail(
                                    code,
                                    dealDetailBeginDate
                                )
                            } else if (progressIndex == viewModel.detailCodeList.size) {
                                btnRequestDealDetail.setText("DealDetail_Completed")
                                clickHHQ()
                            }
//                            else {
//                                if (needLogAfterDD) {
//                                    viewModel.logDealDetailHqSum()
//                                }


//
//                            }
                        }
                    }
                }
            }
        })
    }

    private fun hHqGoNext() {
        progressIndex++
        LogUtil.d("progressIndex:$progressIndex / ${viewModel.codeNameList.size}")
        if (progressIndex < viewModel.codeNameList.size) {
            getHisHq()
        } else {
            viewModel.getPriceHisFileLog()
        }
    }
}