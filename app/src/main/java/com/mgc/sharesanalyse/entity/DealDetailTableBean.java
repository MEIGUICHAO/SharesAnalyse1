package com.mgc.sharesanalyse.entity;

public class DealDetailTableBean {
    private String code;
    private double percent;
    private int allsize;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getAllsize() {
        return allsize;
    }

    public void setAllsize(int allsize) {
        this.allsize = allsize;
    }

    public DealDetailAmountSizeBean getSizeBean() {
        return sizeBean;
    }

    public void setSizeBean(DealDetailAmountSizeBean sizeBean) {
        this.sizeBean = sizeBean;
    }

    private DealDetailAmountSizeBean sizeBean;



    public String toSqlValues() {
        return "(" + code  +
                "," + allsize +
                "," + percent + sizeBean.toValues();
    }
}
