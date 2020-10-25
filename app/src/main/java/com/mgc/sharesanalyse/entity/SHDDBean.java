package com.mgc.sharesanalyse.entity;

import org.jetbrains.annotations.NotNull;

public class SHDDBean {

    //                "CREATE TABLE IF NOT EXISTS $dbName(_ID INTEGER PRIMARY KEY AUTOINCREMENT, C TEXT, N TEXT, AUP INTEGER, MP% INTEGER, LP% INTEGER" +
//                        ",AV INTEGER, AV100 INTEGER, AV50 INTEGER, AV30 INTEGER, AV10 INTEGER, AV5 INTEGER, AV1 INTEGER,AD INTEGER, AD100 INTEGER, AD50 INTEGER, AD30 INTEGER, AD10 INTEGER, AD5 INTEGER, AD1 INTEGER" +
//                        ",PP INTEGER, PP100 INTEGER, PP50 INTEGER, PP30 INTEGER, PP20 INTEGER, PP5 INTEGER, PP1 INTEGER, MP INTEGER, LP INTEGER,MPD TEXT,LPD TEXT, DATE TEXT);"
    private String C;
    private String N;
    private double AUP;
    private double MPER;
    private double LPER;

    public double getAUTR() {
        return AUTR;
    }

    public void setAUTR(double AUTR) {
        this.AUTR = AUTR;
    }

    private double AUTR;
    private double AV;
    private double AV100;
    private double AV50;
    private double AV30;
    private double AV10;
    private double AV5;
    private double AV1;
    private double AD;
    private double AD100;
    private double AD50;
    private double AD30;
    private double AD10;
    private double AD5;
    private double AD1;
    private double PP;
    private double PP100;
    private double PP50;
    private double PP30;
    private double PP10;
    private double PP5;
    private double PP1;
    private double MP;
    private double LP;
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

    public double getAUP() {
        return AUP;
    }

    public void setAUP(double AUP) {
        this.AUP = AUP;
    }

    public double getMPER() {
        return MPER;
    }

    public void setMPER(double MPER) {
        this.MPER = MPER;
    }

    public double getLPER() {
        return LPER;
    }

    public void setLPER(double LPER) {
        this.LPER = LPER;
    }

    public double getAV() {
        return AV;
    }

    public void setAV(double AV) {
        this.AV = AV;
    }

    public double getAV100() {
        return AV100;
    }

    public void setAV100(double AV100) {
        this.AV100 = AV100;
    }

    public double getAV50() {
        return AV50;
    }

    public void setAV50(double AV50) {
        this.AV50 = AV50;
    }

    public double getAV30() {
        return AV30;
    }

    public void setAV30(double AV30) {
        this.AV30 = AV30;
    }

    public double getAV10() {
        return AV10;
    }

    public void setAV10(double AV10) {
        this.AV10 = AV10;
    }

    public double getAV5() {
        return AV5;
    }

    public void setAV5(double AV5) {
        this.AV5 = AV5;
    }

    public double getAV1() {
        return AV1;
    }

    public void setAV1(double AV1) {
        this.AV1 = AV1;
    }

    public double getAD() {
        return AD;
    }

    public void setAD(double AD) {
        this.AD = AD;
    }

    public double getAD100() {
        return AD100;
    }

    public void setAD100(double AD100) {
        this.AD100 = AD100;
    }

    public double getAD50() {
        return AD50;
    }

    public void setAD50(double AD50) {
        this.AD50 = AD50;
    }

    public double getAD30() {
        return AD30;
    }

    public void setAD30(double AD30) {
        this.AD30 = AD30;
    }

    public double getAD10() {
        return AD10;
    }

    public void setAD10(double AD10) {
        this.AD10 = AD10;
    }

    public double getAD5() {
        return AD5;
    }

    public void setAD5(double AD5) {
        this.AD5 = AD5;
    }

    public double getAD1() {
        return AD1;
    }

    public void setAD1(double AD1) {
        this.AD1 = AD1;
    }

    public double getPP() {
        return PP;
    }

    public void setPP(double PP) {
        this.PP = PP;
    }

    public double getPP100() {
        return PP100;
    }

    public void setPP100(double PP100) {
        this.PP100 = PP100;
    }

    public double getPP50() {
        return PP50;
    }

    public void setPP50(double PP50) {
        this.PP50 = PP50;
    }

    public double getPP30() {
        return PP30;
    }

    public void setPP30(double PP30) {
        this.PP30 = PP30;
    }


    public double getPP10() {
        return PP10;
    }

    public void setPP10(double PP10) {
        this.PP10 = PP10;
    }

    public double getPP5() {
        return PP5;
    }

    public void setPP5(double PP5) {
        this.PP5 = PP5;
    }

    public double getPP1() {
        return PP1;
    }

    public void setPP1(double PP1) {
        this.PP1 = PP1;
    }

    public double getMP() {
        return MP;
    }

    public void setMP(double MP) {
        this.MP = MP;
    }

    public double getLP() {
        return LP;
    }

    public void setLP(double LP) {
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
