package com.mgc.sharesanalyse.viewmodel

import android.util.Log
import android.util.SparseArray
import androidx.core.util.isEmpty
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.R
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.LogUtil
import com.mgc.sharesanalyse.utils.ResUtil
import kotlinx.coroutines.*
import java.util.regex.Pattern


class MainViewModel : ViewModel() {
    val sharesDats = MutableLiveData<SparseArray<String>>()
    val loadState = MutableLiveData<LoadState>()
    val stocksArray = ResUtil.getSArray(R.array.stocks_code)
    val urlArray = arrayOfNulls<String>(stocksArray.size / 100 + 1)
    var isInit = true


    fun requestData() {
        if (sharesDats.value == null) {
            sharesDats.value = SparseArray<String>()
        } else {
            sharesDats.value!!.clear()
        }

        if (isInit) {
            isInit = false
            Log.d("mgc", "splitArray size:${stocksArray.size}")
            for (index in 0..stocksArray.size-1) {
                LogUtil.d("mgc", "splitArray index:${index / 100},code:${stocksArray[index]}")
                urlArray[index / 100] =
                    (if (urlArray[index / 100].isNullOrEmpty()) "sh${stocksArray[index]}" else urlArray[index / 100] + ",sh${stocksArray[index]}")
                LogUtil.d("mgc", "url ${index / 100}:${urlArray[index / 100]}")

            }
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
            loadState.value = LoadState.Success()

        }, {
            loadState.value = LoadState.Fail(it.message ?: "加载失败")

        })

    }






}