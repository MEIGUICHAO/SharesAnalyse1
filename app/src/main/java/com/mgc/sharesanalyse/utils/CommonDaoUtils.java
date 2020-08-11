package com.mgc.sharesanalyse.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.mgc.sharesanalyse.base.Datas;
import com.mgc.sharesanalyse.entity.DaoSession;
import com.mgc.sharesanalyse.entity.StocksBean;
import com.mgc.sharesanalyse.entity.StocksBeanDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.validation.Schema;

public class CommonDaoUtils<T>
{
    private static final String TAG = CommonDaoUtils.class.getSimpleName();

    private DaoSession daoSession;
    private Class<T> entityClass;
    private AbstractDao<T, Long> entityDao;

    public CommonDaoUtils(Class<T> pEntityClass, AbstractDao<T, Long> pEntityDao)
    {
        DaoManager mManager = DaoManager.getInstance();
        daoSession = mManager.getDaoSession();
        entityClass = pEntityClass;
        entityDao = pEntityDao;
    }

    /**
     * 插入记录，如果表未创建，先创建表
     *
     * @param pEntity
     * @return
     */
    public boolean insert(T pEntity)
    {
        boolean flag = entityDao.insert(pEntity) == -1 ? false : true;
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param pEntityList
     * @return
     */
    public boolean insertMulti(final List<T> pEntityList)
    {
        try
        {
            daoSession.runInTx(new Runnable()
            {
                @Override
                public void run()
                {
                    for (T meizi : pEntityList)
                    {
                        daoSession.insertOrReplace(meizi);
                    }
                }
            });
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改一条数据
     *
     * @param pEntity
     * @return
     */
    public boolean update(T pEntity)
    {
        try
        {
            daoSession.update(pEntity);
            return true;
        }
        catch (Exception e)
        {
            LogUtil.d("insertTableStringBuilder e:" + e.toString());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除单条记录
     *
     * @param pEntity
     * @return
     */
    public boolean delete(T pEntity)
    {
        try
        {
            //按照id删除
            daoSession.delete(pEntity);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll()
    {
        try
        {
            //按照id删除
            daoSession.deleteAll(entityClass);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<T> queryAll()
    {
        return daoSession.loadAll(entityClass);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public T queryById(long key)
    {
        return daoSession.load(entityClass, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<T> queryByNativeSql(String sql, String[] conditions)
    {
        return daoSession.queryRaw(entityClass, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public List<T> queryByQueryBuilder(WhereCondition cond, WhereCondition... condMore)
    {
        QueryBuilder<T> queryBuilder = daoSession.queryBuilder(entityClass);
        return queryBuilder.where(cond, condMore).list();
    }



    public static void classifyTables(Database db,String curTableName) {
        DaoConfig daoConfig = new DaoConfig(db, StocksBeanDao.class);

        String divider = "";
        String tableName = daoConfig.tablename;
        ArrayList<String> properties = new ArrayList<>();

        StringBuilder createTableStringBuilder = new StringBuilder();

        createTableStringBuilder.append("CREATE TABLE ").append(curTableName).append(" (");
        LogUtil.d("insertTableStringBuilder classifyTables length:" + daoConfig.properties.length);
        for(int j = 0; j < daoConfig.properties.length; j++) {
            String columnName = daoConfig.properties[j].columnName;

            if (!columnName.equals("_id")) {
                if(MigrationHelper.getColumns(db, tableName).contains(columnName)) {
                    properties.add(columnName);

                    String type = null;

                    try {
                        type = MigrationHelper.getTypeByClass(daoConfig.properties[j].type);
                    } catch (Exception exception) {

                    }

                    createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);

                    if(daoConfig.properties[j].primaryKey) {
                        createTableStringBuilder.append(" PRIMARY KEY");
                    }

                    divider = ",";
                }
            }
        }
        createTableStringBuilder.append(");");
        if (!tabbleIsExist(curTableName)) {
            db.execSQL(createTableStringBuilder.toString());
        }
        LogUtil.d("insertTableStringBuilder classifyTables tabbleIsExist:" + tabbleIsExist(curTableName));

        StringBuilder insertTableStringBuilder = new StringBuilder();

        insertTableStringBuilder.append("INSERT INTO ").append(curTableName).append(" (");
        insertTableStringBuilder.append(TextUtils.join(",", properties));
        insertTableStringBuilder.append(") SELECT ");
        insertTableStringBuilder.append(TextUtils.join(",", properties));
        insertTableStringBuilder.append(" FROM ").append(tableName).append(";");
        LogUtil.d("classifyTables insertTableStringBuilder:"+insertTableStringBuilder);
        db.execSQL(insertTableStringBuilder.toString());
        LogUtil.d("classifyTables insertTableStringBuilder!!!:"+insertTableStringBuilder);

    }

    /**
     * 判断某张表是否存在
     * @return
     */
    public static boolean tabbleIsExist(String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DaoManager.getsHelper().getReadableDatabase();
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"+tableName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }




}
