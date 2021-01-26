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

    fun getLastCHDDBeanDate(code: String,tbName: String):String {
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
        var chddbean :ArrayList<CodeHDDBean>? = null
        var jianIndex = 1
//                = queryCHDDByTableName(tbName,code.toCodeHDD(mList[mList.size - 1], FormatterEnum.YYMM))

        while (null == chddbean) {
            if (mList.size - jianIndex < 0) {
                break
            }
            chddbean  = queryCHDDByTableName(tbName,code.toCodeHDD(mList[mList.size - jianIndex], FormatterEnum.YYMM))
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
            "UPDATE $dbName SET GAP_J = '${if(null==codeHDDBean.gaP_J) "" else GsonHelper.getInstance().toJson(codeHDDBean.gaP_J)}' WHERE  DATE = ${codeHDDBean.date}"
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
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =? AND DATE =?", arrayOf(code,date))
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
                            codeHDDBean.shape_J = GsonHelper.parse(Shape_J, ShapeJsonBean::class.java)
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
            queryCHDDByTableName(tableName,dbName)
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
        if (!queryDataIsExsitByCodeAndDate(tbName, reverseBean.code.toString(),date)) {
            var insertSqlStr = ""
            insertSqlStr = reverseBean.insertTB(tbName)
            if (reverseBean is ReverseKJsonBean) {
                val insertDerbySqlStr = reverseBean.insertDerbyTB(Datas.Derby + tbName)
                db.execSQL(insertDerbySqlStr)
            }
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
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
        switchDBName(Datas.GAP_RECORD_DB+DateUtils.changeFromDefaultFormatter(date,FormatterEnum.YYMM))
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
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =? AND B_D =?", arrayOf(code,bd))
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
        createRevCodeTable(tbName, reverseBean,code)
        if (!queryDataIsExsitInRevCodeTB(tbName, date,kotlin.run {
                if (reverseBean is ReverseKJsonBean) {
                    reverseBean.d_T
                } else if(reverseBean is TR_K_SLL_Bean) {
                    reverseBean.d_T
                } else {
                    ""
                }
            })) {
            var insertSqlStr = ""
            if (reverseBean is ReverseKJsonBean) {
                insertSqlStr = reverseBean.insertRevCodeTB(tbName)
                val insertDerbySqlStr = reverseBean.insertRevCodeDerbyTB(Datas.REV_CODE_DERBY + code)
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
                db.rawQuery("SELECT * FROM $dbName WHERE D_T =? AND DATE =?", arrayOf(DT,date))
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


    fun queryRevLimit(tbName: String,smallerMap:HashMap<String,String>, biggerMap:HashMap<String,String>,reverseType:Int): ArrayList<BaseReverseImp>? {
        val pair = tbName.getQuerySql(smallerMap,biggerMap)
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
                        1->{
                            val reverseBean = getRevKJBeanByCursor(cursor)
                            list.add(reverseBean)
                        }
                        2->{
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
        reverseBean.d_D = cursor.getString(cursor.getColumnIndex("D_D"))
        reverseBean.date = cursor.getString(cursor.getColumnIndex("DATE"))
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
        reverseBean.d_D = cursor.getString(cursor.getColumnIndex("D_D"))
        reverseBean.date = cursor.getString(cursor.getColumnIndex("DATE"))
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
        reverseBean.d_D = cursor.getString(cursor.getColumnIndex("D_D"))
        reverseBean.date = cursor.getString(cursor.getColumnIndex("DATE"))
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
            db.rawQuery(" SELECT * FROM $tbName WHERE ($column IN (SELECT MIN($column) FROM $tbName))", null)
        if (null != cursor && cursor.moveToFirst()) {
            minValue = cursor.getString(cursor.getColumnIndex(column))
        }
        cursor.close()
        cursor =
            db.rawQuery(" SELECT * FROM $tbName WHERE ($column IN (SELECT MAX($column) FROM $tbName))", null)
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
        var minValue = ""
        var maxValue = ""
        val addSql =  codeList.getCodeArrayAndLimitSQL(false)


        val querySql = " SELECT * FROM $tbName WHERE $addSql "
        var cursor =
            db.rawQuery(querySql, null)
        val ommList = ArrayList<Float>()
        if (null != cursor && cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                ommList.add(cursor.getFloat(cursor.getColumnIndex("OM_M")))
                cursor.moveToNext()
            }
        }
        ommList.sortFloatAsc()
        LogUtil.d("querySql-->$querySql \n $addSql cursor.count:${cursor.count}")
//        SELECT * FROM A_RTB_50_30 WHERE (OM_M IN (SELECT MIN(OM_M) FROM A_RTB_50_30 WHERE  CODE = ?  AND CODE = ?  AND CODE = ? ))
//        SELECT * FROM A_RTB_50_30 WHERE (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?) AND (CODE = ? AND DATE = ?)
//        300064 ,0928 ,300064 ,0929 ,600211 ,0724 , cursor.count:0
        cursor.close()

        return Pair(ommList[0].toString(), ommList[ommList.size-1].toString())
    }

    fun copyFilterTB2NewTB(
        newTBName: String,
        countList: java.util.ArrayList<BaseReverseImp>,
        type: Int
    ) {
        switchDBName(Datas.REV_FILTERDB+"2020")
        if (countList[0] is ReverseKJsonBean) {
            createRevFilterTable(newTBName, countList[0],type)
            countList.forEach {
                if (it is ReverseKJsonBean) {
                    if (!queryDataIsExsitByCodeAndDate(newTBName, it.code.toString(),it.date)){
                        var insertSqlStr = ""
                        when (type) {
                            1->{
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
                1->{
                    sqlStr = reverseBean.createTB(dbName)
                }
            }
            if (!TextUtils.isEmpty(sqlStr)) {
                db.execSQL(sqlStr)
            }
        }
    }

    @SuppressLint("Recycle")
    fun getAAFilterAllByTbName(sqlStr:String, selection:Array<String?>?): ArrayList<BaseReverseImp>? {
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

    fun getFilterAllByTbName(dbName: String, sqlStr:String,selection:Array<String?>?): ArrayList<BaseReverseImp>? {
        switchDBName(dbName)
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

    fun getFilterAllByDerbyTbName(dbName: String, sqlStr:String,selection:Array<String?>?): ArrayList<BaseReverseImp>? {
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


    @SuppressLint("Recycle")
    fun getDerbyAAFilterAllByTbName(sqlStr:String, selection:Array<String?>?): ArrayList<BaseReverseImp>? {
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


    fun createOtherBBTBDataByOriginCodeAndDate(dbName: String,code:Int,date:String,type: Int,tbName: String,endStr:String = ""):String {
        var newTbName =""
        switchDBName(dbName)
        if (!tabbleIsExist(tbName)) {
            return ""
        }
        val cursor =
            db.rawQuery(" SELECT * FROM $tbName WHERE CODE=? AND DATE =?", arrayOf(code.toString(),date))
        cursor?.let {
            val list: ArrayList<BaseReverseImp>
                    = ArrayList()
            it.moveToFirst()
            while (!it.isAfterLast) {
                when (type) {
                    1->{
                        val bean = getRevKJBeanByCursor(it)
                        list.add(bean)
                    }
                    2->{
                        val bean = getDerbyRevKJBeanByCursor(it)
                        list.add(bean)
                    }
                    3->{
                        val bean = getTRKSLLBeanByCursor(it)
                        list.add(bean)
                    }
                }
                it.moveToNext()
            }
            it.close()
            switchDBName(Datas.REV_FILTERDB + 2020)
            var createTB =""
            var insertTB =""
            list.forEach {
                when (type) {
                    1->{
                        if (it is ReverseKJsonBean) {
                            newTbName =
                                tbName.replace("A_RTB", Datas.BB_FIL_COPY_ + "A_RTB") + endStr
                            createTB = it.createTB(newTbName)
                            insertTB = it.insertTB(newTbName)

                        }
                    }
                    2->{
                        if (it is ReverseKJsonBean) {
                            newTbName = tbName.replace(Datas.Derby,Datas.BB_FIL_COPY_+Datas.Derby) + endStr
                            createTB = it.createDerbyTB(newTbName)
                            insertTB = it.insertDerbyTB(newTbName)
                        }
                    }
                    3->{
                        if (it is TR_K_SLL_Bean) {
                            newTbName = tbName.replace("TR", "BB_TR" ) + endStr
                            createTB = it.createTB(newTbName)
                            insertTB = it.insertTB(newTbName)
                        }
                    }

                }
                if (!createTB.isEmpty()&&!insertTB.isEmpty()) {
                    db.execSQL(createTB)
                    if (!queryDataIsExsitByCodeAndDate(newTbName,code.toString(),date)) {
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
                if (!name.equals(tbName)&&!name.equals("sqlite_sequence")) {
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
        switchDBName(Datas.REV_FILTERDB+"2020")
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

    fun insertFilterResultJson(p50FilterBBKjRangeBean: P50FilterBBKJRangeBean):String {
        switchDBName(Datas.REV_FILTERDB+"2020")
        val tbName = "AAA_FILTER_RESULT"
        val createSql = p50FilterBBKjRangeBean.createTB(tbName)
        db.execSQL(createSql)
        var sqlStr = ""
        if (!queryIsExsitByCodeAndCustomColumn(tbName, arrayOf("P_TYPE"), arrayOf("50"))) {
            sqlStr = p50FilterBBKjRangeBean.insertTB(tbName,"50", GsonHelper.toJson(p50FilterBBKjRangeBean))
        } else {
            sqlStr = p50FilterBBKjRangeBean.updateTB(tbName,GsonHelper.toJson(p50FilterBBKjRangeBean))
        }
        LogUtil.d(sqlStr)
        db.execSQL(sqlStr)
        return sqlStr
    }

    fun getFilterResultJsonByType(P_TYPE: String): String {
        switchDBName(Datas.REV_FILTERDB + "2020")
        val tbName = "AAA_FILTER_RESULT"
        val cursor =
            db.rawQuery(" SELECT JSON FROM $tbName WHERE P_TYPE = ?", arrayOf(P_TYPE))
        var json = ""
        if (null != cursor && cursor.moveToFirst()) {
            json = cursor.getString(cursor.getColumnIndex("JSON"))
            cursor.close()
        }
        return json
    }


    fun queryIsExsitByCodeAndCustomColumn(tbName: String, customKey: Array<String>, customValue: Array<String>): Boolean {
        var isexsit = false
        if (tabbleIsExist(tbName)) {
            var whereLimit = ""
            customKey.forEach {
                whereLimit = if (whereLimit.isEmpty()) "$whereLimit $it = ?" else "$whereLimit AND $it = ?"
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
        val isExit = queryIsExsitByCodeAndCustomColumn("Reasoning",arrayOf("CODE","D"), arrayOf(reasoningRevBean.code.toString(),reasoningRevBean.d))
        LogUtil.d("insertReasoningRevTB--->${reasoningRevBean.code},date--->${reasoningRevBean.d}")
        if (!isExit){
            val insert = reasoningRevBean.insertTB("Reasoning")
            db.execSQL(insert)
        }
        return isExit
    }

    fun insertReasoningAllTB(reasoningRevBean: ReasoningRevBean,is50: Boolean): Boolean {
        switchDBName(Datas.REV_RESONING_DB)
        val tbName = if (is50) "All_Reasoning_50" else "All_Reasoning_30"
        val createSql = reasoningRevBean.createAllTB(tbName)
        db.execSQL(createSql)
        val isExit = queryIsExsitByCodeAndCustomColumn(tbName,arrayOf("CODE","D"), arrayOf(reasoningRevBean.code.toString(),reasoningRevBean.d))
        LogUtil.d("insertReasoningAllTB--->${reasoningRevBean.code},date--->${reasoningRevBean.d},$is50,${reasoningRevBean.p}")
        if (!isExit){
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
            getReasoningRevBeanByCusorAndTb(cursor, tbName, list)
        }
        return list

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
        allOmM: Float,
        is50: Boolean,
        D_T: Int,
        allReasoningBean: ReasoningRevBean
    ): ArrayList<ReasoningAllJudgeBean> {
        switchDBName(Datas.REV_RESONING_DB)
        val list = ArrayList<ReasoningAllJudgeBean>()
        val tbName = if (is50) "All_50" else "All_30"
        val querySql = allReasoningBean.getF_TSql(D_T)
        LogUtil.d("$D_T querySql-->$querySql")
        if (tabbleIsExist(tbName)) {
            val cursor =
                db.rawQuery(" SELECT * FROM $tbName WHERE D_T=$D_T AND OM_M_X<=$allOmM AND OM_M_D>=$allOmM $querySql", null)
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


                    reasoningAllJudgeBean.oM_OC_D = cursor.getFloat(cursor.getColumnIndex("OM_OC_D"))
                    reasoningAllJudgeBean.oM_OP_D = cursor.getFloat(cursor.getColumnIndex("OM_OP_D"))
                    reasoningAllJudgeBean.oM_OL_D = cursor.getFloat(cursor.getColumnIndex("OM_OL_D"))
                    reasoningAllJudgeBean.oC_OP_D = cursor.getFloat(cursor.getColumnIndex("OC_OP_D"))
                    reasoningAllJudgeBean.oC_OL_D = cursor.getFloat(cursor.getColumnIndex("OC_OL_D"))
                    reasoningAllJudgeBean.oP_OL_D = cursor.getFloat(cursor.getColumnIndex("OP_OL_D"))
                    reasoningAllJudgeBean.m_C_D = cursor.getFloat(cursor.getColumnIndex("M_C_D"))
                    reasoningAllJudgeBean.m_P_D = cursor.getFloat(cursor.getColumnIndex("M_P_D"))
                    reasoningAllJudgeBean.m_L_D = cursor.getFloat(cursor.getColumnIndex("M_L_D"))
                    reasoningAllJudgeBean.c_P_D = cursor.getFloat(cursor.getColumnIndex("C_P_D"))
                    reasoningAllJudgeBean.c_L_D = cursor.getFloat(cursor.getColumnIndex("C_L_D"))
                    reasoningAllJudgeBean.p_L_D = cursor.getFloat(cursor.getColumnIndex("P_L_D"))
                    reasoningAllJudgeBean.oM_OC_X = cursor.getFloat(cursor.getColumnIndex("OM_OC_X"))
                    reasoningAllJudgeBean.oM_OP_X = cursor.getFloat(cursor.getColumnIndex("OM_OP_X"))
                    reasoningAllJudgeBean.oM_OL_X = cursor.getFloat(cursor.getColumnIndex("OM_OL_X"))
                    reasoningAllJudgeBean.oC_OP_X = cursor.getFloat(cursor.getColumnIndex("OC_OP_X"))
                    reasoningAllJudgeBean.oC_OL_X = cursor.getFloat(cursor.getColumnIndex("OC_OL_X"))
                    reasoningAllJudgeBean.oP_OL_X = cursor.getFloat(cursor.getColumnIndex("OP_OL_X"))
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
                    LogUtil.d("$tbName $D_T,size:${list.size},reasoningAllJudgeBean-->\n${GsonHelper.toJson(reasoningAllJudgeBean)}")
                    cursor.moveToNext()
                }
                cursor.close()
            }

        }
        return list
    }

    fun getReasoningInitAllJudgeResult(
        tbName: String,
        bean: ReasoningRevBean
    ): ArrayList<ReasoningRevBean> {

        switchDBName(Datas.REV_RESONING_DB)
        val list = ArrayList<ReasoningRevBean>()

        val endJudgeStr =getJudgeEndStr(bean)
        if (tabbleIsExist(tbName)) {
            val querySQL = "F36_T = ${bean.f36_T} AND F30_T = ${bean.f30_T} AND F25_T = ${bean.f25_T} AND F20_T = ${bean.f20_T} AND F15_T = ${bean.f15_T} AND  F10_T = ${bean.f10_T} AND F05_T = ${bean.f05_T} AND  F03_T = ${bean.f03_T}  ${endJudgeStr}"

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
        val endJudgeStr =getJudgeEndStr(bean)
        if (tabbleIsExist(tbName)) {
            val querySQL = "F36_T = ${bean.f36_T} AND F30_T = ${bean.f30_T} AND F25_T = ${bean.f25_T} AND F20_T = ${bean.f20_T} AND F15_T = ${bean.f15_T} AND  F10_T = ${bean.f10_T} AND F05_T = ${bean.f05_T} " +
                    "AND  F03_T = ${bean.f03_T} ${endJudgeStr}"
            val query1SQL = "F36_T = ${bean.f36_T} AND F30_T = ${bean.f30_T} AND F25_T = ${bean.f25_T} AND F20_T = ${bean.f20_T} AND F15_T = ${bean.f15_T} AND  F10_T = ${bean.f10_T} AND F05_T = ${bean.f05_T} " +
                    "AND  F03_T = ${bean.f03_T} ${endJudgeStr}" +
                    " AND  MA1 = ${bean.mA_1} AND  MA3 = ${bean.mA_3} "
            val query2SQL = "F36_T = ${bean.f36_T} AND F30_T = ${bean.f30_T} AND F25_T = ${bean.f25_T} AND F20_T = ${bean.f20_T} AND F15_T = ${bean.f15_T} AND  F10_T = ${bean.f10_T} AND F05_T = ${bean.f05_T} " +
                    "AND  F03_T = ${bean.f03_T} ${endJudgeStr}" +
                    " AND  MA1 = ${bean.mA_1} AND  MA3 = ${bean.mA_3}  AND  MA5 = ${bean.mA_5} "
            getReasoningPList(tbName, querySQL, list)
            getReasoningPList(tbName, query1SQL, list1)
            getReasoningPList(tbName, query2SQL, list2)
        }
        list.sortReasoningRevBeanByP()
        list1.sortReasoningRevBeanByP()
        list2.sortReasoningRevBeanByP()
        return Triple(list,list1,list2)
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
        getReasoningRevBeanByCusorAndTb(cursor, tbName, list)
    }

    private fun getReasoningRevBeanByCusorAndTb(
        cursor: Cursor?,
        tbName: String,
        list: ArrayList<ReasoningRevBean>
    ) {
        if (null != cursor && cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {

                val bean = ReasoningRevBean()
                val CODE = cursor.getInt(cursor.getColumnIndex("CODE"))
                val D = cursor.getString(cursor.getColumnIndex("D"))
                val N = cursor.getString(cursor.getColumnIndex("N"))
                val D_D = cursor.getString(cursor.getColumnIndex("D_D"))
                val P = cursor.getFloat(cursor.getColumnIndex("P"))
                val MP = cursor.getFloat(cursor.getColumnIndex("MP"))
                val LP = cursor.getFloat(cursor.getColumnIndex("LP"))
                val AFTER_O_P = cursor.getFloat(cursor.getColumnIndex("AFTER_O_P"))
                val AFTER_C_P = cursor.getFloat(cursor.getColumnIndex("AFTER_C_P"))
                if (!tbName.equals("Reasoning")) {
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
    //                    bean.fitlertype = FITLERTYPE
                list.add(bean)
                cursor.moveToNext()
            }
            cursor.close()
        }
    }

    fun getNullDDFromReasoningTB(tbName:String): ArrayList<ReasoningRevBean> {
        switchDBName(Datas.REV_RESONING_DB)

        val cursor =
            db.rawQuery(" SELECT * FROM $tbName WHERE D_D ='null'", null)

        val list = ArrayList<ReasoningRevBean>()
        getReasoningRevBeanByCusorAndTb(cursor,tbName,list)
        return list
    }

    fun updateReasoning(tbName:String,bean:ReasoningRevBean) {
        val sql = "UPDATE $tbName SET D_D = '${bean.d_D}',MP = ${bean.mp},P = ${bean.p} ,LP = ${bean.lp} ,AFTER_O_P = ${bean.after_O_P} ,AFTER_C_P = ${bean.after_C_P} WHERE CODE=${bean.code} AND D = '${bean.d}'"
        LogUtil.d("$sql")
        db.execSQL(sql)
    }

}