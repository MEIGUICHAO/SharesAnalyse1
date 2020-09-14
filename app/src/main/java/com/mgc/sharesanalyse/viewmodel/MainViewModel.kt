package com.mgc.sharesanalyse.viewmodel

import android.util.Log
import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.R
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.LogUtil
import com.mgc.sharesanalyse.utils.ResUtil
import kotlinx.coroutines.*


class MainViewModel : BaseViewModel() {


    fun requestData() {
        if (sharesDats.value == null) {
            sharesDats.value = SparseArray<String>()
        } else {
            sharesDats.value!!.clear()
        }

        var resultArray = arrayOfNulls<Deferred<String>>(stocksArray.size / 100 + 1)
        var index = 0

        launch({
            loadState.value = LoadState.Loading()
            urlArray.forEach {
                it?.let {
                    val split = it.split(",")
                    LogUtil.d("url array size:${split.size}")
                    LogUtil.d("mgc", "!!!!index$index,url:$it")
                    resultArray[index] = RetrofitManager.reqApi.getSharesDatas(it)
                    index++
                }
            }
            index = 0
            withContext(Dispatchers.Default) {
                resultArray.forEach {
                    it?.let {
                        var result = it.await()
                        if (result.contains(";")) {
                            val split = result.split(";")
                            LogUtil.d("!!!index:$index,result size:${split.size}")
                            sharesDats.value!!.put(index, result)
                        }
                    }
                    Thread.sleep(50)
                    index++
                }
            }
            loadState.value = LoadState.Success(REQUEST_TYPE_1)

        }, {
            loadState.value = LoadState.Fail()

        })

    }






}