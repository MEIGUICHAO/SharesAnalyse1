package com.mgc.sharesanalyse.entity;

import android.text.TextUtils;

import com.mgc.sharesanalyse.utils.BigDecimalUtils;
import com.mgc.sharesanalyse.utils.GsonHelper;
import com.mgc.sharesanalyse.utils.LogUtil;

import java.util.ArrayList;

public class DealDetailAmountSizeBean {

    public String getToString(float dealAmount) {
        return "{" +
                (m100Size > 0 ? ",m100Size=" + m100Size : "") +
                (m50Size > 0 ? ",m50Size=" + m50Size : "") +
                (m30Size > 0 ? ",m30Size=" + m30Size : "") +
                (m10Size > 0 ? ",m10Size=" + m10Size : "") +
                (m5Size > 0 ? ",m5Size=" + m5Size : "") +
                (m1Size > 0 ? ",m1Size=" + m1Size : "") +
                (m05Size > 0 ? ",m05Size=" + m05Size : "") +
                (m01Size > 0 ? ",m01Size=" + m01Size : "") +
                (getGt5000() > 0 ? ",>5000=" + getGt5000() + ",>5000percent:" + BigDecimalUtils.INSTANCE.div(getGt5000(), dealAmount) + ";" : "") +
                (getGt1000() > 0 ? ",>1000=" + getGt1000() + ",>1000percent:" + BigDecimalUtils.INSTANCE.div(getGt1000(), dealAmount) + ";" : "") +
                (getGt500() > 0 ? ",>500=" + getGt500() + ",>500percent:" + BigDecimalUtils.INSTANCE.div(getGt500(), dealAmount) + ";" : "") +
                (getGt100() > 0 ? ",>100=" + getGt100() + ",>100percent:" + BigDecimalUtils.INSTANCE.div(getGt100(), dealAmount) + ";" : "") +
                '}';
    }

    @Override
    public String toString() {
        return "DealDetailAmountSizeBean{" +
                "gt5000Mamount=" + gt5000Mamount +
                ", gt1000Mamount=" + gt1000Mamount +
                ", gt500Mamount=" + gt500Mamount +
                ", gt100Mamount=" + gt100Mamount +
                ", allsizeSDJ='" + allsizeSDJ + '\'' +
                ", m100SDJ='" + m100SDJ + '\'' +
                ", m50SDJ='" + m50SDJ + '\'' +
                ", m30SDJ='" + m30SDJ + '\'' +
                ", m10SDJ='" + m10SDJ + '\'' +
                ", m5SDJ='" + m5SDJ + '\'' +
                ", m1SDJ='" + m1SDJ + '\'' +
                ", m05SDJ='" + m05SDJ + '\'' +
                ", m01SDJ='" + m01SDJ + '\'' +
                ", date='" + date + '\'' +
                ", m100List=" + m100List +
                ", m100Size=" + m100Size +
                ", m50List=" + m50List +
                ", m50Size=" + m50Size +
                ", m30List=" + m30List +
                ", m30Size=" + m30Size +
                ", m10List=" + m10List +
                ", m10Size=" + m10Size +
                ", m5List=" + m5List +
                ", m5Size=" + m5Size +
                ", m1List=" + m1List +
                ", m1Size=" + m1Size +
                ", m05Size=" + m05Size +
                ", m01Size=" + m01Size +
                '}';
    }

    public float getGt5000() {
        float beiging = 0;
        if (null != m100List) {
            for (int i = 0; i < m100List.size(); i++) {
                beiging = beiging + m100List.get(i).amount;
            }
        }
        if (null != m50List) {
            for (int i = 0; i < m50List.size(); i++) {
                beiging = beiging + m50List.get(i).amount;
            }
        }
        LogUtil.d("getGt5000:"+beiging);
        return beiging;
    }

    public float getGt1000() {
        float beiging = getGt5000();
        if (null != m30List) {
            for (int i = 0; i < m30List.size(); i++) {
                beiging = beiging + m30List.get(i).amount;
            }
        }
        if (null != m10List) {
            for (int i = 0; i < m10List.size(); i++) {
                beiging = beiging + m10List.get(i).amount;
            }
        }
        return beiging;
    }

    public float getGt500() {
        float beiging = getGt1000();
        if (null != m5List) {
            for (int i = 0; i < m5List.size(); i++) {
                beiging = beiging + m5List.get(i).amount;
            }
        }
        return beiging;
    }

    public float getGt100() {
        float beiging = getGt500();
        if (null != m1List) {
            for (int i = 0; i < m1List.size(); i++) {
                beiging = beiging + m1List.get(i).amount;
            }
        }
        return beiging;
    }


    public String toValues() {
        return
                "," + m100Size +
                        "," + m50Size +
                        "," + m30Size +
                        "," + m10Size +
                        "," + m5Size +
                        "," + m1Size +
                        "," + m05Size +
                        "," + m01Size +
                        "," + array2Json(m100List) +
                        "," + array2Json(m50List) +
                        "," + array2Json(m30List) +
                        "," + array2Json(m10List) +
                        "," + array2Json(m5List) +
                        "," + array2Json(m1List) +
                        ");";
    }

    private long gt5000Mamount;
    private long gt1000Mamount;
    private long gt500Mamount;
    private long gt100Mamount;

    public String getAllsizeSDJ() {
        return allsizeSDJ;
    }

    public void setAllsizeSDJ(String allsizeSDJ) {
        this.allsizeSDJ = allsizeSDJ;
    }

    private String allsizeSDJ;
    private String m100SDJ;
    private String m50SDJ;
    private String m30SDJ;
    private String m10SDJ;
    private String m5SDJ;
    private String m1SDJ;

    public long getGt5000Mamount() {
        return gt5000Mamount;
    }

    public void setGt5000Mamount(long gt5000Mamount) {
        this.gt5000Mamount = gt5000Mamount;
    }

    public long getGt1000Mamount() {
        return gt1000Mamount;
    }

    public void setGt1000Mamount(long gt1000Mamount) {
        this.gt1000Mamount = gt1000Mamount;
    }

    public long getGt500Mamount() {
        return gt500Mamount;
    }

    public void setGt500Mamount(long gt500Mamount) {
        this.gt500Mamount = gt500Mamount;
    }

    public long getGt100Mamount() {
        return gt100Mamount;
    }

    public void setGt100Mamount(long gt100Mamount) {
        this.gt100Mamount = gt100Mamount;
    }

    public String getM100SDJ() {
        return m100SDJ;
    }

    public void setM100SDJ(String m100SDJ) {
        this.m100SDJ = m100SDJ;
    }

    public String getM50SDJ() {
        return m50SDJ;
    }

    public void setM50SDJ(String m50SDJ) {
        this.m50SDJ = m50SDJ;
    }

    public String getM30SDJ() {
        return m30SDJ;
    }

    public void setM30SDJ(String m30SDJ) {
        this.m30SDJ = m30SDJ;
    }

    public String getM10SDJ() {
        return m10SDJ;
    }

    public void setM10SDJ(String m10SDJ) {
        this.m10SDJ = m10SDJ;
    }

    public String getM5SDJ() {
        return m5SDJ;
    }

    public void setM5SDJ(String m5SDJ) {
        this.m5SDJ = m5SDJ;
    }

    public String getM1SDJ() {
        return m1SDJ;
    }

    public void setM1SDJ(String m1SDJ) {
        this.m1SDJ = m1SDJ;
    }

    public String getM05SDJ() {
        return m05SDJ;
    }

    public void setM05SDJ(String m05SDJ) {
        this.m05SDJ = m05SDJ;
    }

    public String getM01SDJ() {
        return m01SDJ;
    }

    public void setM01SDJ(String m01SDJ) {
        this.m01SDJ = m01SDJ;
    }

    private String m05SDJ;
    private String m01SDJ;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSDJstr(DealDetailAmountSizeBean curDDSBean, DealDetailTableBean lastDDASBean) {

        String allsizeSDJ = "'" + " " + "'";
        String m100SDJ ="'" + this.m100SDJ + "'";
        String m50SDJ = "'" +this.m50SDJ + "'";
        String m30SDJ ="'" + this.m30SDJ + "'";
        String m10SDJ = "'" +this.m10SDJ + "'";
        String m5SDJ = "'" +this.m5SDJ + "'";
        String m1SDJ =  "'" +this.m05SDJ+ "'";
        String m05SDJ = "'" + this.m1SDJ + "'";
        String m01SDJ = "'" +  this.m01SDJ + "'";

        allsizeSDJ = "'" + curDDSBean.date + ":" + lastDDASBean.getAllsize() + "_" + this.allsizeSDJ + "'";
        if (curDDSBean.m100Size > 0) {
            m100SDJ = "'" + this.m100SDJ + "_" + curDDSBean.date + ":" + curDDSBean.m100Size + "'";
        }
        if (curDDSBean.m50Size > 0) {
            m50SDJ = "'" + this.m50SDJ + "_" + curDDSBean.date + ":" + curDDSBean.m50Size + "'";
        }
        if (curDDSBean.m30Size > 0) {
            m30SDJ = "'" + this.m30SDJ + "_" + curDDSBean.date + ":" + curDDSBean.m30Size + "'";
        }
        if (curDDSBean.m10Size > 0) {
            m10SDJ = "'" + this.m10SDJ + "_" + curDDSBean.date + ":" + curDDSBean.m10Size + "'";
        }
        if (curDDSBean.m5Size > 0) {
            m5SDJ = "'" + this.m5SDJ + "_" + curDDSBean.date + ":" + curDDSBean.m5Size  +"'";
        }
        if (curDDSBean.m1Size > 0) {
            m1SDJ = "'" + this.m1SDJ + "_" + curDDSBean.date + ":" + curDDSBean.m1Size  +"'";
        }
        if (curDDSBean.m05Size > 0) {
            m05SDJ = "'" + this.m05SDJ + "_" + curDDSBean.date + ":" + curDDSBean.m05Size  +"'";
        }
        if (curDDSBean.m01Size > 0) {
            m01SDJ = "'" + this.m01SDJ + "_" + curDDSBean.date + ":" + curDDSBean.m01Size  +"'";
        }

//        if (!TextUtils.isEmpty(curDDSBean.getM100SDJ()) && TextUtils.isEmpty(this.m100SDJ)) {
//            m100SDJ = "'" + curDDSBean.date + ":"+curDDSBean.m100Size + "_" + this.m100SDJ + "'";
//        } else if (!TextUtils.isEmpty(curDDSBean.getM100SDJ())) {
//            m100SDJ = "'" + curDDSBean.date + ":" + curDDSBean.getM100SDJ() + "'";
//        }
//        if (!TextUtils.isEmpty(curDDSBean.getM50SDJ()) && TextUtils.isEmpty(this.m50SDJ)) {
//            m50SDJ = "'" + curDDSBean.getM50SDJ() + "_" + curDDSBean.date + ":" + this.m50SDJ + "'";
//        } else if (!TextUtils.isEmpty(curDDSBean.getM50SDJ())) {
//            m50SDJ = "'" + curDDSBean.date + ":" + curDDSBean.getM50SDJ() + "'";
//        }

//        if (!TextUtils.isEmpty(curDDSBean.getM30SDJ()) && TextUtils.isEmpty(this.m30SDJ)) {
//            m30SDJ = "'" + curDDSBean.getM30SDJ() + "_" + curDDSBean.date + ":" + this.m30SDJ + "'";
//        } else if (!TextUtils.isEmpty(curDDSBean.getM30SDJ())) {
//            m30SDJ = "'" + curDDSBean.date + ":" + curDDSBean.getM30SDJ() + "'";
//        }
//
//        if (!TextUtils.isEmpty(curDDSBean.getM10SDJ()) && TextUtils.isEmpty(this.m10SDJ)) {
//            m10SDJ = "'" + curDDSBean.getM10SDJ() + "_" + curDDSBean.date + ":" + this.m10SDJ + "'";
//        } else if (!TextUtils.isEmpty(curDDSBean.getM10SDJ())) {
//            m10SDJ = "'" + curDDSBean.date + ":" + curDDSBean.getM10SDJ() + "'";
//        }
//
//        if (!TextUtils.isEmpty(curDDSBean.getM5SDJ()) && TextUtils.isEmpty(this.m5SDJ)) {
//            m5SDJ = "'" + curDDSBean.getM5SDJ() + "_" + curDDSBean.date + ":" + this.m5SDJ + "'";
//        } else if (!TextUtils.isEmpty(curDDSBean.getM5SDJ())) {
//            m5SDJ = "'" + curDDSBean.date + ":" + curDDSBean.getM5SDJ() + "'";
//        }
//
//        if (!TextUtils.isEmpty(curDDSBean.getM1SDJ()) && TextUtils.isEmpty(this.m1SDJ)) {
//            m1SDJ = "'" + curDDSBean.getM1SDJ() + "_" + curDDSBean.date + ":" + this.m1SDJ + "'";
//        } else if (!TextUtils.isEmpty(curDDSBean.getM1SDJ())) {
//            m1SDJ = "'" + curDDSBean.date + ":" + curDDSBean.getM1SDJ() + "'";
//        }
//
//        if (!TextUtils.isEmpty(curDDSBean.getM05SDJ()) && TextUtils.isEmpty(this.m05SDJ)) {
//            m1SDJ = "'" + curDDSBean.getM05SDJ() + "_" + curDDSBean.date + ":" + this.m05SDJ + "'";
//        } else if (!TextUtils.isEmpty(curDDSBean.getM05SDJ())) {
//            m1SDJ = "'" + curDDSBean.date + ":" + curDDSBean.getM05SDJ() + "'";
//        }
//
//        if (!TextUtils.isEmpty(curDDSBean.getM01SDJ()) && TextUtils.isEmpty(this.m01SDJ)) {
//            m1SDJ = "'" + curDDSBean.getM01SDJ() + "_" + curDDSBean.date + ":" + this.m01SDJ + "'";
//        } else if (!TextUtils.isEmpty(curDDSBean.getM01SDJ())) {
//            m1SDJ = "'" + curDDSBean.date + ":" + curDDSBean.getM01SDJ() + "'";
//        }
        return "ALLSIZESDJ=" + allsizeSDJ + ",M100SDJ=" + m100SDJ + ",M50SDJ=" + m50SDJ + ",M30SDJ=" + m30SDJ + ",M10SDJ=" + m10SDJ + ",M5SDJ=" + m5SDJ + ",M1SDJ=" + m1SDJ + ",M05SDJ=" + m05SDJ + ",M01SDJ=" + m01SDJ;
    }

    public String getInsertSDJstr(DealDetailAmountSizeBean ddsBean, int allsize) {

        String allsizeSDJ = "'" + " " + "'";
        String m100SDJ = "'" + " " + "'";
        String m50SDJ = "'" + " " + "'";
        String m30SDJ = "'" + " " + "'";
        String m10SDJ = "'" + " " + "'";
        String m5SDJ = "'" + " " + "'";
        String m1SDJ = "'" + " " + "'";
        String m05SDJ = "'" + " " + "'";
        String m01SDJ = "'" + " " + "'";

        allsizeSDJ = "'" + date + ":" + allsize + "'";
        if (ddsBean.m100Size > 0) {
            m100SDJ = "'" + date + ":" + ddsBean.m100Size + "'";
        }
        if (ddsBean.m50Size > 0) {
            m50SDJ = "'" + date + ":" + ddsBean.m50Size + "'";
        }
        if (ddsBean.m30Size >0) {
            m30SDJ = "'" + date + ":" + ddsBean.m30Size + "'";
        }
        if (ddsBean.m10Size>0) {
            m10SDJ = "'" + date + ":" + ddsBean.m10Size + "'";
        }
        if (ddsBean.m5Size > 0) {
            m5SDJ = "'" + date + ":" + ddsBean.m5Size + "'";
        }
        if (ddsBean.m1Size > 0) {
            m1SDJ = "'" + date + ":" + ddsBean.m1Size + "'";
        }
        if (ddsBean.m05Size > 0) {
            m05SDJ = "'" + date + ":" + ddsBean.m05Size + "'";
        }
        if (ddsBean.m01Size > 0) {
            m01SDJ = "'" + date + ":" + ddsBean.m01Size + "'";
        }

        return allsizeSDJ + "," + m100SDJ + "," + m50SDJ + "," + m30SDJ + "," + m10SDJ + "," + m5SDJ + "," + m1SDJ + "," + m05SDJ + "," + m01SDJ;
    }


    public String toInsertSumDDValues(DealDetailAmountSizeBean ddBean, int allsize) {
        return
                "," + (date) +
                        "," + (m100Size) +
                        "," + (m50Size) +
                        "," + (m30Size) +
                        "," + (m10Size) +
                        "," + (m5Size) +
                        "," + (m1Size) +
                        "," + (m05Size) +
                        "," + (m01Size) +
                        "," + (allsize - m100Size - m50Size - m30Size - m10Size - m5Size - m1Size - m05Size - m01Size) +
                        "," + (getGt5000()) +
                        "," + (getGt1000()) +
                        "," + (getGt500()) +
                        "," + (getGt100()) +
                        "," + getInsertSDJstr(ddBean, allsize) +
                        ");";
    }

    public String toUpdateSumDDValues(DealDetailTableBean lastDDASBean) {
        DealDetailAmountSizeBean mSizeBean = lastDDASBean.getSizeBean();

        return
                ",M100S=" + (mSizeBean.m100Size + m100Size) +
                        ",M50S=" + (mSizeBean.m50Size + m50Size) +
                        ",M30S=" + (mSizeBean.m30Size + m30Size) +
                        ",M10S=" + (mSizeBean.m10Size + m10Size) +
                        ",M5S=" + (mSizeBean.m5Size + m5Size) +
                        ",M1S=" + (mSizeBean.m1Size + m1Size) +
                        ",M05S=" + (mSizeBean.m05Size + m05Size) +
                        ",M01S=" + (mSizeBean.m01Size + m01Size) +
                        ",L01S=" + ((lastDDASBean.getAllsize()-mSizeBean.m100Size-mSizeBean.m50Size-mSizeBean.m30Size-mSizeBean.m10Size-mSizeBean.m5Size-mSizeBean.m1Size-mSizeBean.m05Size-mSizeBean.m01Size) + l01Size) +
                        ",G5000M=" + (mSizeBean.getGt5000() + gt5000Mamount) +
                        ",G1000M=" + (mSizeBean.getGt1000() + gt1000Mamount) +
                        ",G500M=" + (mSizeBean.getGt500() + gt500Mamount) +
                        ",G100M=" + (mSizeBean.getGt100() + gt100Mamount) +
                        "," + getSDJstr(mSizeBean, lastDDASBean) +
                        "";
    }

    private <T> String array2Json(ArrayList<T> array) {
        LogUtil.d("GsonHelper.toJson(array):" + GsonHelper.toJson(array));
        return "'" + GsonHelper.toJson(array) + "'";
    }


    private ArrayList<M100> m100List;
    private int m100Size;

    public static class M100 {
        private String time;
        private float amount;

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        private float price;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }
    }

    private ArrayList<M50> m50List;
    private int m50Size;

    public static class M50 {
        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        private float price;
        private String time;
        private float amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }
    }

    private ArrayList<M30> m30List;
    private int m30Size;

    public static class M30 {
        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        private float price;
        private String time;
        private float amount;
    }

    private ArrayList<M10> m10List;
    private int m10Size;

    public static class M10 {
        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        private float price;
        private String time;
        private float amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }
    }
    //,,m30,m10,m5,m1,m05,m01-->(time、amount),size

    private ArrayList<M5> m5List;
    private int m5Size;

    public static class M5 {
        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        private float price;
        private String time;
        private float amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }
    }

    private ArrayList<M1> m1List;
    private int m1Size;

    public static class M1 {
        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        private float price;
        private String time;
        private float amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }
    }

    private int m05Size;


    public ArrayList<M100> getM100List() {
        return m100List;
    }

    public void setM100List(ArrayList<M100> m100List) {
        this.m100List = m100List;
    }

    public ArrayList<M50> getM50List() {
        return m50List;
    }

    public void setM50List(ArrayList<M50> m50List) {
        this.m50List = m50List;
    }

    public ArrayList<M30> getM30List() {
        return m30List;
    }

    public void setM30List(ArrayList<M30> m30List) {
        this.m30List = m30List;
    }

    public ArrayList<M10> getM10List() {
        return m10List;
    }

    public void setM10List(ArrayList<M10> m10List) {
        this.m10List = m10List;
    }

    public ArrayList<M5> getM5List() {
        return m5List;
    }

    public void setM5List(ArrayList<M5> m5List) {
        this.m5List = m5List;
    }

    public ArrayList<M1> getM1List() {
        return m1List;
    }

    public void setM1List(ArrayList<M1> m1List) {
        this.m1List = m1List;
    }


    public int getM100Size() {
        return m100Size;
    }

    public void setM100Size(int m100Size) {
        this.m100Size = m100Size;
    }

    public int getM50Size() {
        return m50Size;
    }

    public void setM50Size(int m50Size) {
        this.m50Size = m50Size;
    }


    public int getM30Size() {
        return m30Size;
    }

    public void setM30Size(int m30Size) {
        this.m30Size = m30Size;
    }


    public int getM10Size() {
        return m10Size;
    }

    public void setM10Size(int m10Size) {
        this.m10Size = m10Size;
    }

    public int getM5Size() {
        return m5Size;
    }

    public void setM5Size(int m5Size) {
        this.m5Size = m5Size;
    }


    public int getM1Size() {
        return m1Size;
    }

    public void setM1Size(int m1Size) {
        this.m1Size = m1Size;
    }


    public int getM05Size() {
        return m05Size;
    }

    public void setM05Size(int m05Size) {
        this.m05Size = m05Size;
    }


    public int getM01Size() {
        return m01Size;
    }

    public void setM01Size(int m01Size) {
        this.m01Size = m01Size;
    }

    private int m01Size;
    private int l01Size;

    public int getL01Size() {
        return l01Size;
    }

    public void setL01Size(int l01Size) {
        this.l01Size = l01Size;
    }
}
