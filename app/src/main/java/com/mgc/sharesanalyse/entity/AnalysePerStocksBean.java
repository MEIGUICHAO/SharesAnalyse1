package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class AnalysePerStocksBean {

    @Id
    private Long id;
    private int code;
    private String gt1000times;
    private String gt100times;
    @Generated(hash = 167756304)
    public AnalysePerStocksBean(Long id, int code, String gt1000times,
            String gt100times) {
        this.id = id;
        this.code = code;
        this.gt1000times = gt1000times;
        this.gt100times = gt100times;
    }
    @Generated(hash = 1996650616)
    public AnalysePerStocksBean() {
    }
    public int getCode() {
        return this.code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getGt1000times() {
        return this.gt1000times;
    }
    public void setGt1000times(String gt1000times) {
        this.gt1000times = gt1000times;
    }
    public String getGt100times() {
        return this.gt100times;
    }
    public void setGt100times(String gt100times) {
        this.gt100times = gt100times;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
