package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class StocksBean {


    private String time;
    private String buy1;
    private String sale1;

    private String buy2;
    private String sale2;

    private String buy3;
    private String sale3;

    private String buy4;
    private String sale4;

    private String buy5;
    private String sale5;

    private String dealStocks;
    private String dealAmount;
    private Double PerStocks;
    private Double PerAmount;

    private Double open;
    private Double current;
    private Double PerPrice;

    private String hightest;
    private String lowest;
    @Generated(hash = 2033036649)
    public StocksBean(String time, String buy1, String sale1, String buy2,
            String sale2, String buy3, String sale3, String buy4, String sale4,
            String buy5, String sale5, String dealStocks, String dealAmount,
            Double PerStocks, Double PerAmount, Double open, Double current,
            Double PerPrice, String hightest, String lowest) {
        this.time = time;
        this.buy1 = buy1;
        this.sale1 = sale1;
        this.buy2 = buy2;
        this.sale2 = sale2;
        this.buy3 = buy3;
        this.sale3 = sale3;
        this.buy4 = buy4;
        this.sale4 = sale4;
        this.buy5 = buy5;
        this.sale5 = sale5;
        this.dealStocks = dealStocks;
        this.dealAmount = dealAmount;
        this.PerStocks = PerStocks;
        this.PerAmount = PerAmount;
        this.open = open;
        this.current = current;
        this.PerPrice = PerPrice;
        this.hightest = hightest;
        this.lowest = lowest;
    }
    @Generated(hash = 1799937850)
    public StocksBean() {
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getBuy1() {
        return this.buy1;
    }
    public void setBuy1(String buy1) {
        this.buy1 = buy1;
    }
    public String getSale1() {
        return this.sale1;
    }
    public void setSale1(String sale1) {
        this.sale1 = sale1;
    }
    public String getBuy2() {
        return this.buy2;
    }
    public void setBuy2(String buy2) {
        this.buy2 = buy2;
    }
    public String getSale2() {
        return this.sale2;
    }
    public void setSale2(String sale2) {
        this.sale2 = sale2;
    }
    public String getBuy3() {
        return this.buy3;
    }
    public void setBuy3(String buy3) {
        this.buy3 = buy3;
    }
    public String getSale3() {
        return this.sale3;
    }
    public void setSale3(String sale3) {
        this.sale3 = sale3;
    }
    public String getBuy4() {
        return this.buy4;
    }
    public void setBuy4(String buy4) {
        this.buy4 = buy4;
    }
    public String getSale4() {
        return this.sale4;
    }
    public void setSale4(String sale4) {
        this.sale4 = sale4;
    }
    public String getBuy5() {
        return this.buy5;
    }
    public void setBuy5(String buy5) {
        this.buy5 = buy5;
    }
    public String getSale5() {
        return this.sale5;
    }
    public void setSale5(String sale5) {
        this.sale5 = sale5;
    }
    public String getDealStocks() {
        return this.dealStocks;
    }
    public void setDealStocks(String dealStocks) {
        this.dealStocks = dealStocks;
    }
    public String getDealAmount() {
        return this.dealAmount;
    }
    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }
    public Double getPerStocks() {
        return this.PerStocks;
    }
    public void setPerStocks(Double PerStocks) {
        this.PerStocks = PerStocks;
    }
    public Double getPerAmount() {
        return this.PerAmount;
    }
    public void setPerAmount(Double PerAmount) {
        this.PerAmount = PerAmount;
    }
    public Double getOpen() {
        return this.open;
    }
    public void setOpen(Double open) {
        this.open = open;
    }
    public Double getCurrent() {
        return this.current;
    }
    public void setCurrent(Double current) {
        this.current = current;
    }
    public Double getPerPrice() {
        return this.PerPrice;
    }
    public void setPerPrice(Double PerPrice) {
        this.PerPrice = PerPrice;
    }
    public String getHightest() {
        return this.hightest;
    }
    public void setHightest(String hightest) {
        this.hightest = hightest;
    }
    public String getLowest() {
        return this.lowest;
    }
    public void setLowest(String lowest) {
        this.lowest = lowest;
    }

}
