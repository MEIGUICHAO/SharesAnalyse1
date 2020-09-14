package com.mgc.sharesanalyse.entity;

public class PriceHisBean {

    private double dealPrice;
    private double dealStocks;
    private String dealPercent;


    public double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(double dealPrice) {
        this.dealPrice = dealPrice;
    }

    public double getDealStocks() {
        return dealStocks;
    }

    public void setDealStocks(double dealStocks) {
        this.dealStocks = dealStocks;
    }

    public String getDealPercent() {
        return dealPercent;
    }

    public void setDealPercent(String dealPercent) {
        this.dealPercent = dealPercent;
    }

}
