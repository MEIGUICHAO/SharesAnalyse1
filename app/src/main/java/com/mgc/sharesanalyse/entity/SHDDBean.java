package com.mgc.sharesanalyse.entity;

import org.jetbrains.annotations.NotNull;

public class SHDDBean {

    //                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, C TEXT, N TEXT, AUP INTEGER, MP% INTEGER, LP% INTEGER" +
//                        ",AV INTEGER, AV100 INTEGER, AV50 INTEGER, AV30 INTEGER, AV10 INTEGER, AV5 INTEGER, AV1 INTEGER,AD INTEGER, AD100 INTEGER, AD50 INTEGER, AD30 INTEGER, AD10 INTEGER, AD5 INTEGER, AD1 INTEGER" +
//                        ",PP INTEGER, PP100 INTEGER, PP50 INTEGER, PP30 INTEGER, PP20 INTEGER, PP5 INTEGER, PP1 INTEGER, MP INTEGER, LP INTEGER,MPD TEXT,LPD TEXT, DATE TEXT);"
    private String C;
    private String N;
    private float AUP;
    private float MPER;
    private float LPER;

    public float getAUTR() {
        return AUTR;
    }

    public void setAUTR(float AUTR) {
        this.AUTR = AUTR;
    }

    private float AUTR;
    private float AV;
    private float AV100;
    private float AV50;
    private float AV30;
    private float AV10;
    private float AV5;
    private float AV1;
    private float AD;
    private float AD100;
    private float AD50;
    private float AD30;
    private float AD10;
    private float AD5;
    private float AD1;
    private float PP;
    private float PP100;
    private float PP50;
    private float PP30;
    private float PP10;
    private float PP5;
    private float PP1;
    private float MP;
    private float LP;
    private String MPD;
    private String LPD;
    private String DATE;
    private String AVJ;

    public String getAVJ() {
        return AVJ;
    }

    public void setAVJ(String AVJ) {
        this.AVJ = AVJ;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getN() {
        return N;
    }

    public void setN(String n) {
        N = n;
    }

    public float getAUP() {
        return AUP;
    }

    public void setAUP(float AUP) {
        this.AUP = AUP;
    }

    public float getMPER() {
        return MPER;
    }

    public void setMPER(float MPER) {
        this.MPER = MPER;
    }

    public float getLPER() {
        return LPER;
    }

    public void setLPER(float LPER) {
        this.LPER = LPER;
    }

    public float getAV() {
        return AV;
    }

    public void setAV(float AV) {
        this.AV = AV;
    }

    public float getAV100() {
        return AV100;
    }

    public void setAV100(float AV100) {
        this.AV100 = AV100;
    }

    public float getAV50() {
        return AV50;
    }

    public void setAV50(float AV50) {
        this.AV50 = AV50;
    }

    public float getAV30() {
        return AV30;
    }

    public void setAV30(float AV30) {
        this.AV30 = AV30;
    }

    public float getAV10() {
        return AV10;
    }

    public void setAV10(float AV10) {
        this.AV10 = AV10;
    }

    public float getAV5() {
        return AV5;
    }

    public void setAV5(float AV5) {
        this.AV5 = AV5;
    }

    public float getAV1() {
        return AV1;
    }

    public void setAV1(float AV1) {
        this.AV1 = AV1;
    }

    public float getAD() {
        return AD;
    }

    public void setAD(float AD) {
        this.AD = AD;
    }

    public float getAD100() {
        return AD100;
    }

    public void setAD100(float AD100) {
        this.AD100 = AD100;
    }

    public float getAD50() {
        return AD50;
    }

    public void setAD50(float AD50) {
        this.AD50 = AD50;
    }

    public float getAD30() {
        return AD30;
    }

    public void setAD30(float AD30) {
        this.AD30 = AD30;
    }

    public float getAD10() {
        return AD10;
    }

    public void setAD10(float AD10) {
        this.AD10 = AD10;
    }

    public float getAD5() {
        return AD5;
    }

    public void setAD5(float AD5) {
        this.AD5 = AD5;
    }

    public float getAD1() {
        return AD1;
    }

    public void setAD1(float AD1) {
        this.AD1 = AD1;
    }

    public float getPP() {
        return PP;
    }

    public void setPP(float PP) {
        this.PP = PP;
    }

    public float getPP100() {
        return PP100;
    }

    public void setPP100(float PP100) {
        this.PP100 = PP100;
    }

    public float getPP50() {
        return PP50;
    }

    public void setPP50(float PP50) {
        this.PP50 = PP50;
    }

    public float getPP30() {
        return PP30;
    }

    public void setPP30(float PP30) {
        this.PP30 = PP30;
    }


    public float getPP10() {
        return PP10;
    }

    public void setPP10(float PP10) {
        this.PP10 = PP10;
    }

    public float getPP5() {
        return PP5;
    }

    public void setPP5(float PP5) {
        this.PP5 = PP5;
    }

    public float getPP1() {
        return PP1;
    }

    public void setPP1(float PP1) {
        this.PP1 = PP1;
    }

    public float getMP() {
        return MP;
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

    public String getMPD() {
        return MPD;
    }

    public void setMPD(String MPD) {
        this.MPD = MPD;
    }

    public String getLPD() {
        return LPD;
    }

    public void setLPD(String LPD) {
        this.LPD = LPD;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String toInsert() {
        return "('" + C +
                "',' " + N +
                "', " + AUP +
                ", " + MPER +
                ", " + LPER +
                ", " + AUTR +
                ", " + AD +
                ", " + AD100 +
                ", " + AD50 +
                ", " + AD30 +
                ", " + AD10 +
                ", " + AD5 +
                ", " + AD1 +
                ", " + PP +
                ", " + PP100 +
                ", " + PP50 +
                ", " + PP30 +
                ", " + PP10 +
                ", " + PP5 +
                ", " + PP1 +
                ", " + MP +
                ", " + LP +
                ", " + MPD +
                ", " + LPD +
                ", '" + DATE+"'"+
                ", '" + AVJ+"')";
    }

    public String toUpdateSqlSumValues() {
        return "" +
                " AUP=" + AUP +
                ", MPP=" + MPER +
                ", LPP=" + LPER +
                ", AUTR=" + AUTR +
                ", AD=" + AD +
                ", AD100=" + AD100 +
                ", AD50=" + AD50 +
                ", AD30=" + AD30 +
                ", AD10=" + AD10 +
                ", AD5=" + AD5 +
                ", AD1=" + AD1 +
                ", PP=" + PP +
                ", PP100=" + PP100 +
                ", PP50=" + PP50 +
                ", PP30=" + PP30 +
                ", PP10=" + PP10 +
                ", PP5=" + PP5 +
                ", PP1=" + PP1 +
                ", MP=" + MP +
                ", LP=" + LP +
                ", MPD=" + MPD +
                ", LPD=" + LPD +
                ", DATE='" + DATE +
                "', AVJ='" + AVJ+"'";
    }
}
