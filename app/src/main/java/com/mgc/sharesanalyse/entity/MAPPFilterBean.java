package com.mgc.sharesanalyse.entity;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

public class MAPPFilterBean {
    private int CODE;
    private float AUP;
    private float MAUP;
    private float LAUP;

    public float getMAUP() {
        return MAUP;
    }

    public void setMAUP(float MAUP) {
        this.MAUP = MAUP;
    }

    public float getLAUP() {
        return LAUP;
    }

    public void setLAUP(float LAUP) {
        this.LAUP = LAUP;
    }

    private int DATE;
    private int COUNT;
    private String MAT1;
    private int MAC1;
    private String PPT1;
    private int PPC1;
    private String MAT2;
    private int MAC2;
    private String PPT2;
    private int PPC2;
    private String MAT3;
    private int MAC3;
    private String PPT3;
    private int PPC3;

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public float getAUP() {
        return AUP;
    }

    public void setAUP(float AUP) {
        this.AUP = AUP;
    }

    public int getDATE() {
        return DATE;
    }

    public void setDATE(int DATE) {
        this.DATE = DATE;
    }

    public int getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(int COUNT) {
        this.COUNT = COUNT;
    }

    public String getMAT1() {
        return MAT1;
    }

    public void setMAT1(String MAT1) {
        this.MAT1 = MAT1;
    }

    public int getMAC1() {
        return MAC1;
    }

    public void setMAC1(int MAC1) {
        this.MAC1 = MAC1;
    }

    public String getPPT1() {
        return PPT1;
    }

    public void setPPT1(String PPT1) {
        this.PPT1 = PPT1;
    }

    public int getPPC1() {
        return PPC1;
    }

    public void setPPC1(int PPC1) {
        this.PPC1 = PPC1;
    }

    public String getMAT2() {
        return MAT2;
    }

    public void setMAT2(String MAT2) {
        this.MAT2 = MAT2;
    }

    public int getMAC2() {
        return MAC2;
    }

    public void setMAC2(int MAC2) {
        this.MAC2 = MAC2;
    }

    public String getPPT2() {
        return PPT2;
    }

    public void setPPT2(String PPT2) {
        this.PPT2 = PPT2;
    }

    public int getPPC2() {
        return PPC2;
    }

    public void setPPC2(int PPC2) {
        this.PPC2 = PPC2;
    }

    public String getMAT3() {
        return MAT3;
    }

    public void setMAT3(String MAT3) {
        this.MAT3 = MAT3;
    }

    public int getMAC3() {
        return MAC3;
    }

    public void setMAC3(int MAC3) {
        this.MAC3 = MAC3;
    }

    public String getPPT3() {
        return PPT3;
    }

    public void setPPT3(String PPT3) {
        this.PPT3 = PPT3;
    }

    public int getPPC3() {
        return PPC3;
    }

    public void setPPC3(int PPC3) {
        this.PPC3 = PPC3;
    }

    @NotNull
    public String toInsert() {
        return "("
                + CODE + ","
                + AUP + ","
                + MAUP + ","
                + LAUP + ","
                + DATE + ","
                + COUNT + ",'"
                + MAT1 + "',"
                + MAC1 + ",'"
                + PPT1 + "',"
                + PPC1 + ",'"
                + MAT2 + "',"
                + MAC2 + ",'"
                + PPT2 + "',"
                + PPC2 + ",'"
                + MAT3 + "',"
                + MAC3 + ",'"
                + PPT3 + "',"
                + PPC3
                + ")";
    }

    @NotNull
    public String toUpdateSqlSumValues() {
        return "AUP = " + AUP +
                ",MAUP = " + MAUP +",LAUP = " + LAUP  +
                (DATE > 0 ? ",DATE = " + DATE : "") +
                (COUNT > 0 ? ",COUNT = " + COUNT : "") +
                (MAC1 > 0 ? ",MAC1 = " + MAC1 : "") +
                (PPC1 > 0 ? ",PPC1 = " + PPC1 : "") +
                (MAC2 > 0 ? ",MAC2 = " + MAC2 : "") +
                (PPC2 > 0 ? ",PPC2 = " + PPC2 : "") +
                (MAC3 > 0 ? ",MAC3 = " + MAC3 : "") +
                (PPC3 > 0 ? ",PPC3 = " + PPC3 : "") +

                (!TextUtils.isEmpty(MAT1) ? ",MAT1 ='" + MAT1 +"'": "") +
                (!TextUtils.isEmpty(PPT1) ? ",PPT1 = '" + PPT1 +"'": "") +
                (!TextUtils.isEmpty(MAT2) ? ",MAT2 = '" + MAT2 +"'": "") +
                (!TextUtils.isEmpty(PPT2) ? ",PPT2 = '" + PPT2 +"'": "") +
                (!TextUtils.isEmpty(MAT3) ? ",MAT3 = '" + MAT3 +"'": "") +
                (!TextUtils.isEmpty(PPT3) ? ",PPT3 = '" + PPT3 +"'": "") +
                "";
    }
}
