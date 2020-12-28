package com.mgc.sharesanalyse.entity;

import java.util.ArrayList;

public class P50FilterBBKJRangeBean {
    private DFilter R_N40_N30;
    private DFilter R_0_10;
    private DFilter R_N30_N20;
    private DFilter R_N10_0;
    private DFilter R_N20_N10;
    private DFilter R_10_20;
    private DFilter R_N70_N60;
    private DFilter R_N50_N40;

    public DFilter getR_N40_N30() {
        return R_N40_N30;
    }

    public void setR_N40_N30(DFilter r_N40_N30) {
        R_N40_N30 = r_N40_N30;
    }

    public DFilter getR_0_10() {
        return R_0_10;
    }

    public void setR_0_10(DFilter r_0_10) {
        R_0_10 = r_0_10;
    }

    public DFilter getR_N30_N20() {
        return R_N30_N20;
    }

    public void setR_N30_N20(DFilter r_N30_N20) {
        R_N30_N20 = r_N30_N20;
    }

    public DFilter getR_N10_0() {
        return R_N10_0;
    }

    public void setR_N10_0(DFilter r_N10_0) {
        R_N10_0 = r_N10_0;
    }

    public DFilter getR_N20_N10() {
        return R_N20_N10;
    }

    public void setR_N20_N10(DFilter r_N20_N10) {
        R_N20_N10 = r_N20_N10;
    }

    public DFilter getR_10_20() {
        return R_10_20;
    }

    public void setR_10_20(DFilter r_10_20) {
        R_10_20 = r_10_20;
    }

    public DFilter getR_N70_N60() {
        return R_N70_N60;
    }

    public void setR_N70_N60(DFilter r_N70_N60) {
        R_N70_N60 = r_N70_N60;
    }

    public DFilter getR_N50_N40() {
        return R_N50_N40;
    }

    public void setR_N50_N40(DFilter r_N50_N40) {
        R_N50_N40 = r_N50_N40;
    }

    public void insertTBByFilterType(ArrayList<String> tbList, float p) {
        "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, P_TYPE INTEGER,JSON TEXT);";
    }

    public static class DFilter {
        private int count;
        private MaxMinBean d03Bean;
        private MaxMinBean d05Bean;
        private MaxMinBean d10Bean;
        private MaxMinBean d15Bean;
        private MaxMinBean d20Bean;
        private MaxMinBean d25Bean;
        private MaxMinBean d30Bean;
        private MaxMinBean d36Bean;

        private MaxMinTRBean d03TRBean;
        private MaxMinTRBean d05TRBean;
        private MaxMinTRBean d10TRBean;

        public MaxMinTRBean getD03TRBean() {
            return d03TRBean;
        }

        public void setD03TRBean(MaxMinTRBean d03TRBean) {
            this.d03TRBean = d03TRBean;
        }

        public MaxMinTRBean getD05TRBean() {
            return d05TRBean;
        }

        public void setD05TRBean(MaxMinTRBean d05TRBean) {
            this.d05TRBean = d05TRBean;
        }

        public MaxMinTRBean getD10TRBean() {
            return d10TRBean;
        }

        public void setD10TRBean(MaxMinTRBean d10TRBean) {
            this.d10TRBean = d10TRBean;
        }

        public MaxMinTRBean getD15TRBean() {
            return d15TRBean;
        }

        public void setD15TRBean(MaxMinTRBean d15TRBean) {
            this.d15TRBean = d15TRBean;
        }

        public MaxMinTRBean getD20TRBean() {
            return d20TRBean;
        }

        public void setD20TRBean(MaxMinTRBean d20TRBean) {
            this.d20TRBean = d20TRBean;
        }

        public MaxMinTRBean getD25TRBean() {
            return d25TRBean;
        }

        public void setD25TRBean(MaxMinTRBean d25TRBean) {
            this.d25TRBean = d25TRBean;
        }

        public MaxMinTRBean getD30TRBean() {
            return d30TRBean;
        }

        public void setD30TRBean(MaxMinTRBean d30TRBean) {
            this.d30TRBean = d30TRBean;
        }

        public MaxMinTRBean getD36TRBean() {
            return d36TRBean;
        }

        public void setD36TRBean(MaxMinTRBean d36TRBean) {
            this.d36TRBean = d36TRBean;
        }

        private MaxMinTRBean d15TRBean;
        private MaxMinTRBean d20TRBean;
        private MaxMinTRBean d25TRBean;
        private MaxMinTRBean d30TRBean;
        private MaxMinTRBean d36TRBean;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


        public MaxMinBean getD03Bean() {
            return d03Bean;
        }

        public void setD03Bean(MaxMinBean d03Bean) {
            this.d03Bean = d03Bean;
        }

        public MaxMinBean getD05Bean() {
            return d05Bean;
        }

        public void setD05Bean(MaxMinBean d05Bean) {
            this.d05Bean = d05Bean;
        }

        public MaxMinBean getD10Bean() {
            return d10Bean;
        }

        public void setD10Bean(MaxMinBean d10Bean) {
            this.d10Bean = d10Bean;
        }

        public MaxMinBean getD15Bean() {
            return d15Bean;
        }

        public void setD15Bean(MaxMinBean d15Bean) {
            this.d15Bean = d15Bean;
        }

        public MaxMinBean getD20Bean() {
            return d20Bean;
        }

        public void setD20Bean(MaxMinBean d20Bean) {
            this.d20Bean = d20Bean;
        }

        public MaxMinBean getD25Bean() {
            return d25Bean;
        }

        public void setD25Bean(MaxMinBean d25Bean) {
            this.d25Bean = d25Bean;
        }

        public MaxMinBean getD30Bean() {
            return d30Bean;
        }

        public void setD30Bean(MaxMinBean d30Bean) {
            this.d30Bean = d30Bean;
        }

        public MaxMinBean getD36Bean() {
            return d36Bean;
        }

        public void setD36Bean(MaxMinBean d36Bean) {
            this.d36Bean = d36Bean;
        }
    }

    public static class MaxMinTRBean {
        private BaseFilterTRBBRangeBean maxBean;

        public BaseFilterTRBBRangeBean getMaxBean() {
            return maxBean;
        }

        public void setMaxBean(BaseFilterTRBBRangeBean maxBean) {
            this.maxBean = maxBean;
        }

        public BaseFilterTRBBRangeBean getMinBean() {
            return minBean;
        }

        public void setMinBean(BaseFilterTRBBRangeBean minBean) {
            this.minBean = minBean;
        }

        private BaseFilterTRBBRangeBean minBean;
    }

    public static class MaxMinBean {
        private BaseFilterKJBBRangeBean maxBean;
        private BaseFilterKJBBRangeBean minBean;

        public BaseFilterKJBBRangeBean getMaxBean() {
            return maxBean;
        }

        public void setMaxBean(BaseFilterKJBBRangeBean maxBean) {
            this.maxBean = maxBean;
        }

        public BaseFilterKJBBRangeBean getMinBean() {
            return minBean;
        }

        public void setMinBean(BaseFilterKJBBRangeBean minBean) {
            this.minBean = minBean;
        }
    }


    public String createTB(String tbname) {
        return "CREATE TABLE IF NOT EXISTS " + tbname + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, P_TYPE INTEGER,JSON TEXT);";
    }

    public String insertTB(String tbName,String P_TYPE,String json) {
        return "INSERT INTO " + tbName + "(P_TYPE ,JSON) VALUES ('" + P_TYPE+"','"+json+"');";
    }

    public String updateTB(String tbName,String json) {
        return "UPDATE " + tbName + " SET JSON = '" + json + "'";
    }


}
