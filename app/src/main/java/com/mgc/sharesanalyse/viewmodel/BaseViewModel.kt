package com.mgc.sharesanalyse.viewmodel

import android.util.Log
import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mgc.sharesanalyse.R
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.LogUtil
import com.mgc.sharesanalyse.utils.ResUtil

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
}