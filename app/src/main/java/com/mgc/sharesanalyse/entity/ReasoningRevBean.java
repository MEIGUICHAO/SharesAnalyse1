package com.mgc.sharesanalyse.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private int L36;
    private int L30;
    private int L25;
    private int L20;
    private int L15;
    private int L10;
    private int L05;
    private int L03;

    private int C36;
    private int C30;
    private int C25;
    private int C20;
    private int C15;
    private int C10;
    private int C05;
    private int C03;

    private int O36;
    private int O30;
    private int O25;
    private int O20;
    private int O15;
    private int O10;
    private int O05;
    private int O03;
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


    public int getL36() {
        return L36;
    }

    public void setL36(int l36) {
        L36 = l36;
    }

    public int getL30() {
        return L30;
    }

    public void setL30(int l30) {
        L30 = l30;
    }

    public int getL25() {
        return L25;
    }

    public void setL25(int l25) {
        L25 = l25;
    }

    public int getL20() {
        return L20;
    }

    public void setL20(int l20) {
        L20 = l20;
    }

    public int getL15() {
        return L15;
    }

    public void setL15(int l15) {
        L15 = l15;
    }

    public int getL10() {
        return L10;
    }

    public void setL10(int l10) {
        L10 = l10;
    }

    public int getL05() {
        return L05;
    }

    public void setL05(int l05) {
        L05 = l05;
    }

    public int getL03() {
        return L03;
    }

    public void setL03(int l03) {
        L03 = l03;
    }

    public int getC36() {
        return C36;
    }

    public void setC36(int c36) {
        C36 = c36;
    }

    public int getC30() {
        return C30;
    }

    public void setC30(int c30) {
        C30 = c30;
    }

    public int getC25() {
        return C25;
    }

    public void setC25(int c25) {
        C25 = c25;
    }

    public int getC20() {
        return C20;
    }

    public void setC20(int c20) {
        C20 = c20;
    }

    public int getC15() {
        return C15;
    }

    public void setC15(int c15) {
        C15 = c15;
    }

    public int getC10() {
        return C10;
    }

    public void setC10(int c10) {
        C10 = c10;
    }

    public int getC05() {
        return C05;
    }

    public void setC05(int c05) {
        C05 = c05;
    }

    public int getC03() {
        return C03;
    }

    public void setC03(int c03) {
        C03 = c03;
    }

    public int getO36() {
        return O36;
    }

    public void setO36(int o36) {
        O36 = o36;
    }

    public int getO30() {
        return O30;
    }

    public void setO30(int o30) {
        O30 = o30;
    }

    public int getO25() {
        return O25;
    }

    public void setO25(int o25) {
        O25 = o25;
    }

    public int getO20() {
        return O20;
    }

    public void setO20(int o20) {
        O20 = o20;
    }

    public int getO15() {
        return O15;
    }

    public void setO15(int o15) {
        O15 = o15;
    }

    public int getO10() {
        return O10;
    }

    public void setO10(int o10) {
        O10 = o10;
    }

    public int getO05() {
        return O05;
    }

    public void setO05(int o05) {
        O05 = o05;
    }

    public int getO03() {
        return O03;
    }

    public void setO03(int o03) {
        O03 = o03;
    }

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
                "," +SHOW +
                "," +FRATE +
                "," +RRATE +
                "," +SIZE +
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
                ", " + MA_10 +
                ", " + L36 +
                ", " + L30 +
                ", " + L25 +
                ", " + L20 +
                ", " + L15 +
                ", " + L10 +
                ", " + L05 +
                ", " + L03 +
                ", " + C36 +
                ", " + C30 +
                ", " + C25 +
                ", " + C20 +
                ", " + C15 +
                ", " + C10 +
                ", " + C05 +
                ", " + C03 +
                ", " + O36 +
                ", " + O30 +
                ", " + O25 +
                ", " + O20 +
                ", " + O15 +
                ", " + O10 +
                ", " + O05 +
                ", " + O03
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
                ",SHOW INTEGER,FRATE INTEGER,RRATE INTEGER,SIZE INTEGER,"+
                ",F36_T INTEGER,F30_T INTEGER,F25_T INTEGER,F20_T INTEGER,F15_T INTEGER,F10_T INTEGER,F05_T INTEGER,F03_T INTEGER,MA1 INTEGER,MA3 INTEGER,MA5 INTEGER,MA10 INTEGER" +
                ",L36 INTEGER,L30 INTEGER,L25 INTEGER,L20 INTEGER,L15 INTEGER,L10 INTEGER,L05 INTEGER,L03 INTEGER"+
                ",C36 INTEGER,C30 INTEGER,C25 INTEGER,C20 INTEGER,C15 INTEGER,C10 INTEGER,C05 INTEGER,C03 INTEGER"+
                ",O36 INTEGER,O30 INTEGER,O25 INTEGER,O20 INTEGER,O15 INTEGER,O10 INTEGER,O05 INTEGER,O03 INTEGER"+
                ");";
    }



    public String insertAllTB(String tbName) {
        return "INSERT INTO " + tbName + "(CODE ,N ,D ,D_D ,P,MP,LP,AFTER_O_P,AFTER_C_P" +
                ",SHOW ,FRATE ,RRATE ,SIZE "+
                ",F36_T,F30_T,F25_T,F20_T,F15_T,F10_T,F05_T,F03_T,MA1 ,MA3 ,MA5 ,MA10" +
                ",L36 ,L30 ,L25 ,L20 ,L15 ,L10 ,L05 ,L03 "+
                ",C36 ,C30 ,C25 ,C20 ,C15 ,C10 ,C05 ,C03 "+
                ",O36 ,O30 ,O25 ,O20 ,O15 ,O10 ,O05 ,O03 "+
                " ) VALUES (" + toAllString()+")";
    }


    public String createOCOOTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER,N TEXT,D TEXT,D_D TEXT,P INTEGER,MP INTEGER,LP INTEGER,AFTER_O_P INTEGER,AFTER_C_P INTEGER," +
                "SHOW INTEGER,FRATE INTEGER,RRATE INTEGER,SIZE INTEGER,"+
                "OC3 INTEGER,OC5 INTEGER,OC10 INTEGER,OC15 INTEGER,OC20 INTEGER,OC25 INTEGER,OC30 INTEGER,OC35 INTEGER,OC40 INTEGER,OC45 INTEGER,OC50 INTEGER,OC55 INTEGER,OC60 INTEGER,OC65 INTEGER,OC70 INTEGER," +
                "OO3 INTEGER,OO5 INTEGER,OO10 INTEGER,OO15 INTEGER,OO20 INTEGER,OO25 INTEGER,OO30 INTEGER,OO35 INTEGER,OO40 INTEGER,OO45 INTEGER,OO50 INTEGER,OO55 INTEGER,OO60 INTEGER,OO65 INTEGER,OO70 INTEGER,"+
                "S_A_TR INTEGER,S_R_TR INTEGER,S_B_TR INTEGER,S_C_TR INTEGER,K_A_TR INTEGER,K_R_TR INTEGER,K_B_TR INTEGER,K_C_TR INTEGER,K_SL_A_TR INTEGER,K_SL_R_TR INTEGER,K_SL_B_TR INTEGER,K_SL_C_TR INTEGER);";
    }


    private String toOCOOString() {
        return  CODE +
                ", '" + N + '\'' +
                ", '" + D + '\'' +
                ", '" + D_D + '\'' +
                ", " + P  +
                ", " + MP  +
                ", " + LP  +
                ", " + After_O_P  +
                ", " + After_C_P  +", " +
                SHOW +"," +
                FRATE +"," +
                RRATE +"," +
                SIZE +"," +
                OC3 +"," +
                OC5 +"," +
                OC10 +"," +
                OC15 +"," +
                OC20 +"," +
                OC25 +"," +
                OC30 +"," +
                OC35 +"," +
                OC40 +"," +
                OC45 +"," +
                OC50 +"," +
                OC55 +"," +
                OC60 +"," +
                OC65 +"," +
                OC70 +"," +
                OO3 +"," +
                OO5 +"," +
                OO10 +"," +
                OO15 +"," +
                OO20 +"," +
                OO25 +"," +
                OO30 +"," +
                OO35 +"," +
                OO40 +"," +
                OO45 +"," +
                OO50 +"," +
                OO55 +"," +
                OO60 +"," +
                OO65 +"," +
                OO70 +"," +
                S_A_TR +"," +
                S_R_TR +"," +
                S_B_TR +"," +
                S_C_TR +"," +
                K_A_TR +"," +
                K_R_TR +"," +
                K_B_TR +"," +
                K_C_TR +"," +
                K_SL_A_TR +"," +
                K_SL_R_TR +"," +
                K_SL_B_TR +"," +
                K_SL_C_TR ;
    }

    private float OC3;
    private float OC5;
    private float OC10;
    private float OC15;
    private float OC20;
    private float OC25;
    private float OC30;
    private float OC35;
    private float OC40;
    private float OC45;
    private float OC50;
    private float OC55;
    private float OC60;
    private float OC65;
    private float OC70;

    private float OO3;
    private float OO5;
    private float OO10;
    private float OO15;
    private float OO20;
    private float OO25;
    private float OO30;
    private float OO35;
    private float OO40;
    private float OO45;
    private float OO50;
    private float OO55;
    private float OO60;
    private float OO65;
    private float OO70;

    private float S_A_TR;
    private float S_R_TR;
    private float S_B_TR;
    private float S_C_TR;
    private float K_A_TR;
    private float K_R_TR;
    private float K_B_TR;
    private float K_C_TR;
    private float K_SL_A_TR;
    private float K_SL_R_TR;
    private float K_SL_B_TR;
    private float K_SL_C_TR;

    public float getOC3() {
        return OC3;
    }

    public void setOC3(float OC3) {
        this.OC3 = OC3;
    }

    public float getOC5() {
        return OC5;
    }

    public void setOC5(float OC5) {
        this.OC5 = OC5;
    }

    public float getOC10() {
        return OC10;
    }

    public void setOC10(float OC10) {
        this.OC10 = OC10;
    }

    public float getOC15() {
        return OC15;
    }

    public void setOC15(float OC15) {
        this.OC15 = OC15;
    }

    public float getOC20() {
        return OC20;
    }

    public void setOC20(float OC20) {
        this.OC20 = OC20;
    }

    public float getOC25() {
        return OC25;
    }

    public void setOC25(float OC25) {
        this.OC25 = OC25;
    }

    public float getOC30() {
        return OC30;
    }

    public void setOC30(float OC30) {
        this.OC30 = OC30;
    }

    public float getOC35() {
        return OC35;
    }

    public void setOC35(float OC35) {
        this.OC35 = OC35;
    }

    public float getOC40() {
        return OC40;
    }

    public void setOC40(float OC40) {
        this.OC40 = OC40;
    }

    public float getOC45() {
        return OC45;
    }

    public void setOC45(float OC45) {
        this.OC45 = OC45;
    }

    public float getOC50() {
        return OC50;
    }

    public void setOC50(float OC50) {
        this.OC50 = OC50;
    }

    public float getOC55() {
        return OC55;
    }

    public void setOC55(float OC55) {
        this.OC55 = OC55;
    }

    public float getOC60() {
        return OC60;
    }

    public void setOC60(float OC60) {
        this.OC60 = OC60;
    }

    public float getOC65() {
        return OC65;
    }

    public void setOC65(float OC65) {
        this.OC65 = OC65;
    }

    public float getOC70() {
        return OC70;
    }

    public void setOC70(float OC70) {
        this.OC70 = OC70;
    }

    public float getOO3() {
        return OO3;
    }

    public void setOO3(float OO3) {
        this.OO3 = OO3;
    }

    public float getOO5() {
        return OO5;
    }

    public void setOO5(float OO5) {
        this.OO5 = OO5;
    }

    public float getOO10() {
        return OO10;
    }

    public void setOO10(float OO10) {
        this.OO10 = OO10;
    }

    public float getOO15() {
        return OO15;
    }

    public void setOO15(float OO15) {
        this.OO15 = OO15;
    }

    public float getOO20() {
        return OO20;
    }

    public void setOO20(float OO20) {
        this.OO20 = OO20;
    }

    public float getOO25() {
        return OO25;
    }

    public void setOO25(float OO25) {
        this.OO25 = OO25;
    }

    public float getOO30() {
        return OO30;
    }

    public void setOO30(float OO30) {
        this.OO30 = OO30;
    }

    public float getOO35() {
        return OO35;
    }

    public void setOO35(float OO35) {
        this.OO35 = OO35;
    }

    public float getOO40() {
        return OO40;
    }

    public void setOO40(float OO40) {
        this.OO40 = OO40;
    }

    public float getOO45() {
        return OO45;
    }

    public void setOO45(float OO45) {
        this.OO45 = OO45;
    }

    public float getOO50() {
        return OO50;
    }

    public void setOO50(float OO50) {
        this.OO50 = OO50;
    }

    public float getOO55() {
        return OO55;
    }

    public void setOO55(float OO55) {
        this.OO55 = OO55;
    }

    public float getOO60() {
        return OO60;
    }

    public void setOO60(float OO60) {
        this.OO60 = OO60;
    }

    public float getOO65() {
        return OO65;
    }

    public void setOO65(float OO65) {
        this.OO65 = OO65;
    }

    public float getOO70() {
        return OO70;
    }

    public void setOO70(float OO70) {
        this.OO70 = OO70;
    }

    public float getS_A_TR() {
        return S_A_TR;
    }

    public void setS_A_TR(float s_A_TR) {
        S_A_TR = s_A_TR;
    }

    public float getS_R_TR() {
        return S_R_TR;
    }

    public void setS_R_TR(float s_R_TR) {
        S_R_TR = s_R_TR;
    }

    public float getS_B_TR() {
        return S_B_TR;
    }

    public void setS_B_TR(float s_B_TR) {
        S_B_TR = s_B_TR;
    }

    public float getS_C_TR() {
        return S_C_TR;
    }

    public void setS_C_TR(float s_C_TR) {
        S_C_TR = s_C_TR;
    }

    public float getK_A_TR() {
        return K_A_TR;
    }

    public void setK_A_TR(float k_A_TR) {
        K_A_TR = k_A_TR;
    }

    public float getK_R_TR() {
        return K_R_TR;
    }

    public void setK_R_TR(float k_R_TR) {
        K_R_TR = k_R_TR;
    }

    public float getK_B_TR() {
        return K_B_TR;
    }

    public void setK_B_TR(float k_B_TR) {
        K_B_TR = k_B_TR;
    }

    public float getK_C_TR() {
        return K_C_TR;
    }

    public void setK_C_TR(float k_C_TR) {
        K_C_TR = k_C_TR;
    }

    public float getK_SL_A_TR() {
        return K_SL_A_TR;
    }

    public void setK_SL_A_TR(float k_SL_A_TR) {
        K_SL_A_TR = k_SL_A_TR;
    }

    public float getK_SL_R_TR() {
        return K_SL_R_TR;
    }

    public void setK_SL_R_TR(float k_SL_R_TR) {
        K_SL_R_TR = k_SL_R_TR;
    }

    public float getK_SL_B_TR() {
        return K_SL_B_TR;
    }

    public void setK_SL_B_TR(float k_SL_B_TR) {
        K_SL_B_TR = k_SL_B_TR;
    }

    public float getK_SL_C_TR() {
        return K_SL_C_TR;
    }

    public void setK_SL_C_TR(float k_SL_C_TR) {
        K_SL_C_TR = k_SL_C_TR;
    }

    @Nullable
    public String insertOCOOTB(@NotNull String tbName) {

        return "INSERT INTO " + tbName + "( CODE ,N ,D ,D_D ,P ,MP ,LP ,AFTER_O_P ,AFTER_C_P ,SHOW,FRATE,RRATE,SIZE," +
                " OC3 ,OC5 ,OC10 ,OC15 ,OC20 ,OC25 ,OC30 ,OC35 ,OC40 ,OC45 ,OC50 ,OC55 ,OC60 ,OC65 ,OC70 ," +
                " OO3 ,OO5 ,OO10 ,OO15 ,OO20 ,OO25 ,OO30 ,OO35 ,OO40 ,OO45 ,OO50 ,OO55 ,OO60 ,OO65 ,OO70 ," +
                " S_A_TR ,S_R_TR ,S_B_TR ,S_C_TR ,K_A_TR ,K_R_TR ,K_B_TR ,K_C_TR ,K_SL_A_TR ,K_SL_R_TR ,K_SL_B_TR ,K_SL_C_TR  "+
                " ) VALUES (" + toOCOOString()+")";
    }

    int SHOW;
    float FRATE;
    float RRATE;
    int SIZE;

    public int getSHOW() {
        return SHOW;
    }

    public void setSHOW(int SHOW) {
        this.SHOW = SHOW;
    }

    public float getFRATE() {
        return FRATE;
    }

    public void setFRATE(float FRATE) {
        this.FRATE = FRATE;
    }

    public float getRRATE() {
        return RRATE;
    }

    public void setRRATE(float RRATE) {
        this.RRATE = RRATE;
    }
}
