package com.mgc.sharesanalyse.entity;

import java.util.List;

public class GapJsonBean {
    private List<GG> GGList;
    private List<GG> BGList;

    private static class GG {
        private int date;
        private float range;
        private float percent;
        private float tr;
        private float op;
        private float cp;
        private float mp;
        private float lp;
        private float gapOp;
        private float gapCp_OldCpRate;
        private float topPrice;
        private float BottomPrice;
    }
}
