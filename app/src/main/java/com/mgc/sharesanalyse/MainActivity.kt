package com.mgc.sharesanalyse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.utils.LogUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    val urlArray = arrayOfNulls<String>(10)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRequest.setOnClickListener{
            requestData()
        }
    }

    private fun requestData() {
        for (index in 0..9) {
            urlArray[index] = null
        }
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
        GlobalScope.launch(Dispatchers.IO) {
            urlArray.forEach {
                it?.let {
                    LogUtil.d("mgc", "!!!!index$index,url:$it")
                    resultArray[index] = RetrofitManager.reqApi.getSharesDatas(it)
                    index++
                }
            }
            index = 0
            resultArray.forEach {
                var result = it!!.await()
                val split = result.split(";")
                LogUtil.d("mgc", "!!!index:$index,result size:${split.size}")
                split.forEach{
                    LogUtil.d("result:$it")
                }
                index++
            }

        }
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
