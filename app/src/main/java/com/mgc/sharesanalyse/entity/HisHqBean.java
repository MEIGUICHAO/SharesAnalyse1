package com.mgc.sharesanalyse.entity;

import java.util.List;

public class HisHqBean {

    /**
     * status : 0
     * hq : [["2020-09-11","7.09","7.32","-0.07","-0.95%","6.90","7.45","2766016","197856.86","3.28%"],["2020-09-10","7.67","7.39","0.07","0.96%","7.33","7.89","3348956","254184.73","3.97%"]]
     * code : cn_601216
     */
//    日期	开盘	收盘	涨跌额	涨跌幅	最低	最高	成交量(手)	成交金额(万)	换手率

    private int status;
    private String code;
    private List<List<String>> hq;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<List<String>> getHq() {
        return hq;
    }

    public void setHq(List<List<String>> hq) {
        this.hq = hq;
    }
}
