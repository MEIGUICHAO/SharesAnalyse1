package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class StocksBean {
    @Id
    private Long id;
    private String stocksName;
    private String stocksCode;
    private String timeStamp;
    private String buy1;
    private String buy2;
    private String buy3;
    private String buy4;
    private String buy5;
    private String sale1;
    private String sale2;
    private String sale3;
    private String sale4;
    private String sale5;
    private String openPrice;

    private String currentPrice;
    private String hightestPrice;
    private String lowestPrice;
    private String dealStocks;
    private String dealAmount;

    private String dealPerStocks;
    private String dealPerPrice;
    @Generated(hash = 231019084)
    public StocksBean(Long id, String stocksName, String stocksCode,
            String timeStamp, String buy1, String buy2, String buy3, String buy4,
            String buy5, String sale1, String sale2, String sale3, String sale4,
            String sale5, String openPrice, String currentPrice,
            String hightestPrice, String lowestPrice, String dealStocks,
            String dealAmount, String dealPerStocks, String dealPerPrice) {
        this.id = id;
        this.stocksName = stocksName;
        this.stocksCode = stocksCode;
        this.timeStamp = timeStamp;
        this.buy1 = buy1;
        this.buy2 = buy2;
        this.buy3 = buy3;
        this.buy4 = buy4;
        this.buy5 = buy5;
        this.sale1 = sale1;
        this.sale2 = sale2;
        this.sale3 = sale3;
        this.sale4 = sale4;
        this.sale5 = sale5;
        this.openPrice = openPrice;
        this.currentPrice = currentPrice;
        this.hightestPrice = hightestPrice;
        this.lowestPrice = lowestPrice;
        this.dealStocks = dealStocks;
        this.dealAmount = dealAmount;
        this.dealPerStocks = dealPerStocks;
        this.dealPerPrice = dealPerPrice;
    }
    @Generated(hash = 1799937850)
    public StocksBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStocksName() {
        return this.stocksName;
    }
    public void setStocksName(String stocksName) {
        this.stocksName = stocksName;
    }
    public String getStocksCode() {
        return this.stocksCode;
    }
    public void setStocksCode(String stocksCode) {
        this.stocksCode = stocksCode;
    }
    public String getTimeStamp() {
        return this.timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getBuy1() {
        return this.buy1;
    }
    public void setBuy1(String buy1) {
        this.buy1 = buy1;
    }
    public String getBuy2() {
        return this.buy2;
    }
    public void setBuy2(String buy2) {
        this.buy2 = buy2;
    }
    public String getBuy3() {
        return this.buy3;
    }
    public void setBuy3(String buy3) {
        this.buy3 = buy3;
    }
    public String getBuy4() {
        return this.buy4;
    }
    public void setBuy4(String buy4) {
        this.buy4 = buy4;
    }
    public String getBuy5() {
        return this.buy5;
    }
    public void setBuy5(String buy5) {
        this.buy5 = buy5;
    }
    public String getSale1() {
        return this.sale1;
    }
    public void setSale1(String sale1) {
        this.sale1 = sale1;
    }
    public String getSale2() {
        return this.sale2;
    }
    public void setSale2(String sale2) {
        this.sale2 = sale2;
    }
    public String getSale3() {
        return this.sale3;
    }
    public void setSale3(String sale3) {
        this.sale3 = sale3;
    }
    public String getSale4() {
        return this.sale4;
    }
    public void setSale4(String sale4) {
        this.sale4 = sale4;
    }
    public String getSale5() {
        return this.sale5;
    }
    public void setSale5(String sale5) {
        this.sale5 = sale5;
    }
    public String getOpenPrice() {
        return this.openPrice;
    }
    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }
    public String getCurrentPrice() {
        return this.currentPrice;
    }
    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }
    public String getHightestPrice() {
        return this.hightestPrice;
    }
    public void setHightestPrice(String hightestPrice) {
        this.hightestPrice = hightestPrice;
    }
    public String getLowestPrice() {
        return this.lowestPrice;
    }
    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
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
    public String getDealPerStocks() {
        return this.dealPerStocks;
    }
    public void setDealPerStocks(String dealPerStocks) {
        this.dealPerStocks = dealPerStocks;
    }
    public String getDealPerPrice() {
        return this.dealPerPrice;
    }
    public void setDealPerPrice(String dealPerPrice) {
        this.dealPerPrice = dealPerPrice;
    }



}
