package com.mgc.sharesanalyse.base

import com.mgc.sharesanalyse.utils.DateUtils
import com.mgc.sharesanalyse.utils.FormatterEnum

object Datas {

    const val CHDD = "DD_"
    const val WinChromeAgent =
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36"
    const val bwcUrl = "https://bwc.waimaimingtang.com"
    const val UserAgent =
        "Mozilla/5.0 (Linux; Android 10; Pixel XL Build/QQ3A.200805.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.62 XWEB/2691 MMWEBSDK/200801 Mobile Safari/537.36 MMWEBID/7894 MicroMessenger/7.0.18.1740(0x27001239) Process/appbrand0 WeChat/arm64 NetType/WIFI Language/zh_CN ABI/arm64"
    var url = "http://hq.sinajs.cn"
    const val sinaVipStockUrl = "https://vip.stock.finance.sina.com.cn"
    const val sinaMarketUrl = "http://market.finance.sina.com.cn"
    const val sohuStockUrl = "https://q.stock.sohu.com/"
    const val xqUrl = "https://xueqiu.com"
    const val NUM_100M = 100000000
    const val NUM_W2Y = 10000
    const val NUM_WAN = 10000 * 100
    const val sinaVipStockUrlRefer =
        "https://vip.stock.finance.sina.com.cn/quotes_service/view/cn_bill.php?symbol="

    var DBPath = "/data/data/" + App.getContext().getPackageName() + "/databases"
    var tableName = "stock_"
    var dealDetailTableName = "DD_"
    var sdd = "SDD_"
    const val shdd = "SHDD_"
    var shddAll = "SHDD_ALL_" + DateUtils.formatToDay(FormatterEnum.YYYY)
    var shddAllTag = "SHDD_ALL_"
    var sddALL = "SDD_ALL_" + DateUtils.formatToDay(FormatterEnum.YYYY)
    var limitSize = 160
    var basebase = 30
    var SPGetHQCodeDate = "SPGetHQCodeDate"
    var baseNum = 30 / basebase
    var limitPerAmount = 500 * baseNum
    var intervalTime = basebase * 1000 * baseNum
    const val dataNamesDefault = "newapiDatabases"
    const val SumProressTB = "SP_TB"
    const val DBFilter = "DB_FILTER_"
    const val TBFilter = "FILTER_"
    const val XQCookies: String =
        "device_id=64fa9d9e2ff422da6cb5b83955c6df2b; s=cu11d1ihnu; Hm_lvt_1db88642e346389874251b5a1eded6e3=1598319404,1599793274; xq_a_token=636e3a77b735ce64db9da253b75cbf49b2518316; xqat=636e3a77b735ce64db9da253b75cbf49b2518316; xq_r_token=91c25a6a9038fa2532dd45b2dd9b573a35e28cfd; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTYwMjY0MzAyMCwiY3RtIjoxNjAwMzA4OTE0NzIxLCJjaWQiOiJkOWQwbjRBWnVwIn0.macMAtICowozpKxmHbt5FyjzUYhKaaQ4JIZlSBSI4OetogsyvwBPQZfLkOd3x8Bac54loKWfAusrU7s-vIOZR68FNScnUVZ0oo_2A4aS-jubHffZvodr2c5la2tUFiMQ6AzbrbPbvMGblyW37tpL-wRRTHoDi9Gi2qASeMsojOZ78j2IVtlk7nWSXN2ydAhWNj0tb9r0xIvb-C8aEmpW-IHJM1ycX0sLwCd5ZP1ciqV93nzE1nHyVOew_lIHg27ulvm-Mc2icH9VdzncAq1pJ1nyLfL14S3QMBrDr4sBeHY_5o9lJqcJ_AX1kPoaiZZlAxsTYVX7cstaImZvbdSk9Q; u=451600308915121; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1600310043; acw_tc=2760821516003106435504085e46e51e350ebe50f7e9659eb68f52c329e523"

    //------------------------------------
    //    const val DEBUG = true
//    const val MAPPFilter = "MAPPF_DEBUG_"
//    const val MAPPFilterDB = "MAPPFilterDB_DEBUG"
    //------------------------------------
    const val DEBUG = true
    const val FILTER_CODE_TB = "FC_"
    const val MAPPFilterDB = "FilterDB_"

    //------------------------------------
    val DEBUG_Code = listOf("300278")
    //    val DEBUG_Code = listOf("688133","300278","000544")
    const val FILTER_TYPE_COUNT = 2
    const val FILTER_TYPE_BEGIN = 2
    const val BIG_RED_RANGE = 0.07.toFloat()
    const val HHQDayCount = 8
    const val REVERSE_TB_P30_11 = "A_RTB_30_1"
    const val REVERSE_TB_P50_11 = "A_RTB_50_1"
    const val REVERSE_TB_P30_33 = "A_RTB_30_3"
    const val REVERSE_TB_P50_33 = "A_RTB_50_10"
    const val REVERSE_TB_P30_55 = "A_RTB_30_5"
    const val REVERSE_TB_P50_55 = "A_RTB_50_5"
    const val REVERSE_TB_P30_10 = "A_RTB_30_10"
    const val REVERSE_TB_P50_10 = "A_RTB_50_10"
    const val A_SLL_TB_ = "A_SLL"
    const val REVERSE_KJ_DB = "REV_KJ_DB"
    const val Derby = "Derby_"
    const val GAP_GG = "GAP_GG_"
    const val GAP_BG = "GAP_BG_"
//    const val HHQDayCount = 6

}