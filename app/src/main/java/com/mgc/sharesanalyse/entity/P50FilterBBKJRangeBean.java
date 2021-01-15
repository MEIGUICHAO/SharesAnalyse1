package com.mgc.sharesanalyse.entity;

import com.mgc.sharesanalyse.utils.DBUtils;
import com.mgc.sharesanalyse.utils.LogUtil;

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
    
    public void insertTBByFilterType(String code,String date,ArrayList<String> tbList, float p) {
        for (int i = 0; i < tbList.size(); i++) {
            String name = tbList.get(i);
            if (name.contains("R_N70_N60") && null != getR_N70_N60()) {
                if (name.contains("D36_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N70_N60().d36Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N70_N60().d36Bean.maxBean);
                } else if (name.contains("D30_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N70_N60().d30Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N70_N60().d30Bean.maxBean);
                } else if (name.contains("D25_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N70_N60().d25Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N70_N60().d25Bean.maxBean);
                } else if (name.contains("D20_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N70_N60().d20Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N70_N60().d20Bean.maxBean);
                } else if (name.contains("D15_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N70_N60().d15Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N70_N60().d15Bean.maxBean);
                } else if (name.contains("D10_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N70_N60().d10Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N70_N60().d10Bean.maxBean);
                } else if (name.contains("D05_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N70_N60().d05Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N70_N60().d05Bean.maxBean);
                } else if (name.contains("D03_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N70_N60().d03Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N70_N60().d03Bean.maxBean);
                }
                
            } else if (name.contains("R_N50_N40") && null != getR_N50_N40()) {
                if (name.contains("D36_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N50_N40().d36Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N50_N40().d36Bean.maxBean);
                } else if (name.contains("D30_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N50_N40().d30Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N50_N40().d30Bean.maxBean);
                } else if (name.contains("D25_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N50_N40().d25Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N50_N40().d25Bean.maxBean);
                } else if (name.contains("D20_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N50_N40().d20Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N50_N40().d20Bean.maxBean);
                } else if (name.contains("D15_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N50_N40().d15Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N50_N40().d15Bean.maxBean);
                } else if (name.contains("D10_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N50_N40().d10Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N50_N40().d10Bean.maxBean);
                } else if (name.contains("D05_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N50_N40().d05Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N50_N40().d05Bean.maxBean);
                } else if (name.contains("D03_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N50_N40().d03Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N50_N40().d03Bean.maxBean);
                }
            } else if (name.contains("R_N40_N30") && null != getR_N40_N30()) {
                if (name.contains("D36_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N40_N30().d36Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N40_N30().d36Bean.maxBean);
                } else if (name.contains("D30_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N40_N30().d30Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N40_N30().d30Bean.maxBean);
                } else if (name.contains("D25_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N40_N30().d25Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N40_N30().d25Bean.maxBean);
                } else if (name.contains("D20_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N40_N30().d20Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N40_N30().d20Bean.maxBean);
                } else if (name.contains("D15_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N40_N30().d15Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N40_N30().d15Bean.maxBean);
                } else if (name.contains("D10_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N40_N30().d10Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N40_N30().d10Bean.maxBean);
                } else if (name.contains("D05_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N40_N30().d05Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N40_N30().d05Bean.maxBean);
                } else if (name.contains("D03_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N40_N30().d03Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N40_N30().d03Bean.maxBean);
                }

            } else if (name.contains("R_N30_N20") && null != getR_N30_N20()) {
                if (name.contains("D36_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N30_N20().d36Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N30_N20().d36Bean.maxBean);
                } else if (name.contains("D30_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N30_N20().d30Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N30_N20().d30Bean.maxBean);
                } else if (name.contains("D25_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N30_N20().d25Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N30_N20().d25Bean.maxBean);
                } else if (name.contains("D20_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N30_N20().d20Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N30_N20().d20Bean.maxBean);
                } else if (name.contains("D15_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N30_N20().d15Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N30_N20().d15Bean.maxBean);
                } else if (name.contains("D10_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N30_N20().d10Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N30_N20().d10Bean.maxBean);
                } else if (name.contains("D05_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N30_N20().d05Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N30_N20().d05Bean.maxBean);
                } else if (name.contains("D03_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N30_N20().d03Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N30_N20().d03Bean.maxBean);
                }
            } else if (name.contains("R_N20_N10") && null != getR_N20_N10()) {
                if (name.contains("D36_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N20_N10().d36Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N20_N10().d36Bean.maxBean);
                } else if (name.contains("D30_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N20_N10().d30Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N20_N10().d30Bean.maxBean);
                } else if (name.contains("D25_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N20_N10().d25Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N20_N10().d25Bean.maxBean);
                } else if (name.contains("D20_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N20_N10().d20Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N20_N10().d20Bean.maxBean);
                } else if (name.contains("D15_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N20_N10().d15Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N20_N10().d15Bean.maxBean);
                } else if (name.contains("D10_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N20_N10().d10Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N20_N10().d10Bean.maxBean);
                } else if (name.contains("D05_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N20_N10().d05Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N20_N10().d05Bean.maxBean);
                } else if (name.contains("D03_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N20_N10().d03Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N20_N10().d03Bean.maxBean);
                }

            } else if (name.contains("R_N10_0") && null != getR_N10_0()) {
                if (name.contains("D36_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N10_0().d36Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N10_0().d36Bean.maxBean);
                } else if (name.contains("D30_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N10_0().d30Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N10_0().d30Bean.maxBean);
                } else if (name.contains("D25_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N10_0().d25Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N10_0().d25Bean.maxBean);
                } else if (name.contains("D20_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N10_0().d20Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N10_0().d20Bean.maxBean);
                } else if (name.contains("D15_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N10_0().d15Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N10_0().d15Bean.maxBean);
                } else if (name.contains("D10_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N10_0().d10Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N10_0().d10Bean.maxBean);
                } else if (name.contains("D05_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N10_0().d05Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N10_0().d05Bean.maxBean);
                } else if (name.contains("D03_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_N10_0().d03Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_N10_0().d03Bean.maxBean);
                }
            } else if (name.contains("R_0_10") && null != getR_0_10()) {
                if (name.contains("D36_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_0_10().d36Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_0_10().d36Bean.maxBean);
                } else if (name.contains("D30_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_0_10().d30Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_0_10().d30Bean.maxBean);
                } else if (name.contains("D25_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_0_10().d25Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_0_10().d25Bean.maxBean);
                } else if (name.contains("D20_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_0_10().d20Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_0_10().d20Bean.maxBean);
                } else if (name.contains("D15_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_0_10().d15Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_0_10().d15Bean.maxBean);
                } else if (name.contains("D10_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_0_10().d10Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_0_10().d10Bean.maxBean);
                } else if (name.contains("D05_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_0_10().d05Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_0_10().d05Bean.maxBean);
                } else if (name.contains("D03_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_0_10().d03Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_0_10().d03Bean.maxBean);
                }

            } else if (name.contains("R_10_20") && null != getR_10_20()) {
                if (name.contains("D36_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_10_20().d36Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_10_20().d36Bean.maxBean);
                } else if (name.contains("D30_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_10_20().d30Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_10_20().d30Bean.maxBean);
                } else if (name.contains("D25_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_10_20().d25Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_10_20().d25Bean.maxBean);
                } else if (name.contains("D20_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_10_20().d20Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_10_20().d20Bean.maxBean);
                } else if (name.contains("D15_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_10_20().d15Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_10_20().d15Bean.maxBean);
                } else if (name.contains("D10_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_10_20().d10Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_10_20().d10Bean.maxBean);
                } else if (name.contains("D05_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_10_20().d05Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_10_20().d05Bean.maxBean);
                } else if (name.contains("D03_")) {
                    getCreateFilterTypeTB(name, p, code, date, getR_10_20().d03Bean.maxBean);
                    getCreateFilterTypeDerbyTB(name+"_Derby", p, code, date, getR_10_20().d03Bean.maxBean);
                }

            }
        }
            
    }

    private void getCreateFilterTypeTB(String tbName, float p, String code, String date, BaseFilterKJBBRangeBean dFilter) {
        String create = "CREATE TABLE IF NOT EXISTS " + tbName + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT,D TEXT,P INTEGER,OM_M INTEGER,OM_C INTEGER,OM_P INTEGER,OM_L INTEGER,OC_M INTEGER,OC_C INTEGER,OC_P INTEGER,OC_L INTEGER,OO_M INTEGER,OO_C INTEGER,OO_P INTEGER,OO_L INTEGER,OL_M INTEGER,OL_C INTEGER,OL_P INTEGER,OL_L INTEGER);";
        DBUtils.INSTANCE.getDb().execSQL(create);
        LogUtil.d("tbName:"+tbName);
        String insertSlq = "INSERT INTO " + tbName + "(CODE,D,P,OM_M ,OM_C ,OM_P ,OM_L ,OC_M ,OC_C ,OC_P ,OC_L ,OO_M ,OO_C ,OO_P ,OO_L ,OL_M ,OL_C ,OL_P ,OL_L ) VALUES ('" + code + "','" + date + "'," + p + "," + dFilter.toString() + ");";
        DBUtils.INSTANCE.getDb().execSQL(insertSlq);
    }

    private void getCreateFilterTypeDerbyTB(String tbName, float p, String code, String date, BaseFilterKJBBRangeBean dFilter) {
        String create = "CREATE TABLE IF NOT EXISTS " + tbName + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT,D TEXT,P INTEGER,OM_OC INTEGER,OM_OP INTEGER,OM_OL INTEGER,OC_OP INTEGER,OC_OL INTEGER,OP_OL INTEGER,M_C INTEGER,M_P INTEGER,M_L INTEGER,C_P INTEGER,C_L INTEGER,P_L INTEGER);";
        DBUtils.INSTANCE.getDb().execSQL(create);
        String insertSlq = "INSERT INTO " + tbName + "(CODE ,D,P ,OM_OC ,OM_OP ,OM_OL ,OC_OP ,OC_OL ,OP_OL ,M_C ,M_P ,M_L ,C_P ,C_L ,P_L ) VALUES ('" + code + "','" + date + "'," + p + "," + dFilter.toDerbyString() + ");";
        DBUtils.INSTANCE.getDb().execSQL(insertSlq);
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
