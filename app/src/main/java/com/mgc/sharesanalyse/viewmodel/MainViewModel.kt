package com.mgc.sharesanalyse.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.LogUtil
import kotlinx.coroutines.*
import java.util.regex.Pattern


class MainViewModel : ViewModel() {
    val sharesDats = MutableLiveData<List<String>>()
    val loadState = MutableLiveData<LoadState>()


    fun requestData() {
        val urlArray = arrayOfNulls<String>(10)
        val splitArray = Datas.sharesList.split(")")
        Log.d("mgc", "splitArray size:${splitArray.size}")

        for (index in 0..splitArray.size) {
            if (index < 1000) {
                val keepDigital =
                    (if (urlArray[index / 100].isNullOrEmpty()) "sh" else ",sh") + keepDigital(
                        splitArray[index]
                    )
                Log.d("mgc", "keepDigital:$keepDigital")
                urlArray[index / 100] =
                    (if (urlArray[index / 100].isNullOrEmpty()) "" else urlArray[index / 100]) + keepDigital
            }
        }

        var resultArray = arrayOfNulls<Deferred<String>>(10)
        var index = 0

        launch({
            loadState.value = LoadState.Loading()
            urlArray.forEach {
                it?.let {
                    LogUtil.d("mgc", "!!!!index$index,url:$it")
                    resultArray[index] = RetrofitManager.reqApi.getSharesDatas(it)
                    index++
                }
            }
            index = 0
            withContext(Dispatchers.Default){
                resultArray.forEach {
                    it?.let {
                        LogUtil.d("!!!index:$index,result size:null")
                        var result = it.await()
                        LogUtil.d("!!!index:$index,result size contains:${result.contains(";")}")
                        val split = result.split(";")
                        LogUtil.d("!!!index:$index,result size:${split.size}")
                        split.forEach{
                            LogUtil.d("result:$it")
                        }
                    }
                    Thread.sleep(50)
                    index++
                }
            }
            loadState.value = LoadState.Success()

        },{
            loadState.value = LoadState.Fail(it.message ?: "加载失败")

        })

    }


    fun keepDigital(oldString: String): String {
        val newString = StringBuffer()
        val matcher = Pattern.compile("\\d").matcher(oldString)
        while (matcher.find()) {
            newString.append(matcher.group())
        }
        return newString.toString()
    }

}