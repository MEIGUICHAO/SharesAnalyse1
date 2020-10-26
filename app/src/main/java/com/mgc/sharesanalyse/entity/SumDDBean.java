package com.mgc.sharesanalyse.entity;

public class SumDDBean {

    private String code;
    private String name;
    private float percent;
    private float maxPer;

    public float getLowPer() {
        return lowPer;
    }

    public void setLowPer(float lowPer) {
        this.lowPer = lowPer;
    }

    private float lowPer;
    private String mpd;

    public float getMaxPer() {
        return maxPer;
    }

    public void setMaxPer(float maxPer) {
        this.maxPer = maxPer;
    }

    public String getMpd() {
        return mpd;
    }

    public void setMpd(String mpd) {
        this.mpd = mpd;
    }

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

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
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

    public String getToString(float dealAmount) {
        return "" +
                ", allsize=" + allsize +
                ", sizeBean=" + sizeBean.getToString(dealAmount);
    }





    public String toUpdateSqlSumValues(DealDetailTableBean ddbean) {
        return "ALLSIZE="+(ddbean.getAllsize()+allsize)  + sizeBean.toUpdateSumDDValues(ddbean);
    }




}
