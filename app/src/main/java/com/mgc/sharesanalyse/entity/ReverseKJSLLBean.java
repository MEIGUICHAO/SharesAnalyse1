package com.mgc.sharesanalyse.entity;

public class ReverseKJSLLBean  implements BaseReverseImp  {

    private int code;
    private String N;
    private String D_D;
    private String date;
    private float U_D03;
    private float U_D05;
    private float U_D10;
    private float U_D15;
    private float U_D20;
    private float U_D25;
    private float U_D30;
    private float U_D60;
    private float U_D72;

    public void setCode(int code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getU_D03() {
        return U_D03;
    }

    public void setU_D03(float u_D03) {
        U_D03 = u_D03;
    }

    public float getU_D05() {
        return U_D05;
    }

    public void setU_D05(float u_D05) {
        U_D05 = u_D05;
    }

    public float getU_D10() {
        return U_D10;
    }

    public void setU_D10(float u_D10) {
        U_D10 = u_D10;
    }

    public float getU_D15() {
        return U_D15;
    }

    public void setU_D15(float u_D15) {
        U_D15 = u_D15;
    }

    public float getU_D20() {
        return U_D20;
    }

    public void setU_D20(float u_D20) {
        U_D20 = u_D20;
    }

    public float getU_D25() {
        return U_D25;
    }

    public void setU_D25(float u_D25) {
        U_D25 = u_D25;
    }

    public float getU_D30() {
        return U_D30;
    }

    public void setU_D30(float u_D30) {
        U_D30 = u_D30;
    }

    public float getU_D60() {
        return U_D60;
    }

    public void setU_D60(float u_D60) {
        U_D60 = u_D60;
    }

    public float getU_D72() {
        return U_D72;
    }

    public void setU_D72(float u_D72) {
        U_D72 = u_D72;
    }

    public float getD_D03() {
        return D_D03;
    }

    public void setD_D03(float d_D03) {
        D_D03 = d_D03;
    }

    public float getD_D05() {
        return D_D05;
    }

    public void setD_D05(float d_D05) {
        D_D05 = d_D05;
    }

    public float getD_D10() {
        return D_D10;
    }

    public void setD_D10(float d_D10) {
        D_D10 = d_D10;
    }

    public float getD_D15() {
        return D_D15;
    }

    public void setD_D15(float d_D15) {
        D_D15 = d_D15;
    }

    public float getD_D20() {
        return D_D20;
    }

    public void setD_D20(float d_D20) {
        D_D20 = d_D20;
    }

    public float getD_D25() {
        return D_D25;
    }

    public void setD_D25(float d_D25) {
        D_D25 = d_D25;
    }

    public float getD_D30() {
        return D_D30;
    }

    public void setD_D30(float d_D30) {
        D_D30 = d_D30;
    }

    public float getD_D60() {
        return D_D60;
    }

    public void setD_D60(float d_D60) {
        D_D60 = d_D60;
    }

    public float getD_D72() {
        return D_D72;
    }

    public void setD_D72(float d_D72) {
        D_D72 = d_D72;
    }

    private float D_D03;
    private float D_D05;
    private float D_D10;
    private float D_D15;
    private float D_D20;
    private float D_D25;
    private float D_D30;

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

    @Override
    public String toString() {
        return
                "" + code +
                        ", '" + N + '\'' +
                        ", '" + D_D + '\'' +
                        ", '" + date + '\'' +
                        ", " + U_D03 * 1000 / 3 +
                        ", " + U_D05 * 1000 / 5 +
                        ", " + U_D10 * 1000 / 10 +
                        ", " + U_D15 * 1000 / 15 +
                        ", " + U_D20 * 1000 / 20 +
                        ", " + U_D25 * 1000 / 25 +
                        ", " + U_D30 * 1000 / 30 +
                        ", " + U_D60 * 1000 / 60 +
                        ", " + U_D72 * 1000 / 72 +
                        ", " + D_D03 * 1000 / 3 +
                        ", " + D_D05 * 1000 / 5 +
                        ", " + D_D10 * 1000 / 10 +
                        ", " + D_D15 * 1000 / 15 +
                        ", " + D_D20 * 1000 / 20 +
                        ", " + D_D25 * 1000 / 25 +
                        ", " + D_D30 * 1000 / 30 +
                        ", " + D_D60 * 1000 / 60 +
                        ", " + D_D72 * 1000 / 72 +
                        ")";
    }

    private float D_D60;
    private float D_D72;

    public String createTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE INTEGER,DATE TEXT,N TEXT,D_D TEXT" +
                ",U_D03 INTEGER,U_D05 INTEGER,U_D10 INTEGER,U_D15 INTEGER,U_D20 INTEGER,U_D25 INTEGER,U_D30 INTEGER,U_D60 INTEGER,U_D72 INTEGER" +
                ",D_D03 INTEGER,D_D05 INTEGER,D_D10 INTEGER,D_D15 INTEGER,D_D20 INTEGER,D_D25 INTEGER,D_D30 INTEGER,D_D60 INTEGER,D_D72 INTEGER);";
    }


    public String insertTB(String tbName) {
        return "INSERT INTO " + tbName + "(CODE ,N,D_D,DATE" +
                ",U_D03,U_D05 ,U_D10 ,U_D15 ,U_D20 ,U_D25 ,U_D30 ,U_D60 ,U_D72 " +
                ",D_D03,D_D05 ,D_D10 ,D_D15 ,D_D20 ,D_D25 ,D_D30 ,D_D60 ,D_D72 " +
                ") VALUES (" + toString();
    }

    @Override
    public int getCode() {
        return code;
    }
}
