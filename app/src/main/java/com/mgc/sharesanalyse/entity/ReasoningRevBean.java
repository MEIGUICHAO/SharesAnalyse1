package com.mgc.sharesanalyse.entity;

public class ReasoningRevBean {
    private int CODE;
    private String N;
    private String D;
    private String D_D;
    private float P;
    private float MP;
    private float LP;
    private float After_O_P;
    private float After_C_P;


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

    public String createTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER,N TEXT,D TEXT,D_D TEXT,P INTEGER,MP INTEGER,LP INTEGER,AFTER_O_P INTEGER,AFTER_C_P INTEGER,FITLERTYPE TEXT,JSON TEXT);";
    }


    public String insertTB(String tbName) {
        return "INSERT INTO " + tbName + "(CODE ,N ,D ,D_D ,P,MP,LP,AFTER_O_P,AFTER_C_P,FITLERTYPE,JSON ) VALUES (" + toString()+")";
    }
}
