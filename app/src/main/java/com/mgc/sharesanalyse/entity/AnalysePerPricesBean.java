package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AnalysePerPricesBean {

    private int code;
    private String perPricesGtCur;
    private String curGtPerPrices;

    @Generated(hash = 1274347093)
    public AnalysePerPricesBean(int code, String perPricesGtCur,
            String curGtPerPrices) {
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


}
