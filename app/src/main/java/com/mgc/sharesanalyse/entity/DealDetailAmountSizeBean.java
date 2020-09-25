package com.mgc.sharesanalyse.entity;

import com.mgc.sharesanalyse.utils.BigDecimalUtils;
import com.mgc.sharesanalyse.utils.GsonHelper;
import com.mgc.sharesanalyse.utils.LogUtil;

import java.util.ArrayList;

public class DealDetailAmountSizeBean {

    public String getToString(double dealAmount) {
        return "{" +
                (m100Size > 0 ? ",m100Size=" + m100Size : "") +
                (m50Size > 0 ? ",m50Size=" + m50Size : "") +
                (m30Size > 0 ? ",m30Size=" + m30Size : "") +
                (m10Size > 0 ? ",m10Size=" + m10Size : "") +
                (m5Size > 0 ? ",m5Size=" + m5Size : "") +
                (m1Size > 0 ? ",m1Size=" + m1Size : "") +
                (m05Size > 0 ? ",m05Size=" + m05Size : "") +
                (m01Size > 0 ? ",m01Size=" + m01Size : "") +
                (getGt5000() > 0 ? ",>5000=" + getGt5000()+",percent:"+ BigDecimalUtils.INSTANCE.div(getGt5000(),dealAmount)+";" : "") +
                (getGt1000() > 0 ? ",>1000=" + getGt1000()+",percent:"+ BigDecimalUtils.INSTANCE.div(getGt1000(),dealAmount)+";" : "") +
                (getGt500() > 0 ? ",>500=" + getGt500()+",percent:"+ BigDecimalUtils.INSTANCE.div(getGt500(),dealAmount)+";" : "") +
                (getGt100() > 0 ? ",>100=" + getGt100()+",percent:"+ BigDecimalUtils.INSTANCE.div(getGt100(),dealAmount)+";" : "") +
                '}';
    }

    private double getGt5000() {
        double beiging = 0;
        if (null !=m100List) {
            for (int i = 0; i < m100List.size(); i++) {
                beiging = beiging + m100List.get(i).amount;
            }
        }
        if (null != m50List) {
            for (int i = 0; i < m50List.size(); i++) {
                beiging = beiging + m50List.get(i).amount;
            }
        }
        return beiging;
    }

    private double getGt1000() {
        double beiging = getGt5000();
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

    private double getGt500() {
        double beiging = getGt1000();
        if (null != m5List) {
            for (int i = 0; i < m5List.size(); i++) {
                beiging = beiging + m5List.get(i).amount;
            }
        }
        return beiging;
    }

    private double getGt100() {
        double beiging = getGt500();
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

    private <T> String array2Json(ArrayList<T> array) {
        LogUtil.d("GsonHelper.toJson(array):" + GsonHelper.toJson(array));
        return "'" + GsonHelper.toJson(array) + "'";
    }


    private ArrayList<M100> m100List;
    private int m100Size;

    public static class M100 {
        private String time;
        private double amount;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        private double price;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    private ArrayList<M50> m50List;
    private int m50Size;

    public static class M50 {
        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        private double price;
        private String time;
        private double amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    private ArrayList<M30> m30List;
    private int m30Size;

    public static class M30 {
        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        private double price;
        private String time;
        private double amount;
    }

    private ArrayList<M10> m10List;
    private int m10Size;

    public static class M10 {
        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        private double price;
        private String time;
        private double amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
    //,,m30,m10,m5,m1,m05,m01-->(time、amount),size

    private ArrayList<M5> m5List;
    private int m5Size;

    public static class M5 {
        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        private double price;
        private String time;
        private double amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    private ArrayList<M1> m1List;
    private int m1Size;

    public static class M1 {
        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        private double price;
        private String time;
        private double amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
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

}
