package com.mgc.sharesanalyse.utils

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mgc.sharesanalyse.base.json2Array
import com.mgc.sharesanalyse.entity.DealDetailAmountSizeBean
import com.mgc.sharesanalyse.entity.DealDetailTableBean


object DBUtils {

     var db: SQLiteDatabase

    init {
        db = DaoManager.getDB()
    }

    fun insertDealDetail2DateTable(dbName:String,dealDetailTableBean: DealDetailTableBean) {
        createDealDetailTable(dbName)
        if ()


    }

    fun queryDealDetailIsExsitByCode(dbName: String, code: String):Boolean {
        createDealDetailTable(dbName)
        var cursor= db.rawQuery("select name from $dbName where code=?", arrayOf(code))
        return null!=cursor
    }

    fun queryDealDetailByCode(dbName: String, code: String): DealDetailTableBean? {
        createDealDetailTable(dbName)
        var dealDetailTableBean: DealDetailTableBean? = null
        var cursor= db.rawQuery("select name from $dbName where code=?", arrayOf(code))
        if (null != cursor && cursor.moveToFirst()) {
            dealDetailTableBean = DealDetailTableBean()
            var dealDetailAmountSizeBean = DealDetailAmountSizeBean()
            val code = cursor.getString(cursor.getColumnIndex("code"))
            val allsize = cursor.getInt(cursor.getColumnIndex("allsize"))
            val percent = cursor.getDouble(cursor.getColumnIndex("percent"))
            val m100s = cursor.getInt(cursor.getColumnIndex("100mS"))
            val m50s = cursor.getInt(cursor.getColumnIndex("50mS"))
            val m30s = cursor.getInt(cursor.getColumnIndex("30mS"))
            val m10s = cursor.getInt(cursor.getColumnIndex("10mS"))
            val m5s = cursor.getInt(cursor.getColumnIndex("5mS"))
            val m1s = cursor.getInt(cursor.getColumnIndex("1mS"))
            val m05s = cursor.getInt(cursor.getColumnIndex("05mS"))
            val m01s = cursor.getInt(cursor.getColumnIndex("01mS"))
            val m100j = cursor.getString(cursor.getColumnIndex("100mJ"))
            val m50j = cursor.getString(cursor.getColumnIndex("50mJ"))
            val m30j = cursor.getString(cursor.getColumnIndex("30mJ"))
            val m10j = cursor.getString(cursor.getColumnIndex("10mJ"))
            val m5j = cursor.getString(cursor.getColumnIndex("5mJ"))
            val m1j = cursor.getString(cursor.getColumnIndex("1mJ"))
            val m05j = cursor.getString(cursor.getColumnIndex("05mJ"))
            val m01j = cursor.getString(cursor.getColumnIndex("01mJ"))
            dealDetailAmountSizeBean.m100Size = m100s
            dealDetailAmountSizeBean.m50Size = m50s
            dealDetailAmountSizeBean.m30Size = m30s
            dealDetailAmountSizeBean.m10Size = m10s
            dealDetailAmountSizeBean.m5Size = m5s
            dealDetailAmountSizeBean.m1Size = m1s
            dealDetailAmountSizeBean.m05Size = m05s
            dealDetailAmountSizeBean.m01Size = m01s

            dealDetailAmountSizeBean.m100List = m100j.json2Array()
            dealDetailAmountSizeBean.m50List = m50s
            dealDetailAmountSizeBean.m30List = m30s
            dealDetailAmountSizeBean.m10List = m10s
            dealDetailAmountSizeBean.m5List = m5s
            dealDetailAmountSizeBean.m1List = m1s
            dealDetailAmountSizeBean.m05List = m05s
            dealDetailAmountSizeBean.m01List = m01s

            dealDetailTableBean.code = code
            dealDetailTableBean.allsize = allsize
            dealDetailTableBean.percent  = percent
            dealDetailTableBean.sizeBean = dealDetailAmountSizeBean

        }

        return null
    }

    fun createDealDetailTable(dbName:String) {
        if (!tabbleIsExist(dbName)) {
            val sqlStr = "create table if not exists $dbName(_id integer primary key autoincrement, code text, allsize integer, percent integer" +
                    ", 100mS integer, 50mS integer, 30mS integer, 10mS integer, 5mS integer, 1mS integer, 05mS integer, 01mS integer" +
                    ", 100mJ text, 50mJ text, 30mJ text, 10mJ text, 5mJ text, 1mJ text, 05mJ text, 01mJ text);"
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

}