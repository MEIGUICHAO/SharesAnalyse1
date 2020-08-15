package com.mgc.sharesanalyse.base

import com.mgc.sharesanalyse.utils.DateUtils
import com.mgc.sharesanalyse.utils.FormatterEnum

object Datas {
    var url = "http://hq.sinajs.cn"


    var DBName = "sharesDB_" + DateUtils.format(
        System.currentTimeMillis(),
        FormatterEnum.YYYY_MM_DD
    )

//    var DBName = "sharesDB_2020-08-13"

    var tableName = "stock_"











}