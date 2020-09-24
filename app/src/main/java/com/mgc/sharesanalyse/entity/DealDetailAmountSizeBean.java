package com.mgc.sharesanalyse.entity;

import com.mgc.sharesanalyse.utils.GsonHelper;
import com.mgc.sharesanalyse.utils.LogUtil;

import java.util.ArrayList;

public class DealDetailAmountSizeBean {
    @Override
    public String toString() {
        return "DealDetailAmountSizeBean{" +
                "m100List=" + m100List +
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
                ", m05List=" + m05List +
                ", m05Size=" + m05Size +
                ", m01List=" + m01List +
                ", m01Size=" + m01Size +
                '}';
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
                "," + array2Json(m05List) +
                "," + array2Json(m01List) +
                ");";
    }

    private <T> String array2Json(ArrayList<T> array) {
        LogUtil.d("GsonHelper.toJson(array):"+GsonHelper.toJson(array));
        return "'"+GsonHelper.toJson(array)+"'";
    }



    private ArrayList<M100> m100List;
    private int m100Size;
    public static class M100{
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
    public static class M50{
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
    public static class M30{
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
    public static class M10{
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
    public static class M5{
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
    public static class M1{
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

    private ArrayList<M05> m05List;
    private int m05Size;
    public static class M05{
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

    private ArrayList<M01> m01List;

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

    public ArrayList<M05> getM05List() {
        return m05List;
    }

    public void setM05List(ArrayList<M05> m05List) {
        this.m05List = m05List;
    }

    public ArrayList<M01> getM01List() {
        return m01List;
    }

    public void setM01List(ArrayList<M01> m01List) {
        this.m01List = m01List;
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
    public static class M01{
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
}
