package com.mgc.sharesanalyse.base

object Datas {
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


}