package com.mgc.sharesanalyse.entity;

public class TR_K_SLL_Bean implements BaseReverseImp {
    private int CODE;
    private String N;
    private String D_D;
    private String DATE;
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
    private String P_T;

    public String getP_T() {
        return P_T;
    }

    public void setP_T(String p_T) {
        P_T = p_T;
    }

    public String getD_T() {
        return D_T;
    }

    public void setD_T(String d_T) {
        D_T = d_T;
    }

    private String D_T;

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

    public String getD_D() {
        return D_D;
    }

    public void setD_D(String d_D) {
        D_D = d_D;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
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


    @Override
    public String createTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER,N TEXT,D_D TEXT,DATE TEXT," +
                "S_A_TR INTEGER,S_R_TR INTEGER,S_B_TR INTEGER,S_C_TR INTEGER," +
                "K_A_TR INTEGER,K_R_TR INTEGER,K_B_TR INTEGER,K_C_TR INTEGER," +
                "K_SL_A_TR INTEGER,K_SL_R_TR INTEGER,K_SL_B_TR INTEGER,K_SL_C_TR INTEGER);";
    }

    @Override
    public String insertTB(String tbName) {
        return "INSERT INTO " + tbName + "(CODE ,N ,D_D ,DATE," +
                "S_A_TR,S_R_TR ,S_B_TR ,S_C_TR ," +
                "K_A_TR ,K_R_TR ,K_B_TR ,K_C_TR , " +
                "K_SL_A_TR ,K_SL_R_TR ,K_SL_B_TR ,K_SL_C_TR) VALUES (" + toString()+");";
    }

    public String createRevCodeTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, N TEXT,P_T TEXT,D_T TEXT,D_D TEXT,DATE TEXT," +
                "S_A_TR INTEGER,S_R_TR INTEGER,S_B_TR INTEGER,S_C_TR INTEGER," +
                "K_A_TR INTEGER,K_R_TR INTEGER,K_B_TR INTEGER,K_C_TR INTEGER," +
                "K_SL_A_TR INTEGER,K_SL_R_TR INTEGER,K_SL_B_TR INTEGER,K_SL_C_TR INTEGER);";
    }

    public String insertRevCodeTB(String tbName) {
        return "INSERT INTO " + tbName + "(D_T ,P_T  ,N ,D_D ,DATE," +
                "S_A_TR,S_R_TR ,S_B_TR ,S_C_TR ," +
                "K_A_TR ,K_R_TR ,K_B_TR ,K_C_TR , " +
                "K_SL_A_TR ,K_SL_R_TR ,K_SL_B_TR ,K_SL_C_TR) VALUES (" + toRevCodeString()+");";
    }


    @Override
    public String toString() {
        return
                "" + CODE +
                ", '" + N + '\'' +
                ", '" + D_D + '\'' +
                ", '" + DATE + '\'' +
                ", " + S_A_TR +
                ", " + S_R_TR +
                ", " + S_B_TR +
                ", " + S_C_TR +
                ", " + K_A_TR +
                ", " + K_R_TR +
                ", " + K_B_TR +
                ", " + K_C_TR +
                "," + K_SL_A_TR +
                "," + K_SL_R_TR +
                ", " + K_SL_B_TR +
                ", " + K_SL_C_TR ;
    }

    public String toRevCodeString() {

        return "'" +D_T + '\'' +
                ", '" + P_T + '\'' +
                ", '" + N + '\'' +
                ", '" + D_D + '\'' +
                ", '" + DATE + '\'' +
                ", " + S_A_TR +
                ", " + S_R_TR +
                ", " + S_B_TR +
                ", " + S_C_TR +
                ", " + K_A_TR +
                ", " + K_R_TR +
                ", " + K_B_TR +
                ", " + K_C_TR +
                "," + K_SL_A_TR +
                "," + K_SL_R_TR +
                ", " + K_SL_B_TR +
                ", " + K_SL_C_TR ;
    }


    @Override
    public int getCode() {
        return CODE;
    }

}
