package com.mgc.sharesanalyse.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mgc.sharesanalyse.base.App;
import com.mgc.sharesanalyse.entity.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

public class CommonDaoUtils<T> {
    private static final String TAG = CommonDaoUtils.class.getSimpleName();

    private DaoSession daoSession;
    private Class<T> entityClass;
    private AbstractDao<T, Long> entityDao;
    public static Database db;

    public CommonDaoUtils(Class<T> pEntityClass, AbstractDao<T, Long> pEntityDao) {
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
    public boolean insert(T pEntity) {
        boolean flag = entityDao.insert(pEntity) == -1 ? false : true;
        return flag;
    }

    /**
     * 插入记录，如果表未创建，先创建表
     *
     * @param pEntity
     * @return
     */
    public void insertInTx(T... pEntity) {
        entityDao.insertInTx(pEntity);
    }

    /**
     * 插入记录，如果表未创建，先创建表
     *
     * @param pEntity
     * @return
     */
    public void updateInTx(T pEntity) {
        entityDao.updateInTx(pEntity);
    }

    public boolean updateOrInsertById(T pEntity,Long id) {
        T t = queryById(id);
        if (null == t) {
            return insert(pEntity);
        } else {
            return update(pEntity);
        }
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param pEntityList
     * @return
     */
    public boolean insertMulti(final List<T> pEntityList) {
        try {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T meizi : pEntityList) {
                        daoSession.insertOrReplace(meizi);
                    }
                }
            });
            return true;
        } catch (Exception e) {
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
    public boolean update(T pEntity) {
        try {
            daoSession.update(pEntity);
            return true;
        } catch (Exception e) {
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
    public boolean delete(T pEntity) {
        try {
            //按照id删除
            daoSession.delete(pEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        try {
            //按照id删除
            daoSession.deleteAll(entityClass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<T> queryAll() {
        return daoSession.loadAll(entityClass);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public T queryById(long key) {
        return daoSession.load(entityClass, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<T> queryByNativeSql(String sql, String[] conditions) {
        return daoSession.queryRaw(entityClass, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public List<T> queryByQueryBuilder(WhereCondition cond, WhereCondition... condMore) {

        QueryBuilder<T> queryBuilder = daoSession.queryBuilder(entityClass);
        return queryBuilder.where(cond, condMore).list();
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public T queryByCode(WhereCondition cond, WhereCondition... condMore) {
        QueryBuilder<T> queryBuilder = daoSession.queryBuilder(entityClass);
        List<T> list = queryBuilder.where(cond, condMore).list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }



    public static void dropTable(Database db, String tableName) {
        String dropTable = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(dropTable);
    }

    public static void renameTable(Database db, String tableName,String newName) {
        if (tabbleIsExist(tableName)) {
            String dropTable = "ALTER TABLE " + tableName + " RENAME TO " + newName + tableName;
            db.execSQL(dropTable);
        }
    }





    /**
     * 判断某张表是否存在
     *
     * @return
     */
    public static boolean tabbleIsExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DaoManager.getsHelper().getReadableDatabase();
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }


}
