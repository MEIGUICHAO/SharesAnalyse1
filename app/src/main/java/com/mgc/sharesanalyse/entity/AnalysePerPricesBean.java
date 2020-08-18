package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class AnalysePerPricesBean {

    @Id
    private Long id;
    private int code;
    private String perPricesGtCur;
    private String curGtPerPrices;

    @Generated(hash = 1572290376)
    public AnalysePerPricesBean(Long id, int code, String perPricesGtCur,
            String curGtPerPrices) {
        this.id = id;
        this.code = code;
        this.perPricesGtCur = perPricesGtCur;
        this.curGtPerPrices = curGtPerPrices;
    }

    @Generated(hash = 1958608294)
    public AnalysePerPricesBean() {
    }

    public String getCurGtPerPrices() {
        return curGtPerPrices;
    }

    public void setCurGtPerPrices(String curGtPerPrices) {
        this.curGtPerPrices = curGtPerPrices;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPerPricesGtCur() {
        return perPricesGtCur;
    }

    public void setPerPricesGtCur(String perPricesGtCur) {
        this.perPricesGtCur = perPricesGtCur;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
