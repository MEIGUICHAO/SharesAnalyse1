package com.mgc.sharesanalyse.entity;

import com.mgc.sharesanalyse.utils.GsonHelper;

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





    public String toInsertDBValues(String dbName) {
        return "INSERT INTO " + dbName + "( NAME , DATE , OP , CP , PP , P , AUP ,TR" +
                ", P_AUTR_J, P_DA_J, P_PP_J, P_MA_J, SpePP_J, DA_J ,K_J ,Shape_J ,K_TR_J ,GAP_J , MS_J ) " +
                "VALUES('" + name + "'" +
                ",'" + DATE + "'" +
                ", " + OP +
                ", " + CP +
                ", " + PP +
                ", " + P +
                ", " + AUP +
                ",'" + TR +
                "', '" + GsonHelper.toJson(p_autr_j) +
                "', '" + GsonHelper.toJson(P_DA_J) +
                "', '" + GsonHelper.toJson(P_PP_J) +
                "', '" + GsonHelper.toJson(P_MA_J) +
                "', '" + (null == SpePP_J ? "" : SpePP_J.toJSon()) +
                "', '" + (null == DA_J ? "" : DA_J.toJSon()) +
                "', '" + GsonHelper.toJson(K_J).
                replace("\"CTC_D\":{},", "").replace("\"CTC_U\":{}", "").replace("\"CTC\":{},", "").
                replace("\"D03\":3,", "").replace("\"D05\":5,", "").replace("\"D10\":10,", "").replace("\"D15\":15,", "").replace("\"D20\":20,", "").replace("\"D25\":25,", "").replace("\"D30\":30,", "").replace("\"D60\":60,", "").replace("\"D72\":72,", "").
                replace("\"USLC\":{},", "").replace("\"DSLC\":{}", "").replace("\"SLL\":{},", "") +
                "', '" + (null == Shape_J ? "" :GsonHelper.toJson(Shape_J)) +
                "', '" + GsonHelper.toJson(K_TR_J) +
                "', '" + GsonHelper.toJson(GAP_J) +
                "', '" + (null == MS_J ? "" :MS_J.toJSon()) +
                "')";
    }


    public P_AUTR_J p_autr_j;
    private P_DA_J P_DA_J;
    private P_PP_J P_PP_J;
    private P_MA_J P_MA_J;

    public CodeHDDBean.P_MA_J getP_MA_J() {
        return P_MA_J;
    }

    public void setP_MA_J(CodeHDDBean.P_MA_J p_MA_J) {
        P_MA_J = p_MA_J;
    }
//     TEXT, TEXT, TEXT, TEXT
    private SpePP_J SpePP_J;
    private DA_J DA_J;
    private KJsonBean K_J;
    private ShapeJsonBean Shape_J;
    private K_TR_Json K_TR_J;
    private GapJsonBean GAP_J;

    public KJsonBean getK_J() {
        return K_J;
    }

    public void setK_J(KJsonBean k_J) {
        K_J = k_J;
    }

    public ShapeJsonBean getShape_J() {
        return Shape_J;
    }

    public void setShape_J(ShapeJsonBean shape_J) {
        Shape_J = shape_J;
    }

    public K_TR_Json getK_TR_J() {
        return K_TR_J;
    }

    public void setK_TR_J(K_TR_Json k_TR_J) {
        K_TR_J = k_TR_J;
    }

    public GapJsonBean getGAP_J() {
        return GAP_J;
    }

    public void setGAP_J(GapJsonBean GAP_J) {
        this.GAP_J = GAP_J;
    }

    private MS_J MS_J;

    public P_AUTR_J getP_autr_j() {
        return p_autr_j;
    }

    public void setP_autr_j(P_AUTR_J p_autr_j) {
        this.p_autr_j = p_autr_j;
    }

    public CodeHDDBean.P_DA_J getP_DA_J() {
        return P_DA_J;
    }

    public void setP_DA_J(CodeHDDBean.P_DA_J p_DA_J) {
        P_DA_J = p_DA_J;
    }

    public CodeHDDBean.P_PP_J getP_PP_J() {
        return P_PP_J;
    }

    public void setP_PP_J(CodeHDDBean.P_PP_J p_PP_J) {
        P_PP_J = p_PP_J;
    }


    public CodeHDDBean.SpePP_J getSpePP_J() {
        return SpePP_J;
    }

    public void setSpePP_J(CodeHDDBean.SpePP_J spePP_J) {
        SpePP_J = spePP_J;
    }

    public CodeHDDBean.DA_J getDA_J() {
        return DA_J;
    }

    public void setDA_J(CodeHDDBean.DA_J DA_J) {
        this.DA_J = DA_J;
    }

    public CodeHDDBean.MS_J getMS_J() {
        return MS_J;
    }

    public void setMS_J(CodeHDDBean.MS_J MS_J) {
        this.MS_J = MS_J;
    }

    static public class P_AUTR_J {
        float D03;
        float D05;
        float D10;
        float D15;
        float D20;
        float D25;

        public float getD03() {
            return D03;
        }

        public void setD03(float d03) {
            D03 = d03;
        }

        public float getD05() {
            return D05;
        }

        public void setD05(float d05) {
            D05 = d05;
        }

        public float getD10() {
            return D10;
        }

        public void setD10(float d10) {
            D10 = d10;
        }

        public float getD15() {
            return D15;
        }

        public void setD15(float d15) {
            D15 = d15;
        }

        public float getD20() {
            return D20;
        }

        public void setD20(float d20) {
            D20 = d20;
        }

        public float getD25() {
            return D25;
        }

        public void setD25(float d25) {
            D25 = d25;
        }

        public float getD30() {
            return D30;
        }

        public void setD30(float d30) {
            D30 = d30;
        }

        float D30;
        float D60;

        public float getD60() {
            return D60;
        }

        public void setD60(float d60) {
            D60 = d60;
        }

        public float getD72() {
            return D72;
        }

        public void setD72(float d72) {
            D72 = d72;
        }

        float D72;

    }

    public CodeHDDBean.P_DV_J getP_DV_J() {
        return P_DV_J;
    }

    public void setP_DV_J(CodeHDDBean.P_DV_J p_DV_J) {
        P_DV_J = p_DV_J;
    }

    private P_DV_J P_DV_J;

    static public class P_DV_J {
        float P_3d_DA;
        float P_5d_DA;
        float P_10d_DA;
        float P_15d_DA;
        float P_20d_DA;
        float P_25d_DA;
        float P_30d_DA;
        float P_60d_DA;
        float P_72d_DA;

        public float getP_60d_DA() {
            return P_60d_DA;
        }

        public void setP_60d_DA(float p_60d_DA) {
            P_60d_DA = p_60d_DA;
        }

        public float getP_72d_DA() {
            return P_72d_DA;
        }

        public void setP_72d_DA(float p_72d_DA) {
            P_72d_DA = p_72d_DA;
        }

        public float getP_30d_DA() {
            return P_30d_DA;
        }

        public void setP_30d_DA(float p_30d_DA) {
            P_30d_DA = p_30d_DA;
        }

        public float getP_3d_DA() {
            return P_3d_DA;
        }

        public void setP_3d_DA(float p_3d_DA) {
            P_3d_DA = p_3d_DA;
        }

        public float getP_5d_DA() {
            return P_5d_DA;
        }

        public void setP_5d_DA(float p_5d_DA) {
            P_5d_DA = p_5d_DA;
        }

        public float getP_10d_DA() {
            return P_10d_DA;
        }

        public void setP_10d_DA(float p_10d_DA) {
            P_10d_DA = p_10d_DA;
        }

        public float getP_15d_DA() {
            return P_15d_DA;
        }

        public void setP_15d_DA(float p_15d_DA) {
            P_15d_DA = p_15d_DA;
        }

        public float getP_20d_DA() {
            return P_20d_DA;
        }

        public void setP_20d_DA(float p_20d_DA) {
            P_20d_DA = p_20d_DA;
        }

        public float getP_25d_DA() {
            return P_25d_DA;
        }

        public void setP_25d_DA(float p_25d_DA) {
            P_25d_DA = p_25d_DA;
        }
    }
    static public class P_DA_J {
        float D03;
        float D05;
        float D10;
        float D15;
        float D20;
        float D25;
        float D30;
        float D60;
        float D72;

        public float getD60() {
            return D60;
        }

        public void setD60(float d60) {
            D60 = d60;
        }

        public float getD72() {
            return D72;
        }

        public void setD72(float d72) {
            D72 = d72;
        }

        public float getD03() {
            return D03;
        }

        public void setD03(float d03) {
            D03 = d03;
        }

        public float getD05() {
            return D05;
        }

        public void setD05(float d05) {
            D05 = d05;
        }

        public float getD10() {
            return D10;
        }

        public void setD10(float d10) {
            D10 = d10;
        }

        public float getD15() {
            return D15;
        }

        public void setD15(float d15) {
            D15 = d15;
        }

        public float getD20() {
            return D20;
        }

        public void setD20(float d20) {
            D20 = d20;
        }

        public float getD25() {
            return D25;
        }

        public void setD25(float d25) {
            D25 = d25;
        }

        public float getD30() {
            return D30;
        }

        public void setD30(float d30) {
            D30 = d30;
        }

    }

    static public class P_PP_J {
        float AAOP;
        float AACP;
        float AMP;

        float ALP;
        float D03;
        float D05;
        float D10;
        float D15;
        float D20;
        float D25;
        float D30;
        float D60;
        float D72;

        public float getAAOP() {
            return AAOP;
        }

        public void setAAOP(float AAOP) {
            this.AAOP = AAOP;
        }

        public float getAACP() {
            return AACP;
        }

        public void setAACP(float AACP) {
            this.AACP = AACP;
        }

        public float getAMP() {
            return AMP;
        }

        public void setAMP(float AMP) {
            this.AMP = AMP;
        }

        public float getALP() {
            return ALP;
        }

        public void setALP(float ALP) {
            this.ALP = ALP;
        }

        public float getD60() {
            return D60;
        }

        public void setD60(float d60) {
            D60 = d60;
        }

        public float getD72() {
            return D72;
        }

        public void setD72(float d72) {
            D72 = d72;
        }

        public float getD03() {
            return D03;
        }

        public void setD03(float d03) {
            D03 = d03;
        }

        public float getD05() {
            return D05;
        }

        public void setD05(float d05) {
            D05 = d05;
        }

        public float getD10() {
            return D10;
        }

        public void setD10(float d10) {
            D10 = d10;
        }

        public float getD15() {
            return D15;
        }

        public void setD15(float d15) {
            D15 = d15;
        }

        public float getD20() {
            return D20;
        }

        public void setD20(float d20) {
            D20 = d20;
        }

        public float getD25() {
            return D25;
        }

        public void setD25(float d25) {
            D25 = d25;
        }

        public float getD30() {
            return D30;
        }

        public void setD30(float d30) {
            D30 = d30;
        }
    }

    static public class P_MA_J {
        float AAOP;
        float AACP;
        float AMP;

        float ALP;
        float D03;
        float D05;
        float D10;
        float D15;
        float D20;
        float D25;
        float D30;
        float D60;
        float D72;

        public float getAAOP() {
            return AAOP;
        }

        public void setAAOP(float AAOP) {
            this.AAOP = AAOP;
        }

        public float getAACP() {
            return AACP;
        }

        public void setAACP(float AACP) {
            this.AACP = AACP;
        }

        public float getAMP() {
            return AMP;
        }

        public void setAMP(float AMP) {
            this.AMP = AMP;
        }

        public float getALP() {
            return ALP;
        }

        public void setALP(float ALP) {
            this.ALP = ALP;
        }

        public float getD60() {
            return D60;
        }

        public void setD60(float d60) {
            D60 = d60;
        }

        public float getD72() {
            return D72;
        }

        public void setD72(float d72) {
            D72 = d72;
        }

        public float getD03() {
            return D03;
        }

        public void setD03(float d03) {
            D03 = d03;
        }

        public float getD05() {
            return D05;
        }

        public void setD05(float d05) {
            D05 = d05;
        }

        public float getD10() {
            return D10;
        }

        public void setD10(float d10) {
            D10 = d10;
        }

        public float getD15() {
            return D15;
        }

        public void setD15(float d15) {
            D15 = d15;
        }

        public float getD20() {
            return D20;
        }

        public void setD20(float d20) {
            D20 = d20;
        }

        public float getD25() {
            return D25;
        }

        public void setD25(float d25) {
            D25 = d25;
        }

        public float getD30() {
            return D30;
        }

        public void setD30(float d30) {
            D30 = d30;
        }
    }



    static public class SpePP_J {

        private float PP100M;
        private float PP50M;
        private float PP30M;
        private float PP10M;
        private float PP5M;
        private float PP1M;
        private float PPL1M;

        public float getPPL1M() {
            return PPL1M;
        }

        public void setPPL1M(float PPL1M) {
            this.PPL1M = PPL1M;
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

        public String toJSon() {
            return ("{" + (PP100M > 0 ? "PP100M:"+PP100M : "") + (PP50M > 0 ? ",PP50M:" + PP50M : "") + (PP30M > 0 ? ",PP30M:" +  PP30M : "") + (PP10M > 0 ? ",PP10M:" +  PP10M : "") + (PP5M > 0 ? ",PP5M:" +  PP5M : "") + (PP1M > 0 ? ",PP1M:" +  PP1M : "") + (PPL1M > 0 ? ",PPL1M:" +  PPL1M : "") + "}")
                    .replace("{,","{").replace("{","{\"").replace(":","\":").replace(",",",\"").replace("{\"}","{}");
        }
    }

    static public class DA_J {
        private float DA;

        private float DA5000;
        private float DA1000;
        private float DA500;
        private float DA100;


        public float getDA() {
            return DA;
        }

        public void setDA(float DA) {
            this.DA = DA;
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

        public String toJSon() {
            return ("{" + (DA > 0 ?"DA:"+ DA : "")  + (DA5000 > 0 ?",DA5000:"+ DA5000 : "") + (DA1000 > 0 ? ",DA1000:" + DA1000 : "") + (DA500 > 0 ? ",DA500:" +  DA500 : "") + (DA100 > 0 ? ",DA100:" +  DA100 : "") + "}")
                    .replace("{,","{").replace("{","{\"").replace(":","\":").replace(",",",\"").replace("{\"}","{}");

        }
    }

    static public class MS_J {
        private int AS;
        private int M100S;
        private int M50S;
        private int M30S;
        private int M10S;
        private int M5S;
        private int M1S;
        private int M05S;
        private int M01S;
        private int L01S;

        public int getAS() {
            return AS;
        }

        public void setAS(int AS) {
            this.AS = AS;
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

        public String toJSon() {
            return ("{" + (AS > 0 ? "AS:"+AS : "")  + (M100S > 0 ? ",M100S:"+M100S : "") + (M50S > 0 ? ",M50S:" + M50S : "") + (M30S > 0 ? ",M30S:" +  M30S : "") + (M10S > 0 ? ",M10S:" +  M10S : "") + (M5S > 0 ? ",M5S:" +  M5S : "") + (M1S > 0 ? ",M1S:" +  M1S : "") + (M05S > 0 ? ",M05S:" +  M05S : "")
                    +  (M01S > 0 ? ",M01S:" +  M01S : "") +  (L01S > 0 ? ",L01S:" +  L01S : "") + "}")
                    .replace("{,","{").replace("{","{\"").replace(":","\":").replace(",",",\"").replace("{\"}","{}");
        }
    }

}
