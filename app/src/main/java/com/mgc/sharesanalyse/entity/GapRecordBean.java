package com.mgc.sharesanalyse.entity;

public class GapRecordBean {

    private int CODE;
    private String N;
    private int B_D;
    private int DATE;

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

    public int getB_D() {
        return B_D;
    }

    public void setB_D(int b_D) {
        B_D = b_D;
    }

    public int getDATE() {
        return DATE;
    }

    public void setDATE(int DATE) {
        this.DATE = DATE;
    }

    public float getGAP_RATE() {
        return GAP_RATE;
    }

    public void setGAP_RATE(float GAP_RATE) {
        this.GAP_RATE = GAP_RATE;
    }

    public float getML_RANGE() {
        return ML_RANGE;
    }

    public void setML_RANGE(float ML_RANGE) {
        this.ML_RANGE = ML_RANGE;
    }

    public float getCO_RANGE() {
        return CO_RANGE;
    }

    public void setCO_RANGE(float CO_RANGE) {
        this.CO_RANGE = CO_RANGE;
    }

    public float getCD1LM() {
        return CD1LM;
    }

    public void setCD1LM(float CD1LM) {
        this.CD1LM = CD1LM;
    }

    public float getCD2LM() {
        return CD2LM;
    }

    public void setCD2LM(float CD2LM) {
        this.CD2LM = CD2LM;
    }

    public float getCD3LM() {
        return CD3LM;
    }

    public void setCD3LM(float CD3LM) {
        this.CD3LM = CD3LM;
    }

    public float getD1LM() {
        return D1LM;
    }

    public void setD1LM(float d1LM) {
        D1LM = d1LM;
    }

    public float getD2LM() {
        return D2LM;
    }

    public void setD2LM(float d2LM) {
        D2LM = d2LM;
    }

    public float getD3LM() {
        return D3LM;
    }

    public void setD3LM(float d3LM) {
        D3LM = d3LM;
    }

    public float getD1OC() {
        return D1OC;
    }

    public void setD1OC(float d1OC) {
        D1OC = d1OC;
    }

    public float getD2OC() {
        return D2OC;
    }

    public void setD2OC(float d2OC) {
        D2OC = d2OC;
    }

    public float getD3OC() {
        return D3OC;
    }

    public void setD3OC(float d3OC) {
        D3OC = d3OC;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    private float GAP_RATE;
    private float ML_RANGE;
    private float CO_RANGE;

    @Override
    public String toString() {
        return
                "" + CODE +
                ",'" + N + '\'' +
                ",=" + B_D +
                "," + DATE +
                "," + GAP_RATE +
                "," + ML_RANGE +
                "," + CO_RANGE +
                "," + CD1LM +
                "," + CD2LM +
                "," + CD3LM +
                "," + D1LM +
                "," + D2LM +
                "," + D3LM +
                "," + D1OC +
                "," + D2OC +
                ", " + D3OC +
                ", '" + JSON + '\'';
    }

    //10-30 C for complete
    private float CD1LM;
    private float CD2LM;
    private float CD3LM;
    private float D1LM;
    private float D2LM;
    private float D3LM;
    private float D1OC;
    private float D2OC;
    private float D3OC;
    private String JSON;


    public String createTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER,N TEXT,B_D INTEGER,DATE INTEGER" +
                ",GAP_RATE INTEGER,ML_RANGE INTEGER,CO_RANGE INTEGER,CD1LM INTEGER,CD2LM INTEGER,CD3LM INTEGER,D1LM INTEGER,D2LM INTEGER,D3LM INTEGER,D1OC INTEGER,D2OC INTEGER,D3OC INTEGER" +
                ",JSON TEXT);";
    }


    public String insertTB(String tbName) {
        return "INSERT INTO " + tbName + "(CODE ,N,B_D,DATE" +
                ",GAP_RATE,ML_RANGE ,CO_RANGE ,CD1LM ,CD2LM ,CD3LM ,D1LM ,D2LM ,D3LM " +
                ",D1OC,D2OC ,D3OC ,JSON" +
                ") VALUES (" + toString() + ");";
    }
}
