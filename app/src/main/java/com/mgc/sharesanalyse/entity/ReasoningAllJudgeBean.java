package com.mgc.sharesanalyse.entity;

class ReasoningAllJudgeBean {

    private int D_T;
    private int L_S_T;
    private int COUNT;
    private float OM_M_D;
    private float OM_C_D;
    private float OM_P_D;
    private float OM_L_D;
    private float OC_M_D;
    private float OC_C_D;
    private float OC_P_D;
    private float OC_L_D;
    private float OO_M_D;
    private float OO_C_D;
    private float OO_P_D;
    private float OO_L_D;
    private float OL_M_D;
    private float OL_C_D;
    private float OL_P_D;
    private float OL_L_D;

    private float OM_M_X;
    private float OM_C_X;
    private float OM_P_X;
    private float OM_L_X;
    private float OC_M_X;
    private float OC_C_X;
    private float OC_P_X;
    private float OC_L_X;
    private float OO_M_X;
    private float OO_C_X;
    private float OO_P_X;
    private float OO_L_X;
    private float OL_M_X;
    private float OL_C_X;
    private float OL_P_X;
    private float OL_L_X;

    @Override
    public String toString() {
        return
                "" + D_T +
                ", " + L_S_T +
                ", " + COUNT +
                ", " + OM_M_D +
                ", " + OM_C_D +
                ", " + OM_P_D +
                ", " + OM_L_D +
                ", " + OC_M_D +
                ", " + OC_C_D +
                ", " + OC_P_D +
                ", " + OC_L_D +
                ", " + OO_M_D +
                ", " + OO_C_D +
                ", " + OO_P_D +
                ", " + OO_L_D +
                ", " + OL_M_D +
                ", " + OL_C_D +
                ", " + OL_P_D +
                ", " + OL_L_D +
                ", " + OM_M_X +
                ", " + OM_C_X +
                ", " + OM_P_X +
                ", " + OM_L_X +
                ", " + OC_M_X +
                ", " + OC_C_X +
                ", " + OC_P_X +
                ", " + OC_L_X +
                ", " + OO_M_X +
                ", " + OO_C_X +
                ", " + OO_P_X +
                ", " + OO_L_X +
                ", " + OL_M_X +
                ", " + OL_C_X +
                ", " + OL_P_X +
                ", " + OL_L_X ;
    }

    public String createTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, D_T INTEGER,L_S_T INTEGER,COUNT INTEGER" +
                ",OM_M_D INTEGER,OM_C_D INTEGER,OM_P_D INTEGER,OM_L_D INTEGER,OC_M_D INTEGER,OC_C_D INTEGER,OC_P_D INTEGER,OC_L_D INTEGER,OO_M_D INTEGER,OO_C_D INTEGER,OO_P_D INTEGER,OO_L_D INTEGER,OL_M_D INTEGER,OL_C_D INTEGER,OL_P_D INTEGER,OL_L_D INTEGER" +
                ",OM_M_X INTEGER,OM_C_X INTEGER,OM_P_X INTEGER,OM_L_X INTEGER,OC_M_X INTEGER,OC_C_X INTEGER,OC_P_X INTEGER,OC_L_X INTEGER,OO_M_X INTEGER,OO_C_X INTEGER,OO_P_X INTEGER,OO_L_X INTEGER,OL_M_X INTEGER,OL_C_X INTEGER,OL_P_X INTEGER,OL_L_X INTEGER);";
    }


    public String insertTB(String tbName) {
        return "INSERT INTO " + tbName + "(D_T ,L_S_T ,COUNT" +
                ",OM_M_D ,OM_C_D ,OM_P_D ,OM_L_D ,OC_M_D ,OC_C_D ,OC_P_D ,OC_L_D ,OO_M_D ,OO_C_D ,OO_P_D ,OO_L_D ,OL_M_D ,OL_C_D ,OL_P_D ,OL_L_D" +
                ",OM_M_X ,OM_C_X ,OM_P_X ,OM_L_X ,OC_M_X ,OC_C_X ,OC_P_X ,OC_L_X ,OO_M_X ,OO_C_X ,OO_P_X ,OO_L_X ,OL_M_X ,OL_C_X ,OL_P_X ,OL_L_X ) VALUES (" + toString()+");";
    }

}
