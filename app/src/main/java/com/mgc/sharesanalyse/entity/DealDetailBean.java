package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DealDetailBean {
    
    @Id(autoincrement = true)
    private Long id;

    @Override
    public String toString() {
        return "DealDetailBean{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", size=" + size +
                ", percent=" + percent +
                ", date='" + date + '\'' +
                ", wholeJson15='" + wholeJson15 + '\'' +
                ", amoutSizeJson='" + amoutSizeJson + '\'' +
                ", halfHourSize='" + halfHourSize + '\'' +
                '}';
    }

    private String code;
    private int size;
    private double percent;
    private String date;
    private String wholeJson15;//date、json,size15
    private String amoutSizeJson;//m100,m50,m30,m10,m5,m1,m05,m01-->(time、amount),size
    private String halfHourSize;//half1--half8

    @Generated(hash = 939049040)
    public DealDetailBean(Long id, String code, int size, double percent, String date,
            String wholeJson15, String amoutSizeJson, String halfHourSize) {
        this.id = id;
        this.code = code;
        this.size = size;
        this.percent = percent;
        this.date = date;
        this.wholeJson15 = wholeJson15;
        this.amoutSizeJson = amoutSizeJson;
        this.halfHourSize = halfHourSize;
    }
    @Generated(hash = 337904532)
    public DealDetailBean() {
    }
    public Long getId() {
        return this.id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public int getSize() {
        return this.size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public double getPercent() {
        return this.percent;
    }
    public void setPercent(double percent) {
        this.percent = percent;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getWholeJson15() {
        return this.wholeJson15;
    }
    public void setWholeJson15(String wholeJson15) {
        this.wholeJson15 = wholeJson15;
    }
    public String getAmoutSizeJson() {
        return this.amoutSizeJson;
    }
    public void setAmoutSizeJson(String amoutSizeJson) {
        this.amoutSizeJson = amoutSizeJson;
    }
    public String getHalfHourSize() {
        return this.halfHourSize;
    }
    public void setHalfHourSize(String halfHourSize) {
        this.halfHourSize = halfHourSize;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
