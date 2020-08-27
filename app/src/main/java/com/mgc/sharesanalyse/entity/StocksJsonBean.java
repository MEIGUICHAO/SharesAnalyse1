package com.mgc.sharesanalyse.entity;


import java.util.ArrayList;
import java.util.List;

public class StocksJsonBean {

    public ArrayList<StocksBean> getStocksBeanList() {
        return stocksBeanList;
    }

    public void setStocksBeanList(ArrayList<StocksBean> stocksBeanList) {
        this.stocksBeanList = stocksBeanList;
    }

    private ArrayList<StocksBean> stocksBeanList;
}
