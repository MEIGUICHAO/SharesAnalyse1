package com.mgc.sharesanalyse

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import io.reactivex.Observable
import kotlinx.android.synthetic.main.act_reasoning_result.*
import kotlinx.android.synthetic.main.activity_new_api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.util.concurrent.TimeUnit

class NewApiActivity : AppCompatActivity() {

    lateinit var viewModel: NewApiViewModel
    var progressIndex = 0
    var judeWeekDayIndex = 0
    var isCurrentHq = false
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
    var lastDealDay = ""

    private fun unzipForeach(zipList: ArrayList<String>, index: Int) {
        val path = "/data/data/" + getPackageName() + "/databases"
        GlobalScope.launch(Dispatchers.IO) {
            ZipUtils.UnZipFolder(this@NewApiActivity, zipList[index], path)
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
        DataSettingUtils.setActivity(this)
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
//            DBUtils.switchDBName("SH_CHDD_2012")
//            if (Datas.DEBUG) {
//                Datas.DEBUG_Code.forEach { code->
//                    DBUtils.dropTable("DD_$code")
//                }
//
//            }
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
                    Datas.DEBUG_Code.forEach { code ->
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
            if (DateUtils.ifAfterToday1530()) {
                DBUtils.switchDBName(Datas.dataNamesDefault)
                DBUtils.dropTable(viewModel.getHHQTableName())
            }
            clickHHQ()
        }
        btnRequestWYToDayHq.setOnClickListener {
            if (DateUtils.ifAfterToday1530() && DateUtils.isWeekDay(System.currentTimeMillis()).first) {
                var ysdts = DateUtils.formatYesterDayTimeStamp()
                while (!DateUtils.isWeekDay(ysdts).first) {
                    ysdts = ysdts - 24 * 60 * 60 * 1000
                }
                lastDealDay = DateUtils.format(
                    ysdts,
                    FormatterEnum.YYYYMMDD
                )
//                DBUtils.switchDBName("688126".toCodeHDD(lastDealDay, FormatterEnum.YYYYMMDD))
//                DBUtils.queryCodeHDDIsExsitByDate()
//                DBUtils.switchDBName(Datas.dataNamesDefault)
//                DBUtils.dropTable(viewModel.getCurrentHQTableName())
                clickCurrentHQ()
            } else {
                Toast.makeText(this, "时间未到", Toast.LENGTH_SHORT).show()
            }
        }
        btnBwcList.setOnClickListener {
            viewModel.bwc()
        }
        btnRequestSumDd.setOnClickListener {
            App.getSinglePool().execute {
                viewModel.getSumDD(isCurrentHq)
            }
        }
        btnReverse.setOnClickListener {
            App.getSinglePool().execute {

//                DBUtils.switchDBName(Datas.REVERSE_KJ_DB)
//                DBUtils.dropTable(Datas.REV_OC_OO_10)
//                DBUtils.dropTable(Datas.REV_OC_OO_30)
//                DBUtils.dropTable(Datas.REV_OC_OO_50)
                viewModel.reverseResult()
                btnRevAllTb.post {
                    btnRevAllTb.performClick()
                }
            }
        }
        btnGapResult.setOnClickListener {
            App.getSinglePool().execute {
                viewModel.foreachGapResult()
            }
        }
        btnRevFilter.setOnClickListener {
            App.getSinglePool().execute {
                viewModel.filterRev()
            }
        }
        btnCopyFilterTBResult.setOnClickListener {
            App.getSinglePool().execute {
//                DBUtils.queryDeleteTBByDBAndLimit("AA_FILTER_60_30_36",Datas.REV_FILTERDB+"2020")
                viewModel.getOtherResultByFilterTb("AA_FILTER_50_36")
            }
        }
        btnLogBBRangeFile.setOnClickListener {
            App.getSinglePool().execute {
                viewModel.logBBRangeFile()
            }
        }
        btnResoning.setOnClickListener {
            App.getSinglePool().execute {

                DBUtils.switchDBName(Datas.REV_RESONING_DB)
//                DBUtils.dropTable("All_Reasoning_50")
//                DBUtils.dropTable("All_Reasoning_30")
//                DBUtils.dropTable("Reasoning")
//                setBtnResoning("Resoning_Working")
                viewModel.reasoningResult(false)
            }
        }
        btnGetResoningResult.setOnClickListener {
            val intent = Intent(this, ReasoningActivity::class.java)
            intent.putExtra("TYPE", 1)
            startActivity(intent)
        }
        btnGetAll30.setOnClickListener {
            val intent = Intent(this, ReasoningActivity::class.java)
            intent.putExtra("TYPE", 2)
            startActivity(intent)
        }
        btnGetAll50.setOnClickListener {
            val intent = Intent(this, ReasoningActivity::class.java)
            intent.putExtra("TYPE", 3)
            startActivity(intent)
        }
        btnGetAllOCOO30.setOnClickListener {
            val intent = Intent(this, ReasoningActivity::class.java)
            intent.putExtra("TYPE", 4)
            startActivity(intent)
        }
        btnGetAllOCOO50.setOnClickListener {
            val intent = Intent(this, ReasoningActivity::class.java)
            intent.putExtra("TYPE", 5)
            startActivity(intent)
        }
        btnRevAllTb.setOnClickListener {

//            val (rangeMax26,rangeMin26)= DataSettingUtils.getMMByValue(Pair("-13.84","-13.84"))
//            val (rangeMax24,rangeMin24)= DataSettingUtils.getMMByValue(Pair("-11.72","-11.28"))
//            val rangeMax26 = (-13.84).toFloat().toGetReasoningJudgeDFloat()
//            val rangeMin26 = (-11.28).toFloat().toGetReasoningJudgeDFloat()
//            val rangeMax24 = (-13.84).toFloat().toGetReasoningJudgeXFloat()
//            val rangeMin24 = (-11.72).toFloat().toGetReasoningJudgeXFloat()
//            LogUtil.d("!!$rangeMax26,$rangeMin26==$rangeMax24,$rangeMin24")
            App.getSinglePool().execute {
                revAllJudgeResult()
                btnReasoningAll.post {
                    btnReasoningAll.performClick()
                }
            }
        }
        btnReasoningAll.setOnClickListener {
//            DBUtils.switchDBName(Datas.REV_RESONING_DB)
//            DBUtils.dropTable("All_Reasoning_50")
//            DBUtils.dropTable("All_Reasoning_30")
//            DBUtils.dropTable(Datas.ALL_Reaoning_OC_OO_30)
//            DBUtils.dropTable(Datas.ALL_Reaoning_OC_OO_50)

            val builder = AlertDialog.Builder(this)
            builder.setMessage("ReasoningAll?")
            builder.setPositiveButton("yes",
                object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        App.getSinglePool().execute {
                            viewModel.reasoningAll()
//                viewModel.updateReasoningTB()
//                viewModel.sortContinue30Map()
                        }
                    }

                })
            builder.create().show()
        }
        btnGetHolderChange.setOnClickListener {
            App.getSinglePool().execute {
                viewModel.hoderCodeIndex = 0
                viewModel.getHolderChange()
            }
        }

        if (Datas.IS_INTERVAL) {
            RxTimerUtil.interval(TimeUnit.HOURS,1, object : RxTimerUtil.IRxNext {
                override fun doNext(number: Long) {
                    if (DateUtils.ifAfterToday1700()&&DateUtils.ifBeforToday1800()) {
                        btnRequestHisHq.post {
                            btnRequestHisHq.performClick()
                        }
                    }
                }

            })
        }
    }


    fun revAllJudgeResult() {
        DBUtils.switchDBName(Datas.REV_RESONING_DB)
//        DBUtils.dropTable("All_30")
//        DBUtils.dropTable("All_50")
        viewModel.revAllJudgeResult()

//        DBUtils.dropTable(Datas.ALL_OC_OO_30)
//        DBUtils.dropTable(Datas.ALL_OC_OO_50)
        viewModel.revOCOOJudgeResult()
    }

    fun setBtnGetHolderChange(info: String) {
        btnGetHolderChange.post {
            btnGetHolderChange.setText(info)
        }
    }
    fun setBtnReasoningAll(info: String) {
        btnReasoningAll.post {
            btnReasoningAll.setText(info)
        }
    }

    fun setBtnGetAll30(info: String) {
        btnGetAll30.post {
            btnGetAll30.setText(info)
        }
    }

    fun setBtnGetAll50(info: String) {
        btnGetAll50.post {
            btnGetAll50.setText(info)
        }
    }

    fun setOCOO30(info: String) {
        btnGetAllOCOO30.post {
            btnGetAllOCOO30.setText(info)
        }
    }

    fun setOCOO50(info: String) {
        btnGetAllOCOO50.post {
            btnGetAllOCOO50.setText(info)
        }
    }

    fun setBtnRevAllTb(info: String) {
        btnRevAllTb.post {
            btnRevAllTb.setText(info)
        }
    }

    fun setReasoningProgress(info: String) {
        tvReasoning.post {
            tvReasoning.setText(info)
        }
    }

    fun setBtnResoning(info: String) {
        LogUtil.d("setBtnResoning-->$info")
        btnResoning.post {
            btnResoning.setText(info)
        }
    }


    fun setBtnLogBBRangeFile(info: String) {
        btnLogBBRangeFile.post {
            btnLogBBRangeFile.setText(info)
        }
    }

    fun setBtnCopyFilterTBResult(info: String) {
        btnCopyFilterTBResult.post {
            btnCopyFilterTBResult.setText(info)
        }
    }

    fun setBtnRevFilterInfo(info: String) {
        btnRevFilter.post {
            btnRevFilter.setText(info)
        }
    }

    fun setBtnGapResultInfo(info: String) {
        btnGapResult.post {
            btnGapResult.setText(info)
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

    fun setBtnCurHQInfo(info: String) {
        btnRequestWYToDayHq.post {
            btnRequestWYToDayHq.setText(info)
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
        isCurrentHq = false
        needLogAfterDD = true
        judeWeekDayIndex = 0
        progressIndex = 0
        viewModel.setFilelogPath(DateUtils.formatToDay(FormatterEnum.YYYYMMDD__HH_MM_SS))
        getHisHq()
    }

    fun clickCurrentHQ() {
        DBUtils.switchDBName(Datas.dataNamesDefault)
        isCurrentHq = true
        btnRequestWYToDayHq.setText("Current_HQ_WORKING")
        judeWeekDayIndex = 0
        progressIndex = 0
        getCurrentHq()
    }

    fun requestDealDetailBtn() {
        DBUtils.switchDBName(Datas.dataNamesDefault)
        val pair = DateUtils.isWeekDay(dealDetailBeginDateTimeStamp)
        LogUtil.d("requestDealDetailBtn:${pair.first},${pair.second}")
        if (pair.first) {
            progressIndex = 0
            val code = viewModel.detailCodeList[progressIndex]
            viewModel.getDealDetail(code, dealDetailBeginDate)
        } else {
            judeWeekDayIndex++
            dealDetailBeginDateTimeStamp =
                dealDetailBeginDateTimeStamp - judeWeekDayIndex * 24 * 60 * 60 * 1000
            requestDealDetailBtn()
        }
    }

    private fun getHisHq() {
        val code = viewModel.codeNameList[progressIndex].split("####")[0].replace("sz", "")
            .replace("sh", "")
        viewModel.getHisHq(code)
    }

    private fun getCurrentHq() {
        val code = viewModel.codeNameList[progressIndex].split("####")[0].replace("sz", "")
            .replace("sh", "")
        viewModel.getCurrentHq(code, lastDealDay)
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
                                val hisHqBean =
                                    GsonHelper.parseArray(it.json, HisHqBean::class.java)
//                                var sumStr = ""
//                                if (null != hisHqBean[0].stat) {
//                                    sumStr =
//                                        "${viewModel.codeNameList[progressIndex]}===累计:${hisHqBean[0].stat[1]},pencent:${hisHqBean[0].stat[3]},lowest:${hisHqBean[0].stat[4]},highest:${hisHqBean[0].stat[5]}"
//                                    sumStr =
//                                        String(sumStr.toByteArray(), Charset.forName("UTF-8")).replace(
//                                            "��",
//                                            "至"
//                                        )
//                                }

                                if (isCurrentHq) {
                                    setBtnCurHQInfo("Curhq_" + hisHqBean[0].code)
                                } else {
                                    setBtnHHQInfo("hhq_" + hisHqBean[0].code.replace("cn_", ""))
                                }
                                hHqGoNext()
//                                viewModel.getHisHqAnalyseResult(
//                                    hisHqBean[0].hq,
//                                    sumStr,
//                                    hisHqBean[0].code.replace("cn_", ""),
//                                    (if (null == hisHqBean[0].stat) null else hisHqBean[0].stat)
//                                )

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
            if (isCurrentHq) {
                getCurrentHq()
            } else {
                getHisHq()
            }
        } else {
            viewModel.getPriceHisFileLog(isCurrentHq)
        }
    }
}