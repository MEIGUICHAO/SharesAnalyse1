package com.mgc.sharesanalyse.utils;

import com.mgc.sharesanalyse.entity.Month8Data;
import com.mgc.sharesanalyse.entity.Month8DataDao;

public class DaoUtilsStore {
    private volatile static DaoUtilsStore instance = new DaoUtilsStore();
    private CommonDaoUtils<Month8Data> Month8DataDaoUtils;

    public static DaoUtilsStore getInstance() {
        return instance;
    }

    private DaoUtilsStore() {
        DaoManager mManager = DaoManager.getInstance();
        Month8DataDao month8DataDao = mManager.getDaoSession().getMonth8DataDao();
        Month8DataDaoUtils = new CommonDaoUtils(Month8Data.class, month8DataDao);
    }

//    private DaoUtilsStore(String dbName) {
//        DaoManager mManager = DaoManager.getInstance().setDBName(dbName);
//        Month8DataDao month8DataDao = mManager.getDaoSession().getMonth8DataDao();
//        Month8DataDaoUtils = new CommonDaoUtils(Month8Data.class, month8DataDao);
//    }


    public CommonDaoUtils<Month8Data> getMonth8DataDaoUtils() {
        return Month8DataDaoUtils;
    }
}
