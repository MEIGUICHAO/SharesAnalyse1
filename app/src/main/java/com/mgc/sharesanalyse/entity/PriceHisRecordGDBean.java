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
    private float baseComparePercent;
    private float dealAmount;
    private float dealAvgAmount;
    private float turnOverRate;
    private String result;

    public PriceHisRecordGDBean() {
    }

    @Generated(hash = 928119174)
    public PriceHisRecordGDBean(long id, String code, int conformSize,
            float baseComparePercent, float dealAmount, float dealAvgAmount,
            float turnOverRate, String result) {
        this.id = id;
        this.code = code;
        this.conformSize = conformSize;
        this.baseComparePercent = baseComparePercent;
        this.dealAmount = dealAmount;
        this.dealAvgAmount = dealAvgAmount;
        this.turnOverRate = turnOverRate;
        this.result = result;
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

    public float getBaseComparePercent() {
        return baseComparePercent;
    }

    public void setBaseComparePercent(float baseComparePercent) {
        this.baseComparePercent = baseComparePercent;
    }

    public float getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(float dealAmount) {
        this.dealAmount = dealAmount;
    }

    public float getDealAvgAmount() {
        return dealAvgAmount;
    }

    public void setDealAvgAmount(float dealAvgAmount) {
        this.dealAvgAmount = dealAvgAmount;
    }

    public float getTurnOverRate() {
        return turnOverRate;
    }

    public void setTurnOverRate(float turnOverRate) {
        this.turnOverRate = turnOverRate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
