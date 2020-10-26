package com.mgc.sharesanalyse.entity;

public class PriceHisBean {

    private float dealPrice;
    private float dealStocks;
    private String dealPercent;


    public float getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(float dealPrice) {
        this.dealPrice = dealPrice;
    }

    public float getDealStocks() {
        return dealStocks;
    }

    public void setDealStocks(float dealStocks) {
        this.dealStocks = dealStocks;
    }

    public String getDealPercent() {
        return dealPercent;
    }

    public void setDealPercent(String dealPercent) {
        this.dealPercent = dealPercent;
    }

}
