package com.mgc.sharesanalyse.base

object Datas {

    const val bwcUrl = "https://bwc.waimaimingtang.com"
    const val UserAgent = "Mozilla/5.0 (Linux; Android 10; Pixel XL Build/QQ3A.200805.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.62 XWEB/2691 MMWEBSDK/200801 Mobile Safari/537.36 MMWEBID/7894 MicroMessenger/7.0.18.1740(0x27001239) Process/appbrand0 WeChat/arm64 NetType/WIFI Language/zh_CN ABI/arm64"
    var url = "http://hq.sinajs.cn"
    const val dealDetailUrl = "https://vip.stock.finance.sina.com.cn"
    const val pricehisUrl = "http://market.finance.sina.com.cn"
    const val hisHqUrl = "https://q.stock.sohu.com/"

    var tableName = "stock_"
    var limitSize = 160
    var basebase = 30
    var baseNum = 30/basebase
    var limitPerAmount = 500 * baseNum
    var intervalTime = basebase * 1000 * baseNum
    const val dataNamesDefault = "newapiDatabases"


}