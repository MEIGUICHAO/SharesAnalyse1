package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class AnalysePerAmountBean {
    private int code;
    private String TenTimesLast;
    private String Ge100million;
    private String Ge50million;
    private String Ge20million;
    private String Ge10million;
    private String Ge5million;

    @Generated(hash = 1521608068)
    public AnalysePerAmountBean(int code, String TenTimesLast, String Ge100million,
            String Ge50million, String Ge20million, String Ge10million,
            String Ge5million) {
        this.code = code;
        this.TenTimesLast = TenTimesLast;
        this.Ge100million = Ge100million;
        this.Ge50million = Ge50million;
        this.Ge20million = Ge20million;
        this.Ge10million = Ge10million;
        this.Ge5million = Ge5million;
    }

    @Generated(hash = 1702331977)
    public AnalysePerAmountBean() {
    }

    public String getGe5million() {
        return Ge5million;
    }

    public void setGe5million(String ge5million) {
        Ge5million = ge5million;
    }

    public int getCode() {
        return this.code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getTenTimesLast() {
        return this.TenTimesLast;
    }
    public void setTenTimesLast(String TenTimesLast) {
        this.TenTimesLast = TenTimesLast;
    }
    public String getGe100million() {
        return this.Ge100million;
    }
    public void setGe100million(String Ge100million) {
        this.Ge100million = Ge100million;
    }
    public String getGe50million() {
        return this.Ge50million;
    }
    public void setGe50million(String Ge50million) {
        this.Ge50million = Ge50million;
    }
    public String getGe20million() {
        return this.Ge20million;
    }
    public void setGe20million(String Ge20million) {
        this.Ge20million = Ge20million;
    }
    public String getGe10million() {
        return this.Ge10million;
    }
    public void setGe10million(String Ge10million) {
        this.Ge10million = Ge10million;
    }
}
