package com.mgc.sharesanalyse.viewmodel

import android.util.Log
import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.R
import com.mgc.sharesanalyse.entity.BWCDPJsonBean
import com.mgc.sharesanalyse.entity.BWCQPResultBean
import com.mgc.sharesanalyse.entity.PriceHisRecordGDBean
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

open class BaseViewModel:ViewModel() {

    val sharesDats = MutableLiveData<SparseArray<String>>()
    val loadState = MutableLiveData<LoadState>()
    var stocksArray = ResUtil.getSArray(R.array.code_all)
    var stocksNameArray = ResUtil.getSArray(R.array.code_all_name)
    var urlArray = emptyArray<String?>()
    var viewModelCode = R.array.code_all
    var viewModelCodeName = R.array.code_all_name
    val REQUEST_TYPE_1 = 1
    val REQUEST_DealDETAIL = 2
    val REQUEST_PRICESHIS = 3
    val REQUEST_HIS_HQ = 4

    var tag = "sh"
    var path = "sh"

    init {
        urlArray = arrayOfNulls<String>(stocksArray.size / 100 + 1)
        Log.d("mgc", "splitArray size:${stocksArray.size}")
        for (index in 0..stocksArray.size-1) {
            LogUtil.d("mgc", "splitArray index:${index / 100},code:${stocksArray[index]}")
            if (stocksArray[index].toDouble() < 600000) {
                tag = "sz"
            } else {
                tag = "sh"
            }
            urlArray[index / 100] =
                (if (urlArray[index / 100].isNullOrEmpty()) "$tag${stocksArray[index]}" else urlArray[index / 100] + ",$tag${stocksArray[index]}")
            LogUtil.d("mgc", "url ${index / 100}:${urlArray[index / 100]}")

        }
    }



    var bwcTag = "###########"
    var requestBody =
        "{\"userId\":\"20200429090359-43c4c27f70_user\",\"openid\":\"oOLE444a8L_g7Fu5pMl074lNDMVo\",\"businessId\":\"20200428151950-5fc381e926_business\",\"overbearfoodId\":\"$bwcTag\",\"serviceNoStr\":\"api_overbear_sign_up\",\"phoneNumber\":\"13316940915\",\"buyChannel\":\"autonomy\",\"shareUserId\":\"\"}"
    var bcw1Complete = false
    var bcw2Complete = false
    var bcw3Complete = false

    var bcw1Begin = false
    var bcw2Begin = false
    var bcw3Begin = false

    fun bwc() {
        var json = RetrofitManager.reqApi.getBwcDpList()
        launch({
            var mjson = json.await()
            var bean = GsonHelper.parse(mjson,BWCDPJsonBean::class.java)
            var slbID = "20200826094759-aede32d710_business"//三里半
            var gscfwID = "20200912151229-81bf084bc5_business"//"蛋包个饭"
            var dpjID = "20200908165211-ead7c9e1af_business"//"舌尖上的大盘鸡"
            val foodList = ArrayList<String>()
            bean.data.forEach {
                LogUtil.d("shopName:${it.shopName}")
                if (it.businessId == slbID || it.businessId == gscfwID || it.businessId == dpjID) {
                    foodList.add(it.overbearFoodEntityId)
                }
            }

            Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).subscribe {
                    val judeTime = DateUtils.formatToDay(FormatterEnum.YYYYMMDD)+" 00:30:00"
                    var timeStamp =  DateUtils.parse(judeTime, FormatterEnum.YYYYMMDD__HH_MM_SS)

                    if (System.currentTimeMillis() < timeStamp) {
//                        var result1：Def
                        var result1: Deferred<String>? = null
                        var result2: Deferred<String>? = null
                        var result3: Deferred<String>? = null
                        if (!bcw1Complete&&!bcw1Begin) {
                            bcw1Begin = true
                            var body1 = requestBody.replace(bwcTag,foodList[0])
                            result1 = RetrofitManager.reqApi.qiangBwc(body1)
                        }
                        if (!bcw2Complete&&!bcw2Begin) {
                            bcw2Begin = true
                            var body2 = requestBody.replace(bwcTag,foodList[1])
                            result2 = RetrofitManager.reqApi.qiangBwc(body2)
                        }
                        if (!bcw3Complete&&!bcw3Begin) {
                            bcw3Begin = true
                            var body3 = requestBody.replace(bwcTag,foodList[2])
                            result3 = RetrofitManager.reqApi.qiangBwc(body3)
                        }
                        launch {
                            if (null != result1) {
                                val resultBean1 =
                                    GsonHelper.parse(result1.await(), BWCQPResultBean::class.java)
                                if (resultBean1.code == 1 || resultBean1.code == -1) {
                                    bcw1Complete = true
                                } else {
                                    bcw1Begin = false
                                }
                            }
                            if (null != result2) {
                                val resultBean2 =  GsonHelper.parse(result2.await(), BWCQPResultBean::class.java)
                                if (resultBean2.code == 1 || resultBean2.code == -1) {
                                    bcw2Complete = true
                                } else {
                                    bcw2Begin = false
                                }
                            }
                            if (null != result3) {
                                val resultBean3 =  GsonHelper.parse(result3.await(), BWCQPResultBean::class.java)
                                if (resultBean3.code == 1 || resultBean3.code == -1) {
                                    bcw3Complete = true
                                } else {
                                    bcw3Begin = false
                                }
                            }
                        }

                    } else {
                        LogUtil.d("not begin")
                    }
                }
        })

    }
}