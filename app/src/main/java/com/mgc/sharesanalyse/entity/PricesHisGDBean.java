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

    @Generated(hash = 209692857)
    public PricesHisGDBean(Long id, String code, String json, String date) {
        this.id = id;
        this.code = code;
        this.json = json;
        this.date = date;
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
}
