package com.mgc.sharesanalyse.entity;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

public class FilterCodeHDDBean {
    private int CODE;
    private int KJ_CTC_TYPE;
    private String KJ_CTC_J;

    public int getKJ_CTC_TYPE() {
        return KJ_CTC_TYPE;
    }

    public void setKJ_CTC_TYPE(int KJ_CTC_TYPE) {
        this.KJ_CTC_TYPE = KJ_CTC_TYPE;
    }

    public String getKJ_CTC_J() {
        return KJ_CTC_J;
    }

    public void setKJ_CTC_J(String KJ_CTC_J) {
        this.KJ_CTC_J = KJ_CTC_J;
    }


    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    @NotNull
    public String toInsert() {
        return "(" + CODE + ","
                + KJ_CTC_TYPE + ",'" + KJ_CTC_J + "'"
                + ")";
    }

    @NotNull
    public String toUpdateSqlSumValues() {
        return "KJ_CTC_TYPE = " + KJ_CTC_TYPE +
                "KJ_CTC_J = '" + KJ_CTC_J + "'"+
                "";
    }
}
