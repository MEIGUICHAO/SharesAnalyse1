package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PriceHisRecordGDBean {
    @Id
    private long id;
    private String code;
    private int conformSize;
    private double baseComparePercent;
    private double dealAmount;
    private double dealAvgAmount;
    private double turnOverRate;
    private String result;

    @Generated(hash = 993254973)
    public PriceHisRecordGDBean(long id, String code, int conformSize,
            double baseComparePercent, double dealAmount, double dealAvgAmount,
            double turnOverRate, String result) {
        this.id = id;
        this.code = code;
        this.conformSize = conformSize;
        this.baseComparePercent = baseComparePercent;
        this.dealAmount = dealAmount;
        this.dealAvgAmount = dealAvgAmount;
        this.turnOverRate = turnOverRate;
        this.result = result;
    }

    @Generated(hash = 550760379)
    public PriceHisRecordGDBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getConformSize() {
        return conformSize;
    }

    public void setConformSize(int conformSize) {
        this.conformSize = conformSize;
    }

    public double getBaseComparePercent() {
        return baseComparePercent;
    }

    public void setBaseComparePercent(double baseComparePercent) {
        this.baseComparePercent = baseComparePercent;
    }

    public double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(double dealAmount) {
        this.dealAmount = dealAmount;
    }

    public double getDealAvgAmount() {
        return dealAvgAmount;
    }

    public void setDealAvgAmount(double dealAvgAmount) {
        this.dealAvgAmount = dealAvgAmount;
    }

    public double getTurnOverRate() {
        return turnOverRate;
    }

    public void setTurnOverRate(double turnOverRate) {
        this.turnOverRate = turnOverRate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
