package com.mgc.sharesanalyse.entity;

import org.jetbrains.annotations.NotNull;

public class CheckFilterBean {
    private String code;
    private int date;
    private int checkSize;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getCheckSize() {
        return checkSize;
    }

    public void setCheckSize(int checkSize) {
        this.checkSize = checkSize;
    }
    public String toInsert() {
        return "('" + code +
                "', " + date +
                ", '" + checkSize+"')";
    }

    @NotNull
    public String toUpdateSqlSumValues() {
        return "" +
                " CHECKSIZE=" + checkSize +
                ", DATE=" + date;
    }
}
