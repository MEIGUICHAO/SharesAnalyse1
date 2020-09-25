package com.mgc.sharesanalyse.utils

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mgc.sharesanalyse.base.json2Array
import com.mgc.sharesanalyse.entity.DealDetailAmountSizeBean
import com.mgc.sharesanalyse.entity.DealDetailTableBean
import java.text.DecimalFormat


object DBUtils {

     var db: SQLiteDatabase

    init {
        db = DaoManager.getDB()
    }

    fun insertDealDetail2DateTable(dbName:String,dealDetailTableBean: DealDetailTableBean) {
        createDealDetailTable(dbName)
        if (!queryDealDetailIsExsitByCode(dbName,dealDetailTableBean.code)) {
            var insertSqlStr = "INSERT INTO $dbName" +
                    "(CODE,NAME,ALLSIZE,PERCENT,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,M100J,M50J,M30J,M10J,M5J,M1J)" +
                    " VALUES${dealDetailTableBean.toSqlValues()}"
            LogUtil.d("insertSqlStr:$insertSqlStr")
            db.execSQL(insertSqlStr)
        }
    }

    fun insertEmptyDealDetail2DateTable(dbName:String,dealDetailTableBean: DealDetailTableBean) {
        createDealDetailTable(dbName)
        if (!queryDealDetailIsExsitByCode(dbName,dealDetailTableBean.code)) {
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

    fun queryDealDetailIsExsitByCode(dbName: String, code: String):Boolean {
        createDealDetailTable(dbName)
        LogUtil.d("code:$code")
        var cursor= db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code.toInt().toString()))
        var isexsit = cursor.count>0
        LogUtil.d("isexsit:$isexsit cursor.count:${cursor.count}")
        cursor.close()
        return isexsit
    }

    fun queryDealDetailByCode(dbName: String, code: String): DealDetailTableBean? {
        createDealDetailTable(dbName)
        var dealDetailTableBean: DealDetailTableBean? = null
        var cursor = db.rawQuery("SELECT * FROM $dbName WHERE CODE =?", arrayOf(code.toInt().toString()))
        if (null != cursor && cursor.moveToFirst()) {
            dealDetailTableBean = DealDetailTableBean()
            var dealDetailAmountSizeBean = DealDetailAmountSizeBean()
            val code = cursor.getString(cursor.getColumnIndex("CODE"))
            val name = cursor.getString(cursor.getColumnIndex("NAME"))
            val allsize = cursor.getInt(cursor.getColumnIndex("ALLSIZE"))
            val percent = cursor.getDouble(cursor.getColumnIndex("PERCENT"))
            val m100s = cursor.getInt(cursor.getColumnIndex("M100S"))
            val m50s = cursor.getInt(cursor.getColumnIndex("M50S"))
            val m30s = cursor.getInt(cursor.getColumnIndex("M30S"))
            val m10s = cursor.getInt(cursor.getColumnIndex("M10S"))
            val m5s = cursor.getInt(cursor.getColumnIndex("M5S"))
            val m1s = cursor.getInt(cursor.getColumnIndex("M1S"))
            val m05s = cursor.getInt(cursor.getColumnIndex("M05S"))
            val m01s = cursor.getInt(cursor.getColumnIndex("M01S"))
            val m100j:String? = cursor.getString(cursor.getColumnIndex("M100J"))
            val m50j:String?  = cursor.getString(cursor.getColumnIndex("M50J"))
            val m30j:String?  = cursor.getString(cursor.getColumnIndex("M30J"))
            val m10j:String?  = cursor.getString(cursor.getColumnIndex("M10J"))
            val m5j:String?  = cursor.getString(cursor.getColumnIndex("M5J"))
            val m1j:String?  = cursor.getString(cursor.getColumnIndex("M1J"))
            dealDetailAmountSizeBean.m100Size = m100s
            dealDetailAmountSizeBean.m50Size = m50s
            dealDetailAmountSizeBean.m30Size = m30s
            dealDetailAmountSizeBean.m10Size = m10s
            dealDetailAmountSizeBean.m5Size = m5s
            dealDetailAmountSizeBean.m1Size = m1s
            dealDetailAmountSizeBean.m05Size = m05s
            dealDetailAmountSizeBean.m01Size = m01s

            dealDetailAmountSizeBean.m100List = m100j?.json2Array(DealDetailAmountSizeBean.M100::class.java)
            dealDetailAmountSizeBean.m50List = m50j?.json2Array(DealDetailAmountSizeBean.M50::class.java)
            dealDetailAmountSizeBean.m30List = m30j?.json2Array(DealDetailAmountSizeBean.M30::class.java)
            dealDetailAmountSizeBean.m10List = m10j?.json2Array(DealDetailAmountSizeBean.M10::class.java)
            dealDetailAmountSizeBean.m5List = m5j?.json2Array(DealDetailAmountSizeBean.M5::class.java)
            dealDetailAmountSizeBean.m1List = m1j?.json2Array(DealDetailAmountSizeBean.M1::class.java)

            dealDetailTableBean.code = code
            dealDetailTableBean.name = name
            dealDetailTableBean.allsize = allsize
            dealDetailTableBean.percent  = percent
            dealDetailTableBean.sizeBean = dealDetailAmountSizeBean

        }
        cursor.close()
        return dealDetailTableBean
    }

    fun createDealDetailTable(dbName:String) {
//        "(CODE,ALLSIZE,PERCENT,M100S,M50S,M30S,M10S,M5S,M1S,M05S,M01S,M100J,M50J,M30J,M10J,M5J,M1J,M05J,M01J)"
        if (!tabbleIsExist(dbName)) {
            val sqlStr = "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT, NAME TEXT, ALLSIZE INTEGER, PERCENT INTEGER" +
                    ", M100S INTEGER, M50S INTEGER, M30S INTEGER, M10S INTEGER, M5S INTEGER, M1S INTEGER, M05S INTEGER, M01S INTEGER" +
                    ", M100J TEXT, M50J TEXT, M30J TEXT, M10J TEXT, M5J TEXT, M1J TEXT, M05J TEXT, M01J TEXT);"
            db.execSQL(sqlStr)
        }
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
                "SELECT COUNT(*) AS C FROM SQLITE_MASTER  WHERE TYPE ='TABLE' AND NAME ='" + tableName.trim { it <= ' ' } + "' "
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

}