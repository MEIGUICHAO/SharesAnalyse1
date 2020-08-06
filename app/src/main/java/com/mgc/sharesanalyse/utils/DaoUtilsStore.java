package com.mgc.sharesanalyse.utils;

import com.mgc.sharesanalyse.entity.Month8Data;
import com.mgc.sharesanalyse.entity.Month8DataDao;
import com.mgc.sharesanalyse.entity.StocksBean;
import com.mgc.sharesanalyse.entity.StocksBeanDao;

public class DaoUtilsStore {
    private volatile static DaoUtilsStore instance = null;
    private CommonDaoUtils<Month8Data> Month8DataDaoUtils;
    private CommonDaoUtils<StocksBean> StocksBeanDaoUtils;

    public static DaoUtilsStore getInstance(){
        if(instance == null) {
            synchronized (DaoUtilsStore.class){
                if (instance == null){
                    instance = new DaoUtilsStore();
                }
            }
        }
        return instance;
    }

    private DaoUtilsStore() {
        DaoManager mManager = DaoManager.getInstance();
        Month8DataDao month8DataDao = mManager.getDaoSession().getMonth8DataDao();
        StocksBeanDao stocksBeanDao = mManager.getDaoSession().getStocksBeanDao();
        Month8DataDaoUtils = new CommonDaoUtils(Month8Data.class, month8DataDao);
        StocksBeanDaoUtils = new CommonDaoUtils(StocksBean.class, stocksBeanDao);
    }

    public void resetDaoUtilsStore() {
        instance = new DaoUtilsStore();
    }


    public CommonDaoUtils<Month8Data> getMonth8DataDaoUtils() {
        return Month8DataDaoUtils;
    }

    public CommonDaoUtils<StocksBean> getStocksBeanDaoUtils() {
        return StocksBeanDaoUtils;
    }
}
