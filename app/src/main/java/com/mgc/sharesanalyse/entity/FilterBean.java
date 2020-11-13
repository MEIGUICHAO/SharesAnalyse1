package com.mgc.sharesanalyse.entity;

import org.jetbrains.annotations.NotNull;

public class FilterBean {
    private String code;
    private int FilterTypeCount;
    private String FilterJs;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFilterTypeCount() {
        return FilterTypeCount;
    }

    public void setFilterTypeCount(int filterTypeCount) {
        FilterTypeCount = filterTypeCount;
    }

    public String getFilterJs() {
        return FilterJs;
    }

    public void setFilterJs(String filterJs) {
        FilterJs = filterJs;
    }

    public String toInsert() {
        return "('" + code +
                "', " + FilterTypeCount +
                ", '" + FilterJs+"')";
    }

    @NotNull
    public String toUpdateSqlSumValues() {
        return "" +
                " FILTERTYPECOUNT=" + FilterTypeCount +
                ", FILTERJS= '" + FilterJs+"'";
    }
}
