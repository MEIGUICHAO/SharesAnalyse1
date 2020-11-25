package com.mgc.sharesanalyse.entity;

public class K_TR_Json {
    private KTRDay SumUSLTR;
    private KTRDay SumDSLTR;
    private KTRDay SumU_DSLTR;
    private KTRDay SumRKSLTR;
    private KTRDay SumBKSLTR;
    private KTRDay SumR_BKSLTR;

    public static class KTRDay {
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
}
