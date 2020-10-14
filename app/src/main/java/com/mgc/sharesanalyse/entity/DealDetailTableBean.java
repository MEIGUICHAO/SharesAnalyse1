package com.mgc.sharesanalyse.entity;

import org.jetbrains.annotations.NotNull;

public class DealDetailTableBean {
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
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

    public String getToString(double dealAmount) {
        return "" +
                ", allsize=" + allsize +
                ", sizeBean=" + sizeBean.getToString(dealAmount);
    }

    private DealDetailAmountSizeBean sizeBean;



    public String toSqlValues() {
        return "(" + code +
                "," + "'" + name + "'" +
                "," + allsize +
                "," + percent + sizeBean.toValues();
    }

    public String toInsertSqlSumValues(DealDetailTableBean ddbean, int allsize) {
        return "(" + code +
                "," + "'" + name + "'" +
                "," + allsize +
                "," + percent
                + sizeBean.toInsertSumDDValues(ddbean.getSizeBean(),allsize);
    }

}
