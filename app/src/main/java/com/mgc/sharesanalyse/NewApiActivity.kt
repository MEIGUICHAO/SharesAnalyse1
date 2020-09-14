package com.mgc.sharesanalyse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mgc.sharesanalyse.base.App
import com.mgc.sharesanalyse.entity.HisHqBean
import com.mgc.sharesanalyse.entity.PriceHisBean
import com.mgc.sharesanalyse.entity.SinaDealDatailBean
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import com.mgc.sharesanalyse.viewmodel.NewApiViewModel
import kotlinx.android.synthetic.main.activity_new_api.*
import org.jsoup.Jsoup

class NewApiActivity : AppCompatActivity() {

    lateinit var viewModel: NewApiViewModel
    var progressIndex = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_api)
        DaoManager.setDbName("newapiDatabases")
        viewModel = ViewModelProvider(this).get(NewApiViewModel::class.java)
        viewModelObserve()

        btnRequestDealDetail.setOnClickListener {
            viewModel.getDealDetail("sh601216","2020-09-11")
        }
        btnRequestPricehis.setOnClickListener {
            viewModel.getPricehis("sh601216","2020-09-11","2020-09-11")
        }
        btnRequestHisHq.setOnClickListener {
            progressIndex = 0
            viewModel.setFilelogPath(DateUtils.formatToDay(FormatterEnum.YYYYMMDD__HH_MM_SS))
            getHisHq()
        }
    }

    private fun getHisHq() {
        var code = viewModel.stocksArray[progressIndex]!!.replace("sz", "").replace("sh", "")
        viewModel.getHisHq(code)
    }


    private fun viewModelObserve() {
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
                                    it.getElementsByTag("td")[0].text().toDouble()
                                priceHisBean.dealStocks =
                                    it.getElementsByTag("td")[1].text().toDouble()
                                priceHisBean.dealPercent = it.getElementsByTag("td")[2].text()
                                priceHisDealList.add(priceHisBean)
                            }
                            priceHisDealList.forEach {
                                LogUtil.d("chars size:${priceHisDealList.size} dealPrice:${it.dealPrice} dealStocks:${it.dealStocks} dealPercent:${it.dealPercent}")
                            }
                        }
                        viewModel.REQUEST_HIS_HQ -> {
                            val hisHqBean = GsonHelper.parseArray(it.json, HisHqBean::class.java)
                            viewModel.getHisHqAnalyseResult(hisHqBean[0].hq, hisHqBean[0].code)

                        }
                    }
                }
                is LoadState.Fail -> {

                }
                is LoadState.Loading -> {

                }
                is LoadState.GoNext ->{
                    when (it.type) {
                        viewModel.REQUEST_HIS_HQ ->{
                            progressIndex++
//                            if (progressIndex < 5) {
                            if (progressIndex < viewModel.stocksArray.size) {
                                getHisHq()
                            }
                        }
                    }
                }
            }
        })
    }
}