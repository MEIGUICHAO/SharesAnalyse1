package com.mgc.sharesanalyse.entity;

public class KJsonBean {
    private SLC SLC;

    static class SLC {
        private USLC USLC;
        private DSLC DSLC;
    }

    private static class USLC {
        private int D03;
        private int D05;
        private int D10;
        private int D15;
        private int D20;
        private int D25;
        private int D30;
        private int D60;
        private int D72;
    }

    private static class DSLC {
        private int D03;
        private int D05;
        private int D10;
        private int D15;
        private int D20;
        private int D25;
        private int D30;
        private int D60;
        private int D72;
    }


    private SLL SLL;

    static class SLL {
        private USLL USLL;
        private DSLL DSLL;
    }

    private static class USLL {
        private float D03;
        private float D05;
        private float D10;
        private float D15;
        private float D20;
        private float D25;
        private float D30;
        private float D60;
        private float D72;
    }

    private static class DSLL {
        private float D03;
        private float D05;
        private float D10;
        private float D15;
        private float D20;
        private float D25;
        private float D30;
        private float D60;
        private float D72;
    }

    private CTC CTC;

    static class CTC {
        private CTC_U CTC_U;
        private CTC_D CTC_D;
    }

    private static class CTC_U {
        private BaseType typeA;
        private BaseType typeB;
        private BaseType typeC;
    }

    private static class CTC_D {
        private BaseType typeA;
        private BaseType typeB;
        private BaseType typeC;
    }



    private static class BaseType {
        private int date;
        private int tr;
        private int op;
        private int cp;
        private int mp;
        private int lp;
        private int range;
    }



}
