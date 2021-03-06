package com.mgc.webviewjshelper.helper

import android.webkit.JavascriptInterface
import com.mgc.sharesanalyse.base.App
import com.mgc.sharesanalyse.utils.LogUtil
import org.jsoup.Jsoup


class JsInterface {


    @JavascriptInterface
    fun showSource(html: String?) {
        App.getSinglePool().execute {
            getHtmlContent(html!!)
        }
    }

    fun getHtmlContent(html: String) {
        LogUtil.d("getHtmlContent:$html")
        var document = Jsoup.parse(html)
        var divs = document.getElementsByTag("div")
        var allElement = document.body().allElements
        for (index in 0 until 2) {
            LogUtil.d("allElement $index:${allElement.html()}")

        }
    }

}