package com.mgc.sharesanalyse.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StocksJsonBean {

    @Id
    Long id;
    String jsonRecord;
    String sbRecord;
    Double Amount;
    Double percent;
    String time;
    @Generated(hash = 1563143387)
    public StocksJsonBean(Long id, String jsonRecord, String sbRecord,
            Double Amount, Double percent, String time) {
        this.id = id;
        this.jsonRecord = jsonRecord;
        this.sbRecord = sbRecord;
        this.Amount = Amount;
        this.percent = percent;
        this.time = time;
    }
    @Generated(hash = 1650624585)
    public StocksJsonBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJsonRecord() {
        return this.jsonRecord;
    }
    public void setJsonRecord(String jsonRecord) {
        this.jsonRecord = jsonRecord;
    }
    public String getSbRecord() {
        return this.sbRecord;
    }
    public void setSbRecord(String sbRecord) {
        this.sbRecord = sbRecord;
    }
    public Double getAmount() {
        return this.Amount;
    }
    public void setAmount(Double Amount) {
        this.Amount = Amount;
    }
    public Double getPercent() {
        return this.percent;
    }
    public void setPercent(Double percent) {
        this.percent = percent;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
