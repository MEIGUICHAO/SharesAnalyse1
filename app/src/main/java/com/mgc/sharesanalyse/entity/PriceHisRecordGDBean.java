package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PriceHisRecordGDBean {
    @Id
    private long id;
    private String code;
    private String result;
    private int conformSize;
    private double baseComparePercent;
    @Generated(hash = 626827676)
    public PriceHisRecordGDBean(long id, String code, String result,
            int conformSize, double baseComparePercent) {
        this.id = id;
        this.code = code;
        this.result = result;
        this.conformSize = conformSize;
        this.baseComparePercent = baseComparePercent;
    }
    @Generated(hash = 550760379)
    public PriceHisRecordGDBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getResult() {
        return this.result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public int getConformSize() {
        return this.conformSize;
    }
    public void setConformSize(int conformSize) {
        this.conformSize = conformSize;
    }
    public double getBaseComparePercent() {
        return this.baseComparePercent;
    }
    public void setBaseComparePercent(double baseComparePercent) {
        this.baseComparePercent = baseComparePercent;
    }

}
