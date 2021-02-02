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
    const val wyStockUrl = "http://api.money.126.net"
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
    const val DEBUG = false
    const val FILTER_CODE_TB = "FC_"
    const val MAPPFilterDB = "FilterDB_"

    //------------------------------------
//    val DEBUG_Code = listOf("000010","002096","002370","002943","300641","300906","601015")
//    val DEBUG_Code = listOf("000816","002797","300491","600983","603698")//all_reaoning_50
    val DEBUG_Code = listOf("000816")
//    val DEBUG_Code = listOf("300337","002626","300152","300405","300247","300283","300029","300414"
//    ,"000561","002308","002508","002068","002732","000558","002724","300387")

//        val DEBUG_Code = listOf("688133","300278","000544")
    const val FILTER_TYPE_COUNT = 2
    const val FILTER_TYPE_BEGIN = 2
    const val BIG_RED_RANGE = 0.07.toFloat()
    const val HHQDayCount = 8
    const val REVERSE_TB_P30_11 = "A_RTB_30_1"
    const val REVERSE_TB_P50_11 = "A_RTB_50_1"
    const val REVERSE_TB_P30_33 = "A_RTB_30_3"
    const val REVERSE_TB_P50_33 = "A_RTB_50_3"
    const val REVERSE_TB_P30_55 = "A_RTB_30_5"
    const val REVERSE_TB_P50_55 = "A_RTB_50_5"
    const val REVERSE_TB_P30_10 = "A_RTB_30_10"
    const val REVERSE_TB_P50_10 = "A_RTB_50_10"
    const val REVERSE_TB_P30_15 = "A_RTB_30_15"
    const val REVERSE_TB_P50_15 = "A_RTB_50_15"
    const val REVERSE_TB_P30_20 = "A_RTB_30_20"
    const val REVERSE_TB_P50_20 = "A_RTB_50_20"
    const val REVERSE_TB_P30_25 = "A_RTB_30_25"
    const val REVERSE_TB_P50_25 = "A_RTB_50_25"
    const val REVERSE_TB_P30_30 = "A_RTB_30_30"
    const val REVERSE_TB_P50_30 = "A_RTB_50_30"
    const val REVERSE_TB_P30_36 = "A_RTB_30_36"
    const val REVERSE_TB_P50_36 = "A_RTB_50_36"

    const val REV_TR_TB_P30_11 = "TR_30_1"
    const val REV_TR_TB_P50_11 = "TR_50_1"
    const val REV_TR_TB_P30_33 = "TR_30_3"
    const val REV_TR_TB_P50_33 = "TR_50_3"
    const val REV_TR_TB_P30_55 = "TR_30_5"
    const val REV_TR_TB_P50_55 = "TR_50_5"
    const val REV_TR_TB_P30_10 = "TR_30_10"
    const val REV_TR_TB_P50_10 = "TR_50_10"
    const val REV_TR_TB_P30_15 = "TR_30_15"
    const val REV_TR_TB_P50_15 = "TR_50_15"
    const val REV_TR_TB_P30_20 = "TR_30_20"
    const val REV_TR_TB_P50_20 = "TR_50_20"
    const val REV_TR_TB_P30_25 = "TR_30_25"
    const val REV_TR_TB_P50_25 = "TR_50_25"
    const val REV_TR_TB_P30_30 = "TR_30_30"
    const val REV_TR_TB_P50_30 = "TR_50_30"
    const val REV_TR_TB_P30_36 = "TR_30_36"
    const val REV_TR_TB_P50_36 = "TR_50_36"
    const val A_SLL_TB_ = "A_SLL"
    const val REVERSE_KJ_DB = "REV_KJ_DB"
    const val GAP_RECORD_DB = "GAP_RECORD_DB"
    const val Derby = "Derby_"
    const val GAP_GG = "GAP_GG_"
    const val GAP_BG = "GAP_BG_"
    const val GAP_BEGIN = "BEGIN"
    const val REV_CODE_DB = "REV_DB"
    const val REV_CODE = "RC_"
    const val REV_CODE_DERBY = "RC_DERBY_"
    const val REV_CODE_TR = "RC_TR_"
    const val AA_FILTER_ = "AA_FILTER_"
    const val BB_FIL_COPY_ = "BB_FIL_COPY_"
    const val REV_FILTERDB = "REV_FILTERDB"
    const val REV_DAYS = 20
    const val FILTER_PROGRESS = 10
    const val FILTER_OC_OO_PROGRESS = 2
    const val FILTER_DERBY_PROGRESS = 5
    const val REV_RESONING_DB = "REV_RESONING_DB"
    const val REASONING_BEGIN_DATE = 20200601
    const val REASONING_DEBUG_CODE = 11
    val debugEndstr = if (DEBUG) "AND (${DEBUG_Code.toReasoningCodeList()})" else ""
    const val reasoning_debug = false
    const val reasoning_debug_begin_day = 20201215
    const val reasoning_debug_end_day = 20210201
    val reasoning_debug_end_str = if (reasoning_debug) " AND DATE < $reasoning_debug_begin_day" else ""
    const val REV_OC_OO_10 = "AA_REV_OC_OO_10"
    const val REV_OC_OO_30 = "AA_REV_OC_OO_30"
    const val REV_OC_OO_50 = "AA_REV_OC_OO_50"
    const val ALL_OC_OO_30 = "All_OC_OO_30"
    const val ALL_OC_OO_50 = "All_OC_OO_50"
    const val ALL_Reaoning_OC_OO_30 = "All_Reaoning_OC_OO_30"
    const val ALL_Reaoning_OC_OO_50 = "All_Reaoning_OC_OO_50"
//    const val FR_RR_LIMIT = "AND FR > 0 AND FR < 0.5 AND RR > 0.2"
    const val REVERSE_DEBUG= true
    const val REVERSE_BEGIN_CODE = 2540
    const val NEED_UPDATE_REV_= true
    val FR_RR_LIMIT = if (NEED_UPDATE_REV_) "" else " AND RR > 0.2 AND FR < 0.4 AND SIZE > 1"




}
