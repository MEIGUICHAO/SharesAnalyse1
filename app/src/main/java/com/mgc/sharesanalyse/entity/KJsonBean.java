package com.mgc.sharesanalyse.entity;

public class KJsonBean {
    public SLC SLC;

    public static class SLC {
        public USLC USLC;
        public DSLC DSLC;

        public KJsonBean.USLC getUSLC() {
            return USLC;
        }

        public void setUSLC(KJsonBean.USLC USLC) {
            this.USLC = USLC;
        }

        public KJsonBean.DSLC getDSLC() {
            return DSLC;
        }

        public void setDSLC(KJsonBean.DSLC DSLC) {
            this.DSLC = DSLC;
        }
    }

    public KJsonBean.SLC getSLC() {
        return SLC;
    }

    public void setSLC(KJsonBean.SLC SLC) {
        this.SLC = SLC;
    }

    public KJsonBean.SLL getSLL() {
        return SLL;
    }

    public void setSLL(KJsonBean.SLL SLL) {
        this.SLL = SLL;
    }

    public KJsonBean.CTC getCTC() {
        return CTC;
    }

    public void setCTC(KJsonBean.CTC CTC) {
        this.CTC = CTC;
    }

    public static class USLC {
        public int D03;
        public int D05;
        public int D10;
        public int D15;
        public int D20;
        public int D25;
        public int D30;

        public int getD03() {
            return D03;
        }

        public void setD03(int d03) {
            D03 = d03;
        }

        public int getD05() {
            return D05;
        }

        public void setD05(int d05) {
            D05 = d05;
        }

        public int getD10() {
            return D10;
        }

        public void setD10(int d10) {
            D10 = d10;
        }

        public int getD15() {
            return D15;
        }

        public void setD15(int d15) {
            D15 = d15;
        }

        public int getD20() {
            return D20;
        }

        public void setD20(int d20) {
            D20 = d20;
        }

        public int getD25() {
            return D25;
        }

        public void setD25(int d25) {
            D25 = d25;
        }

        public int getD30() {
            return D30;
        }

        public void setD30(int d30) {
            D30 = d30;
        }

        public int getD60() {
            return D60;
        }

        public void setD60(int d60) {
            D60 = d60;
        }

        public int getD72() {
            return D72;
        }

        public void setD72(int d72) {
            D72 = d72;
        }

        public int D60;
        public int D72;
    }

    public static class DSLC {
        public int D03;
        public int D05;
        public int D10;
        public int D15;
        public int D20;
        public int D25;

        public int getD03() {
            return D03;
        }

        public void setD03(int d03) {
            D03 = d03;
        }

        public int getD05() {
            return D05;
        }

        public void setD05(int d05) {
            D05 = d05;
        }

        public int getD10() {
            return D10;
        }

        public void setD10(int d10) {
            D10 = d10;
        }

        public int getD15() {
            return D15;
        }

        public void setD15(int d15) {
            D15 = d15;
        }

        public int getD20() {
            return D20;
        }

        public void setD20(int d20) {
            D20 = d20;
        }

        public int getD25() {
            return D25;
        }

        public void setD25(int d25) {
            D25 = d25;
        }

        public int getD30() {
            return D30;
        }

        public void setD30(int d30) {
            D30 = d30;
        }

        public int getD60() {
            return D60;
        }

        public void setD60(int d60) {
            D60 = d60;
        }

        public int getD72() {
            return D72;
        }

        public void setD72(int d72) {
            D72 = d72;
        }

        public int D30;
        public int D60;
        public int D72;
    }


    public SLL SLL;

    public static class SLL {
        public KJsonBean.USLL getUSLL() {
            return USLL;
        }

        public void setUSLL(KJsonBean.USLL USLL) {
            this.USLL = USLL;
        }

        public KJsonBean.DSLL getDSLL() {
            return DSLL;
        }

        public void setDSLL(KJsonBean.DSLL DSLL) {
            this.DSLL = DSLL;
        }

        public USLL USLL;
        public DSLL DSLL;
    }

    public static class USLL {
        public float D03;
        public float D05;
        public float D10;
        public float D15;
        public float D20;
        public float D25;
        public float D30;

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

        public float D60;
        public float D72;
    }

    public static class DSLL {
        public float D03;
        public float D05;
        public float D10;
        public float D15;
        public float D20;
        public float D25;
        public float D30;
        public float D60;

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

        public float D72;
    }

    public CTC CTC;

    public static class CTC {
        public CTC_U CTC_U;
        public CTC_D CTC_D;

        public KJsonBean.CTC_U getCTC_U() {
            return CTC_U;
        }

        public void setCTC_U(KJsonBean.CTC_U CTC_U) {
            this.CTC_U = CTC_U;
        }

        public KJsonBean.CTC_D getCTC_D() {
            return CTC_D;
        }

        public void setCTC_D(KJsonBean.CTC_D CTC_D) {
            this.CTC_D = CTC_D;
        }
    }

    public static class CTC_U {
        public BaseType typeA;
        public BaseType typeB;
        public BaseType typeC;

        public BaseType getTypeA() {
            return typeA;
        }

        public void setTypeA(BaseType typeA) {
            this.typeA = typeA;
        }

        public BaseType getTypeB() {
            return typeB;
        }

        public void setTypeB(BaseType typeB) {
            this.typeB = typeB;
        }

        public BaseType getTypeC() {
            return typeC;
        }

        public void setTypeC(BaseType typeC) {
            this.typeC = typeC;
        }
    }

    public static class CTC_D {
        public BaseType typeA;
        public BaseType typeB;
        public BaseType typeC;

        public BaseType getTypeA() {
            return typeA;
        }

        public void setTypeA(BaseType typeA) {
            this.typeA = typeA;
        }

        public BaseType getTypeB() {
            return typeB;
        }

        public void setTypeB(BaseType typeB) {
            this.typeB = typeB;
        }

        public BaseType getTypeC() {
            return typeC;
        }

        public void setTypeC(BaseType typeC) {
            this.typeC = typeC;
        }
    }


    public static class BaseType {
        public String date;
        public float tr;
        public float op;
        public float cp;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public float getTr() {
            return tr;
        }

        public void setTr(float tr) {
            this.tr = tr;
        }

        public float getOp() {
            return op;
        }

        public void setOp(float op) {
            this.op = op;
        }

        public float getCp() {
            return cp;
        }

        public void setCp(float cp) {
            this.cp = cp;
        }

        public float getMp() {
            return mp;
        }

        public void setMp(float mp) {
            this.mp = mp;
        }

        public float getLp() {
            return lp;
        }

        public void setLp(float lp) {
            this.lp = lp;
        }

        public float getRange() {
            return range;
        }

        public void setRange(float range) {
            this.range = range;
        }

        public float mp;
        public float lp;
        public float range;
    }


}
