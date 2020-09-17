package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PricesHisGDBean {

    @Id
    private Long id;
    private String code;
    private String json;
    private String date;
    private String mineInfo;
    private String XQInfo;

    @Generated(hash = 1659858968)
    public PricesHisGDBean(Long id, String code, String json, String date,
            String mineInfo, String XQInfo) {
        this.id = id;
        this.code = code;
        this.json = json;
        this.date = date;
        this.mineInfo = mineInfo;
        this.XQInfo = XQInfo;
    }
    @Generated(hash = 1088630204)
    public PricesHisGDBean() {
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getMineInfo() {
        return this.mineInfo;
    }
    public void setMineInfo(String mineInfo) {
        this.mineInfo = mineInfo;
    }
    public String getXQInfo() {
        return this.XQInfo;
    }
    public void setXQInfo(String XQInfo) {
        this.XQInfo = XQInfo;
    }
}
