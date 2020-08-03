package com.mgc.sharesanalyse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.utils.LogUtil
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    val urlArray = arrayOfNulls<String>(10)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val splitArray = Datas.sharesList.split(")")
        Log.d("mgc", "splitArray size:${splitArray.size}")

        for (index in 0..9) {
            urlArray[index] = Datas.url
        }
        for (index in 0..splitArray.size) {
            if (index < 1000) {
                val keepDigital = ",sh" + keepDigital(splitArray[index])
                Log.d("mgc", "keepDigital:$keepDigital")
                if (!keepDigital.isEmpty()) {
                    urlArray[index/100] = urlArray[index/100] + keepDigital
                }
            }
        }
        urlArray.forEach {
            var url = it!!.replace("=,", "=,")
            LogUtil.d("mgc", "url:$url")
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
