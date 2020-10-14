package com.mgc.sharesanalyse.entity;

import org.jetbrains.annotations.NotNull;

public class SumDDBean {

    private String code;
    private String name;
    private double percent;
    private double maxPer;
    private int allsize;
    private DealDetailAmountSizeBean sizeBean;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


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




    public String toSqlValues() {
        return "(" + code +
                "," + "'" + name + "'" +
                "," + allsize +
                "," + percent + sizeBean.toValues();
    }


    public String toUpdateSqlSumValues(DealDetailTableBean ddbean) {
        return "(" + (ddbean.getAllsize()+allsize) +
                "," + percent
                + sizeBean.toUpdateSumDDValues(sumDDBean.sizeBean);
    }




}
