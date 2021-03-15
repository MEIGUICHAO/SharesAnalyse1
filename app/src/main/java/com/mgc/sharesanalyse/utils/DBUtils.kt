package com.mgc.sharesanalyse.utils

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import com.mgc.sharesanalyse.base.*
import com.mgc.sharesanalyse.entity.*


object DBUtils {

    var db: SQLiteDatabase
//    UPDATE Person SET FirstName='Fred' WHERE LastName='Wilson'

    init {
        db = DaoManager.getDB()
    }

    fun insertDealDetail2DateTable(dbName: String, dealDetailTableBean: DealDetailTableBean) {
        createDealDetailTable(dbName)
        if (!queryItemIsExsitByCode(dbName, dealDetailTableBean.code)) {
            var insertSqlStr = "INSERT INTO $dbName" +
                    "(CODE,NAME,ALLSIZE,PERCENT,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,M100J,M50J,M30J,M10J,M5J,M1J)" +
                    " VALUES${dealDetailTableBean.toSqlValues()}"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        }
    }

    fun insertOrUpdateSumDD2DateTable(
        date: String,
        dbName: String,
        sumDDBean: SumDDBean?,
        ddbean: DealDetailTableBean
    ) {
        LogUtil.d("@@ddBean m10Size:${ddbean.sizeBean.m10Size}")
        createSDD(dbName)
        ddbean.sizeBean.date = date.replace(Datas.sdd, "")
        if (!queryItemIsExsitByCode(dbName, ddbean.code)) {
            val insertSqlStr = "INSERT INTO $dbName" +
                    "(CODE,NAME,ALLSIZE,DATE,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,L01S,G5000M,G1000M,G500M,G100M,ALLSIZESDJ,M100SDJ,M50SDJ,M30SDJ,M10SDJ,M5SDJ,M1SDJ,M05SDJ,M01SDJ)" +
                    " VALUES${ddbean.toInsertSqlSumValues(ddbean, ddbean.allsize)}"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        } else {
            val ts = DateUtils.parse(date, FormatterEnum.YYYYMMDD)
            val ts2 = DateUtils.parse(sumDDBean!!.sizeBean!!.date, FormatterEnum.YYYYMMDD)
            LogUtil.d("ts:$date,ts2:${sumDDBean.sizeBean!!.date}")
            if (ts > ts2) {
                val sql =
                    "UPDATE $dbName SET ${sumDDBean.toUpdateSqlSumValues(ddbean)},DATE =${date.replace(
                        Datas.sdd,
                        ""
                    )}  WHERE CODE=${ddbean.code}"
                LogUtil.d("updateSqlStr:$sql")
                db.execSQL(sql)
            }
        }
    }

    fun setSDDPercent(dbName: String, percent: Float, code: String) {
        val sql = "UPDATE $dbName SET PERCENT = ${percent}  WHERE CODE=${code}"
        LogUtil.d("setSDDPercent:$sql")
        db.execSQL(sql)
    }

    fun setSDDMaxPercent(dbName: String, percent: Float, maxdate: String, code: String) {
        val sql = "UPDATE $dbName SET MAXPER = ${percent},MPD = ${maxdate}  WHERE CODE=${code}"
        LogUtil.d("setSDDMaxPercent:$sql")
        db.execSQL(sql)
    }

    fun setSDDLowPercent(dbName: String, percent: Float, lowdate: String, code: String) {
        val sql = "UPDATE $dbName SET LOWPER = ${percent},LPD = ${lowdate}  WHERE CODE=${code}"
        LogUtil.d("setSDDMaxPercent:$sql")
        db.execSQL(sql)
    }

//    (CODE,NAME,,,,,,,,,,,,,,
//    ,,,,,,,,)
//    (1,'平安银行',ALLSIZE 2074,M100S 0,M50S 2,M30S 0,M10S 6,M5S 25,M1S 455,M05S 527,M01S 1059,G5000M 13675.268635,G1000M 22820.396535000003,G500M 40074.291105000004,G100M 122086.51915499999,
//    ALLSIZESDJ 20201015:2074,M100SDJ ,M50SDJ 20201015:2,M30SDJ ,M10SDJ 20201015:6,M5SDJ ,M1SDJ 20201015:455,M05SDJ 20201015:527,M01SDJ 20201015:1059)

    fun insertHHq2DateTable(dbName: String, hisHqBean: PricesHisGDBean) {
        switchDBName(Datas.dataNamesDefault)
//        LogUtil.d("dbName:$dbName")
        createHHqTable(dbName)
        if (!queryHHqIsExsitByCode(dbName, hisHqBean.code.toInt().toString())) {
            var insertSqlStr = "INSERT INTO $dbName" +
                    "(CODE,JSON,MINEINFO)" +
                    " VALUES(${hisHqBean.code},'${hisHqBean.json}','${hisHqBean.mineInfo}')"
//            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        }
    }

    fun insertEmptyDealDetail2DateTable(dbName: String, dealDetailTableBean: DealDetailTableBean) {
        createDealDetailTable(dbName)
        if (!queryItemIsExsitByCode(dbName, dealDetailTableBean.code)) {
            var insertSqlStr = "INSERT INTO $dbName" +
                    "(CODE,NAME)" +
                    " VALUES(${dealDetailTableBean.code},${dealDetailTableBean.name})"
//            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        }
    }

    fun dropTable(dbName: String) {
        val dropTable = "DROP TABLE IF EXISTS $dbName"
        LogUtil.d("dropTable:$dropTable")
        db.execSQL(dropTable)
    }

    fun queryHHqIsExsitByCode(dbName: String, code: String): Boolean {
        var isexsit = false
        if (tabbleIsExist(dbName)) {
            LogUtil.d("code:$code")
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code))
            isexsit = cursor.count > 0
            LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
            cursor.close()
        }
//        LogUtil.d("queryHHqIsExsitByCode:$isexsit")
        return isexsit
    }

    fun queryItemIsExsitByCode(dbName: String, code: String): Boolean {
        var isexsit = false
        LogUtil.d("code:$code")
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code.toInt().toString()))
            isexsit = cursor.count > 0
            LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
            cursor.close()
        }
        return isexsit
    }

    fun queryHHqBeanByCode(dbName: String, code: String): PricesHisGDBean? {
        App.getmManager().switchDB(Datas.dataNamesDefault)
        var hHqBean: PricesHisGDBean? = null
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code.toInt().toString()))
            if (null != cursor && cursor.moveToFirst()) {
                hHqBean = PricesHisGDBean()
                var dealDetailAmountSizeBean = DealDetailAmountSizeBean()
                val code = cursor.getString(cursor.getColumnIndex("CODE"))
                val json = cursor.getString(cursor.getColumnIndex("JSON"))
                val mineInfo = cursor.getString(cursor.getColumnIndex("MINEINFO"))
                hHqBean.code = code
                hHqBean.json = json
                hHqBean.mineInfo = mineInfo
            }
            cursor.close()
        }
        return hHqBean
    }

    fun switchDBName(name: String) {
        LogUtil.d("switchDBName:$name")
        if (name != DaoManager.DB_NAME) {
            db.close()
        }
        App.getmManager().switchDB(name)
        db = DaoManager.getDB()
    }

    fun getLastCHDDBeanDate(code: String, tbName: String): String {
        val pathList = FileUtil.getFileNameList(Datas.DBPath)
        val mList = ArrayList<String>()
        pathList.forEach {
            if (it.contains("CHDD") && !it.contains("journal")) {
                mList.add(
                    it.replace("SZ_CHDD_", "").replace("SH_CHDD_", "").replace("CY_CHDD_", "")
                )
            }
        }
        mList.sortStringDateAsc(FormatterEnum.YYMM)
        var chddbean: ArrayList<CodeHDDBean>? = null
        var jianIndex = 1
//                = queryCHDDByTableName(tbName,code.toCodeHDD(mList[mList.size - 1], FormatterEnum.YYMM))

        while (null == chddbean) {
            if (mList.size - jianIndex < 0) {
                break
            }
            chddbean = queryCHDDByTableName(
                tbName,
                code.toCodeHDD(mList[mList.size - jianIndex], FormatterEnum.YYMM)
            )
            jianIndex++
        }

        return if (null != chddbean) chddbean[chddbean.size - 1].date else ""
    }

    fun queryOPByCHDD(code: String, isAll: Boolean, chddDateYM: String): Float {
        val pathList = FileUtil.getFileNameList(Datas.DBPath)
        var op = 0.toFloat()
        if (isAll) {
            val mList = ArrayList<String>()
            pathList.forEach {
                if (it.contains("CHDD") && !it.contains("journal")) {
                    mList.add(
                        it.replace("SZ_CHDD_", "").replace("SH_CHDD_", "").replace("CY_CHDD_", "")
                    )
                }
            }
            mList.sortStringDateAsc(FormatterEnum.YYMM)
            if (mList.size > 0) {
                switchDBName(code.toCodeHDD(mList[0], FormatterEnum.YYMM))
                val tbName = Datas.CHDD + code
                op = queryFirstOPByCodeHDD(tbName)
            }
        } else {
            val dbName = code.toCodeHDD(chddDateYM, FormatterEnum.YYMM)
            if (pathList.contains(dbName)) {
                switchDBName(dbName)
                val tbName = Datas.CHDD + code
                op = queryFirstOPByCodeHDD(tbName)
            }

        }

        return op
    }

    fun queryDealDetailByCode(dbName: String, code: String): DealDetailTableBean? {
        App.getmManager().switchDB(Datas.dataNamesDefault)
        var dealDetailTableBean: DealDetailTableBean? = null
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code.toInt().toString()))

            if (null != cursor && cursor.moveToFirst()) {
                dealDetailTableBean = DealDetailTableBean()
                var dealDetailAmountSizeBean = DealDetailAmountSizeBean()
                val code = cursor.getString(cursor.getColumnIndex("CODE"))
                val name = cursor.getString(cursor.getColumnIndex("NAME"))
                val allsize = cursor.getInt(cursor.getColumnIndex("ALLSIZE"))
                val percent = cursor.getFloat(cursor.getColumnIndex("PERCENT"))
                val m100s = cursor.getInt(cursor.getColumnIndex("M100S"))
                val m50s = cursor.getInt(cursor.getColumnIndex("M50S"))
                val m30s = cursor.getInt(cursor.getColumnIndex("M30S"))
                val m10s = cursor.getInt(cursor.getColumnIndex("M10S"))
                val m5s = cursor.getInt(cursor.getColumnIndex("M5S"))
                val m1s = cursor.getInt(cursor.getColumnIndex("M1S"))
                val m05s = cursor.getInt(cursor.getColumnIndex("M05S"))
                val m01s = cursor.getInt(cursor.getColumnIndex("M01S"))
                val m100j: String? = cursor.getString(cursor.getColumnIndex("M100J"))
                val m50j: String? = cursor.getString(cursor.getColumnIndex("M50J"))
                val m30j: String? = cursor.getString(cursor.getColumnIndex("M30J"))
                val m10j: String? = cursor.getString(cursor.getColumnIndex("M10J"))
                val m5j: String? = cursor.getString(cursor.getColumnIndex("M5J"))
                val m1j: String? = cursor.getString(cursor.getColumnIndex("M1J"))
                dealDetailAmountSizeBean.m100Size = m100s
                dealDetailAmountSizeBean.m50Size = m50s
                dealDetailAmountSizeBean.m30Size = m30s
                dealDetailAmountSizeBean.m10Size = m10s
                dealDetailAmountSizeBean.m5Size = m5s
                dealDetailAmountSizeBean.m1Size = m1s
                dealDetailAmountSizeBean.m05Size = m05s
                dealDetailAmountSizeBean.m01Size = m01s

                dealDetailAmountSizeBean.m100List =
                    m100j?.json2Array(DealDetailAmountSizeBean.M100::class.java)
                dealDetailAmountSizeBean.m50List =
                    m50j?.json2Array(DealDetailAmountSizeBean.M50::class.java)
                dealDetailAmountSizeBean.m30List =
                    m30j?.json2Array(DealDetailAmountSizeBean.M30::class.java)
                dealDetailAmountSizeBean.m10List =
                    m10j?.json2Array(DealDetailAmountSizeBean.M10::class.java)
                dealDetailAmountSizeBean.m5List =
                    m5j?.json2Array(DealDetailAmountSizeBean.M5::class.java)
                dealDetailAmountSizeBean.m1List =
                    m1j?.json2Array(DealDetailAmountSizeBean.M1::class.java)

                dealDetailTableBean.code = code
                dealDetailTableBean.name = name
                dealDetailTableBean.allsize = allsize
                dealDetailTableBean.percent = percent
                dealDetailTableBean.sizeBean = dealDetailAmountSizeBean

            }
            cursor.close()
        }
        return dealDetailTableBean
    }


    fun querySumDDBeanLastDate(dbName: String): String {
        var DATE = ""
        if (tabbleIsExist(dbName)) {
            var cursor = db.rawQuery("SELECT * FROM $dbName order by _ID desc", null)
            if (null != cursor && cursor.moveToFirst()) {
                DATE = cursor.getString(cursor.getColumnIndex("DATE"))
                var CODE = cursor.getString(cursor.getColumnIndex("CODE"))
                LogUtil.d("CODE:$CODE")
            }
            cursor.close()
        }
        return DATE
    }


    fun querySumDDBeanByCode(dbName: String, code: String): SumDDBean? {
        var sumDDBean: SumDDBean? = null
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code.toInt().toString()))

            if (null != cursor && cursor.moveToFirst()) {
                sumDDBean = SumDDBean()
                var dealDetailAmountSizeBean = DealDetailAmountSizeBean()
                //"(CODE,NAME,ALLSIZE,MAXPER,PERCENT,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,G5000M,G1000M,G500M,G100M,ALLSIZESDJ,M100SDJ,M50SDJ,M30SDJ,M10SDJ,M5SDJ,M1SDJ,M05SDJ,M01SDJ)"
                val code = cursor.getString(cursor.getColumnIndex("CODE"))
                val name = cursor.getString(cursor.getColumnIndex("NAME"))
                val MPD = cursor.getString(cursor.getColumnIndex("MPD"))
                val allsize = cursor.getInt(cursor.getColumnIndex("ALLSIZE"))
                val maxper = cursor.getFloat(cursor.getColumnIndex("MAXPER"))
                val LOWPER = cursor.getFloat(cursor.getColumnIndex("LOWPER"))
                val percent = cursor.getFloat(cursor.getColumnIndex("PERCENT"))
                val m100s = cursor.getInt(cursor.getColumnIndex("M100S"))
                val m50s = cursor.getInt(cursor.getColumnIndex("M50S"))
                val m30s = cursor.getInt(cursor.getColumnIndex("M30S"))
                val m10s = cursor.getInt(cursor.getColumnIndex("M10S"))
                val m5s = cursor.getInt(cursor.getColumnIndex("M5S"))
                val m1s = cursor.getInt(cursor.getColumnIndex("M1S"))
                val m05s = cursor.getInt(cursor.getColumnIndex("M05S"))
                val m01s = cursor.getInt(cursor.getColumnIndex("M01S"))
                val L01S = cursor.getInt(cursor.getColumnIndex("L01S"))
                val G5000M: Long? = cursor.getLong(cursor.getColumnIndex("G5000M"))
                val G1000M: Long? = cursor.getLong(cursor.getColumnIndex("G1000M"))
                val G500M: Long? = cursor.getLong(cursor.getColumnIndex("G500M"))
                val G100M: Long? = cursor.getLong(cursor.getColumnIndex("G100M"))
                val ALLSIZESDJ: String? = cursor.getString(cursor.getColumnIndex("ALLSIZESDJ"))
                val M100SDJ: String? = cursor.getString(cursor.getColumnIndex("M100SDJ"))
                val M50SDJ: String? = cursor.getString(cursor.getColumnIndex("M50SDJ"))
                val M30SDJ: String? = cursor.getString(cursor.getColumnIndex("M30SDJ"))
                val M10SDJ: String? = cursor.getString(cursor.getColumnIndex("M10SDJ"))
                val M5SDJ: String? = cursor.getString(cursor.getColumnIndex("M5SDJ"))
                val M1SDJ: String? = cursor.getString(cursor.getColumnIndex("M1SDJ"))
                val M05SDJ: String? = cursor.getString(cursor.getColumnIndex("M05SDJ"))
                val M01SDJ: String? = cursor.getString(cursor.getColumnIndex("M01SDJ"))
                val DATE: String? = cursor.getString(cursor.getColumnIndex("DATE"))

                dealDetailAmountSizeBean.m100Size = m100s
                dealDetailAmountSizeBean.m50Size = m50s
                dealDetailAmountSizeBean.m30Size = m30s
                dealDetailAmountSizeBean.m10Size = m10s
                dealDetailAmountSizeBean.m5Size = m5s
                dealDetailAmountSizeBean.m1Size = m1s
                dealDetailAmountSizeBean.m05Size = m05s
                dealDetailAmountSizeBean.m01Size = m01s
                dealDetailAmountSizeBean.l01Size = L01S

                dealDetailAmountSizeBean.gt5000Mamount = G5000M!!
                dealDetailAmountSizeBean.gt1000Mamount = G1000M!!
                dealDetailAmountSizeBean.gt500Mamount = G500M!!
                dealDetailAmountSizeBean.gt100Mamount = G100M!!

                dealDetailAmountSizeBean.allsizeSDJ = ALLSIZESDJ
                dealDetailAmountSizeBean.m100SDJ = M100SDJ
                dealDetailAmountSizeBean.m50SDJ = M50SDJ
                dealDetailAmountSizeBean.m30SDJ = M30SDJ
                dealDetailAmountSizeBean.m10SDJ = M10SDJ
                dealDetailAmountSizeBean.m5SDJ = M5SDJ
                dealDetailAmountSizeBean.m1SDJ = M1SDJ
                dealDetailAmountSizeBean.m05SDJ = M05SDJ
                dealDetailAmountSizeBean.m01SDJ = M01SDJ
                dealDetailAmountSizeBean.date = DATE

                sumDDBean.code = code
                sumDDBean.name = name
                sumDDBean.allsize = allsize
                sumDDBean.maxPer = maxper
                sumDDBean.lowPer = LOWPER
                sumDDBean.mpd = MPD
                sumDDBean.percent = percent
                sumDDBean.sizeBean = dealDetailAmountSizeBean

            }
            cursor.close()
        }
        return sumDDBean
    }

    fun createHHqTable(dbName: String) {
//        "(CODE,ALLSIZE,PERCENT,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,M100J,M50J,M30J,M10J,M5J,M1J,M05J,M01J)"
        if (!tabbleIsExist(dbName)) {
            val sqlStr =
                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT, JSON TEXT, MINEINFO TEXT);"
            LogUtil.d("createHHqTable:$sqlStr")
            db.execSQL(sqlStr)
        }
    }

    fun createDealDetailTable(dbName: String) {
//        "(CODE,ALLSIZE,PERCENT,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,M100J,M50J,M30J,M10J,M5J,M1J,M05J,M01J)"
        if (!tabbleIsExist(dbName)) {
            val sqlStr =
                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT, NAME TEXT, ALLSIZE INTEGER, PERCENT INTEGER" +
                        ", M100S INTEGER, M50S INTEGER, M30S INTEGER, M10S INTEGER, M5S INTEGER, M1S INTEGER, M05S INTEGER, M01S INTEGER" +
                        ", M100J TEXT, M50J TEXT, M30J TEXT, M10J TEXT, M5J TEXT, M1J TEXT, M05J TEXT, M01J TEXT);"
            db.execSQL(sqlStr)
        }
    }

    fun createSDD(dbName: String) {
//        "(CODE,ALLSIZE,PERCENT,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,M100J,M50J,M30J,M10J,M5J,M1J,M05J,M01J)"
        if (!tabbleIsExist(dbName)) {
            val sqlStr =
                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT, NAME TEXT, ALLSIZE INTEGER, MAXPER INTEGER, LOWPER INTEGER, PERCENT INTEGER" +
                        ", M100S INTEGER, M50S INTEGER, M30S INTEGER, M10S INTEGER, M5S INTEGER, M1S INTEGER, M05S INTEGER, M01S INTEGER, L01S INTEGER, G5000M INTEGER, G1000M INTEGER, G500M INTEGER, G100M INTEGER" +
                        ", ALLSIZESDJ TEXT, M100SDJ TEXT, M50SDJ TEXT, M30SDJ TEXT, M10SDJ TEXT, M5SDJ TEXT, M1SDJ TEXT, M05SDJ TEXT, M01SDJ TEXT, DATE TEXT,MPD TEXT,LPD TEXT);"
            db.execSQL(sqlStr)
        }
    }

    fun createCodeHDD(dbName: String) {
        if (!tabbleIsExist(dbName)) {
            val sqlStr =
                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DATE TEXT, OP INTEGER, CP INTEGER,  PP INTEGER, P INTEGER, AUP INTEGER, TR INTEGER" +
                        ",K_J TEXT,Shape_J TEXT,K_TR_J TEXT,GAP_J TEXT, P_AUTR_J TEXT,P_DA_J TEXT,P_PP_J TEXT,P_MA_J TEXT);"
            db.execSQL(sqlStr)
        }
    }

    fun insertCodeHDD(
        dbName: String,
        codeHDDBean: CodeHDDBean,
        code: String,
        date: String
    ) {
        switchDBName(code.toCodeHDD(date, FormatterEnum.YYYYMMDD))
        createCodeHDD(dbName)
        if (!queryCodeHDDIsExsitByDate(dbName, codeHDDBean.date)) {
            val insertSqlStr = codeHDDBean.toInsertDBValues(dbName)
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        }
    }

    fun updateCHDDGapJson(
        dbName: String,
        code: String,
        codeHDDBean: CodeHDDBean
    ) {
        switchDBName(code.toCodeHDD(codeHDDBean.date, FormatterEnum.YYYYMMDD))
        createCodeHDD(dbName)
        val sql =
            "UPDATE $dbName SET GAP_J = '${if (null == codeHDDBean.gaP_J) "" else GsonHelper.getInstance()
                .toJson(codeHDDBean.gaP_J)}' WHERE  DATE = ${codeHDDBean.date}"
        LogUtil.d("$code updateCHDDGapJson----\n$sql")
        db.execSQL(sql)
    }


    fun queryCodeHDDIsExsitByDate(dbName: String, DATE: String): Boolean {
        var isexsit = false
        LogUtil.d("DATE:$DATE")
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE DATE =?", arrayOf(DATE))
            isexsit = cursor.count > 0
            LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
            cursor.close()
        }
        return isexsit
    }


    fun queryDateInCodeDDByDbName(dbName: String): String {
        var DATE = ""
        if (tabbleIsExist(dbName)) {
            var cursor = db.rawQuery("SELECT * FROM $dbName", null)
            if (null != cursor && cursor.moveToLast()) {
                DATE = cursor.getString(cursor.getColumnIndex("DATE"))
            }
            cursor.close()
        }
        return DATE
    }

    fun queryDateInSDDDByDbName(dbName: String, code: String): String {
        switchDBName(Datas.dataNamesDefault)
        var DATE = ""
        if (tabbleIsExist(dbName)) {
            var cursor = db.rawQuery("SELECT * FROM $dbName WHERE C = ?", arrayOf(code))
            if (null != cursor && cursor.moveToLast()) {
                DATE = cursor.getString(cursor.getColumnIndex("DATE"))
            }
            cursor.close()
        }
        return DATE
    }


    /**
     * 判断某张表是否存在
     *
     * @return
     */
    fun tabbleIsExist(tableName: String?): Boolean {
        var result = false
        if (tableName == null) {
            return false
        }
        var cursor: Cursor? = null
        try {
            val sql =
                "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim { it <= ' ' } + "' "

            cursor = db.rawQuery(sql, null)
            if (cursor.moveToNext()) {
                val count = cursor.getInt(0)
                if (count > 0) {
                    result = true
                }
            }
        } catch (e: Exception) {
            // TODO: handle exception
        } finally {
            cursor?.close()
        }
        return result
    }


    fun foreachDBTable() {
        var result = false
        var cursor: Cursor? = null
        try {
            val sql =
                "select name from Sqlite_master  where type ='table'"

            cursor = db.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                var name = cursor.getString(cursor.getColumnIndex("name"))
                LogUtil.d("foreachDBTable name:$name")
                val sqlstr =
                    "select * from $name"
                val mCursor = db.rawQuery(sqlstr, null)
                LogUtil.d("foreachDBTable mCursor:${mCursor.count}")
                if (name.contains("DD_") && mCursor.count == 0) {
                    dropTable(name)
                }
                if (name.contains("DD_")) {
                    val date = name.replace("DD_", "")
                    val ts = DateUtils.parse(date, FormatterEnum.YYYYMMDD)
                    if (ts > System.currentTimeMillis()) {
                        dropTable(name)
                    }
                }

            }
        } catch (e: Exception) {
            // TODO: handle exception
        } finally {
            cursor?.close()
        }
    }

    fun updateDDPercentByCode(
        dbname: String,
        code: String,
        percent: String
    ) {
        LogUtil.d("updateDDPercentByCode--->$dbname")
        if (tabbleIsExist(dbname)) {
            var sql = "UPDATE $dbname SET PERCENT=${percent} WHERE CODE=${code.toInt()}"
            db.execSQL(sql)
        }
    }


    fun createSHDD(dbName: String) {
//        "(CODE,ALLSIZE,PERCENT,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,M100J,M50J,M30J,M10J,M5J,M1J,M05J,M01J)"
        if (!tabbleIsExist(dbName)) {
            val sqlStr =
                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, C TEXT, N TEXT, AUP INTEGER, MPP INTEGER, LPP INTEGER, AUTR INTEGER" +
                        ",AD INTEGER, AD100 INTEGER, AD50 INTEGER, AD30 INTEGER, AD10 INTEGER, AD5 INTEGER, AD1 INTEGER" +
                        ",PP INTEGER, PP100 INTEGER, PP50 INTEGER, PP30 INTEGER, PP10 INTEGER, PP5 INTEGER, PP1 INTEGER, MP INTEGER, LP INTEGER,MPD TEXT,LPD TEXT, DATE TEXT, AVJ TEXT);"
            db.execSQL(sqlStr)
        }
    }

    fun insertOrUpdateSHDDTable(
        dbName: String,
        curdate: String,
        shddBean: SHDDBean
    ) {

        switchDBName(Datas.dataNamesDefault)
        createSHDD(dbName)
        if (!querySHDDIsExsitByCode(dbName, shddBean.c)) {
            val insertSqlStr = "INSERT INTO $dbName" +
                    "(C,N,AUP,MPP,LPP,AUTR,AD,AD100,AD50,AD30,AD10,AD5,AD1,PP,PP100,PP50,PP30,PP10,PP5,PP1,MP,LP,MPD,LPD,DATE,AVJ)" +
                    " VALUES${shddBean.toInsert()}"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        } else {
            val ts = DateUtils.parse(curdate, FormatterEnum.YYYYMMDD)
            val ts2 = DateUtils.parse(shddBean.date, FormatterEnum.YYYYMMDD)
            if (ts > ts2) {
                shddBean.date = curdate
                val sql =
                    "UPDATE $dbName SET ${shddBean.toUpdateSqlSumValues()}  WHERE C=${shddBean.c}"
                LogUtil.d("updateSqlStr:$sql")
                db.execSQL(sql)
            }
        }
    }


    fun querySHDDByCode(dbName: String, code: String): SHDDBean? {
        switchDBName(Datas.dataNamesDefault)
        var shddBean: SHDDBean? = null
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE C =?", arrayOf(code.toInt().toString()))

            LogUtil.d("cursor:${cursor.count}")
            if (null != cursor && cursor.moveToFirst()) {
                shddBean = SHDDBean()
                val code = cursor.getString(cursor.getColumnIndex("C"))
                val name = cursor.getString(cursor.getColumnIndex("N"))
                val AUP = cursor.getFloat(cursor.getColumnIndex("AUP"))
                val MPER = cursor.getFloat(cursor.getColumnIndex("MPP"))
                val LPER = cursor.getFloat(cursor.getColumnIndex("LPP"))
//                val AV = cursor.getFloat(cursor.getColumnIndex("AV"))
//                val AV100 = cursor.getFloat(cursor.getColumnIndex("AV100"))
//                val AV50 = cursor.getFloat(cursor.getColumnIndex("AV50"))
//                val AV30 = cursor.getFloat(cursor.getColumnIndex("AV30"))
//                val AV10 = cursor.getFloat(cursor.getColumnIndex("AV10"))
//                val AV5 = cursor.getFloat(cursor.getColumnIndex("AV5"))
//                val AV1 = cursor.getFloat(cursor.getColumnIndex("AV1"))
                val AD = cursor.getFloat(cursor.getColumnIndex("AD"))
                val AD100 = cursor.getFloat(cursor.getColumnIndex("AD100"))
                val AD50 = cursor.getFloat(cursor.getColumnIndex("AD50"))
                val AD30 = cursor.getFloat(cursor.getColumnIndex("AD30"))
                val AD10 = cursor.getFloat(cursor.getColumnIndex("AD10"))
                val AD5 = cursor.getFloat(cursor.getColumnIndex("AD5"))
                val AD1 = cursor.getFloat(cursor.getColumnIndex("AD1"))
                val PP100 = cursor.getFloat(cursor.getColumnIndex("PP100"))
                val PP50 = cursor.getFloat(cursor.getColumnIndex("PP50"))
                val PP30 = cursor.getFloat(cursor.getColumnIndex("PP30"))
                val PP10 = cursor.getFloat(cursor.getColumnIndex("PP10"))
                val PP5 = cursor.getFloat(cursor.getColumnIndex("PP5"))
                val PP1 = cursor.getFloat(cursor.getColumnIndex("PP1"))
                val MP = cursor.getFloat(cursor.getColumnIndex("MP"))
                val LP = cursor.getFloat(cursor.getColumnIndex("LP"))
                val MPD = cursor.getString(cursor.getColumnIndex("MPD"))
                val LPD = cursor.getString(cursor.getColumnIndex("LPD"))
                val autr = cursor.getFloat(cursor.getColumnIndex("AUTR"))
//                val autr = cursor.getFloat(cursor.getColumnIndex("AV"))
                val DATE = cursor.getString(cursor.getColumnIndex("DATE"))
                val AVJ = cursor.getString(cursor.getColumnIndex("AVJ"))
                //        "(C,N,AUP,MP%,LP%,AV,AV100,AV50,AV30,AV10,AV5,AV1,AD,AD100,AD50,AD30,AD10,AD5,AD1,PP100,PP50,PP30,PP10,PP5,PP1,MP,LP,MPD,LPD,DATE)"
                shddBean.n = name
                shddBean.c = code
                shddBean.aup = AUP.toFloat()
                shddBean.mper = MPER
                shddBean.lper = LPER
                shddBean.autr = autr
//                shddBean.av = AV
//                shddBean.aV100 = AV100
//                shddBean.aV50 = AV50
//                shddBean.aV30 = AV30
//                shddBean.aV10 = AV10
//                shddBean.aV5 = AV5
//                shddBean.aV1 = AV1
                shddBean.ad = AD
                shddBean.aD100 = AD100
                shddBean.aD50 = AD50
                shddBean.aD30 = AD30
                shddBean.aD10 = AD10
                shddBean.aD5 = AD5
                shddBean.aD1 = AD1
                shddBean.pP100 = PP100
                shddBean.pP50 = PP50
                shddBean.pP30 = PP30
                shddBean.pP10 = PP10
                shddBean.pP5 = PP5
                shddBean.pP1 = PP1
                shddBean.mp = MP
                shddBean.lp = LP
                shddBean.mpd = MPD
                shddBean.lpd = LPD
                shddBean.date = DATE
                shddBean.avj = AVJ
                if (!AVJ.isEmpty()) {
                    val avJsonBean = GsonHelper.parse(AVJ, AvJsonBean::class.java)
                    shddBean.av = avJsonBean.av
                    shddBean.aV100 = avJsonBean.aV100
                    shddBean.aV50 = avJsonBean.aV50
                    shddBean.aV30 = avJsonBean.aV30
                    shddBean.aV10 = avJsonBean.aV10
                    shddBean.aV5 = avJsonBean.aV5
                    shddBean.aV1 = avJsonBean.aV1
                }

            }
            cursor.close()
        }
        return shddBean
    }


    fun querySHDDIsExsitByCode(dbName: String, code: String): Boolean {
        var isexsit = false
        LogUtil.d("CODE:$code")
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE C =?", arrayOf(code))
            isexsit = cursor.count > 0
            LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
            cursor.close()
        }
        return isexsit
    }

    fun queryDataIsExsitByCode(dbName: String, code: String): Boolean {
        var isexsit = false
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code))
            isexsit = cursor.count > 0
            LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
            cursor.close()
        }
        return isexsit
    }

    fun queryDataIsExsitByCodeAndDate(dbName: String, code: String, date: String): Boolean {
        var isexsit = false
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =? AND DATE =?", arrayOf(code, date))
            isexsit = cursor.count > 0
            LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
            cursor.close()
        }
        return isexsit
    }

    fun queryFirstOPByCodeHDD(tbName: String): Float {
        var op = 0.toFloat()
        if (tabbleIsExist(tbName)) {
            val cursor = db.rawQuery("SELECT * FROM $tbName order by _ID asc", null)
            if (null != cursor && cursor.moveToFirst()) {
                op = cursor.getFloat(cursor.getColumnIndex("OP"))
                var Date = cursor.getFloat(cursor.getColumnIndex("DATE"))
                while (op == 0.toFloat()) {
                    cursor.moveToNext()
                    op = cursor.getFloat(cursor.getColumnIndex("OP"))
                    Date = cursor.getFloat(cursor.getColumnIndex("DATE"))
                }
                LogUtil.d("queryFirstOPByCodeHDD Date:$Date")
            }
            cursor.close()
        }
        return op
    }

    fun insertOrUpdateSumProgressRecordTable(
        sumProgessRecordBean: SumProgessRecordBean
    ) {
        switchDBName(Datas.dataNamesDefault)
        createSumProgressTable(Datas.SumProressTB)
        if (!queryDataIsExsitByCode(Datas.SumProressTB, sumProgessRecordBean.code.toString())) {
            val insertSqlStr = "INSERT INTO ${Datas.SumProressTB}" +
                    "(CODE,JSON)" +
                    " VALUES(${sumProgessRecordBean.code},'${GsonHelper.toJson(sumProgessRecordBean)}');"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        } else {
            val sql =
                "UPDATE ${Datas.SumProressTB} SET JSON = '${GsonHelper.toJson(sumProgessRecordBean)}'  WHERE CODE=${sumProgessRecordBean.code}"
            LogUtil.d("updateSqlStr:$sql")
            db.execSQL(sql)
        }
    }

    private fun createSumProgressTable(dbName: String) {
        if (!tabbleIsExist(dbName)) {
            val sqlStr =
                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER, JSON TEXT);"
            db.execSQL(sqlStr)
        }
    }


    fun querySumProgressRecordByCode(code: String): SumProgessRecordBean? {
        switchDBName(Datas.dataNamesDefault)
        var sumProgessRecordBean: SumProgessRecordBean? = null
        if (tabbleIsExist(Datas.SumProressTB)) {
            var cursor =
                db.rawQuery(
                    "SELECT * FROM ${Datas.SumProressTB} WHERE CODE =?",
                    arrayOf(code.toInt().toString())
                )
            LogUtil.d("cursor:${cursor.count}")
            if (null != cursor && cursor.moveToFirst()) {
                sumProgessRecordBean = SumProgessRecordBean()
                val JSON = cursor.getString(cursor.getColumnIndex("JSON"))
                sumProgessRecordBean =
                    GsonHelper.getInstance().fromJson(JSON, SumProgessRecordBean::class.java)

            }
            cursor.close()
        }
        return sumProgessRecordBean
    }


    private fun createFilterTable(dbName: String) {
        if (!tabbleIsExist(dbName)) {
            val sqlStr =
                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT, FILTERTYPECOUNT INTEGER, FILTERJS TEXT);"
            db.execSQL(sqlStr)
        }
    }


    fun queryCHDDByTableName(tableName: String, dbName: String): ArrayList<CodeHDDBean>? {
        switchDBName(dbName)
        var list: ArrayList<CodeHDDBean>? = null
        try {

            if (tabbleIsExist(tableName)) {
                var cursor =
                    db.rawQuery("SELECT * FROM $tableName", null)
                LogUtil.d("cursor:${cursor.count}")
                if (null != cursor && cursor.moveToFirst()) {
                    list = ArrayList<CodeHDDBean>()
                    while (!cursor.isAfterLast) {
                        val codeHDDBean = CodeHDDBean()
                        val NAME = cursor.getString(cursor.getColumnIndex("NAME"))
                        val DATE = cursor.getString(cursor.getColumnIndex("DATE"))
                        val OP = cursor.getFloat(cursor.getColumnIndex("OP"))
                        val CP = cursor.getFloat(cursor.getColumnIndex("CP"))
                        val PP = cursor.getFloat(cursor.getColumnIndex("PP"))
                        val P = cursor.getFloat(cursor.getColumnIndex("P"))
                        val AUP = cursor.getFloat(cursor.getColumnIndex("AUP"))
                        val TR = cursor.getString(cursor.getColumnIndex("TR"))
                        val p_autr_j = cursor.getString(cursor.getColumnIndex("P_AUTR_J"))
                        val P_DA_J = cursor.getString(cursor.getColumnIndex("P_DA_J"))
                        val P_PP_J = cursor.getString(cursor.getColumnIndex("P_PP_J"))
                        val P_MA_J = cursor.getString(cursor.getColumnIndex("P_MA_J"))
//                    val SpePP_J = cursor.getString(cursor.getColumnIndex("SpePP_J"))
//                    val DA_J = cursor.getString(cursor.getColumnIndex("DA_J"))
                        val K_J = cursor.getString(cursor.getColumnIndex("K_J"))
                        val Shape_J = cursor.getString(cursor.getColumnIndex("Shape_J"))
                        val K_TR_J = cursor.getString(cursor.getColumnIndex("K_TR_J"))
                        val GAP_J = cursor.getString(cursor.getColumnIndex("GAP_J"))
//                    val MS_J = cursor.getString(cursor.getColumnIndex("MS_J"))

                        codeHDDBean.name = NAME
                        codeHDDBean.date = DATE
                        codeHDDBean.op = OP
                        codeHDDBean.cp = CP
                        codeHDDBean.pp = PP
                        codeHDDBean.p = P
                        codeHDDBean.aup = AUP
                        codeHDDBean.tr = TR

                        if (!GAP_J.isNullOrEmpty()) {
                            codeHDDBean.gaP_J = GsonHelper.parse(GAP_J, GapJsonBean::class.java)
                        }
                        if (!K_TR_J.isNullOrEmpty()) {
                            codeHDDBean.k_TR_J = GsonHelper.parse(K_TR_J, K_TR_Json::class.java)
                        }
                        if (!Shape_J.isNullOrEmpty()) {
                            codeHDDBean.shape_J =
                                GsonHelper.parse(Shape_J, ShapeJsonBean::class.java)
                        }
                        if (!K_J.isNullOrEmpty()) {
                            codeHDDBean.k_J = GsonHelper.parse(K_J, KJsonBean::class.java)
                        }

                        if (!p_autr_j.isNullOrEmpty()) {
                            codeHDDBean.p_autr_j =
                                GsonHelper.parse(p_autr_j, CodeHDDBean.P_AUTR_J::class.java)
                        }
                        if (!P_DA_J.isNullOrEmpty()) {
                            codeHDDBean.p_DA_J =
                                GsonHelper.parse(P_DA_J, CodeHDDBean.P_DA_J::class.java)
                        }
                        if (!P_PP_J.isNullOrEmpty()) {
                            codeHDDBean.p_PP_J =
                                GsonHelper.parse(P_PP_J, CodeHDDBean.P_PP_J::class.java)
                        }
                        if (!P_MA_J.isNullOrEmpty()) {
                            codeHDDBean.p_MA_J =
                                GsonHelper.parse(P_MA_J, CodeHDDBean.P_MA_J::class.java)
                        }
//                    if (!SpePP_J.isNullOrEmpty()) {
//                        codeHDDBean.spePP_J = GsonHelper.parse(SpePP_J, CodeHDDBean.SpePP_J::class.java)
//                    }
//                    if (!DA_J.isNullOrEmpty()) {
//                        codeHDDBean.dA_J = GsonHelper.parse(DA_J, CodeHDDBean.DA_J::class.java)
//                    }
//                    if (!MS_J.isNullOrEmpty()) {
//                        codeHDDBean.mS_J = GsonHelper.parse(MS_J, CodeHDDBean.MS_J::class.java)
//                    }
                        list.add(codeHDDBean)
                        cursor.moveToNext()
                    }

                }
                cursor.close()
            }
        } catch (e: java.lang.Exception) {
            queryCHDDByTableName(tableName, dbName)
        }
        return list
    }


    fun insertOrUpdateFilterCodeHddTable(
        tbName: String,
        filterCodeHDDBean: FilterCodeHDDBean,
        date: String
    ) {
        switchDBName(
            Datas.MAPPFilterDB + DateUtils.changeFormatter(
                DateUtils.parse(
                    date,
                    FormatterEnum.YYYYMMDD
                ), FormatterEnum.YYMM
            )
        )
        createFilterCodeHddTable(tbName)
        if (!queryDataIsExsitByCode(tbName, filterCodeHDDBean.code.toString())) {
            val insertSqlStr = "INSERT INTO $tbName" +
                    "(CODE, KJ_CTC_TYPE, KJ_CTC_J)" +
                    " VALUES${filterCodeHDDBean.toInsert()}"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        } else {
            val sql =
                "UPDATE $tbName SET ${filterCodeHDDBean.toUpdateSqlSumValues()}  WHERE CODE=${filterCodeHDDBean.code}"
            LogUtil.d("updateSqlStr:$sql")
            db.execSQL(sql)
        }
    }


    private fun createFilterCodeHddTable(dbName: String) {
        if (!tabbleIsExist(dbName)) {
            val sqlStr =
                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER, KJ_CTC_TYPE INTEGER,KJ_CTC_J TEXT);"
            db.execSQL(sqlStr)
        }
    }

    fun queryFilterCodeHddBeanByCode(
        dbName: String,
        code: String,
        date: String
    ): FilterCodeHDDBean? {
        switchDBName(
            Datas.MAPPFilterDB + DateUtils.changeFormatter(
                DateUtils.parse(
                    date,
                    FormatterEnum.YYYYMMDD
                ), FormatterEnum.YYMM
            )
        )
        LogUtil.d("queryMAPPFilterBeanByCode:${DaoManager.DB_NAME}")
        var filterCodeHDDBean: FilterCodeHDDBean? = null
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code.toInt().toString()))
            LogUtil.d("cursor:${cursor.count}")
            if (null != cursor && cursor.moveToFirst()) {
                filterCodeHDDBean =
                    FilterCodeHDDBean()
                val CODE = cursor.getInt(cursor.getColumnIndex("CODE"))
                val KJ_CTC_TYPE = cursor.getInt(cursor.getColumnIndex("KJ_CTC_TYPE"))
                val KJ_CTC_J = cursor.getString(cursor.getColumnIndex("KJ_CTC_J"))

                filterCodeHDDBean.code = CODE
                filterCodeHDDBean.kJ_CTC_TYPE = KJ_CTC_TYPE
                filterCodeHDDBean.kJ_CTC_J = KJ_CTC_J
            }
            cursor.close()
        }
        return filterCodeHDDBean
    }

    fun insertReverseKJTable(
        tbName: String,
        reverseBean: BaseReverseImp,
        date: String
    ) {
        switchDBName(
            Datas.REVERSE_KJ_DB
        )
        LogUtil.d("insertReverseKJTable!!")
        createReverseKJTable(tbName, reverseBean)
        if (!queryDataIsExsitByCodeAndDate(tbName, reverseBean.code.toString(), date)) {
            var insertSqlStr = ""
            insertSqlStr = reverseBean.insertTB(tbName)
            if (reverseBean is ReverseKJsonBean) {
                val insertDerbySqlStr = reverseBean.insertDerbyTB(Datas.Derby + tbName)
                try {
                    db.execSQL(insertDerbySqlStr)
                } catch (e: java.lang.Exception) {
                    LogUtil.d("e-->${e.toString()}")

                }
            }
            LogUtil.d("insertSqlStr:$insertSqlStr")
            try {
                db.execSQL(insertSqlStr)
            } catch (e: java.lang.Exception) {
                LogUtil.d("e-->${e.toString()}")
            }
        }
    }


    private fun createReverseKJTable(
        dbName: String,
        reverseBean: BaseReverseImp
    ) {
        if (!tabbleIsExist(dbName)) {
            var sqlStr = ""
            sqlStr = reverseBean.createTB(dbName)
            if (reverseBean is ReverseKJsonBean) {
                val sqlStr2 = reverseBean.createDerbyTB(Datas.Derby + dbName)
                db.execSQL(sqlStr2)
            }
            db.execSQL(sqlStr)
        }
    }


    fun insertGapRecordJTable(
        tbName: String,
        gapJsonBean: GapRecordBean,
        date: String
    ) {
        switchDBName(
            Datas.GAP_RECORD_DB + DateUtils.changeFromDefaultFormatter(
                date,
                FormatterEnum.YYMM
            )
        )
        LogUtil.d("insertGapRecordJTable!!")
        createGapRecordKJTable(tbName, gapJsonBean)
        try {
            if (!queryDataIsExsitByCodeAndBDAndDate(
                    tbName,
                    gapJsonBean.code.toString(),
                    gapJsonBean.b_D.toString()
                )
            ) {
                val insertSqlStr = gapJsonBean.insertTB(tbName)
                LogUtil.d("insertSqlStr:$insertSqlStr")

                db.execSQL(insertSqlStr)
            } else {
                val updateSql = gapJsonBean.updateGapInfo(tbName)
                LogUtil.d("updateSql:$updateSql")
                db.execSQL(updateSql)
            }
        } catch (e: java.lang.Exception) {
            insertGapRecordJTable(
                tbName,
                gapJsonBean,
                date
            )
        }

    }

    fun queryDataIsExsitByCodeAndBDAndDate(dbName: String, code: String, bd: String): Boolean {
        var isexsit = false
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =? AND B_D =?", arrayOf(code, bd))
            isexsit = cursor.count > 0
            LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
            cursor.close()
        }
        return isexsit
    }

    private fun createGapRecordKJTable(
        dbName: String,
        gapJsonBean: GapRecordBean
    ) {
        if (!tabbleIsExist(dbName)) {
            var sqlStr = gapJsonBean.createTB(dbName)
            db.execSQL(sqlStr)
        }
    }


    fun insertRevCode(
        code: String,
        reverseBean: BaseReverseImp,
        date: String
    ) {
        switchDBName(Datas.REV_CODE_DB)
        LogUtil.d("insertReverseKJTable!!")
        var tbName = Datas.REV_CODE + code
        if (reverseBean is TR_K_SLL_Bean) {
            tbName = Datas.REV_CODE_TR + code
        }
        createRevCodeTable(tbName, reverseBean, code)
        if (!queryDataIsExsitInRevCodeTB(tbName, date, kotlin.run {
                if (reverseBean is ReverseKJsonBean) {
                    reverseBean.d_T
                } else if (reverseBean is TR_K_SLL_Bean) {
                    reverseBean.d_T
                } else {
                    ""
                }
            })) {
            var insertSqlStr = ""
            if (reverseBean is ReverseKJsonBean) {
                insertSqlStr = reverseBean.insertRevCodeTB(tbName)
                val insertDerbySqlStr =
                    reverseBean.insertRevCodeDerbyTB(Datas.REV_CODE_DERBY + code)
                db.execSQL(insertDerbySqlStr)
            } else if (reverseBean is TR_K_SLL_Bean) {
                insertSqlStr = reverseBean.insertRevCodeTB(tbName)
            }
            db.execSQL(insertSqlStr)
        }
    }

    fun queryDataIsExsitInRevCodeTB(dbName: String, date: String, DT: String): Boolean {
        var isexsit = false
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE D_T =? AND DATE =?", arrayOf(DT, date))
            isexsit = cursor.count > 0
            LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
            cursor.close()
        }
        return isexsit
    }

    private fun createRevCodeTable(
        dbName: String,
        reverseBean: BaseReverseImp,
        code: String
    ) {
        if (!tabbleIsExist(dbName)) {
            var sqlStr = ""
            if (reverseBean is ReverseKJsonBean) {
                sqlStr = reverseBean.createRevCodeTB(dbName)
                val sqlDerbyStr = reverseBean.createRevCodeDerbyTB(Datas.REV_CODE_DERBY + code)
                db.execSQL(sqlDerbyStr)
            } else if (reverseBean is TR_K_SLL_Bean) {
                sqlStr = reverseBean.createRevCodeTB(dbName)
            }
            db.execSQL(sqlStr)
        }
    }


    fun queryRevLimit(
        tbName: String,
        smallerMap: HashMap<String, String>,
        biggerMap: HashMap<String, String>,
        reverseType: Int
    ): ArrayList<BaseReverseImp>? {
        val pair = tbName.getQuerySql(smallerMap, biggerMap)
        var list: ArrayList<BaseReverseImp>? = null
        LogUtil.d("queryRevLimit:${pair.first}")
        if (tabbleIsExist(tbName)) {
            val cursor =
                db.rawQuery(pair.first, pair.second)
            if (null != cursor && cursor.moveToFirst()) {
                list = ArrayList()
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    when (reverseType) {
                        1 -> {
                            val reverseBean = getRevKJBeanByCursor(cursor)
                            list.add(reverseBean)
                        }
                        2 -> {
                            val reverseBean = getDerbyRevKJBeanByCursor(cursor)
                            list.add(reverseBean)
                        }
                    }
                    cursor.moveToNext()
                }
            }
            LogUtil.d("queryRevLimit cursor.count:${cursor.count}")
            cursor.close()
        }
        return list
    }

    private fun getDerbyRevKJBeanByCursor(cursor: Cursor): ReverseKJsonBean {
        val reverseBean = ReverseKJsonBean()
        reverseBean.code = cursor.getInt(cursor.getColumnIndex("CODE"))
        reverseBean.n = cursor.getString(cursor.getColumnIndex("N"))
        reverseBean.d_D = cursor.getInt(cursor.getColumnIndex("D_D"))
        reverseBean.date = cursor.getInt(cursor.getColumnIndex("DATE"))
        reverseBean.ao = cursor.getString(cursor.getColumnIndex("AO"))
        reverseBean.curP = cursor.getFloat(cursor.getColumnIndex("CurP"))
        reverseBean.ma = cursor.getInt(cursor.getColumnIndex("MA"))
        reverseBean.oM_OC = cursor.getFloat(cursor.getColumnIndex("OM_OC"))
        reverseBean.oM_OP = cursor.getFloat(cursor.getColumnIndex("OM_OP"))
        reverseBean.oM_OL = cursor.getFloat(cursor.getColumnIndex("OM_OL"))
        reverseBean.oC_OP = cursor.getFloat(cursor.getColumnIndex("OC_OP"))
        reverseBean.oC_OL = cursor.getFloat(cursor.getColumnIndex("OC_OL"))
        reverseBean.oP_OL = cursor.getFloat(cursor.getColumnIndex("OP_OL"))
        reverseBean.m_C = cursor.getFloat(cursor.getColumnIndex("M_C"))
        reverseBean.m_P = cursor.getFloat(cursor.getColumnIndex("M_P"))
        reverseBean.m_L = cursor.getFloat(cursor.getColumnIndex("M_L"))
        reverseBean.c_P = cursor.getFloat(cursor.getColumnIndex("C_P"))
        reverseBean.c_L = cursor.getFloat(cursor.getColumnIndex("C_L"))
        reverseBean.p_L = cursor.getFloat(cursor.getColumnIndex("P_L"))
        return reverseBean
    }

    private fun getRevKJBeanByCursor(cursor: Cursor): ReverseKJsonBean {
        val reverseBean = ReverseKJsonBean()
        reverseBean.code = cursor.getInt(cursor.getColumnIndex("CODE"))
        reverseBean.n = cursor.getString(cursor.getColumnIndex("N"))
        reverseBean.d_D = cursor.getInt(cursor.getColumnIndex("D_D"))
        reverseBean.date = cursor.getInt(cursor.getColumnIndex("DATE"))
        reverseBean.ao = cursor.getString(cursor.getColumnIndex("AO"))
        reverseBean.curP = cursor.getFloat(cursor.getColumnIndex("CurP"))
        reverseBean.ma = cursor.getInt(cursor.getColumnIndex("MA"))
        reverseBean.oM_M = cursor.getFloat(cursor.getColumnIndex("OM_M"))
        reverseBean.oM_C = cursor.getFloat(cursor.getColumnIndex("OM_C"))
        reverseBean.oM_P = cursor.getFloat(cursor.getColumnIndex("OM_P"))
        reverseBean.oM_L = cursor.getFloat(cursor.getColumnIndex("OM_L"))
        reverseBean.oC_M = cursor.getFloat(cursor.getColumnIndex("OC_M"))
        reverseBean.oC_C = cursor.getFloat(cursor.getColumnIndex("OC_C"))
        reverseBean.oC_P = cursor.getFloat(cursor.getColumnIndex("OC_P"))
        reverseBean.oC_L = cursor.getFloat(cursor.getColumnIndex("OC_L"))
        reverseBean.oO_M = cursor.getFloat(cursor.getColumnIndex("OO_M"))
        reverseBean.oO_C = cursor.getFloat(cursor.getColumnIndex("OO_C"))
        reverseBean.oO_P = cursor.getFloat(cursor.getColumnIndex("OO_P"))
        reverseBean.oO_L = cursor.getFloat(cursor.getColumnIndex("OO_L"))
        reverseBean.oL_M = cursor.getFloat(cursor.getColumnIndex("OL_M"))
        reverseBean.oL_C = cursor.getFloat(cursor.getColumnIndex("OL_C"))
        reverseBean.oL_P = cursor.getFloat(cursor.getColumnIndex("OL_P"))
        reverseBean.oL_L = cursor.getFloat(cursor.getColumnIndex("OL_L"))
        reverseBean.gs = cursor.getInt(cursor.getColumnIndex("GS"))
        return reverseBean
    }

    //    " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER,N TEXT,D_D TEXT,DATE TEXT," +
//    " INTEGER, INTEGER, INTEGER, INTEGER," +
//    " INTEGER, INTEGER, INTEGER, INTEGER," +
//    " INTEGER, INTEGER, INTEGER, INTEGER);"
    private fun getTRKSLLBeanByCursor(cursor: Cursor): TR_K_SLL_Bean {
        val reverseBean = TR_K_SLL_Bean()
        reverseBean.setCODE(cursor.getInt(cursor.getColumnIndex("CODE")))
        reverseBean.n = cursor.getString(cursor.getColumnIndex("N"))
        reverseBean.d_D = cursor.getInt(cursor.getColumnIndex("D_D"))
        reverseBean.date = cursor.getInt(cursor.getColumnIndex("DATE"))
        reverseBean.s_A_TR = cursor.getFloat(cursor.getColumnIndex("S_A_TR"))
        reverseBean.s_R_TR = cursor.getFloat(cursor.getColumnIndex("S_R_TR"))
        reverseBean.s_B_TR = cursor.getFloat(cursor.getColumnIndex("S_B_TR"))
        reverseBean.s_C_TR = cursor.getFloat(cursor.getColumnIndex("S_C_TR"))
        reverseBean.k_A_TR = cursor.getFloat(cursor.getColumnIndex("K_A_TR"))
        reverseBean.k_R_TR = cursor.getFloat(cursor.getColumnIndex("K_R_TR"))
        reverseBean.k_B_TR = cursor.getFloat(cursor.getColumnIndex("K_B_TR"))
        reverseBean.k_C_TR = cursor.getFloat(cursor.getColumnIndex("K_C_TR"))
        reverseBean.k_SL_A_TR = cursor.getFloat(cursor.getColumnIndex("K_SL_A_TR"))
        reverseBean.k_SL_R_TR = cursor.getFloat(cursor.getColumnIndex("K_SL_R_TR"))
        reverseBean.k_SL_B_TR = cursor.getFloat(cursor.getColumnIndex("K_SL_B_TR"))
        reverseBean.k_SL_C_TR = cursor.getFloat(cursor.getColumnIndex("K_SL_C_TR"))
        return reverseBean
    }

    fun selectMaxMinValueByTbAndColumn(
        tbName: String,
        column: String,
        dbName: String
    ): Pair<String, String> {
        switchDBName(dbName)
        var minValue = ""
        var maxValue = ""
        var cursor =
            db.rawQuery(
                " SELECT * FROM $tbName WHERE ($column IN (SELECT MIN($column) FROM $tbName ${Datas.debugSelectMaxMinStr}))",
                null
            )
        if (null != cursor && cursor.moveToFirst()) {
            minValue = cursor.getString(cursor.getColumnIndex(column))
        }
        cursor.close()
        cursor =
            db.rawQuery(
                " SELECT * FROM $tbName WHERE ($column IN (SELECT MAX($column) FROM $tbName ${Datas.debugSelectMaxMinStr}))",
                null
            )
        if (null != cursor && cursor.moveToFirst()) {
            maxValue = cursor.getString(cursor.getColumnIndex(column))
        }
        cursor.close()
        LogUtil.d("minValue:$minValue maxValue:$maxValue")
        return Pair(minValue, maxValue)
    }

    fun selectMaxMinValueByTbAndColumnByCodeList(
        codeList: ArrayList<String>,
        tbName: String,
        column: String,
        dbName: String
    ): Pair<String, String> {
        switchDBName(dbName)
        val addSql = codeList.getCodeArrayAndLimitSQL(tbName,false)


        val querySql = " SELECT * FROM $tbName WHERE $addSql "
        var cursor =
            db.rawQuery(querySql, null)
        LogUtil.d("querySql-->$querySql \n $addSql cursor.count:${cursor.count}")
        val ommList = ArrayList<Float>()
        if (null != cursor && cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                LogUtil.d("column-->$column")
                ommList.add(cursor.getFloat(cursor.getColumnIndex(column)))
                cursor.moveToNext()
            }
        }
        ommList.sortFloatAsc()
//        SELECT * FROM A_RTB_50_30 WHERE (OM_M IN (SELECT MIN(OM_M) FROM A_RTB_50_30 WHERE  CODE = ?  AND CODE = ?  AND CODE = ? ))
//        SELECT * FROM A_RTB_50_30 WHERE (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?)
//        300064 ,0928 ,300064 ,0929 ,600211 ,0724 , cursor.count:0
        cursor.close()

        return Pair(ommList[0].toString(), ommList[ommList.size - 1].toString())
    }

    fun copyFilterTB2NewTB(
        newTBName: String,
        countList: java.util.ArrayList<BaseReverseImp>,
        type: Int
    ) {
        switchDBName(Datas.REV_FILTERDB + "2020")
        if (countList[0] is ReverseKJsonBean) {
            createRevFilterTable(newTBName, countList[0], type)
            countList.forEach {
                if (it is ReverseKJsonBean) {
                    if (!queryDataIsExsitByCodeAndDate(
                            newTBName,
                            it.code.toString(),
                            it.date.toString()
                        )
                    ) {
                        var insertSqlStr = ""
                        when (type) {
                            1 -> {
                                insertSqlStr = it.insertTB(newTBName)
                            }
                        }
                        db.execSQL(insertSqlStr)
                    }
                }

            }
        }
    }


    private fun createRevFilterTable(
        dbName: String,
        reverseBean: BaseReverseImp,
        type: Int
    ) {
        if (!tabbleIsExist(dbName)) {
            var sqlStr = ""
            when (type) {
                1 -> {
                    sqlStr = reverseBean.createTB(dbName)
                }
            }
            if (!TextUtils.isEmpty(sqlStr)) {
                db.execSQL(sqlStr)
            }
        }
    }

    @SuppressLint("Recycle")
    fun getAAFilterAllByTbName(
        sqlStr: String,
        selection: Array<String?>?
    ): ArrayList<BaseReverseImp>? {
        switchDBName(Datas.REV_FILTERDB + 2020)
        var list: ArrayList<BaseReverseImp>? = null
        val cursor =
            db.rawQuery(sqlStr, selection)
        cursor?.let {
            list = ArrayList()
            it.moveToFirst()
            while (!it.isAfterLast) {
                val bean = getRevKJBeanByCursor(it)
                list!!.add(bean)
                it.moveToNext()
            }
            it.close()
        }
        return list
    }

    fun getFilterAllByTbName(
        dbName: String,
        sqlStr: String,
        selection: Array<String?>?,
        isOCOO: Boolean = false
    ): ArrayList<BaseReverseImp>? {
        switchDBName(dbName)
        LogUtil.d("$sqlStr")
        var list: ArrayList<BaseReverseImp>? = null
        var cursor:Cursor? = null
        cursor =
            db.rawQuery(sqlStr, selection)
        LogUtil.d("getFilterAllByTbName!!!${cursor.count}")
        if (null != cursor) {
            list = ArrayList()
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                if (isOCOO) {
                    val bean = getRevKJOCOOBeanByCursor(cursor)
                    list.add(bean)
                    LogUtil.d("getFilterAllByTbName!!!${list.size}")
                } else {
                    val bean = getRevKJBeanByCursor(cursor)
                    list.add(bean)
                }
                cursor.moveToNext()
            }
            cursor.close()
        }
        return list
    }

    fun getFilterAllByDerbyTbName(
        dbName: String,
        sqlStr: String,
        selection: Array<String?>?
    ): ArrayList<BaseReverseImp>? {
        switchDBName(dbName)
        var list: ArrayList<BaseReverseImp>? = null
        val cursor =
            db.rawQuery(sqlStr, selection)
        cursor?.let {
            list = ArrayList()
            it.moveToFirst()
            while (!it.isAfterLast) {
                val bean = getDerbyRevKJBeanByCursor(it)
                list!!.add(bean)
                it.moveToNext()
            }
            it.close()
        }
        return list
    }

    private fun getRevKJOCOOBeanByCursor(cursor: Cursor): ReverseKJsonBean {
        val reverseBean = ReverseKJsonBean()
        reverseBean.code = cursor.getInt(cursor.getColumnIndex("CODE"))
        reverseBean.n = cursor.getString(cursor.getColumnIndex("N"))
        reverseBean.d_D = cursor.getInt(cursor.getColumnIndex("D_D"))
        reverseBean.date = cursor.getInt(cursor.getColumnIndex("DATE"))
        reverseBean.oO3 = cursor.getFloat(cursor.getColumnIndex("OO3"))
        reverseBean.oO5 = cursor.getFloat(cursor.getColumnIndex("OO5"))
        reverseBean.oO10 = cursor.getFloat(cursor.getColumnIndex("OO10"))
        reverseBean.oO15 = cursor.getFloat(cursor.getColumnIndex("OO15"))
        reverseBean.oO20 = cursor.getFloat(cursor.getColumnIndex("OO20"))
        reverseBean.oO25 = cursor.getFloat(cursor.getColumnIndex("OO25"))
        reverseBean.oO30 = cursor.getFloat(cursor.getColumnIndex("OO30"))
        reverseBean.oO35 = cursor.getFloat(cursor.getColumnIndex("OO35"))
        reverseBean.oO40 = cursor.getFloat(cursor.getColumnIndex("OO40"))
        reverseBean.oO45 = cursor.getFloat(cursor.getColumnIndex("OO45"))
        reverseBean.oO50 = cursor.getFloat(cursor.getColumnIndex("OO50"))
        reverseBean.oO55 = cursor.getFloat(cursor.getColumnIndex("OO55"))
        reverseBean.oO60 = cursor.getFloat(cursor.getColumnIndex("OO60"))
        reverseBean.oO65 = cursor.getFloat(cursor.getColumnIndex("OO65"))
        reverseBean.oO70 = cursor.getFloat(cursor.getColumnIndex("OO70"))

        reverseBean.oC3 = cursor.getFloat(cursor.getColumnIndex("OC3"))
        reverseBean.oC5 = cursor.getFloat(cursor.getColumnIndex("OC5"))
        reverseBean.oC10 = cursor.getFloat(cursor.getColumnIndex("OC10"))
        reverseBean.oC15 = cursor.getFloat(cursor.getColumnIndex("OC15"))
        reverseBean.oC20 = cursor.getFloat(cursor.getColumnIndex("OC20"))
        reverseBean.oC25 = cursor.getFloat(cursor.getColumnIndex("OC25"))
        reverseBean.oC30 = cursor.getFloat(cursor.getColumnIndex("OC30"))
        reverseBean.oC35 = cursor.getFloat(cursor.getColumnIndex("OC35"))
        reverseBean.oC40 = cursor.getFloat(cursor.getColumnIndex("OC40"))
        reverseBean.oC45 = cursor.getFloat(cursor.getColumnIndex("OC45"))
        reverseBean.oC50 = cursor.getFloat(cursor.getColumnIndex("OC50"))
        reverseBean.oC55 = cursor.getFloat(cursor.getColumnIndex("OC55"))
        reverseBean.oC60 = cursor.getFloat(cursor.getColumnIndex("OC60"))
        reverseBean.oC65 = cursor.getFloat(cursor.getColumnIndex("OC65"))
        reverseBean.oC70 = cursor.getFloat(cursor.getColumnIndex("OC70"))

        reverseBean.pP5 = cursor.getFloat(cursor.getColumnIndex("PP5"))
        reverseBean.pP10 = cursor.getFloat(cursor.getColumnIndex("PP10"))
        reverseBean.pP15 = cursor.getFloat(cursor.getColumnIndex("PP15"))
        reverseBean.pP20 = cursor.getFloat(cursor.getColumnIndex("PP20"))
        reverseBean.pP25 = cursor.getFloat(cursor.getColumnIndex("PP25"))
        reverseBean.pP30 = cursor.getFloat(cursor.getColumnIndex("PP30"))
        reverseBean.pP35 = cursor.getFloat(cursor.getColumnIndex("PP35"))
        reverseBean.pP40 = cursor.getFloat(cursor.getColumnIndex("PP40"))
        reverseBean.pP45 = cursor.getFloat(cursor.getColumnIndex("PP45"))
        reverseBean.pP50 = cursor.getFloat(cursor.getColumnIndex("PP50"))
        reverseBean.pP55 = cursor.getFloat(cursor.getColumnIndex("PP55"))
        reverseBean.pP60 = cursor.getFloat(cursor.getColumnIndex("PP60"))
        reverseBean.pP65 = cursor.getFloat(cursor.getColumnIndex("PP65"))
        reverseBean.pP70 = cursor.getFloat(cursor.getColumnIndex("PP70"))

        reverseBean.ppP5 = cursor.getFloat(cursor.getColumnIndex("PPP5"))
        reverseBean.ppP10 = cursor.getFloat(cursor.getColumnIndex("PPP10"))
        reverseBean.ppP20 = cursor.getFloat(cursor.getColumnIndex("PPP20"))
        reverseBean.ppP30 = cursor.getFloat(cursor.getColumnIndex("PPP30"))
        reverseBean.ppP40 = cursor.getFloat(cursor.getColumnIndex("PPP40"))
        reverseBean.ppP50 = cursor.getFloat(cursor.getColumnIndex("PPP50"))
        reverseBean.ppP60 = cursor.getFloat(cursor.getColumnIndex("PPP60"))
        reverseBean.ppP70 = cursor.getFloat(cursor.getColumnIndex("PPP70"))
        return reverseBean
    }

    @SuppressLint("Recycle")
    fun getDerbyAAFilterAllByTbName(
        sqlStr: String,
        selection: Array<String?>?
    ): ArrayList<BaseReverseImp>? {
        switchDBName(Datas.REV_FILTERDB + 2020)
        var list: ArrayList<BaseReverseImp>? = null
        val cursor =
            db.rawQuery(sqlStr, selection)
        cursor?.let {
            list = ArrayList()
            it.moveToFirst()
            while (!it.isAfterLast) {
                val bean = getDerbyRevKJBeanByCursor(it)
                list!!.add(bean)
                it.moveToNext()
            }
            it.close()
        }
        return list
    }


    fun createOtherBBTBDataByOriginCodeAndDate(
        dbName: String,
        code: Int,
        date: String,
        type: Int,
        tbName: String,
        endStr: String = ""
    ): String {
        var newTbName = ""
        switchDBName(dbName)
        if (!tabbleIsExist(tbName)) {
            return ""
        }
        val cursor =
            db.rawQuery(
                " SELECT * FROM $tbName WHERE CODE=? AND DATE =?",
                arrayOf(code.toString(), date)
            )
        cursor?.let {
            val list: ArrayList<BaseReverseImp> = ArrayList()
            it.moveToFirst()
            while (!it.isAfterLast) {
                when (type) {
                    1 -> {
                        val bean = getRevKJBeanByCursor(it)
                        list.add(bean)
                    }
                    2 -> {
                        val bean = getDerbyRevKJBeanByCursor(it)
                        list.add(bean)
                    }
                    3 -> {
                        val bean = getTRKSLLBeanByCursor(it)
                        list.add(bean)
                    }
                }
                it.moveToNext()
            }
            it.close()
            switchDBName(Datas.REV_FILTERDB + 2020)
            var createTB = ""
            var insertTB = ""
            list.forEach {
                when (type) {
                    1 -> {
                        if (it is ReverseKJsonBean) {
                            newTbName =
                                tbName.replace("A_RTB", Datas.BB_FIL_COPY_ + "A_RTB") + endStr
                            createTB = it.createTB(newTbName)
                            insertTB = it.insertTB(newTbName)

                        }
                    }
                    2 -> {
                        if (it is ReverseKJsonBean) {
                            newTbName = tbName.replace(
                                Datas.Derby,
                                Datas.BB_FIL_COPY_ + Datas.Derby
                            ) + endStr
                            createTB = it.createDerbyTB(newTbName)
                            insertTB = it.insertDerbyTB(newTbName)
                        }
                    }
                    3 -> {
                        if (it is TR_K_SLL_Bean) {
                            newTbName = tbName.replace("TR", "BB_TR") + endStr
                            createTB = it.createTB(newTbName)
                            insertTB = it.insertTB(newTbName)
                        }
                    }

                }
                if (!createTB.isEmpty() && !insertTB.isEmpty()) {
                    db.execSQL(createTB)
                    if (!queryDataIsExsitByCodeAndDate(newTbName, code.toString(), date)) {
                        LogUtil.d("insertTB:$insertTB")
                        db.execSQL(insertTB)
                    }
                }
            }
        }

        return newTbName
    }

    fun queryDeleteTBByDBAndLimit(tbName: String, dbName: String) {
        switchDBName(dbName)
        val cursor =
            db.rawQuery(" SELECT name FROM sqlite_master", null)
        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val name = it.getString(it.getColumnIndex("name"))
                if (!name.equals(tbName) && !name.equals("sqlite_sequence")) {
                    dropTable(name)
                }
                it.moveToNext()
            }
            it.close()
        }
    }

    fun queryTBCountByTBAndDB(tbName: String, dbName: String): Int {
        switchDBName(dbName)
        val cursor =
            db.rawQuery(" SELECT * FROM $tbName", null)
        var count = 0
        cursor?.let {
            count = it.count
            it.close()
        }
        return count
    }

    fun getAllCopyBBTBNameList(): ArrayList<String> {
        switchDBName(Datas.REV_FILTERDB + "2020")
        val cursor =
            db.rawQuery(" SELECT name FROM sqlite_master", null)
        val list = ArrayList<String>()
        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val name = it.getString(it.getColumnIndex("name"))
                if (name.contains("BB_")) {
                    list.add(name)
                }
                it.moveToNext()
            }
            it.close()
        }
        return list
    }

    fun insertFilterResultJson(p50FilterBBKjRangeBean: P50FilterBBKJRangeBean): String {
        switchDBName(Datas.REV_FILTERDB + "2020")
        val tbName = "AAA_FILTER_RESULT"
        val createSql = p50FilterBBKjRangeBean.createTB(tbName)
        db.execSQL(createSql)
        var sqlStr = ""
        if (!queryIsExsitByCodeAndCustomColumn(tbName, arrayOf("P_TYPE"), arrayOf("50"))) {
            sqlStr = p50FilterBBKjRangeBean.insertTB(
                tbName,
                "50",
                GsonHelper.toJson(p50FilterBBKjRangeBean)
            )
        } else {
            sqlStr =
                p50FilterBBKjRangeBean.updateTB(tbName, GsonHelper.toJson(p50FilterBBKjRangeBean))
        }
        LogUtil.d(sqlStr)
        db.execSQL(sqlStr)
        return sqlStr
    }

    fun getFilterResultJsonByType(P_TYPE: String): String {
        switchDBName(Datas.REV_FILTERDB + "2020")
        val tbName = "AAA_FILTER_RESULT"
        var json = ""
        if (tabbleIsExist(tbName)) {
            val cursor =
                db.rawQuery(" SELECT JSON FROM $tbName WHERE P_TYPE = ?", arrayOf(P_TYPE))
            if (null != cursor && cursor.moveToFirst()) {
                json = cursor.getString(cursor.getColumnIndex("JSON"))
                cursor.close()
            }
        }
        return json
    }


    fun queryIsExsitByCodeAndCustomColumn(
        tbName: String,
        customKey: Array<String>,
        customValue: Array<String>
    ): Boolean {
        var isexsit = false
        if (tabbleIsExist(tbName)) {
            var whereLimit = ""
            customKey.forEach {
                whereLimit =
                    if (whereLimit.isEmpty()) "$whereLimit $it = ?" else "$whereLimit AND $it = ?"
            }
            var cursor =
                db.rawQuery("SELECT * FROM $tbName WHERE $whereLimit", customValue)
            isexsit = cursor.count > 0
            LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
            cursor.close()
        }
        return isexsit
    }

    fun insertReasoningRevTB(reasoningRevBean: ReasoningRevBean): Boolean {
        switchDBName(Datas.REV_RESONING_DB)
        val createSql = reasoningRevBean.createTB("Reasoning")
        db.execSQL(createSql)
        val isExit = queryIsExsitByCodeAndCustomColumn(
            "Reasoning",
            arrayOf("CODE", "D"),
            arrayOf(reasoningRevBean.code.toString(), reasoningRevBean.d)
        )
        LogUtil.d("insertReasoningRevTB--->${reasoningRevBean.code},date--->${reasoningRevBean.d}")
        if (!isExit) {
            val insert = reasoningRevBean.insertTB("Reasoning")
            db.execSQL(insert)
        }
        return isExit
    }

    fun insertReasoningAllTB(reasoningRevBean: ReasoningRevBean, is50: Boolean): Boolean {
        switchDBName(Datas.REV_RESONING_DB)
        val tbName = if (is50) "All_Reasoning_50" else "All_Reasoning_30"
        val createSql = reasoningRevBean.createAllTB(tbName)
        db.execSQL(createSql)
        val isExit = queryIsExsitByCodeAndCustomColumn(
            tbName,
            arrayOf("CODE", "D"),
            arrayOf(reasoningRevBean.code.toString(), reasoningRevBean.d)
        )
        LogUtil.d("insertReasoningAllTB--->${reasoningRevBean.code},date--->${reasoningRevBean.d},$is50,${reasoningRevBean.p}")
        if (!isExit) {
            val insert = reasoningRevBean.insertAllTB(tbName)
            db.execSQL(insert)
        }
        return isExit
    }

    fun getReasoningResult(tbName: String): ArrayList<ReasoningRevBean> {
        switchDBName(Datas.REV_RESONING_DB)
        val list = ArrayList<ReasoningRevBean>()
        if (tabbleIsExist(tbName)) {
            val cursor =
                db.rawQuery(" SELECT * FROM $tbName", null)
            if (tbName.contains("OC_OO")) {
                getReasoningOCOORevBeanByCusorAndTb(cursor, list)
            } else {
                getReasoningRevBeanByCusorAndTb(cursor, tbName, list)
            }
        }
        return list

    }

    fun insertOCOOJudgeTB(
        reasoningAllJudgeBean: ReasoningAllJudgeBean,
        insertTB: String
    ) {
        switchDBName(Datas.REV_RESONING_DB)
        if (!tabbleIsExist(insertTB)) {
            val createSQL = reasoningAllJudgeBean.createOCOOTB(insertTB)
            db.execSQL(createSQL)
        }
        if (tabbleIsExist(insertTB)) {
            val querySQL = "SELECT * FROM $insertTB WHERE " +
                    "OC3_X = ${reasoningAllJudgeBean.oC3_X}  " +
                    "AND OC5_X = ${reasoningAllJudgeBean.oC5_X}   " +
                    "AND OC10_X  = ${reasoningAllJudgeBean.oC10_X}  " +
                    "AND OC15_X = ${reasoningAllJudgeBean.oC15_X}   " +
                    "AND OC20_X = ${reasoningAllJudgeBean.oC20_X}   " +
                    "AND OC25_X = ${reasoningAllJudgeBean.oC25_X}   " +
                    "AND OC30_X = ${reasoningAllJudgeBean.oC30_X}   " +
                    "AND OC35_X  = ${reasoningAllJudgeBean.oC35_X}  " +
                    "AND OC40_X  = ${reasoningAllJudgeBean.oC40_X}  " +
                    "AND OC45_X  = ${reasoningAllJudgeBean.oC45_X}  " +
                    "AND OC50_X  = ${reasoningAllJudgeBean.oC50_X}  " +
                    "AND OC55_X  = ${reasoningAllJudgeBean.oC55_X}  " +
                    "AND OC60_X  = ${reasoningAllJudgeBean.oC60_X}  " +
                    "AND OC65_X  = ${reasoningAllJudgeBean.oC65_X}  " +
                    "AND OC70_X  = ${reasoningAllJudgeBean.oC70_X}  " +
                    "AND OO3_X  = ${reasoningAllJudgeBean.oO3_X}  " +
                    "AND OO5_X  = ${reasoningAllJudgeBean.oO5_X}  " +
                    "AND OO10_X  = ${reasoningAllJudgeBean.oO10_X}  " +
                    "AND OO15_X  = ${reasoningAllJudgeBean.oO15_X}  " +
                    "AND OO20_X  = ${reasoningAllJudgeBean.oO20_X}  " +
                    "AND OO25_X  = ${reasoningAllJudgeBean.oO25_X}  " +
                    "AND OO30_X  = ${reasoningAllJudgeBean.oO30_X}  " +
                    "AND OO35_X = ${reasoningAllJudgeBean.oO35_X}   " +
                    "AND OO40_X  = ${reasoningAllJudgeBean.oO40_X}  " +
                    "AND OO45_X  = ${reasoningAllJudgeBean.oO45_X}  " +
                    "AND OO50_X  = ${reasoningAllJudgeBean.oO50_X}  " +
                    "AND OO55_X  = ${reasoningAllJudgeBean.oO55_X}  " +
                    "AND OO60_X  = ${reasoningAllJudgeBean.oO60_X}  " +
                    "AND OO65_X  = ${reasoningAllJudgeBean.oO65_X}  " +
                    "AND OO70_X  = ${reasoningAllJudgeBean.oO70_X}  " +
                    "AND PP5_X  = ${reasoningAllJudgeBean.pP5_X}  " +
                    "AND PP10_X  = ${reasoningAllJudgeBean.pP10_X}  " +
                    "AND PP15_X  = ${reasoningAllJudgeBean.pP15_X}  " +
                    "AND PP20_X  = ${reasoningAllJudgeBean.pP20_X}  "
                    "AND PP25_X  = ${reasoningAllJudgeBean.pP25_X}  " +
                    "AND PP30_X  = ${reasoningAllJudgeBean.pP30_X}  "
                    "AND PP35_X = ${reasoningAllJudgeBean.pP35_X}   " +
                    "AND PP40_X  = ${reasoningAllJudgeBean.pP40_X}  " +
                    "AND PP45_X  = ${reasoningAllJudgeBean.pP45_X}  " +
                    "AND PP50_X  = ${reasoningAllJudgeBean.pP50_X}  " +
                    "AND PP55_X  = ${reasoningAllJudgeBean.pP55_X}  " +
                    "AND PP60_X  = ${reasoningAllJudgeBean.pP60_X}  " +
                    "AND PP65_X  = ${reasoningAllJudgeBean.pP65_X}  " +
                    "AND PP70_X  = ${reasoningAllJudgeBean.pP70_X} " +

                    "AND PPP5_X  = ${reasoningAllJudgeBean.ppP5_X}  " +
                    "AND PPP10_X  = ${reasoningAllJudgeBean.ppP10_X}  " +
                    "AND PPP20_X  = ${reasoningAllJudgeBean.ppP20_X}  "
                    "AND PPP30_X  = ${reasoningAllJudgeBean.ppP30_X}  "
                    "AND PPP40_X  = ${reasoningAllJudgeBean.ppP40_X}  " +
                    "AND PPP50_X  = ${reasoningAllJudgeBean.ppP50_X}  " +
                    "AND PPP60_X  = ${reasoningAllJudgeBean.ppP60_X}  " +
                    "AND PPP70_X  = ${reasoningAllJudgeBean.ppP70_X}"
            val cusor = db.rawQuery(querySQL, null)
            if (cusor.count < 1) {
                cusor.close()
                val insertSQL = reasoningAllJudgeBean.insertOCOOTB(insertTB)
                db.execSQL(insertSQL)
            }
        }
    }


    fun insertAllJudgeTB(
        reasoningAllJudgeBean: ReasoningAllJudgeBean,
        insertTB: String
    ) {
        switchDBName(Datas.REV_RESONING_DB)
        if (!tabbleIsExist(insertTB)) {
            val createSQL = reasoningAllJudgeBean.createTB(insertTB)
            db.execSQL(createSQL)
        }
        if (tabbleIsExist(insertTB)) {
            val insertSQL = reasoningAllJudgeBean.insertTB(insertTB)
            db.execSQL(insertSQL)
        }
    }

    fun getReasoningAllJudgeBeanByAllOmM(
        OM_M:Float,
        OM_C:Float,
        OM_P:Float,
        OM_L:Float,
        OL_M:Float,
        OL_C:Float,
        OL_P:Float,
        OL_L:Float,
        OC_M:Float,
        OC_C:Float,
        OC_P:Float,
        OC_L:Float,
        OP_M:Float,
        OP_C:Float,
        OP_P:Float,
        OP_L:Float,
        is50: Boolean,
        D_T: Int,
        allReasoningBean: ReasoningRevBean
    ): ArrayList<ReasoningAllJudgeBean> {
        switchDBName(Datas.REV_RESONING_DB)
        val list = ArrayList<ReasoningAllJudgeBean>()
        val tbName = if (is50) "All_50" else "All_30"
        val querySql = allReasoningBean.getF_TSql(D_T)
        val mSQL = " SELECT * FROM $tbName WHERE D_T=$D_T " +
                " AND OM_M_X<=$OM_M AND OM_M_D>=$OM_M " +
                " AND OM_C_X<=$OM_C AND OM_C_D>=$OM_C " +
                " AND OM_P_X<=$OM_P AND OM_P_D>=$OM_P " +
                " AND OM_L_X<=$OM_L AND OM_L_D>=$OM_L " +
                " AND OC_M_X<=$OC_M AND OC_M_D>=$OC_M " +
                " AND OC_C_X<=$OC_C AND OC_C_D>=$OC_C " +
                " AND OC_P_X<=$OC_P AND OC_P_D>=$OC_P " +
                " AND OC_L_X<=$OC_L AND OC_L_D>=$OC_L " +
                " AND OO_M_X<=$OP_M AND OO_M_D>=$OP_M " +
                " AND OO_C_X<=$OP_C AND OO_C_D>=$OP_C " +
                " AND OO_P_X<=$OP_P AND OO_P_D>=$OP_P " +
                " AND OO_L_X<=$OP_L AND OO_L_D>=$OP_L " +
                " AND OL_M_X<=$OL_M AND OL_M_D>=$OL_M " +
                " AND OL_C_X<=$OL_C AND OL_C_D>=$OL_C " +
                " AND OL_P_X<=$OL_P AND OL_P_D>=$OL_P " +
                " AND OL_L_X<=$OL_L AND OL_L_D>=$OL_L "+
                "$querySql"
        if (tabbleIsExist(tbName)) {
            val cursor =
                db.rawQuery(
                    mSQL,
                    null
                )
            if (null != cursor && cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val reasoningAllJudgeBean = ReasoningAllJudgeBean()
                    reasoningAllJudgeBean.oM_M_X = cursor.getFloat(cursor.getColumnIndex("OM_M_X"))
                    reasoningAllJudgeBean.oM_M_D = cursor.getFloat(cursor.getColumnIndex("OM_M_D"))
                    reasoningAllJudgeBean.oM_C_D = cursor.getFloat(cursor.getColumnIndex("OM_C_D"))
                    reasoningAllJudgeBean.oM_P_D = cursor.getFloat(cursor.getColumnIndex("OM_P_D"))
                    reasoningAllJudgeBean.oM_L_D = cursor.getFloat(cursor.getColumnIndex("OM_L_D"))
                    reasoningAllJudgeBean.oC_M_D = cursor.getFloat(cursor.getColumnIndex("OC_M_D"))
                    reasoningAllJudgeBean.oC_C_D = cursor.getFloat(cursor.getColumnIndex("OC_C_D"))
                    reasoningAllJudgeBean.oC_P_D = cursor.getFloat(cursor.getColumnIndex("OC_P_D"))
                    reasoningAllJudgeBean.oC_L_D = cursor.getFloat(cursor.getColumnIndex("OC_L_D"))
                    reasoningAllJudgeBean.oO_M_D = cursor.getFloat(cursor.getColumnIndex("OO_M_D"))
                    reasoningAllJudgeBean.oO_C_D = cursor.getFloat(cursor.getColumnIndex("OO_C_D"))
                    reasoningAllJudgeBean.oO_P_D = cursor.getFloat(cursor.getColumnIndex("OO_P_D"))
                    reasoningAllJudgeBean.oO_L_D = cursor.getFloat(cursor.getColumnIndex("OO_L_D"))
                    reasoningAllJudgeBean.oL_M_D = cursor.getFloat(cursor.getColumnIndex("OL_M_D"))
                    reasoningAllJudgeBean.oL_C_D = cursor.getFloat(cursor.getColumnIndex("OL_C_D"))
                    reasoningAllJudgeBean.oL_P_D = cursor.getFloat(cursor.getColumnIndex("OL_P_D"))
                    reasoningAllJudgeBean.oL_L_D = cursor.getFloat(cursor.getColumnIndex("OL_L_D"))
                    reasoningAllJudgeBean.oM_C_X = cursor.getFloat(cursor.getColumnIndex("OM_C_X"))
                    reasoningAllJudgeBean.oM_P_X = cursor.getFloat(cursor.getColumnIndex("OM_P_X"))
                    reasoningAllJudgeBean.oM_L_X = cursor.getFloat(cursor.getColumnIndex("OM_L_X"))
                    reasoningAllJudgeBean.oC_M_X = cursor.getFloat(cursor.getColumnIndex("OC_M_X"))
                    reasoningAllJudgeBean.oC_C_X = cursor.getFloat(cursor.getColumnIndex("OC_C_X"))
                    reasoningAllJudgeBean.oC_P_X = cursor.getFloat(cursor.getColumnIndex("OC_P_X"))
                    reasoningAllJudgeBean.oC_L_X = cursor.getFloat(cursor.getColumnIndex("OC_L_X"))
                    reasoningAllJudgeBean.oO_M_X = cursor.getFloat(cursor.getColumnIndex("OO_M_X"))
                    reasoningAllJudgeBean.oO_C_X = cursor.getFloat(cursor.getColumnIndex("OO_C_X"))
                    reasoningAllJudgeBean.oO_P_X = cursor.getFloat(cursor.getColumnIndex("OO_P_X"))
                    reasoningAllJudgeBean.oO_L_X = cursor.getFloat(cursor.getColumnIndex("OO_L_X"))
                    reasoningAllJudgeBean.oL_M_X = cursor.getFloat(cursor.getColumnIndex("OL_M_X"))
                    reasoningAllJudgeBean.oL_C_X = cursor.getFloat(cursor.getColumnIndex("OL_C_X"))
                    reasoningAllJudgeBean.oL_P_X = cursor.getFloat(cursor.getColumnIndex("OL_P_X"))
                    reasoningAllJudgeBean.oL_L_X = cursor.getFloat(cursor.getColumnIndex("OL_L_X"))


                    reasoningAllJudgeBean.oM_OC_D =
                        cursor.getFloat(cursor.getColumnIndex("OM_OC_D"))
                    reasoningAllJudgeBean.oM_OP_D =
                        cursor.getFloat(cursor.getColumnIndex("OM_OP_D"))
                    reasoningAllJudgeBean.oM_OL_D =
                        cursor.getFloat(cursor.getColumnIndex("OM_OL_D"))
                    reasoningAllJudgeBean.oC_OP_D =
                        cursor.getFloat(cursor.getColumnIndex("OC_OP_D"))
                    reasoningAllJudgeBean.oC_OL_D =
                        cursor.getFloat(cursor.getColumnIndex("OC_OL_D"))
                    reasoningAllJudgeBean.oP_OL_D =
                        cursor.getFloat(cursor.getColumnIndex("OP_OL_D"))
                    reasoningAllJudgeBean.m_C_D = cursor.getFloat(cursor.getColumnIndex("M_C_D"))
                    reasoningAllJudgeBean.m_P_D = cursor.getFloat(cursor.getColumnIndex("M_P_D"))
                    reasoningAllJudgeBean.m_L_D = cursor.getFloat(cursor.getColumnIndex("M_L_D"))
                    reasoningAllJudgeBean.c_P_D = cursor.getFloat(cursor.getColumnIndex("C_P_D"))
                    reasoningAllJudgeBean.c_L_D = cursor.getFloat(cursor.getColumnIndex("C_L_D"))
                    reasoningAllJudgeBean.p_L_D = cursor.getFloat(cursor.getColumnIndex("P_L_D"))
                    reasoningAllJudgeBean.oM_OC_X =
                        cursor.getFloat(cursor.getColumnIndex("OM_OC_X"))
                    reasoningAllJudgeBean.oM_OP_X =
                        cursor.getFloat(cursor.getColumnIndex("OM_OP_X"))
                    reasoningAllJudgeBean.oM_OL_X =
                        cursor.getFloat(cursor.getColumnIndex("OM_OL_X"))
                    reasoningAllJudgeBean.oC_OP_X =
                        cursor.getFloat(cursor.getColumnIndex("OC_OP_X"))
                    reasoningAllJudgeBean.oC_OL_X =
                        cursor.getFloat(cursor.getColumnIndex("OC_OL_X"))
                    reasoningAllJudgeBean.oP_OL_X =
                        cursor.getFloat(cursor.getColumnIndex("OP_OL_X"))
                    reasoningAllJudgeBean.m_C_X = cursor.getFloat(cursor.getColumnIndex("M_C_X"))
                    reasoningAllJudgeBean.m_P_X = cursor.getFloat(cursor.getColumnIndex("M_P_X"))
                    reasoningAllJudgeBean.m_L_X = cursor.getFloat(cursor.getColumnIndex("M_L_X"))
                    reasoningAllJudgeBean.c_P_X = cursor.getFloat(cursor.getColumnIndex("C_P_X"))
                    reasoningAllJudgeBean.c_L_X = cursor.getFloat(cursor.getColumnIndex("C_L_X"))
                    reasoningAllJudgeBean.p_L_X = cursor.getFloat(cursor.getColumnIndex("P_L_X"))

                    reasoningAllJudgeBean.f36_T = cursor.getInt(cursor.getColumnIndex("F36_T"))
                    reasoningAllJudgeBean.f30_T = cursor.getInt(cursor.getColumnIndex("F30_T"))
                    reasoningAllJudgeBean.f25_T = cursor.getInt(cursor.getColumnIndex("F25_T"))
                    reasoningAllJudgeBean.f20_T = cursor.getInt(cursor.getColumnIndex("F20_T"))
                    reasoningAllJudgeBean.f15_T = cursor.getInt(cursor.getColumnIndex("F15_T"))
                    reasoningAllJudgeBean.f10_T = cursor.getInt(cursor.getColumnIndex("F10_T"))
                    reasoningAllJudgeBean.f05_T = cursor.getInt(cursor.getColumnIndex("F05_T"))
                    reasoningAllJudgeBean.f03_T = cursor.getInt(cursor.getColumnIndex("F03_T"))
                    reasoningAllJudgeBean.count = cursor.getInt(cursor.getColumnIndex("COUNT"))
                    reasoningAllJudgeBean.d_T = cursor.getInt(cursor.getColumnIndex("D_T"))
                    list.add(reasoningAllJudgeBean)
                    LogUtil.d(
                        "$tbName $D_T,size:${list.size},reasoningAllJudgeBean-->\n${GsonHelper.toJson(
                            reasoningAllJudgeBean
                        )}"
                    )
                    cursor.moveToNext()
                }
                cursor.close()
            }

        }
        LogUtil.d("$is50 ,${list.size},mSQL-->$mSQL")
        return list
    }

    fun getReasoningInitAllJudgeResult(
        tbName: String,
        bean: ReasoningRevBean
    ): ArrayList<ReasoningRevBean> {

        switchDBName(Datas.REV_RESONING_DB)
        val list = ArrayList<ReasoningRevBean>()

        val endJudgeStr = getJudgeEndStr(bean)
        if (tabbleIsExist(tbName)) {
            val querySQL =
                "F36_T = ${bean.f36_T} AND F30_T = ${bean.f30_T} AND F25_T = ${bean.f25_T} AND F20_T = ${bean.f20_T} AND F15_T = ${bean.f15_T} AND  F10_T = ${bean.f10_T} AND F05_T = ${bean.f05_T} AND  F03_T = ${bean.f03_T}  ${endJudgeStr}"

            getReasoningPList(tbName, querySQL, list)
        }
        list.sortReasoningRevBeanByP()
        return list
    }

    fun getReasoningAllJudgeResult(
        tbName: String,
        bean: ReasoningRevBean
    ): Triple<ArrayList<ReasoningRevBean>, ArrayList<ReasoningRevBean>, ArrayList<ReasoningRevBean>> {

        switchDBName(Datas.REV_RESONING_DB)
        val list = ArrayList<ReasoningRevBean>()
        val list1 = ArrayList<ReasoningRevBean>()
        val list2 = ArrayList<ReasoningRevBean>()
        val endJudgeStr = getJudgeEndStr(bean)
        if (tabbleIsExist(tbName)) {
            val querySQL =
                "F36_T = ${bean.f36_T} AND F30_T = ${bean.f30_T} AND F25_T = ${bean.f25_T} AND F20_T = ${bean.f20_T} AND F15_T = ${bean.f15_T} AND  F10_T = ${bean.f10_T} AND F05_T = ${bean.f05_T} " +
                        "AND  F03_T = ${bean.f03_T} ${endJudgeStr}"
            val query1SQL =
                "F36_T = ${bean.f36_T} AND F30_T = ${bean.f30_T} AND F25_T = ${bean.f25_T} AND F20_T = ${bean.f20_T} AND F15_T = ${bean.f15_T} AND  F10_T = ${bean.f10_T} AND F05_T = ${bean.f05_T} " +
                        "AND  F03_T = ${bean.f03_T} ${endJudgeStr}" +
                        " AND  MA1 = ${bean.mA_1} AND  MA3 = ${bean.mA_3} "
            val query2SQL =
                "F36_T = ${bean.f36_T} AND F30_T = ${bean.f30_T} AND F25_T = ${bean.f25_T} AND F20_T = ${bean.f20_T} AND F15_T = ${bean.f15_T} AND  F10_T = ${bean.f10_T} AND F05_T = ${bean.f05_T} " +
                        "AND  F03_T = ${bean.f03_T} ${endJudgeStr}" +
                        " AND  MA1 = ${bean.mA_1} AND  MA3 = ${bean.mA_3}  AND  MA5 = ${bean.mA_5} "
            getReasoningPList(tbName, querySQL, list)
            getReasoningPList(tbName, query1SQL, list1)
            getReasoningPList(tbName, query2SQL, list2)
        }
        list.sortReasoningRevBeanByP()
        list1.sortReasoningRevBeanByP()
        list2.sortReasoningRevBeanByP()
        return Triple(list, list1, list2)
    }

    fun getReasoningOOOCAllJudgeResult(
        tbName: String,
        bean: ReasoningRevBean
    ): ArrayList<ReasoningRevBean> {

        switchDBName(Datas.REV_RESONING_DB)
        val list = ArrayList<ReasoningRevBean>()

        if (tabbleIsExist(tbName)) {
            val querySQL ="J_ID = ${bean.j_ID}"
//                " OC3 = ${bean.oC3} AND OC5 = ${bean.oC5} AND OC10 = ${bean.oC10} AND OC15 = ${bean.oC15} AND OC20 = ${bean.oC20} AND OC25 = ${bean.oC25} AND OC30 = ${bean.oC30} AND OC35 = ${bean.oC35} " +
//                        "AND OC40 = ${bean.oC40} AND OC45 = ${bean.oC45} AND OC50 = ${bean.oC50} AND OC55 = ${bean.oC55} AND OC60 = ${bean.oC60} AND OC65 = ${bean.oC65} AND OC70 = ${bean.oC70} " +
//                        "AND OO3 = ${bean.oO3} AND OO5 = ${bean.oO5} AND OO10 = ${bean.oO10} AND OO15 = ${bean.oO15} AND OO20 = ${bean.oO20} AND OO25 = ${bean.oO25} AND OO30 = ${bean.oO30} AND OO35 = ${bean.oO35} " +
//                        "AND OO40 = ${bean.oO40} AND OO45 = ${bean.oO45} AND OO50 = ${bean.oO50} AND OO55 = ${bean.oO55} AND OO60 = ${bean.oO60} AND OO65 = ${bean.oO65} AND OO70 = ${bean.oO70} " +
//                        "AND PP5 = ${bean.pP5} AND PP10 = ${bean.pP10} AND PP15 = ${bean.pP15} AND PP20 = ${bean.pP20} AND PP25 = ${bean.pP25} AND PP30 = ${bean.pP30} AND PP35 = ${bean.pP35} " +
//                        "AND PP40 = ${bean.pP40} AND PP45 = ${bean.pP45} AND PP50 = ${bean.pP50} AND PP55 = ${bean.pP55} AND PP60 = ${bean.pP60} AND PP65 = ${bean.pP65} AND PP70 = ${bean.pP70} " +
//                        "AND PPP5 = ${bean.ppP5} AND PPP10 = ${bean.ppP10} AND PPP20 = ${bean.ppP20} AND PPP30 = ${bean.ppP30} " +
//                        "AND PPP40 = ${bean.ppP40} AND PPP50 = ${bean.ppP50} AND PPP60 = ${bean.ppP60}  AND PPP70 = ${bean.ppP70} "
            LogUtil.d("querySQL-->$querySQL")
            getReasoningPList(tbName, querySQL, list)
        }
        list.sortReasoningRevBeanByP()
        return list
    }

    private fun getJudgeEndStr(bean: ReasoningRevBean): String {
        return " AND L36 = ${bean.l36} AND L30 = ${bean.l30} AND L25 = ${bean.l25} AND L20 = ${bean.l20} AND L15 = ${bean.l15} AND L10 = ${bean.l10}  AND L05 = ${bean.l05} AND L03 = ${bean.l03}" +
                " AND O36 = ${bean.o36} AND O30 = ${bean.o30} AND O25 = ${bean.o25} AND O20 = ${bean.o20} AND O15 = ${bean.o15} AND O10 = ${bean.o10}  AND O05 = ${bean.o05} AND O03 = ${bean.o03}" +
                " AND C36 = ${bean.c36} AND C30 = ${bean.c30} AND C25 = ${bean.c25} AND C20 = ${bean.c20} AND C15 = ${bean.c15} AND C10 = ${bean.c10}  AND C05 = ${bean.c05} AND C03 = ${bean.c03}"
    }

    private fun getReasoningPList(
        tbName: String,
        querySQL: String,
        list: ArrayList<ReasoningRevBean>
    ) {
        val cursor =
            db.rawQuery(" SELECT * FROM $tbName WHERE $querySQL", null)
        if (tbName.contains("OC_OO")) {
            getReasoningOCOORevBeanByCusorAndTb(cursor, list)
        } else {
            getReasoningRevBeanByCusorAndTb(cursor, tbName, list)
        }
    }

    fun getCodeListByTBName(tbName: String): ArrayList<ReasoningRevBean> {
        switchDBName(Datas.REV_RESONING_DB)
        val cursor =
            db.rawQuery(" SELECT * FROM $tbName ", null)
        val list = ArrayList<ReasoningRevBean>()
        if (null != cursor && cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val bean = ReasoningRevBean()
                bean.code = cursor.getInt(cursor.getColumnIndex("CODE"))
                bean.d = cursor.getString(cursor.getColumnIndex("D"))
                list.add(bean)
                cursor.moveToNext()
            }
        }
        cursor.close()
        return list
    }

    private fun getReasoningRevBeanByCusorAndTb(
        cursor: Cursor?,
        tbName: String,
        list: ArrayList<ReasoningRevBean>,
        isUpdate:Boolean = false
    ) {
        if (null != cursor && cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {

                val bean = ReasoningRevBean()
                val ID = cursor.getInt(cursor.getColumnIndex("_ID"))
                val SHOW = cursor.getInt(cursor.getColumnIndex("SHOW"))
                val CODE = cursor.getInt(cursor.getColumnIndex("CODE"))
                val D = cursor.getString(cursor.getColumnIndex("D"))
                val N = cursor.getString(cursor.getColumnIndex("N"))
                val D_D = cursor.getString(cursor.getColumnIndex("D_D"))
                val P = cursor.getFloat(cursor.getColumnIndex("P"))
                val MP = cursor.getFloat(cursor.getColumnIndex("MP"))
                val LP = cursor.getFloat(cursor.getColumnIndex("LP"))
                val AFTER_O_P = cursor.getFloat(cursor.getColumnIndex("AFTER_O_P"))
                val AFTER_C_P = cursor.getFloat(cursor.getColumnIndex("AFTER_C_P"))
                if (!tbName.equals("Reasoning") && !isUpdate) {
                    val F36_T = cursor.getInt(cursor.getColumnIndex("F36_T"))
                    val F30_T = cursor.getInt(cursor.getColumnIndex("F30_T"))
                    val F25_T = cursor.getInt(cursor.getColumnIndex("F25_T"))
                    val F20_T = cursor.getInt(cursor.getColumnIndex("F20_T"))
                    val F15_T = cursor.getInt(cursor.getColumnIndex("F15_T"))
                    val F10_T = cursor.getInt(cursor.getColumnIndex("F10_T"))
                    val F05_T = cursor.getInt(cursor.getColumnIndex("F05_T"))
                    val F03_T = cursor.getInt(cursor.getColumnIndex("F03_T"))
                    val MA1 = cursor.getInt(cursor.getColumnIndex("MA1"))
                    val MA3 = cursor.getInt(cursor.getColumnIndex("MA3"))
                    val MA5 = cursor.getInt(cursor.getColumnIndex("MA5"))
                    val MA10 = cursor.getInt(cursor.getColumnIndex("MA10"))

                    val L36 = cursor.getInt(cursor.getColumnIndex("L36"))
                    val L30 = cursor.getInt(cursor.getColumnIndex("L30"))
                    val L25 = cursor.getInt(cursor.getColumnIndex("L25"))
                    val L20 = cursor.getInt(cursor.getColumnIndex("L20"))
                    val L15 = cursor.getInt(cursor.getColumnIndex("L15"))
                    val L10 = cursor.getInt(cursor.getColumnIndex("L10"))
                    val L05 = cursor.getInt(cursor.getColumnIndex("L05"))
                    val L03 = cursor.getInt(cursor.getColumnIndex("L03"))
                    bean.l36 = L36
                    bean.l30 = L30
                    bean.l25 = L25
                    bean.l20 = L20
                    bean.l15 = L15
                    bean.l10 = L10
                    bean.l05 = L05
                    bean.l03 = L03


                    val O36 = cursor.getInt(cursor.getColumnIndex("O36"))
                    val O30 = cursor.getInt(cursor.getColumnIndex("O30"))
                    val O25 = cursor.getInt(cursor.getColumnIndex("O25"))
                    val O20 = cursor.getInt(cursor.getColumnIndex("O20"))
                    val O15 = cursor.getInt(cursor.getColumnIndex("O15"))
                    val O10 = cursor.getInt(cursor.getColumnIndex("O10"))
                    val O05 = cursor.getInt(cursor.getColumnIndex("O05"))
                    val O03 = cursor.getInt(cursor.getColumnIndex("O03"))
                    bean.o36 = O36
                    bean.o30 = O30
                    bean.o25 = O25
                    bean.o20 = O20
                    bean.o15 = O15
                    bean.o10 = O10
                    bean.o05 = O05
                    bean.o03 = O03


                    val C36 = cursor.getInt(cursor.getColumnIndex("C36"))
                    val C30 = cursor.getInt(cursor.getColumnIndex("C30"))
                    val C25 = cursor.getInt(cursor.getColumnIndex("C25"))
                    val C20 = cursor.getInt(cursor.getColumnIndex("C20"))
                    val C15 = cursor.getInt(cursor.getColumnIndex("C15"))
                    val C10 = cursor.getInt(cursor.getColumnIndex("C10"))
                    val C05 = cursor.getInt(cursor.getColumnIndex("C05"))
                    val C03 = cursor.getInt(cursor.getColumnIndex("C03"))
                    bean.c36 = C36
                    bean.c30 = C30
                    bean.c25 = C25
                    bean.c20 = C20
                    bean.c15 = C15
                    bean.c10 = C10
                    bean.c05 = C05
                    bean.c03 = C03

                    bean.f36_T = F36_T
                    bean.f30_T = F30_T
                    bean.f25_T = F25_T
                    bean.f20_T = F20_T
                    bean.f15_T = F15_T
                    bean.f10_T = F10_T
                    bean.f05_T = F05_T
                    bean.f03_T = F03_T
                    bean.mA_1 = MA1
                    bean.mA_3 = MA3
                    bean.mA_5 = MA5
                    bean.mA_10 = MA10
                }
                //                    val FITLERTYPE = cursor.getString(cursor.getColumnIndex("FITLERTYPE"))
                bean.code = CODE
                bean.n = N
                bean.d = D
                bean.d_D = D_D
                bean.p = P
                bean.mp = MP
                bean.lp = LP
                bean.after_O_P = AFTER_O_P
                bean.after_C_P = AFTER_C_P
                bean.id = ID
                bean.show = SHOW
                //                    bean.fitlertype = FITLERTYPE
                list.add(bean)
                cursor.moveToNext()
            }
            cursor.close()
        }
    }

    private fun getReasoningOCOORevBeanByCusorAndTb(
        cursor: Cursor?,
        list: ArrayList<ReasoningRevBean>
    ) {
        if (null != cursor && cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {

                val reverseBean = ReasoningRevBean()
                val ID = cursor.getInt(cursor.getColumnIndex("_ID"))
                val SHOW = cursor.getInt(cursor.getColumnIndex("SHOW"))
                val CODE = cursor.getInt(cursor.getColumnIndex("CODE"))
                val D = cursor.getString(cursor.getColumnIndex("D"))
                val N = cursor.getString(cursor.getColumnIndex("N"))
                val D_D = cursor.getString(cursor.getColumnIndex("D_D"))
                val P = cursor.getFloat(cursor.getColumnIndex("P"))
                val MP = cursor.getFloat(cursor.getColumnIndex("MP"))
                val LP = cursor.getFloat(cursor.getColumnIndex("LP"))
                val AFTER_O_P = cursor.getFloat(cursor.getColumnIndex("AFTER_O_P"))
                val AFTER_C_P = cursor.getFloat(cursor.getColumnIndex("AFTER_C_P"))

                reverseBean.oO3 = cursor.getFloat(cursor.getColumnIndex("OO3"))
                reverseBean.oO5 = cursor.getFloat(cursor.getColumnIndex("OO5"))
                reverseBean.oO10 = cursor.getFloat(cursor.getColumnIndex("OO10"))
                reverseBean.oO15 = cursor.getFloat(cursor.getColumnIndex("OO15"))
                reverseBean.oO20 = cursor.getFloat(cursor.getColumnIndex("OO20"))
                reverseBean.oO25 = cursor.getFloat(cursor.getColumnIndex("OO25"))
                reverseBean.oO30 = cursor.getFloat(cursor.getColumnIndex("OO30"))
                reverseBean.oO35 = cursor.getFloat(cursor.getColumnIndex("OO35"))
                reverseBean.oO40 = cursor.getFloat(cursor.getColumnIndex("OO40"))
                reverseBean.oO45 = cursor.getFloat(cursor.getColumnIndex("OO45"))
                reverseBean.oO50 = cursor.getFloat(cursor.getColumnIndex("OO50"))
                reverseBean.oO55 = cursor.getFloat(cursor.getColumnIndex("OO55"))
                reverseBean.oO60 = cursor.getFloat(cursor.getColumnIndex("OO60"))
                reverseBean.oO65 = cursor.getFloat(cursor.getColumnIndex("OO65"))
                reverseBean.oO70 = cursor.getFloat(cursor.getColumnIndex("OO70"))

                reverseBean.oC3 = cursor.getFloat(cursor.getColumnIndex("OC3"))
                reverseBean.oC5 = cursor.getFloat(cursor.getColumnIndex("OC5"))
                reverseBean.oC10 = cursor.getFloat(cursor.getColumnIndex("OC10"))
                reverseBean.oC15 = cursor.getFloat(cursor.getColumnIndex("OC15"))
                reverseBean.oC20 = cursor.getFloat(cursor.getColumnIndex("OC20"))
                reverseBean.oC25 = cursor.getFloat(cursor.getColumnIndex("OC25"))
                reverseBean.oC30 = cursor.getFloat(cursor.getColumnIndex("OC30"))
                reverseBean.oC35 = cursor.getFloat(cursor.getColumnIndex("OC35"))
                reverseBean.oC40 = cursor.getFloat(cursor.getColumnIndex("OC40"))
                reverseBean.oC45 = cursor.getFloat(cursor.getColumnIndex("OC45"))
                reverseBean.oC50 = cursor.getFloat(cursor.getColumnIndex("OC50"))
                reverseBean.oC55 = cursor.getFloat(cursor.getColumnIndex("OC55"))
                reverseBean.oC60 = cursor.getFloat(cursor.getColumnIndex("OC60"))
                reverseBean.oC65 = cursor.getFloat(cursor.getColumnIndex("OC65"))
                reverseBean.oC70 = cursor.getFloat(cursor.getColumnIndex("OC70"))

                reverseBean.pP5 = cursor.getFloat(cursor.getColumnIndex("PP5"))
                reverseBean.pP10 = cursor.getFloat(cursor.getColumnIndex("PP10"))
                reverseBean.pP15 = cursor.getFloat(cursor.getColumnIndex("PP15"))
                reverseBean.pP20 = cursor.getFloat(cursor.getColumnIndex("PP20"))
                reverseBean.pP25 = cursor.getFloat(cursor.getColumnIndex("PP25"))
                reverseBean.pP30 = cursor.getFloat(cursor.getColumnIndex("PP30"))
                reverseBean.pP35 = cursor.getFloat(cursor.getColumnIndex("PP35"))
                reverseBean.pP40 = cursor.getFloat(cursor.getColumnIndex("PP40"))
                reverseBean.pP45 = cursor.getFloat(cursor.getColumnIndex("PP45"))
                reverseBean.pP50 = cursor.getFloat(cursor.getColumnIndex("PP50"))
                reverseBean.pP55 = cursor.getFloat(cursor.getColumnIndex("PP55"))
                reverseBean.pP60 = cursor.getFloat(cursor.getColumnIndex("PP60"))
                reverseBean.pP65 = cursor.getFloat(cursor.getColumnIndex("PP65"))
                reverseBean.pP70 = cursor.getFloat(cursor.getColumnIndex("PP70"))

                reverseBean.ppP5 = cursor.getFloat(cursor.getColumnIndex("PPP5"))
                reverseBean.ppP10 = cursor.getFloat(cursor.getColumnIndex("PPP10"))
                reverseBean.ppP20 = cursor.getFloat(cursor.getColumnIndex("PPP20"))
                reverseBean.ppP30 = cursor.getFloat(cursor.getColumnIndex("PPP30"))
                reverseBean.ppP40 = cursor.getFloat(cursor.getColumnIndex("PPP40"))
                reverseBean.ppP50 = cursor.getFloat(cursor.getColumnIndex("PPP50"))
                reverseBean.ppP60 = cursor.getFloat(cursor.getColumnIndex("PPP60"))
                reverseBean.ppP70 = cursor.getFloat(cursor.getColumnIndex("PPP70"))
                reverseBean.j_ID = cursor.getInt(cursor.getColumnIndex("J_ID"))
                //                    val FITLERTYPE = cursor.getString(cursor.getColumnIndex("FITLERTYPE"))
                reverseBean.code = CODE
                reverseBean.n = N
                reverseBean.d = D
                reverseBean.d_D = D_D
                reverseBean.p = P
                reverseBean.mp = MP
                reverseBean.lp = LP
                reverseBean.after_O_P = AFTER_O_P
                reverseBean.after_C_P = AFTER_C_P
                reverseBean.id = ID
                reverseBean.show = SHOW
                //                    bean.fitlertype = FITLERTYPE
                list.add(reverseBean)
                cursor.moveToNext()
            }
            cursor.close()
        }
    }

    fun getNullDDFromReasoningTB(tbName: String): ArrayList<ReasoningRevBean> {
        switchDBName(Datas.REV_RESONING_DB)
        val list = ArrayList<ReasoningRevBean>()

        if (tabbleIsExist(tbName)) {
            val cursor =
                db.rawQuery(" SELECT * FROM $tbName WHERE D_D ='null'", null)

            getReasoningRevBeanByCusorAndTb(cursor, tbName, list,true)
        }
        return list
    }

    fun updateReasoning(tbName: String, bean: ReasoningRevBean) {
        switchDBName(Datas.REV_RESONING_DB)
        val sql =
            "UPDATE $tbName SET D_D = '${bean.d_D}',MP = ${bean.mp},P = ${bean.p} ,LP = ${bean.lp} ,AFTER_O_P = ${bean.after_O_P} ,AFTER_C_P = ${bean.after_C_P} WHERE CODE=${bean.code} AND D = '${bean.d}'"
        LogUtil.d("$sql")
        db.execSQL(sql)
    }

    fun updateReasoningShow(tbName: String, bean: ReasoningRevBean) {
        switchDBName(Datas.REV_RESONING_DB)
        val sql =
            "UPDATE $tbName SET SHOW = ${bean.show} WHERE CODE=${bean.code} AND D = '${bean.d}'"
        LogUtil.d("$sql")
        db.execSQL(sql)
    }

    fun insertOCOOBean(revKJOCOOBean: ReverseKJsonBean, tbName: String) {
        switchDBName(Datas.REVERSE_KJ_DB)
        if (!tabbleIsExist(tbName)) {
            val createSQL = revKJOCOOBean.createOCOOTB(tbName)
            db.execSQL(createSQL)
        }
        val insertSql = revKJOCOOBean.insertOCOOTB(tbName)
        LogUtil.d("$insertSql")
        db.execSQL(insertSql)
    }

    fun insertOCOOReasoningBean(
        revKJOCOOBean: ReasoningRevBean,
        tbName: String,
        updateSql: String
    ) {
        switchDBName(Datas.REV_RESONING_DB)
        if (!tabbleIsExist(tbName)) {
            val createSQL = revKJOCOOBean.createOCOOTB(tbName)
            db.execSQL(createSQL)
        }
        val insertSql = revKJOCOOBean.insertOCOOTB(tbName)
        LogUtil.d("insertReasoninResult $tbName:${revKJOCOOBean.n},${revKJOCOOBean.d},${revKJOCOOBean.p},rr:${revKJOCOOBean.rrate},fr:${revKJOCOOBean.frate},size:${revKJOCOOBean.size}")
        LogUtil.d("$insertSql")
        db.execSQL(insertSql)
        if (Datas.NEED_UPDATE_REV_ && revKJOCOOBean.p != 0.toFloat()) {

            val tbjudgeName = if (tbName.contains("50")) Datas.ALL_OC_OO_50 else Datas.ALL_OC_OO_30
            val judgeBean = ReasoningAllJudgeBean()

            val querySQL = "SELECT * FROM $tbjudgeName WHERE " + updateSql
            val cusor = db.rawQuery(querySQL, null)
            LogUtil.d("insertReasoninResult-->\n$querySQL")

            if (null != cusor && cusor.count > 0 && cusor.moveToFirst()) {
//                FR ,RR ,FS ,RS ,SIZE
                judgeBean.fs = cusor.getInt(cusor.getColumnIndex("FS"))
                judgeBean.rs = cusor.getInt(cusor.getColumnIndex("RS"))
                judgeBean.size = cusor.getInt(cusor.getColumnIndex("SIZE"))
                LogUtil.d("fs:${judgeBean.fs},rs:${judgeBean.rs},size:${judgeBean.size}")
                cusor.close()
                val update = judgeBean.updateOCOOTB(
                    revKJOCOOBean.p,
                    revKJOCOOBean.lp,
                    tbName.contains("50"),
                    judgeBean,
                    tbjudgeName,
                    updateSql
                )
                db.execSQL(update)
            }

        }
    }


    fun getReasoningOCOOJudgeBeanByOCOOBean(
        is50: Boolean,
        ocooBean: ReverseKJsonBean
    ): Pair<Int, Triple<Boolean, String, ReasoningAllJudgeBean>> {
        switchDBName(Datas.REV_RESONING_DB)
        val judgeBean = ReasoningAllJudgeBean()
        val tbName = if (is50) Datas.ALL_OC_OO_50 else Datas.ALL_OC_OO_30

        val judgeSQL =
            " OC70_D >= ${ocooBean.oC70} AND OC70_X <= ${ocooBean.oC70} AND OC65_D >= ${ocooBean.oC65} AND OC65_X <= ${ocooBean.oC65} AND " +
                    " OC60_D >= ${ocooBean.oC60} AND OC60_X <= ${ocooBean.oC60} AND OC55_D >= ${ocooBean.oC55} AND OC55_X <= ${ocooBean.oC55} AND " +
                    " OC50_D >= ${ocooBean.oC50} AND OC50_X <= ${ocooBean.oC50} AND OC45_D >= ${ocooBean.oC45} AND OC45_X <= ${ocooBean.oC45} AND " +
                    " OC40_D >= ${ocooBean.oC40} AND OC40_X <= ${ocooBean.oC40} AND OC35_D >= ${ocooBean.oC35} AND OC35_X <= ${ocooBean.oC35} AND " +
                    " OC30_D >= ${ocooBean.oC30} AND OC30_X <= ${ocooBean.oC30} AND " +
                    "OC25_D >= ${ocooBean.oC25} AND OC25_X <= ${ocooBean.oC25} AND " +
                    " OC20_D >= ${ocooBean.oC20} AND OC20_X <= ${ocooBean.oC20} AND " +
                    "OC15_D >= ${ocooBean.oC15} AND OC15_X <= ${ocooBean.oC15} AND " +
                    " OC10_D >= ${ocooBean.oC10} AND OC10_X <= ${ocooBean.oC10} AND " +
                    "OC5_D >= ${ocooBean.oC5} AND OC5_X <= ${ocooBean.oC5} AND " +
                    " OC3_D >= ${ocooBean.oC3} AND OC3_X <= ${ocooBean.oC3} AND " +
                    " OO70_D >= ${ocooBean.oO70} AND OO70_X <= ${ocooBean.oO70} AND OO65_D >= ${ocooBean.oO65} AND OO65_X <= ${ocooBean.oO65} AND " +
                    " OO60_D >= ${ocooBean.oO60} AND OO60_X <= ${ocooBean.oO60} AND OO55_D >= ${ocooBean.oO55} AND OO55_X <= ${ocooBean.oO55} AND " +
                    " OO50_D >= ${ocooBean.oO50} AND OO50_X <= ${ocooBean.oO50} AND OO45_D >= ${ocooBean.oO45} AND OO45_X <= ${ocooBean.oO45} AND " +
                    " OO40_D >= ${ocooBean.oO40} AND OO40_X <= ${ocooBean.oO40} AND OO35_D >= ${ocooBean.oO35} AND OO35_X <= ${ocooBean.oO35} AND " +
                    "OO30_D >= ${ocooBean.oO30} AND OO30_X <= ${ocooBean.oO30} AND " +
                    "OO25_D >= ${ocooBean.oO25} AND OO25_X <= ${ocooBean.oO25} AND " +
                    " OO20_D >= ${ocooBean.oO20} AND OO20_X <= ${ocooBean.oO20} AND " +
                    "OO15_D >= ${ocooBean.oO15} AND OO15_X <= ${ocooBean.oO15} AND " +
                    " OO10_D >= ${ocooBean.oO10} AND OO10_X <= ${ocooBean.oO10} AND " +
                    "OO5_D >= ${ocooBean.oO5} AND OO5_X <= ${ocooBean.oO5} AND " +
                    " OO3_D >= ${ocooBean.oO3} AND OO3_X <= ${ocooBean.oO3} AND " +


                    " PPP70_D >= ${ocooBean.ppP70} AND PPP70_X <= ${ocooBean.ppP70} AND PPP40_D >= ${ocooBean.ppP40} AND PPP40_X <= ${ocooBean.ppP40} AND " +
                    " PPP60_D >= ${ocooBean.ppP60} AND PPP60_X <= ${ocooBean.ppP60} AND PPP30_D >= ${ocooBean.ppP30} AND PPP30_X <= ${ocooBean.ppP30} AND " +
                    " PPP50_D >= ${ocooBean.ppP50} AND PPP50_X <= ${ocooBean.ppP50} AND PPP20_D >= ${ocooBean.ppP20} AND PPP20_X <= ${ocooBean.ppP20} AND " +
                    " PPP10_D >= ${ocooBean.ppP10} AND PPP10_X <= ${ocooBean.ppP10} AND PPP5_D >= ${ocooBean.ppP5} AND PPP5_X <= ${ocooBean.ppP5} AND " +

                    " PP70_D >= ${ocooBean.pP70} AND PP70_X <= ${ocooBean.pP70} AND PP65_D >= ${ocooBean.pP65} AND PP65_X <= ${ocooBean.pP65} AND " +
                    " PP60_D >= ${ocooBean.pP60} AND PP60_X <= ${ocooBean.pP60} AND PP55_D >= ${ocooBean.pP55} AND PP55_X <= ${ocooBean.pP55} AND " +
                    " PP50_D >= ${ocooBean.pP50} AND PP50_X <= ${ocooBean.pP50} AND PP45_D >= ${ocooBean.pP45} AND PP45_X <= ${ocooBean.pP45} AND " +
                    " PP40_D >= ${ocooBean.pP40} AND PP40_X <= ${ocooBean.pP40} AND PP35_D >= ${ocooBean.pP35} AND PP35_X <= ${ocooBean.pP35} AND " +
                    " PP30_D >= ${ocooBean.pP30} AND PP30_X <= ${ocooBean.pP30} AND " +
                    "PP25_D >= ${ocooBean.pP25} AND PP25_X <= ${ocooBean.pP25} AND " +
                    " PP20_D >= ${ocooBean.pP20} AND PP20_X <= ${ocooBean.pP20} AND " +
                    "PP15_D >= ${ocooBean.pP15} AND PP15_X <= ${ocooBean.pP15} AND " +
                    " PP10_D >= ${ocooBean.pP10} AND PP10_X <= ${ocooBean.pP10} AND " +
                    "PP5_D >= ${ocooBean.pP5} AND PP5_X <= ${ocooBean.pP5} ${Datas.FR_RR_LIMIT}"


        var needContinue = false
        var updateSQL = ""
        var judgeID = 0

        if (tabbleIsExist(tbName)) {

            val cursor =
                db.rawQuery(" SELECT * FROM $tbName WHERE $judgeSQL", null)
            LogUtil.d("judgeSQL $is50 cursor-${cursor?.count}-->\n$judgeSQL")

            if (cursor.count > 0 && cursor.moveToFirst()) {
                needContinue = true
                val fr = cursor.getFloat(cursor.getColumnIndex("FR"))
                val rr = cursor.getFloat(cursor.getColumnIndex("RR"))
                val size = cursor.getInt(cursor.getColumnIndex("SIZE"))
                judgeID = cursor.getInt(cursor.getColumnIndex("_ID"))
                judgeBean.fr = fr
                judgeBean.rr = rr
                judgeBean.size = size

                updateSQL =

                    " OC70_D = ${cursor.getFloat(cursor.getColumnIndex("OC70_D"))} AND OC70_X = ${cursor.getFloat(
                        cursor.getColumnIndex("OC70_X")
                    )} AND OC65_D = ${cursor.getFloat(cursor.getColumnIndex("OC65_D"))} AND OC65_X = ${cursor.getFloat(
                        cursor.getColumnIndex("OC65_X")
                    )} AND " +
                            " OC60_D = ${cursor.getFloat(cursor.getColumnIndex("OC60_D"))} AND OC60_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC60_X")
                            )} AND OC55_D = ${cursor.getFloat(cursor.getColumnIndex("OC55_D"))} AND OC55_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC55_X")
                            )} AND " +
                            " OC50_D = ${cursor.getFloat(cursor.getColumnIndex("OC50_D"))} AND OC50_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC50_X")
                            )} AND OC45_D = ${cursor.getFloat(cursor.getColumnIndex("OC45_D"))} AND OC45_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC45_X")
                            )} AND " +
                            " OC40_D = ${cursor.getFloat(cursor.getColumnIndex("OC40_D"))} AND OC40_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC40_X")
                            )} AND OC35_D = ${cursor.getFloat(cursor.getColumnIndex("OC35_D"))} AND OC35_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC35_X")
                            )} AND " +
                            " OC30_D = ${cursor.getFloat(cursor.getColumnIndex("OC30_D"))} AND OC30_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC30_X")
                            )} AND " +
                            "OC25_D = ${cursor.getFloat(cursor.getColumnIndex("OC25_D"))} AND OC25_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC25_X")
                            )} AND " +
                            " OC20_D = ${cursor.getFloat(cursor.getColumnIndex("OC20_D"))} AND OC20_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC20_X")
                            )} AND " +
                            "OC15_D = ${cursor.getFloat(cursor.getColumnIndex("OC15_D"))} AND OC15_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC15_X")
                            )} AND " +
                            " OC10_D = ${cursor.getFloat(cursor.getColumnIndex("OC10_D"))} AND OC10_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC10_X")
                            )} AND " +
                            "OC5_D = ${cursor.getFloat(cursor.getColumnIndex("OC5_D"))} AND OC5_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC5_X")
                            )} AND " +
                            " OC3_D = ${cursor.getFloat(cursor.getColumnIndex("OC3_D"))} AND OC3_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OC3_X")
                            )} AND " +


                            /*------------------------------------------*/
                            " OO70_D = ${cursor.getFloat(cursor.getColumnIndex("OO70_D"))} AND OO70_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO70_X")
                            )} AND OO65_D = ${cursor.getFloat(cursor.getColumnIndex("OO65_D"))} AND OO65_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO65_X")
                            )} AND " +
                            " OO60_D = ${cursor.getFloat(cursor.getColumnIndex("OO60_D"))} AND OO60_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO60_X")
                            )} AND OO55_D = ${cursor.getFloat(cursor.getColumnIndex("OO55_D"))} AND OO55_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO55_X")
                            )} AND " +
                            " OO50_D = ${cursor.getFloat(cursor.getColumnIndex("OO50_D"))} AND OO50_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO50_X")
                            )} AND OO45_D = ${cursor.getFloat(cursor.getColumnIndex("OO45_D"))} AND OO45_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO45_X")
                            )} AND " +
                            " OO40_D = ${cursor.getFloat(cursor.getColumnIndex("OO40_D"))} AND OO40_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO40_X")
                            )} AND OO35_D = ${cursor.getFloat(cursor.getColumnIndex("OO35_D"))} AND OO35_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO35_X")
                            )} AND " +
                            " OO30_D = ${cursor.getFloat(cursor.getColumnIndex("OO30_D"))} AND OO30_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO30_X")
                            )} AND " +
                            "OO25_D = ${cursor.getFloat(cursor.getColumnIndex("OO25_D"))} AND OO25_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO25_X")
                            )} AND " +
                            " OO20_D = ${cursor.getFloat(cursor.getColumnIndex("OO20_D"))} AND OO20_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO20_X")
                            )} AND " +
                            "OO15_D = ${cursor.getFloat(cursor.getColumnIndex("OO15_D"))} AND OO15_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO15_X")
                            )} AND " +
                            " OO10_D = ${cursor.getFloat(cursor.getColumnIndex("OO10_D"))} AND OO10_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO10_X")
                            )} AND " +
                            "OO5_D = ${cursor.getFloat(cursor.getColumnIndex("OO5_D"))} AND OO5_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO5_X")
                            )} AND " +
                            " OO3_D = ${cursor.getFloat(cursor.getColumnIndex("OO3_D"))} AND OO3_X = ${cursor.getFloat(
                                cursor.getColumnIndex("OO3_X")
                            )} AND " +
                            " PP20_D = ${cursor.getFloat(cursor.getColumnIndex("PP20_D"))} AND PP20_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP20_X")
                            )} AND " +



                            " PPP70_D = ${cursor.getFloat(cursor.getColumnIndex("PPP70_D"))} AND PPP70_X = ${cursor.getFloat(cursor.getColumnIndex("PPP70_X"))} AND PPP40_D = ${cursor.getFloat(cursor.getColumnIndex("PPP40_D"))} AND PPP40_X = ${cursor.getFloat(cursor.getColumnIndex("PPP40_X"))} AND " +
                            " PPP60_D = ${cursor.getFloat(cursor.getColumnIndex("PPP60_D"))} AND PPP60_X = ${cursor.getFloat(cursor.getColumnIndex("PPP60_X"))} AND PPP30_D = ${cursor.getFloat(cursor.getColumnIndex("PPP30_D"))} AND PPP30_X = ${cursor.getFloat(cursor.getColumnIndex("PPP30_X"))} AND " +
                            " PPP50_D = ${cursor.getFloat(cursor.getColumnIndex("PPP50_D"))} AND PPP50_X = ${cursor.getFloat(cursor.getColumnIndex("PPP50_X"))} AND PPP20_D = ${cursor.getFloat(cursor.getColumnIndex("PPP20_D"))} AND PPP20_X = ${cursor.getFloat(cursor.getColumnIndex("PPP20_X"))} AND " +
                            " PPP10_D = ${cursor.getFloat(cursor.getColumnIndex("PPP10_D"))} AND PPP10_X = ${cursor.getFloat(cursor.getColumnIndex("PPP10_X"))} AND PPP5_D = ${cursor.getFloat(cursor.getColumnIndex("PPP5_D"))} AND PPP5_X = ${cursor.getFloat(cursor.getColumnIndex("PPP5_X"))} AND " +

                            /*------------------------------------------*/
                            " PP70_D = ${cursor.getFloat(cursor.getColumnIndex("PP70_D"))} AND PP70_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP70_X")
                            )} AND PP65_D = ${cursor.getFloat(cursor.getColumnIndex("PP65_D"))} AND PP65_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP65_X")
                            )} AND " +
                            " PP60_D = ${cursor.getFloat(cursor.getColumnIndex("PP60_D"))} AND PP60_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP60_X")
                            )} AND PP55_D = ${cursor.getFloat(cursor.getColumnIndex("PP55_D"))} AND PP55_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP55_X")
                            )} AND " +
                            " PP50_D = ${cursor.getFloat(cursor.getColumnIndex("PP50_D"))} AND PP50_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP50_X")
                            )} AND PP45_D = ${cursor.getFloat(cursor.getColumnIndex("PP45_D"))} AND PP45_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP45_X")
                            )} AND " +
                            " PP40_D = ${cursor.getFloat(cursor.getColumnIndex("PP40_D"))} AND PP40_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP40_X")
                            )} AND PP35_D = ${cursor.getFloat(cursor.getColumnIndex("PP35_D"))} AND PP35_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP35_X")
                            )} AND " +
                            " PP30_D = ${cursor.getFloat(cursor.getColumnIndex("PP30_D"))} AND PP30_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP30_X")
                            )} AND " +
                            "PP25_D = ${cursor.getFloat(cursor.getColumnIndex("PP25_D"))} AND PP25_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP25_X")
                            )} AND " +
                            "PP15_D = ${cursor.getFloat(cursor.getColumnIndex("PP15_D"))} AND PP15_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP15_X")
                            )} AND " +
                            " PP10_D = ${cursor.getFloat(cursor.getColumnIndex("PP10_D"))} AND PP10_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP10_X")
                            )} AND " +
                            "PP5_D = ${cursor.getFloat(cursor.getColumnIndex("PP5_D"))} AND PP5_X = ${cursor.getFloat(
                                cursor.getColumnIndex("PP5_X")
                            )} "
                cursor.close()
            }

        }

        return Pair(judgeID,Triple(needContinue, updateSQL, judgeBean))

    }

    fun insertHolderBean(tbName: String,holderbean: HolderChangeBean) {
        switchDBName(Datas.OTHER_DB)
        val createSQL = holderbean.createTB(tbName)
        LogUtil.d("createSQL-->$createSQL")
        db.execSQL(createSQL)
        if (tabbleIsExist(tbName)) {
            val insertSQL = holderbean.insertTB(tbName)
            LogUtil.d("insertSQL-->$insertSQL")
            db.execSQL(insertSQL)
        }
        if (null != sqlCompleteListener) {
            sqlCompleteListener!!.onComplete()
        }
    }


    fun queryCPByCodeAndDate(code: String,date: String): String {
        switchDBName(code.toCodeHDD(date, FormatterEnum.YYYYMMDD))
        val tbName = "${Datas.CHDD}${code.toCompleteCode()}"
        var cp = ""
        if (tabbleIsExist(tbName)) {
            val querySQL = "select CP from $tbName where DATE = '${date}'"
            val cursor = db.rawQuery(querySQL, null)
            if (null != cursor && cursor.moveToFirst()) {
                cp = cursor.getString(cursor.getColumnIndex("CP"))
            }
            cursor.close()
        }

        return cp
    }


    fun queryHolderTBLastCode(tbName: String): String {
        switchDBName(Datas.OTHER_DB)
        var CODE = ""
        if (tabbleIsExist(tbName)) {
            val cursor = db.rawQuery("SELECT * FROM $tbName order by _ID desc", null)
            if (null != cursor && cursor.moveToFirst()) {
                CODE = cursor.getString(cursor.getColumnIndex("CODE"))
                LogUtil.d("CODE:$CODE")
            }
            cursor.close()
        }
        return CODE
    }

    var sqlCompleteListener:SQLCompleteListener? = null
    interface SQLCompleteListener {
        fun onComplete()
    }

}