package com.mgc.sharesanalyse.entity;

public class CodeHDDBean {

    private String name;
    private String DATE;
    private String TR;
    private double OP;

    public String getTR() {
        return TR;
    }

    public void setTR(String TR) {
        this.TR = TR;
    }

    private double CP;
    private double PP;

    public double getPP() {
        return PP;
    }

    public void setPP(double PP) {
        this.PP = PP;
    }

    private double P;

    public double getOP() {
        return OP;
    }

    public void setOP(double OP) {
        this.OP = OP;
    }

    public double getCP() {
        return CP;
    }

    public void setCP(double CP) {
        this.CP = CP;
    }

    private double AUP;
    private double DV;
    private double DA;
    private double AS;
    private double M100S;
    private double M50S;
    private double M30S;
    private double M10S;
    private double M5S;
    private double M1S;
    private double M05S;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public double getP() {
        return P;
    }

    public void setP(double p) {
        P = p;
    }

    public double getAUP() {
        return AUP;
    }

    public void setAUP(double AUP) {
        this.AUP = AUP;
    }

    public double getDV() {
        return DV;
    }

    public void setDV(double DV) {
        this.DV = DV;
    }

    public double getDA() {
        return DA;
    }

    public void setDA(double DA) {
        this.DA = DA;
    }

    public double getAS() {
        return AS;
    }

    public void setAS(double AS) {
        this.AS = AS;
    }

    public double getM100S() {
        return M100S;
    }

    public void setM100S(double m100S) {
        M100S = m100S;
    }

    public double getM50S() {
        return M50S;
    }

    public void setM50S(double m50S) {
        M50S = m50S;
    }

    public double getM30S() {
        return M30S;
    }

    public void setM30S(double m30S) {
        M30S = m30S;
    }

    public double getM10S() {
        return M10S;
    }

    public void setM10S(double m10S) {
        M10S = m10S;
    }

    public double getM5S() {
        return M5S;
    }

    public void setM5S(double m5S) {
        M5S = m5S;
    }

    public double getM1S() {
        return M1S;
    }

    public void setM1S(double m1S) {
        M1S = m1S;
    }

    public double getM05S() {
        return M05S;
    }

    public void setM05S(double m05S) {
        M05S = m05S;
    }

    public double getM01S() {
        return M01S;
    }

    public void setM01S(double m01S) {
        M01S = m01S;
    }

    public double getL01S() {
        return L01S;
    }

    public void setL01S(double l01S) {
        L01S = l01S;
    }

    public double getPP100M() {
        return PP100M;
    }

    public void setPP100M(double PP100M) {
        this.PP100M = PP100M;
    }

    public double getPP50M() {
        return PP50M;
    }

    public void setPP50M(double PP50M) {
        this.PP50M = PP50M;
    }

    public double getPP30M() {
        return PP30M;
    }

    public void setPP30M(double PP30M) {
        this.PP30M = PP30M;
    }

    public double getPP10M() {
        return PP10M;
    }

    public void setPP10M(double PP10M) {
        this.PP10M = PP10M;
    }

    public double getPP5M() {
        return PP5M;
    }

    public void setPP5M(double PP5M) {
        this.PP5M = PP5M;
    }

    public double getPP1M() {
        return PP1M;
    }

    public void setPP1M(double PP1M) {
        this.PP1M = PP1M;
    }


    public double getDAA() {
        return DAA;
    }

    public void setDAA(double DAA) {
        this.DAA = DAA;
    }

    public double getDA5000() {
        return DA5000;
    }

    public void setDA5000(double DA5000) {
        this.DA5000 = DA5000;
    }

    public double getDA1000() {
        return DA1000;
    }

    public void setDA1000(double DA1000) {
        this.DA1000 = DA1000;
    }

    public double getDA500() {
        return DA500;
    }

    public void setDA500(double DA500) {
        this.DA500 = DA500;
    }

    public double getDA100() {
        return DA100;
    }

    public void setDA100(double DA100) {
        this.DA100 = DA100;
    }

    private double M01S;
    private double L01S;
    private double PP100M;
    private double PP50M;
    private double PP30M;
    private double PP10M;
    private double PP5M;


    public String toInsertDBValues(String dbName) {
        return "INSERT INTO " + dbName + "( NAME , DATE , OP , CP , PP , P , AUP ,TR, DV , DA , AS " +
                ", M100S , M50S , M30S , M10S , M5S , M1S , M05S , M01S , L01S , PP100M , PP50M , PP30M , PP10M , PP5M , PP1M , PPL1M " +
                ", DAA , DA5000 , DA1000 , DA500 , DA100 ) " +
                "VALUES('" + name + "'" +
                ",'" + DATE + "'" +
                ", " + OP +
                ", " + CP +
                ", " + PP +
                ", " + P +
                ", " + AUP +
                ",'" + TR +
                "', " + DV +
                ", " + DA +
                ", " + AS +
                ", " + M100S +
                ", " + M50S +
                ", " + M30S +
                ", " + M10S +
                ", " + M5S +
                ", " + M1S +
                ", " + M05S +
                ", " + M01S +
                ", " + L01S +
                ", " + PP100M +
                ", " + PP50M +
                ", " + PP30M +
                ", " + PP10M +
                ", " + PP5M +
                ", " + PP1M +
                ", " + PPL1M +
                ", " + DAA +
                ", " + DA5000 +
                ", " + DA1000 +
                ", " + DA500 +
                ", " + DA100 + ")";
    }

    private double PP1M;
    private double PPL1M;

    public double getPPL1M() {
        return PPL1M;
    }

    public void setPPL1M(double PPL1M) {
        this.PPL1M = PPL1M;
    }

    private double DAA;
    private double DA5000;
    private double DA1000;
    private double DA500;
    private double DA100;
}
