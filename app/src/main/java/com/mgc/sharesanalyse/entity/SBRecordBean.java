package com.mgc.sharesanalyse.entity;

import java.util.ArrayList;

public class SBRecordBean {

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    private String dealAmount;

    private ArrayList<RecordBean> recordBeans;

    public ArrayList<RecordBean> getRecordBeans() {
        return recordBeans;
    }

    public void setRecordBeans(ArrayList<RecordBean> recordBeans) {
        this.recordBeans = recordBeans;
    }


}
