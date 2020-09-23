package com.mgc.sharesanalyse.utils;

import com.mgc.sharesanalyse.entity.AllCodeGDBean;
import com.mgc.sharesanalyse.entity.AllCodeGDBeanDao;
import com.mgc.sharesanalyse.entity.DealDetailBean;
import com.mgc.sharesanalyse.entity.DealDetailBeanDao;
import com.mgc.sharesanalyse.entity.PriceHisRecordGDBean;
import com.mgc.sharesanalyse.entity.PriceHisRecordGDBeanDao;
import com.mgc.sharesanalyse.entity.PricesHisGDBean;
import com.mgc.sharesanalyse.entity.PricesHisGDBeanDao;

public class DaoUtilsStore {
    private volatile static DaoUtilsStore instance = null;
    private CommonDaoUtils<PriceHisRecordGDBean> priceHisRecordGDBeanCommonDaoUtils;

    public CommonDaoUtils<DealDetailBean> getDealDetailBeanCommonDaoUtils() {
        return dealDetailBeanCommonDaoUtils;
    }

    public void setDealDetailBeanCommonDaoUtils(CommonDaoUtils<DealDetailBean> dealDetailBeanCommonDaoUtils) {
        this.dealDetailBeanCommonDaoUtils = dealDetailBeanCommonDaoUtils;
    }

    private CommonDaoUtils<DealDetailBean> dealDetailBeanCommonDaoUtils;
    private CommonDaoUtils<AllCodeGDBean> allCodeGDBeanDaoUtils;

    public CommonDaoUtils<AllCodeGDBean> getAllCodeGDBeanDaoUtils() {
        return allCodeGDBeanDaoUtils;
    }

    public CommonDaoUtils<PricesHisGDBean> getPricesHisGDBeanCommonDaoUtils() {
        return pricesHisGDBeanCommonDaoUtils;
    }

    private CommonDaoUtils<PricesHisGDBean> pricesHisGDBeanCommonDaoUtils;




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
        PricesHisGDBeanDao pricesHisGDBeanDao = mManager.getDaoSession().getPricesHisGDBeanDao();
        PriceHisRecordGDBeanDao priceHisRecordGDBeanDao = mManager.getDaoSession().getPriceHisRecordGDBeanDao();
        DealDetailBeanDao dealDetailBeanDao = mManager.getDaoSession().getDealDetailBeanDao();
        AllCodeGDBeanDao allCodeGDBeanDao = mManager.getDaoSession().getAllCodeGDBeanDao();

        pricesHisGDBeanCommonDaoUtils = new CommonDaoUtils(PricesHisGDBean.class, pricesHisGDBeanDao);
        priceHisRecordGDBeanCommonDaoUtils = new CommonDaoUtils(PriceHisRecordGDBean.class, priceHisRecordGDBeanDao);
        dealDetailBeanCommonDaoUtils = new CommonDaoUtils(DealDetailBean.class, dealDetailBeanDao);
        allCodeGDBeanDaoUtils = new CommonDaoUtils(AllCodeGDBean.class, allCodeGDBeanDao);
    }

    public void resetDaoUtilsStore() {
        instance = new DaoUtilsStore();
    }


}
