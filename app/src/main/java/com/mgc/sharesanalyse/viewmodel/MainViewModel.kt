package com.mgc.sharesanalyse.viewmodel

import android.util.Log
import android.util.SparseArray
import androidx.core.util.isEmpty
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
    val sharesDats = MutableLiveData<SparseArray<String>>()
    val loadState = MutableLiveData<LoadState>()


    fun requestData() {
        if (sharesDats.value == null) {
            sharesDats.value = SparseArray<String>()
        } else {
            sharesDats.value!!.clear()
        }

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
                    val split = it.split(",")
                    LogUtil.d("url array size:${split.size}")
                    LogUtil.d("mgc", "!!!!index$index,url:$it")
                    resultArray[index] = RetrofitManager.reqApi.getSharesDatas(it)
                    index++
                }
            }
            index = 0
            withContext(Dispatchers.Default){
                resultArray.forEach {
                    it?.let {
                        var result = it.await()
                        if (result.contains(";")) {
                            val split = result.split(";")
                            LogUtil.d("!!!index:$index,result size:${split.size}")
                            sharesDats.value!!.put(index,result)
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