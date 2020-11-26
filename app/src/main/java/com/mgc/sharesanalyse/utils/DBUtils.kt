package com.mgc.sharesanalyse.utils

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
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
        ddbean.sizeBean.date = date.replace(Datas.sdd,"")
        if (!queryItemIsExsitByCode(dbName, ddbean.code)) {
            val insertSqlStr = "INSERT INTO $dbName" +
                    "(CODE,NAME,ALLSIZE,DATE,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,L01S,G5000M,G1000M,G500M,G100M,ALLSIZESDJ,M100SDJ,M50SDJ,M30SDJ,M10SDJ,M5SDJ,M1SDJ,M05SDJ,M01SDJ)" +
                    " VALUES${ddbean.toInsertSqlSumValues(ddbean,ddbean.allsize)}"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        } else {
            val ts = DateUtils.parse(date, FormatterEnum.YYYYMMDD)
            val ts2 = DateUtils.parse(sumDDBean!!.sizeBean!!.date, FormatterEnum.YYYYMMDD)
            LogUtil.d("ts:$date,ts2:${sumDDBean.sizeBean!!.date}")
            if (ts > ts2) {
                val sql = "UPDATE $dbName SET ${sumDDBean.toUpdateSqlSumValues(ddbean)},DATE =${date.replace(Datas.sdd,"")}  WHERE CODE=${ddbean.code}"
                LogUtil.d("updateSqlStr:$sql")
                db.execSQL(sql)
            }
        }
    }

    fun setSDDPercent(dbName: String,percent:Float,code: String) {
        val sql = "UPDATE $dbName SET PERCENT = ${percent}  WHERE CODE=${code}"
        LogUtil.d("setSDDPercent:$sql")
        db.execSQL(sql)
    }

    fun setSDDMaxPercent(dbName: String,percent:Float,maxdate: String,code: String) {
        val sql = "UPDATE $dbName SET MAXPER = ${percent},MPD = ${maxdate}  WHERE CODE=${code}"
        LogUtil.d("setSDDMaxPercent:$sql")
        db.execSQL(sql)
    }

    fun setSDDLowPercent(dbName: String, percent:Float, lowdate: String, code: String) {
        val sql = "UPDATE $dbName SET LOWPER = ${percent},LPD = ${lowdate}  WHERE CODE=${code}"
        LogUtil.d("setSDDMaxPercent:$sql")
        db.execSQL(sql)
    }

//    (CODE,NAME,,,,,,,,,,,,,,
//    ,,,,,,,,)
//    (1,'平安银行',ALLSIZE 2074,M100S 0,M50S 2,M30S 0,M10S 6,M5S 25,M1S 455,M05S 527,M01S 1059,G5000M 13675.268635,G1000M 22820.396535000003,G500M 40074.291105000004,G100M 122086.51915499999,
//    ALLSIZESDJ 20201015:2074,M100SDJ ,M50SDJ 20201015:2,M30SDJ ,M10SDJ 20201015:6,M5SDJ ,M1SDJ 20201015:455,M05SDJ 20201015:527,M01SDJ 20201015:1059)

    fun insertHHq2DateTable(dbName: String, hisHqBean: PricesHisGDBean) {
        createHHqTable(dbName)
        if (!queryHHqIsExsitByCode(dbName, hisHqBean.code.toInt().toString())) {
            var insertSqlStr = "INSERT INTO $dbName" +
                    "(CODE,JSON,MINEINFO)" +
                    " VALUES(${hisHqBean.code},'${hisHqBean.json}','${hisHqBean.mineInfo}')"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        }
    }

    fun insertEmptyDealDetail2DateTable(dbName: String, dealDetailTableBean: DealDetailTableBean) {
        createDealDetailTable(dbName)
        if (!queryItemIsExsitByCode(dbName, dealDetailTableBean.code)) {
            var insertSqlStr = "INSERT INTO $dbName" +
                    "(CODE,NAME)" +
                    " VALUES(${dealDetailTableBean.code},${dealDetailTableBean.name})"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        }
    }

    fun dropTable(dbName: String) {
        val dropTable = "DROP TABLE IF EXISTS $dbName"
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


    fun queryOPByCHDD(code: String, isAll: Boolean,chddDateYM:String):Float {
        val pathList = FileUtil.getFileNameList(Datas.DBPath)
        var op = 0.toFloat()
        if (isAll) {
            val mList = ArrayList<String>()
            pathList.forEach {
                if (it.contains("CHDD")&&!it.contains("journal")) {
                    mList.add(it.replace("SZ_CHDD_","").replace("SH_CHDD_","").replace("CY_CHDD_",""))
                }
            }
            mList.sortStringDateAsc(FormatterEnum.YYMM)
            if (mList.size > 0) {
                switchDBName(code.toCodeHDD(mList[0],FormatterEnum.YYMM))
                val tbName = Datas.CHDD + code
                op = queryFirstOPByCodeHDD(tbName)
            }
        } else {
            val dbName = code.toCodeHDD(chddDateYM,FormatterEnum.YYMM)
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
            var cursor = db.rawQuery("SELECT * FROM $dbName order by _ID desc",null)
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
                dealDetailAmountSizeBean.gt1000Mamount =G1000M!!
                dealDetailAmountSizeBean.gt500Mamount = G500M!!
                dealDetailAmountSizeBean.gt100Mamount =G100M!!

                dealDetailAmountSizeBean.allsizeSDJ =ALLSIZESDJ
                dealDetailAmountSizeBean.m100SDJ =M100SDJ
                dealDetailAmountSizeBean.m50SDJ =M50SDJ
                dealDetailAmountSizeBean.m30SDJ =M30SDJ
                dealDetailAmountSizeBean.m10SDJ =M10SDJ
                dealDetailAmountSizeBean.m5SDJ =M5SDJ
                dealDetailAmountSizeBean.m1SDJ =M1SDJ
                dealDetailAmountSizeBean.m05SDJ =M05SDJ
                dealDetailAmountSizeBean.m01SDJ =M01SDJ
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
                        ",K_J TEXT,Shape_J TEXT,K_TR_J TEXT,GAP_J TEXT, P_AUTR_J TEXT,P_DA_J TEXT,P_PP_J TEXT,P_MA_J TEXT,SpePP_J TEXT,DA_J TEXT,MS_J TEXT);"
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
            var cursor = db.rawQuery("SELECT * FROM $dbName",null)
            if (null != cursor && cursor.moveToLast()) {
                DATE = cursor.getString(cursor.getColumnIndex("DATE"))
            }
            cursor.close()
        }
        return DATE
    }

    fun queryDateInSDDDByDbName(dbName: String,code: String): String {
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
                if (name.contains("DD_")&&mCursor.count==0) {
                    dropTable(name)
                }
                if (name.contains("DD_")) {
                    val date = name.replace("DD_","")
                    val ts = DateUtils.parse(date,FormatterEnum.YYYYMMDD)
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
                val sql = "UPDATE $dbName SET ${shddBean.toUpdateSqlSumValues()}  WHERE C=${shddBean.c}"
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

    fun queryFirstOPByCodeHDD(tbName: String): Float {
        var op = 0.toFloat()
        if (tabbleIsExist(tbName)) {
            val cursor = db.rawQuery("SELECT * FROM $tbName order by _ID asc",null)
            if (null != cursor && cursor.moveToFirst()) {
                op = cursor.getFloat(cursor.getColumnIndex("OP"))
                var Date = cursor.getFloat(cursor.getColumnIndex("DATE"))
                while (op==0.toFloat()) {
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

    fun insertOrUpdateCheckFilterTable(
        dbName: String,
        date: String,
        checkFilterBean: CheckFilterBean
    ) {
        switchDBName(Datas.DBFilter + DateUtils.changeFormatter(DateUtils.parse(date,FormatterEnum.YYYYMMDD),FormatterEnum.YYMM))
        createCheckFilterTable(dbName)
        if (!queryDataIsExsitByCode(dbName, checkFilterBean.code)) {
            val insertSqlStr = "INSERT INTO $dbName" +
                    "(CODE,CHECKSIZE,DATE)" +
                    " VALUES${checkFilterBean.toInsert()}"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        } else {
            val sql = "UPDATE $dbName SET ${checkFilterBean.toUpdateSqlSumValues()}  WHERE CODE=${checkFilterBean.code}"
            LogUtil.d("updateSqlStr:$sql")
            db.execSQL(sql)
        }
    }

    private fun createCheckFilterTable(dbName: String) {
        if (!tabbleIsExist(dbName)) {
            val sqlStr =
                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT, CHECKSIZE INTEGER, DATE INTEGER);"
            db.execSQL(sqlStr)
        }
    }


    fun queryCheckFilterByCode(dbName: String, code: String, date: String): CheckFilterBean? {
        switchDBName(Datas.DBFilter + date)
        var checkFilterBean: CheckFilterBean? = null
        if (tabbleIsExist(dbName)) {
            var cursor =
                db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code.toInt().toString()))
            LogUtil.d("cursor:${cursor.count}")
            if (null != cursor && cursor.moveToFirst()) {
                checkFilterBean = CheckFilterBean()
                val code = cursor.getString(cursor.getColumnIndex("CODE"))
                val checkSize = cursor.getInt(cursor.getColumnIndex("CHECKSIZE"))
                val date = cursor.getInt(cursor.getColumnIndex("DATE"))
                checkFilterBean.code = code
                checkFilterBean.checkSize = checkSize
                checkFilterBean.date = date

            }
            cursor.close()
        }
        return checkFilterBean
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
        var list:ArrayList<CodeHDDBean>? = null
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
                    val SpePP_J = cursor.getString(cursor.getColumnIndex("SpePP_J"))
                    val DA_J = cursor.getString(cursor.getColumnIndex("DA_J"))
                    val K_J = cursor.getString(cursor.getColumnIndex("K_J"))
                    val Shape_J = cursor.getString(cursor.getColumnIndex("Shape_J"))
                    val K_TR_J = cursor.getString(cursor.getColumnIndex("K_TR_J"))
                    val GAP_J = cursor.getString(cursor.getColumnIndex("GAP_J"))
                    val MS_J = cursor.getString(cursor.getColumnIndex("MS_J"))

                    codeHDDBean.name = NAME
                    codeHDDBean.date = DATE
                    codeHDDBean.op = OP
                    codeHDDBean.cp = CP
                    codeHDDBean.pp = PP
                    codeHDDBean.p = P
                    codeHDDBean.aup = AUP
                    codeHDDBean.tr = TR

                    if (!GAP_J.isNullOrEmpty()) {
                        codeHDDBean.gaP_J = GsonHelper.parse(GAP_J,GapJsonBean::class.java)
                    }
                    if (!K_TR_J.isNullOrEmpty()) {
                        codeHDDBean.k_TR_J = GsonHelper.parse(K_TR_J,K_TR_Json::class.java)
                    }
                    if (!Shape_J.isNullOrEmpty()) {
                        codeHDDBean.shape_J = GsonHelper.parse(Shape_J,ShapeJsonBean::class.java)
                    }
                    if (!K_J.isNullOrEmpty()) {
                        codeHDDBean.k_J = GsonHelper.parse(K_J,KJsonBean::class.java)
                    }

                    if (!p_autr_j.isNullOrEmpty()) {
                        codeHDDBean.p_autr_j = GsonHelper.parse(p_autr_j, CodeHDDBean.P_AUTR_J::class.java)
                    }
                    if (!P_DA_J.isNullOrEmpty()) {
                        codeHDDBean.p_DA_J = GsonHelper.parse(P_DA_J, CodeHDDBean.P_DA_J::class.java)
                    }
                    if (!P_PP_J.isNullOrEmpty()) {
                        codeHDDBean.p_PP_J = GsonHelper.parse(P_PP_J, CodeHDDBean.P_PP_J::class.java)
                    }
                    if (!P_MA_J.isNullOrEmpty()) {
                        codeHDDBean.p_MA_J = GsonHelper.parse(P_MA_J, CodeHDDBean.P_MA_J::class.java)
                    }
                    if (!SpePP_J.isNullOrEmpty()) {
                        codeHDDBean.spePP_J = GsonHelper.parse(SpePP_J, CodeHDDBean.SpePP_J::class.java)
                    }
                    if (!DA_J.isNullOrEmpty()) {
                        codeHDDBean.dA_J = GsonHelper.parse(DA_J, CodeHDDBean.DA_J::class.java)
                    }
                    if (!MS_J.isNullOrEmpty()) {
                        codeHDDBean.mS_J = GsonHelper.parse(MS_J, CodeHDDBean.MS_J::class.java)
                    }
                    list.add(codeHDDBean)
                    cursor.moveToNext()
                }

            }
            cursor.close()
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
            val sql = "UPDATE $tbName SET ${filterCodeHDDBean.toUpdateSqlSumValues()}  WHERE CODE=${filterCodeHDDBean.code}"
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

    fun queryFilterCodeHddBeanByCode(dbName: String, code: String,date: String): FilterCodeHDDBean? {
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

}