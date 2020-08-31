package com.mgc.sharesanalyse.entity;

public class RecordBean {

    public double bAmount;
    public double sAmount;
    public double bsDiffAmount;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String time;

    public double getbAmount() {
        return bAmount;
    }

    public void setbAmount(double bAmount) {
        this.bAmount = bAmount;
    }

    public double getsAmount() {
        return sAmount;
    }

    public void setsAmount(double sAmount) {
        this.sAmount = sAmount;
    }

    public double getBsDiffAmount() {
        return bsDiffAmount;
    }

    public void setBsDiffAmount(double bsDiffAmount) {
        this.bsDiffAmount = bsDiffAmount;
    }

    public double getbPercent() {
        return bPercent;
    }

    public void setbPercent(double bPercent) {
        this.bPercent = bPercent;
    }

    public double bPercent;
    public double cPercent;
    public double perAmount;

    public double getcPercent() {
        return cPercent;
    }

    public void setcPercent(double cPercent) {
        this.cPercent = cPercent;
    }
}
