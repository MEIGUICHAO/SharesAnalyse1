package com.mgc.sharesanalyse.entity;

public class CodeHDDBean {

    private String name;
    private String DATE;
    private String TR;
    private float OP;

    public String getTR() {
        return TR;
    }

    public void setTR(String TR) {
        this.TR = TR;
    }

    private float CP;
    private float PP;

    public float getPP() {
        return PP;
    }

    public void setPP(float PP) {
        this.PP = PP;
    }

    private float P;

    public float getOP() {
        return OP;
    }

    public void setOP(float OP) {
        this.OP = OP;
    }

    public float getCP() {
        return CP;
    }

    public void setCP(float CP) {
        this.CP = CP;
    }

    private float AUP;
    private float DV;
    private float DA;
    private float AS;
    private int M100S;
    private int M50S;
    private int M30S;
    private int M10S;
    private int M5S;
    private int M1S;
    private int M05S;

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

    public float getP() {
        return P;
    }

    public void setP(float p) {
        P = p;
    }

    public float getAUP() {
        return AUP;
    }

    public void setAUP(float AUP) {
        this.AUP = AUP;
    }

    public float getDV() {
        return DV;
    }

    public void setDV(float DV) {
        this.DV = DV;
    }

    public float getDA() {
        return DA;
    }

    public void setDA(float DA) {
        this.DA = DA;
    }

    public float getAS() {
        return AS;
    }

    public void setAS(float AS) {
        this.AS = AS;
    }

    public int getM100S() {
        return M100S;
    }

    public void setM100S(int m100S) {
        M100S = m100S;
    }

    public int getM50S() {
        return M50S;
    }

    public void setM50S(int m50S) {
        M50S = m50S;
    }

    public int getM30S() {
        return M30S;
    }

    public void setM30S(int m30S) {
        M30S = m30S;
    }

    public int getM10S() {
        return M10S;
    }

    public void setM10S(int m10S) {
        M10S = m10S;
    }

    public int getM5S() {
        return M5S;
    }

    public void setM5S(int m5S) {
        M5S = m5S;
    }

    public int getM1S() {
        return M1S;
    }

    public void setM1S(int m1S) {
        M1S = m1S;
    }

    public int getM05S() {
        return M05S;
    }

    public void setM05S(int m05S) {
        M05S = m05S;
    }

    public int getM01S() {
        return M01S;
    }

    public void setM01S(int m01S) {
        M01S = m01S;
    }

    public int getL01S() {
        return L01S;
    }

    public void setL01S(int l01S) {
        L01S = l01S;
    }

    public float getPP100M() {
        return PP100M;
    }

    public void setPP100M(float PP100M) {
        this.PP100M = PP100M;
    }

    public float getPP50M() {
        return PP50M;
    }

    public void setPP50M(float PP50M) {
        this.PP50M = PP50M;
    }

    public float getPP30M() {
        return PP30M;
    }

    public void setPP30M(float PP30M) {
        this.PP30M = PP30M;
    }

    public float getPP10M() {
        return PP10M;
    }

    public void setPP10M(float PP10M) {
        this.PP10M = PP10M;
    }

    public float getPP5M() {
        return PP5M;
    }

    public void setPP5M(float PP5M) {
        this.PP5M = PP5M;
    }

    public float getPP1M() {
        return PP1M;
    }

    public void setPP1M(float PP1M) {
        this.PP1M = PP1M;
    }



    public float getDA5000() {
        return DA5000;
    }

    public void setDA5000(float DA5000) {
        this.DA5000 = DA5000;
    }

    public float getDA1000() {
        return DA1000;
    }

    public void setDA1000(float DA1000) {
        this.DA1000 = DA1000;
    }

    public float getDA500() {
        return DA500;
    }

    public void setDA500(float DA500) {
        this.DA500 = DA500;
    }

    public float getDA100() {
        return DA100;
    }

    public void setDA100(float DA100) {
        this.DA100 = DA100;
    }

    private int M01S;
    private int L01S;
    private float PP100M;
    private float PP50M;
    private float PP30M;
    private float PP10M;
    private float PP5M;


    public String toInsertDBValues(String dbName) {
        return "INSERT INTO " + dbName + "( NAME , DATE , OP , CP , PP , P , AUP ,TR, DV , DA , AllS " +
                ", M100S , M50S , M30S , M10S , M5S , M1S , M05S , M01S , L01S , PP100M , PP50M , PP30M , PP10M , PP5M , PP1M , PPL1M " +
                " , DA5000 , DA1000 , DA500 , DA100 ) " +
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
                ", " + DA5000 +
                ", " + DA1000 +
                ", " + DA500 +
                ", " + DA100 + ")";
    }

    private float PP1M;
    private float PPL1M;

    public float getPPL1M() {
        return PPL1M;
    }

    public void setPPL1M(float PPL1M) {
        this.PPL1M = PPL1M;
    }

    private float DA5000;
    private float DA1000;
    private float DA500;
    private float DA100;
}
