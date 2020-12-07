package com.mgc.sharesanalyse.entity;

import java.util.ArrayList;

public class GapJsonBean {
    public int getAGGS() {
        return AGGS;
    }

    public void setAGGS(int AGGS) {
        this.AGGS = AGGS;
    }

    public int getABGS() {
        return ABGS;
    }

    public void setABGS(int ABGS) {
        this.ABGS = ABGS;
    }

    private int AGGS;
    private int ABGS;
    private ArrayList<GG> GGList;
    private ArrayList<GG> BGList;

    public ArrayList<GG> getGGList() {
        return GGList;
    }

    public void setGGList(ArrayList<GG> GGList) {
        this.GGList = GGList;
    }

    public ArrayList<GG> getBGList() {
        return BGList;
    }

    public void setBGList(ArrayList<GG> BGList) {
        this.BGList = BGList;
    }

    public static class GG {
        private int date;
        private float range;
        private float percent;
        private float tr;
        private float op;
        private float cp;
        private float mp;
        private float lp;
        private float gapOp;
        private float gap_OldCpRate;
        //bad gap 上行价格
        private float topPrice;
        //good gap 下行价格
        private float BottomPrice;
        private float SupportPrice;

        public float getSupportPrice() {
            return SupportPrice;
        }

        public void setSupportPrice(float supportPrice) {
            SupportPrice = supportPrice;
        }

        public float getPressurePrice() {
            return PressurePrice;
        }

        public void setPressurePrice(float pressurePrice) {
            PressurePrice = pressurePrice;
        }

        private float PressurePrice;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public float getRange() {
            return range;
        }

        public void setRange(float range) {
            this.range = range;
        }

        public float getPercent() {
            return percent;
        }

        public void setPercent(float percent) {
            this.percent = percent;
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

        public float getGapOp() {
            return gapOp;
        }

        public void setGapOp(float gapOp) {
            this.gapOp = gapOp;
        }

        public float getGap_OldCpRate() {
            return gap_OldCpRate;
        }

        public void setGap_OldCpRate(float gap_OldCpRate) {
            this.gap_OldCpRate = gap_OldCpRate;
        }

        public float getTopPrice() {
            return topPrice;
        }

        public void setTopPrice(float topPrice) {
            this.topPrice = topPrice;
        }

        public float getBottomPrice() {
            return BottomPrice;
        }

        public void setBottomPrice(float bottomPrice) {
            BottomPrice = bottomPrice;
        }
    }
}
