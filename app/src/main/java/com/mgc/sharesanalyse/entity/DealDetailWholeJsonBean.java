package com.mgc.sharesanalyse.entity;

public class DealDetailWholeJsonBean {
    private String date;
    private String json;
    private float percent;

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public int getAllSize() {
        return allSize;
    }

    public void setAllSize(int allSize) {
        this.allSize = allSize;
    }

    private int allSize;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
