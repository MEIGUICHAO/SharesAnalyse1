package com.mgc.sharesanalyse.utils;

import com.mgc.sharesanalyse.entity.AnalysePerAmountBean;
import com.mgc.sharesanalyse.entity.AnalysePerAmountBeanDao;
import com.mgc.sharesanalyse.entity.AnalysePerPricesBean;
import com.mgc.sharesanalyse.entity.AnalysePerPricesBeanDao;
import com.mgc.sharesanalyse.entity.AnalysePerStocksBean;
import com.mgc.sharesanalyse.entity.AnalysePerStocksBeanDao;
import com.mgc.sharesanalyse.entity.AnalyseSizeBean;
import com.mgc.sharesanalyse.entity.AnalyseSizeBeanDao;
import com.mgc.sharesanalyse.entity.Month8Data;
import com.mgc.sharesanalyse.entity.Month8DataDao;
import com.mgc.sharesanalyse.entity.PriceHisRecordGDBean;
import com.mgc.sharesanalyse.entity.PriceHisRecordGDBeanDao;
import com.mgc.sharesanalyse.entity.PricesHisGDBean;
import com.mgc.sharesanalyse.entity.PricesHisGDBeanDao;
import com.mgc.sharesanalyse.entity.StocksBean;
import com.mgc.sharesanalyse.entity.StocksBeanDao;
import com.mgc.sharesanalyse.entity.StocksJsonBean;
import com.mgc.sharesanalyse.entity.StocksJsonBeanDao;

public class DaoUtilsStore {
    private volatile static DaoUtilsStore instance = null;
    private CommonDaoUtils<Month8Data> Month8DataDaoUtils;
    private CommonDaoUtils<StocksBean> StocksBeanDaoUtils;
    private CommonDaoUtils<AnalysePerAmountBean> AnalysePerAmountBeanDaoUtils;
    private CommonDaoUtils<AnalysePerStocksBean> AnalysePerStocksBeanDaoUtils;
    private CommonDaoUtils<AnalyseSizeBean> AnalyseSizeBeanDaoUtils;
    private CommonDaoUtils<StocksJsonBean> stocksJsonBeanCommonDaoUtils;
    private CommonDaoUtils<PriceHisRecordGDBean> priceHisRecordGDBeanCommonDaoUtils;

    public CommonDaoUtils<PricesHisGDBean> getPricesHisGDBeanCommonDaoUtils() {
        return pricesHisGDBeanCommonDaoUtils;
    }

    private CommonDaoUtils<PricesHisGDBean> pricesHisGDBeanCommonDaoUtils;

    public CommonDaoUtils<AnalyseSizeBean> getAnalyseSizeBeanDaoUtils() {
        return AnalyseSizeBeanDaoUtils;
    }

    public CommonDaoUtils<AnalysePerPricesBean> getAnalysePerPricesBeanDaoUtils() {
        return AnalysePerPricesBeanDaoUtils;
    }

    private CommonDaoUtils<AnalysePerPricesBean> AnalysePerPricesBeanDaoUtils;



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

    public CommonDaoUtils<PriceHisRecordGDBean> getPriceHisRecordGDBeanCommonDaoUtils() {
        return priceHisRecordGDBeanCommonDaoUtils;
    }

    private DaoUtilsStore() {
        DaoManager mManager = DaoManager.getInstance();
        Month8DataDao month8DataDao = mManager.getDaoSession().getMonth8DataDao();
        StocksBeanDao stocksBeanDao = mManager.getDaoSession().getStocksBeanDao();
        AnalysePerAmountBeanDao analysePerAmountBeanDao = mManager.getDaoSession().getAnalysePerAmountBeanDao();
        AnalysePerStocksBeanDao analysePerStocksBeanDao = mManager.getDaoSession().getAnalysePerStocksBeanDao();
        AnalysePerPricesBeanDao analysePerPricesBeanDao = mManager.getDaoSession().getAnalysePerPricesBeanDao();
        AnalyseSizeBeanDao analyseSizeBeanDao = mManager.getDaoSession().getAnalyseSizeBeanDao();
        StocksJsonBeanDao stocksJsonBeanDao = mManager.getDaoSession().getStocksJsonBeanDao();
        PricesHisGDBeanDao pricesHisGDBeanDao = mManager.getDaoSession().getPricesHisGDBeanDao();
        PriceHisRecordGDBeanDao priceHisRecordGDBeanDao = mManager.getDaoSession().getPriceHisRecordGDBeanDao();
        Month8DataDaoUtils = new CommonDaoUtils(Month8Data.class, month8DataDao);
        StocksBeanDaoUtils = new CommonDaoUtils(StocksBean.class, stocksBeanDao);
        AnalysePerAmountBeanDaoUtils = new CommonDaoUtils(AnalysePerAmountBean.class, analysePerAmountBeanDao);
        AnalysePerStocksBeanDaoUtils = new CommonDaoUtils(AnalysePerStocksBean.class, analysePerStocksBeanDao);
        AnalysePerPricesBeanDaoUtils = new CommonDaoUtils(AnalysePerPricesBean.class, analysePerPricesBeanDao);
        AnalyseSizeBeanDaoUtils = new CommonDaoUtils(AnalyseSizeBean.class, analyseSizeBeanDao);
        stocksJsonBeanCommonDaoUtils = new CommonDaoUtils(StocksJsonBean.class, stocksJsonBeanDao);

        pricesHisGDBeanCommonDaoUtils = new CommonDaoUtils(PricesHisGDBean.class, pricesHisGDBeanDao);
        priceHisRecordGDBeanCommonDaoUtils = new CommonDaoUtils(PriceHisRecordGDBean.class, priceHisRecordGDBeanDao);
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
    public CommonDaoUtils<AnalysePerAmountBean> getAnalysePerAmountBeanDaoUtils() {
        return AnalysePerAmountBeanDaoUtils;
    }

    public CommonDaoUtils<AnalysePerStocksBean> getAnalysePerStocksBeanDaoUtils() {
        return AnalysePerStocksBeanDaoUtils;
    }

    public CommonDaoUtils<StocksJsonBean> getStocksJsonBeanCommonDaoUtils() {
        return stocksJsonBeanCommonDaoUtils;
    }
}
