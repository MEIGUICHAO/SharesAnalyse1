package com.mgc.sharesanalyse.entity;

public class RecordBean {

    public float bAmount;
    public float sAmount;
    public float bsDiffAmount;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String time;

    public float getbAmount() {
        return bAmount;
    }

    public void setbAmount(float bAmount) {
        this.bAmount = bAmount;
    }

    public float getsAmount() {
        return sAmount;
    }

    public void setsAmount(float sAmount) {
        this.sAmount = sAmount;
    }

    public float getBsDiffAmount() {
        return bsDiffAmount;
    }

    public void setBsDiffAmount(float bsDiffAmount) {
        this.bsDiffAmount = bsDiffAmount;
    }

    public float getbPercent() {
        return bPercent;
    }

    public void setbPercent(float bPercent) {
        this.bPercent = bPercent;
    }

    public float bPercent;
    public float cPercent;
    public float perAmount;

    public float getcPercent() {
        return cPercent;
    }

    public void setcPercent(float cPercent) {
        this.cPercent = cPercent;
    }
}
