package com.mgc.sharesanalyse.utils;

import android.content.Context;

import com.mgc.sharesanalyse.BuildConfig;
import com.mgc.sharesanalyse.base.Datas;
import com.mgc.sharesanalyse.entity.DaoMaster;
import com.mgc.sharesanalyse.entity.DaoSession;
import com.mgc.sharesanalyse.entity.Month8DataDao;
import com.mgc.sharesanalyse.entity.StocksBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    //    greendaotest
//    public static final String DB_NAME = "greendaotest";

    public static final String DB_NAME = Datas.INSTANCE.getDBName();

    private Context context;

    //多线程中要被共享的使用volatile关键字修饰
    private volatile static DaoManager manager = new DaoManager();
    private static DaoMaster sDaoMaster;

    public static CommonOpenHelper getsHelper() {
        return sHelper;
    }

    public static void setsHelper(CommonOpenHelper sHelper) {
        DaoManager.sHelper = sHelper;
    }

    private static CommonOpenHelper sHelper;
    private static DaoSession sDaoSession;

    /**
     * 单例模式获得操作数据库对象
     *
     * @return
     */
    public static DaoManager getInstance() {
        return manager;
    }

    private DaoManager() {
        setDebug();
    }

    public void init(Context context) {
        this.context = context;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     *
     * @return
     */
    public DaoMaster getDaoMaster() {
        if (sDaoMaster == null) {
            sHelper = new CommonOpenHelper(context, DB_NAME, null, Month8DataDao.class, StocksBeanDao.class);
            sDaoMaster = new DaoMaster(sHelper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    public void switchDB(String dbName,Class... classes) {
        sHelper = new CommonOpenHelper(context, dbName, null,classes);
        sDaoMaster = new DaoMaster(sHelper.getWritableDatabase());
        sDaoSession = null;
        getDaoSession();
        DaoUtilsStore.getInstance().resetDaoUtilsStore();
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (sDaoSession == null) {
            if (sDaoMaster == null) {
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug() {
        if (BuildConfig.DEBUG) {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        }
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper() {
        if (sHelper != null) {
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession() {
        if (sDaoSession != null) {
            sDaoSession.clear();
            sDaoSession = null;
        }
    }
}
