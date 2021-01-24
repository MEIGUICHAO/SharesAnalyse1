package com.mgc.sharesanalyse.entity;

import java.io.Serializable;

public class ReasoningRevBean implements Serializable {
    private int CODE;
    private String N;
    private String D;
    private String D_D;
    private float P;
    private float MP;
    private float LP;
    private float After_O_P;
    private float After_C_P;
    private int F36_T;
    private int F30_T;
    private int F25_T;
    private int F20_T;
    private int F15_T;
    private int F10_T;
    private int F05_T;
    private int F03_T;
    private int MA_1;
    private int MA_3;
    private int MA_5;
    private String AO;

    public String getAO() {
        return AO;
    }

    public void setAO(String AO) {
        this.AO = AO;
    }

    public int getMA_1() {
        return MA_1;
    }

    public void setMA_1(int MA_1) {
        this.MA_1 = MA_1;
    }

    public int getMA_3() {
        return MA_3;
    }

    public void setMA_3(int MA_3) {
        this.MA_3 = MA_3;
    }

    public int getMA_5() {
        return MA_5;
    }

    public void setMA_5(int MA_5) {
        this.MA_5 = MA_5;
    }

    public int getMA_10() {
        return MA_10;
    }

    public void setMA_10(int MA_10) {
        this.MA_10 = MA_10;
    }

    private int MA_10;

    public int getF36_T() {
        return F36_T;
    }

    public void setF36_T(int f36_T) {
        F36_T = f36_T;
    }

    public int getF30_T() {
        return F30_T;
    }

    public void setF30_T(int f30_T) {
        F30_T = f30_T;
    }

    public int getF25_T() {
        return F25_T;
    }

    public void setF25_T(int f25_T) {
        F25_T = f25_T;
    }

    public int getF20_T() {
        return F20_T;
    }

    public void setF20_T(int f20_T) {
        F20_T = f20_T;
    }

    public int getF15_T() {
        return F15_T;
    }

    public void setF15_T(int f15_T) {
        F15_T = f15_T;
    }

    public int getF10_T() {
        return F10_T;
    }

    public void setF10_T(int f10_T) {
        F10_T = f10_T;
    }

    public int getF05_T() {
        return F05_T;
    }

    public void setF05_T(int f05_T) {
        F05_T = f05_T;
    }

    public int getF03_T() {
        return F03_T;
    }

    public void setF03_T(int f03_T) {
        F03_T = f03_T;
    }

    public float getMP() {
        return MP;
    }

    public float getAfter_O_P() {
        return After_O_P;
    }

    public void setAfter_O_P(float after_O_P) {
        After_O_P = after_O_P;
    }

    public float getAfter_C_P() {
        return After_C_P;
    }

    public void setAfter_C_P(float after_C_P) {
        After_C_P = after_C_P;
    }

    public void setMP(float MP) {
        this.MP = MP;
    }

    public float getLP() {
        return LP;
    }

    public void setLP(float LP) {
        this.LP = LP;
    }


    public String getFITLERTYPE() {
        return FITLERTYPE;
    }

    public void setFITLERTYPE(String FITLERTYPE) {
        this.FITLERTYPE = FITLERTYPE;
    }

    private String FITLERTYPE;
    private String JSON;


    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public String getN() {
        return N;
    }

    public void setN(String n) {
        N = n;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getD_D() {
        return D_D;
    }

    public void setD_D(String d_D) {
        D_D = d_D;
    }

    public float getP() {
        return P;
    }

    public void setP(float p) {
        P = p;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    @Override
    public String toString() {
        return  CODE +
                ", '" + N + '\'' +
                ", '" + D + '\'' +
                ", '" + D_D + '\'' +
                ", " + P  +
                ", " + MP  +
                ", " + LP  +
                ", " + After_O_P  +
                ", " + After_C_P  +
                ", '" + FITLERTYPE + '\'' +
                ", '" + JSON + '\'' ;
    }

    public String toAllString() {
        return  CODE +
                ", '" + N + '\'' +
                ", '" + D + '\'' +
                ", '" + D_D + '\'' +
                ", " + P  +
                ", " + MP  +
                ", " + LP  +
                ", " + After_O_P  +
                ", " + After_C_P  +
                ", " + F36_T  +
                ", " + F30_T  +
                ", " + F25_T  +
                ", " + F20_T  +
                ", " + F15_T  +
                ", " + F10_T  +
                ", " + F05_T  +
                ", " + F03_T +
                ", " + MA_1 +
                ", " + MA_3 +
                ", " + MA_5 +
                ", " + MA_10
                ;
    }

    public String createTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER,N TEXT,D TEXT,D_D TEXT,P INTEGER,MP INTEGER,LP INTEGER,AFTER_O_P INTEGER,AFTER_C_P INTEGER,FITLERTYPE TEXT,JSON TEXT);";
    }


    public String insertTB(String tbName) {
        return "INSERT INTO " + tbName + "(CODE ,N ,D ,D_D ,P,MP,LP,AFTER_O_P,AFTER_C_P,FITLERTYPE,JSON ) VALUES (" + toString()+")";
    }


    public String createAllTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER,N TEXT,D TEXT,D_D TEXT,P INTEGER,MP INTEGER,LP INTEGER,AFTER_O_P INTEGER,AFTER_C_P INTEGER" +
                ",F36_T INTEGER,F30_T INTEGER,F25_T INTEGER,F20_T INTEGER,F15_T INTEGER,F10_T INTEGER,F05_T INTEGER,F03_T INTEGER,MA1 INTEGER,MA3 INTEGER,MA5 INTEGER,MA10 INTEGER);";
    }



    public String insertAllTB(String tbName) {
        return "INSERT INTO " + tbName + "(CODE ,N ,D ,D_D ,P,MP,LP,AFTER_O_P,AFTER_C_P,F36_T,F30_T,F25_T,F20_T,F15_T,F10_T,F05_T,F03_T,MA1 ,MA3 ,MA5 ,MA10 ) VALUES (" + toAllString()+")";
    }
}
