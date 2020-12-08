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

    public float getCD10LM() {
        return CD10LM;
    }

    public void setCD10LM(float CD10LM) {
        this.CD10LM = CD10LM;
    }

    public float getCD20LM() {
        return CD20LM;
    }

    public void setCD20LM(float CD20LM) {
        this.CD20LM = CD20LM;
    }

    public float getCD30LM() {
        return CD30LM;
    }

    public void setCD30LM(float CD30LM) {
        this.CD30LM = CD30LM;
    }

    public float getD10LM() {
        return D10LM;
    }

    public void setD10LM(float d10LM) {
        D10LM = d10LM;
    }

    public float getD20LM() {
        return D20LM;
    }

    public void setD20LM(float d20LM) {
        D20LM = d20LM;
    }

    public float getD30LM() {
        return D30LM;
    }

    public void setD30LM(float d30LM) {
        D30LM = d30LM;
    }

    public float getD10OC() {
        return D10OC;
    }

    public void setD10OC(float d10OC) {
        D10OC = d10OC;
    }

    public float getD20OC() {
        return D20OC;
    }

    public void setD20OC(float d20OC) {
        D20OC = d20OC;
    }

    public float getD30OC() {
        return D30OC;
    }

    public void setD30OC(float d30OC) {
        D30OC = d30OC;
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

    public float getCD05LM() {
        return CD05LM;
    }

    public void setCD05LM(float CD05LM) {
        this.CD05LM = CD05LM;
    }

    public float getCD15LM() {
        return CD15LM;
    }

    public void setCD15LM(float CD15LM) {
        this.CD15LM = CD15LM;
    }

    public float getD05LM() {
        return D05LM;
    }

    public void setD05LM(float d05LM) {
        D05LM = d05LM;
    }

    public float getD15LM() {
        return D15LM;
    }

    public void setD15LM(float d15LM) {
        D15LM = d15LM;
    }

    public float getD05OC() {
        return D05OC;
    }

    public void setD05OC(float d05OC) {
        D05OC = d05OC;
    }

    public float getD15OC() {
        return D15OC;
    }

    public void setD15OC(float d15OC) {
        D15OC = d15OC;
    }

    @Override
    public String toString() {
        return
                "" + CODE +
                ",'" + N + '\'' +
                "," + B_D +
                ", " + DATE +
                ", " + GAP_RATE +
                ", " + ML_RANGE +
                ", " + CO_RANGE +
                ", " + CD05LM +
                ", " + CD10LM +
                ", " + CD15LM +
                "," + CD20LM +
                ", " + CD30LM +
                "," + D05LM +
                ", " + D10LM +
                ", " + D15LM +
                ", " + D20LM +
                ", " + D30LM +
                ", " + D05OC +
                "," + D10OC +
                ", " + D15OC +
                "," + D20OC +
                "," + D30OC +
                ", '" + JSON + '\'' ;
    }

    //10-30 C for complete
    private float CD05LM;
    private float CD10LM;
    private float CD15LM;
    private float CD20LM;
    private float CD30LM;
    private float D05LM;
    private float D10LM;
    private float D15LM;
    private float D20LM;
    private float D30LM;
    private float D05OC;
    private float D10OC;
    private float D15OC;
    private float D20OC;
    private float D30OC;
    private String JSON;


    public String createTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER,N TEXT,B_D INTEGER,DATE INTEGER" +
                ",GAP_RATE INTEGER,ML_RANGE INTEGER,CO_RANGE INTEGER,CD05LM INTEGER,CD10LM INTEGER,CD15LM INTEGER,CD20LM INTEGER,CD30LM INTEGER,D05LM INTEGER,D10LM INTEGER,D15LM INTEGER,D20LM INTEGER,D30LM INTEGER,D05OC INTEGER,D10OC INTEGER,D15OC INTEGER,D20OC INTEGER,D30OC INTEGER" +
                ",JSON TEXT);";
    }


    public String insertTB(String tbName) {
        return "INSERT INTO " + tbName + "(CODE ,N,B_D,DATE" +
                ",GAP_RATE,ML_RANGE ,CO_RANGE ,CD05LM ,CD10LM,CD15LM ,CD20LM ,CD30LM ,D05LM ,D10LM ,D15LM ,D20LM ,D30LM " +
                ",D05OC,D10OC,D15OC,D20OC ,D30OC ,JSON" +
                ") VALUES (" + toString() + ");";
    }
}
